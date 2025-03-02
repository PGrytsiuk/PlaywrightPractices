package com.langfit.webui.login.tests;

import com.langfit.data.web.hooks.SetupForLangfitBasic;
import com.langfit.data.web.pages.LoginPage;
import com.langfit.test.fixture.TestInitializer;
import com.common.hooks.CustomListeners;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.utils.ScreenshotsAndRecordings.setupContextWithVideo;

@Listeners(CustomListeners.class)
public class LanguageSelectorTest extends SetupForLangfitBasic {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);
        // Initialize the LoginPage object
        loginPage = testInitializer.getLoginPage();
    }

    @Test(priority = 6)
    @Story("Language selector")
    @Description("This test case verify the welcome message text when selection new language")
    @Severity(SeverityLevel.MINOR)
    public void languageSelectorTest(){
            setupContextWithVideo(browser, "LANGUAGE_SELECTOR");
            page.navigate("/login");

            String [] languages = new String[]{
                    "Welcome to LangFit Gym",
                    "Ласкаво просимо до LangFit Gym",
                    "Добро пожаловать в LangFit Gym",
                    "Witamy w LangFit Gym",
                    "LangFit Gym-ə xoş gəlmisiniz"
            };

            //Tap on the LanguageSelector
            loginPage.languageSelectordropdown();
            //Verify if 5 languages are present in the selector
            loginPage.assertLanguageSelectorSize();
            int languagesCount = loginPage.languageSelectorSize();
                if(languagesCount>0){
                System.out.println("Languages are present");
            }
            //Go through each language and verify the Welcome message for each localization
            loginPage.verifyLanguageWelcomeMessages(languages);
    }
}
