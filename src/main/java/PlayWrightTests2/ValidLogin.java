package PlayWrightTests2;

import Pages.HomePage;
import Pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;


public class ValidLogin {



    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);
        HomePage homePage= new HomePage(page);
        loginPage.login("pavlo_grytsiuk", "pgry2412");
        // Verify the username on the home page
        homePage.verifyUserName("Pavlo Grytsiuk");

        page.close();
        browser.close();
        playwright.close();

    }



}
