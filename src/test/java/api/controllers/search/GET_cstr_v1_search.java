package api.controllers.search;

import api.config.TestPropertiesConfig;
import api.dto.SearchResponse;
import api.support.GuestTokenProvider;
import api.utils.CookieUtils;
import api.utils.RestLog;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class GET_cstr_v1_search {

    private static final String SEARCH_ENDPOINT = "ugp-api/cstr/v1/search";

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);

    /* ---------- общий spec ---------- */
    private RequestSpecification spec() {

        GuestTokenProvider.Box antiBot = GuestTokenProvider.current();
        String allCookies = CookieUtils.buildCookies(antiBot.cookie());

        return given()
                .filter(RestLog.rq()).filter(RestLog.rs())
                .baseUri(cfg.getApiBaseUrl())
                .header("authorization", "Bearer " + antiBot.token())
                .header("x-access-token", antiBot.token())
                .header("cookie", allCookies);
    }

    /* --------------- API --------------- */

    @Step("Get search colorful socks without filters ")
    public SearchResponse getAllSearchResponse() {
        return spec()
                .queryParam("query", "colorful socks")
                .queryParam("resultsPerPage", 60)
                .queryParam("us", "ae")
                .when()
                .get(SEARCH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().as(SearchResponse.class);
    }

    @Step("Get search colorful socks filtered by AE brand ")
    public SearchResponse.Example getResponseFilteredByBrand() {
        SearchResponse.Example example;
        return example =

                given()
                        .spec(spec())
                        .queryParam("query", "colorful socks")
                        .queryParam("brand", "AE")
                        .queryParam("resultsPerPage", 60)
                        .queryParam("us", "ae")
                        .when()
                        .get(SEARCH_ENDPOINT)
                        .then()
                        .statusCode(200)
                        .extract().as(SearchResponse.Example.class);
    }

    @Step("Get search colorful socks filtered by gender ")
    public SearchResponse getResponseFilteredByGender() {
        return spec()
                .queryParam("query", "colorful socks")
                .queryParam("gender", "Men")
                .queryParam("us", "ae")
                .when()
                .get(SEARCH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().as(SearchResponse.class);
    }

    @Step("Check filter was selected by Brand")
    public SearchResponse getResponseFilterWasCheckedByBrand() {
        return spec()
                .queryParam("query", "colorful socks")
                .queryParam("brand", "Aerie")
                .queryParam("resultsPerPage", 60)
                .queryParam("us", "ae")
                .when()
                .get(SEARCH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().as(SearchResponse.class);
    }

    @Step("enter unexpected value")
    public SearchResponse getZeroResults() {
        return spec()
                .queryParam("query", "123123")
                .queryParam("resultsPerPage", 60)
                .queryParam("us", "ae")
                .when()
                .get(SEARCH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().as(SearchResponse.class);
    }


}
