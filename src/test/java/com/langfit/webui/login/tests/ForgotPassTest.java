package com.langfit.webui.login.tests;

import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.data.web.pages.LoginPage;
import com.langfit.tests.specific.hooks.EmailsHandlingResetPasswordFlow;
import com.langfit.test.fixture.TestInitializer;
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
public class ForgotPassTest extends SetupForLangfitBasic {

    @DataProvider(name="EmailOrusername")
    public Object[][] emailOrUsername() {
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

    @Test(priority = 8, dataProvider = "emailOrUsername", enabled = false)
    @Story("Forgot password")
    @Description("This test case verify if user is able to set up new password via reset password link")
    @Severity(SeverityLevel.CRITICAL)
    public void forgotPassword(String usernameOrEmail) throws Exception {
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
