package api.tokens;

import api.enums.UserRoles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class GetUser {
   private static final Properties props = new Properties();

    static {
        try (InputStream input = GetUser.class.getClassLoader().getResourceAsStream("properties/auth.properties")) {
            if (input == null) {
                throw new RuntimeException("auth.properties not found");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load auth.properties", e);
        }
    }

    public static String getToken() throws IOException, InterruptedException {
        String modeString = props.getProperty("auth.mode", "AUTH").toUpperCase();
        UserRoles mode = UserRoles.valueOf(modeString);

        return switch (mode) {
            case GUEST -> GetTokenService.getGuestToken();
            case AUTH -> {
                String username = props.getProperty("auth.username");
                String password = props.getProperty("auth.password");
                yield GetTokenService.getAuthToken(username, password);
            }
        };
    }
}
