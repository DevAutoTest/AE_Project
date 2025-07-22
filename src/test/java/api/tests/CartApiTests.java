package api.tests;

import api.controllers.bag.CartController;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CartApiTests {
    private final CartController cart = new CartController();

    @Test
    @Tag("critical")
    void addMinImpossibleItemsTest() {
        Response rsp = cart.addItem("0043256122", 11)
                .then()
                .statusCode(422)
                .extract().response();
        String expTextErr = "Item Quantity Exceeds Max Allowable Item Quantity";
        String actualRslt = rsp.jsonPath()
                .getString("errors[0].message");
        assertThat(actualRslt).isEqualTo(expTextErr);

    }
}
