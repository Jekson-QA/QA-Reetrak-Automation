package com.reetrak.automation.pages.warehousemaster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReaggregationPage extends DeaggregationPage {

    @FindBy(xpath = "//div[contains(@class,'mat-tab-label') or contains(@class,'nav-link')]//div[normalize-space()='Reaggregation'] | //span[normalize-space()='Reaggregation']")
    private WebElement reaggregationTab;



    @FindBy(xpath = "//mat-select[contains(@placeholder,'Parent Type') or @formcontrolname='parentType' or @formcontrolname='selectParentType' or .//span[contains(text(), 'Select Parent Type')]] | //span[contains(text(), 'Select Parent Type')]/ancestor::mat-select")
    private WebElement selectParentTypeSelect;

    @FindBy(xpath = "//input[@id='parentCode' or @formcontrolname='parentCode' or contains(@placeholder,'Parent Code')] | //mat-form-field[.//mat-label[contains(text(), 'Parent Code')]]//input")
    private WebElement parentCodeInput;

    public ReaggregationPage(WebDriver driver) {
        super(driver);
    }

    public void clickReaggregationTab() {
        waitUtils.waitForElementToBeClickable(reaggregationTab);
        scrollToElement(reaggregationTab);
        click(reaggregationTab);
        waitForSeconds(1);
    }



    public void selectParentType(String value) {
        if (value != null && !value.isBlank()) {
            selectFromDropDown(selectParentTypeSelect, value);
        }
    }

    public void enterParentCode(String code) {
        if (code != null && !code.isBlank()) {
            scrollToElement(parentCodeInput);
            type(parentCodeInput, code);
        }
    }
}
