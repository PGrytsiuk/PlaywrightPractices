package LangFitTests;

import Hooks.SetupForLoggedUser;
import Pages.HomePage;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(Hooks.CustomListeners.class)
public class HomePageNavMenuTest extends SetupForLoggedUser {

    public HomePageNavMenuTest(String browserType){
        super(browserType);
    }

    @Test(priority = 5)
    @Story("Home Page Navigation menu test")
    @Description("This test case verifies if user to check left Navigation menu options")
    @Severity(SeverityLevel.MINOR)
    public void NavMenuTest(){
            page.navigate("https://gym.langfit.net/");

            //RandomCheck
            Locator navMenuLocator = page.locator("(//div[contains(@class,'lt-join-lesson-button ng-isolate-scope')]//button)[1]");
            assertThat(navMenuLocator).isVisible();
            HomePage homePage = new HomePage(page);
            homePage.verifyUserName("Pavlo Grytsiuk");
    }
}
