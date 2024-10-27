package LangFitTests;

import Hooks.Setup;
import Pages.LoginPage;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class DowloadMobileAppSecondTest extends Setup {

    @Test(priority = 3)
    public void downloadMobileApp() {
        try {
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);

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
            Assert.assertEquals(iOSAppsPage.title(), "\u200ELangFit on the App Store", "iOS App Store title mismatch");
            System.out.println(iOSAppsPage.textContent("h1"));

            // Switch to the Play Market page
            Page playMarketPage = pages.get(2);
            page = playMarketPage; // Set the current page to this tab for proper screenshot in case of failure
            System.out.println(playMarketPage.title());
            Assert.assertEquals(playMarketPage.title(), "LangFit on the Play Market", "Play Market title mismatch");
            System.out.println(playMarketPage.textContent("h1"));

        } catch (Exception e) {
            System.err.println("An error occurred during the downloadMobileApp test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
