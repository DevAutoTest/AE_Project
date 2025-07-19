package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersAccountSideBarPage extends BasePage {
    public static final String ACCOUNT_MESSAGE = "Account";

    By onlineOrderHistory = By.xpath("//a[@href='/us/en/myaccount/order-history']");

    public UsersAccountSideBarPage(WebDriver driver) {
        super(driver);
    }

    @Step("click online order history link")
    public void clickOrderHistoryLink(){
        driver.findElement(onlineOrderHistory).click();
    }
}
