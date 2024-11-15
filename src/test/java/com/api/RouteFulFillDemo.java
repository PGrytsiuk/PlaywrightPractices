package com.api;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RouteFulFillDemo {

    Playwright pw;
    Browser browser;
    String token ="test";

    @Test(enabled = false)
    public void requestChangeDemo() {

        pw = Playwright.create();
        browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext ctx = browser.newContext(new Browser.NewContextOptions()
                .setExtraHTTPHeaders(Map.of("Authorization", "token " + token))
        );

        Page page = ctx.newPage();

        page.route("**/*", route -> {
            Map<String, String> headers = new HashMap<>(route.request().headers());
            headers.put("Test", "added header");
            headers.remove("authorization");
            route.resume(new Route.ResumeOptions().setHeaders(headers));
        });

        Response response = page.navigate("https://api.github.com/user");
        System.out.println(response.request().headers());
        System.out.println(response.text());
        Assert.assertEquals(401, response.status());
       /* Assert.assertEquals(200, response.status());*/
    }

}
