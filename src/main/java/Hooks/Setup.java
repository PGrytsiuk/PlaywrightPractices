package Hooks;

import Utils.AllureEnvironmentWriter;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class Setup {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    protected String browserType;

    public Setup() {}

    public Setup(String browserType) {
        this.browserType = browserType;
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browserType) {
        this.browserType = browserType;
        try {
            if (playwright == null) {
                playwright = Playwright.create();
            }
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true);

            switch (browserType.toLowerCase()) {
                case "firefox":
                    browser = playwright.firefox().launch(options);
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(options);
                    break;
                case "chromium":
                default:
                    browser = playwright.chromium().launch(options);
            }

            // Write Allure environment information
            AllureEnvironmentWriter.writeEnvironment(playwright, browser);

            if (browser == null) {
                throw new RuntimeException("Browser initialization failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void createContextAndPage() {
        try {
            if (browser == null) {
                throw new RuntimeException("Browser instance is null in BeforeMethod. Ensure setUp was called correctly.");
            }
            context = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
            page = context.newPage();
            page.setViewportSize(1920, 1080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void closeContextAndAddScreenshotIfFail(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshotForPage(page, result.getName());
            }
            if (page != null) page.close();
            if (context != null) context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takeScreenshotForPage(Page page, String testName) {
        if (page != null) {
            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots", testName + ".png"))
                    .setFullPage(true));
            saveScreenshot(screenshotBytes, testName);
        }
    }

    @Attachment(value = "Screenshot of {testName}", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot, String testName) {
        return screenshot;
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        try {
            if (browser != null) {
                for (BrowserContext ctx : browser.contexts()) {
                    for (Page p : ctx.pages()) {
                        if (p != null) p.close();
                    }
                    ctx.close();
                }
                browser.close();
            }

            if (playwright != null) playwright.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}