package ui.pages;

import Danilova.PageObjects.FastShopPage;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.steps.AddItemsToBagSteps;

public class WomenNewArrivalsPageTests extends PrepareDriverTest {

    @Test
    @Tag("smoke")
    @Description("check choosing of 1 random item and accessibility Add item to bag")
    void chooseOneRandomItem() {
        AddItemsToBagSteps addItemStep = new AddItemsToBagSteps(driver);
        addItemStep.chooseRandomItemStep(home);

        FastShopPage dropPage = new FastShopPage(driver);

        Assertions.assertTrue(dropPage.addToBagButtonIsPresent());
    }
}
