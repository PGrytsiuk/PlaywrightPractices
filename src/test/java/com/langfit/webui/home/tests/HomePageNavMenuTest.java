package com.langfit.webui.home.tests;

import com.langfit.data.web.components.NavMenu;
import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.test.fixture.TestInitializer;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(CustomListeners.class)
public class HomePageNavMenuTest extends SetupForLangfitBasic {

    private NavMenu leftnavMenu;

    @BeforeMethod
    @Parameters({"useCookies", "requireLogin"})
    public void setUpTest(@Optional("true") boolean useCookies, @Optional("true") boolean requireLogin) throws Exception {
        // Initialize context and page with the parameters
        createContextAndPage(useCookies, requireLogin);
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the left navigation menu object
        leftnavMenu = testInitializer.getLeftnavMenu();
    }

    @Test(priority = 10, invocationCount = 3)
    @Story("Home Page Navigation menu test")
    @Description("This test case verifies if user to check left Navigation menu options")
    @Severity(SeverityLevel.NORMAL)
    public void NavMenuTest() {
        //Login to user account
        page.navigate("https://gym.langfit.net/");

        String[] expectedUrls = new String[] {
                "https://gym.langfit.net/",
                "https://gym.langfit.net/homeworks",
                "https://gym.langfit.net/lessons_history",
                "https://gym.langfit.net/settings",
                "https://gym.langfit.net/help"
        };
        //Verify if left navigation menu is visible
        Assert.assertTrue(leftnavMenu.leftMenuIsPresent(), "Navigation menu is visible");
        //verify the amount of available options for the left navigation menu
        Assert.assertEquals(leftnavMenu.leftNavMenuOptionsAmountSize(), 5);
        //Verify if all navigation menu components are visible
        Assert.assertTrue(leftnavMenu.verifyLeftNavigationMenuComponents(), "All navigation menu components are visible");
        //Verify left menu navigation functionality by tapping on each option
        leftnavMenu.leftNavigationMenuFunctionalityChecking(expectedUrls);
    }
}