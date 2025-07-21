package api.tests;

import api.controllers.bag.CartController;
import api.dto.CartResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)   // JUnit создаст один экземпляр класса и не будет его перезатирать в каждом тесте
@ExtendWith(api.extensions.BeforeAll.class)
class CartApiSuitSmokeTests {

    private final CartController cart = new CartController();
    private final String TEST_SKU_ID = "0043519081";
    static String cartId;
    CartResponse cartResponse;
    String itemId;

    @Disabled("has a problem")
    @Order(1)
    @Test
    @Tag("smoke")
    void getCartInfoTest(){
        cartResponse = cart.getCart();
        System.out.println("first info: " + cartResponse);
        cartId = cartResponse.getData().id;
        System.out.println("cartId" + cartId);
        assertThat(cartId).isNotNull();
    }

    @Disabled("has a problem")
    @Order(2)
    @Test
    @Tag("smoke")
    void addItemTest()  {
        int qty = 1;
        cartResponse = cart.getCart();
        int cartItemQnt = cartResponse.getData().getItemCount();

        assertThat(cartItemQnt)
                .isZero();

        cart.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);


        cartResponse = cart.getCart();
        assertThat(cartResponse.getData().getItemCount())
                .isEqualTo(qty);
        if(cartId==null){
            System.out.println("cartId null");
        }
        assertThat(cartResponse.getData().items.get(0).getSku()).isEqualTo(TEST_SKU_ID);
        System.out.println("after add info: " + cartResponse);
        System.out.println("sku = " + cartResponse.getData().items.get(0).getSku());
        assertThat(cartResponse.getData().id).isEqualTo(cartId);
        System.out.println("ItemId is " + cartResponse.getData().items.get(0).getItemId());
    }

    @Disabled("has a problem")
    @Order(3)
    @Test
    @Tag("smoke")
    void updateItemTest(){
        cartResponse = cart.getCart();
        itemId=cartResponse.getData().items.get(0).getItemId();
        int newQty = 3;
        cart.updateItem(TEST_SKU_ID, newQty, itemId);

        assertThat(cart.getCart().getData().items.get(0).getSku()).isEqualTo(TEST_SKU_ID);
        assertThat(cart.getCart().getData().items.get(0).getQuantity()).isEqualTo(newQty);
    }

    @Disabled("has a problem")
    @Order(4)
    @Test
    @Tag("smoke")
    void deleteItemTest(){
        cartResponse = cart.getCart();
        itemId=cartResponse.getData().items.get(0).getItemId();
        System.out.println("itemId is: " + cartResponse.getData().items.get(0).getItemId());
        cart.deleteItem(itemId);

        assertThat(cart.getCart().getData().getItemCount()).isZero();
    }










//    @Test
//  @Disabled("⛔ Временно отключен — в разработке")
//    void addItemsToCartFromCsv() throws IOException, InterruptedException {
//        List<AddItemRequest.Item> items = CsvReader.readItemsFromCsv("testData/addCartItems.csv");
//        AddItemRequest requestBody = new AddItemRequest(items);
//        System.out.println(requestBody);
//       Response response =  given()
//                .baseUri("https://www.ae.com/ugp-api")
//                .header("aesite", "AEO_US")
//                .header("Authorization", "Bearer " + x_access_token)
//                .header("x-access-token", x_access_token)
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/bag/v1/items")
//                .then()
//                .body("cartId", notNullValue())
//                .extract().response();
//        System.out.println(cart_id);
//        System.out.println("FULL RESPONSE:");
//        System.out.println(response.asPrettyString());
//    }




//    @Test
//    void getCartInfo() throws IOException, InterruptedException {
//                Response response = given()
//                        .baseUri("https://www.ae.com/ugp-api")
//                        .header("aesite", "AEO_US")
//                        .header("Authorization", "Bearer " + x_access_token)
//                        .header("x-access-token", x_access_token)
//                        .contentType("application/json")
//                        .accept("application/json")
//                        .when()
//                        .get("/bag/v1?couponErrorBehavior=cart&inventoryCheck=true")
//                        .then()
//                        .extract().response();
//        List<Map<String, Object>> items = response.jsonPath().getList("data.items");
//
//        if (items == null || items.isEmpty()) {
//            System.out.println("❗ Нет товаров в корзине (items пустой или отсутствует)");
//        } else {
//            for (Map<String, Object> item : items) {
//                System.out.println("📦 Item: " + item);
//                System.out.println("🔑 Keys: " + item.keySet());
//
//                String sku_id = (String) item.get("sku");
//                String item_id = (String) item.get("itemId");
//
//                System.out.println("✅ SKU ID: " + sku_id);
//                System.out.println("✅ ITEM ID: " + item_id);
//            }
//
//        }
//        System.out.println("FULL RESPONSE:");
//        System.out.println(response.asPrettyString());
//    }
//
//    @Test
//    @Disabled("⛔ Временно отключен — в разработке")
//    void addSameItemCountInCart() throws IOException, InterruptedException {
//        System.out.println(cart_id);
//        String body = String.format("""
//    {
//      "items": [
//        {
//          "skuId": "%s",
//          "quantity": 5,
//          "itemId": "%s"
//        }
//      ]
//    }
//    """, TEST_SKU_ID, item_id);
//
//        given()
//                .baseUri("https://www.ae.com/ugp-api")
//                .header("aesite", "AEO_US")
//                .header("Authorization", "Bearer " + x_access_token)
//                .header("x-access-token", x_access_token)
//                .body(body)
//                .contentType("application/json")
//                .accept("application/json")
//                .when()
//                .patch("/bag/v1/items")
//                .then()
//                .body("cartId", equalToIgnoringCase(cart_id));
//    }

}








