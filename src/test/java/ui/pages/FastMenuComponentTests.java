package ui.pages;

import Danilova.PageObjects.WomenNewArrivalsPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Danilova.PageObjects.WomenNewArrivalsPage.PAGE_TITLE;
import static Danilova.PageObjects.WomenNewArrivalsPage.WOMEN_NEW_ARRIVALS_URL;

public class FastMenuComponentTests extends PrepareDriverTest {


    @Test
    @Description("Open women new arrivals page")
    void openFastWomenNewMenuTest()  {

        CloseAddBoxes.closeAdds();

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs(PAGE_TITLE));
        String actualResult = womenNewPage.getCurrentUrl();

        Assertions.assertEquals(WOMEN_NEW_ARRIVALS_URL, actualResult);
    }
}
