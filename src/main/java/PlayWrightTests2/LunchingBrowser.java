package PlayWrightTests2;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LunchingBrowser {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
        new LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();
        page.navigate("https://gym.langfit.net/login");

    }



}
