package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LineMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Line Master']")
    private WebElement lineMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Line Master']")
    private WebElement lineMasterPageName;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addLineButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editLineButton;

    @FindBy(xpath = "//button[@id='refresh']")
    private WebElement refreshButton;

    // ----- Old Reetrak Client-----
    // @FindBy(xpath = "//h4[@class='card-title' and normalize-space()='Add Line
    // Information']")
    // private WebElement addLineInformationTitle;

    // ----- New Reetrak Client-----
    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addLineInformationTitle;

    // ----- Old Reetrak Client -----
    // @FindBy(xpath = "//h4[@class='card-title' and normalize-space()='Edit Line
    // Information']")
    // private WebElement updateLineInformationTitle;

    // ----- New Reetrak Client -----
    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement updateLineInformationTitle;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement deactiveLineButton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement activeLineButton;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement OK_ConfirmationMessage;

    // -------

    @FindBy(xpath = "//input[@id='lineName']")
    private WebElement lineName;

    @FindBy(xpath = "//label[@for='checkLevel1Enable-input']/div[1]")
    private WebElement level1CheckBox;

    @FindBy(xpath = "//input[@id='level1']")
    private WebElement level1IP;

    @FindBy(xpath = "//label[@for='checkLevel2Enable-input']/div[1]")
    private WebElement level2CheckBox;

    @FindBy(xpath = "//input[@id='level2']")
    private WebElement level2IP;

    @FindBy(xpath = "//label[@for='checkLevel3Enable-input']/div[1]")
    private WebElement level3CheckBox;

    @FindBy(xpath = "//input[@id='level3']")
    private WebElement level3IP;

    @FindBy(xpath = "//label[@for='checkLevel4Enable-input']/div[1]")
    private WebElement level4CheckBox;

    @FindBy(xpath = "//input[@id='level4']")
    private WebElement level4IP;

    @FindBy(xpath = "//label[@for='checkLevel5Enable-input']/div[1]")
    private WebElement level5CheckBox;

    @FindBy(xpath = "//input[@id='level5']")
    private WebElement level5IP;

    @FindBy(xpath = "//label[@for='checkLevel6Enable-input']/div[1]")
    private WebElement level6CheckBox;

    @FindBy(xpath = "//input[@id='level6']")
    private WebElement level6IP;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    public LineMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openLineMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(lineMasterMenu);
        click(lineMasterMenu);
        waitUtils.waitForVisibility(lineMasterPageName);
    }

    public String getLineMasterPageName() {
        return getText(lineMasterPageName);
    }

    public void clickAddLine() throws Exception {
        waitUtils.waitForVisibility(addLineButton);
        Thread.sleep(2000);
        click(addLineButton);
    }

    public String getAddLineInformationTitle() {
        waitUtils.waitForVisibility(addLineInformationTitle);
        return getText(addLineInformationTitle);
    }

    public String getUpdateLineInformationTitle() {
        waitUtils.waitForVisibility(updateLineInformationTitle);
        return getText(updateLineInformationTitle);
    }

    public void openLatestLineForEdit(String line) throws Exception {
        selectLatestLine(line);
        waitUtils.waitForVisibility(editLineButton);
        click(editLineButton);
        waitUtils.waitForVisibility(updateLineInformationTitle);
    }

    public void addLineDetails(String lineNameValue, String level1IpValue, String level2IpValue, String level3IpValue,
            String level4IpValue,
            String level5IpValue, String level6IpValue) throws Exception {
        waitUtils.waitForVisibility(addLineButton);
        clickAddLine();
        waitUtils.waitForVisibility(addLineInformationTitle);
        updateIfProvided(lineName, lineNameValue);
        enableLevelAndSetIp(level1CheckBox, level1IP, level1IpValue);
        enableLevelAndSetIp(level2CheckBox, level2IP, level2IpValue);
        enableLevelAndSetIp(level3CheckBox, level3IP, level3IpValue);
        enableLevelAndSetIp(level4CheckBox, level4IP, level4IpValue);
        enableLevelAndSetIp(level5CheckBox, level5IP, level5IpValue);
        enableLevelAndSetIp(level6CheckBox, level6IP, level6IpValue);
    }

    public void clickSaveForNewLine(String lineNameValue) throws Exception {
        scrollToElement(saveButton);
        click(saveButton);
        RemarkandOK(driver, "Test : " + lineNameValue + " Added Successfully");
    }

    public void updateLineDetails(String lineNameValue, String level2IpValue, String level3IpValue,
            String level4IpValue,
            String level6IpValue) {
        updateIfProvided(lineName, lineNameValue);
        updateIfProvided(level2IP, level2IpValue);
        updateIfProvided(level3IP, level3IpValue);
        updateIfProvided(level4IP, level4IpValue);
        updateIfProvided(level6IP, level6IpValue);
    }

    public void clickSaveForUpdatedLine() throws Exception {
        scrollToElement(saveButton);
        click(saveButton);
        RemarkandOK(driver, "Test : Existing Line Updated");
    }

    public void selectLatestLine(String linename) throws Exception {
        Thread.sleep(2000);
        By Line = (By.xpath("//div[@col-id='name' and normalize-space()='" + linename + "']"));
        clickWithRetry(Line);
        click(lineMasterPageName);
    }

    // public void getLineStatus(String linename) throws Exception {

    // Thread.sleep(2000);
    // WebElement Line = driver
    // .findElement(By.xpath("//div[@col-id='name' and normalize-space()='" +
    // linename + "']/ancestor::div[@role='row']//div[@col-id='0']"));
    // click(Line);

    // }

    public String lineActiveDeactiveFunctionality(String Action, String linename) throws Exception {
        String Tempconfirmation = "";
        selectLatestLine(linename);
        if ("Deactivate".equalsIgnoreCase(Action)) {
            click(deactiveLineButton);
            Tempconfirmation = "Are you sure you want to Deactivate Line : " + linename + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Deactivating Line");
        }
        if ("Activate".equalsIgnoreCase(Action)) {
            click(activeLineButton);
            Tempconfirmation = "Are you sure you want to Activate Line : " + linename + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Activating Line");
        }
        return Tempconfirmation;
    }

    private void enableLevelAndSetIp(WebElement checkBox, WebElement input, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        scrollToElement(checkBox);
        click(checkBox);
        updateIfProvided(input, value);
    }

    private void updateIfProvided(WebElement element, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        scrollToElement(element);
        type(element, value);
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editLineButton);
        if (isInvisible) {
            return "Page Refresh Successfully";
        } else {
            return "Issue";
        }
    }

}
