package ui.pages;

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
import java.util.Objects;

@Getter
@Feature("preparing driver")
@ExtendWith(AllureExtension.class)

public class PrepareDriverTest {

    // Добавляем статический метод для доступа к драйверу
    //non static
    @Getter
    static WebDriver driver;


    @BeforeEach
    void setup() {
        try {
            initDriver();
            if (driver != null) {
                driver.manage().window().maximize();
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
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        // Безопасное добавление вложения в Allure
        safeAddAttachment("Remote URL", remoteUrl != null ? remoteUrl : "Not specified");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            // Основные параметры для CI
            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
            options.addArguments("--window-size=1920,1080"); // Установка размера окна

            // Для полноэкранного режима в контейнере
            options.addArguments("--start-maximized");
            // Дополнительные возможности
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            options.setCapability("se:recordVideo", true);

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
