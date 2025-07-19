package ui.pages;

import Danilova.PageObjects.AccountSideBarPage;
import Danilova.PageObjects.HomePage;
import Danilova.PageObjects.SignInPage;
import Danilova.PageObjects.UsersAccountSideBarPage;
import Danilova.models.UsersFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SignInPageTests  extends PrepareDriverTest{

    @Test
    @Tag("critical")
    @Disabled("antibot Akamai is working")
    void signInTest() {
        HomePage home = new HomePage(driver);
        home.header().clckHdrAcntBttn();
        AccountSideBarPage asbp = new AccountSideBarPage(driver);
        asbp.openSignInPage();
        SignInPage page = new SignInPage(driver);

        page.fillSignInForm(UsersFactory.CREATED_WITHOUT_ORDERS_USER);
        page.clickSignIn();

        String actualTitle = page.getTitle();
        String expectedTitle = UsersAccountSideBarPage.ACCOUNT_MESSAGE;
        Assertions.assertEquals(expectedTitle, actualTitle);
    }



}
