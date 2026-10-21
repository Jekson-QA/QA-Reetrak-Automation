package com.reetrak.automation.pages.productionmaster;

import com.reetrak.automation.pages.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class POMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='PO Master']")
    private WebElement poMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='PO Master']")
    private WebElement poMasterPageName;

    @FindBy(xpath = "//*[@id=\"refresh\"]")
    private WebElement refreshButton;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addButton;

    @FindBy(xpath = "//button[@id='import']")
    private WebElement importButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@id='filter']")
    private WebElement filterButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addPoDialogTitle;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement updatePoDialogTitle;

    @FindBy(xpath = "//input[@id='identifier']")
    private WebElement poNumberInput;

    @FindBy(xpath = "//input[@id='LotNumber']")
    private WebElement batchNumberInput;

    @FindBy(xpath = "//mat-dialog-container//*[contains(normalize-space(),'Mfg Date')]/ancestor::mat-form-field[1]//input")
    private WebElement mfgDateInput;

    @FindBy(xpath = "//mat-dialog-container//*[contains(normalize-space(),'Exp Date')]/ancestor::mat-form-field[1]//input")
    private WebElement expDateInput;

    @FindBy(xpath = "//input[@id='quantity']")
    private WebElement batchSizeInput;

    @FindBy(xpath = "//mat-select[@id='productCode']")
    private WebElement productDropdown;

    @FindBy(xpath = "//mat-select[@id='lineDetail']")
    private WebElement lineDropdown;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//button[@id='close']")
    private WebElement cancelButton;

    @FindBy(xpath = "//input[@id='quickFilterInput']")
    private WebElement quickFilterInput;

    public POMasterPage(WebDriver driver) {
        super(driver);
    }

    public String getPOMasterPageName() throws Exception {
        return getText(poMasterPageName);
    }

    public void openPOMaster() {
        boolean isExpanded = false;
        try {
            isExpanded = poMasterMenu.isDisplayed();
        } catch (Exception e) {
            isExpanded = false;
        }
        if (!isExpanded) {
            waitUtils.waitForVisibility(productionMasterMenu);
            scrollToElement(productionMasterMenu);
            click(productionMasterMenu);
        }
        scrollToElement(poMasterMenu);
        click(poMasterMenu);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.textToBePresentInElement(poMasterPageName, "PO Master"));
    }

    public void AddorUpdatePO(String Action, String existingPO, String PO, String Batch, String MfgDate, String ExpDate,
            String BatchSize,
            String Product, String Line) throws Exception {

        if ("Add".equalsIgnoreCase(Action)) {
            click(addButton);
        }
        if ("Update".equalsIgnoreCase(Action)) {
            editPO(existingPO);
        }

        type(poNumberInput, PO);
        type(batchNumberInput, Batch);
        setDateInput(mfgDateInput, MfgDate);
        setDateInput(expDateInput, ExpDate);
        scrollToElement(batchSizeInput);
        type(batchSizeInput, BatchSize);
        scrollToElement(productDropdown);
        selectFromDropDown(productDropdown, Product);
        scrollToElement(lineDropdown);
        selectFromDropDown(lineDropdown, Line);

        if ("Add".equalsIgnoreCase(Action)) {
            click(saveButton);
            RemarkandOK(driver, "Test : " + PO + " and " + Batch + " added successfully");
        }
        if ("Update".equalsIgnoreCase(Action)) {
            click(saveButton);
            RemarkandOK(driver, "Test : " + PO + " and " + Batch + " updated successfully");
        }

    }

    public void selectExisingPO(String PO) throws Exception {
        type(quickFilterInput, PO);
        Thread.sleep(1000);
        By poLocator = By.xpath("//div[@role='gridcell' and @col-id='identifier' and normalize-space()='" + PO + "']");
        clickWithRetry(poLocator);
        click(poMasterPageName);
    }

    public void editPO(String PO) throws Exception {
        selectExisingPO(PO);
        waitUtils.waitForVisibility(editButton);
        click(editButton);
        waitUtils.waitForVisibility(updatePoDialogTitle);
    }

}
