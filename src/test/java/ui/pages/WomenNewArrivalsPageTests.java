package ui.pages;

import Danilova.PageObjects.FastShopPage;
import Danilova.PageObjects.WomenNewArrivalsPage;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WomenNewArrivalsPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("accessibility add item to bag")
    void clickRandomItem() {
        new CloseAddBoxesTest().closeAdds(home);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        new CloseAddBoxesTest().closeAdds(home);

        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        Assertions.assertTrue(dropPage.addToBagButtonIsPresent());
    }
}
