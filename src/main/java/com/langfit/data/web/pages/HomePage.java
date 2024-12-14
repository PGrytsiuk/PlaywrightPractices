package com.langfit.data.web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    private final Locator hostLabel;
    private final Locator joinTheLesson;
    private final Locator getRecentActivitySection;
    private final Locator recentActivityLink;
    private final Locator lessonMaterialsDialog;
    private final Locator lessonMaterialsLink;
    private final Locator materialPage;
    private final Locator backArrow;
    private final Locator dialogOk;


    public HomePage(Page page) {
        super(page);
        this.hostLabel = page.locator("//*[contains (@class, 'user-name')]");
        this.joinTheLesson = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Join the lesson"));
        this.recentActivityLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Show used materials"));
        this.getRecentActivitySection = page.locator("//div[@class='lt-student-recent-activity ng-scope']");
        this.lessonMaterialsDialog = page.locator("//md-dialog[@role='dialog']");
        this.lessonMaterialsLink = page.locator("//md-dialog[@role='dialog']//a");
        this.materialPage = page.getByText("Material");
        this.backArrow = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("lt-icon-arrow-left"));
        this.dialogOk = page.getByLabel("ok");
    }

    @Step("Verify if Username is placed on the account page")
    public void verifyUserName(String expectedUsername) {
        logger.info("Verifying if username '{}' is placed on the account page", expectedUsername);
        assertThat(hostLabel).hasText(expectedUsername);
    }

    @Step("Tap on the Join the lesson button")
    public void tapOnJoinTheLesson() {
        logger.info("Tap on the Join the lesson button");
        joinTheLesson.click();
    }

    @Step("Verify if recent activity section is present")
    public boolean verifyRecentActivitySection() {
        logger.info("Verify if recent activity section is present");
        return getRecentActivitySection.isVisible();
    }

    @Step("Tap onto the recent activity link")
    public void clickRecentActivityLink(int index) {
        logger.info("Tap onto the recent activity link");
        recentActivityLink.nth(index).click();
    }

    @Step("Verify if lesson materials dialog is present")
    public boolean verifyLessonMaterialsDialog() {
        logger.info("Verify if lesson materials dialog is present");
        return lessonMaterialsDialog.isVisible();
    }

    @Step("Tap onto the lesson materials link")
    public void clickLessonMaterialsLink() {
        logger.info("Tap onto the lesson materials link");
        lessonMaterialsLink.first().click();
    }

    @Step("Close the dialog by tapping Ok button")
    public void closeDialog() {
        logger.info("Close the dialog by tapping Ok button");
        dialogOk.click();
    }

    @Step("tap on the back arrow")
    public void tapOnBackArrow() {
        logger.info("tap on the back arrow");
        backArrow.click();
    }

    @Step("Verify recent activity section functionality")
    public void verifyRecentActivitySectionFunctionality() {
        logger.info("Verify recent activity section functionality");
        int activitiesCount = recentActivityLink.count();
        for (int i = 0; i < activitiesCount; i++) {
            clickRecentActivityLink(i);
            if (verifyLessonMaterialsDialog()) {
              //  int lessonsCount = lessonMaterialsLink.count();
               // for (int j = 0; j < lessonsCount; j++) {
                    clickLessonMaterialsLink();
                    materialPage.isVisible();
                    tapOnBackArrow();
            }
        }

    }

}
