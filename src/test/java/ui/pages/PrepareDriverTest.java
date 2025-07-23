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
                Allure.addAttachment("Driver initialization", "Failed to initialize driver");
            }
        } catch (Exception e) {
            Allure.addAttachment("Setup error", e.getMessage());
            throw e;
        }
    }

    @AfterEach
    void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            Allure.addAttachment("Teardown error", e.getMessage());
        }
    }

    private void initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        // Безопасное добавление вложения в Allure
        Allure.addAttachment("remote", Objects.requireNonNullElse(remoteUrl, "SELENIUM_REMOTE_URL not set"));
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Add headless mode
            options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
            options.addArguments("--no-sandbox"); // Switch off sandbox to prevent access rights issues
            options.addArguments("--disable-dev-shm-usage"); // Use /tmp instead of /dev/shm
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
