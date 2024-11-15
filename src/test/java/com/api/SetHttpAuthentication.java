package com.api;

import com.data.TestData;
import com.common.hooks.BasicSetup;
import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SetHttpAuthentication extends BasicSetup {

    Playwright pw;
    Browser browser;
    String token ="test";

    @Test
    public void setHttpAuthentication(){

        pw = Playwright.create();
        browser = pw.chromium().launch();

        //----------- UI part ------------
        BrowserContext uiContext = browser.newContext();

        Page uiPage = uiContext.newPage();
        uiPage.navigate("https://github.com/PGrytsiuk");
        Assert.assertTrue(uiPage.isVisible("text=Repositories 3"));


        //----------- Web Api part - cross-check ------------

        BrowserContext apiContext = browser.newContext(new Browser.NewContextOptions()
                //.setHttpCredentials("Pgrytsiuk", "pwd")
                .setExtraHTTPHeaders(Map.of("Authorization", "token "  + token))
        );

        Page webApiPAge = apiContext.newPage();
        Response response = webApiPAge.navigate("https://api.github.com/user");
        System.out.println(response.text());
        Assert.assertEquals(200, response.status());
        Assert.assertTrue(response.text().contains("\"public_repos\": 3"));
    }

}
