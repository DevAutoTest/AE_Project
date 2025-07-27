package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
    public boolean shippingBoxIsPresent(){

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
    public void closeShippingBox(){
//        SearchContext shadow = driver.findElement(bonusOfferShadowRootBox).getShadowRoot();
//        shadow.findElement(closeBonusOfferBox).click();
        driver.findElement(closeBox).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(shippingBox));
    }
}
