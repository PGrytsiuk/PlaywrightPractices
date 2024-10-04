package TestNGTests;

import Configs.ConfigLoader;
import Fixture.Setup;
import Pages.LoginPage;
import Utils.ScreenshotsAndRecordings;
import org.testng.annotations.Test;



public class TestNGtest1 extends Setup {
    @Test(priority = 1)
        void InvalidLoginCredentials(){
            try {
                setupContextWithVideo("TestNGtest3");
                ConfigLoader config = new ConfigLoader();
                String username = config.getProperty("Invalid_username");
                String password = config.getProperty("Invalid_password");

                page.navigate("https://gym.langfit.net/login");

                LoginPage loginPage = new LoginPage(page);
                //Entering invalid credentials
                loginPage.login(username,password);
                // Verify the alert
                boolean Toast = loginPage.AlertAppear();
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

