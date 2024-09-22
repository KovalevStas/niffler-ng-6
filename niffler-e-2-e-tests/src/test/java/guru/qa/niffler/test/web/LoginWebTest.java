package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;

public class LoginWebTest {
    private static final Config CFG = Config.getInstance();
    Faker faker = new Faker();

    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() {
        String username = faker.name().username();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register(username, "123", "123").signInBtnClick().login(username, "123").checkHistory().checkStatistics();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        String username = faker.name().username();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .register()
                .register(username, "123", "123").signInBtnClick().login(username, "1234");
        new LoginPage().assertLoginErrorShow("Неверные учетные данные пользователя");
    }
}
