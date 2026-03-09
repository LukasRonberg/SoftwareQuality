package lesson06.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public abstract class BaseUiTest {
    protected static final String SHOP_URL = "http://localhost:8080";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = Duration.ofSeconds(10).toMillis();
        Configuration.browserSize = "1920x1080";
        openShop();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    protected void openShop() {
        open(SHOP_URL);
    }

    protected SelenideElement elementByXpath(String xpath) {
        return $x(xpath);
    }
}
