package LangFitTests;

import Hooks.EmailsHandlingResetPasswordFlow;
import Hooks.Setup;
import Pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Hooks.CustomListeners.class)
public class ForgotPassTest extends Setup {

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

    @Test(priority = 1, dataProvider = "EmailOrusername")
    @Story("Forgot password")
    @Description("This test case verify if user is able to set up new password via reset password link")
    @Severity(SeverityLevel.CRITICAL)
    public void ForgotPassword(String usernameOrEmail) {
        try {
            /*   setupContextWithVideo("FORGOT_PASSWORD");*/
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);
            EmailsHandlingResetPasswordFlow ExecuteResetEmail = new EmailsHandlingResetPasswordFlow(page);

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
                try {
                    //Execute email reset password journey
                    ExecuteResetEmail.executeResetPasswordMail();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new AssertionError("Neither success nor error message was displayed.");
            }

        } catch (Exception e) {

            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());

        }
    }
}
