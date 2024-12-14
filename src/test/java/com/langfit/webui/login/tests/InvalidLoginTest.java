package com.langfit.webui.login.tests;

import com.langfit.data.TestData;
import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.data.web.pages.LoginPage;
import com.utils.ScreenshotsAndRecordings;
import com.langfit.test.fixture.TestInitializer;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.utils.ScreenshotsAndRecordings.setupContextWithVideo;

@Listeners(CustomListeners.class)
public class InvalidLoginTest extends SetupForLangfitBasic {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
    }

    @Test(priority = 5)
    @Story("Invalid login")
    @Description("This test case verify if user is not able to login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void invalidLoginCredentials(){
             setupContextWithVideo(browser, "INVALID_LOGIN");
             page.navigate("/login");

             //Verify if error messages are visible
              loginPage.emptyLogin();
              int errorMessages = loginPage.errorMessageSize();
              if(errorMessages > 0){
                  System.out.println(" Error messages count " + errorMessages);
              }
              loginPage.assertErrorMessagesCount();
              //Enter invalid credentials
              loginPage.login(TestData.getInvalidUsername(), TestData.getInvalidPassword());
              // Verify the alert
              boolean Toast = loginPage.alertAppear();
              loginPage.getToastMessageText("Invalid username or password");

            System.out.println("Toast message is present: " + Toast);
            System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());

            ScreenshotsAndRecordings.ScreenshotCapture(page, "Invalid Login");
    }
}

