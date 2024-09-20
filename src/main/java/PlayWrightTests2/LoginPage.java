package PlayWrightTests2;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends LunchingBrowser {


    private Page page;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator signInButton;

    public LoginPage(Page page) {
        super(page);
        this.usernameField = page.locator("[name='username']");
        this.passwordField = page.locator("[name='userPassword']");
        this.signInButton = page.locator("[aria-label='sign in']");
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


    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
        // Assuming successful login redirects to HomePage
    }
}
