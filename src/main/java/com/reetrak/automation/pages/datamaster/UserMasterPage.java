package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='User Master']")
    private WebElement userMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='User Master']")
    private WebElement userMasterPageName;

    @FindBy(xpath = "//button[@id='addRole']")
    private WebElement addUserButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editUsereButton;

    @FindBy(xpath = "//button[@id='isActive']")
    private WebElement deactiveUsereButton;

    @FindBy(xpath = "//button[@id='deactive']")
    private WebElement activeUsereButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='REGENERATE KEY']]")
    private WebElement RegenrateKeyButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='FORCED LOGOUT']]")
    private WebElement ForceedLogoutButton;

    @FindBy(xpath = "//button[@id='refresh']")
    private WebElement refreshButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addEditUserPageTitle;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='User Name']")
    // private WebElement userName;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='User Name' or @id='userName']")
    private WebElement userName;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='First Name']")
    // private WebElement firstName;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='First Name' or @id='firstName']")
    private WebElement firstName;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Last Name']")
    // private WebElement lastName;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Last Name' or @id='lastName']")
    private WebElement lastName;

    // @FindBy(xpath = "(//span[text()='Role'])[2]")
    @FindBy(xpath = "//div[@class='mat-select-arrow-wrapper']/div[@class='mat-select-arrow']")
    private WebElement roleDropDown;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Designation/Department']")
    // private WebElement department;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Designation/Department' or @id='designation']")
    private WebElement department;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Email']")
    // private WebElement email;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Email' or @id='email']")
    private WebElement email;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Phone']")
    // private WebElement phone;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Phone' or @id='phone']")
    private WebElement phone;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//button[@id='newSubmit1']")
    // private WebElement addButton;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//button[@id='newSubmit1' or @id='newSubmit']")
    private WebElement addButton;

    @FindBy(xpath = "//button[@id='newSubmit']")
    private WebElement updateButton;

    @FindBy(xpath = "//mat-checkbox[@id='isChangePassword']")
    private WebElement changePasswordCheckBox;

    @FindBy(xpath = "//input[@id='newPassword']")
    private WebElement newPassword;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//button[@id='close']")
    private WebElement closeButton;

    @FindBy(xpath = "(//span[@class='highlighted-text'])[1]")
    private WebElement NewUserName;

    @FindBy(xpath = "(//span[@class='highlighted-text'])[2]")
    private WebElement Token;

    @FindBy(xpath = "//button[@id='submitRemark']")
    private WebElement DownloadButton;

    @FindBy(xpath = "//div[contains(@class,'card-body')]//p")
    WebElement confirmationMessage;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement OK_ConfirmationMessage;

    public UserMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openUserMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(userMasterMenu);
        click(userMasterMenu);
    }

    public String getUserMasterPageName() throws Exception {
        return getText(userMasterPageName);
    }

    public void addUser() {
        click(addUserButton);
    }

    public String getAddEditUserPageTitle() throws Exception {
        return getText(addEditUserPageTitle);
    }

    public String getAddButtonStatus() {
        waitUtils.waitForVisibility(addButton);
        boolean status = addButton.isEnabled();
        if (!status) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    public void AddorUpdateUser(String Action, String Name, String FName, String LName, String RoleName,
            String Designation,
            String Email,
            String Phone) throws Exception {
        if ("Add".equalsIgnoreCase(Action)) {
            addUser();
            Thread.sleep(2000);
            type(userName, Name);
        }
        if ("Update".equalsIgnoreCase(Action)) {
            editUser(Name);
        }
        type(firstName, FName);
        type(lastName, LName);
        selectFromDropDown(roleDropDown, RoleName);
        type(department, Designation);
        type(email, Email);
        scrollToElement(phone);
        type(phone, Phone);

        if ("Add".equalsIgnoreCase(Action)) {
            click(addButton);
            RemarkandOK(driver, "Test : New User Created Successfully");
        }
        if ("Update".equalsIgnoreCase(Action)) {
            click(updateButton);
            RemarkandOK(driver, "Test : Updated Esiting User Successfully");
        }
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

    public String getUsernameFieldStatus() throws Exception {
        waitUtils.waitForVisibility(userName);
        Thread.sleep(2000);
        boolean status = userName.isEnabled();
        if (!status) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    public void selectExisingUser(String userName) throws Exception {
        By userLocator = By
                .xpath("//div[@role='row']//div[@col-id='userName' and normalize-space()='" + userName + "']");
        clickWithRetry(userLocator);
        click(userMasterPageName);
    }

    public void editUser(String userName) throws Exception {
        selectExisingUser(userName);
        waitUtils.waitForVisibility(editUsereButton);
        click(editUsereButton);
        waitUtils.waitForVisibility(addEditUserPageTitle);
    }

    public String regenerateKeyFunctionality(String userName) throws Exception {
        clickOnRegenerateKeyButton(userName);
        String Tempconfirmation = getText(confirmationMessage);
        click(OK_ConfirmationMessage);
        return Tempconfirmation;
    }

    public void clickOnRegenerateKeyButton(String userName) throws Exception {
        selectExisingUser(userName);
        click(RegenrateKeyButton);
    }

    public String forcedLogoutFunctionality(String userName) throws Exception {
        selectExisingUser(userName);
        click(ForceedLogoutButton);
        // String Tempconfirmation = getText(confirmationMessage);
        String Tempconfirmation = "Do you you want to logout selected user " + userName + " ?";
        click(OK_ConfirmationMessage);
        RemarkandOK(driver, "Test : Forced Logout Attempt");
        return Tempconfirmation;
    }

    public void changePasswordFunctionality(String userName, String TempPassword) throws Exception {
        editUser(userName);
        scrollToElement(changePasswordCheckBox);
        click(changePasswordCheckBox);
        scrollToElement(newPassword);
        type(newPassword, TempPassword);
        scrollToElement(confirmPassword);
        type(confirmPassword, TempPassword);
        click(updateButton);
        RemarkandOK(driver, "Test : Password updated");
    }

    public String userActiveDeactiveFunctionality(String Action, String userName) throws Exception {
        String Tempconfirmation = "";
        selectExisingUser(userName);
        if ("Deactivate".equalsIgnoreCase(Action)) {
            Thread.sleep(1000);
            // waitUtils.waitForVisibility(deactiveUsereButton);
            click(deactiveUsereButton);
            Tempconfirmation = "Do you want to deactivate : " + userName + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Deactivating User");
        }
        if ("Activate".equalsIgnoreCase(Action)) {
            Thread.sleep(1000);
            // waitUtils.waitForVisibility(activeUsereButton);
            click(activeUsereButton);
            Tempconfirmation = "Do you want to activate : " + userName + " ?";
            click(OK_ConfirmationMessage);
            RemarkandOK(driver, "Test : Activating User");
        }
        return Tempconfirmation;
    }

    public String getUserStatus(String userName) throws Exception {
        By userLocator = By
                .xpath("//div[@role='row']//div[@col-id='userName' and normalize-space()='" + userName + "']");
        clickWithRetry(userLocator);

        By statusCellLocator = By.xpath("//div[@role='row']//div[@col-id='userName' and normalize-space()='"
                + userName + "']/following-sibling::div[last()]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        for (int i = 0; i < 3; i++) {
            try {
                WebElement statusCell = wait.until(ExpectedConditions.visibilityOfElementLocated(statusCellLocator));
                return statusCell.getText().trim();
            } catch (Exception e) {
                if (i == 2) {
                    throw e;
                }
                Thread.sleep(500);
            }
        }
        throw new IllegalStateException("Could not get user status for user: " + userName);
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editUsereButton);
        if (isInvisible) {
            return "Page Refresh Successfully";
        } else {
            return "Issue";
        }
    }

}
