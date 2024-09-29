package playWrightTests;
import org.junit.Assert;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DefaultLoginTest {



    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
       page.navigate("https://gym.langfit.net/login");

        Locator username = page.locator("[name='username']");
        username.fill("pavlo_grytsiuk");
        Locator password = page.locator("[name='userPassword']");
        password.fill("pgry2412");
        Locator SignIn =page.locator("[aria-label='sign in']");
            //Field masking for screenshot
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotPath = "./snaps/scr" + timestamp + ".png";
            Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
            page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)).setFullPage(false).setMask(List.of(username)));


        SignIn.click();
        Locator Host = page.locator("//*[contains (@class, 'user-name')]");
        Host.getByText("Pavlo Grytsiuk");
        assertThat(Host).hasText("Pavlo Grytsiuk");
        String SomeText =page.locator("//*[contains (@class, 'lt-mobile-app-promo__title')]").textContent();
        System.out.println(SomeText);

        Locator profile = page.locator("//*[contains (@class, 'menu-button')]");
        profile.click();
        Locator EditProfile = page.locator("//*[contains (@aria-label, 'edit profile')]");
        EditProfile.click();

       page.navigate("https://gym.langfit.net/profile");
        Locator edit =page.locator("//*[contains (@md-svg-icon, 'lt-icon-edit')]");
        edit.click();
        String inputValue1 =page.locator("//*[contains (@name, 'firstName')]").inputValue();
        String Expected="Pavlo";
        Assert.assertEquals(inputValue1, Expected);

        page.locator("//*[contains (@name, 'firstName')]").clear();

        page.close();
        browser.close();


        }

    }

}
