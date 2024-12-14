package com.langfit.data.web.hooks;

import com.langfit.data.TestData;
import com.langfit.data.web.pages.LoginPage;
import com.utils.AllureEnvironmentWriter;
import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class SetupForLangfitBasic {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private Path storageStatePath;

    @BeforeClass(alwaysRun = true)
    @Parameters("browserType")
    public void setUp(@Optional("chrome") String browserType) {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true).setSlowMo(500);
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
    @Parameters({"useCookies", "requireLogin"})
    public void createContextAndPage(@Optional("false") boolean useCookies, @Optional("false") boolean requireLogin) throws Exception {
        if (requireLogin) {
            createContextAndPageWithLogin(useCookies);
        } else {
            createContextAndPageWithoutLogin();
        }
    }

    private void createContextAndPageWithLogin(boolean useCookies) throws Exception {
        storageStatePath = Paths.get("target", "states", UUID.randomUUID() + ".json");
        Files.createDirectories(storageStatePath.getParent());

        if (useCookies && Files.exists(storageStatePath)) {
            System.out.println("Loading existing storage state: " + storageStatePath);
            context = browser.newContext(
                    new Browser.NewContextOptions().setStorageStatePath(storageStatePath)
            );
        } else {
            System.out.println("State file not found or not using cookies.");
            context = browser.newContext();
            page = context.newPage();
            System.out.println("Logging in to generate new state.");
            loginAndSaveState();
            context.close(); // Close the context after saving the state

            // Open a new context with the saved state if using cookies
            if (useCookies) {
                context = browser.newContext(
                        new Browser.NewContextOptions().setStorageStatePath(storageStatePath)
                );
            }
        }
        page = context.newPage();
        page.setViewportSize(1920, 1080);
    }

    private void createContextAndPageWithoutLogin() {
        TestData.reloadConfig();
        String baseURL = "https://gym.langfit.net/";
        context = browser.newContext(new Browser.NewContextOptions().setBaseURL(baseURL).setAcceptDownloads(true));
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

        if (storageStatePath != null) {
            try {
                Files.deleteIfExists(storageStatePath);
            } catch (Exception e) {
                System.err.println("Error deleting state file: " + e.getMessage());
            }
        }
    }

    private void loginAndSaveState() throws IOException {
        TestData.reloadConfig();
        page.navigate("https://gym.langfit.net/login");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login(TestData.getValidUsername(), TestData.getLastPassword());
        context.storageState(new BrowserContext.StorageStateOptions().setPath(storageStatePath));
        if (!Files.exists(storageStatePath) || Files.size(storageStatePath) == 0) {
            throw new RuntimeException("Failed to save valid storage state at: " + storageStatePath);
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