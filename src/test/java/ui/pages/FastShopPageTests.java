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
    void addItemToBagWomenNewArrTest() throws InterruptedException {
        CloseAddBoxes.closeAdds(driver);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        if(home.signUpBox.signUpIsPresent()){
            home.signUpBox.closeSignUpBox();
        }

        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

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
