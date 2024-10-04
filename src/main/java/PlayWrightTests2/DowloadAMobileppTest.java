package PlayWrightTests2;

import Fixture.Setup;
import Pages.LoginPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;



public class DowloadAMobileppTest  extends Setup {

    @Test(priority = 2)
    void downloadMobileApp(){
        try {
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);

            //Tap on the AppStore icon
            Page AppStorePage = page.context().waitForPage(loginPage::clickAppStoreRedirect);
            AppStorePage.waitForLoadState();
            System.out.println(AppStorePage.title());
            Locator LangFitIOS = AppStorePage.getByText("LangFit 4+");
            assertThat(LangFitIOS).hasText("LangFit 4+");
            //Close App Store tab
            AppStorePage.close();

            //Back to Langfit and tap on the Android icon
            Page PlayMarketPage = page.context().waitForPage(loginPage::clickPlayMarketRedirect);
            PlayMarketPage.waitForLoadState();
            System.out.println(PlayMarketPage.title());
            Locator LangFitAndroid = PlayMarketPage.locator("//span[text()='LangFit']");
            assertThat(LangFitAndroid).hasText("LangFit");
            PlayMarketPage.close();


        } catch (Exception e){
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
            e.printStackTrace();
        }



    }


}
