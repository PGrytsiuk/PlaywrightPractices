package com.langfit.data.web.components;

import com.langfit.data.web.pages.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Header extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(Header.class);

    private final Locator profileicon;
    private final Locator profileedit;
    private final Locator logout;
    private final Locator username;
    private final Locator logoutdialog;
    private final Locator dialogabort;
    private final Locator dialogConfirmlogout;

    public Header(Page page) {
        super(page);
        this.profileicon = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("lt-icon-arrow-down"));
        this.profileedit = page.getByLabel("edit profile");
        this.logout = page.getByLabel("logout");
        this.username = page.locator("//div[@class='user-name ng-binding']");
        this.logoutdialog = page.locator("//md-dialog[@role='dialog']");
        this.dialogabort = page.locator("//button[@ng-click='dialog.abort()']");
        this.dialogConfirmlogout = page.locator("//button[@ng-click='dialog.hide()']");

    }
    @Step("Tap on the profile icon")
    public Header tapTheProfileIcon() {
        logger.info("Tap on the profile icon");
        profileicon.click();
        return this;
    }

    @Step("Select the Profile option")
    public Header tapOnTheProfileOption() {
        logger.info("Select the Profile option");
        username.click();
        return this;
    }

    @Step("Verify if Username is placed inside drop down")
    public void verifyTheDropDownUsername(String expectedUsername) {
        logger.info("Verifying if username '{}' is placed on the account page", expectedUsername);
        assertThat(username).hasText(expectedUsername);
    }

    @Step("Tap on the logout")
    public Header doLogout() {
        logger.info("Tap on the logout");
        logout.click();
        return this;

    }

    @Step("Verify if logout dialog is shown")
    public boolean logoutDialogIsVisible() {
        logger.info("Verify if logout dialog is shown");
        return logoutdialog.isVisible();
    }

    @Step("Close the dialog by tapping on the No Option")
    public void closeDialog() {
        logger.info("Verify if user is able to close the Logout dialog");
        dialogabort.click();
    }

    @Step("Confirm logout")
    public void confirmLogout() {
        logger.info("Perform the click action on the Logout and wait for the navigation to the Login URL");
        dialogConfirmlogout.click();
        page.waitForURL("https://gym.langfit.net/login");
    }
}
