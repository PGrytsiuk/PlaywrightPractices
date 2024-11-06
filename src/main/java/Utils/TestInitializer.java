package Utils;

import com.microsoft.playwright.Page;
import Pages.LoginPage;

public class TestInitializer {

    private Page page;
    private LoginPage loginPage;

    public TestInitializer(Page page) {
        this.page = page;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }
        return loginPage;
    }

    // Add more page initializations as needed
}