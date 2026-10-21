package com.reetrak.automation.pages.serialmaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SSCCPoolPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_SERIAL_MASTER']/p")
    private WebElement serialMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='SSCC Pool']")
    private WebElement ssccPoolMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='SSCC Pool']")
    private WebElement ssccPoolPageName;

    // ----- Old Reeclient
    // @FindBy(xpath = "//h4[@class='card-title' and normalize-space()='Add SSCC
    // Pool Details']")
    // WebElement addSSCCPoolPageTitle;

    // ----- New Reeclient
    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    WebElement addSSCCPoolPageTitle;

    @FindBy(xpath = ("//button[@id='add']"))
    WebElement addButton;

    @FindBy(xpath = ("//button[@id='edit']"))
    WebElement editButton;

    @FindBy(xpath = ("//mat-select[@id='pcmType']"))
    WebElement serialMasterDropDown;

    @FindBy(xpath = ("//mat-select[@id='extdigit']"))
    WebElement extDigitDropDown;

    @FindBy(xpath = ("//input[@id='prefix']"))
    WebElement ssccPrefix;

    @FindBy(xpath = ("//input[@id='required']"))
    WebElement requiredCount;

    @FindBy(xpath = ("//input[@id='threshold']"))
    WebElement thresholdCount;

    @FindBy(xpath = ("//button[@id='submit']"))
    WebElement saveButton;

    @FindBy(xpath = ("//button[@id='deactive']"))
    WebElement deactiveSSCCPoolButton;

    @FindBy(xpath = ("//button[@id='active']"))
    WebElement activeSSCCPoolButton;

    @FindBy(xpath = ("//button[@id='refresh']"))
    WebElement refreshButton;

    @FindBy(xpath = "(//p)[15]")
    WebElement active_deactiveConfirmationMessage;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement okButton_on_active_deactiveConfirmationMessage;

    public SSCCPoolPage(WebDriver driver) {
        super(driver);
    }

    public void openSSCCPool() {
        waitUtils.waitForVisibility(serialMasterMenu);
        scrollToElement(serialMasterMenu);
        click(serialMasterMenu);
        scrollToElement(ssccPoolMenu);
        click(ssccPoolMenu);
        waitUtils.waitForVisibility(ssccPoolPageName);
    }

    public String getSSCCPoolPageName() {
        return getText(ssccPoolPageName);
    }

    public void clickAddSSCCPool() throws Exception {
        waitUtils.waitForVisibility(addButton);
        Thread.sleep(1000);
        click(addButton);
    }

    public void clickEditSSCCPool() throws Exception {
        waitUtils.waitForVisibility(editButton);
        Thread.sleep(1000);
        click(editButton);
    }

    public String getAddSSCCPoolTitle() throws Exception {
        clickAddSSCCPool();
        return getText(addSSCCPoolPageTitle);
    }

    public void selectSSCCPool(String SNM, String Prefix, String Edigit) throws Exception {
        Thread.sleep(1000);
        By SSCCPool = (By.xpath("//div[@col-id='0' and normalize-space()='" + SNM
                + "']/following-sibling::div[@col-id='prefix' and normalize-space()='" + Prefix
                + "']/following-sibling::div[@col-id='extdigit' and normalize-space()='" + Edigit + "']"));
        clickWithRetry(SSCCPool);
        click(ssccPoolPageName);
    }

    public void AddSSCCPool(String SerialManager, String ExtDigit, String Prefix,
            String Required, String Threshold) throws Exception {

        clickAddSSCCPool();
        selectFromDropDown(serialMasterDropDown, SerialManager);
        selectFromDropDown(extDigitDropDown, ExtDigit);
        type(ssccPrefix, Prefix);
        type(requiredCount, Required);
        type(thresholdCount, Threshold);
        click(saveButton);
        RemarkandOK(driver, "Test : New SSCC Pool Added");
    }

    public void UpdateSSCCPool(String SerialManager, String ExtDigit, String Prefix,
            String Required, String Threshold, String ExistingSerialManager, String ExistingExtDigit,
            String ExistingPrefix) throws Exception {
        selectSSCCPool(ExistingSerialManager, ExistingExtDigit, ExistingPrefix);
        clickEditSSCCPool();
        selectFromDropDown(serialMasterDropDown, SerialManager);
        selectFromDropDown(extDigitDropDown, ExtDigit);
        type(ssccPrefix, Prefix);
        type(requiredCount, Required);
        type(thresholdCount, Threshold);
        click(saveButton);
        RemarkandOK(driver, "Test : SSCC Pool Updated");
    }

    public String deactiveSSCCPool(String ExistingSerialManager, String ExistingExtDigit, String ExistingPrefix)
            throws Exception {
        // selectSSCCPool(ExistingSerialManager, ExistingExtDigit, ExistingPrefix);
        selectSSCCPool(ExistingSerialManager, ExistingPrefix, ExistingExtDigit);
        click(deactiveSSCCPoolButton);
        String deactiveMessage = "Are you sure you want to Deactivate SSCC pool : " + ExistingExtDigit + "-"
                + ExistingPrefix + " ?";
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Deactivate SSCC Pool");
        return deactiveMessage;
    }

    public String activeSSCCPool(String ExistingSerialManager, String ExistingExtDigit, String ExistingPrefix)
            throws Exception {
        // selectSSCCPool(ExistingSerialManager, ExistingExtDigit, ExistingPrefix);
        selectSSCCPool(ExistingSerialManager, ExistingPrefix, ExistingExtDigit);
        click(activeSSCCPoolButton);
        String deactiveMessage = "Are you sure you want to Activate SSCC pool : " + ExistingExtDigit + "-"
                + ExistingPrefix + " ?";
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Activate SSCC Pool");
        return deactiveMessage;
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editButton);
        if (isInvisible) {
            return "Page Refresh Successfully.";
        } else {
            return "Issue";
        }
    }

}
