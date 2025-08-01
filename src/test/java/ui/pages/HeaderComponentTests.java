package ui.pages;

import Danilova.PageObjects.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Header module tests")
public class HeaderComponentTests extends PrepareDriverTest {

    @Test
    @Description("Check AE logo")
    @Tag("smoke")
    void checkLogoAEHeaderTest() {
        new CloseAddBoxesTest().closeAdds(home);
        WebElement logo = driver.findElement(home.getHeader().getLogoAE_Header());

        assertNotNull(logo, "Logo \"American Eagle\" not found ");
        assertTrue(logo.isDisplayed(), "logo AE not displayed");
    }

    /*Добавить тесты для aerie logo*/

    @Test
    @Tag("smoke")
    @Description("Opening search icon is present in header component, clickable and closable")
    void openHdrSrchSideBar() {
        new CloseAddBoxesTest().closeAdds(home);
        driver.findElement(home.header().getHeaderSearchButton()).click();
        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        String actualTitle = searchSide.getSearchTitle();
        String expectedTitle = "Search";

        assertEquals(expectedTitle, actualTitle);

        searchSide.clickSerchClose();
        String actualHomeUrl = driver.getCurrentUrl();
        String expectedHomeUrl = HomePage.HOME_PAGE_URL;

        assertEquals(expectedHomeUrl, actualHomeUrl);
    }

    @Test
    @Tag("smoke")
    @Description("Open Account icon is present in header component, clickable and closable")
    void openHdrAcntBttnTest() {
        new CloseAddBoxesTest().closeAdds(home);

        AccountSideBarPage asbp = new AccountSideBarPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(home.header.getHeaderAccountButton()).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        String actualTitle = asbp.getTitleAcntSideBar();
        String expectedTitle = "Account";

        assertEquals(expectedTitle, actualTitle);

        asbp.clcCloseBttn();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        String actualHomeUrl = driver.getCurrentUrl();
        String expectedHomeUrl = HomePage.HOME_PAGE_URL;

        assertEquals(expectedHomeUrl, actualHomeUrl);
    }

    @Test
    @Tag("smoke")
    @Description("Open Cart icon is present in header component, clickable and go home page")
    void openHdrCartBttnTest() {

        new CloseAddBoxesTest().closeAdds(home);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        home.header().clckHdrCartBttn();
        wait.until(ExpectedConditions.urlContains("cart"));
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = ShoppingBagPage.CART_URL;

        assertEquals(expectedUrl, actualUrl);

        home.header.clickLogoAE_Header();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(ShoppingBagPage.CART_URL)));
        String actualHomeUrl = driver.getCurrentUrl();
        String expectedHomeUrl = HomePage.HOME_PAGE_URL;

        assertEquals(expectedHomeUrl, actualHomeUrl);
    }
}
