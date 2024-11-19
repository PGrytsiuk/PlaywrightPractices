package com.playwright.tests;

import com.microsoft.playwright.*;
import java.util.List;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class WindowsHandling {

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            BrowserContext context = browser.newContext();

            Page page = context.newPage();
            page.navigate("https://www.lambdatest.com/selenium-playground/window-popup-modal-demo");
            //popup handling
            Page popup = page.waitForPopup(()->{
                page.getByText("Follow on Twitter").click();
            });
            popup.waitForLoadState();
            assertThat(popup).hasTitle("LambdaTest (@lambdatesting) / X");
            System.out.println(popup.title());

            popup.getByText("Log in").click();
            Locator twitterSignUp=popup.locator("//span[normalize-space()='Sign in to X']//child::span");
            assertThat(twitterSignUp).hasText("Sign in to X");
            popup.close();

            //multiply popups handling
            Page tabs = page.waitForPopup(new Page.WaitForPopupOptions().setPredicate(p->p.context().pages().size()==3),
                    ()->{
                page.getByText("Follow all").click();
            });
            List <Page> pages = tabs.context().pages();
            System.out.println(pages.size());

            pages.forEach(tab->{
                System.out.println(tab.title());
            });

            Page fbpage = null;
            Page twpage = null;
            if(pages.getFirst().title().endsWith("Twitter")){
                twpage = pages.getLast();
            }
            else{
                 fbpage = pages.getFirst();
            }
            assert fbpage != null;
            System.out.println(fbpage.url());

            //open browser separate tab
            Page newTab=page.context().newPage();
            newTab.navigate("https://accounts.lambdatest.com/register?_gl=1*ydrenx*_gcl_au*ODA3NzgyNDgzLjE3Mjc1MzM4MTM.");
            newTab.close();
            context.close();

            //open new browser window
            BrowserContext context1 = browser.newContext();
            Page userpage=context1.newPage();
            userpage.navigate("https://accounts.lambdatest.com/register?_gl=1*ydrenx*_gcl_au*ODA3NzgyNDgzLjE3Mjc1MzM4MTM.");
            context1.close();

        }
    }
}
