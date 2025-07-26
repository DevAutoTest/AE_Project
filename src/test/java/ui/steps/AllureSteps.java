package ui.steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ui.pages.PrepareDriverTest;

import java.io.ByteArrayInputStream;

public class AllureSteps {
    private final WebDriver driver;

    // Конструктор, принимающий драйвер
    public AllureSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Capture screenshot (spoiler)")
    public void screenshotSpoiler() {
        Allure.addAttachment("Screenshot",
                new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }
}
