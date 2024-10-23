package Utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RecordVideoSize;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenshotsAndRecordings {

    public static void ScreenshotCapture(Page page, String testName){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String screenshotPath = "./snaps/"+ testName +"_"+ timestamp + ".png";

        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();
        page.screenshot(screenshotOptions.setPath(Paths.get(screenshotPath)));

    }


    public static BrowserContext VideoCapture (Browser browser, String testName){

        String VideoCapturePath = "./videos/"+ testName;
        return browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get(VideoCapturePath))
                .setRecordVideoSize(new RecordVideoSize(1280, 720)));
    }

    public static void setupContextWithVideo(Browser browser, String videoFileName) {
        String videoCapturePath = "./videos/" + videoFileName;
        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get(videoCapturePath))
                        .setRecordVideoSize(new RecordVideoSize(1280, 720))
        );

    }


}
