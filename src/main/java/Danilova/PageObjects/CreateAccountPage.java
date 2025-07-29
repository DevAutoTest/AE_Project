package Danilova.PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import Danilova.models.UserAcnt;

import java.time.Duration;
import java.util.Objects;

public class CreateAccountPage extends BasePage {
    public static final String CREATE_ACCOUNT_PAGE_URL = "https://www.ae.com/us/en/myaccount/create-account";
    public static final String CREATE_ACCOUNT_PAGE_TITLE = "Create an Account";
    public static final String ACCOUNT_CREATED_URL = "https://www.ae.com/us/en/myaccount/real-rewards/account-summary";
    public static final String ACCOUNT_CREATED_MESSAGE = "Account created!";
    public By pageTitle = By.xpath("//h1[@class='page-header qa-page-header _page-header_190u4w']");
    public By accountCreated = By.xpath("//div[@class='alert-content']/h6");
    By emailInput = By.xpath("//input[@class='form-control form-input-login autofill-detect' and @placeholder='Email']");
    By firstNameInput = By.xpath("//input[@class='form-control form-input-firstname autofill-detect']");
    By lastNameInput = By.xpath("//input[@class='form-control form-input-lastname autofill-detect']");
    By mobileNumberInput = By.xpath("//input[@class='form-control form-control form-input-phone-number']");
    By passwordInput = By.xpath("//input[@class='form-control form-input-password autofill-detect' and @placeholder='Password']");
    By confirmPasswordInput = By.xpath("//input[@class='form-control form-input-confirm-password autofill-detect' and @placeholder='Confirm Password']");
    By zipCodeInput = By.xpath("//input[@class='form-control form-input-postal-code autofill-detect' and @placeholder='Zip Code']");
    By monthInput = By.xpath("//select[@class='form-control autofill-detect'  and @name='month']");
    By dayInput = By.xpath("//select[@class='form-control autofill-detect'  and @name='day']");
    By acceptCheckBox = By.cssSelector("div[data-test-checkbox='acceptTerms']");
    By createAccountButton = By.xpath("//button[@data-test-btn='submit']");

    public CreateAccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check is Create Account URL page")
    public boolean isCreateAccountPage(String url) {
        return CREATE_ACCOUNT_PAGE_URL.equals(url);
    }

    @Step("Get Create Account Title page")
    public String createAcntPgTitle() {
        return driver.findElement(pageTitle).getText();
    }

    @Step("Get email input locator")
    public By getEmailInput() {
        return emailInput;
    }

    @Step("input email input")
    public void sentEmail(String s) {
        driver.findElement(getEmailInput()).sendKeys(s);
    }

    @Step("Get first name input locator")
    public By getFirstNameInput() {
        return firstNameInput;
    }

    @Step("sent first name")
    public void sentFirstName(String s) {
        driver.findElement(getFirstNameInput()).sendKeys(s);
    }

    @Step("Get last name input locator")
    public By getLastNameInput() {
        return lastNameInput;
    }

    @Step("sent last name")
    public void sentLastName(String s) {
        driver.findElement(getLastNameInput()).sendKeys(s);
    }

    @Step("Get mobile number input")
    public By getMobileNumberInput() {
        return mobileNumberInput;
    }

    @Step("sent mobile number")
    public void sentMobileNumber(String s) {
        driver.findElement(getMobileNumberInput()).sendKeys(s);
    }

    @Step("Get password input locator")
    public By getPasswordInput() {
        return passwordInput;
    }

    @Step("sent password")
    public void sentPassword(String s) {
        driver.findElement(getPasswordInput()).sendKeys(s);
    }

    @Step("Get confirm password input locator")
    public By getConfirmPasswordInputInput() {
        return confirmPasswordInput;
    }

    @Step("sent confirm password")
    public void sentConfirmPasswordInput(String s) {
        driver.findElement(getConfirmPasswordInputInput()).sendKeys(s);
    }

    @Step("Get zip code input locator")
    public By getZipCodeInput() {
        return zipCodeInput;
    }

    @Step("sent zip code")
    public void sentZipCodeInput(String s) {
        driver.findElement(getZipCodeInput()).sendKeys(s);
    }


    @Step("Get month input locator")
    public By getMonthInput() {
        return monthInput;
    }

    @Step("sent month input")
    public void selectMonth(int month) {
        Select select = new Select(driver.findElement(getMonthInput()));
        select.selectByIndex(month);
    }

    @Step("Get day input locator")
    public By getDayInput() {
        return dayInput;
    }

    @Step("sent day")
    public void selectDay(int day) {
        Select select = new Select(driver.findElement(getDayInput()));
        select.selectByIndex(day);
    }

    @Step("click accept check box")
    public void clkAcceptCheckBox() {
        driver.findElement(acceptCheckBox).click();
    }

    @Step("accaunt created page is open")
    public boolean isAccountSuccsPage() {
        return Objects.equals(driver.getCurrentUrl(), ACCOUNT_CREATED_URL);
    }


    @Step("user created successfully message")
    public boolean isSucMessage() {
        return driver.findElement(accountCreated).getText().equals(ACCOUNT_CREATED_MESSAGE);
    }

    @Step("submit form")
    public void submitForm() {
        if (driver.findElement(createAccountButton).isEnabled()) {
            System.out.println("Create button is enabled");
            WebElement submitBtn = driver.findElement(createAccountButton);
            new Actions(driver)
                    .moveToElement(submitBtn)
                    .pause(Duration.ofMillis(5000))
                    .click()
                    .perform();
        } else System.out.println("Create button is disabled");


    }

    @Step("Fill form user success data {user}")
    public void fillForm(UserAcnt user) {


        sentEmail(user.getEmail());
        sentFirstName(user.getFirstName());
        sentLastName(user.getLastName());
        sentMobileNumber(user.getMobileNumber());
        sentPassword(user.getPassword());
        sentConfirmPasswordInput(user.getCnfPassword());
        sentZipCodeInput(user.getZipCode());

        // month/day можно тоже принимать из user
        selectMonth(user.getMonth());
        selectDay(user.getDay());
    }

}
