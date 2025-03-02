package com.playwright.tests2;

import com.langfit.data.web.pages.LoginPage;
import com.utils.ScreenshotsAndRecordings;
import com.microsoft.playwright.*;

public class InvalidLogin {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        // Create a new context with video recording
        BrowserContext context = ScreenshotsAndRecordings.VideoCapture(browser, "Invalid Login");
        Page page= context.newPage();

        page.navigate("https://gym.langfit.net/login");

        LoginPage loginPage = new LoginPage(page);

        loginPage.login("pavlo_grytsiuk1233", "pgry2412123");
        // Verify the alert
        boolean Toast=loginPage.alertAppear();
        loginPage.getToastMessageText("Invalid username or password");

        System.out.println("Toast message is present: " +Toast);
        System.out.println(page.locator("//*[contains (@class, 'toast-message')]").textContent());
        ScreenshotsAndRecordings.ScreenshotCapture(page, "Invalid Login");
            // Closing the page and context, which should save the video

        page.close();
        context.close();
        browser.close();
        playwright.close();

    }
}
