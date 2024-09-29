package PlayWrightTests2;

import Pages.LoginPage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RecordVideoSize;
import java.nio.file.Paths;




public class InvalidLogin {


    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        // Create a new context with video recording
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(new RecordVideoSize(1280, 720))
        );
        Page page = browserContext.newPage();

        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);

        loginPage.login("pavlo_grytsiuk1233", "pgry2412123");
        // Verify the alert
        boolean Toast=loginPage.AlertAppear();
        loginPage.getToastMessageText("Invalid username or password");

        System.out.println("Toast message is present: " +Toast);
        System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());

            // Closing the page and context, which should save the video

        page.close();
        browserContext.close();
        browser.close();
        playwright.close();


    }

}
