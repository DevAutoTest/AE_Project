package ui.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import ui.steps.AllureSteps;

public class AllureExtension implements AfterTestExecutionCallback {
    AllureSteps allureSteps = new AllureSteps();
    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) AllureSteps.screenshotSpoiler();
    }
}