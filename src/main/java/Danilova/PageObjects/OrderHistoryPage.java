package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderHistoryPage extends BasePage {
    public static final String ORDER_HISTORY_URL = "https://www.ae.com/us/en/myaccount/order-history";
    By year_2025 = By.xpath("//ul[@id='dropdown-ember92']/li[@data-value='2025']");
    By year_2024 = By.xpath("//ul[@id='dropdown-ember92']/li[@data-value='2024']");
    By year_2023 = By.xpath("//ul[@id='dropdown-ember92']/li[@data-value='2023']");
    By year_2022 = By.xpath("//ul[@id='dropdown-ember92']/li[@data-value='2022']");
    By year_2021 = By.xpath("//ul[@id='dropdown-ember92']/li[@data-value='2021']");

    By countOffOrdersText = By.xpath("//span[contains(@class,'_select-label') and contains(.,'Orders Placed in')]");

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("click 2025")
    void click2025() {
        driver.findElement(year_2025).click();
    }

    @Step("click 2024")
    void click2024() {
        driver.findElement(year_2024).click();
    }

    @Step("click 2023")
    void click2023() {
        driver.findElement(year_2023).click();
    }

    @Step("click 2022")
    void click2022() {
        driver.findElement(year_2022).click();
    }

    @Step("click 2021")
    void click2021() {
        driver.findElement(year_2021).getText();
    }

    @Step("Get count of orders by year 2025")
    public String getCountByYear(String year) {
        switch (year) {
            case "2025" -> {
                click2025();
                return driver.findElement(countOffOrdersText).getText();
            }
            case "2024" -> {
                click2024();
                return driver.findElement(countOffOrdersText).getText();
            }
            case "2023" -> {
                click2023();
                return driver.findElement(countOffOrdersText).getText();
            }
            case "2022" -> {
                click2022();
                return driver.findElement(countOffOrdersText).getText();
            }
            case "2021" -> {
                click2021();
                return driver.findElement(countOffOrdersText).getText();
            }
        }
        return "Uooops!";
    }

}
