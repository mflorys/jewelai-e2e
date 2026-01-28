package com.jewelai.e2e.tests;

import com.jewelai.e2e.db.DbCleaner;
import com.jewelai.e2e.db.JdbcDbCleaner;
import com.jewelai.e2e.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class BaseTest {

    protected WebDriver driver;
    protected UUID testRunId;
    private final DbCleaner dbCleaner = new JdbcDbCleaner();

    @BeforeEach
    public void baseSetUp() {
        testRunId = UUID.randomUUID();
        driver = DriverFactory.getDriver();
    }

    @AfterEach
    public void baseTeardown() {
        try {
            DriverFactory.quitDriver();
        } finally {
            try {
                dbCleaner.cleanup(testRunId);
            } catch (RuntimeException e) {
                System.err.println("E2E cleanup failed (ignored): " + e.getMessage());
            }
        }
    }

    protected UUID getTestRunId() {
        return testRunId;
    }
}