package Danilova.PageObjects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddedToBagSideBar extends BasePage {

    public static final String ADDED_SUCCESS = "Added to bag!";
    @Getter
    String text = driver.findElement(By.xpath("//h2[@class='modal-title']")).getText();

    By viewBagButton = By.xpath("//button[@data-test-btn='viewBag']");

    public AddedToBagSideBar(WebDriver driver) {
        super(driver);
    }

    public void clickViewBag(){
        driver.findElement(viewBagButton).click();

    }


}
