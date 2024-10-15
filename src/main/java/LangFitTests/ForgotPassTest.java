package LangFitTests;


import Hooks.EmailsHandlingResetPasswordFlow;
import Hooks.Setup;
import Pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ForgotPassTest extends Setup {




    @DataProvider(name="EmailOrusername")
    public Object[][] EmailOrUsername() {
        return new Object[][] {
               { "testpgrytsiuk@gmail.com" },
                { "<EMAIL>[0]"}

        };
    }

    @Test(priority = 4, dataProvider = "EmailOrusername")

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
            } else {
                throw new AssertionError("Neither success nor error message was displayed.");
            }
            

            try {
                ExecuteResetEmail.executeResetPasswordMail();

           } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
        }

    }

}
