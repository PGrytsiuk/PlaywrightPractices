package com.langfit.webui;

import langfit.web.hooks.SetupForLangFit;
import langfit.web.pages.LoginPage;
import com.langfit.testsspecifichooks.EmailsHandlingResetPasswordFlow;
import com.langfit.testdata.TestInitializer;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListeners.class)
public class ForgotPassTest extends SetupForLangFit {

    public ForgotPassTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    @DataProvider(name="EmailOrusername")
    public Object[][] EmailOrUsername() {
        return new Object[][] {
               { "testpgrytsiuk@gmail.com" },
                { "<EMAIL>[0]"}
        };
    }

    private LoginPage loginPage;
    private EmailsHandlingResetPasswordFlow ExecuteResetEmail;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
        ExecuteResetEmail = testInitializer.getEmailsHandlingResetPasswordFlow();
    }

    @Test(priority = 1, dataProvider = "EmailOrusername", enabled = false)
    @Story("Forgot password")
    @Description("This test case verify if user is able to set up new password via reset password link")
    @Severity(SeverityLevel.CRITICAL)
    public void ForgotPassword(String usernameOrEmail) throws Exception {
            page.navigate("/login");

            //Tap on the Forgot Password link
            loginPage.tapForgotPassword();
            //Verify the title
            loginPage.assertResetPasswordMessage("Reset Password");
            //Enter the username or email
            loginPage.enterUsernameOrEmail(usernameOrEmail);
            //Click the Send button
            loginPage.clickSend();

            // Check for either the success or error message
            if (loginPage.errorToastWhenResettingPassword()) {
                loginPage.assertPopupErrorMessage("Provided username or email address doesn't exist on the system");
            } else if (loginPage.resetPasswordpopup()) {
                loginPage.assertPopupSuccessTitle("An email has been send to the provided email with further instructions");
                    //Execute email reset password journey
                    ExecuteResetEmail.executeResetPasswordMail();
            } else {
                throw new AssertionError("Neither success nor error message was displayed.");
        }
    }
}
