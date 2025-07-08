package ui.pages;

import Danilova.PageObjects.HeaderPage;
import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Search Side Bar module tests")
public class SearchSideBarPageTests extends PrepareDriverTest {
    @Test
    @Description("Check text hint search input")
    void CheckInputHintText(){
        HeaderPage headerPage = new HeaderPage(driver);
        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        headerPage.clckSrchHdrBttn();
        String expectedText = "Products, help topics, etc.";
        String actualText = searchSide.getSearchText();
        assertEquals(expectedText, actualText);
    }


}
