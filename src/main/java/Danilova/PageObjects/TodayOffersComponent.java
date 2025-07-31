package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TodayOffersComponent {

    WebDriver driver;
    private final WebDriverWait wait;

    public TodayOffersComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(1));
    }

    By todayBox = By.xpath("//div[@class='container-fluid _container-inner_uptugp']");
    By closeBox = By.xpath("//button[@class='btn-close qa-btn-cancel _btn_1n5a75 _btn-close_uptugp']");

    @Step("Does todayBox present?")
    public boolean todayBoxIsPresent() {

        try {
            driver.findElement(todayBox);
            System.out.println("todayBox presents");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("todayBox doesn't present");
            return false;
        }
    }

    @Step("Close todayBox box")
    public void closeTodayBox() {
        try {
            try {
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeBox));
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(todayBox));
            } catch (StaleElementReferenceException e) {
                // Если элемент "устарел", пробуем ещё раз
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeBox));
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(todayBox));
            }
        } catch (NoSuchElementException | TimeoutException e) {
            System.out.println("today offer doesn't close: " + e.getMessage());
        }
    }
}


