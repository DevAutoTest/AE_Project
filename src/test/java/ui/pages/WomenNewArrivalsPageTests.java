package ui.pages;

import Danilova.PageObjects.FastShopPage;
import Danilova.PageObjects.HomePage;

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

        Assertions.assertTrue(dropPage.addToBagButtonIsPresent());
    }
}
