package ui.pages;

import Danilova.PageObjects.HeaderComponent;
import Danilova.PageObjects.HomePage;
import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Search Side Bar module tests")
public class SearchSideBarPageTests extends PrepareDriverTest {
    @Test
    @Tag("smoke")
    @Description("Check placeholder text of search input")
    void CheckInputHintText(){
        HomePage home = new HomePage(driver);
        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        home.header().clckHdrSrchBttn();
        String expectedText = "Products, help topics, etc.";
        String actualText = searchSide.getSearchText();
        assertEquals(expectedText, actualText);
    }
}
