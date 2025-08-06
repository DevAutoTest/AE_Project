package ui.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import ui.pages.PrepareDriverTest;
import ui.steps.AllureSteps;

public class AllureExtension implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {

            Object testInstance = context.getRequiredTestInstance();
            if (testInstance instanceof PrepareDriverTest) {
                WebDriver driver = ((PrepareDriverTest) testInstance).getDriver();
                new AllureSteps(driver).screenshotSpoiler();
            }
        }
    }
}