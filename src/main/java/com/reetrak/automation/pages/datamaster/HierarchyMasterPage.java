package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HierarchyMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Hierarchy Master']")
    private WebElement hierarchyMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Hierarchy Master']")
    private WebElement hierarchyMasterPageName;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addHierarchyPageTitle;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement deactiveButton;

    @FindBy(xpath = "//button[@id='deactive']")
    private WebElement activeButton;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement name;

    @FindBy(xpath = "//mat-select[@id='for']")
    private WebElement forDropDown;

    @FindBy(xpath = "//mat-select[@id='type']")
    private WebElement typeDropDown;

    @FindBy(xpath = "//mat-select[@id='creator']")
    private WebElement creatorDropDown;

    @FindBy(xpath = "//mat-select[@id='reviewer']")
    private WebElement reviewerDropDown;

    @FindBy(xpath = "//mat-select[@id='approver']")
    private WebElement approverDropDown;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement OK_ConfirmationMessage;

    @FindBy(xpath = "//button[@id='reSet']")
    private WebElement resetButton;

    @FindBy(xpath = ("//button[@id='refresh']"))
    WebElement refreshButton;

    public HierarchyMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openHierarchyMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(hierarchyMasterMenu);
        click(hierarchyMasterMenu);
        waitUtils.waitForVisibility(hierarchyMasterPageName);
    }

    public String getHierarchyMasterPageName() {
        return getText(hierarchyMasterPageName);
    }

    public String getAddEditHierarchyPageTitle() throws Exception {
        return getText(addHierarchyPageTitle);
    }

    public void addHierarchyButton() throws Exception {
        Thread.sleep(2000);
        waitUtils.waitForVisibility(addButton);
        click(addButton);
    }

    public String savebuttonstatus() {

        waitUtils.waitForVisibility(saveButton);
        boolean status = saveButton.isEnabled();
        if (!status) {
            return "Save button is disabled";
        } else {
            return "Save button should be enabled";
        }
    }

    public void addNewHierarchy(String value1, String value2, String value3, String value4, String value5,
            String value6) throws Exception {
        addHierarchyButton();
        waitUtils.waitForVisibility(name);
        type(name, value1);
        selectFromDropDown(forDropDown, value2);
        Thread.sleep(1000);
        selectFromDropDown(typeDropDown, value3);
        Thread.sleep(1000);
        selectFromDropDown(creatorDropDown, value4);
        Thread.sleep(1000);
        selectFromDropDown(reviewerDropDown, value5);
        Thread.sleep(1000);
        selectFromDropDown(approverDropDown, value6);
        Thread.sleep(1000);
        click(saveButton);
        RemarkandOK(driver, "ADD New Hierarchy");
    }

    public void selectHierarchy(String value1) throws Exception {
        Thread.sleep(2000);
        By hierarchy = (By.xpath("//div[@col-id='name' and normalize-space()='" + value1 + "']"));
        clickWithRetry(hierarchy);
        click(hierarchyMasterPageName);
    }

    public void editHierarchyButton() {
        waitUtils.waitForVisibility(editButton);
        click(editButton);
    }

    public void updateHierarchy(String hierarchy, String value1, String value2, String value3,
            String value4, String value5, String value6) throws Exception {
        selectHierarchy(hierarchy);
        editHierarchyButton();
        waitUtils.waitForVisibility(name);
        type(name, value1);
        selectFromDropDown(forDropDown, value2);
        Thread.sleep(1000);
        selectFromDropDown(typeDropDown, value3);
        Thread.sleep(1000);
        selectFromDropDown(creatorDropDown, value4);
        Thread.sleep(1000);
        selectFromDropDown(reviewerDropDown, value5);
        Thread.sleep(1000);
        selectFromDropDown(approverDropDown, value6);
        Thread.sleep(1000);
        click(saveButton);
        RemarkandOK(driver, "Update Hierarchy");
    }

    public String hierarchyActiveDeactiveFunctionality(String Action, String hierarchy) throws Exception {
        String Tempconfirmation = "";
        selectHierarchy(hierarchy);
        if ("Deactivate".equalsIgnoreCase(Action)) {
            click(deactiveButton);
            Tempconfirmation = "Do you want to deactivate : " + hierarchy + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Deactivating Hierarchy");
        }
        if ("Activate".equalsIgnoreCase(Action)) {
            click(activeButton);
            Tempconfirmation = "Do you want to activate : " + hierarchy + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Activating Hierarchy");
        }
        return Tempconfirmation;
    }

    public String resetFunctionality(String value1) throws Exception {
        String FinalValue = "";
        selectHierarchy(value1);
        editHierarchyButton();
        scrollToElement(reviewerDropDown);
        waitUtils.waitForVisibility(reviewerDropDown);
        String ValueBefroeReset = getText(reviewerDropDown);
        scrollToElement(resetButton);
        click(resetButton);
        Thread.sleep(500);
        String ValueAfterReset = getText(reviewerDropDown);

        if (ValueBefroeReset.equals(ValueAfterReset)) {
            FinalValue = "Fail";
        }
        if (!ValueBefroeReset.equals(ValueAfterReset)) {
            FinalValue = "Reset Successfully.";
        }
        click(saveButton);
        RemarkandOK(driver, "Test : Removed Reviewer");
        return FinalValue;
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
