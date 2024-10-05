package TestNGTests;

import Pages.LoginPage;
import Utils.ScreenshotsAndRecordings;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;


public class TestNGtest {

    @Test(priority = 3, enabled = false)
    public void InvalidLogin(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        BrowserContext context = ScreenshotsAndRecordings.VideoCapture(browser, "Invalid Login");
         Page page = context.newPage();
            page.navigate("https://gym.langfit.net/login");

            LoginPage loginPage = new LoginPage(page);

            loginPage.login("pavlo_grytsiuk1233", "pgry2412123");
            // Verify the alert
            boolean Toast = loginPage.AlertAppear();
            loginPage.getToastMessageText("Invalid username or password");

            System.out.println("Toast message is present: " + Toast);
            System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());
            ScreenshotsAndRecordings.ScreenshotCapture(page, "Invalid Login");
            // Closing the page and context, which should save the video




        playwright.close();


    }

}
