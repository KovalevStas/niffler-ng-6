package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

public class RegisterWebTest {

    private static final Config CFG = Config.getInstance();

    Faker faker = new Faker();

    @Test
    void registerWithShortPassword() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register("bee", "12", "12")
                .assertPasswordErrorShow("Allowed password length should be from 3 to 12 characters")
                .assertPasswordSubmitErrorShow("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    void shouldRegisterNewUser() {
        String username = faker.name().username();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register(username, "123", "123").signInBtnClick();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        String username = faker.name().username();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register(username, "123", "123").signInBtnClick()
                .register()
                .register(username, "1234", "1234")
                .assertLoginErrorShow("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register(faker.name().username(), "123", "1234")
                .assertPasswordErrorShow("Passwords should be equal");
    }
}