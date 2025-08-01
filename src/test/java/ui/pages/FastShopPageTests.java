package ui.pages;

import Danilova.PageObjects.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FastShopPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("Add random item to bag")
    void addItemToBagWomenNewArrTest() {
        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);
        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();

        AddedToBagSideBar bar = new AddedToBagSideBar(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(bar.viewBagButton));

        String expectedText = AddedToBagSideBar.ADDED_SUCCESS;
        String currentText = bar.getText();

        Assertions.assertEquals(expectedText, currentText);

        bar.clickViewBag();

        wait.until(ExpectedConditions.urlContains("cart"));
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = ShoppingBagPage.CART_URL;
        Assertions.assertEquals(expectedUrl, currentUrl);
    }
}
