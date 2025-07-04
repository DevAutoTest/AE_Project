package api.tokens;

import api.config.TestPropertiesConfig;
import api.controllers.authentification.POST_v4_getAccessToken;
import api.enums.UserRoles;
import api.support.GuestTokenProvider;
import org.aeonbits.owner.ConfigFactory;

public class GetUserToken {
    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    private GetUserToken() {/* util-class */}

    /** Режим, заданный в <code>auth.properties</code> (GUEST / AUTH). */
    private static UserRoles mode() {
        // лишний trim() спасёт от случайных пробелов в файле
        return UserRoles.valueOf(configProperties.getUserMode());
    }

    /** Всегда возвращает валидный токен. */
    public static String token() {
        return switch (mode()) {
            case GUEST -> GuestTokenProvider.current().token();          // x-access-token
            case AUTH  -> POST_v4_getAccessToken.getAuthToken(); // Bearer ***…
        };
    }
}