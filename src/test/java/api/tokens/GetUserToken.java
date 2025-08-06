package api.tokens;
import api.config.TestPropertiesConfig;
import api.controllers.authentification.POST_v4_getAccessToken;
import api.support.GuestTokenProvider;
import api.utils.JwtUtils;
import org.aeonbits.owner.ConfigFactory;

public final class GetUserToken {

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);

    private static boolean guestMode() {
        return "GUEST".equalsIgnoreCase(cfg.getUserMode());
    }

    /** cash JWT*/
    private static volatile String cached;

    private GetUserToken() { /* util-class */ }

    public static String token() {

        if (cached != null && !JwtUtils.isExpired(cached)) {
            return cached;
        }

        if (guestMode()) {
            return cached = GuestTokenProvider.current().token();
        }

        if (haveCreds()) {
            cached = POST_v4_getAccessToken.getAuthToken();
        } else {
            cached = GuestTokenProvider.current().token();
        }
        return cached;
    }

    /** choose GUEST or AUTH user*/
    private static boolean haveCreds() {
        return cfg.getUserName() != null && !cfg.getUserName().isBlank()
                && cfg.getPassword() != null && !cfg.getPassword().isBlank();
    }
}