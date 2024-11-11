package Utils;

import com.microsoft.playwright.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironment() {
        try (Playwright playwright = Playwright.create()) {
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(true);

            Browser browser = playwright.chromium().launch(options);

            Properties properties = new Properties();
            properties.setProperty("os_platform", System.getProperty("os.name"));
            properties.setProperty("java_version", System.getProperty("java.version"));
            properties.setProperty("browser_version", getBrowserVersion(playwright, browser));

            try (FileWriter writer = new FileWriter("allure-results/environment.properties")) {
                properties.store(writer, "Allure Environment Properties");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBrowserVersion(Playwright playwright, Browser browser) {
        String browserName = browser.browserType().name();
        String browserVersion = null;

        switch (browserName.toLowerCase()) {
            case "chromium":
                browserVersion = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true)).version();
                break;
            case "firefox":
                browserVersion = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true)).version();
                break;
            case "webkit":
                browserVersion = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true)).version();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return browserName + " " + browserVersion;
    }

    public static void main(String[] args) {
        writeEnvironment();
    }
}