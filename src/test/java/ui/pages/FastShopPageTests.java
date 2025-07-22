package ui.pages;

import Danilova.PageObjects.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FastShopPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("Add random item to bag")
    void addItemToBag() throws InterruptedException {
        HomePage home = new HomePage(driver);

        //из России с 19 июля не работает
        if (home.dialogBox.isPresent()) {
            home.dialogBox.closeDialogBox();
        }
        //из России с 19 июля не работает
        if (home.offerBox.isPresent()) {
            home.offerBox.closeOfferBox();
        }

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

        if (home.rewardBox.isBannerDisplayed()) {
            home.rewardBox.closeBanner();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        dropPage.clickRandomColorResult();
        dropPage.clickRandomSizeResult();
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();

        AddedToBagSideBar bar = new AddedToBagSideBar(driver);

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
