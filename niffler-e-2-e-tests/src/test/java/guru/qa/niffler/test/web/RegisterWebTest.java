package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

public class RegisterWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void registerWithShortPassword() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .goToRegisterPage()
                .fillRegisterPage("bee", "12", "12")
                .submit()
                .assertPasswordErrorShow("Allowed password length should be from 3 to 12 characters")
                .assertPasswordSubmitErrorShow("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    void shouldRegisterNewUser() {
        String username = RandomDataUtils.randomUsername();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .goToRegisterPage()
                .fillRegisterPage(username, "123", "123")
                .successSubmit();
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        String username = RandomDataUtils.randomUsername();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .goToRegisterPage()
                .fillRegisterPage(username, "123", "123")
                .successSubmit()
                .goToRegisterPage()
                .fillRegisterPage(username, "1234", "1234")
                .submit()
                .assertLoginErrorShow("Username `" + username + "` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .goToRegisterPage()
                .fillRegisterPage(RandomDataUtils.randomUsername(), "123", "1234")
                .submit()
                .assertPasswordErrorShow("Passwords should be equal");
    }
}