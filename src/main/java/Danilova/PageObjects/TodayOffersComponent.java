package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TodayOffersComponent {

    WebDriver driver;
    private final WebDriverWait wait;

    public TodayOffersComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
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
//        SearchContext shadow = driver.findElement(bonusOfferShadowRootBox).getShadowRoot();
//        shadow.findElement(closeBonusOfferBox).click();
            driver.findElement(closeBox).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(todayBox));
        } catch (NoSuchElementException e) {
            System.out.println("today offer doesn't close");
        }
    }

}
