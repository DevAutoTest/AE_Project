package ui.pages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ui.utils.CsvUtils;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuComponentTests extends PrepareDriverTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testWomenMenuLinksAndCategoriesText(String expectedCategoryCsv, String expectedUrlCsv) {
        new CloseAddBoxesTest().closeAdds(home);

        home.menu.hoverOverWomen();

        List<String> actualLinks = home.menu.getWomenMenuLinks();

        int uiLinksCount = actualLinks.size();
        int csvLinksCount = new CsvUtils().getCsvRowCount("/testData/MenuCategories.csv");

        assertTrue(actualLinks.contains(expectedUrlCsv),
                "Link '" + expectedUrlCsv + "' not found in menu. Accessible links: " + actualLinks);

        Assertions.assertEquals(csvLinksCount, uiLinksCount, "Check menu Categories and Links count!");

        List<String> actualCategory = home.menu.getWomenMenuTexts();
        assertTrue(actualCategory.contains(expectedCategoryCsv),
                "Category '" + expectedCategoryCsv + "' not found in menu. Accessible categories: " + actualCategory);
    }

    @ParameterizedTest
    @Disabled("Need to do")
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testOpenWomenMenuLinks(String expectedCategory, String expectedUrl) {

        List<String> actualLinks = home.menu.getWomenMenuLinks();

        for (String actualLink : actualLinks) {
            home.menu.clickWomenMenuLink(actualLink);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
            home.goBack();
        }
    }
}
