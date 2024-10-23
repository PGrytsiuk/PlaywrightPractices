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
    private final Locator SuccessToast;
    private final Locator SuccessToastMessage;

    public ResetPassword(Page page){
        super(page);
        this.NewPasswordTitle=page.locator("//h2[normalize-space(text())='New password']");
        this.NewPasswordInput=page.locator("//input[@aria-label='new password']");
        this.ConfirmPasswordInput=page.locator("//input[@aria-label='confirm password']");
        this.SendButton=page.locator("//button[@aria-label='send']");
        this.SuccessToast=page.locator("//div[@class='toast toast-success']");
        this.SuccessToastMessage=page.locator("//div[normalize-space(text())='Password successfully changed']");


    }

    public void successToastIsVisible(){
        SuccessToast.isVisible();
    }

    public void assertSuccessToast(String expectedTitle){
        assertThat(SuccessToastMessage).hasText(expectedTitle);
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
    public void sendButtonDisabledbyDefault(){
        assertTrue(SendButton.isDisabled(), "Button should be disabled by default.");

    }

    public void enteringNewPassword(String newPassword, String confirmPassword){
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickSendButton();

    }



}