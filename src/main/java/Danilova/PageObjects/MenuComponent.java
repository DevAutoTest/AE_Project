package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;


public class MenuComponent {
    final WebDriver driver;
    final WebDriverWait wait;

    public MenuComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By womenMenu = By.xpath("//li[@data-test='top-link-wrapper']/a[@href='/us/en/c/women/womens?pagetype=clp']");
    By categories = By.xpath("//li[a[contains(@href,'/us/en/c/women')]][@data-test='top-link-wrapper']//div/div/div/ul/li[contains(@class,'list-element')]/a[contains(@href,'/us/en/c/') or contains(@href,'/us/en/x') and text()[normalize-space()]]");
    //  By womenMenuLinks = By.xpath("//li[a[contains(@href,'/us/en/c/women')]][@data-test='top-link-wrapper']//div/div/div/ul/li[contains(@class,'list-element')]/a[contains(@href,'/us/en/c/') or contains(@href,'/us/en/x') and text()[normalize-space()]]");

    @Step("Get list of women menu categories as String")
    public List<String> getWomenMenuTexts() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categories));
        return driver.findElements(categories).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Get list of links of women menu as String")
    public List<String> getWomenMenuLinks() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(categories));
        return driver.findElements(categories).stream()
                .map(categories -> categories.getAttribute("href"))
                .collect(Collectors.toList());
    }

    @Step("Click women menu links")
    public void clickWomenMenuLink(String url) {
        hoverOverWomen();

        List<WebElement> categoryElements = driver.findElements(categories);
        WebElement targetElement = categoryElements.stream()
                .filter(element -> url.equals(element.getAttribute("href")))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Категория с URL '" + url + "' не найдена"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetElement);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);

        wait.until(ExpectedConditions.urlToBe(url));
    }


    @Step("Hover over Women menu")
    public void hoverOverWomen() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(womenMenu));
        new Actions(driver).moveToElement(element).perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}
