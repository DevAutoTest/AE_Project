package Danilova.PageObjects;



import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RealRewardBannerComponent {

   WebDriver driver;
    private WebDriverWait wait;

    public RealRewardBannerComponent(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Возвращает SearchContext внутри Shadow DOM, или null, если хоста нет
     */
    private SearchContext getShadowRoot() {
        List<WebElement> hosts = driver.findElements(
                By.cssSelector("div.bloomreach-weblayer")
        );
        if (hosts.isEmpty()) {
            return null;
        }
        return hosts.get(0).getShadowRoot();  // <-- возвращает SearchContext
    }

    public boolean isBannerDisplayed() {
        SearchContext shadow = getShadowRoot();
        if (shadow == null) {
            return false;
        }
        List<WebElement> banners = shadow.findElements(
                By.cssSelector("div.bonus-offer-image")
        );
        return !banners.isEmpty() && banners.get(0).isDisplayed();
    }

    public void closeBanner() {
        SearchContext shadow = getShadowRoot();
        System.out.println("Closing reward bunner");
        if (shadow == null) {
            return;
        }
        List<WebElement> closeBtns = shadow.findElements(
                By.cssSelector("button.close")
        );
        if (closeBtns.isEmpty()) {
            return;
        }
        WebElement btn = closeBtns.get(0);
        btn.click();
        // подождать, пока баннер исчезнет
        wait.until(driver ->
                driver.findElements(By.cssSelector("div.bloomreach-weblayer div.bonus-offer-image"))
                        .isEmpty()
        );
    }
}
