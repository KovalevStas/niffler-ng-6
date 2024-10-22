package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

@ParametersAreNonnullByDefault
public class MainPage {

    private final SelenideElement header = $("#root header");
    private final SelenideElement headerMenu = $("ul[role='menu']");
    private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
    private final SelenideElement statComponent = $("#stat");
    private final SelenideElement spendingTable = $("#spendings");
    private final SelenideElement history = $(withText("History of Spendings")).parent();
    private final SelenideElement statistics = $(withText("Statistics")).parent();

    @Nonnull
    public FriendsPage friendsPage() {
        header.$("button").click();
        headerMenu.$$("li").find(text("Friends")).click();
        return new FriendsPage();
    }

    @Nonnull
    public PeoplePage allPeoplesPage() {
        header.$("button").click();
        headerMenu.$$("li").find(text("All People")).click();
        return new PeoplePage();
    }

    @Nonnull
    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$("td").get(5).click();
        return new EditSpendingPage();
    }

    public void checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
    }

    @Nonnull
    public MainPage checkThatPageLoaded() {
        statComponent.should(visible).shouldHave(text("Statistics"));
        spendingTable.should(visible).shouldHave(text("History of Spendings"));
        return this;
    }

    public ProfilePage goToProfile() {
        header.$("button").click();
        headerMenu.$$("li").find(text("Profile")).click();
        return new ProfilePage();
    }

    public MainPage checkHistory() {
        history.shouldBe(visible, Duration.ofSeconds(5));
        return new MainPage();
    }

    public MainPage checkStatistics() {
        statistics.shouldBe(visible, Duration.ofSeconds(5));
        return new MainPage();
    }
}
