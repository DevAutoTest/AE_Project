package ui.pages;

import Danilova.PageObjects.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShoppingBagPageTests extends PrepareDriverTest {
    @Test
    @Tag("critical")
    @Description("Check color of random item in bag")
    void checkItemColorInBag() throws InterruptedException {

        CloseAddBoxes.closeAdds(driver);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        if(home.rewardBox.isRealRewardPresent()){
            home.rewardBox.closeRewardBox();
        }
        if(home.signUpBox.signUpIsPresent()){
            home.signUpBox.closeSignUpBox();
        }
        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        dropPage.clickRandomColorResult();
        dropPage.clickRandomSizeResult();
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();
        AddedToBagSideBar bar = new AddedToBagSideBar(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
    @Description("Check size of random item in bag")
    void checkItemSizeInBag() throws InterruptedException {

        CloseAddBoxes.closeAdds(driver);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        if(home.rewardBox.isRealRewardPresent()){
            home.rewardBox.closeRewardBox();
        }
        if(home.signUpBox.signUpIsPresent()){
            home.signUpBox.closeSignUpBox();
        }
        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        dropPage.clickRandomColorResult();
        dropPage.clickRandomSizeResult();
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
    @Description("Check quantity of random item in bag")
    void checkItemQtyInBag() throws InterruptedException {
       CloseAddBoxes.closeAdds(driver);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        if(home.rewardBox.isRealRewardPresent()){
            home.rewardBox.closeRewardBox();
        }
        if(home.signUpBox.signUpIsPresent()){
            home.signUpBox.closeSignUpBox();
        }
        womenNewPage.chooseOneItem();

        FastShopPage dropPage = new FastShopPage(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        dropPage.clickRandomColorResult();
        dropPage.clickRandomSizeResult();
        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();

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
    @Description("Add random count of items to bag")
    void addRandomCountOfItemsToBag() throws InterruptedException {
        int iterations = 4;
        CloseAddBoxes.closeAdds(driver);
        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);

        // Добавляем несколько товаров
        for (int i = 0; i < iterations; i++) {
            System.out.println("Adding item #" + (i + 1));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            if(home.rewardBox.isRealRewardPresent()){
                home.rewardBox.closeRewardBox();
            }
            if(home.signUpBox.signUpIsPresent()){
                home.signUpBox.closeSignUpBox();
            }
            if(home.rewardBox.isRealRewardPresent()){
                home.rewardBox.closeRewardBox();
            }

            if(home.signUpBox.signUpIsPresent()){
                home.signUpBox.closeSignUpBox();
            }

            womenNewPage.chooseOneItem();

            FastShopPage dropPage = new FastShopPage(driver);

            dropPage.clickRandomColorResult();
            dropPage.clickRandomSizeResult();
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
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        String expectedText = AddedToBagSideBar.ADDED_SUCCESS;
//        String currentText = bar.getText();
//
//        Assertions.assertEquals(expectedText, currentText);

        bar.clickViewBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
