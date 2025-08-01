package Danilova.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.qameta.allure.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class ShoppingBagPage extends BasePage {

    public static final String CART_URL = "https://www.ae.com/us/en/cart";

    List<WebElement> listBagItems = driver.findElements(By.xpath("//ul[@data-testid='commerce-items']//li[contains(@class,'qa-animated')]"));
    List<WebElement> listOfItemsColors = driver.findElements(By.xpath("//div[contains(@class,'cart-item-color')]"));
    List<WebElement> listOfItemsSizes = driver.findElements(By.xpath("//div[contains(@class,'cart-item-size qa-cart-item-size')]"));
    List<WebElement> listOfItemsQty = driver.findElements(By.xpath("//div[contains(@class,'cart-item-quantity')]"));


    String globalQty = driver.findElement(By.xpath("//h2[contains(@class,'text-capitalize _items')]")).getText();

    public ShoppingBagPage(WebDriver driver) {
        super(driver);
    }

    @Step("Getting count of bag items")
    public int getCountOfBagItems() {
        return listBagItems.size();
    }

    @Step("Checking color in a bag for 1 item")
    public String getColor() {
        new Actions(driver)
                .moveToElement(listOfItemsColors.get(0))
                .pause(Duration.ofMillis(500))
                .perform();
        String fullText = listOfItemsColors.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    @Step("Checking size in a bag for 1 item")
    public String getSize() {
        new Actions(driver)
                .moveToElement(listOfItemsSizes.get(0))
                .pause(Duration.ofMillis(500))
                .perform();
        String fullText = listOfItemsSizes.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    @Step("Checking quantity in a bag for 1 item")
    public String getQty() {
        new Actions(driver)
                .moveToElement(listOfItemsQty.get(0))
                .pause(Duration.ofMillis(500))
                .perform();
        String fullText = listOfItemsQty.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    @Step("Checking global quantity in a bag for several items")
    public String getGlobalQty() {
        String fullText = globalQty.trim();
        String[] parts = fullText.split(" ");
        return parts.length > 1 ? parts[0] : "";
    }

    @Step("Checking sum quantity in a bag for several items")
    public int sumItemsQty() {

        int resultCount = 0;
        for (WebElement webElement : listOfItemsQty) {
            new Actions(driver)
                    .moveToElement(webElement)
                    .pause(Duration.ofMillis(500))
                    .perform();

            String fullText = webElement.getText().trim();
            String[] parts = fullText.split(": ");
            String result = parts.length > 1 ? parts[1] : "";
            resultCount += Integer.parseInt(result);
        }
        return resultCount;
    }
}
