package playWrightTests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LunchingBrowser1 {

    public LunchingBrowser1(Page page) {
    }

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
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
        SignIn.click();
        Locator Host = page.locator("//*[contains (@class, 'user-name')]");
        /* Host.getByText("Pavlo Grytsiuk");*/
        assertThat(Host).hasText("Pavlo Grytsiuk");
        browser.close();


    }



}
