package Utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironment(Playwright playwright, Browser browser) {
        Properties properties = new Properties();
        properties.setProperty("os_platform", System.getProperty("os.name"));
        properties.setProperty("java_version", System.getProperty("java.version"));
        properties.setProperty("browser_version", getBrowserVersion(playwright, browser));

        try (FileWriter writer = new FileWriter("allure-results/environment.properties")) {
            properties.store(writer, "Allure Environment Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getBrowserVersion(Playwright playwright, Browser browser) {
        String browserName = browser.browserType().name();
        String browserVersion = switch (browserName.toLowerCase()) {
            case "chromium" -> playwright.chromium().launch(new LaunchOptions().setHeadless(true)).version();
            case "firefox" -> playwright.firefox().launch(new LaunchOptions().setHeadless(true)).version();
            case "webkit" -> playwright.webkit().launch(new LaunchOptions().setHeadless(true)).version();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };

        return browserName + " " + browserVersion;
    }

    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(true));
        writeEnvironment(playwright, browser);
    }
}