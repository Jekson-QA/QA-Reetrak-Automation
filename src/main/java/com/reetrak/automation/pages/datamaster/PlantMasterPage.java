package com.reetrak.automation.pages.datamaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlantMasterPage extends BasePage {

    // @FindBy(xpath = "//p[text()='Data Master ']")
    @FindBy(xpath = "//*[@id='title-RTK_DATA_MASTER']/p")
    private WebElement dataMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Plant Master']")
    private WebElement plantMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Plant Master']")
    private WebElement plantPageName;

    @FindBy(xpath = "(//button[@type='button'])[3]")
    private WebElement plantAddButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addPlantInfoTitle;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement updatePlantInfoTitle;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement savebutton;

    @FindBy(xpath = "//span[normalize-space()='Cancel'] | //button[@id='close']")
    private WebElement cancelbutton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement plantname;

    @FindBy(xpath = "//input[@id='plantCode']")
    private WebElement plantcode;

    @FindBy(xpath = "//input[@id='licenseNumber']")
    private WebElement licenseNumber;

    @FindBy(xpath = "//input[@id='agency']")
    private WebElement licenseagency;

    @FindBy(xpath = "//input[@id='licStateOrRegion']")
    private WebElement licStateOrRegion;

    @FindBy(xpath = "//input[@id='glnprefix']")
    private WebElement glnprefix;

    @FindBy(xpath = "//input[@id='glnNo']")
    private WebElement glnnumber;

    @FindBy(xpath = "//input[@id='address1']")
    private WebElement address1;

    @FindBy(xpath = "//input[@id='address2']")
    private WebElement address2;

    @FindBy(xpath = "//input[@id='pincode']")
    private WebElement pincode;

    @FindBy(xpath = "//input[@id='city']")
    private WebElement city;

    @FindBy(xpath = "//input[@id='state']")
    private WebElement state;

    @FindBy(xpath = "//input[@id='country']")
    private WebElement country;

    @FindBy(xpath = "//input[@id='phone']")
    private WebElement phone;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement email;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editPlantButton;

    @FindBy(xpath = "//button[@id='refresh']")
    WebElement refreshButton;

    @FindBy(xpath = "//button[@id='deactive']")
    private WebElement deactivePlantButton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement activePlantButton;

    @FindBy(xpath = "//div[contains(@class,'card-body')]//p")
    WebElement active_deactiveConfirmationMessage;

    @FindBy(xpath = "//button[@id='submitRemark']")
    WebElement okButton_on_active_deactiveConfirmationMessage;

    @FindBy(xpath = "//div[@tabindex='-1' and @col-id='3']")
    WebElement plantStatus_onGrid;

    @FindBy(xpath = "//label[@for='isSameAsCompanyAddress-input']")
    WebElement checkBox_of_SameasCompanyAddress;

    public PlantMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openPlantMaster() {
        waitUtils.waitForVisibility(dataMasterMenu);
        scrollToElement(dataMasterMenu);
        click(dataMasterMenu);
        scrollToElement(plantMasterMenu);
        click(plantMasterMenu);
        waitUtils.waitForVisibility(plantPageName);
    }

    public String getplantMasterPageName() throws Exception {
        return getText(plantPageName);
    }

    public void Addplant() {
        waitUtils.waitForVisibility(plantAddButton);
        click(plantAddButton);
    }

    public String getaddPlantInfoTitle() throws Exception {
        return getText(addPlantInfoTitle);
    }

    public String savebuttonstatus() {

        waitUtils.waitForVisibility(savebutton);
        boolean status = savebutton.isEnabled();
        if (!status) {
            return "Save button is disabled";
        } else {
            return "Save button should be enabled";
        }
    }

    public void addPlantDetails(
            String plantNameValue,
            String plantCodeValue,
            String licenseNumberValue,
            String licenseAgencyValue,
            String licStateOrRegionValue,
            String glnPrefixValue,
            String glnNumberValue,
            String address1Value,
            String address2Value,
            String pincodeValue,
            String cityValue,
            String stateValue,
            String countryValue,
            String phoneValue,
            String emailValue) {

        updateIfProvided(plantname, plantNameValue);
        updateIfProvided(plantcode, plantCodeValue);
        updateIfProvided(licenseNumber, licenseNumberValue);
        updateIfProvided(licenseagency, licenseAgencyValue);
        updateIfProvided(licStateOrRegion, licStateOrRegionValue);
        updateIfProvided(glnprefix, glnPrefixValue);
        updateIfProvided(glnnumber, glnNumberValue);
        updateIfProvided(address1, address1Value);
        updateIfProvided(address2, address2Value);
        updateIfProvided(pincode, pincodeValue);
        updateIfProvided(city, cityValue);
        updateIfProvided(state, stateValue);
        updateIfProvided(country, countryValue);
        updateIfProvided(phone, phoneValue);
        updateIfProvided(email, emailValue);
    }

    public void updatePlantDetails(
            String plantNameValue,
            String licenseNumberValue,
            String licenseAgencyValue,
            String licStateOrRegionValue,
            String glnPrefixValue,
            String glnNumberValue,
            String address1Value,
            String address2Value,
            String pincodeValue,
            String cityValue,
            String stateValue,
            String countryValue,
            String phoneValue,
            String emailValue) {

        updateIfProvided(plantname, plantNameValue);
        updateIfProvided(licenseNumber, licenseNumberValue);
        updateIfProvided(licenseagency, licenseAgencyValue);
        updateIfProvided(licStateOrRegion, licStateOrRegionValue);
        updateIfProvided(glnprefix, glnPrefixValue);
        updateIfProvided(glnnumber, glnNumberValue);
        updateIfProvided(address1, address1Value);
        updateIfProvided(address2, address2Value);
        updateIfProvided(pincode, pincodeValue);
        updateIfProvided(city, cityValue);
        updateIfProvided(state, stateValue);
        updateIfProvided(country, countryValue);
        updateIfProvided(phone, phoneValue);
        updateIfProvided(email, emailValue);
    }

    public void clickSave() throws Exception {
        scrollToElement(savebutton);
        click(savebutton);
        RemarkandOK(driver, "Test:OK");
    }

    public void clickCancel() throws Exception {
        scrollToElement(cancelbutton);
        click(cancelbutton);
        waitUtils.waitForVisibility(plantMasterMenu);
    }

    public void selectPlant() throws Exception {
        Thread.sleep(2000);
        By firstRecord = (By.xpath("(//div[@role='row' and @row-index='0'])[2]"));
        clickWithRetry(firstRecord);
        click(plantPageName);
    }

    public void openPlantForEdit() throws Exception {
        selectPlant();
        click(editPlantButton);
    }

    public String getUpdatePlantTitle() {
        waitUtils.waitForVisibility(updatePlantInfoTitle);
        return getText(updatePlantInfoTitle);
    }

    private void updateIfProvided(WebElement element, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        scrollToElement(element);
        element.clear();
        type(element, value);
    }

    public String deactivePlant() throws Exception {
        selectPlant();
        click(plantPageName);
        click(deactivePlantButton);
        String deactiveMessage = getText(active_deactiveConfirmationMessage);
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Deactivate Plant");
        return deactiveMessage;
    }

    public String getdeactivemessage() {
        return getText(active_deactiveConfirmationMessage);
    }

    public String activeplant() throws Exception {
        selectPlant();
        click(activePlantButton);
        String activeMessage = getText(active_deactiveConfirmationMessage);
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test:Activate Plant");
        return activeMessage;
    }

    public String getactivemessage() {
        return getText(active_deactiveConfirmationMessage);
    }

    public String getPlantStatus() {
        waitUtils.waitForVisibility(plantStatus_onGrid);
        return getText(plantStatus_onGrid);
    }

    public void sameascompanyaddress() throws Exception {
        selectPlant();
        click(editPlantButton);
        waitUtils.waitForVisibilityforscroll(checkBox_of_SameasCompanyAddress);
        click(checkBox_of_SameasCompanyAddress);
    }

    public String getPlantCodeFieldStatus() {
        waitUtils.waitForVisibility(plantcode);
        boolean status = plantcode.isEnabled();
        if (!status) {
            return "Plant Code field is disabled";
        } else {
            return "Plant Code field is enabled";
        }
    }

    public String refreshButton() throws Exception {
        selectPlant();
        waitUtils.waitForVisibility(refreshButton);
        click(refreshButton);
        boolean isInvisible = waitUtils.waitForInvisibility(editPlantButton);
        if (isInvisible) {
            return "Page Refresh Successfully";
        } else {
            return "Issue";
        }
    }

}
