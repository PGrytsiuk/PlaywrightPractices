package LangFitTests;

import Fixture.Setup;
import Pages.LoginPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ForgotPassTest extends Setup {


    @DataProvider(name = "EmailOrusername")
    public Object[][] EmailOrUsername() {
        return new Object[][]{
                {"pgrytsiuk1992@gmail.com"},
                {"pavlo_grytsiuk"},
                {"<EMAIL>[0]"},
                {"Test"}

        };
    }

    @Test(priority = 4, dataProvider = "EmailOrusername")
    public void ForGotPassword(String usernameOrEmail) {
        try {
            /*   setupContextWithVideo("FORGOT_PASSWORD");*/
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);
            //Tap on the Forgot Password link
            loginPage.tapForgotPassword();
            //Verify the title
            loginPage.assertResetPasswordMessage("Reset Password");
            //Enter the username or email
            loginPage.enterUsernameOrEmail(usernameOrEmail);
            //Click the Send button
            loginPage.clickSend();

            // Check for either the success or error message
            if (loginPage.ErrorToastWhenResettingPassword()) {
                loginPage.assertPopupErrorMessage("Provided username or email address doesn't exist on the system");
            } else if (loginPage.ResetPasswordpopup()) {
                loginPage.assertPopupSuccessTitle("An email has been send to the provided email with further instructions");
            } else {
                throw new AssertionError("Neither success nor error message was displayed.");
            }

        } catch (Exception e) {
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
        }

    }

}
