package com.common.hooks;

import com.utils.AllureEnvironmentWriter;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class BasicSetup {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    @BeforeClass(alwaysRun = true)
    @Parameters("browserType")
    public void setUp(@Optional("chrome") String browserType) {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500);
        switch (browserType.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch((options).setChannel("chrome"));
                break;
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "edge":
                browser = playwright.chromium().launch((options).setChannel("msedge"));
                break;
            case "safari":
                browser = playwright.webkit().launch(options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        AllureEnvironmentWriter.writeEnvironment(playwright, browser);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeMethod
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setAcceptDownloads(true));
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
}
