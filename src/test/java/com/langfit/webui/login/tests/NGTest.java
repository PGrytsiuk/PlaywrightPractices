package com.langfit.webui.login.tests;

import com.langfit.data.web.hooks.SetupForLangFit;
import com.langfit.data.web.pages.LoginPage;
import com.utils.ScreenshotsAndRecordings;
import com.langfit.test.fixture.TestInitializer;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;

@Listeners(CustomListeners.class)
public class NGTest extends SetupForLangFit {

    public NGTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    @DataProvider(name = "Invalid users")
    public Object[][] InvalidUsers() {

        return new Object[][]{{"1pavlo_grytsiuk1233", "pgry2412123"},
                {"2pavlo_grytsiuk1235", "pgry24121223"},
                {"3pavlo_grytsiuk1247", "pgry2412143"}};
    }

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
    }

    @Test(priority = 4, dataProvider = "Invalid users")
    @Story("Invalid login(Duplicate)")
    @Description("This test case verify if user is not able to login with invalid credentials with different set of data")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidLogin(String login, String password) {
        BrowserContext context = ScreenshotsAndRecordings.VideoCapture(browser, "Invalid Login");

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page.navigate("/login");

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