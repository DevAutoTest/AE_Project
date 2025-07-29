package ui.pages;

import Danilova.PageObjects.FastShopPage;
import Danilova.PageObjects.WomenNewArrivalsPage;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WomenNewArrivalsPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("accessibility add item to bag")
    void clickRandomItem()  {
        CloseAddBoxes.closeAdds();
        home.fastMenu.openWomenMenu();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        if (home.promotion.specPromIsPresent()) {
            home.promotion.closeSpecialPromBox();
        }
        if (home.rewardBox.isRealRewardPresent()) {
            home.rewardBox.closeRewardBox();
        }
        if (home.signUpBox.signUpIsPresent()) {
            home.signUpBox.closeSignUpBox();
        }

        womenNewPage.chooseOneItem();

        if (home.promotion.specPromIsPresent()) {
            home.promotion.closeSpecialPromBox();
        }
        if (home.rewardBox.isRealRewardPresent()) {
            home.rewardBox.closeRewardBox();
        }
        if (home.signUpBox.signUpIsPresent()) {
            home.signUpBox.closeSignUpBox();
        }

        FastShopPage dropPage = new FastShopPage(driver);

        Assertions.assertTrue(dropPage.addToBagButtonIsPresent());
    }
}
