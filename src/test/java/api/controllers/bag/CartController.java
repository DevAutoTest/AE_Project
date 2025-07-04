package api.controllers.bag;

import api.dto.AddItemRequest;
import api.dto.CartResponse;
import api.config.TestPropertiesConfig;
import api.dto.DeleteItemRequest;
import api.dto.UpdateItemRequest;
import api.support.GuestTokenProvider;
import api.utils.CsvReader;
import io.qameta.allure.Step;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CartController {

    /* ───────── endpoints ───────── */
    private static final String BAG_ENDPOINT        = "/ugp-api/bag/v1";
    private static final String ITEMS_ENDPOINT      = "/ugp-api/bag/v1/items";

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    /* ───────── common spec ─────── */
    private final RequestSpecification spec() {
        var box = GuestTokenProvider.current();          // {token,cookie}

        return given()
                .baseUri(cfg.getApiBaseUrl())
                .contentType("application/json")
                .accept("application/vnd.oracle.resource+json,*/*;q=0.01")
                .header("authorization", "Bearer " + box.token())
                .header("x-access-token", box.token())
                .header("aesite", "AEO_US")
                .header("aelang", "en_US")
                .header("aecountry", "US")
                .header("cookie", box.cookie())          // _abck; ak_bmsc; bm_sz
                .header("user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                                + "AppleWebKit/537.36 (KHTML, like Gecko) "
                                + "Chrome/138.0.0.0 Safari/537.36")
                .filter(new RequestLoggingFilter(LogDetail.ALL))
                .filter(new ResponseLoggingFilter(LogDetail.ALL));
    }

    /* --------------- API --------------- */

    @Step("Get cart")
    public CartResponse getCart() {
        return spec()
                .get("/ugp-api/bag/v1")
                .then().statusCode(200)
                .extract().as(CartResponse.class);
    }

    @Step("Add item to cart")
    public Response addItem(String skuId, int quantity) {
        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        AddItemRequest body     = new AddItemRequest(List.of(item));

        return spec()
                .body(body)
                .when().post(ITEMS_ENDPOINT)
                .then().statusCode(202)
                .extract().response();
    }

    @Step("Add items to cart from csv")
    public Response  addItemsToCartFromCsv()  {
        List<AddItemRequest.Item> items = CsvReader.readItemsFromCsv("testData/addCartItems.csv");
        AddItemRequest requestBody = new AddItemRequest(items);
        System.out.println(requestBody);
        return spec()
                .body(requestBody)
                .when()
                .post(ITEMS_ENDPOINT)
                .then()
                .statusCode(202)
                .body("cartId", notNullValue())
                .extract()
                .response();
    }

    @Step("Update item count")
    public Response updateItem(String skuId, int quantity, String itemId)  {
        UpdateItemRequest.Item item = new UpdateItemRequest.Item(skuId, quantity, itemId);
        UpdateItemRequest requestBody = new UpdateItemRequest(List.of(item));

        return spec()
                .body(requestBody)
                .when()
                .patch(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Delete item")
    public Response deleteItem(String itemIds)  {
        DeleteItemRequest.Item item = new DeleteItemRequest.Item(itemIds);
        DeleteItemRequest requestBody = new DeleteItemRequest(List.of(item));

        return spec()
                .body(requestBody)
                .when()
                .delete(ITEMS_ENDPOINT+ "?itemIds=" + itemIds)
                .andReturn();
    }
}