package com.reetrak.automation.pages.productionmaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;

public class BatchRequestPage extends BasePage {

    private static final List<String> BATCH_STATUSES = Arrays.asList(
            "Ready For Transfer",
            "Ready for transfer",
            "Running",
            "Temporary Closed",
            "Re-Initiate for extra quantity");

    private String selectedPONumber;
    private String selectedBatchStatus;


    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Batch Request']")
    private WebElement batchRequestMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Batch Request']")
    private WebElement batchRequestPageName;

    @FindBy(xpath = "//*[@id=\"load\"]")
    private WebElement loadBatchButton;

    @FindBy(xpath = "//*[@id=\"transferBatch\"]")
    private WebElement transferButton;

    @FindBy(xpath = "//*[@id=\"retransferBatch\"]/span/span")
    private WebElement reTransferButton;

    @FindBy(xpath = "//*[@id=\"closeBatch\"]")
    private WebElement closeAndReopenButton;
    
    @FindBy(xpath = "//*[@id=\"editBatch\"]")
    private WebElement editButton;

    @FindBy(xpath = "//*[@id=\"save\"]")
    private WebElement OKbuttonRetransfer;

    @FindBy(xpath = "//*[@id=\"submit\"]")
    private WebElement OKbutReopen;

    public BatchRequestPage(WebDriver driver) {
        super(driver);
    }

    public void openBatchRequest() {
        boolean isExpanded = false;
        try {
            isExpanded = batchRequestMenu.isDisplayed();
        } catch (Exception e) {
            isExpanded = false;
        }
        if (!isExpanded) {
            waitUtils.waitForVisibility(productionMasterMenu);
            scrollToElement(productionMasterMenu);
            click(productionMasterMenu);
        }
        scrollToElement(batchRequestMenu);
        click(batchRequestMenu);
        waitUtils.waitForVisibility(batchRequestPageName);
    }

    public String getBatchRequestPageName() {
        return getText(batchRequestPageName);
    }

    public void clickLoadBatch() {
        waitUtils.waitForVisibility(loadBatchButton);
        click(loadBatchButton);
        waitForDialogTitle("PO Details");
    }

