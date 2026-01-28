package com.jewelai.e2e.tests;

import com.jewelai.e2e.pages.HomePage;
import com.jewelai.e2e.pages.LoginPage;
import com.jewelai.e2e.utils.Config;
import org.junit.jupiter.api.*;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @Test
    void loginWithValidCredentials() {
        loginPage.login(Config.getProperty("email"), Config.getProperty("password"));
        HomePage homePage = new HomePage(driver);
        homePage.waitForLoaded();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/projects"), "Should redirect to projects page");
        Assertions.assertNotNull(homePage.getAccessToken(), "Access token should be present");
    }

    @Test
    void loginWithInvalidCredentials() {
        loginPage.login("wrong@mail.com", "pass");
        Assertions.assertNotNull(loginPage.getError(), "Error message should be displayed");
        Assertions.assertFalse(driver.getCurrentUrl().contains("/projects"), "Should not redirect to projects page");
    }
}
