package api.controllers.authentification;

import api.support.GuestTokenProvider;
import api.config.TestPropertiesConfig;
import api.utils.RestLog;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static io.restassured.RestAssured.given;

/**
 * AUTH-token (grant_type=password) for registered user.
 */
public final class POST_v4_getAccessToken {

    private static final String ENDPOINT = "/ugp-api/auth/oauth/v4/token";
    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);

    private POST_v4_getAccessToken() {
    }

    @Step("Get AUTH token (grant_type=password)")
    public static String getAuthToken() {

        GuestTokenProvider.Box antiBot = GuestTokenProvider.current();
        Map<String, String> warm = api.utils.AkamaiCookieHolder.get();

       // AKAMAI anti bot:
        Stream<Map.Entry<String, String>> akamai = warm.entrySet().stream();
        Stream<Map.Entry<String, String>> guest =
                Arrays.stream(antiBot.cookie().split(";"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(s -> s.split("=", 2))
                        .map(a -> Map.entry(a[0].trim(), a[1]));

        String allCookies = Stream.concat(akamai, guest)
                .map(e -> e.getKey() + '=' + e.getValue())
                .collect(Collectors.joining("; "));

        System.out.println("COOKIES ➜ " + allCookies);

        return given()
                .filter(RestLog.rq()).filter(RestLog.rs())
                .baseUri(cfg.getApiBaseUrl())
                .contentType("application/x-www-form-urlencoded")
                .header("User-Agent", GuestTokenProvider.UA)
                .header("authorization", cfg.getBasicAuth())
                .header("aesite", "AEO_US")
                .header("aelang", "en_US")
                .header("aecountry", "US")
                .header("x-access-token", antiBot.token())
                .header("cookie", allCookies)
                .formParam("grant_type", "password")
                .formParam("username", cfg.getUserName())
                .formParam("password", cfg.getPassword())
                .post(ENDPOINT)
                .then().statusCode(200)
                .extract().jsonPath().getString("access_token");
    }
}