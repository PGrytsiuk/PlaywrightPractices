package com.playwright.tests2;

import com.common.hooks.TestHookSetup;
import org.testng.annotations.Test;

public class SimpleTest extends TestHookSetup {

    @Test
    public void testLogin() {
        // Use the initialized page object for automation
        page.navigate("https://www.google.com/");
        page.fill("//textarea[@aria-controls='Alh6id']", "testuser");
        page.click("(//input[@class='gNO89b'])[1]");
    }
}