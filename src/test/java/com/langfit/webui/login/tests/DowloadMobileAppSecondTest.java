package com.langfit.webui.login.tests;

import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.LoginPage;
import com.langfit.test.fixture.TestInitializer;
import com.microsoft.playwright.Page;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(CustomListeners.class)
public class DowloadMobileAppSecondTest extends SetupForLangFit {

    public DowloadMobileAppSecondTest(String browserType) {
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

    @Test(priority = 3, invocationCount = 2)
    @Story("Mobiles app pages")
    @Description("This test case verify if user is able to download the mobile app from App store and Play market")
    @Severity(SeverityLevel.MINOR)
    public void downloadMobileApp() {
            page.navigate("/login");

            // Open new tabs/windows by clicking buttons
            page.waitForPopup(new Page.WaitForPopupOptions().setPredicate(p -> p.context().pages().size() == 3), () -> {
                loginPage.clickAppStoreRedirect();
                loginPage.clickPlayMarketRedirect();
            });

            List<Page> pages = context.pages();
            for (Page tab : pages) {
                tab.waitForLoadState();
                System.out.println(tab.url());
            }
            // Switch to the iOS App Store page
            Page iOSAppsPage = pages.get(1);
            page = iOSAppsPage; // Set the current page to this tab for proper screenshot in case of failure
            System.out.println(iOSAppsPage.title());
            Assert.assertEquals(iOSAppsPage.textContent("h1"), "\n" +
                    "          LangFit\n" +
                    "            4+\n" +
                    "        ", "iOS App Store h1 tag mismatch");

            System.out.println(iOSAppsPage.textContent("h1"));

            // Switch to the Play Market page
            Page playMarketPage = pages.get(2);
            page = playMarketPage; // Set the current page to this tab for proper screenshot in case of failure
            System.out.println(playMarketPage.title());
            Assert.assertEquals(playMarketPage.title(), "LangFit on the Play market", "Play Market title mismatch");
            System.out.println(playMarketPage.textContent("h1"));
    }
}
