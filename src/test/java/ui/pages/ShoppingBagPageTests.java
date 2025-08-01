package ui.pages;

import Danilova.PageObjects.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingBagPageTests extends PrepareDriverTest {
    @Test
    @Tag("critical")
    @Order(1)
    @Description("Check color of random item in bag")
    void checkItemColorInBagTest() {

        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        new CloseAddBoxesTest().closeAdds(home);

        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();
        AddedToBagSideBar bar = new AddedToBagSideBar(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        bar.clickViewBag();

        wait.until(ExpectedConditions.urlContains("cart"));

        ShoppingBagPage bag = new ShoppingBagPage(driver);
        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);
        String expectedColor = dropPage.getSelectedColor();
        String currentColor = bag.getColor();
        Assertions.assertEquals(expectedColor, currentColor);
    }

    @Test
    @Tag("critical")
    @Order(2)
    @Description("Check size of random item in bag")
    void checkItemSizeInBagTest() {

        new CloseAddBoxesTest().closeAdds(home);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);

        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();

        AddedToBagSideBar bar = new AddedToBagSideBar(driver);

        bar.clickViewBag();

        wait.until(ExpectedConditions.urlContains("cart"));

        ShoppingBagPage bag = new ShoppingBagPage(driver);
        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);

        String currentSize = bag.getSize();
        String expectedSize = dropPage.getSelectedSize();
        Assertions.assertEquals(expectedSize, currentSize);
    }

    @Test
    @Tag("critical")
    @Order(3)
    @Description("Check quantity of random item in bag")
    void checkItemQtyInBagTest() {
        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);

        List<WebElement> tiles = womenNewPage.getAllNewArrivals();
        new CloseAddBoxesTest().closeAdds(home);

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        AddedToBagSideBar bar = new AddedToBagSideBar(driver);

        bar.clickViewBag();

        wait.until(ExpectedConditions.urlContains("cart"));

        ShoppingBagPage bag = new ShoppingBagPage(driver);
        int currentCount = bag.getCountOfBagItems();
        int expectedCount = 1;
        Assertions.assertEquals(expectedCount, currentCount);

        int currentQty = Integer.parseInt(bag.getQty());
        System.out.println(currentQty);
        int expectedQty = dropPage.getSelectedQuantity();
        System.out.println(expectedQty);
        Assertions.assertEquals(expectedQty, currentQty);
    }

    @Test
    @Tag("critical")
    @Order(4)
    @Description("Add random count of items to bag")
    void addRandomCountOfItemsToBagTest() {
        int defaultCount = 2;
        int iterations = Integer.parseInt(System.getProperty("itemsCount", String.valueOf(defaultCount)));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        new CloseAddBoxesTest().closeAdds(home);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        // Добавляем несколько товаров
        for (int i = 0; i < iterations; i++) {
            System.out.println("Adding item #" + (i + 1));

            new CloseAddBoxesTest().closeAdds(home);

            List<WebElement> tiles = womenNewPage.getAllNewArrivals();

            new CloseAddBoxesTest().closeAdds(home);

            womenNewPage.chooseOneItem(tiles);

            FastShopPage dropPage = new FastShopPage(driver);


            dropPage.clickRandomColorResult();
            List<WebElement> sizes = dropPage.getAllSizes();
            dropPage.clickRandomSizeResult(sizes);
            dropPage.clickAddToBagRandomCountOfItems();
            dropPage.addToBagClick();

            // Если это не последняя итерация - возвращаемся назад
            if (i < iterations - 1) {
                driver.navigate().back();
                // Ждем загрузки страницы
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

        int bagIconCount = home.header.getBagCount();
        Assertions.assertEquals(globalCount, bagIconCount);
    }
}
