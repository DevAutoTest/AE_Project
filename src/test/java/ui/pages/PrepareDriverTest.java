package ui.pages;

import Danilova.PageObjects.HomePage;
import Danilova.PageObjects.ShoppingBagPage;
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

@Getter
@Feature("preparing driver")
@ExtendWith(AllureExtension.class)

public class PrepareDriverTest {

    protected WebDriver driver;
    //Должно быть не статическое, чтобы сбрасывалась сессия браузера и корзины перед новыми тестами
    protected HomePage home;


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
        // Читаем из system properties (передаётся через -D)
        String remoteUrl = System.getProperty("selenium.remote.url");
        // Безопасное добавление вложения в Allure
        safeAddAttachment("Remote URL", remoteUrl != null ? remoteUrl : "Not specified");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            //options: применяется к флагам Docker CLI
            //CLI = Command-Line Interface — это способ взаимодействия с программой через командную строку (терминал), а не через графический интерфейс (GUI).
            ChromeOptions options = getChromeOptions();
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));

            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
                clearShoppingBag();
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
        }

    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        // Чтение размеров из системных свойств (с значениями по умолчанию)
        int width = Integer.parseInt(System.getProperty("browser.width", "1920"));
        int height = Integer.parseInt(System.getProperty("browser.height", "1080"));
        // Основные параметры для CI
           /* Headless=true (по умолчанию в CI):
            Быстрее выполнение, Меньше потребление ресурсов,  Не требует GUI
                 Headless=false:
            Для отладки через VNC (порт 7900)
            Когда нужны скриншоты/видео
            Для сложных UI-тестов с WebGL/Canvas **/


        // options.addArguments("--headless");  // Запуск браузера без GUI (экономит ресурсы в CI). Но: если вам нужны видео/скриншоты, уберите.
        options.addArguments("--disable-gpu"); // Switch off GPU, because we don't need it in headless mode
        options.addArguments("--no-sandbox"); // Отключает sandbox-режим (иначе Chrome в Docker может падать с ошибками).
        options.addArguments("--disable-dev-shm-usage"); // Использует /tmp вместо /dev/shm (избегает ошибок нехватки памяти в Docker)
        options.addArguments("--window-size=" + width + "," + height); // Установка явного размера окна, предпочтительнее для CI
        options.addArguments("--user-data-dir=/tmp/chrome_temp"); //без возникала 500 ошибка Could not start a new session. Response code 500. Message: session not created: probably user data directory is already in use, please specify a unique value for --user-data-dir argument, or don't use --user-data-dir
        return options;
    }

    public void clearShoppingBag() {
        driver.get("https://www.ae.com/us/en/cart");
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
