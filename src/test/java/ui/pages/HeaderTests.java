package ui.pages;

import Danilova.PageObjects.HeaderPage;
import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Header module tests")
public class HeaderTests extends PrepareDriverTest {
    @Test
    void checkLogoAEHeaderTest() {
        HeaderPage headerPage = new HeaderPage(driver);

        WebElement logo = headerPage.getlogoAE_Header();
        String hint = driver.findElement(headerPage.getTextHintLogo()).getAttribute("title");
        String expectedHint = "American Eagle Outfitters Men's & Women's Clothing, Shoes & Accessories";

        assertNotNull(logo, "Logo \"American Eagle\" not found ");
        assertTrue(logo.isDisplayed(), "logo AE not displayed");
        assertTrue(headerPage.isEqualDAE_Header(logo.getAttribute("d")));
        assertEquals(expectedHint, hint);
    }

    /*Добавить тесты для aerie logo*/


    @Test
    void SearchHeaderButtonIsExist(){
        HeaderPage headerPage = new HeaderPage(driver);
        By search = headerPage.getSearch();

        assertNotNull(search);
    }

    @Test
    void openSearchSideBar(){
        HeaderPage headerPage = new HeaderPage(driver);

        WebElement bttn = driver.findElement(headerPage.getSearch());
        assertTrue(bttn.isDisplayed());
        headerPage.clckSrchHdrBttn();

       SearchSideBarPage searchSide = new SearchSideBarPage(driver);

        String searchSideTitle = searchSide.getSearchTitle();
        String expectedTitle = "Search";

        assertEquals(expectedTitle, searchSideTitle);
    }

}
