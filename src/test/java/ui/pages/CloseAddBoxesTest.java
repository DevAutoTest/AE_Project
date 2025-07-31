package ui.pages;

import Danilova.PageObjects.HomePage;

import java.util.List;
import java.util.function.Consumer;

public class CloseAddBoxesTest {
    void closeAdds(HomePage home) {

        // Список проверок и действий в виде Consumer<HomePage>
        List<Consumer<HomePage>> closePopupActions = List.of(
                h -> {
                    if (h.todayOffer.todayBoxIsPresent()) h.todayOffer.closeTodayBox();
                },
                h -> {
                    if (h.signUpBox.signUpIsPresent()) h.signUpBox.closeSignUpBox();
                },
                h -> {
                    if (h.shippingBox.shippingBoxIsPresent()) h.shippingBox.closeShippingBox();
                },
                h -> {
                    if (h.dialogBox.isPresent()) h.dialogBox.closeDialogBox();
                },
                h -> {
                    if (h.offerBox.isPresent()) h.offerBox.closeOfferBox();
                },
                h -> {
                    if (h.rewardBox.isRealRewardPresent()) h.rewardBox.closeRewardBox();
                },
                h -> {
                    if (h.promotion.specPromIsPresent()) h.promotion.closeSpecialPromBox();
                }
        );
        closePopupActions.forEach(action -> action.accept(home));
    }
}
