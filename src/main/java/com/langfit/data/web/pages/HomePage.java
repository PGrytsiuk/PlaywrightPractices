package com.langfit.data.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private final Locator hostLabel;
    private final Locator joinTheLesson;


    public HomePage(Page page) {
        super(page);
        this.hostLabel = page.locator("//*[contains (@class, 'user-name')]");
        this.joinTheLesson = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Join the lesson"));

    }

    @Step("Verify if Username is placed on the account page")
    public void verifyUserName(String expectedUsername) {
        logger.info("Verifying if username '{}' is placed on the account page", expectedUsername);
        assertThat(hostLabel).hasText(expectedUsername);
    }
}
