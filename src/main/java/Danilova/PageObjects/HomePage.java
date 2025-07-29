package Danilova.PageObjects;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;


/**
 * Сюда пишем методы общие для сайта
 * + метод открытия главной страницы
 * + методы получения локаторов этой страницы
 * + редиректы с главной страницы куда то
 **/
@Getter
public class HomePage extends BasePage {
    public static final String HOME_PAGE_URL = "https://www.ae.com/us/en";
    public static final String AERIE_URL = "https://www.ae.com/us/en/c/aerie/cat4840006?pagetype=clp";

    public final HeaderComponent header;
    public final MenuComponent menu;
    public final FastMenuComponent fastMenu;
    public final TodayOffersComponent todayOffer;
    public final ShippingToBoxComponent shippingBox;
    public final DialogBoxComponent dialogBox;
    public final BonusOfferShadowRootComponent offerBox;
    public final RealRewardBannerComponent rewardBox;
    public final SignUpOfferBoxShadowRootComponent signUpBox;
    public final SpecialPromotionBoxShadowRootComponent promotion;
    public final CookieBannerComponent cookie;


    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
        this.menu = new MenuComponent(driver);
        this.fastMenu = new FastMenuComponent(driver);
        this.dialogBox = new DialogBoxComponent(driver);
        this.offerBox = new BonusOfferShadowRootComponent(driver);
        this.rewardBox = new RealRewardBannerComponent(driver);
        this.signUpBox = new SignUpOfferBoxShadowRootComponent(driver);
        this.todayOffer = new TodayOffersComponent(driver);
        this.shippingBox = new ShippingToBoxComponent(driver);
        this.promotion = new SpecialPromotionBoxShadowRootComponent(driver);
        this.cookie = new CookieBannerComponent(driver);
        open();
    }

    @Step("Get header component")
    public HeaderComponent header() {
        return header;
    }

    @Step("Open AMERICAN EAGLE by URL")
    public void open() {
        driver.get(HOME_PAGE_URL);
    }

    @Step("Open AERIE by URL")
    private void openAerie() {
        driver.get(AERIE_URL);
    }
}
