package com.reetrak.automation.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(120);

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public Boolean waitForInvisibility(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForTitleContains(String titlePart) {
        return wait.until(ExpectedConditions.titleContains(titlePart));
    }

// ---------------------- Custom wait - Start ---------------------
    public WebElement customewaitForVisibility(WebElement element, int seconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return customWait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForVisibilityforscroll(WebElement element) {
        try {
            Thread.sleep(500);
            if (!element.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                return wait.until(ExpectedConditions.visibilityOf(element));
            }
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            return null;
        }
    }
// ---------------------- Custom wait - End -----------------------
}