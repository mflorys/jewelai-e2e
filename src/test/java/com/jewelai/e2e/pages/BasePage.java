package com.jewelai.e2e.pages;

import com.jewelai.e2e.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected final WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        long timeout = Long.parseLong(Config.getProperty("timeout"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected By byText(String text) {
        return By.xpath("//*[normalize-space(text())='" + text + "']");
    }

    public void open(String path) {
        String baseUrl = Config.getProperty("baseUrl");
        String url = path.startsWith("http") ? path : baseUrl + path;
        driver.get(url);
    }

    public WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        waitVisible(locator).click();
    }

    public void type(By locator, String value) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(value);
    }

    public String text(By locator) {
        return waitVisible(locator).getText();
    }

    public void waitUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public String getLocalStorageItem(String key) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object value = js.executeScript("return window.localStorage.getItem(arguments[0]);", key);
        return value == null ? null : value.toString();
    }
}
