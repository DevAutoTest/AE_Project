package api.support;

import api.config.TestPropertiesConfig;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import java.time.Instant;
import static io.restassured.RestAssured.given;

/**
 * ―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
 *  GuestTokenProvider
 *  ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 *  Утилитный класс-«синглтон»:
 *  • получает guest-token для UGP-API («grant_type=client_credentials»)
 *  • вытягивает вместе с токеном три анти-бот cookies (_abck, ak_bmsc,
 *    bm_sz) – без них Akamai / BotManager вернёт 403 на любые POST/PUT
 *  • кеширует связку {token, cookie} до истечения срока действия токена
 *  • выдаёт всегда «свежую» пару через статические методы current()/token()
 *
 *  Использование
 *  ────────────
 *      var box = GuestTokenProvider.current();          // {token,cookie}
 *      request.header("authorization", "Bearer " + box.token());
 *      request.header("x-access-token", box.token());   // AEO-конвенция
 *      request.header("cookie",         box.cookie()); // три anti-bot
 *
 *  Thread-safe?  ✔
 *  Здесь достаточно обычного (не synchronized) поля, т.к. мизерные риски
 *  гонки во время first-call приводят лишь к лишнему запросу /token –
 *  сервис корректно вернёт два независимых guest-tokens.
 * ――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
 */



/** Получает и кеширует guest-token + анти-бот куки. */
public final class GuestTokenProvider {

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    /**
     /* ------------------------------------------------------------------ */
    /*  1. Immutable-футляр с данными о токене                            */
    /* ------------------------------------------------------------------ */
    /** пара {токен, cookie, expiry}
     * Начиная с Java 16 появился record — «компактный неизменяемый POJO».
     * Компилятор генерирует:
     *   • private final поля token/cookie/expiresAt
     *   • public конструктор
     *   • геттер-компоненты token(), cookie(), expiresAt()
     *   • equals()/hashCode()/toString().
     * Писать «ручной» класс ради трёх полей больше не нужно.
     */


    public record Box(String token, String cookie, Instant expiresAt) {}

    /** Единственный кеш (static) – живёт столько, сколько живёт JVM. */
    private static Box cached;   // кеш на время жизни guest-токена

    /* ------------------------------------------------------------------ */
    /*  2. Публичный API                                                  */
    /* ------------------------------------------------------------------ */

    /** Возвращает «гарантированно свежую» связку {token,cookie}. */
    public static Box current() {
        if (cached == null || cached.expiresAt.isBefore(Instant.now())) {
            cached = fetchFresh();
        }
        return cached;
    }

    /** Только сам guest-token (без cookie). */
    public static String token() {
        return current().token();      // берём токен из Box
    }
    /** Cookie-строка вида "_abck=…; ak_bmsc=…; bm_sz=…" */
    public static String cookie() {
        return current().cookie();
    }

    /* ------------------------------------------------------------------ */
    /*  3. Внутренний метод: запрос к /auth/oauth/v5/token                */
    /* ------------------------------------------------------------------ */
    private static Box fetchFresh() {
        /* ==========  HTTP POST /token  ========== */
        Response r = given()
                .baseUri(cfg.getApiBaseUrl())
                .header("authorization", cfg.getBasicAuth())
                .formParam("grant_type", "client_credentials")
                .post("/ugp-api/auth/oauth/v5/token")
                .then().statusCode(200).extract().response();

        /* ----------- парсим JSON и cookies ----------- */
        String token   = r.jsonPath().getString("access_token");
        int    ttl     = r.jsonPath().getInt("expires_in");   // секунд

        /* Собираем строку cookie: три anti-bot куки нужны ВСЕГДА. */
        String cookie  = String.join("; ",
                "_abck="   + r.getCookie("_abck"),
                "ak_bmsc=" + r.getCookie("ak_bmsc"),
                "bm_sz="   + r.getCookie("bm_sz"));

        /* Уменьшаем TTL на 30 с – чтобы не попасть на «крайний кадр». */
        Instant exp = Instant.now().plusSeconds(ttl - 30);
        return new Box(token, cookie, exp);
    }

    /* ------------------------------------------------------------------ */
    /*  4. Закрытый конструктор-заглушка                                  */
    /* ------------------------------------------------------------------ */
    /**
     * Утилитным классам запрещают создавать экземпляры:
     *   new GuestTokenProvider();  // так НЕ даём сделать
     *
     * private-конструктор помечается как «unused»,
     * IDE можно успокоить аннотацией @SuppressWarnings("unused").
     */
    @SuppressWarnings("unused")
    private GuestTokenProvider() {/* no-instance */}
  }
