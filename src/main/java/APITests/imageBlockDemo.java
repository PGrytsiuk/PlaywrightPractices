package APITests;

import Hooks.BasicSetup;
import Utils.ScreenshotsAndRecordings;
import org.junit.Assert;
import org.testng.annotations.Test;

public class imageBlockDemo extends BasicSetup {

    @Test
    public void imageBlcckDemo(){

        page.route("**/*.{png,jpg,jpeg,svg}", route-> route.abort());
        page.navigate("https://playwright.dev/");

        ScreenshotsAndRecordings.ScreenshotCapture(page,"blockImage_Playwright");
    }

    @Test
    public void jsBlockDemo(){

        page.route("**/*.{js}", route -> route.abort());
        page.navigate("https://playwright.dev/");

        String theme = page.getAttribute("html", "data-theme");
        Assert.assertEquals("light", theme);

        page.click(".lightToggleIcon_pyhR");
        String theme2 = page.getAttribute("html", "data-theme");
        Assert.assertEquals("dark", theme2);
    }

    @Test
    public void jsBlockDemo2(){

        page.route("**/*", route -> {
            if("script".equalsIgnoreCase(route.request().resourceType()))
                route.abort();
            else
                route.resume();
        });
        page.navigate("https://playwright.dev/");

        String theme = page.getAttribute("html", "data-theme");
        Assert.assertEquals("light", theme);

        page.click(".lightToggleIcon_pyhR");
        String theme2 = page.getAttribute("html", "data-theme");
        Assert.assertEquals("dark", theme2);
    }

}
