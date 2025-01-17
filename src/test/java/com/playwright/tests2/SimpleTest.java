package com.playwright.tests2;

import com.common.hooks.TestHookSetup;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

public class SimpleTest extends TestHookSetup {

    @Test
    public void testLogin() {
        // Use the initialized page object for automation
        page.navigate("https://www.google.com/");
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Пошук")).fill("testuser");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Пошук Google")).nth(0).click();
    }
}