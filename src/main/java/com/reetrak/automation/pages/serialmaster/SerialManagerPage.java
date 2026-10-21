package com.reetrak.automation.pages.serialmaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SerialManagerPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_SERIAL_MASTER']")
    private WebElement serialMasterMenu;

    @FindBy(xpath = "//*[@id='RTK_SERIAL_MANAGER']")
    private WebElement serialManagerMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Serial Manager']")
    private WebElement serialManagerPageName;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addEditSerialManagerPageTitle;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addserialmanagerbutton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement serialmanagername;

    @FindBy(xpath = "//mat-select[@id='snType']")
    private WebElement serialcodetype;

    @FindBy(xpath = "//mat-select[@id='snType']")
    private WebElement serialcodetype1;

    @FindBy(xpath = "//mat-select[@id='snlength']")
    private WebElement serialcodelength;

    @FindBy(xpath = "//mat-select[@id='pcmType']")
    private WebElement serialmaster;

    @FindBy(xpath = "//mat-checkbox[@id='isNormSSCC']")
    private WebElement ssccpoolcheckbox;

    @FindBy(xpath = "//mat-select[@id='normalSSCC1']")
    private WebElement ssccpool1dropdown;

    @FindBy(xpath = "//mat-select[@id='normalSSCC2']")
    private WebElement ssccpool2dropdown;

    @FindBy(xpath = "//mat-checkbox[@id='partialAsSSCC']")
    private WebElement parialassscccheckbox;

    @FindBy(xpath = "//mat-checkbox[@id='isPartialSSCC']")
    private WebElement partialssccpoolcheckbox;

    @FindBy(xpath = "//mat-select[@id='partialSSCC1']")
    private WebElement partialssccpool1dropdown;

    @FindBy(xpath = "//mat-select[@id='partialSSCC2']")
    private WebElement partialssccpool2dropdown;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement savebutton;

    @FindBy(xpath = "//*[@id='remarks']")
    private WebElement remark;

    @FindBy(xpath = "//*[@id='submitRemark']")
    private WebElement remarkok;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editbutton;

    @FindBy(xpath = "//button[@id='reSet1']")
    private WebElement ssccpoolreset;

    @FindBy(xpath = "//button[@id='reSet']")
    private WebElement partialssccpoolreset;

    @FindBy(xpath = "//button[@id='deactive']")
    private WebElement deactivatebutton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement activatebutton;

    @FindBy(xpath = "//button[@id='submitRemark']")
    private WebElement confirmationok;

    @FindBy(xpath = "//button[@id='refresh']")
    private WebElement refreshbutton;

    public SerialManagerPage(WebDriver driver) {
        super(driver);
    }

    private void addSoftError(String message) {
        String location = "";
        try {
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : trace) {
                if (element.getClassName().startsWith("com.reetrak.automation") &&
                        !element.getMethodName().equals("addSoftError")) {
                    location = " (at " + element.getFileName() + ":" + element.getLineNumber() + " in method "
                            + element.getMethodName() + ")";
                    break;
                }
            }
        } catch (Exception e) {
            // Ignore
        }

        String finalMessage = message + location;
        try {
            Class<?> reporterClass = Class.forName("org.testng.Reporter");
            java.lang.reflect.Method getCurrentTestResultMethod = reporterClass.getMethod("getCurrentTestResult");
            Object testResult = getCurrentTestResultMethod.invoke(null);
            if (testResult != null) {
                java.lang.reflect.Method getAttributeMethod = testResult.getClass().getMethod("getAttribute",
                        String.class);
                java.lang.reflect.Method setAttributeMethod = testResult.getClass().getMethod("setAttribute",
                        String.class, Object.class);

                @SuppressWarnings("unchecked")
                java.util.List<String> softErrors = (java.util.List<String>) getAttributeMethod.invoke(testResult,
                        "softErrors");
                if (softErrors == null) {
                    softErrors = new java.util.ArrayList<>();
                    setAttributeMethod.invoke(testResult, "softErrors", softErrors);
                }
                softErrors.add(finalMessage);
            }
        } catch (Exception e) {
            System.err.println("Failed to log soft error via reflection: " + e.getMessage());
        }
    }

    public void openSerialManager() {
        waitUtils.waitForVisibility(serialMasterMenu);
        scrollToElement(serialMasterMenu);
        click(serialMasterMenu);
        scrollToElement(serialManagerMenu);
        click(serialManagerMenu);
    }

    public void selectExisingSN(String serialmanagername) throws Exception {
        By snLocator = By
                .xpath("//div[@role='row']//div[@col-id='name' and normalize-space()='" + serialmanagername + "']");
        clickWithRetry(snLocator);
        click(serialManagerPageName);
    }

    public void editSNmanager(String serialmanagername) throws Exception {
        selectExisingSN(serialmanagername);
        waitUtils.waitForVisibility(editbutton);
        click(editbutton);
        waitUtils.waitForVisibility(addEditSerialManagerPageTitle);
    }

    public String getsntypeFieldStatus() {
        waitUtils.waitForVisibility(serialcodetype1);
        boolean status = serialcodetype1.isEnabled();
        if (status) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    public String getsnlengthFieldStatus() {
        waitUtils.waitForVisibility(serialcodelength);
        boolean status = serialcodelength.isEnabled();
        if (status) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    public String getSerialMasterPageName() throws Exception {
        return getText(serialManagerPageName);
    }

    public void addSerialManager() {
        click(addserialmanagerbutton);
    }

    public String getAddEditSerialManagerPageTitle() throws Exception {
        return getText(addEditSerialManagerPageTitle);
    }

    public String getSaveButtonStatus() {
        waitUtils.waitForVisibility(savebutton);
        boolean status = savebutton.isEnabled();
        if (!status) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    private void handleRegulationIfAvailable() {
        try {
            By regulationLocator = By.xpath(
                    "//mat-select[contains(@placeholder,'Regulation') or contains(@placeholder,'Relation') or @formcontrolname='regulation' or @formcontrolname='relation' or .//span[contains(text(), 'Regulation')] or .//span[contains(text(), 'Relation')]] | "
                            +
                            "//mat-form-field[.//*[contains(normalize-space(),'Regulation') or contains(normalize-space(),'Relation')]]//*[contains(@class,'mat-select-trigger') or contains(@class,'mat-mdc-select-trigger') or self::mat-select]");
            java.util.List<WebElement> elements = driver.findElements(regulationLocator);
            if (!elements.isEmpty()) {
                WebElement regulationDropdown = elements.get(0);
                if (regulationDropdown.isDisplayed()) {
                    scrollToElement(regulationDropdown);
                    selectFromDropDown(regulationDropdown, "No");
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            System.out.println("Regulation dropdown not available or not interactable: " + e.getMessage());
        }
    }

    public void AddSerialManager(String Action, String Name, String Codetype, String Codelength, String Master,
            String SSCCPOOL1,
            String SSCCPOOL2,
            String PARTIALSSCCPOOL1,
            String PARTIALSSCCPOOL2) throws Exception {
        if ("Add".equalsIgnoreCase(Action)) {
            addSerialManager();
            Thread.sleep(2000);
            type(serialmanagername, Name);
        }
        // if ("Update".equalsIgnoreCase(Action)) {
        // editSerialManager(Name);
        // }
        selectFromDropDown(serialcodetype, Codetype);
        Thread.sleep(500);
        selectFromDropDown(serialcodelength, Codelength);
        Thread.sleep(500);
        selectSerialMaster(Master);
        Thread.sleep(500);
        // handleRegulationIfAvailable();
        scrollToElement(ssccpoolcheckbox);
        click(ssccpoolcheckbox);
        Thread.sleep(500);
        selectFromDropDown(ssccpool1dropdown, SSCCPOOL1);
        Thread.sleep(500);
        // selectFromDropDown(ssccpool2dropdown, SSCCPOOL2);
        if (SSCCPOOL2 != null && !SSCCPOOL2.trim().isEmpty()) {
            selectFromDropDown(ssccpool2dropdown, SSCCPOOL2);
            Thread.sleep(500);
        }
        scrollToElement(parialassscccheckbox);
        click(parialassscccheckbox);
        Thread.sleep(500);
        selectFromDropDown(partialssccpool1dropdown, PARTIALSSCCPOOL1);
        Thread.sleep(500);
        // selectFromDropDown(partialssccpool2dropdown, PARTIALSSCCPOOL2);
        if (PARTIALSSCCPOOL2 != null && !PARTIALSSCCPOOL2.trim().isEmpty()) {
            selectFromDropDown(partialssccpool2dropdown, PARTIALSSCCPOOL2);
            Thread.sleep(500);
        }

        if ("Add".equalsIgnoreCase(Action)) {
            click(savebutton);
            RemarkandOK(driver, "Test : " + Name + " Serial Manager Created Successfully");
        }
        if ("Update".equalsIgnoreCase(Action)) {
            click(savebutton);
            RemarkandOK(driver, "Test :" + Name + " Updated Esiting User Successfully");
        }
    }

    public void AddSerialManagersamefullandpartial(String Action, String Name, String Codetype, String Codelength,
            String Master,
            String SSCCPOOL1,
            String SSCCPOOL2,
            String PARTIALSSCCPOOL1,
            String PARTIALSSCCPOOL2) throws Exception {
        if ("Add".equalsIgnoreCase(Action)) {
            addSerialManager();
            Thread.sleep(2000);
            type(serialmanagername, Name);
        }
        // if ("Update".equalsIgnoreCase(Action)) {
        // editSerialManager(Name);
        // }
        selectFromDropDown(serialcodetype, Codetype);
        Thread.sleep(500);
        selectFromDropDown(serialcodelength, Codelength);
        Thread.sleep(500);
        selectSerialMaster(Master);
        Thread.sleep(500);
        // handleRegulationIfAvailable();
        click(ssccpoolcheckbox);
        Thread.sleep(500);
        selectFromDropDown(ssccpool1dropdown, SSCCPOOL1);
        Thread.sleep(500);
        selectFromDropDown(ssccpool2dropdown, SSCCPOOL2);
        Thread.sleep(500);

        if ("Add".equalsIgnoreCase(Action)) {
            click(savebutton);
            RemarkandOK(driver, "Test : " + Name + " Serial Manager Created Successfully");
        }
        if ("Update".equalsIgnoreCase(Action)) {
            click(savebutton);
            RemarkandOK(driver, "Test : " + Name + " Updated Existing Serial Manager Successfully");
        }
    }

    public void UpdateSerialManager(String Action, String Name, String SSCCPOOL1,
            String PARTIALSSCCPOOL1) throws Exception {
        type(serialmanagername, Name);
        Thread.sleep(500);
        click(ssccpoolreset);
        Thread.sleep(500);
        click(partialssccpoolreset);
        Thread.sleep(500);
        selectFromDropDown(ssccpool1dropdown, SSCCPOOL1);
        Thread.sleep(500);
        selectFromDropDown(partialssccpool1dropdown, PARTIALSSCCPOOL1);
        Thread.sleep(500);

        if ("Update".equalsIgnoreCase(Action)) {
            click(savebutton);
            RemarkandOK(driver, "Test : " + Name + " Updated Existing Serial Manager Successfully");
        }
    }

    public String serialmanagerActiveDeactiveFunctionality(String Action, String Name) throws Exception {
        String Tempconfirmation = "";
        selectExisingSN(Name);
        if ("Deactivate".equalsIgnoreCase(Action)) {
            click(deactivatebutton);
            Tempconfirmation = "Do you want to deactivate : " + Name + " ?";
            click(confirmationok);
            RemarkandOK(driver, "Test : " + Name + " Serial Manager Deactivated Successfully");
        }
        if ("Activate".equalsIgnoreCase(Action)) {
            click(activatebutton);
            Tempconfirmation = "Do you want to activate : " + Name + " ?";
            click(confirmationok);
            RemarkandOK(driver, "Test : " + Name + " Serial Manager Activated Successfully");
        }
        return Tempconfirmation;
    }

    public void ssccpoolcheckbox() {
        click(ssccpoolcheckbox);
    }

    public void partialassscccheckbox() {
        click(partialssccpoolcheckbox);
    }

    public String refreshButton(String Value) throws Exception {
        selectExisingSN(Value);
        return refreshButton();
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshbutton);
        click(refreshbutton);
        boolean isInvisible = waitUtils.waitForInvisibility(editbutton);
        if (isInvisible) {
            return "Page Refresh Successfully.";
        } else {
            return "Issue";
        }
    }

    private void selectSerialMaster(String masterValue) throws Exception {
        waitForLoaderToDisappear();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Wait for serialmaster dropdown to be clickable and click it
        waitUtils.waitForElementToBeClickable(serialmaster);
        click(serialmaster);
        Thread.sleep(1000); // Wait for the dropdown panel to render

        // Find the search input filter element inside the open overlay pane
        try {
            WebElement searchInput = driver.findElement(By.xpath(
                    "//*[@id=\"mat-input-6\"]"));
            if (searchInput.isDisplayed() && searchInput.isEnabled()) {
                searchInput.clear();
                searchInput.sendKeys(masterValue);
                Thread.sleep(1000); // Wait for the list to filter
            }
        } catch (Exception e) {
            System.out.println("Search input filter not found/accessible in dropdown: " + e.getMessage());
        }

        // Locate the matching option using case-insensitive normalized space
        String lowerValue = masterValue.toLowerCase().trim();
        By optionLocator = By.xpath(
                "//mat-option//span[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                        + lowerValue + "'] | " +
                        "//mat-option[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                        + lowerValue + "'] | " +
                        "//mat-option//span[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                        + lowerValue + "')] | " +
                        "//mat-option[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                        + lowerValue + "')]");

        // Click the option
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        click(optionElement);

        try {
            driver.switchTo().activeElement().sendKeys(org.openqa.selenium.Keys.TAB);
        } catch (Exception e) {
            System.out.println("Switching active element failed: " + e.getMessage());
        }
    }

}
