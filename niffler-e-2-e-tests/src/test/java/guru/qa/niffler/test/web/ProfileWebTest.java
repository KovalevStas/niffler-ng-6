package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProfileWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void createCategoryTest() {
        Configuration.browserSize = "1900x1600";
        String categoryName = "New Category7";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345").goToProfile().createCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        Assertions.assertTrue(profilePage.IsCategoryCreated(categoryName));
        profilePage.archiveCategory(categoryName);
    }

    @Test
    void archiveCategoryTest() {
        String categoryName = "New Category9";
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .login("duck", "12345").goToProfile().createCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        profilePage.archiveCategory(categoryName);
        Assertions.assertFalse(profilePage.IsCategoryCreated(categoryName));
    }
}
