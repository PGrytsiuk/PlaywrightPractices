package com.langfit.data.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class ResetPassword extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ResetPassword.class);

    private final Locator NewPasswordTitle;
    private final Locator NewPasswordInput;
    private final Locator ConfirmPasswordInput;
    private final Locator SendButton;
    private final Locator SuccessToast;
    private final Locator SuccessToastMessage;

    public ResetPassword(Page page) {
        super(page);
        this.NewPasswordTitle = page.locator("//h2[normalize-space(text())='New password']");
        this.NewPasswordInput = page.locator("//input[@aria-label='new password']");
        this.ConfirmPasswordInput = page.locator("//input[@aria-label='confirm password']");
        this.SendButton = page.locator("//button[@aria-label='send']");
        this.SuccessToast = page.locator("//div[@class='toast toast-success']");
        this.SuccessToastMessage = page.locator("//div[normalize-space(text())='Password successfully changed']");
    }

    @Step("Verify if SuccessToast is visible")
    public void successToastIsVisible() {
        logger.info("Verifying if SuccessToast is visible");
        SuccessToast.isVisible();
    }

    @Step("Verify the SuccessToast message text")
    public void assertSuccessToast(String expectedTitle) {
        logger.info("Asserting SuccessToast message text: '{}'", expectedTitle);
        assertThat(SuccessToastMessage).hasText(expectedTitle);
    }

    @Step("Verify if new Password title is visible")
    public boolean newPasswordTitle() {
        logger.info("Verifying if new Password title is visible");
        return NewPasswordTitle.isVisible();
    }

    @Step("Verify the new password text")
    public void assertNewPasswordTitle(String expectedTitle) {
        logger.info("Asserting new Password title text: '{}'", expectedTitle);
        assertThat(NewPasswordTitle).hasText(expectedTitle);
    }

    @Step("Enter new password")
    public void enterNewPassword(String newPassword) {
        logger.info("Entering new password: '{}'", newPassword);
        NewPasswordInput.fill(newPassword);
    }

    @Step("Confirm new password")
    public void enterConfirmPassword(String confirmPassword) {
        logger.info("Confirming new password: '{}'", confirmPassword);
        ConfirmPasswordInput.fill(confirmPassword);
    }

    @Step("Tap on the Send button")
    public void clickSendButton() {
        logger.info("Clicking on the Send button");
        SendButton.click();
    }

    @Step("Verify if Send button is disabled by default")
    public void sendButtonDisabledByDefault() {
        logger.info("Verifying if Send button is disabled by default");
        assertTrue(SendButton.isDisabled(), "Button should be disabled by default.");
    }

    @Step("Fill new and confirm passwords fields and tap on the Send button")
    public void enteringNewPassword(String newPassword, String confirmPassword) {
        logger.info("Filling new password: '{}' and confirm password: '{}' fields and tapping on the Send button",
                newPassword, confirmPassword);
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickSendButton();
    }
}