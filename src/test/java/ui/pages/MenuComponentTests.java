package ui.pages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuComponentTests extends PrepareDriverTest {


    @ParameterizedTest
    @Disabled("takes much time")
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testWomenMenuLinksAndCategoriesText(String expectedCategory, String expectedUrl) {
        CloseAddBoxes.closeAdds();
        //important hoverOver to load menu woman elements!
        home.menu.hoverOverWomen();
        // 1. Получаем актуальные ссылки из меню
        List<String> actualLinks = home.menu.getWomenMenuLinks();

        // 2. Проверяем, что ожидаемая ссылка есть в меню
        assertTrue(actualLinks.contains(expectedUrl),
                "Ссылка '" + expectedUrl + "' не найдена в меню. Доступные ссылки: " + actualLinks);

        List<String> actualCategory = home.menu.getWomenMenuTexts();
        assertTrue(actualCategory.contains(expectedCategory),
                "Категория '" + expectedCategory + "' не найдена в меню. Доступные категории: " + actualCategory);

    }

    @ParameterizedTest
    @Disabled("Need to do")
    @CsvFileSource(resources = "/testData/MenuCategories.csv", numLinesToSkip = 0)
    public void testOpenWomenMenuLinks(String expectedCategory, String expectedUrl) {

        // 1. Получаем актуальные ссылки из меню
        List<String> actualLinks = home.menu.getWomenMenuLinks();

        for (String actualLink : actualLinks) {
            home.menu.clickWomenMenuLink(actualLink);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            Assertions.assertEquals(expectedUrl, driver.getCurrentUrl());
            home.goBack();
        }
    }
}
