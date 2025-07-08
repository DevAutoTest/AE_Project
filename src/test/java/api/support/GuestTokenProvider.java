package api.support;

import api.config.TestPropertiesConfig;
import api.utils.JwtUtils;
import api.utils.RestLog;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;


public final class GuestTokenProvider {

    private static final String ENDPOINT = "/ugp-api/auth/oauth/v5/token";
    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);
    /* ---------- вот сюда! ---------- */
    /** Единый User-Agent — им пользуемся в *любых* запросах к AEO-API. */
    public static final String UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) "
                    + "Chrome/138.0.0.0 Safari/537.36";

    public record Box(String token, String cookie, long exp) {}

    private static volatile Box cache;

    public static Box current() {

        if (cache != null && cache.exp > System.currentTimeMillis()) return cache;

        Response resp = given()
                .filter(RestLog.rq())          // ➊
                .filter(RestLog.rs())
                .baseUri(cfg.getApiBaseUrl())
                .contentType("application/x-www-form-urlencoded")
                .header("authorization", cfg.getBasicAuth())
                .header("aesite",  "AEO_US")
                .header("aelang",  "en_US")
                .header("aecountry","US")
                .formParam("grant_type", "client_credentials")
                .post(ENDPOINT)
                .then().statusCode(200)
                .extract().response();

        String jwt    = resp.jsonPath().getString("access_token");
        String cookie = String.join("; ",
                resp.getCookies().entrySet().stream()
                        .filter(e -> e.getKey().matches("_abck|ak_bmsc|bm_sz"))
                        .map(e -> e.getKey() + '=' + e.getValue())
                        .toList());

        long expMs = JwtUtils.expirationMillis(jwt);     // см. утилиту ниже
        return cache = new Box(jwt, cookie, expMs - 5_000);   // 5 сек. зазор
    }
}