package playWrightTests;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class AxREsultsTest {

    @Test
        // 2
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() throws Exception {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate("https://gym.langfit.net/login"); // 3

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze(); // 4

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations()); // 5
    }

    @Test(enabled = false)
    void navigationMenuFlyoutShouldNotHaveAutomaticallyDetectableAccessibilityViolations() throws Exception {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate("https://gym.langfit.net/login");

        page.locator("section").click();

        // It is important to waitFor() the page to be in the desired
        // state *before* running analyze(). Otherwise, axe might not
        // find all the elements your test expects it to scan.
        page.locator("(//md-option[contains(@class,'option ng-scope')])//div[@class='md-text ng-binding']").waitFor();

        AxeResults accessibilityScanResults = new AxeBuilder(page)
                .include(Arrays.asList("(//md-option[contains(@class,'option ng-scope')])//div[@class='md-text ng-binding']"))
                .analyze();

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());
    }
}
