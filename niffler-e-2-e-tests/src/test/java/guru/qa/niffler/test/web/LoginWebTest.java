package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

public class LoginWebTest {
    private static final Config CFG = Config.getInstance();

    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() {
        String username = RandomDataUtils.randomUsername();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doRegister()
                .fillRegisterPage(username, "123", "123")
                .successSubmit()
                .successLogin(username, "123")
                .checkHistory()
                .checkStatistics();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        String username = RandomDataUtils.randomUsername();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .doRegister()
                .fillRegisterPage(username, "123", "123").successSubmit().login(username, "1234");
        new LoginPage().checkError("Неверные учетные данные пользователя");
    }
}
