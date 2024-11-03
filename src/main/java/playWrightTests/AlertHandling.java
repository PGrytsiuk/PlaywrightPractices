package playWrightTests;

import com.microsoft.playwright.*;

public class AlertHandling {

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            BrowserContext context = browser.newContext();

            Page page = context.newPage();
            page.navigate("https://www.lambdatest.com/selenium-playground/javascript-alert-box-demo");

            //alert with one Ok option
            Locator buttons=page.locator("text='Click Me'");
            page.onceDialog(Dialog::accept);
            buttons.first().click();

         /*   Thread.sleep(5000);*/
            //alert with two options Ok/Cancel
            page.onceDialog(alert-> {
            String message = alert.message();
                System.out.println(message);
                alert.accept();
                    });
            buttons.nth(1).click();
            System.out.println(page.locator("#confirm-demo").textContent());

            //alert with input field
            page.onceDialog(alert-> {
                String message = alert.message();
                System.out.println(message);
                System.out.println(alert.defaultValue());
                alert.accept("Pavlo");
            });
            buttons.last().click();
            System.out.println(page.locator("#prompt-demo").textContent());

            page.waitForTimeout(3000);

        }
    }
}
