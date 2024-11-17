package com.langfit.test.fixture;

import com.langfit.tests.specific.hooks.EmailsHandlingResetPasswordFlow;
import langfit.web.pages.HomePage;
import langfit.web.pages.ResetPassword;
import utils.PasswordGenerator;
import com.microsoft.playwright.Page;
import langfit.web.pages.LoginPage;

public class TestInitializer {

    private Page page;
    private LoginPage loginPage;
    private HomePage homepage;
    private EmailsHandlingResetPasswordFlow emailsHandlingResetPasswordFlow;
    private ResetPassword resetPassword;
    private PasswordGenerator passwordGenerator;


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
        if(emailsHandlingResetPasswordFlow == null){
                emailsHandlingResetPasswordFlow = new EmailsHandlingResetPasswordFlow(page);
            }
        return emailsHandlingResetPasswordFlow;
    }

    public HomePage getHomepage(){
        if(homepage == null){
            homepage = new HomePage(page);
        }
        return homepage;
    }

    public ResetPassword getResetPassword(){
        if(resetPassword == null){
            resetPassword = new ResetPassword(page);
        }
        return resetPassword;
    }

    public PasswordGenerator getPasswordGenerator(){
        if(passwordGenerator == null){
            passwordGenerator = new PasswordGenerator();
        }
        return passwordGenerator;
    }



    // Add more page initializations as needed
}