package PlayWrightTests2;

import Pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;



public class InvalidLogin {


    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);

        loginPage.login("pavlo_grytsiuk1233", "pgry2412123");
        // Verify the alret
        boolean Tost=loginPage.AlertAppear();
        loginPage.getToastMessageText("Invalid username or password");

        System.out.println("Toast message is present: " +Tost);
        System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());

        page.close();
        browser.close();
        playwright.close();


    }

}
