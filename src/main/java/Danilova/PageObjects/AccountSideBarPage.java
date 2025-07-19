package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountSideBarPage extends BasePage {

    public By acSideBarTitle = By.xpath("//h2[@class='modal-title']");
    public By signInBttn = By.xpath("//button[@class='btn btn-secondary qa-btn-signin btn-sm _actions-button_12ftjw']");
    public By crtAcntBttn = By.xpath("//a[@class='ember-view btn btn-sm btn-secondary _actions-button_12ftjw' ]");
    public By acntSttngs = By.xpath("//button[@class='btn-link qa-btn-toggle-login _link_12ftjw _link-list-btn_12ftjw']");
    public By clsAcntSideBar = By.xpath("//button[@class='btn-close sidetray-close-button qa-btn-cancel']");
    public By signOutBttn = By.xpath("//button[@data-test-btn='sign-out']");



    public AccountSideBarPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get title of account side bar")
    public String getTitleAcntSideBar() {
        return driver.findElement(acSideBarTitle).getText();
    }

    @Step("Open create account page")
    public void openCrtAcntPage() {
        driver.findElement(crtAcntBttn).click();
    }

    @Step("Open SignIn page")
    public void openSignInPage() {
        driver.findElement(signInBttn).click();
    }

    @Step("Click close button")
    public void clcCloseBttn() {
        driver.findElement(clsAcntSideBar).click();
    }

    @Step("Click Signout button")
    public void clcSignOut() {
        driver.findElement(signOutBttn).click();
    }



}
