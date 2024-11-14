package com.langfit.webui;

import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.LoginPage;
import com.langfit.testdata.TestInitializer;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
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

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(CustomListeners.class)
public class TandCandDowloadAMobileppTest extends SetupForLangFit {

    public TandCandDowloadAMobileppTest(String browserType) {
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

    @Test(priority = 2)
    @Story("Mobiles app pages(Duplicate, different realization)")
    @Description("This test case verify if user is able to download the mobile app from App Store and Play Market (Duplicate of DownloadMobileAppSecondTest)")
    @Severity(SeverityLevel.MINOR)
    public void downloadMobileApps(){
        // Navigate to the login page
        page.navigate("/login");

        // Tap on the Terms and Conditions link and verify download
        Download download = page.waitForDownload(loginPage::termsAndConditions);
        Assert.assertNotNull(download, "Download object is null");
        System.out.println("Download path: " + download.path());

        // Ensure the download path is logged properly
        System.out.println("Download path: " + download.path());

        // Verify App Store redirect
        verifyAppStorePage();

        // Verify Play Market redirect
        verifyPlayMarketPage();
    }

    private void verifyAppStorePage() {
        Page appStorePage = page.context().waitForPage(loginPage::clickAppStoreRedirect);
        appStorePage.waitForLoadState();
        System.out.println("App Store Page Title: " + appStorePage.title());

        Locator langFitIOSIcon = appStorePage.getByText("LangFit 4+");
        assertThat(langFitIOSIcon).hasText("LangFit 4+");
    }

    private void verifyPlayMarketPage() {
        Page playMarketPage = page.context().waitForPage(loginPage::clickPlayMarketRedirect);
        playMarketPage.waitForLoadState();
        System.out.println("Play Market Page Title: " + playMarketPage.title());

        Assert.assertEquals(playMarketPage.title(), "LangFit on the Play Market");
        Locator langFitAndroid = playMarketPage.locator("//span[text()='LangFit']");
        assertThat(langFitAndroid).hasText("LangFit");
    }
}