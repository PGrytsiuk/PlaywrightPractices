package com.langfit.data.web.hooks;

import com.microsoft.playwright.*;
import org.testng.annotations.*;

public class TestHookSetup {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @Parameters("browserType") // Fetch the browserType parameter from testng.xml
    @BeforeClass
    public void setUp(@Optional("edge") String browserType) {
        // Initialize Playwright
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500);
        // Select browser type based on parameter
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

        // Create a browser context and page
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

