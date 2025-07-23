package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** компонент, у которого есть внутри только WebDriver и локаторы.*/

public class HeaderComponent {
    final WebDriver driver;
    By bagCount = By.xpath("//a[@class='ember-view bag-button underline-on-hover qa-tnav-bag-icon']//span/span");


    public HeaderComponent(WebDriver driver) {

        this.driver = driver;
    }

    // get logo AE_Header
    By logoAE_Header = By.xpath("//a[@data-testid='ae-logo']");

    //Locator menu Logo AE_Menu

    By headerSearchButton = By.xpath("//button[@data-test-btn='search-cta']");
    By headerAccountButton = By.xpath("//a[@class='clickable qa-show-sidetray-account sidetray-account']");
    By headerCartButton = By.xpath("//a[@href='/us/en/cart']");


    @Step("Get logo AE_Header")
    public WebElement getLogoAE_Header() {
        return driver.findElement(logoAE_Header);
    }

    @Step("Click logo AE_Header")
    public void clickLogoAE_Header() {
        driver.findElement(logoAE_Header).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Step("Get search header button")
    public By getSearch(){
        return headerSearchButton;
    }

    @Step("click search header button")
    public void clckHdrSrchBttn(){
        driver.findElement(headerSearchButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Step("Get account header button")
    public By getAcntBttn(){
        return headerAccountButton;
    }

    @Step("click account header button")
    public void clckHdrAcntBttn(){
        driver.findElement(headerAccountButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Step("click cart header button")
    public void clckHdrCartBttn(){
        driver.findElement(headerCartButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @Step("Get header bag icon count items added to bag")
    public int getBagCount(){
       return Integer.parseInt(driver.findElement(bagCount).getText());
    }



}
