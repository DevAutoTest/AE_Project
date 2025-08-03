package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Getting current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Go back button")
    public void goBack() {
        driver.navigate().back();
    }
}
