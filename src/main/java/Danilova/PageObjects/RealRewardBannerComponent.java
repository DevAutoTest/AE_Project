package Danilova.PageObjects;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RealRewardBannerComponent {

    WebDriver driver;
    private final WebDriverWait wait;

    public By shadowHostSignUpBox = By.cssSelector(".bloomreach-weblayer");
    By closeBox = By.cssSelector(".close-button");


    public RealRewardBannerComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }
    @Step("Does reward box present?")
    public boolean isRealRewardPresent() {
        try {
            final WebElement shadowHost = driver.findElement(shadowHostSignUpBox);
            final SearchContext shadowRoot = shadowHost.getShadowRoot();
            shadowRoot.findElement(shadowHostSignUpBox);
            System.out.println("reward box presents");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("reward box doesn't present");
            return false;
        }
    }

    @Step("Close reward box")
    public void closeRewardBox() {
        try {
            final WebElement shadowHost = driver.findElement(shadowHostSignUpBox);
            final SearchContext shadowRoot = shadowHost.getShadowRoot();
            shadowRoot.findElement(closeBox).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostSignUpBox));
        } catch (NoSuchElementException e) {
            System.out.println("reward box doesn't close");
        }
    }
}
