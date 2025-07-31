package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpecialPromotionBoxShadowRootComponent {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public By shadowHostSpecialPromBox = By.cssSelector(".bloomreach-weblayer");
    By closeBox = By.cssSelector(".close");

    public SpecialPromotionBoxShadowRootComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Step("Does special prom box present?")
    public boolean specPromIsPresent() {
        try {
            final WebElement shadowHost = driver.findElement(shadowHostSpecialPromBox);
            final SearchContext shadowRoot = shadowHost.getShadowRoot();
            shadowRoot.findElement(shadowHostSpecialPromBox);
            System.out.println("special prom box presents");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("special prom box doesn't present");
            return false;
        }
    }

    @Step("Close special promotion box")
    public void closeSpecialPromBox() {
        try {
            try {
                final WebElement shadowHost = driver.findElement(shadowHostSpecialPromBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostSpecialPromBox));
            } catch (StaleElementReferenceException e) {
                // Если элемент "устарел", пробуем ещё раз
                final WebElement shadowHost = driver.findElement(shadowHostSpecialPromBox);
                final SearchContext shadowRoot = shadowHost.getShadowRoot();
                shadowRoot.findElement(closeBox).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shadowHostSpecialPromBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("special promotion box doesn't close: " + e.getMessage());
        }
    }
}
