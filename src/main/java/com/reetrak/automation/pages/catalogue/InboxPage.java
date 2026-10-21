package com.reetrak.automation.pages.catalogue;

import com.reetrak.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InboxPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_CATALOGUE']/p")
    private WebElement catalogueMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Inbox']")
    private WebElement inboxMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Inbox']")
    private WebElement inboxPageName;

    @FindBy(xpath = "//button[contains(normalize-space(),'ALL - APPROVE')]")
    private WebElement allApproveButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'ALL - REVIEW')]")
    private WebElement allReviewButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'REFRESH')]")
    private WebElement refreshButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'Action')]")
    private WebElement actionButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'REMARK')]")
    private WebElement remarkButton;

    // Hierarchy Information dialog buttons
    @FindBy(xpath = "//jv-button[@label='INBOX.APPROVED']")
    private WebElement approveButton;

    @FindBy(xpath = "//jv-button[@label='INBOX.REJECTED']")
    private WebElement rejectButton;

    @FindBy(xpath = "//jv-button[@label='INBOX.REVIEWED']")
    private WebElement reviewButton;

    @FindBy(xpath = "//jv-button[@label='COMMON.CANCEL']")
    private WebElement cancelButton;

    @FindBy(xpath = "//input[@id='quickFilterInput']")
    private WebElement serachInput;

    @FindBy(xpath = "//button[@id='submitRemark']")
    private WebElement okButtonConfirmationMessage;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public void openInbox() {
        boolean isExpanded = false;
        try {
            isExpanded = inboxMenu.isDisplayed();
        } catch (Exception e) {
            isExpanded = false;
        }
        if (!isExpanded) {
            waitUtils.waitForVisibility(catalogueMenu);
            scrollToElement(catalogueMenu);
            click(catalogueMenu);
        }
        waitUtils.waitForVisibility(inboxMenu);
        scrollToElement(inboxMenu);
        click(inboxMenu);
        waitUtils.waitForVisibility(inboxPageName);
    }

    public String getInboxPageName() {
        return getText(inboxPageName);
    }

    public void filterRequest(String requestName) {
        WebElement filterInput = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        filterInput.clear();
        type(filterInput, requestName);
    }

    public void selectRowByRequestName(String requestName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row')) and .//*[contains(normalize-space(),'"
                                + requestName + "')]]")));
        scrollToElement(row);

        java.util.List<WebElement> cells = row
                .findElements(By.xpath(".//*[@role='gridcell' or contains(@class,'ag-cell') or self::td]"));
        WebElement target = cells.isEmpty() ? row : cells.get(0);

        new org.openqa.selenium.interactions.Actions(driver).moveToElement(target).click().perform();
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", target);
    }

    public void clickAction() {
        waitUtils.waitForVisibility(actionButton);
        click(actionButton);
    }

    // Used by product
    public void clickApprove() {
        waitUtils.waitForVisibility(approveButton);
        click(approveButton);
    }

    public void confirmOk() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"submitRemark\"]")));
        click(okButton);
    }

    public String getDisplayedBatchNumber() {
        WebElement dialog = waitForDialog("Hierarchy Information");
        return getFieldDisplayValue(dialog, "Batch Number");
    }

    private WebElement waitForDialog(String dialogTitle) {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//div[contains(@class,'cdk-overlay-pane') or contains(@class,'mat-dialog-container')]"
                                + "[.//*[normalize-space()='" + dialogTitle + "']]")));
    }

    private WebElement findFieldByLabel(WebElement scope, String label) {
        java.util.List<By> fieldLocators = java.util.Arrays.asList(
                By.xpath(".//mat-form-field[.//*[normalize-space()='" + label + "']][1]"),
                By.xpath(".//*[normalize-space()='" + label + "']/ancestor::mat-form-field[1]"),
                By.xpath(
                        ".//*[normalize-space()='" + label + "']/ancestor::div[contains(@class,'mat-form-field')][1]"));

        for (By locator : fieldLocators) {
            java.util.List<WebElement> fields = scope.findElements(locator);
            if (!fields.isEmpty()) {
                return fields.get(0);
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("Unable to locate field with label: " + label);
    }

    private String getFieldDisplayValue(WebElement scope, String label) {
        WebElement field = findFieldByLabel(scope, label);
        for (By locator : new By[] {
                By.xpath(".//input[1]"),
                By.xpath(".//*[@role='textbox'][1]"),
                By.xpath(".//span[contains(@class,'mat-select-value-text')][1]"),
                By.xpath(".//*[normalize-space() and not(self::label)][last()]") }) {
            java.util.List<WebElement> candidates = field.findElements(locator);
            for (WebElement candidate : candidates) {
                String value = candidate.getAttribute("value");
                if (value != null && !value.trim().isEmpty()) {
                    return value.trim();
                }
                value = candidate.getText();
                if (value != null && !value.trim().isEmpty() && !value.trim().equals(label)) {
                    return value.trim();
                }
            }
        }
        return "";
    }

    public WebElement getSelectedRow(String requestName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row')) and .//*[contains(normalize-space(),'"
                                + requestName + "')]]")));
    }

    public String getDisplayedFieldValue(String label) {
        WebElement dialog = waitForDialog("Hierarchy Information");
        return getFieldDisplayValue(dialog, label);
    }

    public void submitRemarks(String remarks) throws InterruptedException {
        RemarkandOK(driver, remarks);
    }

    public void approveRequest(String requestName, String remarks) throws InterruptedException {
        selectRowByRequestName(requestName);
        Thread.sleep(1000);
        clickAction();
        Thread.sleep(1500);
        clickApprove();
        confirmOk();
        RemarkandOK(driver, remarks);
    }

    public boolean isRequestPresent(String requestName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//*[(@role='row' or self::tr or contains(@class,'ag-row')) and .//*[contains(normalize-space(),'"
                                    + requestName + "')]]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
