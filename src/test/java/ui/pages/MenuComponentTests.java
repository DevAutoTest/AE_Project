package ui.pages;

import Danilova.PageObjects.HomePage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ui.utils.CsvUtils;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuComponentTests extends PrepareDriverTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/testData/MenuCategories.csv")

    public void womenMenuLinksAndCategoriesTextTest(String expectedCategoryCsv, String expectedUrlCsv) {
        new CloseAdsBoxesTests().closeAdsTest(home);

        home.menu.hoverOverWomen();

        List<String> actualLinks = home.menu.getWomenMenuLinks();

        int uiLinksCount = actualLinks.size();
        int csvLinksCount = new CsvUtils().getCsvRowCount("/testData/MenuCategories.csv");

        assertTrue(actualLinks.contains(expectedUrlCsv),
                "Link '" + expectedUrlCsv + "' not found in menu. Accessible links: " + actualLinks);

        assertEquals(csvLinksCount, uiLinksCount, "Check menu Categories and Links count!");

        List<String> actualCategory = home.menu.getWomenMenuTexts();
        assertTrue(actualCategory.contains(expectedCategoryCsv),
                "Category '" + expectedCategoryCsv + "' not found in menu. Accessible categories: " + actualCategory);
    }

    @ParameterizedTest
   // @Disabled("Need to do")
    @Tag("Smoke")
    @Description("menu links are clickable")
    @MethodSource("ui.utils.CsvUtils#readCategoryUrlPairsFromCsv")
    public void openWomenMenuLinksTest(String expectedCategoryCsv, String expectedUrlCsv) {
        new CloseAdsBoxesTests().closeAdsTest(home);
        home.menu.hoverOverWomen();

        List<String> actualLinks = home.menu.getWomenMenuTexts();

        if (!actualLinks.contains(expectedCategoryCsv)) {
            throw new AssertionError("Category not found: " + expectedCategoryCsv + "\nAvailable: " + actualLinks);
        }

        home.menu.clickWomenMenuLinks(expectedUrlCsv);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        String currentUrl = driver.getCurrentUrl();
        assertEquals(expectedUrlCsv, currentUrl, "URL mismatch for category: " + expectedCategoryCsv);

        driver.navigate().back();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals(HomePage.HOME_PAGE_URL, driver.getCurrentUrl(), "This is not Home Page!");
    }

}
