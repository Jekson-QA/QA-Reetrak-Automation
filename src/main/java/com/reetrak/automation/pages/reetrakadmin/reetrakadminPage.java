package com.reetrak.automation.pages.reetrakadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.reetrak.automation.pages.BasePage;

public class reetrakadminPage extends BasePage {

    @FindBy(xpath = "//p[text()='Setting ']")
    private WebElement settingMenuElement;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Manage Company']")
    private WebElement manageCompanyElement;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addButtonElement;

    @FindBy(xpath = "//input[@id='Name' or @name='name']")
    private WebElement nameElement;

    @FindBy(xpath = "//input[@id='Prefix' or @name='companyprefix']")
    private WebElement companyPrefixInput;

    @FindBy(xpath = "//mat-select[@id='SN Length']")
    private WebElement snLengthDropdown;

    @FindBy(xpath = "//input[@id='URL' or @id='url']")
    private WebElement urlElement;

    // ----- Old Reeclient UI-------
    // @FindBy(xpath = "//input[@id='FileInput']")
    // private WebElement logoInput;

    // ----- New Reeclient UI-------
    @FindBy(xpath = "//input[@type='file' or @id='FileInput']")
    private WebElement logoInput;

    @FindBy(xpath = "//input[@id='Address1' or @id='address1']")
    private WebElement address1Input;

    @FindBy(xpath = "//input[@id='Address2' or @id='address2']")
    private WebElement address2Input;

    @FindBy(xpath = "//input[@id='Pin Code' or @id='pincode']")
    private WebElement pinCodeInput;

    @FindBy(xpath = "//input[@id='City' or @id='city']")
    private WebElement cityInput;

    @FindBy(xpath = "//input[@id='State' or @id='state']")
    private WebElement stateInput;

    @FindBy(xpath = "//input[@id='Country' or @id='country']")
    private WebElement countryInput;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Manage License']")
    private WebElement manageLicenseElement;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement licenseSaveButton;

    @FindBy(xpath = "//input[@id='key']")
    private WebElement licenseKeyInput;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Manage Role']")
    private WebElement manageRoleElement;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Manage Role']")
    private WebElement manageRolePageName;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement roleEditButton;

    @FindBy(xpath = "//button[@id='assignAllRight']")
    private WebElement assignedAllButton;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement updateRoleButton;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Manage User']")
    private WebElement manageUserElement;

    @FindBy(xpath = "//button[@id='addRole']")
    private WebElement addUserButton;

    @FindBy(xpath = "//input[@id='User Name' or @id='userName']")
    private WebElement userName;

    @FindBy(xpath = "//input[@id='First Name' or @id='firstName']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@id='Last Name' or @id='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@id='Designation/Department' or @id='designation']")
    private WebElement department;

    @FindBy(xpath = "//input[@id='Email' or @id='email']")
    private WebElement email;

    @FindBy(xpath = "//input[@id='Phone' or @id='phone']")
    private WebElement phone;

    @FindBy(xpath = "//button[@id='newSubmit1' or @id='newSubmit']")
    private WebElement addButton;

    @FindBy(xpath = "(//span[@class='highlighted-text'])[1]")
    private WebElement NewUserName;

    @FindBy(xpath = "(//span[@class='highlighted-text'])[2]")
    private WebElement Token;

    @FindBy(xpath = "//button[@id='submitRemark']")
    private WebElement DownloadButton;

    @FindBy(xpath = "//button[@id='refresh']")
    private WebElement refreshButton;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Manage User']")
    private WebElement manageUserPageName;

    @FindBy(xpath = "//button[@id='isActive']")
    private WebElement deactiveUsereButton;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//button[@id='submitRemark' and text()=' Ok']")
    // WebElement OK_ConfirmationMessage;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//button[@id='submitRemark' and @type='button']")
    private WebElement OK_ConfirmationMessage;

    public reetrakadminPage(WebDriver driver) {
        super(driver);
    }

