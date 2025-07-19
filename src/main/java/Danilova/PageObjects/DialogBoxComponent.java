package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class DialogBoxComponent {
    final WebDriver driver;
    By dialogBox = By.xpath("//div[@role='dialog' and @data-test-flyout='featured-offers']");
    By closeDialogBox = By.xpath("//button[@data-test-btn='close']");

    public DialogBoxComponent(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Does Dialog box present?")
    public boolean isPresent(){
        try {
            driver.findElement(dialogBox);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
     //  return driver.findElement(dialogBox).isDisplayed();
    }

    @Step("Clos dialog box")
    public void closeDialogBox(){
        driver.findElement(closeDialogBox).click();
    }


}
