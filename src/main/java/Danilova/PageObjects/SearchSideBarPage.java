package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchSideBarPage extends BasePage {

    public final static String SEARCH_RESULT_URL = "https://www.ae.com/us/en/s/";

    public SearchSideBarPage(WebDriver driver) {
        super(driver);
    }

    By searchTitle = By.xpath("//h2[@class='modal-title']");
    By searchInput = By.xpath("//input[@type='search']");
    By closeButton = By.xpath("//button[contains(@class, 'btn-close sidetray-close-button qa-btn-cancel')]");
    By searchButton = By.xpath("//button[contains(@class, 'btn-link qa-btn-submit btn _search-btn_1vao1q')]");

    @Step("Get search side page title")
    public String getSearchTitle() {
        return driver.findElement(searchTitle).getText();
    }


    @Step("Get search input text")
    public String getSearchText() {
        //Products, help topics, etc.
        return driver.findElement(searchInput).getAttribute("placeholder");
    }

    @Step("click close search side bar")
    public void clickSerchClose() {
        driver.findElement(closeButton).click();
    }

    @Step("click search button")
    public void clickSerchButton() {
        driver.findElement(searchButton).click();
    }

    @Step("enter for search")
    public void enterSearchInput(String text) {
        driver.findElement(searchInput).sendKeys(text);
    }


}
