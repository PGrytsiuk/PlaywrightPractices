package LangFitTests;

import Hooks.Setup;
import Pages.LoginPage;
import Utils.ScreenshotsAndRecordings;
import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.nio.file.Paths;

@Listeners(Hooks.CustomListeners.class)
public class NGTest extends Setup {

    public NGTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    @DataProvider(name = "Invalid users")
    public Object[][] InvalidUsers() {

        return new Object[][]{{"1pavlo_grytsiuk1233", "pgry2412123"},
                {"2pavlo_grytsiuk1235", "pgry24121223"},
                {"3pavlo_grytsiuk1247", "pgry2412143"}};
    }

    @Test(priority = 3, dataProvider = "Invalid users")
    @Story("Invalid login(Duplicate)")
    @Description("This test case verify if user is not able to login with invalid credentials with different set of data")
    @Severity(SeverityLevel.CRITICAL)
    public void InvalidLogin(String login, String password) {
        BrowserContext context = ScreenshotsAndRecordings.VideoCapture(browser, "Invalid Login");

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);

        loginPage.login(login, password);
        // Verify the alert
        boolean Toast = loginPage.alertAppear();
        loginPage.getToastMessageText("Invalid username or password");

        System.out.println("Toast message is present: " + Toast);
        System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());
        ScreenshotsAndRecordings.ScreenshotCapture(page, "Invalid Login");
        // Closing the page and context, which should save the video

        // Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));

    }
}