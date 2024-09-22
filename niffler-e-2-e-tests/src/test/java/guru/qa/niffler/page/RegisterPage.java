package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RegisterPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement usernameError = $$(".form__label").find(text("Username")).$(".form__error");
    private final SelenideElement passwordError = $$(".form__label").find(text("Password")).$(".form__error");
    private final SelenideElement passwordSubmitError = $$(".form__label").find(text("Password")).$(".form__error");
    private final SelenideElement passwordBtn = $("#passwordBtn");
    private final SelenideElement passwordSubmitBtn = $("#passwordSubmitBtn");
    private final SelenideElement signInBtn = $(withText("Sign in"));


    public RegisterPage fillRegisterPage(String username, String password, String passwordSubmit) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        passwordSubmitInput.setValue(passwordSubmit);
        return this;
    }

    public RegisterPage assertLoginErrorShow(String errorText) {
        usernameError.shouldHave(text(errorText));
        return this;
    }

    public RegisterPage assertPasswordErrorShow(String errorText) {
        passwordError.shouldHave(text(errorText));
        return this;
    }

    public RegisterPage assertPasswordSubmitErrorShow(String errorText) {
        passwordSubmitError.shouldHave(text(errorText));
        return this;
    }

    public LoginPage successSubmit() {
        submit();
        signInBtn.click();
        return new LoginPage();
    }

    public LoginPage signInBtnClick() {
        signInBtn.click();
        return new LoginPage();
    }

    public RegisterPage submit() {
        submitButton.click();
        return this;
    }
}
