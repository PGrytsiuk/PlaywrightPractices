package LangFitTests;


import Hooks.Setup;
import Pages.LoginPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static Utils.ScreenshotsAndRecordings.setupContextWithVideo;


@Listeners(Hooks.CustomListeners.class)
public class LanguageSelectorTest extends Setup {

    @Test(priority = 2)
    public void languageSelectorTest(){
        try {
            setupContextWithVideo(browser, "LANGUAGE_SELECTOR");
            page.navigate("https://gym.langfit.net/login");

            String [] languages = new String[]{
                    "Welcome to LangFit Gym",
                    "Ласкаво просимо до LangFit Gym",
                    "Добро пожаловать в LangFit Gym",
                    "Witamy w LangFit Gym",
                    "LangFit Gym-ə xoş gəlmisiniz"
            };

            //Tap on the LanguageSelector
            LoginPage loginPage = new LoginPage(page);
            loginPage.languageSelectordropdown();
            Thread.sleep(3000);
            //Verify if 5 languages are present in the selector
            loginPage.assertLanguageSelectorSize();
            int languagesCount = loginPage.languageSelectorSize();
                if(languagesCount>0){

                System.out.println("Languages are present");

                 }

            //Go through each language and verify the Welcome message for each localization
            loginPage.verifyLanguageWelcomeMessages(languages);

        }catch (Exception e){
            System.err.println("An error occurred during theDowloadAMobilepp test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
