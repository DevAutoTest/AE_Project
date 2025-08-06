package api.controllers.bag;

import api.dto.AddItemRequest;
import api.dto.CartResponse;
import api.config.TestPropertiesConfig;
import api.dto.DeleteItemRequest;
import api.dto.UpdateItemRequest;
import api.support.GuestTokenProvider;
import api.utils.CookieUtils;
import api.utils.CsvReader;
import api.utils.RestLog;
import io.qameta.allure.Description;
import io.qameta.allure.Step;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class CartController {

   // private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";
    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";

    private static final TestPropertiesConfig cfg =
            ConfigFactory.create(TestPropertiesConfig.class);

    /* ----------common spec ---------- */
    private RequestSpecification spec() {

        GuestTokenProvider.Box antiBot = GuestTokenProvider.current();

        return given()
                .filter(RestLog.rq()).filter(RestLog.rs())
                .baseUri(cfg.getApiBaseUrl())
                .accept(JSON)
                .contentType(JSON)
                .header("authorization", "Bearer " + antiBot.token())
                .header("x-access-token", antiBot.token())
                .header("aesite", "AEO_US");
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
        AddItemRequest body = new AddItemRequest(List.of(item));

        return spec()
                .body(body)
                .when()
                .post(ITEMS_ENDPOINT)
                .then()
                .extract().response();
    }

    @Step("Add items to cart from csv")
    @Description("Need ToDo")
    public Response addItemsToCartFromCsv() {
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
    public Response updateItem(String skuId, int quantity, String itemId) {
        UpdateItemRequest.Item item = new UpdateItemRequest.Item(skuId, quantity, itemId);
        UpdateItemRequest requestBody = new UpdateItemRequest(List.of(item));

        return spec()
                .body(requestBody)
                .when()
                .patch(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Delete item")
    public Response deleteItem(String itemIds) {
        DeleteItemRequest.Item item = new DeleteItemRequest.Item(itemIds);
        DeleteItemRequest requestBody = new DeleteItemRequest(List.of(item));

        return spec()
                .body(requestBody)
                .when()
                .delete(ITEMS_ENDPOINT + "?itemIds=" + itemIds)
                .andReturn();
    }
}