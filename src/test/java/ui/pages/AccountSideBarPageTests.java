package ui.pages;

import Danilova.PageObjects.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Danilova.PageObjects.CreateAccountPage.CREATE_ACCOUNT_PAGE_TITLE;

@Feature("Account side bar module tests")
public class AccountSideBarPageTests extends PrepareDriverTest {

    @Test
    @Description("Open Create Account page")
    @Tag("smoke")
    void openCreateAccountPageTest() {
        CloseAddBoxes.closeAdds();

        home.header().clckHdrAcntBttn();
        AccountSideBarPage asb = new AccountSideBarPage(driver);
        asb.openCrtAcntPage();

        CreateAccountPage crAcPg = new CreateAccountPage(driver);
        crAcPg.isCreateAccountPage(driver.getCurrentUrl());

        String actualTitle = crAcPg.createAcntPgTitle();

        assertEquals(CREATE_ACCOUNT_PAGE_TITLE, actualTitle);
    }

    @Test
    @Description("Open SignIn page")
    @Tag("smoke")
    void openSignInPageTest() {
        CloseAddBoxes.closeAdds();
        home.header().clckHdrAcntBttn();
        AccountSideBarPage asb = new AccountSideBarPage(driver);
        asb.openSignInPage();
    }

    @Test
    @Disabled("Akamai bot")
    void signOutTest() {
    }
}
