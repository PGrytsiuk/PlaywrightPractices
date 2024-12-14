package com.langfit.webui.home.tests;

import com.common.hooks.CustomListeners;
import com.langfit.data.web.components.Header;
import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.test.fixture.TestInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(CustomListeners.class)
public class LogoutTest extends SetupForLangfitBasic {

    private Header header;

    @BeforeMethod
    @Parameters({"useCookies", "requireLogin"})
    public void setUpTest(@Optional("true") boolean useCookies, @Optional("true") boolean requireLogin) throws Exception {
        // Initialize context and page with the parameters
        createContextAndPage(useCookies, requireLogin);
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the left navigation menu object
        header = testInitializer.getHeader();
    }

    @Test(priority = 11)
    @Story("Logout test")
    @Description("This test case verifies if is able to logout from his account")
    @Severity(SeverityLevel.CRITICAL)
    public void logoutTest() {
        //Login to user account
        page.navigate("https://gym.langfit.net/");

        //Tap on the profile icon
        header.tapTheProfileIcon();
        //verify the Username
        header.verifyTheDropDownUsername("Pavlo Grytsiuk");
        //Select logout
        header.doLogout();
        //Logout dialog is shown
        Assert.assertTrue(header.logoutDialogIsVisible(), "verify if message is shown after tapping Logout option");
        //Tap No option
        header.closeDialog();
        //Dialog is closed
        Assert.assertFalse(header.logoutDialogIsVisible(), "verify if message is not shown after closing dialog");
        //Open dialog again
        header.tapTheProfileIcon()
                .doLogout();
        //Confirm logout
        header.confirmLogout();
        //User is moved to the login page
        Assert.assertEquals(page.url(), "https://gym.langfit.net/login");
    }
}
