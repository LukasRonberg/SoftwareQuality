package lesson06.testcases;

import lesson06.base.BaseUiTest;
import lesson06.pages.ShopHomePage;
import lesson06.pages.SignUpAndLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

public class ShopHomePageTest extends BaseUiTest {

    @Test
    public void shouldOpenWebshopInNewTab() {
        ShopHomePage shopHomePage = new ShopHomePage();

        Assert.assertTrue(shopHomePage.isLoaded(), "Expected to open webshop index page.");
    }

    @Test
    public void happyPath() {
        SignUpAndLoginPage signUpAndLoginPage = new SignUpAndLoginPage();
        ShopHomePage shopHomePage = new ShopHomePage();
        UUID randomUUID = UUID.randomUUID();
        String uuidString = randomUUID.toString();

        signUpAndLoginPage.clickSignUp();
        signUpAndLoginPage.enterEmail("test" + uuidString + "@kea.dk");
        signUpAndLoginPage.enterPasswordAndRepeatPassword("Testing");

    }
}
