package Utils;

import com.microsoft.playwright.*;

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

    public static String getBrowserName() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            return browser.browserType().name();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("browserName")) {
            System.out.println(getBrowserName());
        } else {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                writeEnvironment(browser);
            }
        }
    }
}