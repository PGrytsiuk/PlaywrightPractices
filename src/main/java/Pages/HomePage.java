package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {

    private final Locator hostLabel;

    public HomePage(Page page) {
        super(page);
        this.hostLabel = page.locator("//*[contains (@class, 'user-name')]");
    }

    public void verifyUserName(String expectedUsername) {
        assertThat(hostLabel).hasText(expectedUsername);
    }


}
