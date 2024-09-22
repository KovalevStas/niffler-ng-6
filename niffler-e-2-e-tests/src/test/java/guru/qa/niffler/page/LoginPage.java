package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement registerButton = $("a[href='/register']");
    private final SelenideElement errorMessage = $(".form__error");

    public MainPage successLogin(String username, String password) {
        login(username, password);
        return new MainPage();
    }

    public void login(String username, String password) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.shouldBe(visible).setValue(password);
        submitButton.click();
    }

    public RegisterPage goToRegisterPage() {
        registerButton.click();
        return new RegisterPage();
    }

    public LoginPage assertLoginErrorShow(String errorText) {
        errorMessage.shouldHave(text(errorText));
        return new LoginPage();
    }

}
