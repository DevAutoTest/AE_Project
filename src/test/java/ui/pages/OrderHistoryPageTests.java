package ui.pages;

import Danilova.PageObjects.*;
import Danilova.models.UsersFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class OrderHistoryPageTests extends PrepareDriverTest {

    @Test
    @Tag("critical")
    @Disabled("antibot Akamai is working")
    void checkOrdersOfNewUserTest() {
        CloseAddBoxes.closeAdds();

        home.header().clckHdrAcntBttn();
        AccountSideBarPage asbp = new AccountSideBarPage(driver);
        asbp.openSignInPage();
        SignInPage page = new SignInPage(driver);

        page.fillSignInForm(UsersFactory.CREATED_WITHOUT_ORDERS_USER);
        page.clickSignIn();


        UsersAccountSideBarPage sideBar = new UsersAccountSideBarPage(driver);
        sideBar.clickOrderHistoryLink();

        OrderHistoryPage historyPage = new OrderHistoryPage(driver);


        String expectedCount = "0";
        String actualCount = historyPage.getCountByYear("2025").substring(0, 0);

        Assertions.assertEquals(expectedCount, actualCount);
    }
}
