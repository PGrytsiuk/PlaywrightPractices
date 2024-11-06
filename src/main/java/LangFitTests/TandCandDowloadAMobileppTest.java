package LangFitTests;

import Hooks.Setup;
import Pages.LoginPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Hooks.CustomListeners.class)
public class TandCandDowloadAMobileppTest extends Setup {

    public TandCandDowloadAMobileppTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    @Test(priority = 2)
    @Story("Mobiles app pages(Duplicate, different realization)")
    @Description("This test case verify if user is able to download the mobile app from App store and Play market(Duplicate of DownloadMobileAppSecondTest)")
    @Severity(SeverityLevel.MINOR)
    public void downloadMobileApps(){

        try {
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);
            //Tap on the Terms and conditions link
            Download download = page.waitForDownload(loginPage::termsAndConditions);
            Assert.assertNotNull(download, "Download object is null");
            System.out.println("Download path: " + download.path());
/*
            TermsAndCondtion.close();
*/

            //Tap on the App Store icon
            Page AppStorePage = page.context().waitForPage(loginPage::clickAppStoreRedirect);
            AppStorePage.waitForLoadState();
            System.out.println(AppStorePage.title());
            Locator LangFitIOSicon = AppStorePage.getByText("LangFit 4+");
            assertThat(LangFitIOSicon).hasText("LangFit 4+");

            //Close App Store tab
/*
            AppStorePage.close();
*/

            //Back to Langfit and tap on the Android icon
            Page PlayMarketPage = page.context().waitForPage(loginPage::clickPlayMarketRedirect);
            PlayMarketPage.waitForLoadState();
            System.out.println(PlayMarketPage.title());
            Assert.assertEquals(PlayMarketPage.title(), "LangFit on the Play Market");
            Locator LangFitAndroid = PlayMarketPage.locator("//span[text()='LangFit']");
            assertThat(LangFitAndroid).hasText("LangFit");
//
/*
            PlayMarketPage.close();
*/

        } catch (Exception e){
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