    public String getPODetailsDialogTitle() {
        return waitForDialogTitle("PO Details").getText().trim();
    }
    public void selectPOByFilterOnly(String poNumber) {
    WebElement dialog = waitForDialog("PO Details");
    WebElement filterInput = dialog.findElement(By.xpath(
            ".//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']"));
    scrollToElement(filterInput);
    type(filterInput, poNumber);
    }
    //Used By P2 Backup
    public void selectPOByFilter(String poNumber) {
    WebElement dialog = waitForDialog("PO Details");
    WebElement filterInput = dialog.findElement(By.xpath(
            ".//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']"));
    scrollToElement(filterInput);
    type(filterInput, poNumber);
    WebElement row = waitForPORow(poNumber);
    WebElement targetCell = row.findElement(By.xpath(".//*[normalize-space()='" + poNumber + "']"));
    scrollToElement(row);
    new Actions(driver).moveToElement(targetCell).click().perform();
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);
    waitForSelectedPORow(poNumber);
    }

    public void selectMultiplePOs(List<String> poNumbers) {
        WebElement dialog = waitForDialog("PO Details");
        WebElement filterInput = dialog.findElement(By.xpath(
                ".//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']"));
        for (String po : poNumbers) {
            scrollToElement(filterInput);
            filterInput.clear();
            type(filterInput, po);
            WebElement row = waitForPORow(po);
            WebElement targetCell = row.findElement(By.xpath(".//*[normalize-space()='" + po + "']"));
            scrollToElement(row);
            new Actions(driver).moveToElement(targetCell).click().perform();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", row);
            waitForSelectedPORow(po);
        }
    }

    public void clickPODetailsSave() {
        clickDialogButton("PO Details");
        waitForDialogTitle("Batch Information");
    }

    public String getBatchInformationDialogTitle() {
        return waitForDialogTitle("Batch Information").getText().trim();
    }

    public String getDisplayedLineName() {
        WebElement dialog = waitForDialog("Batch Information");
        WebElement field = findFieldByLabel(dialog, "Line");

        for (By candidateLocator : new By[] {
                By.xpath(".//input[1]"),
                By.xpath(".//*[@role='textbox'][1]"),
                By.xpath(".//span[contains(@class,'mat-select-value-text')][1]"),
                By.xpath(".//*[normalize-space()][1]") }) {
            List<WebElement> candidates = field.findElements(candidateLocator);
            for (WebElement candidate : candidates) {
                String value = candidate.getAttribute("value");
                if (value != null && !value.trim().isEmpty()) {
                    return value.trim();
                }
                value = candidate.getText();
                if (value != null && !value.trim().isEmpty()) {
                    return value.trim();
                }
            }
        }

        throw new IllegalStateException("Unable to read Batch Information line value.");
    }

    public void selectBatchInformationLine(String line) {
        WebElement dialog = waitForDialog("Batch Information");
        selectFieldValueIfPresent(dialog, "Line", line);
    }

    public void clickBatchInformationSave() {
        clickDialogButton("Batch Information");
        waitForDialogTitle("Batch Confirmation");
    }

    public String getBatchConfirmationDialogTitle() {
        return waitForDialogTitle("Batch Confirmation").getText().trim();
    }
    //Used By P2
    public void selectBatchConfirmationMode(String mode) {
        WebElement dialog = waitForDialog("Batch Confirmation");
        selectFieldValueIfPresent(dialog, "Mode", mode);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }

    public void clickBatchConfirmationSave() {
        WebElement dialog = waitForDialog("Batch Confirmation");
        WebElement button = dialog.findElement(By.xpath("//*[@id=\"update\"]"));
        scrollToElement(button);
        click(button);
    }

    public void clickBatchConfirmationSaveAndSubmitRemarks(String remarks) {
        clickBatchConfirmationSave();
        submitRemarks(remarks);
    }

    public String getNotificationMessage() {
        return super.getNotificationMessage();
    }


    public void selectBatchByPONumber(String poNumber) {
        WebElement filterInput = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        type(filterInput, poNumber);

        WebElement row = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and .//*[normalize-space()='" + poNumber + "']]")));
        scrollToElement(row);
        new Actions(driver).moveToElement(row).click().perform();
        selectedPONumber = poNumber;
    }
    public void selectBatchByPONumberP2(String poNumber) {
        WebElement filterInput = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        type(filterInput, poNumber);
        selectedPONumber = poNumber;
    }
    
    public void selectBatchByPONumberAndStatus(String poNumber, String status) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement filterInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        filterInput.clear();
        type(filterInput, poNumber);
        WebElement row = findBatchRowByPONumberAndStatus(poNumber, status);
        scrollToElement(row);
        selectBatchRow(row);
        selectedPONumber = poNumber;
        selectedBatchStatus = status;
        wait.until(ExpectedConditions.elementToBeClickable(transferButton));
    }
    public void selectBatchByPONumberAndStatusForEdit(String poNumber, String status) {
        WebElement filterInput = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        filterInput.clear();
        type(filterInput, poNumber);
        
        WebElement row = findBatchRowByPONumberAndStatus(poNumber, status);
        scrollToElement(row);
        selectBatchRow(row);
        selectedPONumber = poNumber;
        selectedBatchStatus = status;
    }
    
    private void selectBatchRow(WebElement row) {
        scrollToElement(row);
        List<WebElement> cells = row.findElements(By.xpath(".//*[@role='gridcell' or self::td]"));
        WebElement target = cells.isEmpty() ? row : cells.get(0);
        new Actions(driver).moveToElement(target).click().perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", target);
    }
     private void waitForSelectedBatchRow(String poNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and (contains(@class,'selected')"
                                + " or contains(@class,'ag-row-focus')"
                                + " or contains(@class,'ag-row-selected')"
                                + " or @aria-selected='true')"
                                + " and .//*[normalize-space()=" + xpathLiteral(poNumber) + "]]")),
                ExpectedConditions.elementToBeClickable(transferButton)));
    }

    public void selectFirstBatchWithStatus(String PONUMBER) {
        WebElement row = findBatchRowByStatus(PONUMBER);
        scrollToElement(row);
        click(row);
        click(row);
    }
    public void selectFirstBatchWithPOName(String status) {
        WebElement row = findBatchRowByStatus(status);
        scrollToElement(row);
        click(row);
        click(row);
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'");
        StringBuilder literal = new StringBuilder("concat(");
        for (int index = 0; index < parts.length; index++) {
            if (index > 0) {
                literal.append(", \"'\", ");
            }
            literal.append("'").append(parts[index]).append("'");
        }
        literal.append(")");
        return literal.toString();
    }
    public void selectFirstBatchWithoutStatus(String status) {
        WebElement row = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr)"
                                + " and (.//*[normalize-space()='Running'"
                                + " or normalize-space()='Ready For Transfer'"
                                + " or normalize-space()='Ready for transfer'"
                                + " or normalize-space()='Temporary Closed'"
                                + " or contains(normalize-space(),'Re-Initiate for extra quantity')])"
                                + " and not(.//*[normalize-space()='" + status + "'])]")));
        scrollToElement(row);
        click(row);
    }

    public String getSelectedBatchStatusP7() {
        if (selectedPONumber != null && !selectedPONumber.isBlank()) {
            if (selectedBatchStatus != null && !selectedBatchStatus.isBlank()) {
                return getRowStatus(findBatchRowByPONumberAndStatus(selectedPONumber, selectedBatchStatus));
            }
            return getRowStatus(findBatchRowByPONumber(selectedPONumber));
        }

        WebElement selectedRow = getSelectedBatchRow();
        return getRowStatus(selectedRow);
    }
    public String getSelectedBatchStatus() {
        WebElement selectedRow = getSelectedBatchRow();
        return getRowStatus(selectedRow);
    }
    public void waitForBatchStatus(String expectedStatus) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver -> {
            try {
                String actualStatus = getSelectedBatchStatus();
                return expectedStatus.equalsIgnoreCase(actualStatus);
            } catch (Exception e) {
                return false;
            }
        });
    }
    public void clickTransfer() {
        if (selectedPONumber != null && !selectedPONumber.isBlank()) {
            if (selectedBatchStatus != null && !selectedBatchStatus.isBlank()) {
                selectBatchRow(findBatchRowByPONumberAndStatus(selectedPONumber, selectedBatchStatus));
            } else {
                selectBatchRow(findBatchRowByPONumber(selectedPONumber));
            }
        }
        scrollToElement(transferButton);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(transferButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", transferButton);
    }


    public String getTransferBatchDetailsDialogTitle() {
        return waitForDialogTitle("Transfer Batch Details").getText().trim();
    }

    public void selectTransferBatchDetailsModeAndLine(String mode, String line) {
        WebElement dialog = waitForDialog("Transfer Batch Details");
        selectFieldValueIfPresent(dialog, "Line", line);
        selectFieldValueIfPresent(dialog, "Mode", mode);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }
    public void selectTransferBatchDetailsMode(String mode) {
        WebElement dialog = waitForDialog("Batch Confirmation");
        selectFieldValueIfPresent(dialog, "Mode", mode);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
    }
    public void clickTransferBatchDetailsSave() {
        clickDialogButton("Transfer Batch Details");
    }

    public void confirmTransferBatchAndSubmitRemarks(String confirmationMessage, String remarks) {
        confirmDialogByText(confirmationMessage);
        submitRemarks(remarks);
    }
    public void waitForSeconds(int seconds) {
    try {
        Thread.sleep(seconds * 1000L);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    }
    public void clickReTransfer() {
        scrollToElement(reTransferButton);
        click(reTransferButton);
        waitForSeconds(3);
    }

    public void clickCloseAndReopen() {
        scrollToElement(closeAndReopenButton);
        click(closeAndReopenButton);
    }

    public void clickEdit() {
        scrollToElement(editButton);
        click(editButton);
    }

    public void confirmDialogByText(String text) {
        WebElement dialog = waitForDialogContaining(text);
        clickButtonInScope(dialog, "OK");
    }

    public void confirmDialogContaining(String partialMessage) {
    WebElement dialog = waitForDialogContaining(partialMessage);
    clickButtonInScope(dialog, "OK");
    }
    
    public void confirmReTransferFromDetails() {
        WebElement dialog = waitForDialogContaining("Are you sure want to re-transfer batch");
        clickButtonInScope(dialog, "OK");
    }

    public void submitRemarks(String remarks) {
        WebElement dialog = waitForDialog("Batch Remarks");
        WebElement remarksField = dialog.findElement(By.xpath(".//textarea[@id='remarks' or @formcontrolname='remarks']"));
        type(remarksField, remarks);
        clickButtonInScope(dialog, "OK");
        this.selectedBatchStatus = null;
    }

    public void openReopenDialogForSelectedBatch() {
        clickCloseAndReopen();
        waitForDialog("ReOpen/Close Batch");
    }

    public void chooseReopenBatch() {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        WebElement radio = dialog.findElement(By.xpath("//*[@id=\"mat-radio-3\"]/label/div[1]"));
        scrollToElement(radio);
        click(radio);
    }

    public void choosePermanentClose() {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        WebElement radio = dialog.findElement(By.xpath("//*[@id=\"permanentClose\"]"));
        scrollToElement(radio);
        click(radio);
    }

    public void confirmReopenBatch(String remarks) {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        //clickButtonInScope(dialog, "OK");
        WebElement radio = dialog.findElement(By.xpath("//*[@id=\"submit\"]"));
        click(radio);
        confirmDialogByText("Do you want to reopen the selected batch :");
        submitRemarks(remarks);
    }
    public void confirmPermanentCloseBatch(String remarks) {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        //clickButtonInScope(dialog, "OK");
        WebElement radio = dialog.findElement(By.xpath("//*[@id=\"submit\"]"));
        click(radio);
        confirmDialogByText("Do you want to permanently close the selected batch :");
        submitRemarks(remarks);
    }

    public void selectReopenModeAndLine(String mode, String line) {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        selectFieldValueIfPresent(dialog, "Select Mode", mode);
        if (com.reetrak.automation.utilities.BatchRequestDatabaseUtils.isLineSelectionWithReopenBatchEnabled()) {
            selectFieldValueIfPresent(dialog, "Select Line", line);
        }
    }

    public void clearReopenLineSelectionAndSave(String selectedLine) {
        WebElement dialog = waitForDialog("ReOpen/Close Batch");
        WebElement lineSelect = findFieldByLabel(dialog, "Select Line");
        WebElement trigger = lineSelect.findElement(By.xpath(".//*[contains(@class,'mat-select-arrow') or @role='combobox'][1]"));
        scrollToElement(trigger);
        click(trigger);
        WebElement selectedOption = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[.//span[normalize-space()='" + selectedLine + "']]")));
        click(selectedOption);
        clickButtonInScope(dialog, "OK");
    }

    public String getRequiredLineValidationMessage() {
    WebElement dialog = waitForDialog("ReOpen/Close Batch");
    WebElement errorMsg = dialog.findElement(
        By.xpath(".//*[contains(normalize-space(text()),'This field is required')]")
    );
    return errorMsg.getText().trim();
    }   

    public void updateExtraQuantityAndSave(String extraQuantity) {
        waitForDialogTitle("Update Batch information");
        try {
            WebElement dialog = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//div[contains(@class,'cdk-overlay-pane') or contains(@class,'mat-dialog-container')]"
                                    + "[.//*[normalize-space()='Update Batch information']]")));
            ((JavascriptExecutor) driver).executeScript(
                "var scrollable = arguments[0].querySelector('mat-dialog-content, .mat-dialog-content, [style*=\"overflow\"]') || arguments[0];" +
                "scrollable.scrollTop = scrollable.scrollHeight;", 
                dialog);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Failed to scroll: " + e.getMessage());
        }
        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[normalize-space()='Extra Quantity']/ancestor::mat-form-field[1]//input")));
        scrollToElement(input);
        type(input, extraQuantity);
        WebElement update = driver.findElement(By.xpath("//*[@id=\"submit\"]"));
        scrollToElement(update);
        click(update);
    }

    public boolean isDialogVisible(String dialogTitle) {
        try {
            return waitForDialogTitle(dialogTitle).isDisplayed();
        } catch (Exception exception) {
            return false;
        }
    }

    private WebElement waitForDialog(String dialogTitle) {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//div[contains(@class,'cdk-overlay-pane') or contains(@class,'mat-dialog-container')]"
                                + "[.//*[normalize-space()='" + dialogTitle + "']]")));
    }

    private WebElement waitForDialogContaining(String text) {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//div[contains(@class,'cdk-overlay-pane')][.//*[contains(normalize-space(),'" + text + "')]]")));
    }
    //Used by P2
    private WebElement waitForDialogTitle(String dialogTitle) {
        return new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[normalize-space()='" + dialogTitle + "']")));
    }

    private void clickDialogButton(String dialogTitle) {
        WebElement dialog = waitForDialog(dialogTitle);
        WebElement button = dialog.findElement(By.xpath(".//button[@id='save' or normalize-space()='SAVE' or normalize-space()='Save']"));
        scrollToElement(button);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.elementToBeClickable(button));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }
    private WebElement waitForPORow(String poNumber) {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//div[contains(@class,'cdk-overlay-pane') or contains(@class,'mat-dialog-container')]"
                                + "[.//*[normalize-space()='PO Details']]"
                                + "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and .//*[normalize-space()='" + poNumber + "']]")));
    }
    private void waitForSelectedPORow(String poNumber) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> {
            WebElement row = driver.findElement(By.xpath(
                    "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                            + " and .//*[normalize-space()='" + poNumber + "']]"));
            String classAttr = row.getAttribute("class");
            String ariaSelected = row.getAttribute("aria-selected");
            
            boolean isClassSelected = classAttr != null && (
                    classAttr.contains("selected") ||
                    classAttr.contains("active") ||
                    classAttr.contains("highlight") ||
                    classAttr.contains("mat-selected") ||
                    classAttr.contains("ui-state-highlight") ||
                    classAttr.contains("ag-row-selected")
            );
            boolean isAriaSelected = "true".equalsIgnoreCase(ariaSelected);
            return isClassSelected || isAriaSelected;
        });
    }


    public void selectBatchByPONumberForReTrasfer(String poNumber) {
        WebElement filterInput = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[@id='quickFilterInput' or @placeholder='Type Text To Filter' or @aria-label='Type Text To Filter']")));
        scrollToElement(filterInput);
        type(filterInput, poNumber);

        WebElement row = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and .//*[normalize-space()=" + xpathLiteral(poNumber) + "]]")));
        selectBatchRow(row);
        selectedPONumber = poNumber;
        waitForSelectedBatchRow(poNumber);
    }

    private void clickButtonInScope(WebElement scope, String buttonText) {
        WebElement button = scope.findElement(By.xpath(
                ".//*[normalize-space()='" + buttonText + "' or @id='submitRemark' or @id='OK' or @id='ok']"));
        scrollToElement(button);
        click(button);
    }

    private WebElement findBatchRowByStatus(String status) {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr) and .//*[contains(normalize-space(),'" + status + "')]]")));
    }

    private WebElement findBatchRowByPONumber(String poNumber) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and .//*[normalize-space()=" + xpathLiteral(poNumber) + "]]")));
    }

    private WebElement findBatchRowByPONumberAndStatus(String poNumber, String status) {
        return new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                                + " and .//*[normalize-space()=" + xpathLiteral(poNumber) + "]"
                                + " and .//*[contains(normalize-space()," + xpathLiteral(status) + ")]]")));
    }

    private void selectFieldValueIfPresent(WebElement scope, String label, String value) {
        if (value == null || value.isBlank()) {
            return;
        }

        WebElement field = findFieldByLabel(scope, label);
        WebElement trigger = field.findElement(By.xpath(
                ".//*[contains(@class,'mat-select-arrow') or @role='combobox' or contains(@class,'mat-select-trigger')][1]"));
        scrollToElement(trigger);
        click(trigger);
        
        String cleanVal = value.trim().toLowerCase().replace(" ", "");
        
        WebElement filter = findOpenSelectFilter();
        if (filter != null) {
            type(filter, value.trim());
        }
        
        try {
            By exactLocator = By.xpath("//mat-option[.//span[normalize-space()='" + value.trim() + "'] or normalize-space()='" + value.trim() + "']");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(exactLocator));
            click(option);
            return;
        } catch (Exception e) {
            if (filter != null) {
                filter.clear();
                type(filter, value.trim().replace(" ", ""));
            }
        }
        
        try {
            By fallbackLocator = By.xpath("//mat-option[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ ', 'abcdefghijklmnopqrstuvwxyz'), '" + cleanVal + "')]");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(fallbackLocator));
            click(option);
        } catch (Exception e) {
            if (filter != null) {
                filter.clear();
            }
            By fallbackLocator = By.xpath("//mat-option[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ ', 'abcdefghijklmnopqrstuvwxyz'), '" + cleanVal + "')]");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(fallbackLocator));
            click(option);
        }
    }

    private WebElement findOpenSelectFilter() {
        List<WebElement> filters = driver.findElements(By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//input[contains(@placeholder,'Find') or contains(@aria-label,'Find')]"));
        return filters.isEmpty() ? null : filters.get(0);
    }

    public void selectHierarchySelection(String hierarchyName) throws Exception {
        WebElement dialog = waitForDialog("Hierarchy Selection");
        
        WebElement trigger = dialog.findElement(By.xpath(".//*[@id='selectHierarachy']"));
        scrollToElement(trigger);
        click(trigger);
        
        String cleanVal = hierarchyName.trim().toLowerCase().replace(" ", "");
        
        WebElement filter = findOpenSelectFilter();
        if (filter != null) {
            type(filter, hierarchyName.trim());
        }
        
        try {
            By exactLocator = By.xpath("//mat-option[.//span[normalize-space()='" + hierarchyName.trim() + "'] or normalize-space()='" + hierarchyName.trim() + "']");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(exactLocator));
            click(option);
        } catch (Exception e) {
            if (filter != null) {
                filter.clear();
                type(filter, hierarchyName.trim().replace(" ", ""));
            }
            By fallbackLocator = By.xpath("//mat-option[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ ', 'abcdefghijklmnopqrstuvwxyz'), '" + cleanVal + "')]");
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(fallbackLocator));
            click(option);
        }
        
        Thread.sleep(500);
        
        WebElement okButton = dialog.findElement(By.xpath(".//*[normalize-space()='OK'] | .//*[@id='ok'] | .//*[@id='OK']"));
        scrollToElement(okButton);
        click(okButton);

        try {
            WebElement confirmDialog = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//div[contains(@class,'cdk-overlay-pane')][.//*[contains(normalize-space(),'OK') or contains(normalize-space(),'Cancel')]]")));
            WebElement confirmOk = confirmDialog.findElement(By.xpath(".//button[normalize-space()='OK' or @id='ok' or @id='OK'] | .//*[@id='OK'] | .//*[@id='ok']"));
            click(confirmOk);
        } catch (Exception e) {
            System.out.println("No confirmation dialog after Hierarchy Selection OK click: " + e.getMessage());
        }
    }

    private boolean containsBatchStatus(String text) {
        return BATCH_STATUSES.stream().anyMatch(status -> text.toLowerCase().contains(status.toLowerCase()));
    }
    private WebElement getSelectedBatchRow() {
        if (selectedPONumber != null && !selectedPONumber.isBlank()) {
            List<WebElement> rowsByPO = driver.findElements(By.xpath(
                    "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                            + " and .//*[normalize-space()=" + xpathLiteral(selectedPONumber) + "]]"));
            if (!rowsByPO.isEmpty()) {
                return rowsByPO.get(0);
            }
        }
        List<WebElement> selectedRows = driver.findElements(By.xpath(
                "//*[(@role='row' or self::tr or contains(@class,'ag-row'))"
                        + " and (contains(@class,'selected')"
                        + " or contains(@class,'ag-row-focus')"
                        + " or contains(@class,'ag-row-selected')"
                        + " or @aria-selected='true')]"));
        for (WebElement selectedRow : selectedRows) {
            if (containsBatchStatus(selectedRow.getText())) {
                return selectedRow;
            }
        }

        WebElement active = driver.switchTo().activeElement();
        WebElement activeRow = (WebElement) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].closest('[role=row], tr');", active);
        if (activeRow != null) {
            return activeRow;
        }

        throw new NoSuchElementException("Unable to find selected batch row.");
    }

    public String getSelectedBatchPONumber() {
        return getSelectedBatchCellText(0);
    }
    private String getSelectedBatchCellText(int cellIndex) {
        WebElement row = getSelectedBatchRow();
        List<WebElement> cells = row.findElements(By.xpath(".//*[@role='gridcell' or self::td]"));
        if (cells.size() > cellIndex) {
            String text = cells.get(cellIndex).getText().trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        throw new NoSuchElementException("Unable to read selected batch grid cell index: " + cellIndex);
    }
    public void ReTransferBatchDetailsModeAndLine(String line,String mode) {
        WebElement dialog = waitForDialog("Batch Confirmation");
        selectFieldValueIfPresent(dialog, "Line", line);
        selectFieldValueIfPresent(dialog, "Mode", mode);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        clickOnOKbutton();
    }

    public void clickOnOKbutton() {
        click(OKbuttonRetransfer);
    }

    private String getFieldDisplayValue(WebElement scope, String label) {
        WebElement field = findFieldByLabel(scope, label);
        for (By locator : new By[] {
                By.xpath(".//input[1]"),
                By.xpath(".//*[@role='textbox'][1]"),
                By.xpath(".//span[contains(@class,'mat-select-value-text')][1]"),
                By.xpath(".//*[normalize-space() and not(self::label)][last()]") }) {
            List<WebElement> candidates = field.findElements(locator);
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
    private String normalizeBatchStatus(String status) {
        if ("Ready for transfer".equalsIgnoreCase(status)) {
            return "Ready For Transfer";
        }
        return status;
    }
        private boolean isBatchStatus(String text) {
        return BATCH_STATUSES.stream().anyMatch(status -> status.equalsIgnoreCase(text));
    }
    private String getRowStatus(WebElement row) {
        String rowText = row.getText();
        for (String batchStatus : BATCH_STATUSES) {
            if (rowText.toLowerCase().contains(batchStatus.toLowerCase())) {
                return normalizeBatchStatus(batchStatus);
            }
        }
        List<WebElement> statusCells = row.findElements(By.xpath(
                ".//*[@col-id='status' or contains(@class,'cdk-column-status')]"));
        for (WebElement statusCell : statusCells) {
            String status = statusCell.getText().trim();
            if (isBatchStatus(status)) {
                return status;
            }
        }

        List<WebElement> cells = row.findElements(By.xpath(".//*[@role='gridcell' or self::td]"));
        for (WebElement cell : cells) {
            String status = cell.getText().trim();
            if (isBatchStatus(status)) {
                return status;
            }
        }

        if (!cells.isEmpty()) {
            String status = cells.get(cells.size() - 1).getText().trim();
            if (!status.isEmpty()) {
                return status;
            }
        }

        throw new NoSuchElementException("Unable to read status from selected batch row.");
    }

    public void clearReopenLineSelectionAndSave() {
    click(OKbutReopen);
    }

    private WebElement findFieldByLabel(WebElement scope, String label) {
        List<By> fieldLocators = Arrays.asList(
                By.xpath(".//mat-form-field[.//*[normalize-space()='" + label + "']][1]"),
                By.xpath(".//*[normalize-space()='" + label + "']/ancestor::mat-form-field[1]"),
                By.xpath(".//*[normalize-space()='" + label + "']/ancestor::div[contains(@class,'mat-form-field')][1]"));

        for (By locator : fieldLocators) {
            List<WebElement> fields = scope.findElements(locator);
            if (!fields.isEmpty()) {
                return fields.get(0);
            }
        }
        throw new NoSuchElementException("Unable to locate field with label: " + label);
    }
}
