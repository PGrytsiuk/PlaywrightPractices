package LangFitTests;

import Hooks.Setup;
import Pages.LoginPage;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class DowloadMobileAppSecondTest extends Setup {

    @Test(priority = 3)
    void downloadMobileApp(){

        try {
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);
            page.waitForPopup(new Page.WaitForPopupOptions().setPredicate(p->p.context().pages().size()==3), ()->{
                loginPage.clickPlayMarketRedirect();
                loginPage.clickAppStoreRedirect();

            });


            List<Page> pages = page.context().pages();
                for(Page tabs : pages) {
                    tabs.waitForLoadState();
                    System.out.println(tabs.url());
                }


                Page IOSAppsPage = pages.get(1);
                System.out.println(IOSAppsPage.title());
                Assert.assertEquals(IOSAppsPage.title(), "\u200ELangFit on the App Store");
                System.out.println(IOSAppsPage.textContent("h1"));




                Page PlayMarketPage = pages.get(2);
                System.out.println(PlayMarketPage.title());
                Assert.assertEquals(PlayMarketPage.title(), "LangFit on the Play Market");
                System.out.println(PlayMarketPage.textContent("h1"));






        } catch (Exception e){
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
            e.printStackTrace();
        }



    }


}
