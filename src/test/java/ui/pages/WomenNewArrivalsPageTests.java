package ui.pages;

import Danilova.PageObjects.FastShopPage;
import Danilova.PageObjects.WomenNewArrivalsPage;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class WomenNewArrivalsPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("accessibility add item to bag")
    void clickRandomItem() throws InterruptedException {
        CloseAddBoxes.closeAdds(driver);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

        CloseAddBoxes.closeAdds(driver);

        Assertions.assertTrue(dropPage.addToBagButtonIsPresent());
    }
}
