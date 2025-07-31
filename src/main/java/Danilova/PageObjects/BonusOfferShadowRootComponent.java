package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Вы не можете использовать XPath внутри shadowRoot, только CSS-селекторы.
 */

public class BonusOfferShadowRootComponent {
    final WebDriver driver;
    private final WebDriverWait wait;
//    By bonusOfferShadowRootBox = By.cssSelector("div.bloomreach-weblayer");
//    By bonusOfferBox = By.cssSelector("div.weblayer--box-promotion-1.vertical-center.horizontal-center.enter-fade");
//    By closeBonusOfferBox = By.cssSelector("div.bloomreach-weblayer button[aria-label=\"Close\"]");


    By offerBox = By.cssSelector(".text-center ");
    By closeOffer = By.cssSelector(".close-button");

    public BonusOfferShadowRootComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Step("Does bonus offer box present?")
    public boolean isPresent() {
        try {
            // First check if the host element exists
            WebElement shadowHost = driver.findElement(offerBox);

            // Try to find the close button directly (no shadow root)
            // Adjust this based on your actual HTML structure
            driver.findElement(closeOffer);
            System.out.println("bonus offer box presents");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("bonus offer doesn't present");
            return false;
        }

    }

    @Step("Close bonus offer box")
    public void closeOfferBox() {
        try {
            try {
                WebElement closeButton = driver.findElement(closeOffer);
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(offerBox));
            } catch (StaleElementReferenceException e) {
                // Если элемент "устарел", пробуем ещё раз
                WebElement closeButton = driver.findElement(closeOffer);
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(offerBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("bonus offer doesn't close or wasn't present: " + e.getMessage());
        }
    }
}
