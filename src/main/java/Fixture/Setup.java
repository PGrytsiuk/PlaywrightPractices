package Fixture;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RecordVideoSize;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;


public class Setup {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );


      /* this.page = browser.newPage();

      this.context = browser.newContext();*/
    }

    protected void setupContextWithVideo() {
        setupContextWithVideo(null);
    }

    protected void setupContextWithVideo(String videoFileName) {

        String VideoCapturePath = "./videos/" + videoFileName;
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get(VideoCapturePath))
                        .setRecordVideoSize(new RecordVideoSize(1280, 720))
        );// Assuming you want .webm format
       page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


}