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

public class ExportBatchDetailsPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Export Batch Detail']")
    private WebElement exportBatchDetailMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Export Batch Detail']")
    private WebElement exportBatchDetailPageName;

    @FindBy(xpath = "//input[contains(@placeholder,'From Date') or @formcontrolname='fromDate' or @id='fromDate'] | //mat-form-field[.//mat-label[contains(text(), 'From Date')]]//input")
    private WebElement fromDateInput;

    @FindBy(xpath = "//input[contains(@placeholder,'To Date') or @formcontrolname='toDate' or @id='toDate'] | //mat-form-field[.//mat-label[contains(text(), 'To Date')]]//input")
    private WebElement toDateInput;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Export Format') or @formcontrolname='exportFormatType' or .//span[normalize-space()='Export Format Type']]")
    private WebElement exportFormatTypeSelect;

    @FindBy(xpath = "//mat-select[(contains(@placeholder,'Status') and not(contains(@placeholder,'Posting'))) or @formcontrolname='status' or .//span[normalize-space()='Status']]")
    private WebElement statusSelect;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Posting Status') or @formcontrolname='postingStatus' or .//span[normalize-space()='Posting Status']]")
    private WebElement postingStatusSelect;

    @FindBy(xpath = "//button[normalize-space()='FILTER' or normalize-space()='Filter' or @id='filter']")
    private WebElement filterButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Batch') or @formcontrolname='batch' or @formcontrolname='batchName' or @formcontrolname='batchId' or .//span[normalize-space()='Select Batch']]")
    private WebElement selectBatchSelect;

    @FindBy(xpath = "//div[contains(@class,'ag-header-select-all')]//span | //input[@type='checkbox' and contains(@id,'select-all')] | //mat-checkbox[contains(@id,'select-all') or contains(@class,'mat-column-select')]")
    private WebElement selectAllCheckbox;

    @FindBy(xpath = "//*[@id=\"reportGenerate\"]")
    private WebElement generateButton;

    public ExportBatchDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void openExportBatchDetail() {
        waitUtils.waitForVisibility(productionMasterMenu);
        scrollToElement(productionMasterMenu);
        click(productionMasterMenu);
        waitUtils.waitForVisibility(exportBatchDetailMenu);
        scrollToElement(exportBatchDetailMenu);
        click(exportBatchDetailMenu);
        waitUtils.waitForVisibility(exportBatchDetailPageName);
    }

    public String getExportBatchDetailPageName() {
        return getText(exportBatchDetailPageName);
    }

    private void selectFromDropDownCustom(WebElement selectElement, String value) {
        if (value != null && !value.isBlank()) {
            value = value.trim();
            waitForLoaderToDisappear();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            waitUtils.waitForElementToBeClickable(selectElement);
            click(selectElement);

            By optionLocator = By.xpath(
                    "//div[contains(@class,'cdk-overlay-pane')]//mat-option[.//span[normalize-space()='" + value
                            + "'] or normalize-space()='" + value + "']"
                            + " | //div[contains(@class,'cdk-overlay-pane')]//mat-option[.//span[normalize-space()='"
                            + value.toUpperCase() + "'] or normalize-space()='" + value.toUpperCase() + "']"
                            + " | //mat-option//span[normalize-space()='" + value + "']"
                            + " | //mat-option//span[normalize-space()='" + value.toUpperCase() + "']"
                            + " | //mat-option[normalize-space()='" + value + "']");
            wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
            try {
                driver.switchTo().activeElement().sendKeys(Keys.TAB);
            } catch (Exception e) {
                System.out.println("Switching active element failed: " + e.getMessage());
            }
        }
    }

    public void selectExportFormatType(String value) {
        selectFromDropDownCustom(exportFormatTypeSelect, value);
    }

    public void selectStatus(String value) {
        selectFromDropDownCustom(statusSelect, value);
    }

    public void selectPostingStatus(String value) {
        selectFromDropDownCustom(postingStatusSelect, value);
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

    public void clickFilter() {
        waitUtils.waitForElementToBeClickable(filterButton);
        scrollToElement(filterButton);
        click(filterButton);
        waitForSeconds(2);
    }

    public void selectBatch(String batchNumber) {
        if (batchNumber != null && !batchNumber.isBlank()) {
            batchNumber = batchNumber.trim();
            openDropdown(selectBatchSelect);

            By optionLocator = By.xpath("//mat-option//span[contains(normalize-space(), '" + batchNumber
                    + "')] | //mat-option[contains(normalize-space(), '" + batchNumber + "')]");
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                option.click();
            } catch (Exception e) {
                try {
                    List<WebElement> options = driver.findElements(By.xpath("//mat-option"));
                    if (!options.isEmpty()) {
                        options.get(0).click();
                    }
                } catch (Exception ex) {
                    System.out.println("Selecting batch option fallback skipped: " + ex.getMessage());
                }
            }
        }
    }

    public void clickSelectAll() {
        By xpathLocator = By.xpath(
                "//div[contains(@class,'ag-header-select-all')]//span"
                + " | //div[contains(@class,'ag-header-select-all')]"
                + " | //div[contains(@class,'ag-header-cell')]//span[contains(@class,'ag-icon-checkbox')]"
                + " | //input[@type='checkbox' and contains(@id,'select-all')]"
                + " | //mat-checkbox[contains(@id,'select-all') or contains(@class,'mat-column-select') or contains(.,'Select All') or contains(.,'select-all')]"
                + " | //div[contains(@class,'ag-row')]//span[contains(@class,'ag-icon-checkbox')]"
                + " | //div[contains(@class,'ag-selection-checkbox')]"
                + " | //input[@type='checkbox']"
        );
        try {
            List<WebElement> elements = driver.findElements(xpathLocator);
            if (!elements.isEmpty()) {
                WebElement selectAll = elements.get(0);
                if (selectAll.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectAll);
                }
            }
        } catch (Exception e) {
            System.out.println("Select all checkbox interaction bypassed: " + e.getMessage());
        }
    }

    public void clickGenerate() throws InterruptedException {
        try {
            scrollToElement(generateButton);
            click(generateButton);
        } catch (Exception e) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", generateButton);
            } catch (Exception ex) {
                System.out.println("Click generate fallback failed: " + ex.getMessage());
            }
        }
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

    public boolean checkAndHandleEventsNotConfigured(String serviceName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            By locator = By.xpath(
                    "//div[contains(@class,'alert-danger') or contains(@class,'danger') or contains(@class,'alert')]"
                    + " | //span[@data-notify='message']"
                    + " | //*[contains(text(),'Events are not configured') or contains(text(),'not configured for the batch')]"
            );
            List<WebElement> elements = driver.findElements(locator);
            for (WebElement el : elements) {
                if (el.isDisplayed()) {
                    String msg = el.getText().trim();
                    if (msg.toLowerCase().contains("events are not configured") || msg.toLowerCase().contains("not configured for the batch")) {
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("ERROR NOTIFICATION DETECTED: " + msg);
                        System.out.println("1. Updating epcis_event configuration in database...");
                        com.reetrak.automation.utilities.BatchRequestDatabaseUtils.updateEpcisEventConfiguration();

                        System.out.println("2. Restarting server service: " + serviceName + "...");
                        restartService(serviceName);

                        System.out.println("3. Server restart complete and URL accessible.");
                        System.out.println("------------------------------------------------------------------");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // Notification did not appear or was not an event configuration error
        }
        return false;
    }

    public List<String> getDropdownOptions(String dropdownName) {
        WebElement selectElement;
        if ("Export Format Type".equalsIgnoreCase(dropdownName)) {
            selectElement = exportFormatTypeSelect;
        } else if ("Status".equalsIgnoreCase(dropdownName)) {
            selectElement = statusSelect;
        } else if ("Posting Status".equalsIgnoreCase(dropdownName)) {
            selectElement = postingStatusSelect;
        } else if ("Select Batch".equalsIgnoreCase(dropdownName)) {
            selectElement = selectBatchSelect;
        } else {
            throw new IllegalArgumentException("Unknown dropdown name: " + dropdownName);
        }

        scrollToElement(selectElement);
        click(selectElement);
        waitForSeconds(1);

        List<WebElement> options = driver.findElements(By.xpath("//mat-option//span[normalize-space()]"));
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : options) {
            String text = option.getText().trim();
            if (!text.isEmpty()) {
                optionTexts.add(text);
            }
        }

        // Close dropdown
        driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
        waitForSeconds(1);

        return optionTexts;
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
