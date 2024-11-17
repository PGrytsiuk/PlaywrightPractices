package com.langfit.webui.login.tests;

import com.langfit.data.TestData;
import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.LoginPage;
import com.langfit.test.fixture.TestInitializer;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListeners.class)
public class LogoTest extends SetupForLangFit {

    public LogoTest(String browserType) {
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

    @Test(priority = 4)
    @Story("Logo test")
    @Description("This test case verify that page is refreshed after tapping on Logo image")
    @Severity(SeverityLevel.MINOR)
    public void logoTest() {
        page.navigate("/login");

        // Verify if logo is present
        Assert.assertTrue(loginPage.logoIsPresent(), "Logo is visible");
        // Fill the username and password and tap on the Logo
        loginPage.pageIsRefreshedAfterTappingLogo(TestData.getValidUsername(), TestData.getLastPassword());
        // Assert that fields are refreshed
        Assert.assertTrue(loginPage.usernameIsEmpty() && loginPage.passwordIsEmpty(), "Username and Password fields are empty");
    }
}