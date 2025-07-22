package Danilova.PageObjects;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

@Getter
public class FastMenuComponent {
    final WebDriver driver;
    private final WebDriverWait wait;
    By womenNew = By.xpath("//a[starts-with(@href, '/us/en/c/women/new-arrivals/') and @target='_self']");
    By womenNewSpan1 = By.xpath("//div[@data-testid='lockup-layout-primary']//span[text()=\"Women's New Arrivals\"]");
    By womenNewSpan = By.xpath("//span[text()=\"Women's New Arrivals\"]");
    By womenTops = By.xpath("//a[@href='/us/en/c/women/tops/cat10049?pagetype=plp']");
    By womenJeans = By.xpath("//a[@href='/us/en/c/women/bottoms/jeans/cat6430042?pagetype=plp']");
    By womenDress = By.xpath("//a[@href='/us/en/c/ae/women/dresses/cat1320034?pagetype=plp']");
    By menNew = By.xpath("//a[@href='/us/en/c/men/new-arrivals/1hnv8q2Z1266xyj-filtered?pagetype=plp']");
    By menTops = By.xpath("//a[@href='/us/en/c/ae/men/tops/cat10025?pagetype=plp']");
    By menJeans = By.xpath("//a[@href='/us/en/c/ae/men/bottoms/jeans/cat6430041?pagetype=plp']");
    By menAE = By.xpath("//a[@href='/us/en/c/men/activewear/cat1100008?pagetype=plp']");



    public FastMenuComponent(WebDriver driver){
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }



    @Step("Open fast women menu")
    public void openWomenMenu() throws InterruptedException {

        // 1) Ждём, пока элемент появится в DOM
        WebElement link = wait.until(
                ExpectedConditions.presenceOfElementLocated(womenNew)
        );

        // 2) Скроллим его в центр по горизонтали и вертикали
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", link
        );

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        link.click();
    }

    @Step("Open fast women tops menu")
    public void openWomenTopsMenu(){
        driver.findElement(womenTops).click();
    }

    @Step("Open fast women jeans menu")
    public void openWomenJeansMenu(){
        driver.findElement(womenJeans).click();
    }

    @Step("Open fast women dresses menu")
    public void openWomenDressesMenu(){
        driver.findElement(womenDress).click();
    }

    @Step("Open fast men menu")
    public void openMenMenu(){
        driver.findElement(menNew).click();
    }

    @Step("Open fast men tops menu")
    public void openMenTopsMenu(){
        driver.findElement(menTops).click();
    }

    @Step("Open fast men jeans menu")
    public void openMenJeansMenu(){
        driver.findElement(menJeans).click();
    }

    @Step("Open fast men AE menu")
    public void openMenAEMenu(){
        driver.findElement(menAE).click();
    }


}
