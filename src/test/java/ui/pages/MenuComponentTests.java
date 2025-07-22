package ui.pages;

import Danilova.PageObjects.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuComponentTests extends ParametrizedTestBase {

    private HomePage homePage;

    @BeforeEach
    public void prepareTest() {
        homePage = new HomePage(driver);
        //important hoverOver to load elements!
        homePage.menu.hoverOverWomen();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testWomenMenuLinksAndCategoriesText(String expectedCategory, String expectedUrl)  {

        // 1. Получаем актуальные ссылки из меню
        List<String> actualLinks = homePage.menu.getWomenMenuLinks();

        // 2. Проверяем, что ожидаемая ссылка есть в меню
        assertTrue(actualLinks.contains(expectedUrl),
                "Ссылка '" + expectedUrl + "' не найдена в меню. Доступные ссылки: " + actualLinks);

        List<String> actualCategory = homePage.menu.getWomenMenuTexts();
        assertTrue(actualCategory.contains(expectedCategory),
                "Категория '" + expectedCategory + "' не найдена в меню. Доступные категории: " + actualCategory);

    }

    @ParameterizedTest
    @Disabled("Need to do")
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testOpenWomenMenuLinks(String expectedCategory, String expectedUrl)  {

        // 1. Получаем актуальные ссылки из меню
        List<String> actualLinks = homePage.menu.getWomenMenuLinks();

        for (String actualLink : actualLinks) {
            homePage.menu.clickWomenMenuLink(actualLink);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
            homePage.goBack();
        }
    }
}
