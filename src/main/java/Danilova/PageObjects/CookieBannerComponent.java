package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CookieBannerComponent {
    private final WebDriver driver;
    private final WebDriverWait wait;

    By cookieBanner = By.xpath("//div[@role='dialog' and @aria-label='Clarip Cookie Consent Banner' ]");
    By closeCookieButton = By.xpath("//button[@aria-label='dismiss cookie message']");

    public CookieBannerComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    @Step("Does cookie banner present?")
    public boolean cookieBannerIsPresent() {
        try {
            return driver.findElement(cookieBanner).isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("special prom box doesn't present");
            return false;
        }
    }

    @Step("Close cookie banner")
    public void closeCookieBanner() {
        try {
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(closeCookieButton));
            } catch (StaleElementReferenceException e) {
                driver.findElement(closeCookieButton).click();
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("cookie banner doesn't close: " + e.getMessage());
        }
    }
}
