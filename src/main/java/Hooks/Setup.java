package Hooks;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.nio.file.Paths;



public class Setup {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );


        this.page = browser.newPage();

        this.context = browser.newContext();
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        if (page != null && result.getStatus() == ITestResult.FAILURE) {
            // Take screenshot only if the test fails
            byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("target/screenshots", result.getName() + ".png")).setFullPage(true));
            // Attach the screenshot to the Allure report
            saveScreenshot(screenshotBytes, result.getName());

        }

        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Attachment(value = "Screenshot of {testName}", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot, String testName) {
        return screenShot;
    }

}