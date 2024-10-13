package playWrightTests;
import org.junit.Assert;
import com.microsoft.playwright.*;


public class CheckBoxHandling {

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page page = browser.newPage();


            page.navigate("https://designsystem.digital.gov/components/checkbox/");
            boolean checkboxVisible = page.locator("//*[contains(@for, 'check-historical-douglass')]").first().isVisible();
            if (checkboxVisible) {
                System.out.println("Checkbox is visible");
            } else {
                System.out.println("Checkbox is not visible");
                return;
            }

            String xpath = "(//*[contains(@for, 'check-historical')])[position() <= 3]";
            Locator checkboxes =page.locator("xpath=" + xpath);

            for (int i = 0; i < checkboxes.count(); i++) {
              checkboxes.nth(i).check();

              Locator checkboxesSelected = checkboxes.nth(i);

                // Assert that the checkboxes are checked
               Assert.assertTrue("Checkbox should be checked." + (i + 1), checkboxesSelected.isChecked());
                System.out.println("Checked: " + checkboxesSelected.textContent());
            }
            // Uncheck the first three matching checkboxes
            for (int i = 0; i < checkboxes.count(); i++) {
                checkboxes.nth(i).uncheck();

                Locator checkboxesUnSelected = checkboxes.nth(i);
                // Assert that the checkboxes are unchecked
               Assert.assertFalse("Checkbox should be unchecked."+ (i+1), checkboxesUnSelected.isChecked());

                System.out.println("Unchecked: " + checkboxesUnSelected.textContent());
            }

            browser.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
