package ui.pages;

import Danilova.PageObjects.*;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import Danilova.models.UsersFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Create account module tests")
public class CreateAccountPageTests extends PrepareDriverTest {

    @Test
    @Tag("critical")
    @Disabled("Antibot Akamai is working")
    void createAccountSuccessTest() {
        new CloseAdsBoxesTests().closeAdsTest(home);

        home.header().clckHdrAcntBttn();
        AccountSideBarPage asbp = new AccountSideBarPage(driver);
        asbp.openCrtAcntPage();

        CreateAccountPage page = new CreateAccountPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        new CloseAdsBoxesTests().closeAdsTest(home);

        wait.until(ExpectedConditions.elementToBeClickable(page.getEmailInput()));

        page.fillForm(UsersFactory.VALID_USER);

        page.clkAcceptCheckBox();

        page.submitForm();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        assertTrue(page.isAccountSuccsPage());
        assertTrue(page.isSucMessage());
    }
}

