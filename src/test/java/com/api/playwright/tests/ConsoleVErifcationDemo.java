package com.api.playwright.tests;

import com.common.hooks.BasicSetup;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConsoleVErifcationDemo extends BasicSetup {

    @Test
    public void handlingConsoleMessages() {

        page.onConsoleMessage(msg -> {
            System.out.println("Console message found: \n" +msg.type() + ": " + msg.text());
        });

        page.onConsoleMessage(msg -> {
            if("error".equals(msg.type())) {
                System.out.println("Error text: " + msg.text());
                Assert.fail("Error found. Failing the test");
            }
        });

        page.navigate("https://github.com/PGrytsiukk");
    }
}
