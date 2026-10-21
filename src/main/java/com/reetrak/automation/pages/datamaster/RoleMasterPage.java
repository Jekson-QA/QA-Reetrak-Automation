package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RoleMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Role Master']")
    private WebElement roleMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Role Master']")
    private WebElement roleMasterPageName;

    @FindBy(xpath = "//button[@id='addRole']")
    private WebElement addRoleButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editRoleButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addEditNewRolePageTitle;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement roleName;

    @FindBy(xpath = "//mat-select[@id='hierarchy']")
    private WebElement hierarchyOrderDropDown;

    @FindBy(xpath = "//button[@id='assignAllRight']")
    private WebElement assignedAllButton;

    @FindBy(xpath = "//button[@id='unAssignAllRight']")
    private WebElement unassignedAllButton;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement addUpdateButton;

    @FindBy(xpath = "//button[@id='close']")
    private WebElement closeButton;

    @FindBy(xpath = "(//button[@type='button'])[2]")
    WebElement refreshButton;

    @FindBy(xpath = "//button[@id='deActive']")
    private WebElement deactiveRoleButton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement activeRoleButton;

    @FindBy(xpath = "//div[contains(@class,'card-body')]//p")
    WebElement active_deactiveConfirmationMessage;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement okButton_on_active_deactiveConfirmationMessage;

    // -----Locators of rights-----Starts---

    @FindBy(xpath = "//div[text()=' ReeTrak-Data Master ']")
    private WebElement dataMaster;

    @FindBy(xpath = "//div[text()=' ReeTrak-Production Master ']")
    private WebElement productionMaster;

    @FindBy(xpath = "//div[text()=' ReeTrak-Report Master ']")
    private WebElement rportMaster;

    @FindBy(xpath = "//div[text()=' ReeTrak-User Report  ']")
    private WebElement userReport;

    @FindBy(xpath = "//div[text()=' ReeTrak-Dashboard PO Details ']")
    private WebElement dashboardBatchDetails;

    @FindBy(xpath = "//div[text()=' ReeTrak-Dashboard Batch Details ']")
    private WebElement dashboardPODetails;

    @FindBy(xpath = "//div[text()=' ReeTrak-Line Master ']")
    private WebElement lineMaster;

    // -----Locators of rights-----End---

    public RoleMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openRoleMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(roleMasterMenu);
        click(roleMasterMenu);
    }

    public String getRoleMasterPageName() throws Exception {
        return getText(roleMasterPageName);
    }

    public void clickOnPageTitle() {
        click(roleMasterPageName);
    }

    public void addRole() {
        click(addRoleButton);
    }

    public void selectExistingRole(String availableRole) throws Exception {
        By roleLocator = By.xpath("//div[@tabindex='-1' and normalize-space()='" + availableRole + "']");
        clickWithRetry(roleLocator);
        click(roleMasterPageName);
    }

    public String editRole(String availableRole) throws Exception {
        selectExistingRole(availableRole);
        click(editRoleButton);
        String pageTitle = getaddEditNewRolePageTitle();
        return pageTitle;
    }

    public String getaddEditNewRolePageTitle() throws Exception {
        waitUtils.waitForVisibility(assignedAllButton);
        return getText(addEditNewRolePageTitle);
    }

    public void addRoleByAssignedAllButton(String rolename, String hierarchyname) throws Exception {
        addRole();
        waitUtils.waitForVisibility(assignedAllButton);
        type(roleName, rolename);
        selectFromDropDown(hierarchyOrderDropDown, hierarchyname);
        click(assignedAllButton);
        waitUtils.waitForElementToBeClickable(addUpdateButton);
        click(addUpdateButton);
        RemarkandOK(driver, "Test : New Role Added By Assign All button");
    }

    public void addNewRoleForCustomRights(String rolename, String hierarchyname) throws Exception {
        addRole();
        waitUtils.waitForVisibility(assignedAllButton);
        type(roleName, rolename);
        selectFromDropDown(hierarchyOrderDropDown, hierarchyname);
        scrollToElement(dataMaster);
        click(dataMaster);
        scrollToElement(productionMaster);
        click(productionMaster);
        scrollToElement(rportMaster);
        click(rportMaster);
        scrollToElement(userReport);
        click(userReport);
        scrollToElement(dashboardBatchDetails);
        click(dashboardBatchDetails);
        scrollToElement(dashboardPODetails);
        click(dashboardPODetails);
        waitUtils.waitForElementToBeClickable(addUpdateButton);
        click(addUpdateButton);
        RemarkandOK(driver, "Test : New Role Added for Custom Rights");
    }

    public void updateRoleForCustomRights(String exisongRole, String rolename, String hierarchyname) throws Exception {
        editRole(exisongRole);
        waitUtils.waitForVisibility(assignedAllButton);
        type(roleName, rolename);
        selectFromDropDown(hierarchyOrderDropDown, hierarchyname);
        scrollToElement(dashboardPODetails);
        click(dashboardPODetails);
        scrollToElement(lineMaster);
        click(lineMaster);
        scrollToElement(dashboardBatchDetails);
        click(dashboardBatchDetails);
        waitUtils.waitForElementToBeClickable(addUpdateButton);
        click(addUpdateButton);
        RemarkandOK(driver, "Test : Role Updated by Individual Rights");
    }

    public void updateRoleByUnAssignedAllButton(String exisongRole) throws Exception {
        editRole(exisongRole);
        waitUtils.waitForVisibility(assignedAllButton);
        click(unassignedAllButton);
        waitUtils.waitForElementToBeClickable(addUpdateButton);
        click(addUpdateButton);
        RemarkandOK(driver, "Test : Attempt to updating a role with no rights assigned");
    }

    public String deactiveRole(String availableRole) throws Exception {
        selectExistingRole(availableRole);
        click(deactiveRoleButton);
        // String deactiveMessage = getText(active_deactiveConfirmationMessage);
        String deactiveMessage = "Do you want to deactivate : " + availableRole + " ?";
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Deactivate Role");
        return deactiveMessage;
    }

    public String activeRole(String availableRole) throws Exception {
        selectExistingRole(availableRole);
        click(activeRoleButton);
        String deactiveMessage = getText(active_deactiveConfirmationMessage);
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Activate Role");
        return deactiveMessage;
    }

    public String getRoleStatus(String availableRole) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement roleCell = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//div[@tabindex='-1' and normalize-space()='" + availableRole + "']")));
        roleCell.click();
        WebElement statusCell = wait
                .until(ExpectedConditions.visibilityOf(roleCell.findElement(By.xpath("following-sibling::div"))));
        String status = statusCell.getText();
        return status;
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editRoleButton);
        if (isInvisible) {
            return "Page Refresh Successfully";
        } else {
            return "Issue";
        }
    }

}
