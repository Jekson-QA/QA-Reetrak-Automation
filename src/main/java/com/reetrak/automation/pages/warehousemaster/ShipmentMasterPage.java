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

public class ShipmentMasterPage extends BasePage {

    // --- Sidebar Navigation ---
    @FindBy(xpath = "//*[@id='title-RTK_WAREHOUSE_MASTER']/p | //span[normalize-space()='Warehouse Master']")
    private WebElement warehouseMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Shipment Master'] | //a[contains(@href,'shipmentView')] | //span[normalize-space()='Shipment Master']")
    private WebElement shipmentMasterMenu;

    @FindBy(xpath = "//span[normalize-space()='Shipment Master'] | //*[@id='top']//span[normalize-space()='Shipment Master']")
    private WebElement shipmentMasterPageName;

    // --- Main Page Tabs ---
    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='Shipment'] | //div[normalize-space()='Shipment'] | //span[normalize-space()='Shipment']")
    private WebElement shipmentTab;

    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='Source/Destination Address'] | //div[normalize-space()='Source/Destination Address'] | //span[normalize-space()='Source/Destination Address']")
    private WebElement sourceDestinationAddressTab;

    // --- Common Page Controls ---
    @FindBy(xpath = "//button[normalize-space()='ACTIVE' or contains(., 'ACTIVE')]")
    private WebElement activeButton;

    @FindBy(xpath = "//button[normalize-space()='DEACTIVE' or contains(., 'DEACTIVE')]")
    private WebElement deactiveButton;

    @FindBy(xpath = "//button[contains(.,'ADD') or contains(.,'+ ADD')] | //*[@id='add']")
    private WebElement addButton;

    @FindBy(xpath = "//button[contains(.,'REFRESH')] | //*[@id='refresh']")
    private WebElement refreshButton;

    @FindBy(xpath = "//input[@placeholder='Search' or contains(@placeholder,'Search')]")
    private WebElement searchInput;

    // --- Add Source/Destination Address Modal (Source/Destination Info) ---
    @FindBy(xpath = "//*[contains(@class,'modal-title') or contains(@class,'card-title') or self::h4 or self::h5 or self::span][contains(normalize-space(),'Source/Destination Info')]")
    private WebElement sourceDestinationModalTitle;

    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='EPCIS'] | //span[normalize-space()='EPCIS'] | //div[normalize-space()='EPCIS']")
    private WebElement epcisTab;

    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='TRACE LINK'] | //span[normalize-space()='TRACE LINK'] | //div[normalize-space()='TRACE LINK']")
    private WebElement traceLinkTab;

    // EPCIS Tab - Source Information Fields
    @FindBy(xpath = "//input[@id='name'] | (//mat-form-field[.//mat-label[contains(text(),'Business Name')]]//input)[1]")
    private WebElement sourceBusinessNameInput;

    @FindBy(xpath = "//input[@id='address1']")
    private WebElement sourceAddress1Input;

    @FindBy(xpath = "//input[@id='address2']")
    private WebElement sourceAddress2Input;

    @FindBy(xpath = "//input[@id='city']")
    private WebElement sourceCityInput;

    @FindBy(xpath = "//input[@id='state']")
    private WebElement sourceStateInput;

    @FindBy(xpath = "//input[@id='postalCode']")
    private WebElement sourcePostalCodeInput;

    @FindBy(xpath = "//input[@id='country']")
    private WebElement sourceCountryInput;

    @FindBy(xpath = "//input[@id='gln']")
    private WebElement sourceGlnOwningInput;

    @FindBy(xpath = "//input[@id='sgln']")
    private WebElement sourceSglnOwningInput;

    @FindBy(xpath = "//input[@id='locationgln']")
    private WebElement sourceGlnLocationInput;

    @FindBy(xpath = "//input[@id='locationsgln']")
    private WebElement sourceSglnLocationInput;

    // EPCIS Tab - Destination Information Fields
    @FindBy(xpath = "//input[@id='dname'] | (//mat-form-field[.//mat-label[contains(text(),'Business Name')]]//input)[2]")
    private WebElement destBusinessNameInput;

    @FindBy(xpath = "//input[@id='daddress1']")
    private WebElement destAddress1Input;

    @FindBy(xpath = "//input[@id='daddress2']")
    private WebElement destAddress2Input;

    @FindBy(xpath = "//input[@id='dcity']")
    private WebElement destCityInput;

    @FindBy(xpath = "//input[@id='dstate']")
    private WebElement destStateInput;

    @FindBy(xpath = "//input[@id='dpostalCode']")
    private WebElement destPostalCodeInput;

    @FindBy(xpath = "//input[@id='dcountry']")
    private WebElement destCountryInput;

    @FindBy(xpath = "//input[@id='destinationgln']")
    private WebElement destGlnOwningInput;

    @FindBy(xpath = "//input[@id='destinationsgln']")
    private WebElement destSglnOwningInput;

    @FindBy(xpath = "//input[@id='destinationlocationgln']")
    private WebElement destGlnLocationInput;

    @FindBy(xpath = "//input[@id='destinationlocationsgln']")
    private WebElement destSglnLocationInput;

    // Modal Action Buttons
    @FindBy(xpath = "//button[@id='submit'] | //button[normalize-space()='SAVE' or contains(., 'SAVE')]")
    private WebElement modalSaveButton;

    @FindBy(xpath = "//button[@id='close'] | //button[normalize-space()='CANCEL' or contains(., 'CANCEL')]")
    private WebElement modalCancelButton;

    @FindBy(xpath = "//button[contains(@class,'close') or @aria-label='Close'] | //span[text()='×']")
    private WebElement modalCloseXButton;

    // --- Add Shipment Modal (TraceLink/EPCIS Information & EPCIS Information) ---
    @FindBy(xpath = "//*[contains(@class,'modal-title') or contains(@class,'card-title') or self::h4 or self::h5 or self::span][contains(normalize-space(),'TraceLink/EPCIS Information') or contains(normalize-space(),'EPCIS Information')]")
    private WebElement shipmentModalTitle;

    @FindBy(xpath = "//mat-select[contains(@placeholder,'Line') or @formcontrolname='line' or @formcontrolname='lineId' or .//span[normalize-space()='Line']]")
    private WebElement selectLineDropdown;

    @FindBy(xpath = "//button[normalize-space()='BATCH' or contains(., 'BATCH')]")
    private WebElement batchFilterButton;

    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(normalize-space(),'Batch')] or .//*[normalize-space()='Batch']]//*[contains(@class,'mat-select-trigger') or self::mat-select] | //mat-select[contains(@placeholder,'Batch') or @formcontrolname='batch' or @formcontrolname='batchId' or @id='batch'] | //mat-select[.//span[normalize-space()='Batch']]")
    private WebElement selectBatchDropdown;

    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(normalize-space(),'Source/Destination')] or .//*[contains(normalize-space(),'Source/Destination')]]//*[contains(@class,'mat-select-trigger') or self::mat-select] | //mat-select[contains(@placeholder,'Source/Destination') or @formcontrolname='sourceDestination' or @formcontrolname='sourceDestinationId'] | //mat-select[.//span[contains(text(),'Source/Destination')]]")
    private WebElement selectSourceDestinationDropdown;

    @FindBy(xpath = "//button[normalize-space()='CODE LIST' or contains(., 'CODE LIST')]")
    private WebElement codeListButton;

    @FindBy(xpath = "//*[@id='codeListView']/span | //*[@id='codeListView'] | //*[contains(normalize-space(),'Click to view code(s)') or contains(text(),'Click to view code')] | //mat-expansion-panel-header[contains(.,'Click to view code')]")
    private WebElement clickToViewCodesField;

    @FindBy(xpath = "//mat-checkbox[.//span[contains(text(),'Select/Select All')]] | //mat-checkbox[contains(.,'Select/Select All')] | //mat-checkbox[contains(.,'Select All')] | (//mat-checkbox)[1]")
    private WebElement selectAllCodesCheckbox;

    @FindBy(xpath = "//div[contains(@class,'card-footer') or contains(@class,'modal-footer') or self::div]//button[normalize-space()='SAVE' or contains(., 'SAVE')] | //button[@id='submit']")
    private WebElement epcisSaveButton;

    @FindBy(xpath = "//button[normalize-space()='RESET' or contains(., 'RESET')]")
    private WebElement shipmentModalResetButton;

    @FindBy(xpath = "//button[normalize-space()='CLOSE' or contains(., 'CLOSE')]")
    private WebElement shipmentModalCloseButton;

    // Shipment Filter on main Shipment tab
    @FindBy(xpath = "//button[normalize-space()='SHIPMENT FILTER' or contains(., 'SHIPMENT FILTER')]")
    private WebElement shipmentFilterButton;

    public ShipmentMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openShipmentMaster() {
        waitUtils.waitForVisibility(warehouseMasterMenu);
        scrollToElement(warehouseMasterMenu);
        click(warehouseMasterMenu);
        waitUtils.waitForVisibility(shipmentMasterMenu);
        scrollToElement(shipmentMasterMenu);
        click(shipmentMasterMenu);
        waitUtils.waitForVisibility(shipmentMasterPageName);
    }

    public String getShipmentMasterPageName() {
        return getText(shipmentMasterPageName);
    }

    public void clickShipmentTab() {
        waitUtils.waitForElementToBeClickable(shipmentTab);
        click(shipmentTab);
        waitForSeconds(1);
    }

    public void clickSourceDestinationAddressTab() {
        waitUtils.waitForElementToBeClickable(sourceDestinationAddressTab);
        click(sourceDestinationAddressTab);
        waitForSeconds(1);
    }

    public void clickAdd() {
        waitUtils.waitForElementToBeClickable(addButton);
        scrollToElement(addButton);
        click(addButton);
        waitForSeconds(1);
    }

    public void clickRefresh() {
        waitUtils.waitForElementToBeClickable(refreshButton);
        click(refreshButton);
        waitForSeconds(1);
    }

    public void search(String query) {
        if (query != null && !query.isBlank()) {
            waitUtils.waitForVisibility(searchInput);
            type(searchInput, query);
            waitForSeconds(1);
        }
    }

    public boolean isSourceDestinationModalDisplayed() {
        try {
            waitUtils.waitForVisibility(sourceDestinationModalTitle);
            return sourceDestinationModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEpcisTab() {
        waitUtils.waitForElementToBeClickable(epcisTab);
        click(epcisTab);
        waitForSeconds(1);
    }

    public void clickTraceLinkTab() {
        waitUtils.waitForElementToBeClickable(traceLinkTab);
        click(traceLinkTab);
        waitForSeconds(1);
    }

    private void safeType(WebElement element, String value) {
        if (value == null || value.isBlank())
            return;
        waitForLoaderToDisappear();
        scrollToElement(element);
        try {
            waitUtils.waitForVisibility(element);
            element.clear();
            element.sendKeys(value);
            element.sendKeys(Keys.TAB);
        } catch (org.openqa.selenium.InvalidElementStateException e) {
            System.out.println("Standard clear() failed on element, using key chord / JavaScript fallback: " + e.getMessage());
            try {
                element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                element.sendKeys(value);
                element.sendKeys(Keys.TAB);
            } catch (Exception ex) {
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true })); arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                    element, value);
            }
        } catch (Exception e) {
            System.out.println("Standard type failed, attempting JS fallback: " + e.getMessage());
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true })); arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                element, value);
        }
    }

    public void fillSourceAddress(String businessName, String address1, String address2,
            String city, String state, String postalCode, String country,
            String glnOwning, String sglnOwning, String glnLocation, String sglnLocation) {
        if (address2 == null || address2.isBlank()) {
            address2 = address1;
        }
        safeType(sourceBusinessNameInput, businessName);
        safeType(sourceAddress1Input, address1);
        safeType(sourceAddress2Input, address2);
        safeType(sourceCityInput, city);
        safeType(sourceStateInput, state);
        safeType(sourcePostalCodeInput, postalCode);
        safeType(sourceCountryInput, country);
        safeType(sourceGlnOwningInput, glnOwning);
        safeType(sourceSglnOwningInput, sglnOwning);
        safeType(sourceGlnLocationInput, glnLocation);
        safeType(sourceSglnLocationInput, sglnLocation);
    }

    public void fillDestinationAddress(String businessName, String address1, String address2,
            String city, String state, String postalCode, String country,
            String glnOwning, String sglnOwning, String glnLocation, String sglnLocation) {
        if (address2 == null || address2.isBlank()) {
            address2 = address1;
        }
        safeType(destBusinessNameInput, businessName);
        safeType(destAddress1Input, address1);
        safeType(destAddress2Input, address2);
        safeType(destCityInput, city);
        safeType(destStateInput, state);
        safeType(destPostalCodeInput, postalCode);
        safeType(destCountryInput, country);
        safeType(destGlnOwningInput, glnOwning);
        safeType(destSglnOwningInput, sglnOwning);
        safeType(destGlnLocationInput, glnLocation);
        safeType(destSglnLocationInput, sglnLocation);
    }

    public void clickSave() {
        try {
            java.util.List<WebElement> invalidInputs = driver.findElements(By.xpath("//app-epcisaddress-form//input[contains(@class,'ng-invalid')]"));
            if (!invalidInputs.isEmpty()) {
                System.out.println("Warning: Invalid fields before Save: " + invalidInputs.size());
                for (WebElement f : invalidInputs) {
                    System.out.println(" - Invalid field id: " + f.getAttribute("id") + ", name: " + f.getAttribute("name"));
                }
            }
        } catch (Exception e) {
            // ignore
        }
        scrollToElement(modalSaveButton);
        click(modalSaveButton);
        try {
            RemarkandOK(driver, "Test : Save Shipment Address");
        } catch (Exception e) {
            System.out.println("Remark dialog not shown or handled: " + e.getMessage());
        }
    }

    public void clickCancel() {
        waitUtils.waitForElementToBeClickable(modalCancelButton);
        scrollToElement(modalCancelButton);
        click(modalCancelButton);
        waitForSeconds(1);
    }

    public void closeModalX() {
        try {
            waitUtils.waitForElementToBeClickable(modalCloseXButton);
            click(modalCloseXButton);
            waitForSeconds(1);
        } catch (Exception e) {
            // ignore
        }
    }

    public boolean isShipmentModalDisplayed() {
        try {
            waitUtils.waitForVisibility(shipmentModalTitle);
            return shipmentModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectLine(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(selectLineDropdown, value);
        }
    }

    public void clickBatchFilter() {
        waitUtils.waitForElementToBeClickable(batchFilterButton);
        click(batchFilterButton);
        waitForSeconds(2);
    }

    public void selectBatch(String batchNumber) {
        openDropdown(selectBatchDropdown);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            if (batchNumber != null && !batchNumber.isBlank()) {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//div[contains(@class,'cdk-overlay-pane')]//input[@placeholder='Search Batch' or @placeholder='Find Batch...' or contains(@placeholder,'Batch')] | //input[@placeholder='Search Batch'] | (//div[contains(@class,'cdk-overlay-pane')]//input)[last()]")));
                click(searchInput);
                searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                searchInput.sendKeys(batchNumber);
            }
        } catch (Exception ignored) {}
        By optionLocator;
        if (batchNumber != null && !batchNumber.isBlank()) {
            optionLocator = By.xpath("//mat-option[.//span[contains(normalize-space(),'" + batchNumber
                    + "')]] | //mat-option[contains(.,'" + batchNumber + "')] | (//mat-option)[1]");
        } else {
            optionLocator = By.xpath("(//mat-option)[1]");
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        try {
            option.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        }
        waitForSeconds(1);
    }

    public void selectSourceDestination(String addressName) {
        waitForLoaderToDisappear();
        waitUtils.waitForElementToBeClickable(selectSourceDestinationDropdown);
        openDropdown(selectSourceDestinationDropdown);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            if (addressName != null && !addressName.isBlank()) {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'cdk-overlay-pane')]//input[@placeholder='Search Address' or contains(@placeholder,'Address')] | //input[@placeholder='Search Address'] | (//div[contains(@class,'cdk-overlay-pane')]//input)[last()]")));
                click(searchInput);
                searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
                searchInput.sendKeys(addressName);
            }
        } catch (Exception ignored) {}
        By optionLocator;
        if (addressName != null && !addressName.isBlank()) {
            optionLocator = By.xpath("//mat-option[.//span[contains(normalize-space(),'" + addressName
                    + "')]] | //mat-option[contains(.,'" + addressName + "')] | (//mat-option)[1]");
        } else {
            optionLocator = By.xpath("(//mat-option)[1]");
        }
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        try {
            option.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        }
        waitForSeconds(1);
    }

    public void clickCodeList() {
        waitForLoaderToDisappear();
        waitUtils.waitForElementToBeClickable(codeListButton);
        click(codeListButton);
    }

    public boolean clickToViewCodes() {
        waitForLoaderToDisappear();
        By[] candidates = new By[] {
                By.xpath("//*[@id='codeListView']/span"),
                By.xpath("//*[@id='codeListView']"),
                By.xpath("//*[contains(normalize-space(),'Click to view code(s)') or contains(text(),'Click to view code')]"),
                By.xpath("//mat-expansion-panel-header[contains(.,'Click to view code')]"),
                By.xpath("//div[contains(@class,'expansion') or contains(@class,'panel')][contains(.,'Click to view code')]")
        };
        WebElement viewLink = null;
        for (By by : candidates) {
            try {
                java.util.List<WebElement> els = driver.findElements(by);
                for (WebElement el : els) {
                    if (el.isDisplayed()) {
                        viewLink = el;
                        break;
                    }
                }
                if (viewLink != null) break;
            } catch (Exception ignored) {}
        }
        if (viewLink != null) {
            scrollToElement(viewLink);
            try {
                viewLink.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewLink);
            }
        }
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofMillis(1500));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//mat-checkbox[contains(.,'Select/Select All') or contains(.,'Select All')] | //label[contains(.,'Select/Select All')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectAllCodes() {
        waitForLoaderToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-checkbox[contains(.,'Select/Select All') or contains(.,'Select All')] | //label[contains(.,'Select/Select All')]")));
        scrollToElement(checkbox);

        WebElement clickableTarget = null;
        try {
            clickableTarget = checkbox.findElement(By.xpath(
                    ".//label | .//div[contains(@class,'mat-checkbox-inner-container')] | .//span[contains(@class,'mat-checkbox-inner-container')] | .//span[contains(@class,'mat-checkbox-label')]"));
        } catch (Exception e) {
            clickableTarget = checkbox;
        }

        String cls = checkbox.getAttribute("class");
        if (cls == null || !cls.contains("mat-checkbox-checked")) {
            try {
                clickableTarget.click();
            } catch (Exception clickEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableTarget);
            }
        }

        cls = checkbox.getAttribute("class");
        if (cls == null || !cls.contains("mat-checkbox-checked")) {
            try {
                WebElement input = checkbox.findElement(By.xpath(".//input[@type='checkbox']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].dispatchEvent(new Event('change'));", input);
            } catch (Exception ignored) {}
        }
    }

    public void clickShipmentSave(String remark) {
        waitForLoaderToDisappear();
        scrollToElement(epcisSaveButton);
        click(epcisSaveButton);
        try {
            RemarkandOK(driver, (remark != null && !remark.isBlank()) ? remark : "shipment added");
        } catch (Exception e) {
            System.out.println("Shipment Remark dialog error or not shown: " + e.getMessage());
        }
    }

    public boolean hasAvailableCodes() {
        try {
            java.util.List<WebElement> els = driver.findElements(By.xpath("//*[contains(text(),'Total S/N')]"));
            for (WebElement el : els) {
                String text = el.getText();
                if (text != null && text.contains("Total S/N")) {
                    if (text.contains(":0/0") || text.contains(": 0/0") || text.contains(": 0 / 0") || text.contains("0/0")) {
                        return false;
                    }
                }
            }
        } catch (Exception ignored) {}
        return true;
    }

    public void clickShipmentCancel() {
        waitForLoaderToDisappear();
        try {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        } catch (Exception ignored) {}

        // Click CANCEL button (case-insensitive)
        try {
            java.util.List<WebElement> cancelBtns = driver.findElements(By.xpath(
                    "//button[contains(translate(normalize-space(),'CANCEL','cancel'),'cancel') or contains(translate(.,'CANCEL','cancel'),'cancel') or @id='cancel' or @id='close']"));
            for (WebElement btn : cancelBtns) {
                if (btn.isDisplayed()) {
                    try {
                        btn.click();
                    } catch (Exception ex) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                    }
                    break;
                }
            }
        } catch (Exception ignored) {}

        // If modal still displayed, click top-right close 'X' icon
        if (isShipmentModalDisplayed()) {
            try {
                java.util.List<WebElement> closeIcons = driver.findElements(By.xpath(
                        "//button[contains(@class,'btn-close') or contains(@class,'close') or .//i[normalize-space()='close'] or .//mat-icon[normalize-space()='close']] | //mat-dialog-container//button[contains(@class,'mat-icon-button')]"));
                for (WebElement icon : closeIcons) {
                    if (icon.isDisplayed()) {
                        try {
                            icon.click();
                        } catch (Exception ex) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", icon);
                        }
                        break;
                    }
                }
            } catch (Exception ignored) {}
        }

        // Quick wait for modal to disappear (max 1.5s)
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1500));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//*[contains(@class,'modal-title') or contains(@class,'card-title') or self::h4 or self::h5 or self::span][contains(normalize-space(),'TraceLink/EPCIS Information') or contains(normalize-space(),'EPCIS Information')]")));
        } catch (Exception ignored) {}
    }

    public boolean isShipmentAlreadyGenerated() {
        try {
            java.util.List<WebElement> notifs = driver.findElements(By.xpath("//span[@data-notify='message']"));
            for (WebElement n : notifs) {
                if (n.isDisplayed()) {
                    String msg = n.getText();
                    if (msg != null && (msg.toLowerCase().contains("already") || msg.toLowerCase().contains("exist"))) {
                        closeNotification();
                        return true;
                    }
                }
            }
        } catch (Exception ignored) {}

        try {
            java.util.List<WebElement> alerts = driver.findElements(By.xpath(
                    "//*[contains(@class,'alert') or contains(@class,'swal') or contains(@class,'modal') or self::p or self::span][contains(translate(text(),'ALREADY','already'),'already') or contains(translate(text(),'EXIST','exist'),'exist')]"));
            for (WebElement alert : alerts) {
                if (alert.isDisplayed()) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    public void closeShipmentModal() {
        try {
            waitUtils.waitForElementToBeClickable(shipmentModalCloseButton);
            click(shipmentModalCloseButton);
        } catch (Exception e) {
            closeModalX();
        }
    }

    public void resetShipmentModal() {
        try {
            waitUtils.waitForElementToBeClickable(shipmentModalResetButton);
            click(shipmentModalResetButton);
        } catch (Exception e) {
            // ignore
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
            Thread.sleep(Math.min(seconds * 150L, 300L));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
