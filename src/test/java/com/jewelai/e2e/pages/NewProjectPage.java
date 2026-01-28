package com.jewelai.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewProjectPage extends BasePage {

    private final By startScratch = byText("Start from scratch");

    public NewProjectPage(WebDriver driver) {
        super(driver);
    }

    public void waitForLoaded() {
        waitVisible(startScratch);
    }
}
