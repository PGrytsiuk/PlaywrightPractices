package LangFitTests;

import Configs.ConfigLoader;
import Hooks.Setup;
import Pages.LoginPage;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LogoTest extends Setup {

    @Test(priority = 4)
    public void logoTest(){
        try{

            //Fixture
            ConfigLoader config = new ConfigLoader();
            String username = config.getProperty("Valid_username");
            String password = config.getProperty("Valid_password");

            LoginPage login = new LoginPage(page);

            page.navigate("https://gym.langfit.net/login");
            //Verify if logo is present
            Assert.assertTrue(login.logoIsPresent(), "Logo is visible");
            //Fill the username and password and tap on the Logo
            login.pageIsRefreshedAfterTappingLogo(username, password);
            //Assert that fields are refreshed
            Assert.assertTrue(login.usernameIsEmpty() &&  login.passwordIsEmpty(), "Username and Password fields are empty");



        }catch (Exception e){
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();
        }



    }


}
