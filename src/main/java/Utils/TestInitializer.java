package Utils;

import Hooks.EmailsHandlingResetPasswordFlow;
import Pages.HomePage;
import com.microsoft.playwright.Page;
import Pages.LoginPage;

public class TestInitializer {

    private Page page;
    private LoginPage loginPage;
    private HomePage homepage;
    private EmailsHandlingResetPasswordFlow EmailsHandlingResetPasswordFlow;


    public TestInitializer(Page page) {
        this.page = page;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }
        return loginPage;
    }

    public EmailsHandlingResetPasswordFlow getEmailsHandlingResetPasswordFlow(){
        if(EmailsHandlingResetPasswordFlow == null){
                EmailsHandlingResetPasswordFlow = new EmailsHandlingResetPasswordFlow(page);
            }
        return EmailsHandlingResetPasswordFlow;
    }

    public HomePage getHomepage(){
        if(homepage == null){
            homepage = new HomePage(page);
        }
        return homepage;
    }

    // Add more page initializations as needed
}