package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DialogBoxComponent {
    final WebDriver driver;
    private final WebDriverWait wait;
    By dialogBox = By.xpath("//div[@role='dialog' and @data-test-flyout='featured-offers']");
    By closeDialogBox = By.xpath("//button[@data-test-btn='close']");

    public DialogBoxComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @Step("Does Dialog box present?")
    public boolean isPresent() {
        try {
            driver.findElement(dialogBox);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Clos dialog box")
    public void closeDialogBox() {
        try {
            driver.findElement(closeDialogBox).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dialogBox));
        } catch (NoSuchElementException e) {
            System.out.println("bonus offer doesn't present");
        }
    }


}
