package Danilova.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddedToBagSideBar extends BasePage {
    public WebDriverWait wait;

    public static final String ADDED_SUCCESS = "Added to bag!";

    public By text = By.xpath("//h2[@class='modal-title']");
    public By viewBagButton = By.xpath("//button[@data-test-btn='viewBag']");

    public AddedToBagSideBar(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void clickViewBag() {
        driver.findElement(viewBagButton).click();

    }

    public String getText() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver.findElement(text).getText();
    }
}