    public void addCompany(String CompanyName, String CompanyPrefix, String SNLength, String URL, String logopath,
            String address1, String address2, String Pincode, String City, String State, String Country)
            throws Exception {
        waitUtils.waitForVisibility(settingMenuElement);
        scrollToElement(settingMenuElement);
        click(settingMenuElement);
        scrollToElement(manageCompanyElement);
        click(manageCompanyElement);
        waitUtils.waitForVisibility(addButtonElement);
        click(addButtonElement);
        scrollToElement(nameElement);
        type(nameElement, CompanyName);
        type(companyPrefixInput, CompanyPrefix);
        selectFromDropDown(snLengthDropdown, SNLength);
        type(urlElement, URL);
        logoInput.sendKeys(logopath);
        scrollToElement(countryInput);
        type(address1Input, address1);
        type(address2Input, address2);
        type(pinCodeInput, Pincode);
        type(cityInput, City);
        type(stateInput, State);
        type(countryInput, Country);
        click(saveButton);
        RemarkandOK(driver, "Test : Company Added Successfully");
    }

    public void addLicense(String licenseKey) throws Exception {
        waitUtils.waitForVisibility(settingMenuElement);
        scrollToElement(settingMenuElement);
        click(settingMenuElement);
        scrollToElement(manageLicenseElement);
        click(manageLicenseElement);
        waitUtils.waitForVisibility(licenseSaveButton);
        Thread.sleep(2000);
        type(licenseKeyInput, licenseKey);
        Thread.sleep(2000);
        click(licenseSaveButton);
        RemarkandOK(driver, "Test : License Added Successfully");
    }

    public void selectRole() throws Exception {
        By roleLocator = By.xpath("//div[@tabindex='-1' and normalize-space()='MANUFACTURER ADMIN']");
        clickWithRetry(roleLocator);
        click(manageRolePageName);
        click(roleEditButton);
    }

    public void updateRoleByAssignedAllButton() throws Exception {
        waitUtils.waitForVisibility(settingMenuElement);
        scrollToElement(settingMenuElement);
        click(settingMenuElement);
        scrollToElement(manageRoleElement);
        click(manageRoleElement);
        Thread.sleep(5000);
        waitUtils.waitForVisibility(manageRolePageName);
        selectRole();
        Thread.sleep(5000);
        waitUtils.waitForVisibility(assignedAllButton);
        click(assignedAllButton);
        click(updateRoleButton);
        RemarkandOK(driver, "Test : Role Updated Successfully By Assign All button");
    }

    public void adduser(String Name, String FName, String LName,
            String Designation,
            String Email,
            String Phone) throws Exception {
        waitUtils.waitForVisibility(settingMenuElement);
        scrollToElement(settingMenuElement);
        click(settingMenuElement);
        scrollToElement(manageUserElement);
        click(manageUserElement);
        waitUtils.waitForVisibility(addUserButton);
        click(addUserButton);
        waitUtils.waitForVisibility(userName);
        Thread.sleep(2000);
        type(userName, Name);
        type(firstName, FName);
        type(lastName, LName);
        type(department, Designation);
        type(email, Email);
        scrollToElement(phone);
        type(phone, Phone);
        click(addButton);
        RemarkandOK(driver, "Test : User Added Successfully");
    }

    public String getUserName() {
        waitUtils.waitForVisibility(DownloadButton);
        String TempUserName = getText(NewUserName);
        return TempUserName;
    }

    public String getToken() {
        waitUtils.waitForVisibility(DownloadButton);
        String TempToken = getText(Token);
        return TempToken;
    }

    public void clickOnDownloadButton() throws Exception {
        Thread.sleep(1000);
        click(DownloadButton);
        Thread.sleep(2000);
        handleSaveAsPopup();
        Thread.sleep(7000);
        click(refreshButton);
    }

    public void selectExisingUser(String userName) throws Exception {
        By userLocator = By
                .xpath("//div[@role='row']//div[@col-id='userName' and normalize-space()='" + userName + "']");
        clickWithRetry(userLocator);
        click(manageUserPageName);
    }

    public void userDeactiveFunctionality(String userName) throws Exception {
        waitUtils.waitForVisibility(settingMenuElement);
        scrollToElement(settingMenuElement);
        click(settingMenuElement);
        scrollToElement(manageUserElement);
        click(manageUserElement);
        waitUtils.waitForVisibility(addUserButton);
        selectExisingUser(userName);
        click(deactiveUsereButton);
        click(OK_ConfirmationMessage);
        RemarkandOK(driver, "Test : Deactivating User");
    }

}
