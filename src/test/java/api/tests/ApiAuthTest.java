package api.tests;

import api.config.PropertiesConfig;
import api.dto.AddItemRequest;
import api.tokens.GetTokenService;
import api.tokens.GetUser;
import api.utils.CsvReader;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class ApiAuthTest {
    PropertiesConfig config = new PropertiesConfig();
    public static final String BASE_URL = "https://www.ae.com/us/en";
    public String sku_id = "0043256122";
    static String token;
    public String item_id;
    public String cart_id;



    @BeforeAll
    static void setUp() throws IOException, InterruptedException {
        // driver = new ChromeDriver();
        RestAssured.defaultParser = Parser.JSON; // парсер
        //  driver.get(BASE_URL);
        token = GetUser.getToken();
        System.out.println("Received token: " + token);
       }


    @Test
   @Disabled("⛔ Временно отключен — в разработке")
    void addItemToCart() throws IOException, InterruptedException {
        String body = """
                {
                    "items": [
                        {
                            "skuId": "0043256122",
                            "quantity": 3
                        }
                    ]
                }
                """;
        Response response =
                given()
                        .baseUri("https://www.ae.com/ugp-api") // пример базового URL, замени на свой
                        .header("aesite", "AEO_US")
                        .header("Authorization", "Bearer " + token)
                        .header("x-access-token", token)
                        .contentType("application/json")
                        .header("origin", "https://www.ae.com")
                        .header("referer", "https://www.ae.com/us/en")
                        .body(body)
                        .when()
                        .post("/bag/v1/items") // замени на реальный endpoint
                        .then()
                        .extract().response();

        System.out.println("FULL RESPONSE ADD ITEM:");
        System.out.println(response.asPrettyString());
    }


    @Test
    @Disabled("⛔ Временно отключен — в разработке")
    void addOneItemToCart() throws IOException, InterruptedException {
        AddItemRequest.Item item = new AddItemRequest.Item("0043256122", 3);
        AddItemRequest requestBody = new AddItemRequest(List.of(item));
        given()
                .baseUri("https://www.ae.com/ugp-api") // пример базового URL, замени на свой
                .header("aesite", "AEO_US")
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/bag/v1/items") // замени на реальный endpoint
                .then()
                .statusCode(202)
                .body("cartId", notNullValue())
                .body("errors", hasSize(0)); // если в ответе есть такой флаг

    }


    @Test
    @Disabled("⛔ Временно отключен — в разработке")
    void addItemsToCartFromCsv() throws IOException, InterruptedException {
        List<AddItemRequest.Item> items = CsvReader.readItemsFromCsv("testData/addCartItems.csv");
        AddItemRequest requestBody = new AddItemRequest(items);
        System.out.println(requestBody);
        Response response =  given()
                .baseUri("https://www.ae.com/ugp-api")
                .header("aesite", "AEO_US")
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/bag/v1/items")
                .then()
                .body("cartId", notNullValue())
                .extract().response();
        System.out.println(cart_id);
        System.out.println("FULL RESPONSE ADD ITEMS TO CART:");
        System.out.println(response.asPrettyString());
    }


    @Test
   // @Disabled("⛔ Временно отключен — в разработке")
    void getCartInfo() throws IOException, InterruptedException {
        Response response = given()
                .baseUri("https://www.ae.com/ugp-api")
                .header("aesite", "AEO_US")
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/bag/v1?couponErrorBehavior=cart&inventoryCheck=true")
                .then()
                .extract().response();
        List<Map<String, Object>> items = response.jsonPath().getList("data.items");

        if (items == null || items.isEmpty()) {
            System.out.println("❗ Нет товаров в корзине (items пустой или отсутствует)");
        } else {
            for (Map<String, Object> item : items) {
                System.out.println("📦 Item: " + item);
                System.out.println("🔑 Keys: " + item.keySet());

                String sku_id = (String) item.get("sku");
                String item_id = (String) item.get("itemId");

                System.out.println("✅ SKU ID: " + sku_id);
                System.out.println("✅ ITEM ID: " + item_id);
            }

        }
        System.out.println("FULL RESPONSE CART INFO:");
        System.out.println(response.asPrettyString());
    }

    @Test
        // @Disabled("⛔ Временно отключен — в разработке")
    void getProfileInfo() throws IOException, InterruptedException {
        Response response = given()
                .baseUri("https://www.ae.com/ugp-api")
                .header("aesite", "AEO_US")
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .get("/users/v1")
                .then()
                .extract().response();
        List<Map<String, Object>> profileInfo = response.jsonPath().getList("data.profile");

        if (profileInfo == null || profileInfo.isEmpty()) {
            System.out.println("❗ Нет профиля");
        } else {
            for (Map<String, Object> login : profileInfo) {
                System.out.println("📦 login: " + login);
                System.out.println("🔑 Keys: " + login.keySet());

                System.out.println("✅ SKU ID: " + sku_id);
                System.out.println("✅ ITEM ID: " + item_id);
            }

        }
        System.out.println("FULL RESPONSE PROFILE:");
        System.out.println(response.asPrettyString());
    }
}
