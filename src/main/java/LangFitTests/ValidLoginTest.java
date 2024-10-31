package LangFitTests;

import Configs.ConfigLoader;
import Hooks.Setup;
import Pages.HomePage;
import Pages.LoginPage;
import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import static Utils.ScreenshotsAndRecordings.setupContextWithVideo;

public class ValidLoginTest extends Setup {

    @Test(priority = 5)
    @Story("Valid login")
    @Description("This test case verify if user is able to login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void validLoginTest() {

        setupContextWithVideo(browser,"VALID_LOGIN");

        ConfigLoader config = new ConfigLoader();
        String username = config.getProperty("Valid_username");
        String password = config.getProperty("latestPassword");

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
