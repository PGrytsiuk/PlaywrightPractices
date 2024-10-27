package Hooks;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.nio.file.Paths;

public class Setup {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    @BeforeSuite
    public void setUp() {
        try {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
        } catch (Exception e) {
            e.printStackTrace(); // This will help you see any exceptions thrown on the console.
        }
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage(); // Initialize the page
    }

    @AfterMethod
    public void closeContextAndAddScreenshotIfFail(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Take screenshot for the active page if the test fails
            takeScreenshotForPage(page, result.getName());
        }

        // Close all pages in the context
        for (Page p : context.pages()) {
            if (p != null) {
                p.close();
            }
        }

        // Then close the context
        if (context != null) context.close();
    }

    private void takeScreenshotForPage(Page page, String testName) {
        if (page != null) {
            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots", testName + ".png"))
                    .setFullPage(true)
            );
            // Attach the screenshot to the Allure report
            saveScreenshot(screenshotBytes, testName);
        }
    }

    @Attachment(value = "Screenshot of {testName}", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot, String testName) {
        return screenshot;
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        // Close all contexts and their pages
        if (browser != null) {
            for (BrowserContext ctx : browser.contexts()) {
                for (Page p : ctx.pages()) {
                    if (p != null) {
                        p.close();
                    }
                }
                ctx.close();
            }

            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}