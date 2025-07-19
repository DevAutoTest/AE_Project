package ui.pages;

import Danilova.PageObjects.AccountSideBarPage;
import Danilova.PageObjects.HomePage;
import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Header module tests")
public class HeaderComponentTests extends PrepareDriverTest  {
    @Test
    @Description("Check logo hint")
    @Tag("smoke")
    void checkLogoAEHeaderTest() {
        HomePage home = new HomePage(driver);

        WebElement logo = home.getHeader().getLogoAE_Header();
        String hint = driver.findElement(home.getHeader().getTextHintLogo()).getAttribute("title");
        String expectedHint = "American Eagle Outfitters Men's & Women's Clothing, Shoes & Accessories";

        assertNotNull(logo, "Logo \"American Eagle\" not found ");
        assertTrue(logo.isDisplayed(), "logo AE not displayed");
        assertTrue(home.getHeader().isEqualDAE_Header(logo.getAttribute("d")));
        assertEquals(expectedHint, hint);
    }

    /*Добавить тесты для aerie logo*/


    @Test
    @Tag("smoke")
    @Description("Check search icon is present in header component")
    void SearchHeaderButtonIsExist(){
        HomePage home = new HomePage(driver);

        By search = home.getHeader().getSearch();

        assertNotNull(search);
    }

    @Test
    @Tag("smoke")
    @Description("Opening search icon")
    void openHdrSrchSideBar(){
        HomePage home = new HomePage(driver);

        driver.findElement(home.header().getSearch()).click();
        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        String actualTitle = searchSide.getSearchTitle();
        String expectedTitle = "Search";

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    @Tag("smoke")
    @Description("Open Account icon is present in header component")
    void openHdrAcntBttnTest(){
        HomePage home = new HomePage(driver);
        AccountSideBarPage asbp = new AccountSideBarPage(driver);

        driver.findElement(home.header().getAcntBttn()).click();
        String actualTitle = asbp.getTitleAcntSideBar();
        String expectedTitle = "Account";

        assertEquals(expectedTitle, actualTitle);
    }

    //Добавить фаворитов

    //Добавить иконку корзинки

}
