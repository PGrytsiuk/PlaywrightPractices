package LangFitTests;

import Hooks.EmailsHandlingResetPasswordFlow;
import Hooks.Setup;
import Pages.LoginPage;
import Utils.TestInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
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

    @Test(priority = 1, dataProvider = "EmailOrusername", enabled = true)
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
