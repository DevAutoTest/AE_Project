package ui.pages;

import Danilova.PageObjects.HeaderPage;
import Danilova.PageObjects.HomePage;
import Danilova.PageObjects.SearchSideBarPage;
import io.qameta.allure.Feature;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.extensions.AllureExtension;

import java.time.Duration;

@Feature("preparing driver")
@ExtendWith(AllureExtension.class)

public class PrepareDriverTest {

    @Getter
    static WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));


    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    }
