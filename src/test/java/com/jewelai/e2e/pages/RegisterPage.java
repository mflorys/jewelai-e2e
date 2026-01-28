package com.jewelai.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By emailInput = By.cssSelector("input[type='email']");
    private final By passwordInput = By.cssSelector("input[type='password']");
    private final By submitButton = byText("Sign up");
    private final By loginLink = byText("Sign in");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        super.open("/register");
        waitVisible(emailInput);
    }

    public void register(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public void navigateToLogin() {
        click(loginLink);
    }
}
