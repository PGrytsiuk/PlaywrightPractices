package com.langfit.webui.login.tests;

import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.LoginPage;
import utils.ScreenshotsAndRecordings;
import com.langfit.test.fixture.TestInitializer;
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
public class ViewPortTest extends SetupForLangFit {

    public ViewPortTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
    }

    @DataProvider(name = "Invalid users")
    public Object[][] InvalidUsers() {

        return new Object[][]{{"1pavlo_grytsiuk1233", "pgry2412123"},
               };
    }

    @DataProvider(name ="View ports")
    public  Object[][] ViewPorts(){

        return new Object[][]{{1920, 1080},
                {2560, 1440},
                {30840, 2160}};
    }

    @DataProvider(name = "combinedData")
    public Object[][] combinedData() {
                Object[][] viewports = ViewPorts();
        Object[][] loginData = InvalidUsers();

        Object[][] combined = new Object[viewports.length * loginData.length][4];

        int index = 0;
        for (Object[] viewport : viewports) {
            for (Object[] login : loginData) {
                combined[index][0] = viewport[0];
                combined[index][1] = viewport[1];
                combined[index][2] = login[0];
                combined[index][3] = login[1];
                index++;
            }
        }

        return combined;
    }

    @Test(priority = 3, dataProvider = "combinedData")
    @Story("Test viewports")
    @Description("This test case verify if login page with different resolutions")
    @Severity(SeverityLevel.NORMAL)
    public void InvalidLogin(int width, int height, String login, String password) {

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page.navigate("/login");


        page.setViewportSize(width, height);
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