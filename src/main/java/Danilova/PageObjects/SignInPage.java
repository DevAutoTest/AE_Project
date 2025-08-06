package Danilova.PageObjects;

import Danilova.models.UserAcnt;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class SignInPage extends BasePage {
    By emailInput = By.xpath("//input[@placeholder='Email']");
    By passwordInput = By.xpath("//input[@placeholder='Password']");
    By signInButton = By.xpath("//button[@data-test-btn='submit']");
    By successTitle = By.xpath("//h2[@class ='modal-title']");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @Step("Sent user Email")
    public void sentEmail(String s) {
        driver.findElement(emailInput).sendKeys(s);
    }

    @Step("Sent user password")
    public void sentPassword(String s) {
        driver.findElement(passwordInput).sendKeys(s);
    }

    @Step("SClick sign in button")
    public void clickSignIn() {
        WebElement signButton = driver.findElement(signInButton);

        new Actions(driver)
                .moveToElement(signButton)
                .pause(Duration.ofMillis(6000))
                .click()
                .perform();
    }

    @Step("Fill registered user data {user}")
    public void fillSignInForm(UserAcnt user) {
        sentEmail(user.getEmail());
        sentPassword(user.getPassword());
    }

    @Step("Get signIn success page title")
    public String getTitle() {
        return driver.findElement(successTitle).getText();
    }
}
