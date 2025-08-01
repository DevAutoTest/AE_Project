package Danilova.PageObjects;

import io.qameta.allure.Step;
import lombok.Getter;
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
    @Getter
    private String selectedColor;
    @Getter
    private String selectedSize;
    @Getter
    private int selectedQuantity;
    //    String firstPrice = driver.findElement(By.xpath("//div[@data-testid='list-price']")).getText();
    //
    //    String currentPrice = driver.findElement(By.xpath("//div[@data-testid='sale-price']")).getText();
    //
    //    String discount = driver.findElement(By.xpath("//div[contains(@class,'_sale-tag')]")).getText();

    By addToBagButton = By.xpath("//button[@data-test-btn='addToBag']");
    By increaseCountButton = By.xpath("//button[@aria-label='increase']");

    public FastShopPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Checking of presence add to bag button")
    public boolean addToBagButtonIsPresent() {
        try {
            // Сначала дожидаемся появления элемента в DOM
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addToBagButton));
            new Actions(driver)
                    .moveToElement(button)
                    .pause(Duration.ofMillis(500))
                    .perform();

            // Проверяем видимость элемента
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

        // генерируем индекс от 0 до count-1
        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        // Попробуем кликнуть, повторяя при StaleElementReference
        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement tile = tiles.get(idx);
                System.out.println("попытка = " + attempts);

                // Сохраняем выбранный цвет
                this.selectedColor = tile.getAttribute("alt");

                // скроллим к нему, чтобы не было off-screen
                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(500))
                        .perform();

                // ждём, пока станет кликабельным
                wait.until(ExpectedConditions.elementToBeClickable(tile)).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return;  // успех — выходим
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                // перечитываем актуальные элементы
                tiles = getAllColors();
            }
        }
        // если после retry всё ещё не получилось
        throw new WebDriverException("Failed to click random color");
    }

    @Step("Return list of available sizes")
    public List<WebElement> getAllSizes() {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {

            WebElement sizeDropdown = longWait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@aria-label='Size' or contains(@class,'size-selector')]")));

            Actions actions = new Actions(driver);
            actions.moveToElement(sizeDropdown).click().perform();


            // 3. Ожидание появления нескольких первых вариантов размеров
            //очень много размеров: https://www.ae.com/us/en/p/women/baggy-wide-leg-jeans/baggy-wide-leg-jeans/ae-dreamy-drapey-stretch-super-high-waisted-baggy-wide-leg-jean/0437_5870_913?menu=cat4840004
            longWait.until(driver -> {
                List<WebElement> elements = driver.findElements(By.xpath("//div[@data-test-select-custom='size']//a[@role='menuitem']"));
                List<WebElement> visible = elements.stream()
                        .filter(WebElement::isDisplayed)
                        .collect(Collectors.toList());
                return visible.size() >= 2 ? visible : null;
            });

            // 4. Возвращаем только активные размеры
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

        // генерируем индекс от 0 до count-1
        int idx = randomIntInclusive(0, count - 1);
        System.out.println("random indx = " + idx);

        // Попробуем кликнуть, повторяя при StaleElementReference
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement tile = tiles.get(idx);

                System.out.println("попытка = " + attempts);
                System.out.println("URL=" + getCurrentUrl());

                // Сохраняем выбранный размер
                this.selectedSize = tile.getText();
                System.out.println(selectedSize);

                // скроллим к нему, чтобы не было off-screen
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                new Actions(driver)
                        .moveToElement(tile)
                        .pause(Duration.ofMillis(700))
                        .perform();
                tile.click();

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return;  // успех — выходим
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                attempts++;
                // перечитываем актуальные элементы
                tiles = getAllSizes();
            }
        }
        // если после retry всё ещё не получилось
        throw new WebDriverException("Failed to click random color");
    }

    @Step("add to bag")
    public void addToBagClick() {
        driver.findElement(addToBagButton).click();
    }

    /**
     * Кликает по кнопке Add To Bag случайное количество раз (от 1 до 10)
     * без необходимости передавать какие-либо параметры
     */
    @Step("Click Add To Bag button random times (1-10)")
    public void clickAddToBagRandomCountOfItems() {
        int clickCount = random.nextInt(9); // Случайное число от 0 до 9
        System.out.println("Will perform " + clickCount + " clicks on Add To Bag button");

        this.selectedQuantity = clickCount + 1;
        for (int i = 0; i < clickCount; i++) {
            try {
                // Используем определенный в классе локатор addToBagButton
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(increaseCountButton)));

                // Прокручиваем к кнопке для надежности
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", button);

                button.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                System.out.println("Click #" + (i + 1) + " performed");

                // Небольшая пауза между кликами
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
