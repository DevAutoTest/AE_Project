package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;


/** Сюда пишем методы общие для сайта
 *  + метод открытия главной страницы
 *  + методы получения локаторов этой страницы
 *  + редиректы с главной страницы куда то**/

public class HomePage  extends BasePage {
    public static final String HOME_PAGE_URL = "https://www.ae.com/us/en";
    public static final String AERIE_URL = "https://www.ae.com/us/en/c/aerie/cat4840006?pagetype=clp";

    public HomePage(WebDriver driver) {
        super(driver);
        open();
    }

    //locators

    //actions
    @Step("Open AMERICAN EAGLE by URL")
    private void open() {
        driver.get(HOME_PAGE_URL);
    }

    @Step("Open AERIE by URL")
    private void openAerie() {
        driver.get(AERIE_URL);
    }


}
