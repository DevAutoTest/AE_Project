package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BonusOfferComponent {
    final WebDriver driver;
    private final WebDriverWait wait;

//    By offerBox = By.cssSelector(".text-center ");
//    By closeOffer = By.cssSelector(".close-button");

    By offerBox = By.xpath("//div[@class='text-center '][.//button[@class='close-button']]");
    By closeOffer = By.cssSelector(".close-button");

    public BonusOfferComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Step("Does bonus offer box present?")
    public boolean isPresent() {
        try {
            WebElement button = driver.findElement(offerBox);
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
