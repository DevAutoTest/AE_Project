package ui.pages;

import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Search Side Bar module tests")
public class SearchSideBarPageTests extends PrepareDriverTest {
    @Test
    @Tag("smoke")
    @Description("Check placeholder text of search input")
    void checkInputHintText() {
      //  CloseAddBoxes.closeAdds();

        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        home.header().clckHdrSrchBttn();
        String expectedText = "Products, help topics, etc.";
        String actualText = searchSide.getSearchText();
        assertEquals(expectedText, actualText);
    }

    @Test
    @Tag("smoke")
    @Description("Get search input")
    void enterForSearch() {
        new CloseAddBoxesTest().closeAdds(home);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        SearchSideBarPage searchSide = new SearchSideBarPage(driver);
        home.header().clckHdrSrchBttn();

        searchSide.enterSearchInput("women top");
        searchSide.clickSerchButton();
        String expectedURL = SearchSideBarPage.SEARCH_RESULT_URL;
        wait.until(ExpectedConditions.urlContains(expectedURL));

        String actualURL = searchSide.getCurrentUrl();
        org.assertj.core.api.Assertions.assertThat(actualURL).contains(expectedURL);
    }
}
