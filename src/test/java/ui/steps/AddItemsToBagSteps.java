package ui.steps;

import Danilova.PageObjects.*;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.pages.CloseAddBoxesTest;
import ui.pages.PrepareDriverTest;

import java.time.Duration;
import java.util.List;

public class AddItemsToBagSteps extends PrepareDriverTest {
    WebDriver driver;

    public AddItemsToBagSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Choose random Item (random color, size and count) STEP")
    public void chooseRandomItemStep(HomePage home) {
        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);
        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();
    }

    @Step("Add random 1 item to Bag STEP")
    public void addRandomOneItemToBagStep(HomePage home) {
        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);
        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        new CloseAddBoxesTest().closeAdds(home);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();
        dropPage.clickRandomSizeResult(sizes);
        dropPage.clickAddToBagRandomCountOfItems();

        dropPage.addToBagClick();
    }

    @Step("Check Bag Color with 1 Item STEP")
    public String checkColorInBagWithOneItemStep(HomePage home) {
        new CloseAddBoxesTest().closeAdds(home);

        home.fastMenu.openWomenMenu();

        WomenNewArrivalsPage womenNewPage = new WomenNewArrivalsPage(driver);
        new CloseAddBoxesTest().closeAdds(home);
        List<WebElement> tiles = womenNewPage.getAllNewArrivals();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        womenNewPage.chooseOneItem(tiles);

        FastShopPage dropPage = new FastShopPage(driver);

        dropPage.clickRandomColorResult();
        List<WebElement> sizes = dropPage.getAllSizes();

        dropPage.clickRandomSizeResult(sizes);

        dropPage.clickAddToBagRandomCountOfItems();
        dropPage.addToBagClick();

        AddedToBagSideBar bar = new AddedToBagSideBar(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        bar.clickViewBag();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("cart"));

        return dropPage.getSelectedColor();
    }

    @Step("Check Bag Size with 1 Item STEP")
    public String checkSizeInBagWithOneItemStep(HomePage home) {

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

        return dropPage.getSelectedSize();
    }

    @Step("Check Bag Qty with 1 Item STEP")
    public int checkQtyInBagWithOneItemStep(HomePage home) {
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

        return dropPage.getSelectedQuantity();
    }
}



