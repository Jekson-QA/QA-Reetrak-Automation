package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompanyMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Company Master']")
    private WebElement companyMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Company Master']")
    private WebElement pageName;

    @FindBy(xpath = "//span[@data-notify='message' and text()='Company details Successfully updated.']")
    private WebElement notification;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@id='button']")
    private WebElement refreshButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement editCompanyTitle;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Name']")
    // private WebElement nameInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Name' or @name='name']")
    private WebElement nameInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='Prefix']")
    // private WebElement companyPrefixInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Prefix' or @name='companyprefix']")
    private WebElement companyPrefixInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//div[@class='mat-select-value']")
    // private WebElement snLengthDropdown;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//mat-select[@id='SN Length']")
    private WebElement snLengthDropdown;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='FileInput' and @type='file']")
    // private WebElement logoInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@type='file' or @id='FileInput']")
    private WebElement logoInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='URL(www.xyz.xyz)']")
    // private WebElement URL;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='URL' or @id='url']")
    private WebElement URL;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='Address 1']")
    // private WebElement address1Input;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Address1' or @id='address1']")
    private WebElement address1Input;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='Address 2']")
    // private WebElement address2Input;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Address2' or @id='address2']")
    private WebElement address2Input;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='Pin Code']")
    // private WebElement pinCodeInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Pin Code' or @id='pincode']")
    private WebElement pinCodeInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='City']")
    // private WebElement cityInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='City' or @id='city']")
    private WebElement cityInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='State']")
    // private WebElement stateInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='State' or @id='state']")
    private WebElement stateInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@placeholder='Country']")
    // private WebElement countryInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//input[@id='Country' or @id='country']")
    private WebElement countryInput;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement Remarkcancelbutton;

    public CompanyMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openCompanyMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(companyMasterMenu);
        click(companyMasterMenu);
        waitUtils.waitForVisibility(pageName);
    }

    public String getCompanyMasterPageName() throws InterruptedException {
        Thread.sleep(1000);
        return getText(pageName);
    }

    public String getnotification() {
        return getText(notification);
    }

    public void updateCompany() {
        selectCompany();
        // WebElement firstRecord = driver.findElement(By.xpath("(//div[@role='row' and
        // @row-index='0'])[2]"));
        // waitUtils.waitForElementToBeClickable(firstRecord);
        // firstRecord.click();
        // pageName.click();
        // waitUtils.customewaitForVisibility(editButton, 50).isDisplayed();
        click(editButton);
        waitUtils.waitForVisibility(editCompanyTitle);
    }

    public void selectCompany() {
        WebElement firstRecord = driver.findElement(By.xpath("(//div[@role='row' and @row-index='0'])[2]"));
        waitUtils.waitForElementToBeClickable(firstRecord);
        firstRecord.click();
        pageName.click();
        waitUtils.customewaitForVisibility(editButton, 50).isDisplayed();
    }

    public String getEditCompanyTitle() {
        return getText(editCompanyTitle);
    }

    public void updateCompanyDetails(

            String url,
            String logoPath,
            String address1,
            String address2,
            String pinCode,
            String city,
            String state,
            String country) throws InterruptedException {

        updateIfProvided(URL, url);
        updateLogoIfProvided(logoPath);
        updateIfProvided(address1Input, address1);
        updateIfProvided(address2Input, address2);
        updateIfProvided(pinCodeInput, pinCode);
        updateIfProvided(cityInput, city);
        updateIfProvided(stateInput, state);
        updateIfProvided(countryInput, country);
    }

    public void clickSave() throws InterruptedException {
        scrollToElement(saveButton);
        waitUtils.waitForVisibility(saveButton);
        click(saveButton);
        RemarkandOK(driver, "Test:Updated Company Details");
        waitUtils.waitForVisibility(notification);
    }

    public void clickCancel() {
        scrollToElement(cancelButton);
        click(cancelButton);
        waitUtils.waitForVisibility(pageName);
    }

    private void updateIfProvided(WebElement element, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        scrollToElement(element);
        type(element, value);
    }

    private void updateLogoIfProvided(String logoPath) throws InterruptedException {
        if (logoPath == null || logoPath.isBlank()) {
            return;
        }
        Thread.sleep(1000);
        logoInput.sendKeys(logoPath);
    }

    public String refreshButton() throws Exception {
        // selectExistingRole(availableRole);
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editButton);
        if (isInvisible) {
            return "Page Refresh Successfully";
        } else {
            return "Issue";
        }
    }
}
