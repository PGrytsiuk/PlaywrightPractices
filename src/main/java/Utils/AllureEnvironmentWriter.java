package Utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironment(Browser browser) {
        Properties properties = new Properties();
        properties.setProperty("os_platform", System.getProperty("os.name"));
        properties.setProperty("java_version", System.getProperty("java.version"));
        properties.setProperty("browser_version", getBrowserVersion(browser));

        try (FileWriter writer = new FileWriter("allure-results/environment.properties")) {
            properties.store(writer, "Allure Environment Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getBrowserVersion(Browser browser) {
        return browser.browserType().name() + " " + browser.version();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "browserName":
                    System.out.println(getBrowserName());
                    break;
                case "version":
                    System.out.println(getBrowserVersion(getSingleBrowserInstance()));
                    break;
                default:
                    System.out.println("Unknown argument: " + args[0]);
            }
        } else {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                writeEnvironment(browser);
            }
        }
    }

    private static Browser getSingleBrowserInstance() {
        try (Playwright playwright = Playwright.create()) {
            return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        }
    }

    public static String getBrowserName() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            return browser.browserType().name();
        }
    }
}