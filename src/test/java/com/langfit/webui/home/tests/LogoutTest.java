package com.langfit.webui.home.tests;

import com.common.hooks.CustomListeners;
import com.langfit.data.web.components.Header;
import com.langfit.data.web.hooks.SetupForLoggedUserOnLangfit;
import com.langfit.test.fixture.TestInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListeners.class)
public class LogoutTest extends SetupForLoggedUserOnLangfit {

    public LogoutTest(String browserType) {
        super(browserType);
    }

    private Header header;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the left navigation menu object
        header = testInitializer.getHeader();
    }

    @Test(priority = 1)
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
