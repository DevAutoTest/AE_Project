package Danilova.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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


   public int getCountOfBagItems(){
        return listBagItems.size();
   }

    public String getColor(){
        String fullText = listOfItemsColors.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    public String getSize(){
        String fullText = listOfItemsSizes.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    public String getQty(){
        String fullText = listOfItemsQty.get(0).getText().trim();
        String[] parts = fullText.split(": ");
        return parts.length > 1 ? parts[1] : "";
    }

    public String getGlobalQty(){
        String fullText = globalQty.trim();
        String[] parts = fullText.split(" ");
        return parts.length > 1 ? parts[0] : "";
    }

    public int sumItemsQty(){
       int items =  listOfItemsQty.size();
       int resultCount = 0;
        for (WebElement webElement : listOfItemsQty) {
            String fullText = webElement.getText().trim();
            String[] parts = fullText.split(": ");
            String result = parts.length > 1 ? parts[1] : "";
            resultCount += Integer.parseInt(result);
        }
        return resultCount;
    }
}
