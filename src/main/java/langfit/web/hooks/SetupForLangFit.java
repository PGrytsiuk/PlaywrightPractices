package langfit.web.hooks;

import com.data.TestData;
import utils.AllureEnvironmentWriter;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class SetupForLangFit {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;
    protected String browserType;

    public SetupForLangFit(String browserType) {
        this.browserType = browserType;
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browserType) {
        this.browserType = browserType;

        // Check for Playwright initialization
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(500);

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
    }

    @BeforeMethod
    public void createContextAndPage() {
        // force reload config before each test
        TestData.reloadConfig();
        String baseURL = "https://gym.langfit.net/";
        context = browser.newContext(new Browser.NewContextOptions().setBaseURL(baseURL).setAcceptDownloads(true));
        page = context.newPage();
        page.setViewportSize(1920, 1080);
    }

    @AfterMethod
    public void closeContextAndAddScreenshotIfFail(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshotForPage(page, result.getName());
            }
        } finally {
            if (page != null) page.close();
            if (context != null) context.close();
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
        if (browser != null) {
            for (BrowserContext ctx : browser.contexts()) {
                for (Page p : ctx.pages()) {
                    if (p != null) p.close();
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