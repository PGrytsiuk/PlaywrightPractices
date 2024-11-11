package LangFitTests;

import Hooks.SetupForLoggedUserOnLangfit;
import Pages.HomePage;
import Utils.TestInitializer;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(Hooks.CustomListeners.class)
public class HomePageNavMenuTest extends SetupForLoggedUserOnLangfit {

    public HomePageNavMenuTest(String browserType) {
        super(browserType);
    }

    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the HomePage object
        homePage = testInitializer.getHomepage();
    }

    @Test(priority = 6)
    @Story("Home Page Navigation menu test")
    @Description("This test case verifies if user to check left Navigation menu options")
    @Severity(SeverityLevel.MINOR)
    public void NavMenuTest() {
        page.navigate("https://gym.langfit.net/");

        // Random check
        Locator navMenuLocator = page.locator("(//div[contains(@class,'lt-join-lesson-button ng-isolate-scope')]//button)[1]");
        assertThat(navMenuLocator).isVisible();
        homePage.verifyUserName("Pavlo Grytsiuk");
    }
}