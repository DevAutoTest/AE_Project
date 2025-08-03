package Danilova.PageObjects;

import io.qameta.allure.Step;
import lombok.Getter;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static Danilova.utils.RandomUtils.randomIntInclusive;

public class FastShopPage extends BasePage {
    private final WebDriverWait wait;
    private final Random random = new Random();
    public int clickCount = 0;
    @Getter
    private String selectedColor;
    @Getter
    private String selectedSize;
    @Getter
    private int selectedQuantity;

    @Getter
    public int maxCountPerOrder = 50;

    @Getter
    public String exceedLimitMessage = "Sorry, you have exceeded the limit of items per order.";

    public By exceedLimitElement = By.xpath("//li[@data-testid='form-error']");
    @Getter
    public By increaseCount = By.xpath("//button[@aria-label='increase']");

    By addToBagButton = By.xpath("//button[@data-test-btn='addToBag']");
    By increaseCountButton = By.xpath("//button[@aria-label='increase']");
    By saveToFavoritesButton = By.xpath("//button[@name='toggleFavorites']");

    public FastShopPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Checking of presence add to bag button")
    public boolean addToBagButtonIsPresent() {
        try {

            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addToBagButton));
            new Actions(driver)
                    .moveToElement(button)
                    .pause(Duration.ofMillis(500))
                    .perform();

            return wait.until(ExpectedConditions.visibilityOf(button)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    @Step("Return list of available colors")
    private List<WebElement> getAllColors() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.visibilityOfAllElements(
                        driver.findElement(By.xpath("//div[@data-test-extras='colors']//div[@role='button']//img"))));
        return driver.findElements(By.xpath("//div[@data-test-extras='colors']//div[@role='button']//img"));
    }

    @Step("Click random color")
    public void clickRandomColorResult() {
        List<WebElement> tiles = getAllColors();
        int count = tiles.size();
        System.out.println("count of colors = " + count);
        if (count == 0) {
            throw new NoSuchElementException("No product colors found on page");
        }

        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement tile = tiles.get(idx);
                System.out.println("попытка = " + attempts);

                this.selectedColor = tile.getAttribute("alt");

                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(500))
                        .perform();

                wait.until(ExpectedConditions.elementToBeClickable(tile)).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                tiles = getAllColors();
            }
        }
        throw new WebDriverException("Failed to click random color");
    }

    @Step("Return list of available sizes")
    public List<WebElement> getAllSizes() {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            WebElement sizeDropdown = longWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@aria-label='Size' or contains(@class,'size-selector')]")));

            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(saveToFavoritesButton)).perform();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            sizeDropdown.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

            wait.until(driver -> {
                List<WebElement> all = driver.findElements(
                        By.xpath("//div[@data-test-select-custom='size']//a[@role='menuitem']")
                );
                List<WebElement> visible = all.stream()
                        .filter(WebElement::isDisplayed)
                        .collect(Collectors.toList());
                return visible.size() >= 2 ? visible : null;
            });

            return driver.findElements(By.xpath(
                    "//div[@data-test-select-custom='size']//a[@role='menuitem' and not(.//small) and not(contains(@class,'disabled'))]"));

        } catch (TimeoutException e) {
            throw new NoSuchElementException("Size selector not found on the page", e);
        }
    }

    @Step("Click random size")
    public void clickRandomSizeResult(List<WebElement> tiles) {

        int count = tiles.size();
        System.out.println("count of sizes = " + count);
        if (count == 0) {
            throw new NoSuchElementException("No product size found on page");
        }

        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement tile = tiles.get(idx);

                System.out.println("попытка = " + attempts);
                System.out.println("URL=" + getCurrentUrl());

                this.selectedSize = tile.getText();
                System.out.println(selectedSize);

                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(500))
                        .perform();

                tile.click();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                tiles = getAllSizes();
            }
        }
        throw new WebDriverException("Failed to click random color");
    }

    @Step("add to bag")
    public void addToBagClick() {
        driver.findElement(addToBagButton).click();
    }

    @Step("Click Add To Bag button random count of itimes (1-10)")
    public void clickAddToBagRandomCountOfItems() {
        clickCount = random.nextInt(9); // Случайное число от 0 до 9
        System.out.println("Will perform " + clickCount + " clicks on Add To Bag button");

        this.selectedQuantity = clickCount + 1;
        for (int i = 0; i < clickCount; i++) {
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseCountButton)));

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", button);

                button.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                System.out.println("Click #" + (i + 1) + " performed");

                if (i < clickCount - 1) {
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                System.out.println("Error on click #" + (i + 1) + ": " + e.getMessage());
                break;
            }
        }
    }
}
