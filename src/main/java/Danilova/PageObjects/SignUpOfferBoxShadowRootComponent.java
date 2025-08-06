package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignUpOfferBoxShadowRootComponent {

    WebDriver driver;
    private final WebDriverWait wait;

    public By shadowHostSignUpBox = By.cssSelector(".bloomreach-weblayer");
    By closeBox = By.cssSelector(".close-button");

    public SignUpOfferBoxShadowRootComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Step("Does signUp box present?")
    public boolean signUpIsPresent() {
        try {
            final WebElement shadowHost = driver.findElement(shadowHostSignUpBox);
            final SearchContext shadowRoot = shadowHost.getShadowRoot();

            boolean isPresent = !shadowRoot
                    .findElements(By.cssSelector("div.modal-body.email-form.active"))
                    .isEmpty();

            if (isPresent) {
                return true;
            } else {
                System.out.println("This isn't SignIn box");
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("signUpBox doesn't present");
            return false;
        }
    }

    @Step("Close signUpBox box")
    public void closeSignUpBox() {
        try {
            try {
                final WebElement shadowHost = driver.findElement(shadowHostSignUpBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostSignUpBox));
            } catch (StaleElementReferenceException e) {

                final WebElement shadowHost = driver.findElement(shadowHostSignUpBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostSignUpBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("sign up box doesn't close: " + e.getMessage());
        }
    }
}
