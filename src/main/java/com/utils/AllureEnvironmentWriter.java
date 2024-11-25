package com.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

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
        String browserVersion = null;

        switch (browserName.toLowerCase()) {
            case "chromium":
                browserVersion = playwright.chromium().launch().version();
                break;
            case "firefox":
                browserVersion = playwright.firefox().launch().version();
                break;
            case "webkit":
                browserVersion = playwright.webkit().launch().version();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        return browserName + " " + browserVersion;
    }

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            writeEnvironment(playwright, browser);
        }
    }
}
