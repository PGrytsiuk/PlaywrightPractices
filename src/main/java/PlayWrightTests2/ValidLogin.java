package PlayWrightTests2;


import Pages.HomePage;
import Pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ValidLogin {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new LaunchOptions().setHeadless(false)
        );


            Page page = browser.newPage();
            page.navigate("https://gym.langfit.net/login");

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotPath = "./snaps/scr" + timestamp + ".png";
            //screenshots
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)));


            LoginPage loginPage = new LoginPage(page);
            HomePage homePage = new HomePage(page);
            loginPage.login("pavlo_grytsiuk", "pgry2412");
            // Verify the username on the home page
            homePage.verifyUserName("Pavlo Grytsiuk");

            //full page screenshots
            String uuid = UUID.randomUUID().toString();
            String screenshotPathFullPage = "./snaps/scr" + uuid + ".png";
            page.screenshot(screenshotOptions.setFullPage(true).setPath(Paths.get(screenshotPathFullPage)));

            page.close();
            browser.close();
            playwright.close();



    }



}
