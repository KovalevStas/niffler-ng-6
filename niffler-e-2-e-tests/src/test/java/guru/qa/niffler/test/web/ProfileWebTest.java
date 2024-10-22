package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.Test;

public class ProfileWebTest {

    private static final Config CFG = Config.getInstance();

    @Test
    void createCategoryTest() {
        Configuration.browserSize = "1900x1600";
        String categoryName = RandomDataUtils.randomCategoryName();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin("duck", "12345").goToProfile().addCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        profilePage.checkCategoryExists(categoryName);
        profilePage.archiveCategory(categoryName);
    }

    @Test
    void archiveCategoryTest() {
        String categoryName = RandomDataUtils.randomCategoryName();
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin("duck", "12345").goToProfile().addCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        profilePage.archiveCategory(categoryName);
        profilePage.clickShowArchivedBtn();
        profilePage.checkCategoryExists(categoryName);
    }
}
