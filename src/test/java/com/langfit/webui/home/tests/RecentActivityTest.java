package com.langfit.webui.home.tests;

import com.common.hooks.CustomListeners;
import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.data.web.pages.HomePage;
import com.langfit.test.fixture.TestInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(CustomListeners.class)
public class RecentActivityTest extends SetupForLangfitBasic {

    private HomePage homePage;

    @BeforeMethod
    @Parameters({"useCookies", "requireLogin"})
    public void setUpTest(@Optional("true") boolean useCookies, @Optional("true") boolean requireLogin) throws Exception {
        // Initialize context and page with the parameters
        createContextAndPage(useCookies, requireLogin);
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        homePage = testInitializer.getHomepage();
    }

    @Test(priority = 12)
    @Story("Recent activity test")
    @Description("This test case verifies if user is able to verify recent activity")
    @Severity(SeverityLevel.MINOR)
    public void recentActivityTest() {
        //Login to user account
        page.navigate("https://gym.langfit.net/");

        //verify recent activity section.
        Assert.assertTrue(homePage.verifyRecentActivitySection(), "Recent activity section is not visible");
        //Tap on the recent activity link
        homePage.clickRecentActivityLink(0);
        //Verify lesson materials dialog
        homePage.verifyLessonMaterialsDialog();
        //Close the dialog
        homePage.closeDialog();
        //Verify recent activity section functionality
        homePage.verifyRecentActivitySectionFunctionality();
    }
}
