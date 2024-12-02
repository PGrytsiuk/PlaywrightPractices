package com.langfit.data.web.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;

public class NavMenu {

    private static final Logger logger = LoggerFactory.getLogger(NavMenu.class);
    private final Locator leftnavmenu;
    private final Locator hamburger;
    private final Locator homePage;
    private final Locator homeWorks;
    private final Locator lessonsHistory;
    private final Locator settings;
    private final Locator help;
    private final Locator leftNavMenuOptionsAmount;
    //private final Locator clickLeftNavigationMenuOption;
    private final Locator expandedLeftNavMenu;


    protected Page page;

    public NavMenu(Page page) {
        this.page = page;
        this.leftnavmenu = page.locator(".lt-left-menu-box");
        this.hamburger = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("open menu"));
        this.homePage = page.getByLabel("Home Page");
        this.homeWorks = page.getByLabel("Homeworks");
        this.lessonsHistory = page.getByLabel("Lessons history");
        this.settings = page.getByLabel("Settings");
        this.help = page.getByLabel("Help");
        this.leftNavMenuOptionsAmount = page.locator("//md-list/a");
        this.expandedLeftNavMenu = page.locator(".lt-left-menu-list");
    }

    @Step("Check if left NavMenu is visible")
    public boolean leftMenuIsPresent() {
        logger.info("Checking if left NavMenu is present");
        return leftnavmenu.isVisible();
    }

    @Step("Very if left navigation menu components are present")
    public boolean verifyLeftNavigationMenuComponents() {
        logger.info("Verifying visibility of left navigation menu components");

        boolean homePageVisible = homePage.isVisible();
        boolean homeWorksVisible = homeWorks.isVisible();
        boolean lessonsHistoryVisible = lessonsHistory.isVisible();
        boolean settingsVisible = settings.isVisible();
        boolean helpVisible = help.isVisible();

        logger.info("Home Page visibility: " + homePageVisible);
        logger.info("Homeworks visibility: " + homeWorksVisible);
        logger.info("Lessons History visibility: " + lessonsHistoryVisible);
        logger.info("Settings visibility: " + settingsVisible);
        logger.info("Help visibility: " + helpVisible);

        return homePageVisible && homeWorksVisible && lessonsHistoryVisible && settingsVisible && helpVisible;
    }

    @Step("Check if hamburger is visible")
    public boolean hamburgersPresent() {
        logger.info("Checking if hamburger is present");
        return hamburger.isVisible();
    }

    @Step("Tap on hamburger menu")
    public void hamburgerClick() {
        logger.info("tap on hamburger menu");
        hamburger.click();
    }

    @Step("Check if menu is expanded after clicking on hambuer icon")
    public void leftNavMenuIsExpanded() {
        logger.info("checking if menu is expanded after clicking on hambuer icon");
        expandedLeftNavMenu.isVisible();
    }

    private Locator leftNavMenuOptionsAmountLocator() {
        return leftNavMenuOptionsAmount;
    }

    @Step("Get size of left navigation menu options")
    public int leftNavMenuOptionsAmountSize() {
        logger.info("Getting size of left navigation menu options");
        return leftNavMenuOptionsAmount.count();
    }

    @Step("Click on left navigation menu at index: {index}")
    public void clickLeftNavigationOption(int index) {
        logger.info("Clicking on left navigation menu option at index: {}", index);
        leftNavMenuOptionsAmountLocator().nth(index).click();
    }

    @Step("Get nav menu option urls")
    public String getNavMenuOptionUrl() {
        logger.info("Getting left navigation menu options urls");
        return page.url();
    }

    @Step("Verify left navigation menu functionality for each option")
    public void leftNavigationMenuFunctionalityChecking(String[] expectedUrls) {
        logger.info("Verify left navigation menu functionality for each option");
        int optionCount = leftNavMenuOptionsAmountLocator().count();
        for (int i = 0; i< optionCount; i++) {
            clickLeftNavigationOption(i);
            String currentUrl = getNavMenuOptionUrl();
           assertEquals(currentUrl,expectedUrls[i]);
          /*  if (i < optionCount - 1) {
                // If needed, click the hamburger menu to reopen the navigation
                hamburgerClick();
            }*/
        }
    }
}
