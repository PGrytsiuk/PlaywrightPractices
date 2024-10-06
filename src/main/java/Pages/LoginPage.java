package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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

    public LoginPage(Page page) {
        super(page);
        this.usernameField = page.locator("[name='username']");
        this.passwordField = page.locator("[name='userPassword']");
        this.signInButton = page.locator("[aria-label='sign in']");
        this.ToastAlert =page.locator("//*[contains(@id, 'toast-container')]");
        this.ToastMessage=page.locator("//*[contains (@class, 'toast-message')]");
        this.AppStoreRedirect=page.locator("//img[@alt='Download on the App Store']");
        this.PlayMarketRedirect=page.locator("//img[@alt='Get it on Google Play']");
        this.ErrorMessage=page.locator("(//div[@class='md-input-message-animation ng-scope'])");
        this.LanguageSelector= page.locator("section");
        this.LanguageSelectorSize=page.locator("(//md-option[contains(@class,'option ng-scope')])//div[@class='md-text ng-binding']");
        this.TermsAndCondition =page.locator("//a[@class='terms-service-link md-violet-theme']");
        this.WelcomeMessage=page.locator("//h1[@class='welcome-message ng-binding']");
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
                    LanguageSelectordropdown();
                }

            }
    }

    public void TermsAndConditions(){
        TermsAndCondition.click();
    }

    public void LanguageSelectordropdown(){
        LanguageSelector.click();
    }

    public int LanguageSelectorSize(){
        return LanguageSelectorSize.count();
    }

    public void AssertLanguageSelectorSize(){
        int languageSelectorExpectedCount = 5;
        Assert.assertEquals(LanguageSelectorSize(), languageSelectorExpectedCount);
    }


    public int ErrorMessageSize(){
        return ErrorMessage.count();

    }

    public void AssertErrorMessagesCount(){
        int errorMessagesExpectedCount = 1;
        Assert.assertEquals(ErrorMessageSize(), errorMessagesExpectedCount);
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

    public boolean  AlertAppear(){
        ToastAlert.isVisible();
        return true;

    }
    public void getToastMessageText(String expectedAlertMessage){
        assertThat(ToastMessage).hasText(expectedAlertMessage);
    }

    public void EmptyLogin(){
        enterUsername("");
        enterPassword("");
        clickSignIn();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
        // Assuming successful login redirects to HomePage
    }

    public void clickAppStoreRedirect(){
        AppStoreRedirect.click();
    }

    public void clickPlayMarketRedirect(){
        PlayMarketRedirect.click();
    }
}