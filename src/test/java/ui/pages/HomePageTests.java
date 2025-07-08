package ui.pages;

import Danilova.PageObjects.HomePage;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** В тестах оставляем только клики и ассерты **/
@Feature("Home Page Tests")
public class HomePageTests extends PrepareDriverTest {
    static final String HOME_PAGE_URL = "https://www.ae.com/us/en";

    @Test
    void openHomePageTest() {
        HomePage homePage = new HomePage(driver);
        String currentUrl = homePage.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, currentUrl);
    }



}
