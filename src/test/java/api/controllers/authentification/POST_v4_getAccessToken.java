package api.controllers.authentification;

import api.support.GuestTokenProvider;
import api.config.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class POST_v4_getAccessToken {
    private static final String AUTH_TOKEN_ENDPOINT = "/ugp-api/auth/oauth/v4/token";
    static TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());


    @Step("Get AUTH token (grant_type=password)")
    public static String getAuthToken() {
    // 1. Берём свежий guest-token и анти-бот куки
        // 1. Берём свежий guest-token и анти-бот куки
        var box = GuestTokenProvider.current();


    // 3. Делаем POST
    Response resp = given()
            .filter(new RequestLoggingFilter(LogDetail.ALL))   //  ← полный запрос
            .filter(new ResponseLoggingFilter(LogDetail.ALL))
            .baseUri(configProperties.getApiBaseUrl())
            /* browser finger-print (можно сократить, но минимальный набор – UA) */
            .header("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                            + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")

            /* — обязательные служебные заголовки — */
            .accept("application/vnd.oracle.resource+json")
            .header("accept-language", "en-US,en;q=0.9")
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .header("authorization", configProperties.getBasicAuth())    // client_id:secret
            .header("aesite",  "AEO_US")
            .header("aelang",  "en_US")
            .header("aecountry", "US")                      // ← ОБЯЗАТЕЛЕН!
            .header("x-access-token", box.token())
            .header("cookie",        box.cookie())

            /* тело формы */
            .formParam("grant_type", "password")
            .formParam("username",   configProperties.getUserName())
            .formParam("password",   configProperties.getPassword())

            .log().ifValidationFails()
            .post(AUTH_TOKEN_ENDPOINT)
            .then().log().ifValidationFails()
            .statusCode(200)                      // <- теперь проходит
            .extract().response();

        return resp.jsonPath().getString("access_token");
    }
}
