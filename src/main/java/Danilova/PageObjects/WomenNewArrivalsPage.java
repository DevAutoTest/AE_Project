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

    /**
     * Возвращает список актуальных плиток на странице
     */
    public List<WebElement> getAllNewArrivals() {
        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'product-tile')]")));

                List<WebElement> elements = driver.findElements(
                        By.xpath("//div[starts-with(@class,'product-tile _container_') and not(.//*[normalize-space() = 'Coming Soon']) and not(.//*[normalize-space() = 'Sold Out'])]"));
                new Actions(driver)
                        .moveToElement(elements.get(0))
                        .pause(Duration.ofMillis(200))
                        .perform();

                // Проверяем, что элементы действительно доступны
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
        // List<WebElement> tiles = getAllNewArrivals();
        int count = tiles.size();
        System.out.println("count = " + count);
        if (count == 0) {
            throw new NoSuchElementException("No product tiles found on New Arrivals page");
        }

        // генерируем индекс от 0 до count-1
        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        // Попробуем кликнуть, повторяя при StaleElementReference
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement tile = tiles.get(idx);
                System.out.println("попытка = " + attempts);
                // скроллим к нему, чтобы не было off-screen
                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(200))
                        .perform();
                // ждём, пока станет кликабельным
                System.out.println("Кликаем по выбранному элементу");

                wait.until(ExpectedConditions.elementToBeClickable(tile)).click();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                System.out.println(driver.getCurrentUrl());

                return;  // успех — выходим
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                // перечитываем актуальные элементы
                tiles = getAllNewArrivals();
            }
        }
        // если после retry всё ещё не получилось
        throw new WebDriverException("Failed to click random New Arrival product after retries");
    }

    @Step("Add random items to bag from New Arrivals (1 to max available)")
    @Disabled("Need time to improve")
    public void addRandomItemsToBagWithPossibleDuplicates() {
        List<WebElement> tiles = getAllNewArrivals();
        int totalItems = tiles.size();

        if (totalItems == 0) {
            throw new NoSuchElementException("No product tiles found on New Arrivals page");
        }

        // Генерируем случайное количество товаров для добавления (от 1 до общего количества)
        int itemsToAdd = randomIntInclusive(1, totalItems);
        System.out.println("Adding " + itemsToAdd + " random items to bag (possibly duplicates)");

        for (int i = 0; i < itemsToAdd; i++) {
            int attempts = 0;
            boolean success = false;

            while (attempts < 3 && !success) {
                try {
                    // Выбираем случайный индекс (может повторяться)
                    int idx = randomIntInclusive(0, totalItems - 1);

                    WebElement tile = tiles.get(idx);
                    System.out.println("Adding item #" + (i + 1) + " with index: " + idx);

                    // Скроллим к элементу
                    new Actions(driver)
                            .moveToElement(tile)
                            .pause(Duration.ofMillis(200))
                            .perform();
                    // Кликаем на товар
                    wait.until(ExpectedConditions.elementToBeClickable(tile)).click();

                } catch (StaleElementReferenceException e) {
                    attempts++;
                    System.out.println("Stale element, refreshing list. Attempt: " + attempts);

                } catch (ElementClickInterceptedException e) {
                    attempts++;
                    System.out.println("Click intercepted, retrying. Attempt: " + attempts);
                } catch (TimeoutException e) {
                    attempts++;
                    System.out.println("Timeout, retrying. Attempt: " + attempts);
                }
            }
        }
    }

}
