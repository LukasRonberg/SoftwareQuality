package lesson06.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.xpath;

public class ShopHomePage {
    private static final String PAGE_NAME = "index.html";
    private final static SelenideElement PAGE_TITLE =
            $(xpath("//h1"));

    public boolean isLoaded() {
        System.out.println("wait");
        return PAGE_TITLE.isDisplayed();
    }

    public SelenideElement elementByXpath(String xpath) {
        return $x(xpath);
    }

    public ElementsCollection elementsByXpath(String xpath) {
        return $$x(xpath);
    }
}
