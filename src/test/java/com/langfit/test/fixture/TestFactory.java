package com.langfit.test.fixture;

import com.langfit.webui.home.tests.HomePageNavMenuTest;
import com.langfit.webui.home.tests.LogoutTest;
import com.langfit.webui.home.tests.ValidLoginTest;
import com.langfit.webui.login.tests.*;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

public class TestFactory {

    private final String browserType;


    public TestFactory(String browserType) {
        this.browserType = browserType;
    }

    @Factory
    @Parameters("browserType")
    public Object[] createInstances(String browserType) {
        return new Object[] {
                new InvalidLoginTest(browserType),
                new DowloadMobileAppSecondTest(browserType),
                new ForgotPassTest(browserType),
                new LanguageSelectorTest(browserType),
                new LogoTest(browserType),
                new TandCandDowloadAMobileppTest(browserType),
                new ValidLoginTest(browserType),
                new NGTest(browserType),
                new ViewPortTest(browserType),
                new HomePageNavMenuTest(browserType),
                new LogoutTest()
        };
    }
}