package com.jewelai.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By emailInput = By.cssSelector("input[type='email']");
    private final By passwordInput = By.cssSelector("input[type='password'], input[type='text']");
    private final By submitButton = byText("Sign in");
    private final By errorBox = By.cssSelector("div[role='alert'], div.text-red-700");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        super.open("/login");
        waitVisible(emailInput);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public String getError() {
        try {
            return text(errorBox);
        } catch (Exception ignored) {
            return null;
        }
    }
}
