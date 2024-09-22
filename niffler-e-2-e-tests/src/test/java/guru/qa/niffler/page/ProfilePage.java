package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {
    private final SelenideElement avatar = $(".MuiAvatar-root img");
    private final SelenideElement uploadImgBtn = $(".MuiBox-root [role='button']");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement photoInput = $("input[type='file']");
    private final SelenideElement saveChangesBtn = $("button[type='submit']");
    private final SelenideElement archivedSwitcher = $(".MuiSwitch-root");
    private final SelenideElement newCategoryInput = $("#category");
    private final ElementsCollection categories = $$(".MuiBox-root .MuiChip-root");
    private final SelenideElement dialogWindow = $(".MuiDialog-paper");
    private final SelenideElement dialogWindowCloseBtn = $(".MuiDialog-paper").$$("button").find(text("Close"));
    private final SelenideElement dialogWindowArchiveBtn = $(".MuiDialog-paper").$$("button").find(text("Archive"));
    private final SelenideElement dialogWindowUnarchiveBtn = $(".MuiDialog-paper").$$("button").find(text("Unarchive"));

    public ProfilePage uploadPhotoFromClasspath(String path) {
        photoInput.uploadFromClasspath(path);
        return this;
    }

    public ProfilePage IsCategoryCreated(String categoryDescription) {
        categories.find(text(categoryDescription)).shouldBe(visible, Duration.ofSeconds(5));
        return this;
    }

    public ProfilePage changeName(String newName) {
        nameInput.clear();
        nameInput.setValue(newName);
        return this;
    }

    public ProfilePage archiveCategory(String categoryName) {
        categories.find(text(categoryName)).parent().$$("button").get(1).click();
        dialogWindow.shouldBe(visible, Duration.ofSeconds(3));
        dialogWindowArchiveBtn.click();
        return this;
    }

    public ProfilePage unarchiveCategory(String categoryName) {
        categories.find(text(categoryName)).parent().$("button").click();
        dialogWindow.shouldBe(visible, Duration.ofSeconds(3));
        dialogWindowUnarchiveBtn.click();
        return this;
    }

    public ProfilePage createCategory(String categoryName) {
        newCategoryInput.setValue(categoryName).pressEnter();
        return this;
    }

    public ProfilePage editCategory(String categoryName, String newCategoryName) {
        categories.find(text(categoryName)).parent().$$("button").get(0).click();
        categories.find(text(categoryName)).$("input[value='" + categoryName + "']").setValue(newCategoryName).pressEnter();
        return this;
    }

    public ProfilePage clickShowArchivedBtn() {
        archivedSwitcher.click();
        return this;
    }

    public ProfilePage checkUsername(String username) {
        this.usernameInput.should(value(username));
        return this;
    }

    public ProfilePage checkName(String name) {
        nameInput.shouldHave(value(name));
        return this;
    }

    public ProfilePage checkPhotoExist() {
        avatar.should(attributeMatching("src", "data:image.*"));
        return this;
    }

    public ProfilePage checkThatCategoryInputDisabled() {
        newCategoryInput.should(disabled);
        return this;
    }

    public ProfilePage submitProfile() {
        saveChangesBtn.click();
        return this;
    }
}
