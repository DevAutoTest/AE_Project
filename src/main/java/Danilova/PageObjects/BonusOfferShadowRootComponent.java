package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Вы не можете использовать XPath внутри shadowRoot, только CSS-селекторы.*/

public class BonusOfferShadowRootComponent {
    final WebDriver driver;
    private WebDriverWait wait;
//    By bonusOfferShadowRootBox = By.cssSelector("div.bloomreach-weblayer");
//    By bonusOfferBox = By.cssSelector("div.weblayer--box-promotion-1.vertical-center.horizontal-center.enter-fade");
//    By closeBonusOfferBox = By.cssSelector("div.bloomreach-weblayer button[aria-label=\"Close\"]");


    By offerBox = By.xpath("//div[@class='text-center ']");
    By closeOffer = By.xpath("//button[@aria-label='Close']");

    public BonusOfferShadowRootComponent(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Does bonus offer box present?")
    public boolean isPresent(){

        try {
            driver.findElement(offerBox);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    @Step("Close bonus offer box")
    public void closeOfferBox(){
//        SearchContext shadow = driver.findElement(bonusOfferShadowRootBox).getShadowRoot();
//        shadow.findElement(closeBonusOfferBox).click();
        driver.findElement(closeOffer).click();
        // ждём, пока сам баннер исчезнет
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.text-center ")
        ));
    }
}
