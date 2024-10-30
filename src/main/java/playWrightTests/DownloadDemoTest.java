package playWrightTests;

import Hooks.Setup;
import com.microsoft.playwright.Download;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DownloadDemoTest extends Setup {

    @Test
    public void downloadTestWithHandler(){

        try{
            page.navigate("https://www.win-rar.com/predownload.html?&L=4");

            page.onDownload(download -> {
                System.out.println(download.path());
                 download.saveAs(Paths.get(new File("C:/Users/pgryt/Downloads").toURI()));

            });

            page.click("//a[@id='download-button']");

        } catch (Exception e) {
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void downloadTestWithHandler1(){

        try{
            page.navigate("https://www.google.com/chrome/");

             Download download = page.waitForDownload(()->{
               page.click("//button[@id='js-download-hero']");
            });

            Path path = download.path();
            System.out.println(path);

        }catch (Exception e) {
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void downloadHeadlessTest(){

        try{
            page.navigate("https://gym.langfit.net/login");

            Download download = page.waitForDownload(()->
                    page.click("//a[@class='terms-service-link md-violet-theme']")
            );

            System.out.println(download.path());
           /* download.saveAs(Paths.get(new File("C:/Users/pgryt/Downloads/terms_of_use-2.pdf").toURI()));*/

        }catch (Exception e) {
            System.err.println("An error occurred during the InvalidLoginCredentials test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
