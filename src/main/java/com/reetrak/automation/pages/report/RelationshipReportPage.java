package com.reetrak.automation.pages.report;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.reetrak.automation.pages.BasePage;

public class RelationshipReportPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_REPORT_MASTER']/p | //span[normalize-space()='Report Master']")
    private WebElement reportMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Relationship Report'] | //span[normalize-space()='Relationship Report']")
    private WebElement relationshipReportMenu;

    @FindBy(xpath = "//*[@id=\"top\"]/div/div[1]/a/span")
    private WebElement relationshipReportPageName;

    @FindBy(xpath = "//input[contains(@placeholder,'From Date') or @formcontrolname='fromDate' or @id='fromDate'] | //mat-form-field[.//mat-label[contains(text(), 'From Date')]]//input")
    private WebElement fromDateInput;

    @FindBy(xpath = "//input[contains(@placeholder,'To Date') or @formcontrolname='toDate' or @id='toDate'] | //mat-form-field[.//mat-label[contains(text(), 'To Date')]]//input")
    private WebElement toDateInput;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Line') or @formcontrolname='line' or @formcontrolname='lineId' or @formcontrolname='lineName' or .//span[normalize-space()='Select Line']]")
    private WebElement selectLineSelect;

    @FindBy(xpath = "//button[normalize-space()='FILTER' or normalize-space()='Filter']")
    private WebElement filterButton;

    @FindBy(xpath = "//button[normalize-space()='RESET' or normalize-space()='Reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Batch') or @formcontrolname='batch' or @formcontrolname='batchId' or .//span[normalize-space()='Select Batch']]")
    private WebElement selectBatchSelect;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Batch Level') or @formcontrolname='batchLevel' or @formcontrolname='batchLevelId' or .//span[normalize-space()='Batch Level']]")
    private WebElement batchLevelSelect;

    @FindBy(xpath = "//input[@id='code' or contains(@placeholder,'Code') or @formcontrolname='code'] | //mat-form-field[.//mat-label[contains(text(), 'Code')]]//input")
    private WebElement codeInput;

    @FindBy(xpath = "//button[normalize-space()='GENERATE' or normalize-space()='Generate']")
    private WebElement generateButton;

    @FindBy(xpath = "//button[contains(., 'CSV') or contains(., 'csv')] | //span[contains(text(), 'CSV')]")
    private WebElement csvButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Salable Selection') or @formcontrolname='salable' or @formcontrolname='salableSelection' or .//span[normalize-space()='Salable Selection']]")
    private WebElement salableSelectionSelect;

    public RelationshipReportPage(WebDriver driver) {
        super(driver);
    }

    public void openRelationshipReport() {
        waitUtils.waitForVisibility(reportMasterMenu);
        click(reportMasterMenu);

        // Wait for submenu expansion animation to complete
        waitForSeconds(1);

        // Send PageDown key to scroll the sidebar master menu down once
        try {
            new org.openqa.selenium.interactions.Actions(driver)
                    .sendKeys(Keys.PAGE_DOWN)
                    .perform();
            waitForSeconds(1);
        } catch (Exception e) {
            System.out.println("Could not send PAGE_DOWN via Actions: " + e.getMessage());
        }

        waitUtils.waitForVisibility(relationshipReportMenu);
        click(relationshipReportMenu);
        waitUtils.waitForVisibility(relationshipReportPageName);
    }

    public String getRelationshipReportPageName() {
        return getText(relationshipReportPageName);
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
            selectFromDropDown(selectLineSelect, value);
        }
    }

    public void clickFilter() {
        waitUtils.waitForElementToBeClickable(filterButton);
        scrollToElement(filterButton);
        click(filterButton);
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

    public void selectBatchLevel(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(batchLevelSelect, value);
        }
    }

    public void setCode(String code) {
        if (code != null && !code.isBlank()) {
            scrollToElement(codeInput);
            type(codeInput, code);
        }
    }

    public void clickGenerate() {
        waitUtils.waitForElementToBeClickable(generateButton);
        scrollToElement(generateButton);
        click(generateButton);
        waitForSeconds(3); // wait for report dialog popup to render
    }

    public void clickCSV() {
        waitUtils.waitForElementToBeClickable(csvButton);
        scrollToElement(csvButton);
        click(csvButton);
        waitForSeconds(2); // wait for Salable Selection dialog
    }

    public void selectSalableSelection(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(salableSelectionSelect, value);
        }
    }

    public void submitSalableSelection() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Wait for a button inside the overlay dialog/pane that is not CANCEL to be clickable
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//button[not(contains(translate(text(), 'CANCEL', 'cancel'), 'cancel'))]"
            )));
            click(submitBtn);
        } catch (Exception e) {
            System.out.println("Warning: Dynamic wait for non-CANCEL submit button failed, falling back: " + e.getMessage());
            try {
                WebElement submitBtn = driver.findElement(By.xpath(
                        "(//*[contains(@class,'cdk-overlay-pane') or contains(@class,'dialog') or contains(@class,'modal') or self::mat-dialog-container]//*[not(contains(translate(text(), 'CANCEL', 'cancel'), 'cancel')) and not(contains(translate(@value, 'CANCEL', 'cancel'), 'cancel')) and (normalize-space()='POST' or normalize-space()='Post' or normalize-space()='post' or normalize-space()='OK' or normalize-space()='Ok' or normalize-space()='ok' or normalize-space()='SUBMIT' or normalize-space()='Submit' or normalize-space()='submit' or normalize-space()='EXPORT' or normalize-space()='Export' or normalize-space()='export' or normalize-space()='DOWNLOAD' or normalize-space()='Download' or normalize-space()='download' or normalize-space()='SAVE' or normalize-space()='Save' or normalize-space()='save' or normalize-space()='CONFIRM' or normalize-space()='Confirm' or normalize-space()='confirm' or normalize-space(@value)='POST' or normalize-space(@value)='Post' or normalize-space(@value)='post' or normalize-space(@value)='OK' or normalize-space(@value)='Ok' or normalize-space(@value)='ok' or normalize-space(@value)='SUBMIT' or normalize-space(@value)='Submit' or normalize-space(@value)='submit' or normalize-space(@value)='EXPORT' or normalize-space(@value)='Export' or normalize-space(@value)='export' or normalize-space(@value)='DOWNLOAD' or normalize-space(@value)='Download' or normalize-space(@value)='download' or normalize-space(@value)='SAVE' or normalize-space(@value)='Save' or normalize-space(@value)='save' or normalize-space(@value)='CONFIRM' or normalize-space(@value)='Confirm' or normalize-space(@value)='confirm' or @id='submit' or @id='ok' or @id='submitRemark')])[last()]"));
                click(submitBtn);
            } catch (Exception ex) {
                System.out.println("Warning: Fallback submit button click also failed: " + ex.getMessage());
            }
        }
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