package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement header = $("#root header");
    private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
    private final SelenideElement headerMenu = $("ul[role='menu']");
    private final SelenideElement history = $(withText("History of Spendings")).parent();
    private final SelenideElement statistics = $(withText("History of Statistics")).parent();

    public FriendsPage friendsPage() {
        header.$("button").click();
        headerMenu.$$("li").find(text("Friends")).click();
        return new FriendsPage();
    }

    public PeoplePage allPeoplesPage() {
        header.$("button").click();
        headerMenu.$$("li").find(text("All People")).click();
        return new PeoplePage();
    }

    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    public void checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
    }

    public ProfilePage goToProfile() {
        header.$("button").click();
        headerMenu.$$("li").find(text("Profile")).click();
        return new ProfilePage();
    }

    public MainPage checkStatistics() {
        statistics.shouldBe(visible, Duration.ofSeconds(5));
        return new MainPage();
    }

    public MainPage checkHistory() {
        history.shouldBe(visible, Duration.ofSeconds(5));
        return new MainPage();
    }
}
