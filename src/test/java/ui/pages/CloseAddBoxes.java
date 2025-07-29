package ui.pages;

import Danilova.PageObjects.HomePage;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.function.Consumer;

public class CloseAddBoxes extends PrepareDriverTest {

    static void closeAdds() {
        // Список проверок и действий в виде Consumer<HomePage>
        List<Consumer<HomePage>> closePopupActions = List.of(
                home -> {
                    if (home.todayOffer.todayBoxIsPresent()) home.todayOffer.closeTodayBox();
                },
                home -> {
                    if (home.signUpBox.signUpIsPresent()) home.signUpBox.closeSignUpBox();
                },
                home -> {
                    if (home.shippingBox.shippingBoxIsPresent()) home.shippingBox.closeShippingBox();
                },
                home -> {
                    if (home.dialogBox.isPresent()) home.dialogBox.closeDialogBox();
                },
                home -> {
                    if (home.offerBox.isPresent()) home.offerBox.closeOfferBox();
                },
                home -> {
                    if (home.rewardBox.isRealRewardPresent()) home.rewardBox.closeRewardBox();
                },
                home -> {
                    if (home.promotion.specPromIsPresent()) home.promotion.closeSpecialPromBox();
                }
        );
        closePopupActions.forEach(action -> action.accept(home));
    }
}
