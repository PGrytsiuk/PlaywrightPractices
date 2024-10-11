package LangFitTests;

import Configs.ConfigLoader;
import Fixture.Setup;
import Pages.HomePage;
import Pages.LoginPage;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ValidLoginTest extends Setup {

    @Test(priority = 1)
    public void validLoginTest() {

        setupContextWithVideo("VALID_LOGIN");

        ConfigLoader config = new ConfigLoader();
        String username = config.getProperty("Valid_username");
        String password = config.getProperty("Valid_password");

        page.navigate("https://gym.langfit.net/login");

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = "./snaps/Valid Login" + timestamp + ".png";
        //screenshots
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)));


        LoginPage loginPage = new LoginPage(page);
        HomePage homePage = new HomePage(page);
        loginPage.login(username, password);
        // Verify the username on the home page
        homePage.verifyUserName("Pavlo Grytsiuk");

        //full page screenshots
        String uuid = UUID.randomUUID().toString();
        String screenshotPathFullPage = "./snaps/ValidLogin" + uuid + ".png";
        page.screenshot(screenshotOptions.setFullPage(true).setPath(Paths.get(screenshotPathFullPage)));

    }

}
