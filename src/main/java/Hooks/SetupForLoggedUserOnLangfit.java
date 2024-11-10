package Hooks;

import Configs.ConfigLoader;
import Data.TestData;
import Pages.LoginPage;
import Utils.AllureEnvironmentWriter;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.nio.file.Paths;

public class SetupForLoggedUserOnLangfit {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    protected String browserType;

    public SetupForLoggedUserOnLangfit() {}

    public SetupForLoggedUserOnLangfit(String browserType) {
        this.browserType = browserType;
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browserType) {
        this.browserType = browserType;
        initPlaywright();
        browser = launchBrowser();
        AllureEnvironmentWriter.writeEnvironment(playwright, browser);

        context = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
        page = context.newPage();

        ConfigLoader config = new ConfigLoader();
        String username = config.getProperty("Valid_username");
        String password = config.getProperty("latestPassword");

        loginAndSaveState(username, password);
    }

    private void initPlaywright() {
        playwright = Playwright.create();
    }

    private Browser launchBrowser() {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true);

        return switch (browserType.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }

    private void loginAndSaveState(String username, String password) {
        // force reload config before each test
        TestData.reloadConfig();
        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);
        loginPage.login(username, password);

        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));
        context.close();
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext(
                new Browser.NewContextOptions().setStorageStatePath(Paths.get("state.json"))
        );
        page = context.newPage();
        page.setViewportSize(1920, 1080);
    }

    @AfterMethod
    public void closeContextAndAddScreenshotIfFail(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshotForPage(page, result.getName());
        }
        if (page != null) page.close();
        if (context != null) context.close();
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