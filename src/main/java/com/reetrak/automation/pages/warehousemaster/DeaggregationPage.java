package com.reetrak.automation.pages.warehousemaster;

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

public class DeaggregationPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_WAREHOUSE_MASTER']/p | //span[normalize-space()='Warehouse Master']")
    private WebElement warehouseMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Repacking'] | //span[normalize-space()='Repacking']")
    private WebElement repackingMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Repacking'] | //span[normalize-space()='Repacking']")
    private WebElement repackingPageName;

    // Tabs
    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='Deaggregation'] | //span[normalize-space()='Deaggregation']")
    private WebElement deaggregationTab;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Line') or @formcontrolname='line' or @formcontrolname='lineId' or @formcontrolname='lineName' or .//span[normalize-space()='Line'] or .//span[normalize-space()='Select Line']]")
    private WebElement selectLineSelect;

    @FindBy(xpath = "//button[normalize-space()='BATCH' or normalize-space()='Batch' or contains(., 'BATCH') or contains(., 'Batch')]")
    private WebElement batchButton;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Batch') or @formcontrolname='batch' or @formcontrolname='batchId' or .//span[normalize-space()='Select Batch']]")
    private WebElement selectBatchSelect;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Select Level') or @formcontrolname='level' or @formcontrolname='selectLevel' or .//span[normalize-space()='Select Level']]")
    private WebElement selectLevelSelect;

    @FindBy(xpath = "//input[@id='scanCode' or contains(@placeholder,'Scan Code') or @formcontrolname='scanCode' or @formcontrolname='code'] | //mat-form-field[.//mat-label[contains(text(), 'Scan Code')]]//input")
    private WebElement scanCodeInput;

    @FindBy(xpath = "//button[normalize-space()='CONFIRM' or normalize-space()='Confirm' or contains(., 'CONFIRM') or contains(., 'Confirm')]")
    private WebElement confirmButton;

    @FindBy(xpath = "//button[normalize-space()='SAVE' or normalize-space()='Save' or contains(., 'SAVE') or contains(., 'Save')]")
    private WebElement saveButton;



    public DeaggregationPage(WebDriver driver) {
        super(driver);
    }

    public void openRepacking() {
        waitUtils.waitForVisibility(warehouseMasterMenu);
        scrollToElement(warehouseMasterMenu);
        click(warehouseMasterMenu);
        waitUtils.waitForVisibility(repackingMenu);
        scrollToElement(repackingMenu);
        click(repackingMenu);
        waitUtils.waitForVisibility(repackingPageName);
    }

    public String getRepackingPageName() {
        return getText(repackingPageName);
    }

    public void clickDeaggregationTab() {
        waitUtils.waitForElementToBeClickable(deaggregationTab);
        click(deaggregationTab);
        waitForSeconds(1);
    }

    public void selectLine(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(selectLineSelect, value);
        }
    }

    public void clickBatch() {
        waitUtils.waitForElementToBeClickable(batchButton);
        scrollToElement(batchButton);
        click(batchButton);
        waitForSeconds(2);
    }

    public void selectBatch(String batchNumber) {
        if (batchNumber != null && !batchNumber.isBlank()) {
            openDropdown(selectBatchSelect);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'cdk-overlay-pane')]//input[@placeholder='Find Batch...' or contains(@placeholder,'Batch')] | //input[@placeholder='Find Batch...'] | (//div[contains(@class,'cdk-overlay-pane')]//input)[last()]")));
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
                        By.xpath("//mat-option//span[contains(normalize-space(), '" + batchNumber + "')] | //mat-option[contains(normalize-space(), '" + batchNumber + "')]")));
            } catch (Exception e) {
                option = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-option")));
            }
            click(option);
            waitForSeconds(1);
        }
    }

    public void selectLevel(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(selectLevelSelect, value);
        }
    }

    public void enterScanCode(String code) {
        if (code != null && !code.isBlank()) {
            scrollToElement(scanCodeInput);
            type(scanCodeInput, code);
        }
    }

    public void clickConfirm() {
        waitUtils.waitForElementToBeClickable(confirmButton);
        scrollToElement(confirmButton);
        click(confirmButton);
        waitForSeconds(2);
    }

    public void clickSave() throws InterruptedException {
        waitUtils.waitForElementToBeClickable(saveButton);
        scrollToElement(saveButton);
        click(saveButton);
        waitForSeconds(1);
    }

    public void handleSaveConfirmation(String remarks) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // 1. Click the first confirmation OK button using ID 'ok'
            try {
                WebElement confirmOkBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='ok'] | //button[@id='ok'] | //button[contains(normalize-space(),'OK') and not(@id='submitRemark')]")
                ));
                click(confirmOkBtn);
                System.out.println("Clicked on first confirmation OK button.");
                waitForSeconds(1);
            } catch (Exception e) {
                System.out.println("No first confirmation OK button found/needed, proceeding: " + e.getMessage());
            }

            // 2. Wait for Remarks field to be visible in the Repack Remark dialog
            WebElement remarksField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[@id='remarks' or @formcontrolname='remarks' or contains(@placeholder,'Remark')] | " +
                         "//input[@id='remarks' or @formcontrolname='remarks' or contains(@placeholder,'Remark')] | " +
                         "//mat-dialog-container//textarea | //mat-dialog-container//input | " +
                         "//textarea | //input[contains(@placeholder,'Remark')]")
            ));
            remarksField.clear();
            remarksField.sendKeys(remarks);
            waitForSeconds(1);
            
            // 3. Wait for the remark OK button to be clickable (usually submitRemark or OK text)
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='submitRemark'] | " +
                         "//mat-dialog-container//button[contains(normalize-space(),'OK') or contains(normalize-space(),'Ok')] | " +
                         "//button[normalize-space()='OK' or normalize-space()='Ok' or contains(.,'OK') or contains(.,'Ok')]")
            ));
            click(okBtn);
            waitForSeconds(1);
            System.out.println("Entered repack remark: '" + remarks + "' and clicked OK.");
        } catch (Exception e) {
            System.out.println("Could not handle save confirmation / remark: " + e.getMessage());
        }
    }

    public void clickClickToViewCodes() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[normalize-space()='Click to view code(s)'] | //a[contains(text(),'Click to view code')] | //*[normalize-space()='Click to view code(s)']")
        ));
        scrollToElement(link);
        click(link);
        waitForSeconds(2);
    }

    public void selectCodeInDialog(String code) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator = By.xpath(
            "//mat-checkbox[contains(.,'" + code + "')] | " +
            "//mat-checkbox[.//span[contains(text(),'" + code + "')]] | " +
            "//*[contains(text(),'" + code + "')]/ancestor::mat-checkbox"
        );
        WebElement checkboxElement = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollToElement(checkboxElement);
        
        // Check if the checkbox is already checked
        String className = checkboxElement.getAttribute("class");
        String ariaChecked = checkboxElement.getAttribute("aria-checked");
        boolean isChecked = (className != null && className.contains("mat-checkbox-checked")) || 
                            "true".equals(ariaChecked);
                            
        if (!isChecked) {
            click(checkboxElement);
            System.out.println("Checkbox for code " + code + " was not checked. Clicked to check it.");
        } else {
            System.out.println("Checkbox for code " + code + " is already checked.");
        }
        waitForSeconds(1);
    }

    public void clickDialogOk() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-dialog-container | //div[contains(@class,'dialog')] | //div[contains(@class,'modal')] | //app-verify-code-dialog")
            ));
            System.out.println("DEBUG: Found dialog: tag=" + dialog.getTagName() + ", class=" + dialog.getAttribute("class"));
            java.util.List<WebElement> elements = dialog.findElements(By.xpath(".//*"));
            for (WebElement el : elements) {
                String txt = el.getText();
                if (txt != null && !txt.trim().isEmpty() && txt.trim().length() < 50) {
                    System.out.println("DEBUG ELEMENT: tag=" + el.getTagName() + ", text='" + txt.trim() + "', class='" + el.getAttribute("class") + "', id='" + el.getAttribute("id") + "'");
                }
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Could not inspect dialog elements: " + e.getMessage());
        }

        String[] xpaths = {
            "//mat-dialog-container//*[normalize-space()='OK' or normalize-space()='Ok']",
            "//*[contains(@class,'dialog') or contains(@class,'modal')]//*[normalize-space()='OK' or normalize-space()='Ok']",
            "//button[normalize-space()='OK' or normalize-space()='Ok']",
            "//a[normalize-space()='OK' or normalize-space()='Ok']",
            "//*[normalize-space()='OK' or normalize-space()='Ok']"
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (String xpath : xpaths) {
            try {
                WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                click(okBtn);
                waitForSeconds(2);
                System.out.println("Successfully clicked dialog OK button using xpath: " + xpath);
                return;
            } catch (Exception e) {
                // Try next xpath
            }
        }
        System.out.println("No dialog OK button found to click (likely inline UI), skipping OK button click.");
    }

    public void clickLastButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement lastBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='OK' or normalize-space()='Ok'] | //mat-dialog-container//button[normalize-space()='OK' or normalize-space()='Ok'] | //div[contains(@class,'dialog')]//button[normalize-space()='OK' or normalize-space()='Ok']")
            ));
            click(lastBtn);
            waitForSeconds(1);
            System.out.println("Clicked on final success dialog OK button.");
        } catch (Exception e) {
            System.out.println("No final success dialog OK button found to click.");
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
