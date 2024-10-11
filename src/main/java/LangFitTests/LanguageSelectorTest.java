package LangFitTests;


import Fixture.Setup;
import Pages.LoginPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(Fixture.CustomListeners.class)
public class LanguageSelectorTest extends Setup {

    @Test(priority = 2)
    public void languageSelectorTest(){
        try {
            setupContextWithVideo("LANGUAGE_SELECTOR");
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
            loginPage.LanguageSelectordropdown();
            Thread.sleep(3000);
            //Verify if 5 languages are present in the selector
            loginPage.AssertLanguageSelectorSize();
            int languagesCount = loginPage.LanguageSelectorSize();
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
