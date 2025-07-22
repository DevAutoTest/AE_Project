package api.tests;

import api.controllers.bag.CartController;
import api.dto.CartResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// JUnit создаст один экземпляр класса и не будет его перезатирать в каждом тесте
@ExtendWith(api.extensions.BeforeAll.class)
class CartApiSuitSmokeTests {

    private final CartController cart = new CartController();
    private final String TEST_SKU_ID = "0043519081";
    static String cartId;
    CartResponse cartResponse;
    String itemId;

    @Order(1)
    @Test
    @Tag("smoke")
    void getCartInfoTest() {
        cartResponse = cart.getCart();
        System.out.println("first info: " + cartResponse);
        cartId = cartResponse.getData().id;
        System.out.println("cartId" + cartId);
        assertThat(cartId).isNotNull();
    }

    @Order(2)
    @Test
    @Tag("smoke")
    void addItemTest() {
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
        if (cartId == null) {
            System.out.println("cartId null");
        }
        assertThat(cartResponse.getData().items.get(0).getSku()).isEqualTo(TEST_SKU_ID);
        System.out.println("after add info: " + cartResponse);
        System.out.println("sku = " + cartResponse.getData().items.get(0).getSku());
        assertThat(cartResponse.getData().id).isEqualTo(cartId);
        System.out.println("ItemId is " + cartResponse.getData().items.get(0).getItemId());
    }


    @Order(3)
    @Test
    @Tag("smoke")
    void updateItemTest() {
        cartResponse = cart.getCart();
        itemId = cartResponse.getData().items.get(0).getItemId();
        int newQty = 3;
        cart.updateItem(TEST_SKU_ID, newQty, itemId);

        assertThat(cart.getCart().getData().items.get(0).getSku()).isEqualTo(TEST_SKU_ID);
        assertThat(cart.getCart().getData().items.get(0).getQuantity()).isEqualTo(newQty);
    }


    @Order(4)
    @Test
    @Tag("smoke")
    void deleteItemTest() {
        cartResponse = cart.getCart();
        itemId = cartResponse.getData().items.get(0).getItemId();
        System.out.println("itemId is: " + cartResponse.getData().items.get(0).getItemId());
        cart.deleteItem(itemId);

        assertThat(cart.getCart().getData().getItemCount()).isZero();
    }

}








