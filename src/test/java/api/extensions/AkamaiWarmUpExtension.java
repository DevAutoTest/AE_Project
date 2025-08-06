package api.extensions;

import api.utils.AkamaiCookieHolder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Opens ae.com in two steps by real-browser,
 * wait until Akamai JS-sensor put cookie and save them in {@link AkamaiCookieHolder}.
 */
public final class AkamaiWarmUpExtension implements BeforeAllCallback {

    private static final String ROOT = "https://www.ae.com/";
    private static final String STOREFRONT = ROOT + "us/en/";
    private static final int MAX_MS = 5_000;
    private static final int STEP_MS = 300;
    private static final String RX_NAME = "_abck|bm_sz|ak_bmsc|bm_mi";

    @Override
    public void beforeAll(ExtensionContext ctx) throws Exception {
        WebDriverManager.chromedriver().setup();

        ChromeOptions opt = new ChromeOptions();
        opt.addArguments(
                "--headless=new",
                "--disable-gpu",
                "--window-size=1920,1080"
        );
        opt.setPageLoadTimeout(Duration.ofSeconds(60));

        ChromeDriver drv = new ChromeDriver(opt);
        try {
            // 1. GET STOREFRONT:_abck + bm_sz
            drv.get(STOREFRONT);

            // 2. return GET on the same STOREFRONT — Akamai JS-sensor will add ak_bmsc + bm_mi
            drv.get(STOREFRONT);

            Map<String, String> akamai = waitForCookies(drv);
            if (akamai.size() < 4) {
                System.err.printf(
                        "⚠ %d ms get %d of 4 Akamai-cookie: %s%n",
                        MAX_MS, akamai.size(), akamai);
            }

            AkamaiCookieHolder.set(akamai);
            System.out.println("Akamai cookies → " + akamai);
        } finally {
            drv.quit();
        }
    }

    /**
     *Waiting displaying  Akamai-cookie, ask every STEP_MS, but not max MAX_MS.
     */
    private static Map<String, String> waitForCookies(ChromeDriver drv)
            throws InterruptedException {

        long deadline = System.currentTimeMillis() + MAX_MS;
        Map<String, String> curr;

        do {
            curr = drv.manage().getCookies().stream()
                    .filter(c -> c.getName().matches(RX_NAME))
                    .collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
            if (curr.size() == 4) {
                break;
            }
            Thread.sleep(STEP_MS);
        } while (System.currentTimeMillis() < deadline);

        return curr;
    }
}