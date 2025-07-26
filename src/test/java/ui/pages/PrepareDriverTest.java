package ui.pages;

import Danilova.PageObjects.HomePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import ui.extensions.AllureExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

@Getter
@Feature("preparing driver")
@ExtendWith(AllureExtension.class)

public class PrepareDriverTest {

    protected WebDriver driver;
    protected static HomePage home;


    @BeforeEach
    void setup() {
        try {
            initDriver();
            if (driver != null) {
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                home = new HomePage(driver); // Инициализируем только после создания драйвера
                //important hoverOver to load menu woman elements!
               // home.menu.hoverOverWomen();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
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
            }
        } catch (Exception e) {
            safeAddAttachment("Teardown error", "Error during teardown: " + e.getMessage());
        }
    }

    private void initDriver() {
        // Читаем из system properties (передаётся через -D)
        String remoteUrl = System.getProperty("selenium.remote.url");
        // Безопасное добавление вложения в Allure
        safeAddAttachment("Remote URL", remoteUrl != null ? remoteUrl : "Not specified");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            // Основные параметры для CI
           /* Headless=true (по умолчанию в CI):
            Быстрее выполнение, Меньше потребление ресурсов,  Не требует GUI
                 Headless=false:
            Для отладки через VNC (порт 7900)
            Когда нужны скриншоты/видео
            Для сложных UI-тестов с WebGL/Canvas **/


            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
            options.addArguments("--window-size=1920,1080"); // Установка явного размера окна, предпочтительнее для CI
            options.addArguments("--user-data-dir=/tmp/chrome_temp"); //без возникала 500 ошибка Could not start a new session. Response code 500. Message: session not created: probably user data directory is already in use, please specify a unique value for --user-data-dir argument, or don't use --user-data-dir


            // Обработка параметров из Gradle yaml
            String browserSize = System.getProperty("browser.size", "1920x1080");
            boolean startMaximized = Boolean.parseBoolean(System.getProperty("browser.start.maximized", "true"));

            if (startMaximized) {
                options.addArguments("--start-maximized");
            } else {
                options.addArguments("--window-size=" + browserSize);
            }


            // Для Selenium Grid
            options.setCapability("se:recordVideo", true);
            options.setCapability("se:screenResolution", "1920x1080");
            options.setCapability("se:timeZone", "UTC");

            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));

            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
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

//    private WebDriver initDriver() {
//        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
//
//        // Для CI всегда требуем remoteUrl
//        if (System.getenv("CI") != null && (remoteUrl == null || remoteUrl.isEmpty())) {
//            throw new IllegalStateException("SELENIUM_REMOTE_URL must be set in CI environment");
//        }
//
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
//        options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
//
//        if (remoteUrl != null && !remoteUrl.isEmpty()) {
//            try {
//                return new RemoteWebDriver(new URL(remoteUrl), options);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException("Invalid Selenium URL: " + remoteUrl, e);
//            }
//        }
//
//        // Локальный запуск (только для разработки)
//        return new ChromeDriver(options);
//    }

}
