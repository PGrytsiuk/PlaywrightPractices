package com.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironment(Playwright playwright, Browser browser) {
        Properties properties = new Properties();
        properties.setProperty("os_platform", System.getProperty("os.name"));
        properties.setProperty("java_version", System.getProperty("java.version"));
        properties.setProperty("browser_version", getBrowserVersion(playwright, browser));

        try {
            // Ensure the target/allure-results directory exists.
            Path allureResultsPath = Paths.get("target/allure-results");
            Files.createDirectories(allureResultsPath);  // Create directory if it doesn't exist

            // Write the properties file
            try (FileOutputStream output = new FileOutputStream(allureResultsPath.resolve("environment.properties").toFile())) {
                properties.store(output, "Allure Environment Properties");
            }
        } catch (IOException e) {
            System.err.println("Failed to write to environment.properties: " + e.getMessage());
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
