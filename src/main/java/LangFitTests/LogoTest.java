package LangFitTests;

import Configs.ConfigLoader;
import Data.TestData;
import Hooks.Setup;
import Pages.LoginPage;
import Utils.TestInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

@Listeners(Hooks.CustomListeners.class)
public class LogoTest extends Setup {

    public LogoTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    private static final Logger logger = LoggerFactory.getLogger(LogoTest.class);

    private LoginPage login;
    @BeforeMethod
    public void setUpTest() {
        // Initialize TestInitializer
        TestInitializer testInitializer = new TestInitializer(page);

        // Initialize the LoginPage object
        login = testInitializer.getLoginPage();
    }

    @Test(priority = 4)
    @Story("Logo test")
    @Description("This test case verify that page is refreshed after tapping on Logo image")
    @Severity(SeverityLevel.MINOR)
    public void logoTest(){
        try{

            /*//Fixture
            ConfigLoader config = new ConfigLoader();
            String username = config.getProperty("Valid_username");
            String password = config.getProperty("Valid_password");

            LoginPage login = new LoginPage(page);*/
            page.navigate("/login");
            logger.info("Navigate to page");
            //Verify if logo is present
            logger.info("Verify if logo is present");
            Assert.assertTrue(login.logoIsPresent(), "Logo is visible");
            //Fill the username and password and tap on the Logo
            logger.info("Fill the username and password and tap on the Logo");
            login.pageIsRefreshedAfterTappingLogo(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);
            //Assert that fields are refreshed
            logger.info("Assert that fields are refreshed");
            Assert.assertTrue(login.usernameIsEmpty() &&  login.passwordIsEmpty(), "Username and Password fields are empty");

        }catch (Exception e){
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
