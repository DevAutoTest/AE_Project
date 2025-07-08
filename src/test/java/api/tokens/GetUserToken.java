package api.tokens;
import api.config.TestPropertiesConfig;
import api.controllers.authentification.POST_v4_getAccessToken;
import api.support.GuestTokenProvider;
import api.utils.JwtUtils;
import org.aeonbits.owner.ConfigFactory;

/**
 * Отдаёт JWT-токен:
 * <ul>
 *   <li>если заданы username / password — получает и кеширует auth-token;</li>
 *   <li>иначе — возвращает guest-token.</li>
 * </ul>
 */
public final class GetUserToken {

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);


    private static boolean guestMode() {
        return "GUEST".equalsIgnoreCase(cfg.getUserMode());
    }

    /** кешируем JWT, пока он жив */
    private static volatile String cached;

    private GetUserToken() { /* util-class */ }

    /** Главная точка получения токена. */
    public static String token() {

        // если уже есть и не протух — сразу отдаём
        if (cached != null && !JwtUtils.isExpired(cached)) {
            return cached;
        }
        /* guest-режим принудительно */
        if (guestMode()) {
            return cached = GuestTokenProvider.current().token();
        }


        /* авторизация только если есть логин-пароль */
        if (haveCreds()) {
            cached = POST_v4_getAccessToken.getAuthToken();
        } else {
            cached = GuestTokenProvider.current().token();
        }
        return cached;
    }

    /* ---------- helpers ---------- */

    /** выбирает guest или auth пользователь*/
    private static boolean haveCreds() {
        return cfg.getUserName() != null && !cfg.getUserName().isBlank()
                && cfg.getPassword() != null && !cfg.getPassword().isBlank();
    }
}