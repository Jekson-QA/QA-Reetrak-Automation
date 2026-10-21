package com.reetrak.automation.pages.productionmaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManageEventsPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Manage Events']")
    private WebElement manageEventsMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Manage Events'] | //span[normalize-space()='Manage Events']")
    private WebElement manageEventsPageName;

    @FindBy(xpath = "//input[contains(@placeholder,'From Date') or @formcontrolname='fromDate' or @id='fromDate'] | //mat-form-field[.//mat-label[contains(text(), 'From Date')]]//input")
    private WebElement fromDateInput;

    @FindBy(xpath = "//input[contains(@placeholder,'To Date') or @formcontrolname='toDate' or @id='toDate'] | //mat-form-field[.//mat-label[contains(text(), 'To Date')]]//input")
    private WebElement toDateInput;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Line') or @formcontrolname='line' or @formcontrolname='lineId' or @formcontrolname='lineName' or .//span[normalize-space()='Line']]")
    private WebElement lineSelect;

    @FindBy(xpath = "//button[normalize-space()='BATCH' or normalize-space()='Batch' or contains(., 'BATCH') or contains(., 'Batch')]")
    private WebElement batchButton;

    @FindBy(xpath = "//button[normalize-space()='RESET' or normalize-space()='Reset' or contains(., 'RESET') or contains(., 'Reset')]")
    private WebElement resetButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Batch') or @formcontrolname='batch' or @formcontrolname='batchName' or @formcontrolname='batchId' or .//span[normalize-space()='Select Batch']]")
    private WebElement selectBatchSelect;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Generated Type') or @formcontrolname='generatedType' or @formcontrolname='generationType' or .//span[normalize-space()='Generated Type']]")
    private WebElement generatedTypeSelect;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Export Format') or @formcontrolname='exportFormat' or @formcontrolname='exportFormatType' or .//span[normalize-space()='Export Format']]")
    private WebElement exportFormatSelect;

    @FindBy(xpath = "//button[normalize-space()='POST' or normalize-space()='Post' or contains(., 'POST') or contains(., 'Post')]")
    private WebElement postButton;

    @FindBy(xpath = "//button[normalize-space()='CANCEL' or normalize-space()='Cancel' or contains(., 'CANCEL') or contains(., 'Cancel')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Event') or @formcontrolname='event' or @formcontrolname='eventType' or .//span[normalize-space()='Event']]")
    private WebElement eventSelect;

    @FindBy(xpath = "//button[normalize-space()='CODE LIST' or normalize-space()='Code List' or contains(., 'CODE LIST') or contains(., 'Code List')]")
    private WebElement codeListButton;

    @FindBy(xpath = "//mat-checkbox[contains(., 'Select/Select All') or contains(., 'select-all')]")
    private WebElement selectAllCodesCheckbox;

    public ManageEventsPage(WebDriver driver) {
        super(driver);
    }

    public void openManageEvents() {
        waitUtils.waitForVisibility(productionMasterMenu);
        scrollToElement(productionMasterMenu);
        click(productionMasterMenu);
        waitUtils.waitForVisibility(manageEventsMenu);
        scrollToElement(manageEventsMenu);
        click(manageEventsMenu);
        waitUtils.waitForVisibility(manageEventsPageName);
    }

    public String getManageEventsPageName() {
        return getText(manageEventsPageName);
    }

    public void setFromDate(String date) {
        if (date != null && !date.isBlank()) {
            scrollToElement(fromDateInput);
            click(fromDateInput);
            fromDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            fromDateInput.sendKeys(date + Keys.ENTER);
        }
    }

    public void setToDate(String date) {
        if (date != null && !date.isBlank()) {
            scrollToElement(toDateInput);
            click(toDateInput);
            toDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            toDateInput.sendKeys(date);
        }
    }

    public void selectLine(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(lineSelect, value);
        }
    }

    public void clickBatch() {
        waitUtils.waitForElementToBeClickable(batchButton);
        scrollToElement(batchButton);
        click(batchButton);
        waitForSeconds(2);
    }

    public void clickReset() {
        waitUtils.waitForElementToBeClickable(resetButton);
        scrollToElement(resetButton);
        click(resetButton);
        waitForSeconds(2);
    }

    public void selectBatch(String batchNumber) {
        if (batchNumber != null && !batchNumber.isBlank()) {
            openDropdown(selectBatchSelect);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//div[contains(@class,'cdk-overlay-pane')]//input[@placeholder='Find Batch...' or contains(@placeholder,'Batch')] | //input[@placeholder='Find Batch...'] | (//div[contains(@class,'cdk-overlay-pane')]//input)[last()]")));
                click(searchInput);
                searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                searchInput.sendKeys(batchNumber);
                waitForSeconds(1);
            } catch (Exception e) {
                System.out.println("Could not use search filter: " + e.getMessage());
            }
            WebElement option;
            try {
                option = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-option//span[contains(normalize-space(), '" + batchNumber
                                + "')] | //mat-option[contains(normalize-space(), '" + batchNumber + "')]")));
            } catch (Exception e) {
                option = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-option")));
            }
            click(option);
            waitForSeconds(1);
        }
    }

    public void selectGeneratedType(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(generatedTypeSelect, value);
        }
    }

    public void selectExportFormat(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(exportFormatSelect, value);
        }
    }

    public void clickPost() {
        waitUtils.waitForElementToBeClickable(postButton);
        scrollToElement(postButton);
        click(postButton);
        waitForSeconds(2);
    }

    public void clickCancel() {
        waitUtils.waitForElementToBeClickable(cancelButton);
        scrollToElement(cancelButton);
        click(cancelButton);
        waitForSeconds(2);
    }

    public void selectEvent(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(eventSelect, value);
        }
    }

    public void clickCodeList() {
        waitUtils.waitForElementToBeClickable(codeListButton);
        scrollToElement(codeListButton);
        click(codeListButton);
        waitForSeconds(2);
    }

    public void clickSelectAllCodes() {
        waitUtils.waitForElementToBeClickable(selectAllCodesCheckbox);
        scrollToElement(selectAllCodesCheckbox);
        click(selectAllCodesCheckbox);
        waitForSeconds(1);
    }

    public void handlePostConfirmation(String remarks) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement submitRemarkBtn = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='submitRemark']")));
            if (submitRemarkBtn.isDisplayed()) {
                RemarkandOK(driver, remarks);
            }
        } catch (Exception e) {
            try {
                WebElement okBtn = driver.findElement(By.xpath("//button[normalize-space()='OK']"));
                if (okBtn.isDisplayed()) {
                    click(okBtn);
                }
            } catch (Exception ex) {
                // No dialog appeared
            }
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            String msg = getNotificationMessage();
            System.out.println("Notification message: " + msg);
            return msg.toLowerCase().contains("success") || msg.toLowerCase().contains("successfully")
                    || msg.toLowerCase().contains("posted");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNotificationGreen() {
        try {
            WebElement notifyContainer = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                            "//div[contains(@class,'alert-success') or contains(@class,'success') or contains(@style,'green')]")));
            return notifyContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getValidationErrors() {
        List<WebElement> errors = driver.findElements(By.xpath(
                "//mat-error | //div[contains(@class,'invalid-feedback') or contains(text(),'required') or contains(text(),'required')]"));
        List<String> errorTexts = new ArrayList<>();
        for (WebElement error : errors) {
            if (error.isDisplayed()) {
                errorTexts.add(error.getText().trim());
            }
        }
        return errorTexts;
    }

    private void openDropdown(WebElement dropdown) {
        scrollToElement(dropdown);
        waitUtils.waitForVisibility(dropdown);
        try {
            waitUtils.waitForElementToBeClickable(dropdown).click();
        } catch (Exception clickException) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
        }
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
