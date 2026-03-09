package lesson06.testcases;

import lesson06.base.BaseUiTest;
import lesson06.pages.ShopHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShopHomePageTest extends BaseUiTest {

    @Test
    public void shouldOpenWebshopInNewTab() {
        ShopHomePage shopHomePage = new ShopHomePage();

        Assert.assertTrue(shopHomePage.isLoaded(), "Expected to open webshop index page.");
    }
}
