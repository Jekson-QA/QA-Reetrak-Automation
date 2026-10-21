package com.reetrak.automation.pages;

import com.reetrak.automation.utilities.WaitUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitUtils waitUtils;

    @SuppressWarnings("this-escape")
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    protected void waitForLoaderToDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                    "//div[contains(@class,'ng-trigger-loaderInOut')] | //div[contains(@class,'loader')] | //*[contains(@class,'spinner')]")));
        } catch (Exception e) {
            // Ignore if loader was not present or already disappeared
        }
    }

    protected void type(WebElement element, String value) {
        waitForLoaderToDisappear();
        waitUtils.waitForVisibility(element).clear();
        element.sendKeys(value);
    }

    protected void typeAndEnter(WebElement element, String value) {
        type(element, value);
        element.sendKeys(Keys.ENTER);
    }

    protected void click(WebElement element) {
        waitForLoaderToDisappear();
        try {
            waitUtils.waitForElementToBeClickable(element).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("Standard click intercepted, retrying with JavaScript executor after a brief wait...");
            try {
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } catch (Exception jsException) {
                throw e;
            }
        }
    }

    protected String getText(WebElement element) {
        return waitUtils.waitForVisibility(element).getText().trim();
    }

    protected void selectFromDropDown(WebElement element, String value) {
        waitForLoaderToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        waitUtils.waitForElementToBeClickable(element);
        click(element);
        String noSpaceHyphen = value.replaceAll("\\s*-\\s*", "-");
        String poolNumberOnly = value.contains("-") ? value.substring(value.lastIndexOf("-") + 1).trim() : value;

        By optionLocator = By
                .xpath("//mat-option[contains(normalize-space(), '" + value + "')]"
                        + " | //mat-option[contains(normalize-space(), '" + noSpaceHyphen + "')]"
                        + " | //mat-option[contains(normalize-space(), '" + poolNumberOnly + "')]"
                        + " | //mat-option//span[contains(normalize-space(), '" + value + "')]"
                        + " | //mat-option//span[contains(normalize-space(), '" + noSpaceHyphen + "')]"
                        + " | //mat-option//span[contains(normalize-space(), '" + poolNumberOnly + "')]"
                        + " | //mat-option[normalize-space()='" + value + "']"
                        + " | //mat-option//span[normalize-space()='" + value + "']"
                        + " | //mat-option//span[normalize-space()='" + value.toUpperCase() + "']");
        boolean clicked = false;
        for (int attempts = 0; attempts < 3; attempts++) {
            try {
                wait.until(ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(optionLocator))).click();
                clicked = true;
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                System.out.println("Retrying click on option '" + value + "' due to stale element (Attempt "
                        + (attempts + 1) + ")");
            }
        }
        if (!clicked) {
            throw new IllegalStateException(
                    "Failed to click option '" + value + "' after retries due to StaleElementReferenceException.");
        }
        try {
            driver.switchTo().activeElement().sendKeys(Keys.TAB);
        } catch (Exception e) {
            System.out.println("Switching active element failed (likely due to native save dialog): " + e.getMessage());
        }
    }

    // --------------- Save As Popo-up Handling : Start ---------------//
    public void handleSaveAsPopup() {
        try {
            Thread.sleep(2000); // Wait for the Save As dialog to appear
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("Save As dialog confirmed using Robot class (pressed ENTER key).");
            Thread.sleep(3000); // Wait for download to complete
        } catch (Exception e) {
            System.out.println("Failed to handle Save As popup using Robot: " + e.getMessage());
        }
    }

    // --------------- Save As Popo-up Handling : Start ---------------//
    // --------------- Read Notifiction : Start ---------------//
    @FindBy(xpath = "//span[@data-notify='message']")
    private WebElement notification;

    public String getNotificationMessage() {
        waitUtils.waitForVisibility(notification);
        return getText(notification);
    }

    public void closeNotification() {
        try {
            WebElement closeBtn = driver
                    .findElement(By.xpath("//button[@data-notify='dismiss' or contains(@class,'close')]"));
            click(closeBtn);
            waitUtils.waitForInvisibility(notification);
        } catch (Exception e) {
            System.out.println("Notification close button error: " + e.getMessage());
        }
    }
    // --------------- Read Notifiction : End ---------------//

    // --------------- Remarks and Authentication : Start ---------------//
    @FindBy(xpath = ("//textarea[@id='remarks']"))
    WebElement Remarks;

    @FindBy(xpath = ("//button[@id='submitRemark']"))
    WebElement OK;

    // ----- Old Reeclient UI-------
    // @FindBy(xpath = ("//h4[@class='card-title']"))
    // WebElement RemarksTitle;

    // ----- New Reeclient UI-------
    @FindBy(xpath = ("//h4[.//span[contains(normalize-space(), 'Remarks')]]"))
    WebElement RemarksTitle;

    protected void RemarkandOK(WebDriver driver, String Remark) throws InterruptedException {
        this.driver = driver;
        waitForLoaderToDisappear();
        Thread.sleep(1000);
        PageFactory.initElements(driver, this);

        for (int i = 0; i < 5; i++) {
            try {
                PageFactory.initElements(driver, this);
                WebElement visibleOk = waitUtils.waitForVisibility(OK);
                if (visibleOk != null && visibleOk.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(
                        "RemarkandOK: Waiting for OK button, attempt " + (i + 1) + " failed: " + e.getMessage());
                Thread.sleep(500);
            }
        }

        for (int i = 0; i < 5; i++) {
            try {
                PageFactory.initElements(driver, this);
                WebElement visibleTitle = waitUtils.waitForVisibility(RemarksTitle);
                if (visibleTitle != null && visibleTitle.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(
                        "RemarkandOK: Waiting for RemarksTitle, attempt " + (i + 1) + " failed: " +
                                e.getMessage());
                Thread.sleep(500);
            }
        }

        for (int i = 0; i < 5; i++) {
            try {
                PageFactory.initElements(driver, this);
                type(Remarks, Remark);
                break;
            } catch (Exception e) {
                System.out.println("RemarkandOK: Typing remark, attempt " + (i + 1) + " failed: " + e.getMessage());
                Thread.sleep(500);
            }
        }

        Thread.sleep(2000);

        for (int i = 0; i < 5; i++) {
            try {
                PageFactory.initElements(driver, this);
                click(OK);
                break;
            } catch (Exception e) {
                System.out
                        .println("RemarkandOK: Clicking OK button, attempt " + (i + 1) + " failed: " + e.getMessage());
                Thread.sleep(500);
            }
        }

        Thread.sleep(1000);
    }
    // --------------- Remakrs and Authentication : End ---------------//

    // --------------- Scroll Logic : Start ---------------//
    protected void scrollToElement(WebElement element) {
        WebElement visibleElement = waitUtils.waitForVisibilityforscroll(element);
        if (visibleElement != null) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", visibleElement);
        }
    }
    // --------------- Scroll Logic : End ---------------//

    public void clickWithRetry(By locator) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        for (int i = 0; i < 3; i++) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                if (i == 2) {
                    throw e;
                }
                Thread.sleep(500);
            }
        }
    }

    // --------------- Date selection Logic : Start ---------------//

    public void setDateInput(WebElement inputElement, String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return;
        }

        LocalDate targetDate = parseDate(dateStr);
        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH))
                .toUpperCase(Locale.ENGLISH);

        scrollToElement(inputElement);
        waitUtils.waitForVisibility(inputElement);
        setAngularInputValue(inputElement, formattedDate);
    }

    protected void setAngularInputValue(WebElement inputElement, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "const input = arguments[0];"
                        + "const value = arguments[1];"
                        + "input.removeAttribute('readonly');"
                        + "input.focus();"
                        + "const setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                        + "setter.call(input, value);"
                        + "input.dispatchEvent(new InputEvent('input', { bubbles: true, inputType: 'insertText', data: value }));"
                        + "input.dispatchEvent(new Event('change', { bubbles: true }));"
                        + "input.dispatchEvent(new KeyboardEvent('keyup', { bubbles: true, key: 'Enter' }));"
                        + "input.dispatchEvent(new Event('blur', { bubbles: true }));",
                inputElement,
                value);
    }

    private LocalDate parseDate(String dateStr) {
        String[] patterns = { "dd-MM-yyyy", "dd/MM/yyyy", "yyyy-MM-dd", "dd-MMM-yyyy", "d-M-yyyy" };
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("Unable to parse date string: " + dateStr);
    }

    // --------------- Date selection Logic : End ---------------//

    /**
     * Restarts a system service by its name.
     * Supports Windows (using net stop and net start) and Unix-based OS (using
     * systemctl).
     *
     * @param serviceName The name of the service to restart
     */
    public void restartService(String serviceName) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                System.out.println("Stopping Windows service: " + serviceName);
                executeCommand(new String[] { "cmd.exe", "/c", "net", "stop", serviceName });

                System.out.println("Starting Windows service: " + serviceName);
                executeCommand(new String[] { "cmd.exe", "/c", "net", "start", serviceName });
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                System.out.println("Restarting Linux service: " + serviceName);
                executeCommand(new String[] { "sudo", "systemctl", "restart", serviceName });
            } else {
                throw new UnsupportedOperationException("Unsupported OS: " + os);
            }
            System.out.println("Successfully restarted service: " + serviceName);
            System.out.println("Waiting 10 seconds for web application startup after service start...");
            Thread.sleep(10000);
            waitForUrlToLoad();
        } catch (Exception e) {
            System.err.println("Failed to restart service: " + serviceName + ". Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to restart service: " + serviceName, e);
        }
    }

    private void waitForUrlToLoad() {
        String baseUrl = com.reetrak.automation.utilities.ConfigReader.get("base.url", "https://www.saucedemo.com/");
        if (baseUrl != null) {
            System.out.println("Waiting for application URL to be fully loaded and accessible: " + baseUrl);
            boolean accessible = false;
            long startTime = System.currentTimeMillis();
            long timeoutMillis = 300000; // 5 minutes timeout
            while (System.currentTimeMillis() - startTime < timeoutMillis) {
                try {
                    driver.get(baseUrl);
                    String readyState = (String) ((JavascriptExecutor) driver)
                            .executeScript("return document.readyState");
                    if ("complete".equalsIgnoreCase(readyState)) {
                        String title = driver.getTitle();
                        if (title != null && !title.toLowerCase().contains("error")
                                && !title.toLowerCase().contains("not found")
                                && !title.toLowerCase().contains("reached")) {
                            accessible = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("URL not accessible yet, retrying... (" + e.getMessage() + ")");
                }
                try {
                    Thread.sleep(3000); // Wait 3 seconds before next retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting for URL accessibility", ie);
                }
            }
            if (!accessible) {
                throw new RuntimeException("Application URL did not become accessible within timeout: " + baseUrl);
            }
            System.out.println("Application URL is now fully loaded and accessible.");
        }
    }

    private void executeCommand(String[] command) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // Read command output to prevent hanging and log details
        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[ServiceManager] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Command failed with exit code: " + exitCode);
        }
    }

    // --------------- Database Helper Logic - Start --------------- //

    protected String getDbSettingValue(String tableName, String keyColumn, String valueColumn, String keyValue) {
        return com.reetrak.automation.utilities.DatabaseUtils.getDbSettingValue(tableName, keyColumn, valueColumn,
                keyValue);
    }

    // --------------- Database Helper Logic - End --------------- //

}
