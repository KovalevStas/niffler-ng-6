package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

public class RegisterWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void registerWithShortPassword() {
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register("bee", "12", "12")
                .assertPasswordErrorShow("Allowed password length should be from 3 to 12 characters")
                .assertPasswordSubmitErrorShow("Allowed password length should be from 3 to 12 characters");
    }
}
