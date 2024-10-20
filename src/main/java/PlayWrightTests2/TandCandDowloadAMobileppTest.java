package PlayWrightTests2;

import Hooks.Setup;
import Pages.LoginPage;

import static Utils.ScreenshotsAndRecordings.setupContextWithVideo;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import Utils.ScreenshotsAndRecordings;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;



public class TandCandDowloadAMobileppTest extends Setup {

    @Test(priority = 2, enabled = false)
    void downloadMobileApp(){
            setupContextWithVideo(browser, "DOWNLOAD_MOBILE_APP");

        try {
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);
            //Tap on the Terms and conditions link
            Page TermsAndCondtion = page.context().waitForPage(loginPage::termsAndConditions);
            TermsAndCondtion.waitForLoadState();
            System.out.println(TermsAndCondtion.title());
            TermsAndCondtion.close();

            //Tap on the AppStore icon
            Page AppStorePage = page.context().waitForPage(loginPage::clickAppStoreRedirect);
            AppStorePage.waitForLoadState();
            System.out.println(AppStorePage.title());
            Locator LangFitIOSicon = AppStorePage.getByText("LangFit 4+");
            assertThat(LangFitIOSicon).hasText("LangFit 4+");

            ScreenshotsAndRecordings.ScreenshotCapture(AppStorePage, "DowloadAMobilepp_IOS ");

            //Close App Store tab
            AppStorePage.close();

            //Back to Langfit and tap on the Android icon
            Page PlayMarketPage = page.context().waitForPage(loginPage::clickPlayMarketRedirect);
            PlayMarketPage.waitForLoadState();
            System.out.println(PlayMarketPage.title());
            Locator LangFitAndroid = PlayMarketPage.locator("//span[text()='LangFit']");
            assertThat(LangFitAndroid).hasText("LangFit");

            ScreenshotsAndRecordings.ScreenshotCapture(PlayMarketPage, "DowloadAMobilepp_Android ");

            PlayMarketPage.close();


        } catch (Exception e){
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
            e.printStackTrace();
        }



    }


}
