package com.jewelai.e2e.tests;

import com.jewelai.e2e.db.DbTestGateway;
import com.jewelai.e2e.pages.HomePage;
import com.jewelai.e2e.pages.RegisterPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest extends BaseTest {

    private RegisterPage registerPage;
    private final DbTestGateway dbGateway = new DbTestGateway();

    @BeforeEach
    void setUp() {
        registerPage = new RegisterPage(driver);
        registerPage.open();
    }

    @Test
    void registerNewUser() {
        String email = "test-" + testRunId + "@jewelai.studio";
        String password = "password123";

        registerPage.register(email, password);

        // Assert UI
        HomePage homePage = new HomePage(driver);
        homePage.waitForLoaded();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/projects"), "Should redirect to projects page after registration");

        // Assert DB
        Assertions.assertTrue(dbGateway.userExists(email, testRunId), "User should exist in DB with correct testRunId");
    }
}
