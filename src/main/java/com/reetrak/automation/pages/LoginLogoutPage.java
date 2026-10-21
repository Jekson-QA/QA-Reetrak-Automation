package com.reetrak.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginLogoutPage extends BasePage {

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='User Name']")
    // private WebElement usernameInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='userName' or @placeholder='User Name']")
    private WebElement usernameInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='Password']")
    // private WebElement passwordInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='password' or @placeholder='Password']")
    private WebElement passwordInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//button[@type='submit']")
    // private WebElement loginButton;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//button[@id='logIn' or @type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement ALbtn;

    @FindBy(xpath = "//img[@src='./assets/img/FingerprintExeNotConnected.png']")
    WebElement fingerprintimage;

    public LoginLogoutPage(WebDriver driver) {
        super(driver);
    }

    public void open(String baseUrl) {
        driver.get(baseUrl);
    }

    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public void loginAs(String username, String password) throws InterruptedException {
        enterUsername(username);
        enterPassword(password);
        try {
            waitUtils.customewaitForVisibility(fingerprintimage, 2).isDisplayed();
            System.out.println(" Finger Print is eanbled ->>>>>");
        } catch (Exception e) {
            System.out.println(" Finger Print is not eanbled ->>>>>");
        }
        // waitUtils.waitForVisibility(fingerprintimage).isDisplayed();
        clickLogin();
        try {
            Thread.sleep(1000);
            if (ALbtn.isDisplayed()) {
                ALbtn.click();
            }
            // if (waitUtils.waitForVisibility(ALbtn).isDisplayed()) {
            // ALbtn.click();
            // }
        } catch (Exception e) {
            System.out.println(" Element Not Display during login ->>>>>");
        }
    }

    // ----------------------Logout Start----------------------------
    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//a[@class='nav-link']")
    // WebElement Profile;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//a[@id='navbarDropdownMenuLink']")
    WebElement Profile;

    @FindBy(xpath = "//a[.//i[text()='exit_to_app'] and contains(normalize-space(.), 'Log Out')]")
    WebElement Logout;

    public void clickonprofile() {
        waitUtils.waitForVisibility(Profile).isDisplayed();
        click(Profile);
    }

    public void clickonlogout() {
        waitUtils.waitForVisibility(Logout).isDisplayed();
        click(Logout);
    }

    public void logoutAs() throws InterruptedException {
        clickonprofile();
        clickonlogout();
    }
    // ----------------------Logout End----------------------------

    // ----------------------Set Password--Start----------------------------

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//h4[text()='Set Password']")
    // private WebElement SetPasswordTitle;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//app-reset-password//h3")
    private WebElement SetPasswordTitle;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    private WebElement TokenField;

    @FindBy(xpath = "(//input[@type='password'])[2]")
    private WebElement NewPasswordField;

    @FindBy(xpath = "(//input[@type='password'])[3]")
    private WebElement ConfirmPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement SetButton;

    public void setPassword(String Text1, String Text2, String Text3) {
        waitUtils.waitForVisibility(SetPasswordTitle);
        type(TokenField, Text1);
        type(NewPasswordField, Text2);
        type(ConfirmPasswordField, Text3);
        click(SetButton);
    }

    // ----------------------Set Password--End----------------------------

    // @FindBy(xpath = "//span[contains(normalize-space(), 'Dashboard')]")
    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text() = 'Dashboard']")
    private WebElement DashbaordTitle;

    public String getDashbaordTitle() {
        waitUtils.waitForVisibility(DashbaordTitle).isDisplayed();
        return getText(DashbaordTitle);
    }

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//h4[@class='card-title' and text()='LOG IN']")
    // private WebElement LoginTitle;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//h3[normalize-space()='Welcome back']")
    private WebElement LoginTitle;

    public String getLoginTitle() {
        waitUtils.waitForVisibility(LoginTitle).isDisplayed();
        return getText(LoginTitle);
    }

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//mat-error[@id='mat-error-0' and text()=' Please enter valid user Name']")
    // private WebElement UsernameTitle;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//mat-error[normalize-space()='Please enter valid user Name']")
    private WebElement UsernameTitle;

    public String getUsernameTitle() {
        waitUtils.waitForVisibility(UsernameTitle).isDisplayed();
        return getText(UsernameTitle);
    }

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//mat-error[@id='mat-error-1' and text()=' Please enter valid Password']")
    // private WebElement PasswordTitle;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//mat-error[normalize-space()='Please enter valid Password']")
    private WebElement PasswordTitle;

    public String getPasswordTitle() {
        waitUtils.waitForVisibility(PasswordTitle).isDisplayed();
        return getText(PasswordTitle);
    }

}