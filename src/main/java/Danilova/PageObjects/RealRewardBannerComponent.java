package Danilova.PageObjects;


import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RealRewardBannerComponent {

    WebDriver driver;
    private final WebDriverWait wait;

    public By shadowHostRewardBox = By.cssSelector(".bloomreach-weblayer");
    By closeBox = By.cssSelector(".close");


    public RealRewardBannerComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Step("Does reward box present?")
    public boolean isRealRewardPresent() {
        try {
            final WebElement shadowHost = driver.findElement(shadowHostRewardBox);
            final SearchContext shadowRoot = shadowHost.getShadowRoot();
            shadowRoot.findElement(shadowHostRewardBox);
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
            try {
                final WebElement shadowHost = driver.findElement(shadowHostRewardBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostRewardBox));
            } catch (StaleElementReferenceException e) {
                // Если элемент "устарел", пробуем ещё раз
                final WebElement shadowHost = driver.findElement(shadowHostRewardBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostRewardBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("reward box doesn't close: " + e.getMessage());
        }
    }
}
