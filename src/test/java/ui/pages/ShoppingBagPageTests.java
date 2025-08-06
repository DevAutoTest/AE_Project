package ui.pages;

import Danilova.PageObjects.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.steps.AddItemsToBagSteps;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ShoppingBagPageTests extends PrepareDriverTest {
    @Test
    @Tag("critical")
    @Description("Check color of random item in bag")
    void checkItemColorInBagTest() {

        AddItemsToBagSteps addToBag = new AddItemsToBagSteps(driver);
        String selectedColor = addToBag.checkColorInBagWithOneItemStep(home);

        ShoppingBagPage bag = new ShoppingBagPage(driver);
        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);

        String currentColor = bag.getColor();
        Assertions.assertEquals(selectedColor, currentColor);
    }

    @Test
    @Tag("critical")
    @Description("Check size of random item in bag")
    void checkItemSizeInBagTest() {
        AddItemsToBagSteps addToBag = new AddItemsToBagSteps(driver);
        String selectedSize = addToBag.checkSizeInBagWithOneItemStep(home);

        ShoppingBagPage bag = new ShoppingBagPage(driver);

        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);

        String currentSize = bag.getSize();

        Assertions.assertEquals(selectedSize, currentSize);
    }

    @Test
    @Tag("critical")
    @Description("Check quantity of random item in bag")
    void checkItemQtyInBagTest() {

        AddItemsToBagSteps addToBag = new AddItemsToBagSteps(driver);
        int selectedQty = addToBag.checkQtyInBagWithOneItemStep(home);

        ShoppingBagPage bag = new ShoppingBagPage(driver);
        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);

        int currentQty = Integer.parseInt(bag.getQty());
        System.out.println(currentQty);

        Assertions.assertEquals(selectedQty, currentQty);
    }

    @Test
    @Tag("critical")
    @Description("Add random count of items to bag")
    void addRandomCountOfItemsToBagTest() {
        int defaultCount = 2;
        int iterations = Integer.parseInt(System.getProperty("itemsCount", String.valueOf(defaultCount)));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        new CloseAdsBoxesTests().closeAdsTest(home);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        for (int i = 0; i < iterations; i++) {
            System.out.println("Adding item #" + (i + 1));

            new CloseAdsBoxesTests().closeAdsTest(home);

            List<WebElement> tiles = womenNewPage.getAllNewArrivals();

            new CloseAdsBoxesTests().closeAdsTest(home);

            womenNewPage.chooseOneItem(tiles);

            FastShopPage dropPage = new FastShopPage(driver);

            dropPage.clickRandomColorResult();
            List<WebElement> sizes = dropPage.getAllSizes();
            dropPage.clickRandomSizeResult(sizes);
            dropPage.clickAddToBagRandomCountOfItems();
            dropPage.addToBagClick();

            if (i < iterations - 1) {
                driver.navigate().back();
                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.urlContains("new-arrivals"));
            }
        }
        AddedToBagSideBar bar = new AddedToBagSideBar(driver);

        wait.until(ExpectedConditions.presenceOfElementLocated(bar.viewBagButton));
        bar.clickViewBag();
        wait.until(ExpectedConditions.urlContains("cart"));

        ShoppingBagPage bag = new ShoppingBagPage(driver);

        int globalCount = Integer.parseInt(bag.getGlobalQty());
        int sumResult = bag.sumItemsQty();
        Assertions.assertEquals(globalCount, sumResult);

        bag.goBack();

        int bagIconCount = home.header.getHeaderBagCount();
        Assertions.assertEquals(globalCount, bagIconCount);
    }

    @Test
    @Tag("extended")
    @Description("Add 50 count of items to bag")
    void add50ItemsToBagTest() {
        new CloseAdsBoxesTests().closeAdsTest(home);

        int startCount = 0;
        int targetCount = 50;

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        new CloseAdsBoxesTests().closeAdsTest(home);
        home.fastMenu.openWomenMenu();

        while (startCount <= targetCount) {
            List<WebElement> tiles = womenNewPage.getAllNewArrivals();

            womenNewPage.chooseOneItem(tiles);

            FastShopPage dropPage = new FastShopPage(driver);

            dropPage.clickRandomColorResult();
            List<WebElement> sizes = dropPage.getAllSizes();
            dropPage.clickRandomSizeResult(sizes);

            int remaining = targetCount - startCount;
            int clicksAllowed = Math.min(9, remaining - 1);

            int clickCount = new Random().nextInt(clicksAllowed + 1);

            for (int i = 0; i < clickCount; i++) {
                driver.findElement(dropPage.getIncreaseCount()).click();// 1 клик по кнопке "+"
            }

            dropPage.addToBagClick();

            int itemsAdded = 1 + clickCount; // 1 — это первый товар, остальное через +
            startCount += itemsAdded;
            System.out.println("Added " + itemsAdded + " items. Total: " + startCount);

            driver.navigate().back();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            if (startCount == targetCount) {
                return;
            }
        }

        HeaderComponent header = new HeaderComponent(driver);
        int actualCount = header.getHeaderBagCount();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        Assertions.assertEquals(targetCount, actualCount);
    }


    @Test
    @Tag("extended")
    @Order(6)
    @Description("Adding 51 count of items to bag is prohibited")
    void add51ItemsToBagTest() {
        new CloseAdsBoxesTests().closeAdsTest(home);

        int startCount = 0;
        int targetCount = 51;

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        new CloseAdsBoxesTests().closeAdsTest(home);
        home.fastMenu.openWomenMenu();
        FastShopPage dropPage = new FastShopPage(driver);

        while (startCount <= targetCount) {
            List<WebElement> tiles = womenNewPage.getAllNewArrivals();

            womenNewPage.chooseOneItem(tiles);

            dropPage.clickRandomColorResult();

            List<WebElement> sizes = dropPage.getAllSizes();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
            dropPage.clickRandomSizeResult(sizes);

            int remaining = targetCount - startCount;
            int clicksAllowed = Math.min(9, remaining - 1); // потому что уже 1 добавится автоматически

            int clickCount = new Random().nextInt(clicksAllowed + 1); // может быть 0, если остался только 1

            for (int i = 0; i < clickCount; i++) {
                driver.findElement(dropPage.getIncreaseCount()).click();// 1 клик по кнопке "+"
            }

            dropPage.addToBagClick();

            int itemsAdded = 1 + clickCount; // 1 — это первый товар, остальное через +
            startCount += itemsAdded;
            System.out.println("Added " + itemsAdded + " items. Total: " + startCount);
            if (startCount < targetCount) {
                driver.navigate().back();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            } else return;
        }
        Assertions.assertEquals(dropPage.exceedLimitMessage, driver.findElement(dropPage.exceedLimitElement).getText());
    }
}
