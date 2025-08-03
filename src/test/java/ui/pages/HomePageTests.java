package ui.pages;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Home Page Tests")
public class HomePageTests extends PrepareDriverTest {
    static final String HOME_PAGE_URL = "https://www.ae.com/us/en";

    @Test
    @Description("Open home page")
    @Tag("smoke")
    void openHomePageTest() {
        new CloseAddBoxesTest().closeAdds(home);
        String currentUrl = home.getCurrentUrl();
        assertEquals(HOME_PAGE_URL, currentUrl);
    }
}
