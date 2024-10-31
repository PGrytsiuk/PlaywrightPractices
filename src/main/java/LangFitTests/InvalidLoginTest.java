package LangFitTests;

import Configs.ConfigLoader;
import Hooks.Setup;
import Pages.LoginPage;
import Utils.ScreenshotsAndRecordings;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import static Utils.ScreenshotsAndRecordings.setupContextWithVideo;

public class InvalidLoginTest extends Setup {
    @Test(priority = 2)
    @Story("Invalid login")
    @Description("This test case verify if user is not able to login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    void InvalidLoginCredentials(){
        try {
             setupContextWithVideo(browser, "INVALID_LOGIN");
             ConfigLoader config = new ConfigLoader();
             String username = config.getProperty("Invalid_username");
             String password = config.getProperty("Invalid_password");

             page.navigate("https://gym.langfit.net/login");

             LoginPage loginPage = new LoginPage(page);
             //Verify if error messages are visible

              loginPage.emptyLogin();
              int errorMessages = loginPage.errorMessageSize();
              if(errorMessages > 0){
                  System.out.println(" Error messages count " + errorMessages);

              }
              loginPage.assertErrorMessagesCount();
              //Enter invalid credentials
              loginPage.login(username,password);
              // Verify the alert
              boolean Toast = loginPage.alertAppear();
              loginPage.getToastMessageText("Invalid username or password");

            System.out.println("Toast message is present: " + Toast);
            System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());

            ScreenshotsAndRecordings.ScreenshotCapture(page, "Invalid Login");
        }catch (Exception e){
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

