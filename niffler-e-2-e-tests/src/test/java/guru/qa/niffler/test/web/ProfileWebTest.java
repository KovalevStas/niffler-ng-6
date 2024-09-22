package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.ProfilePage;
import org.junit.jupiter.api.Test;

public class ProfileWebTest {

    private static final Config CFG = Config.getInstance();
    Faker faker = new Faker();

    @Test
    void createCategoryTest() {
        Configuration.browserSize = "1900x1600";
        String categoryName = faker.bothify("??????????####");
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin("duck", "12345").goToProfile().createCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        profilePage.IsCategoryCreated(categoryName);
        profilePage.archiveCategory(categoryName);
    }

    @Test
    void archiveCategoryTest() {
        String categoryName = faker.bothify("??????????####");
        Selenide.open(CFG.frontUrl(), LoginPage.class)
                .successLogin("duck", "12345").goToProfile().createCategory(categoryName);
        ProfilePage profilePage = new ProfilePage();
        profilePage.archiveCategory(categoryName);
        profilePage.clickShowArchivedBtn();
        profilePage.IsCategoryCreated(categoryName);
    }
}
