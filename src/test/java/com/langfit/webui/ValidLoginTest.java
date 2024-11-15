package com.langfit.webui;

import com.data.TestData;
import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.HomePage;
import langfit.web.pages.LoginPage;
import com.langfit.testdata.TestInitializer;
import com.microsoft.playwright.Page;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static utils.ScreenshotsAndRecordings.setupContextWithVideo;

@Listeners(CustomListeners.class)
public class ValidLoginTest extends SetupForLangFit {

    public ValidLoginTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
        homePage = testInitializer.getHomepage();
    }

    @Test(priority = 5)
    @Story("Valid login")
    @Description("This test case verify if user is able to login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void validLoginTest() {
        setupContextWithVideo(browser,"VALID_LOGIN");
        page.navigate("/login");

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = "./snaps/Valid Login" + timestamp + ".png";
        //screenshots
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)));

        loginPage.login(TestData.getValidUsername(), TestData.getLastPassword());
        // Verify the username on the home page
        homePage.verifyUserName("Pavlo Grytsiuk");

        //full page screenshots
        String uuid = UUID.randomUUID().toString();
        String screenshotPathFullPage = "./snaps/ValidLogin" + uuid + ".png";
        page.screenshot(screenshotOptions.setFullPage(true).setPath(Paths.get(screenshotPathFullPage)));
    }
}