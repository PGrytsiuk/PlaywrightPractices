package langfit.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator signInButton;
    private final Locator ToastAlert;
    private final Locator ToastMessage;
    private final Locator AppStoreRedirect;
    private final Locator PlayMarketRedirect;
    private final Locator ErrorMessage;
    private final Locator LanguageSelector;
    private final Locator LanguageSelectorSize;
    private final Locator TermsAndCondition;
    private final Locator WelcomeMessage;
    private final Locator ForgotPassword;
    private final Locator ResetPasswordpopup;
    private final Locator ResetPasswordMessage;
    private final Locator UsernameOREmail;
    private final Locator Send;
    private final Locator ResetPasswordSuccessMessage;
    private final Locator ErrorToastResetPassword;
    private final Locator ResetPasswordErrorMessage;
    private final Locator Logo;

    public LoginPage(Page page) {
        super(page);
        this.Logo=page.locator("//a[@aria-label='Login page']");
        this.usernameField = page.locator("[name='username']");
        this.passwordField = page.locator("[name='userPassword']");
        this.signInButton = page.locator("//button[@aria-label='sign in']");
        this.ToastAlert =page.locator("//*[contains(@id, 'toast-container')]");
        this.ToastMessage=page.locator("//*[contains (@class, 'toast-message')]");
        this.AppStoreRedirect=page.locator("//img[@alt='Download on the App Store']");
        this.PlayMarketRedirect=page.locator("//img[@alt='Get it on Google Play']");
        this.ErrorMessage=page.locator("(//div[@class='md-input-message-animation ng-scope'])");
        this.LanguageSelector= page.locator("section");
        this.LanguageSelectorSize=page.locator("(//md-option[contains(@class,'option ng-scope')])//div[@class='md-text ng-binding']");
        this.TermsAndCondition =page.locator("//a[@class='terms-service-link md-violet-theme']");
        this.WelcomeMessage=page.locator("//h1[@class='welcome-message ng-binding']");
        this.ForgotPassword=page.locator("//span[@translate='login.FORGOT_PASSWORD']");
        this.ResetPasswordpopup=page.locator("//md-dialog[contains(@class,'lt-forgot-modal lt-modal')]");
        this.ResetPasswordMessage=page.locator("//span[@translate='login.RESET']");
        this.UsernameOREmail=page.locator("//input[@name='usernameOrEmail']");
        this.Send=page.locator("//button[@aria-label='send']");
        this.ResetPasswordSuccessMessage=page.locator("//h2[@class='valid-email-message ng-scope']");
        this.ErrorToastResetPassword=page.locator("//div[@class='toast toast-error']");
        this.ResetPasswordErrorMessage=page.locator("//div[@aria-label='Error']/following-sibling::div[1]");
    }

    @Step("Check if logo is present")
    public boolean logoIsPresent() {
        logger.info("Checking if logo is present");
        return Logo.isVisible();
    }

    @Step("Click on logo")
    public void clickLogo() {
        logger.info("Clicking on logo");
        page.reload();
    }

    @Step("Refresh the page after tapping logo with username: {username} and password: {password}")
    public void pageIsRefreshedAfterTappingLogo(String username, String password) {
        logger.info("Refreshing the page after tapping logo with username: {} and password: {}", username, password);
        enterUsername(username);
        enterPassword(password);
        clickLogo();
    }

    @Step("Get username field value")
    public String getUsernameValue() {
        logger.info("Getting username field value");
        return usernameField.inputValue();
    }

    @Step("Get password field value")
    public String getPasswordValue() {
        logger.info("Getting password field value");
        return passwordField.inputValue();
    }

    @Step("Check if username field is empty")
    public boolean usernameIsEmpty() {
        logger.info("Checking if username field is empty");
        return getUsernameValue().isEmpty();
    }

    @Step("Check if password field is empty")
    public boolean passwordIsEmpty() {
        logger.info("Checking if password field is empty");
        return getPasswordValue().isEmpty();
    }

    @Step("Tap on Forgot Password")
    public void tapForgotPassword() {
        logger.info("Tapping on Forgot Password");
        ForgotPassword.click();
    }

    @Step("Check if reset password popup is visible")
    public boolean resetPasswordpopup() {
        logger.info("Checking if reset password popup is visible");
        return ResetPasswordpopup.isVisible();
    }

    @Step("Assert reset password message: {expectedMessage}")
    public void assertResetPasswordMessage(String expectedMessage) {
        logger.info("Asserting reset password message: {}", expectedMessage);
        assertThat(ResetPasswordMessage).hasText(expectedMessage);
    }

    @Step("Enter username or email: {usernameOrEmail}")
    public void enterUsernameOrEmail(String usernameOrEmail) {
        logger.info("Entering username or email: {}", usernameOrEmail);
        UsernameOREmail.fill(usernameOrEmail);
    }

    @Step("Click on Send button")
    public void clickSend() {
        logger.info("Clicking on Send button");
        Send.click();
    }

    @Step("Assert popup success message: {expectedTitle}")
    public void assertPopupSuccessTitle(String expectedTitle) {
        logger.info("Asserting popup success message: {}", expectedTitle);
        String actualMessage = ResetPasswordSuccessMessage.textContent();
        if (actualMessage != null && actualMessage.contains(expectedTitle)) {
            System.out.println("Success message verified: " + actualMessage);
        } else {
            throw new AssertionError("Expected success message not found: " + expectedTitle);
        }
    }

    @Step("Check for error toast when resetting password")
    public boolean errorToastWhenResettingPassword() {
        logger.info("Checking for error toast when resetting password");
        try {
            ErrorToastResetPassword.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return ErrorToastResetPassword.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Assert popup error message: {expectedMessage}")
    public void assertPopupErrorMessage(String expectedMessage) {
        logger.info("Asserting popup error message: {}", expectedMessage);
        String actualErrorMessage = ResetPasswordErrorMessage.textContent();
        if (actualErrorMessage != null && actualErrorMessage.contains(expectedMessage)) {
            System.out.println("Error message verified: " + actualErrorMessage);
        } else {
            throw new AssertionError("Expected error message not found: " + expectedMessage);
        }
    }

    @Step("Get welcome message text")
    public String getWelcomeMessageText() {
        logger.info("Getting welcome message text");
        return welcomeMessageLocator().textContent();
    }

    @Step("Click on dropdown option at index: {index}")
    public void clickDropdownOption(int index) {
        logger.info("Clicking on dropdown option at index: {}", index);
        LanguageSelectorSizeLocator().nth(index).click();
    }

    @Step("Verify welcome messages for different languages")
    public void verifyLanguageWelcomeMessages(String[] languages) {
        logger.info("Verifying welcome messages for different languages");
        int optionCount = LanguageSelectorSizeLocator().count();
        for (int i = 0; i < optionCount; i++) {
            clickDropdownOption(i);
            String actualWelcomeMessage = getWelcomeMessageText();
            assert actualWelcomeMessage.equals(languages[i]) : "Expected: " + languages[i] + ", but got: " + actualWelcomeMessage;

            if (i < optionCount - 1) {
                languageSelectordropdown();
            }
        }
    }

    @Step("Click on Terms and Conditions link")
    public void termsAndConditions() {
        logger.info("Clicking on Terms and Conditions link");
        TermsAndCondition.click();
    }

    @Step("Open language selector dropdown")
    public void languageSelectordropdown() {
        logger.info("Opening language selector dropdown");
        LanguageSelector.click();
    }

    @Step("Get size of language selector")
    public int languageSelectorSize() {
        logger.info("Getting size of language selector");
        return LanguageSelectorSize.count();
    }

    @Step("Assert size of language selector")
    public void assertLanguageSelectorSize() {
        logger.info("Asserting size of language selector");
        int languageSelectorExpectedCount = 5;
        Assert.assertEquals(languageSelectorSize(), languageSelectorExpectedCount);
    }

    @Step("Get size of error messages")
    public int errorMessageSize() {
        logger.info("Getting size of error messages");
        return ErrorMessage.count();
    }

    @Step("Assert number of error messages")
    public void assertErrorMessagesCount() {
        logger.info("Asserting number of error messages");
        int errorMessagesExpectedCount = 2;
        Assert.assertEquals(errorMessageSize(), errorMessagesExpectedCount);
    }

    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        usernameField.fill(username);
    }

    @Step("Enter password: {password}")
    public void enterPassword(String password) {
        logger.info("Entering password: {}", password);
        passwordField.fill(password);
    }

    @Step("Click on Sign In button")
    public void clickSignIn() {
        logger.info("Clicking on Sign In button");
        signInButton.click();
    }

    @Step("Check if toast alert appears")
    public boolean alertAppear() {
        logger.info("Checking if toast alert appears");
        ToastAlert.isVisible();
        return true;
    }

    @Step("Get toast message text: {expectedAlertMessage}")
    public void getToastMessageText(String expectedAlertMessage) {
        logger.info("Getting toast message text: {}", expectedAlertMessage);
        assertThat(ToastMessage).hasText(expectedAlertMessage);
    }

    @Step("Perform empty login")
    public void emptyLogin() {
        logger.info("Performing empty login");
        enterUsername("");
        enterPassword("");
        clickSignIn();
    }

    @Step("Perform login with username: {username} and password: {password}")
    public void login(String username, String password) {
        logger.info("Performing login with username: {} and password: {}", username, password);
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }

    @Step("Click on App Store redirect")
    public void clickAppStoreRedirect() {
        logger.info("Clicking on App Store redirect");
        AppStoreRedirect.click();
    }

    @Step("Click on Play Market redirect")
    public void clickPlayMarketRedirect() {
        logger.info("Clicking on Play Market redirect");
        PlayMarketRedirect.click();
    }

    private Locator welcomeMessageLocator() {
        return WelcomeMessage;
    }

    private Locator LanguageSelectorSizeLocator() {
        return LanguageSelectorSize;
    }
}