package Pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage {



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

    public boolean logoIsPresent(){
        return Logo.isVisible();

    }

    public void clickLogo() {
        page.reload();
      
    }


    public void pageIsRefreshedAfterTappingLogo(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickLogo();

    }


    public String getUsernameValue(){
         return usernameField.inputValue();
      
    }

    public String  getPasswordValue(){
        return passwordField.inputValue();

    }

    public boolean usernameIsEmpty(){
        return getUsernameValue().isEmpty();

    }

    public boolean passwordIsEmpty(){
        return getPasswordValue().isEmpty();
      
    }

    public void tapForgotPassword() {
        ForgotPassword.click();
      
    }

    public boolean resetPasswordpopup() {
        return ResetPasswordpopup.isVisible();

    }

    public void assertResetPasswordMessage(String expectedMessage) {
        assertThat(ResetPasswordMessage).hasText(expectedMessage);
    }

    public void enterUsernameOrEmail(String usernameOrEmail) {
        UsernameOREmail.fill(usernameOrEmail);
    }

    public void clickSend() {
        Send.click();
    }


    public void assertPopupSuccessTitle(String expectedTitle) {
        String actualMessage = ResetPasswordSuccessMessage.textContent();
        if (actualMessage != null && actualMessage.contains(expectedTitle)) {
            System.out.println("Success message verified: " + actualMessage);
        } else {
            throw new AssertionError("Expected success message not found: " + expectedTitle);
        }
    }

    public boolean errorToastWhenResettingPassword(){
        try {
            ErrorToastResetPassword.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return ErrorToastResetPassword.isVisible();
        } catch (Exception e) {
            return false;
        }

    }

    public void assertPopupErrorMessage(String expectedMessage) {
        String actualErrorMessage = ResetPasswordErrorMessage.textContent();
        if (actualErrorMessage != null && actualErrorMessage.contains(expectedMessage)) {
            System.out.println("Error message verified: " + actualErrorMessage);
        } else {
            throw new AssertionError("Expected error message not found: " + expectedMessage);
        }
    }

    private Locator welcomeMessageLocator() {
        return WelcomeMessage;
    }

    public String getWelcomeMessageText() {
        return welcomeMessageLocator().textContent();
    }

    private Locator LanguageSelectorSizeLocator() {
        return LanguageSelectorSize;
    }

    public void clickDropdownOption(int index){
        LanguageSelectorSizeLocator().nth(index).click();
    }

    public void verifyLanguageWelcomeMessages(String[] languages) {
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

    public void termsAndConditions(){
        TermsAndCondition.click();
    }

    public void languageSelectordropdown(){
        LanguageSelector.click();
    }

    public int languageSelectorSize(){
        return LanguageSelectorSize.count();
    }

    public void assertLanguageSelectorSize(){
        int languageSelectorExpectedCount = 5;
        Assert.assertEquals(languageSelectorSize(), languageSelectorExpectedCount);
    }


    public int errorMessageSize(){
        return ErrorMessage.count();

    }

    public void assertErrorMessagesCount(){
        int errorMessagesExpectedCount = 1;
        Assert.assertEquals(errorMessageSize(), errorMessagesExpectedCount);
    }

    public void enterUsername(String username) {
        usernameField.fill(username);
    }

    public void enterPassword(String password) {
        passwordField.fill(password);
    }

    public void clickSignIn() {
        signInButton.click();
    }

    public boolean alertAppear(){
        ToastAlert.isVisible();
        return true;

    }
    public void getToastMessageText(String expectedAlertMessage){
        assertThat(ToastMessage).hasText(expectedAlertMessage);
    }

    public void emptyLogin(){
        enterUsername("");
        enterPassword("");
        clickSignIn();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();

    }

    public void clickAppStoreRedirect(){
        AppStoreRedirect.click();
    }

    public void clickPlayMarketRedirect(){
        PlayMarketRedirect.click();
    }
}