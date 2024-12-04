package com.langfit.test.fixture;

import com.langfit.data.web.components.Header;
import com.langfit.data.web.components.NavMenu;
import com.langfit.tests.specific.hooks.EmailsHandlingResetPasswordFlow;
import com.langfit.data.web.pages.HomePage;
import com.langfit.data.web.pages.ResetPassword;
import com.utils.PasswordGenerator;
import com.microsoft.playwright.Page;
import com.langfit.data.web.pages.LoginPage;

public class TestInitializer {

    private final Page page;
    private LoginPage loginPage;
    private HomePage homepage;
    private EmailsHandlingResetPasswordFlow emailsHandlingResetPasswordFlow;
    private ResetPassword resetPassword;
    private PasswordGenerator passwordGenerator;
    private NavMenu leftnavMenu;
    private Header header;


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

    public NavMenu getLeftnavMenu() {
        if(leftnavMenu == null) {
            leftnavMenu = new NavMenu(page);
        }
        return leftnavMenu;
    }

    public Header getHeader() {
        if(header== null) {
            header = new Header(page);
        }
        return header;
    }

    // Add more page initializations as needed
}