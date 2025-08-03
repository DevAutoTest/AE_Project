package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShippingToBoxComponent {

    WebDriver driver;
    private final WebDriverWait wait;
    By shippingBox = By.xpath("//div[@role='dialog' and @data-test-flyout='onboarding']");
    By closeBox = By.xpath("//button[@data-test-btn='close' and @name='close' and @data-track-event='click']");

    public ShippingToBoxComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    }

    @Step("Does shipping box present?")
    public boolean shippingBoxIsPresent() {
        try {
            driver.findElement(shippingBox);
            System.out.println("shippingBox presents");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("shippingBox doesn't present");
            return false;
        }
    }

    @Step("Close shippingBox box")
    public void closeShippingBox() {
        try {
            try {
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeBox));
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shippingBox));
            } catch (StaleElementReferenceException e) {

                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeBox));
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(shippingBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("today offer doesn't close: " + e.getMessage());
        }
    }
}
