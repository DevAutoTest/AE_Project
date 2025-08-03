package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static Danilova.utils.RandomUtils.randomIntInclusive;

public class WomenNewArrivalsPage extends BasePage {

    public final static String WOMEN_NEW_ARRIVALS_URL = "https://www.ae.com/us/en/c/women/new-arrivals/11gj7jfZ1266xyj-filtered?pagetype=plp";
    public final static String PAGE_TITLE = "New Arrivals | Women's Clothes & Apparel | American Eagle";

    private final WebDriverWait wait;

    public WomenNewArrivalsPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @Step("Getting of all New Arrivals List")
    public List<WebElement> getAllNewArrivals() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'product-tile')]")));

                List<WebElement> elements = driver.findElements(
                        By.xpath("//div[starts-with(@class,'product-tile _container_') and not(.//*[normalize-space() = 'Coming Soon']) and not(.//*[normalize-space() = 'Sold Out'])]"));
                Actions actions = new Actions(driver);
                actions.moveToElement(elements.get(0)).perform();

                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements;
                }
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Attempt " + attempts + " failed with StaleElementReference");
            }
        }
        throw new WebDriverException("Failed to get new arrivals after 3 attempts");
    }

    @Step("Add to bag one random result from New Arrivals")
    public void chooseOneItem(List<WebElement> tiles) {

        int count = tiles.size();
        System.out.println("count = " + count);
        if (count == 0) {
            throw new NoSuchElementException("No product tiles found on New Arrivals page");
        }

        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        int attempts = 0;
        while (attempts < 5) {
            try {
                WebElement tile = tiles.get(idx);
                System.out.println("попытка = " + attempts);
                System.out.println("Кликаем по выбранному элементу");

                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(200))
                        .click()
                        .perform();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                System.out.println(driver.getCurrentUrl());

                List<WebElement> unavailable = driver.findElements(By.xpath("//div[text()='Unavailable']"));

                if (!unavailable.isEmpty() && unavailable.get(0).isDisplayed()) {
                    System.out.println("Unavailable");
                    driver.navigate().back();
                    attempts++;
                    idx = randomIntInclusive(0, count - 1);
                    continue;
                }

                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                tiles = getAllNewArrivals();
            }
        }
        throw new WebDriverException("Failed to click random New Arrival product after retries");
    }
}
