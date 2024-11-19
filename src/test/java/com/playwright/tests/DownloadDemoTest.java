package com.playwright.tests;

import com.common.hooks.BasicSetup;
import com.microsoft.playwright.Download;
import org.testng.annotations.Test;
import java.nio.file.Path;

public class DownloadDemoTest extends BasicSetup {

    public DownloadDemoTest(String browserType) {
        super(browserType); // Pass the browser type to the Setup constructor
    }

    @Test
    public void downloadTestWithHandler(){
            page.navigate("https://notepad-plus-plus.org/downloads/v8.7/");

            page.onDownload(download -> {
                System.out.println(download.path());
                /* download.saveAs(Paths.get(new File("C:/Users/pgryt/Downloads").toURI()));*/

            });

            page.click("//main[@id='main']//img[1]");
    }

    @Test
    public void downloadTestWithHandler1(){

            page.navigate("https://www.google.com/chrome/");

             Download download = page.waitForDownload(()->{
               page.click("//button[@id='js-download-hero']");
            });

            Path path = download.path();
            System.out.println(path);
    }

    @Test
    public void downloadHeadlessTest(){
            page.navigate("https://gym.langfit.net/login");

            Download download = page.waitForDownload(()->
                    page.click("//a[@class='terms-service-link md-violet-theme']")
            );

            System.out.println(download.path());
       /*    download.saveAs(Paths.get(new File("C:/Users/pgryt/Downloads/terms_of_use-2.pdf").toURI()));*/
    }
}
