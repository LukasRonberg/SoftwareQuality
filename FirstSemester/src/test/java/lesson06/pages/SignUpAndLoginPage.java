package lesson06.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class SignUpAndLoginPage {
    private final SelenideElement SIGN_UP =
            $(xpath("//a[contains(@title,'Sign up')]"));
    private final SelenideElement EMAIL =
            $(xpath("//input[@type='email']"));
    private final SelenideElement PASSWORD =
            $(xpath("//name[@type='password']"));
    private final SelenideElement PASSWORD_REPEAT =
            $(xpath("//name[@type='repeatPassword']"));


    public void clickSignUp(){
        SIGN_UP.click();
    }
    public void enterEmail(String text){
        EMAIL.sendKeys(text);
    }

    public void enterPasswordAndRepeatPassword(String password){
        PASSWORD.sendKeys(password);
        PASSWORD_REPEAT.sendKeys(password);
    }
}
