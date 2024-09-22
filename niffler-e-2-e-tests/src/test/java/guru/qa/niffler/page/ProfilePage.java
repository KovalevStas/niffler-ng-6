package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {
    private final SelenideElement avatar = $(".MuiAvatar-root img");
    private final SelenideElement uploadImgBtn = $(".MuiBox-root [role='button']");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement nameInput = $("#name");
    private final SelenideElement saveChangesBtn = $("button[type='submit']");
    private final SelenideElement showArchivedBtn = $(".MuiSwitch-root");
    private final SelenideElement newCategoryInput = $("#category");
    private final ElementsCollection categories = $$(".MuiBox-root .MuiChip-root");
    private final SelenideElement dialogWindow = $(".MuiDialog-paper");
    private final SelenideElement dialogWindowCloseBtn = $(".MuiDialog-paper").$$("button").find(text("Close"));
    private final SelenideElement dialogWindowArchiveBtn = $(".MuiDialog-paper").$$("button").find(text("Archive"));
    private final SelenideElement dialogWindowUnarchiveBtn = $(".MuiDialog-paper").$$("button").find(text("Unarchive"));


    public boolean IsCategoryCreated(String categoryDescription) {
        return categories.find(text(categoryDescription)).has(visible, Duration.ofSeconds(5));
    }

    public ProfilePage changeName(String newName) {
        nameInput.setValue(newName);
        saveChangesBtn.click();
        return new ProfilePage();
    }

    public ProfilePage archiveCategory(String categoryName) {
        categories.find(text(categoryName)).parent().$$("button").get(1).click();
        dialogWindow.shouldBe(visible, Duration.ofSeconds(3));
        dialogWindowArchiveBtn.click();
        return new ProfilePage();
    }

    public ProfilePage unarchiveCategory(String categoryName) {
        categories.find(text(categoryName)).parent().$("button").click();
        dialogWindow.shouldBe(visible, Duration.ofSeconds(3));
        dialogWindowUnarchiveBtn.click();
        return new ProfilePage();
    }

    public ProfilePage createCategory(String categoryName) {
        newCategoryInput.setValue(categoryName).pressEnter();
        return new ProfilePage();
    }

    public ProfilePage editCategory(String categoryName, String newCategoryName) {
        categories.find(text(categoryName)).parent().$$("button").get(0).click();
        categories.find(text(categoryName)).$("input[value='" + categoryName + "']").setValue(newCategoryName).pressEnter();
        return new ProfilePage();
    }

    public ProfilePage clickShowArchivedBtn() {
        showArchivedBtn.click();
        return new ProfilePage();
    }
}
