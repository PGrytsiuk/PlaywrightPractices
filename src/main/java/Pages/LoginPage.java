package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage extends BasePage {



    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator signInButton;
    private final Locator ToastAlert;
    private final Locator ToastMessage;

    public LoginPage(Page page) {
        super(page);
        this.usernameField = page.locator("[name='username']");
        this.passwordField = page.locator("[name='userPassword']");
        this.signInButton = page.locator("[aria-label='sign in']");
        this.ToastAlert =page.locator("//*[contains(@id, 'toast-container')]");
        this.ToastMessage=page.locator("//*[contains (@class, 'toast-message')]");
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
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
        // Assuming successful login redirects to HomePage
    }
}
