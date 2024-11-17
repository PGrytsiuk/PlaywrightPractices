package com.playwright.tests2;

import com.common.configs.ConfigLoader;
import langfit.web.pages.HomePage;
import langfit.web.pages.LoginPage;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ValidLoginTraceViewFailingDemo {

    Playwright pw;
    Browser browser;
    BrowserContext context;

    @Test
    public void traceViewerFailingTestDemo() {

        pw = Playwright.create();
        browser = pw.chromium().launch(
                new LaunchOptions().setHeadless(true)
        );
        context = browser.newContext();
        ConfigLoader config = new ConfigLoader();
        String username = config.getProperty("Valid_username");
        String password = config.getProperty("Valid_password");

        //Start tracing before creating / navigating page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        Page page = context.newPage();
        page.navigate("https://gym.langfit.net/login");

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = "./snaps/scr" + timestamp + ".png";
        //screenshots
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)));

        LoginPage loginPage = new LoginPage(page);
        HomePage homePage = new HomePage(page);
        loginPage.login(username,  password );
        // Verify the username on the home page
        homePage.verifyUserName("Pavlo Grytsiuk");

        //full page screenshots
        String uuid = UUID.randomUUID().toString();
        String screenshotPathFullPage = "./snaps/scr" + uuid + ".png";
        page.screenshot(screenshotOptions.setFullPage(true).setPath(Paths.get(screenshotPathFullPage)));
    }

    @AfterMethod
    public void cleanup(){
        //Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        browser.close();
        pw.close();
    }
}
