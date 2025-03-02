package com.api.playwright.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Map;

public class RequestChangeContentDemo {

    Playwright pw;
    Browser browser;
    String token = System.getenv("GITHUB_TOKEN");

    @Test(enabled = false)
    public void requestChangeContentDemo() {

        pw = Playwright.create();
        browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext ctx = browser.newContext(new Browser.NewContextOptions()
                .setExtraHTTPHeaders(Map.of("Authorization", "token " + token))
        );

        Page page = ctx.newPage();

        page.route("**/*", route -> route.fulfill(new Route.FulfillOptions()
                        .setStatus(203)
                        .setPath(Paths.get("src\\web\\files\\test_data.json"))
                ));

        Response response = page.navigate("https://api.github.com/user");
        System.out.println(response.text());
        Assert.assertEquals(203, response.status());
        Assert.assertTrue(response.text().contains("Route FulFill Demo"));
    }
}
