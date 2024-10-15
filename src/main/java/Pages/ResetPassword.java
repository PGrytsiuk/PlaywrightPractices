package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class ResetPassword extends BasePage {

    private final Locator NewPasswordTitle;
    private final Locator NewPasswordInput;
    private final Locator ConfirmPasswordInput;
    private final Locator SendButton;

    public ResetPassword(Page page){
        super(page);
        this.NewPasswordTitle=page.locator("//h2[normalize-space(text())='New password']");
        this.NewPasswordInput=page.locator("//input[@aria-label='new password']");
        this.ConfirmPasswordInput=page.locator("//input[@aria-label='confirm password']");
        this.SendButton=page.locator("//button[@aria-label='send']");


    }


    public boolean newPasswordTitle(){

        return NewPasswordTitle.isVisible();
    }

    public void assertNewPasswordtitle(String expectedTitle){

        assertThat(NewPasswordTitle).hasText(expectedTitle);
    }

    public void enterNewPassword(String newPassword){
        NewPasswordInput.fill(newPassword);

    }

    public void enterConfirmPassword(String confirmPassword){
        ConfirmPasswordInput.fill(confirmPassword);

    }

    public void clickSendButton(){
        SendButton.click();

    }
    public void SendButtonDisabledbyDefault(){
        assertTrue(SendButton.isDisabled(), "Button should be disabled by default.");

    }

    public void EnteringNewPassword(String newPassword, String confirmPassword){
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickSendButton();

    }



}
