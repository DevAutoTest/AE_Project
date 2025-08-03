package ui.pages;

import Danilova.PageObjects.HomePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import ui.extensions.AllureExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Feature("preparing driver")
@ExtendWith(AllureExtension.class)

public class PrepareDriverTest {

    protected WebDriver driver;
    protected HomePage home;


    @BeforeEach
    void setup() {
        try {
            initDriver();

            if (driver != null) {
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                home = new HomePage(driver); // Инициализируем только после создания драйвера

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                // Логируем размер и позицию окна
                safeAddAttachment("Actual window size", driver.manage().window().getSize().toString());
                safeAddAttachment("Window position", driver.manage().window().getPosition().toString());
            } else {
                safeAddAttachment("Driver initialization", "Driver initialization failed");
            }
        } catch (Exception e) {
            safeAddAttachment("Setup error", "Error during setup: " + e.getMessage());
            throw e;
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (driver != null) {

                driver.quit();
                driver = null;
                safeAddAttachment("Driver closed", "Driver successfully closed");
                safeAddAttachment("Window size", driver.manage().window().getSize().toString());
            }
        } catch (Exception e) {
            safeAddAttachment("Teardown error", "Error during teardown: " + e.getMessage());
        }
    }

    private void initDriver() {

        String remoteUrl = System.getProperty("selenium.remote.url");

        safeAddAttachment("Remote URL", remoteUrl != null ? remoteUrl : "Not specified");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            //options: for Docker CLI flags
            //CLI = Command-Line Interface — это способ взаимодействия с программой через командную строку (терминал), а не через графический интерфейс (GUI).
            ChromeOptions options = getChromeOptions();
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));

            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
                cleanShoppingBag();
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        int width = Integer.parseInt(System.getProperty("browser.width", "1920"));
        int height = Integer.parseInt(System.getProperty("browser.height", "1080"));
        options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
        options.addArguments("--no-sandbox"); // Отключает sandbox-режим (иначе Chrome в Docker может падать с ошибками).
        options.addArguments("--disable-dev-shm-usage"); // Использует /tmp вместо /dev/shm (избегает ошибок нехватки памяти в Docker)
        options.addArguments("--window-size=" + width + "," + height); // Установка явного размера окна, предпочтительнее для CI
        options.addArguments("--user-data-dir=/tmp/chrome_" + UUID.randomUUID());
        return options;
    }

    public void cleanShoppingBag() {
        driver.get("https://www.ae.com/us/en/cart");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            List<WebElement> removeButtons = driver.findElements(By.xpath("//button[contains(@aria-label,'Remove')]"));
            for (WebElement btn : removeButtons) {
                btn.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
        } catch (Exception e) {
            System.out.println("Nothing to remove in cart.");
        }
    }

    private void safeAddAttachment(String name, String content) {
        try {
            Allure.addAttachment(name, "text/plain",
                    content != null ? content : "No content provided",
                    StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            System.err.println("Failed to add Allure attachment: " + e.getMessage());
        }
    }
}
