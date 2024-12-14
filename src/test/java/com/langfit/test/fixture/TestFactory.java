package com.langfit.test.fixture;

import com.langfit.webui.home.tests.HomePageNavMenuTest;
import com.langfit.webui.home.tests.LogoutTest;
import com.langfit.webui.home.tests.RecentActivityTest;
import com.langfit.webui.home.tests.ValidLoginTest;
import com.langfit.webui.login.tests.*;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

public class TestFactory {

    @Factory
    @Parameters("browserType")
    public Object[] createInstances() {
        return new Object[] {
                new InvalidLoginTest(),
                new DowloadMobileAppSecondTest(),
                new ForgotPassTest(),
                new LanguageSelectorTest(),
                new LogoTest(),
                new TandCandDowloadAMobileppTest(),
                new ValidLoginTest(),
                new NGTest(),
                new ViewPortTest(),
                new HomePageNavMenuTest(),
                new LogoutTest(),
                new RecentActivityTest()
        };
    }
}