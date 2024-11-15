package com.playwrighttests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DropDownsHandling {

    public static  void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page page = browser.newPage();

            page.navigate("https://www.lambdatest.com/selenium-playground/select-dropdown-demo");

            String path ="#select-demo";
            Locator dropdown =page.locator(path).first();
            Locator result = page.locator("//p[contains(@class, 'selected-value text-size-14')]");
            dropdown.selectOption(new SelectOption().setValue("Monday"));
            //select by option
            dropdown.selectOption("Thursday");
            //Select by label
            dropdown.selectOption(new SelectOption().setLabel("Wednesday"));
            System.out.println(dropdown.textContent());
            //Select by index
            dropdown.selectOption(new SelectOption().setIndex(3));
            System.out.println(result.textContent());

            // Select multiply

            Locator states = page.locator("//select[@name='States']");
            states.selectOption(new String[] {"Ohio", "New York"});
            Locator option= page.locator("option");
            System.out.println(option.count());
            List <String> allOptions = option.allInnerTexts();
           /* for (int i=0; i < allOptions.size(); i++){
                System.out.println(allOptions.get(i));
            }*/
           allOptions.forEach(System.out::println);

            page.navigate("https://designsystem.digital.gov/patterns/create-a-user-profile/address/");
            Locator but = page.locator("//button[contains (@aria-label, 'Toggle the dropdown list')]").first();
            but.click();
            Locator selectOption = page.locator("//li[contains (@id, 'physical-state--list--option-0')]",
                    new Page.LocatorOptions().setHasText("AL - Alabama"));
            selectOption.click();
            assertThat(selectOption).containsText("AL - Alabama");
            System.out.println(selectOption.textContent()+" Passed");

        }
    }
}

