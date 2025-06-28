package api.tests;

import api.config.PropertiesConfig;
import api.tokens.GetTokenService;
import api.utils.*;
import api.dto.AddItemRequest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiGuestTests {
    //WebDriver driver;
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
       token = GetTokenService.getGuestToken();
        System.out.println("Received token: " + token);
        // Гость


        // Пользователь

    }


    @AfterEach
    void tearDown() {

    }

    @Test
    @Disabled("⛔ Временно отключен — в разработке")
    void getHomePage() {

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
                .body(body)
                .when()
                .post("/bag/v1/items") // замени на реальный endpoint
                .then()
                .extract().response();

        System.out.println("FULL RESPONSE:");
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

//    @ParameterizedTest
//    @CsvFileSource(resources = "/testData/addCartItems.csv", numLinesToSkip = 1)
//    void addItemsToCartFromCsv(String skuId, Integer quantity) throws Exception {
//        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
//        AddItemRequest requestBody = new AddItemRequest(List.of(item));
//
//        given()
//                .baseUri("https://www.ae.com/ugp-api")
//                .header("aesite", "AEO_US")
//                .header("Authorization", "Bearer " + GetToken.getToken())
//                .header("x-access-token", GetToken.getToken())
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/bag/v1/items")
//                .then()
//                .body("cartId", notNullValue());
//    }


    @Test
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
        System.out.println("FULL RESPONSE:");
        System.out.println(response.asPrettyString());
    }




    @Test
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
        System.out.println("FULL RESPONSE:");
        System.out.println(response.asPrettyString());
    }

    @Test
    @Disabled("⛔ Временно отключен — в разработке")
    void addSameItemCountInCart() throws IOException, InterruptedException {
        System.out.println(cart_id);
        String body = String.format("""
    {
      "items": [
        {
          "skuId": "%s",
          "quantity": 5,
          "itemId": "%s"
        }
      ]
    }
    """, sku_id, item_id);

        given()
                .baseUri("https://www.ae.com/ugp-api")
                .header("aesite", "AEO_US")
                .header("Authorization", "Bearer " + token)
                .header("x-access-token", token)
                .body(body)
                .contentType("application/json")
                .accept("application/json")
                .when()
                .patch("/bag/v1/items")
                .then()
                .body("cartId", equalToIgnoringCase(cart_id));
    }

}








