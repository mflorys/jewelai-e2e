package com.jewelai.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By createButton = By.cssSelector("button[aria-label='Create new project']");
    private final By emptyCta = byText("Create your first");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void waitForLoaded() {
        waitVisible(createButton);
    }

    public void startNewProject() {
        if (driver.findElements(createButton).isEmpty()) {
            click(emptyCta);
        } else {
            click(createButton);
        }
        waitUrlContains("/projects/new");
    }

    public String getAccessToken() {
        return getLocalStorageItem("jewelai.accessToken");
    }
}
