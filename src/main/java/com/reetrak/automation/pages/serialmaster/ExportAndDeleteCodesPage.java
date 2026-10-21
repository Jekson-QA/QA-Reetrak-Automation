package com.reetrak.automation.pages.serialmaster;

import com.reetrak.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExportAndDeleteCodesPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_SERIAL_MASTER']/p")
    private WebElement serialMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='SSCC Pool']")
    private WebElement ssccPoolMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='SSCC Pool']")
    private WebElement ssccPoolPageName;

    @FindBy(xpath = ("//button[@id='edit']"))
    WebElement editButton;

    @FindBy(xpath = "//button[@id='submitRemark' and text()=' Ok']")
    WebElement okButton_ConfirmationMessage;

    @FindBy(xpath = "//i[@mattooltip='EXPORT']")
    WebElement exportButtonElement;

    @FindBy(xpath = "//i[@mattooltip='DELETE']")
    WebElement deleteButtonElement;

    public ExportAndDeleteCodesPage(WebDriver driver) {
        super(driver);
    }

    public void openSSCCPool() {
        waitUtils.waitForVisibility(serialMasterMenu);
        scrollToElement(serialMasterMenu);
        click(serialMasterMenu);
        scrollToElement(ssccPoolMenu);
        click(ssccPoolMenu);
        waitUtils.waitForVisibility(ssccPoolPageName);
    }

    public void clickEditSSCCPool() throws Exception {
        waitUtils.waitForVisibility(editButton);
        Thread.sleep(1000);
        click(editButton);
    }

    public void selectSSCCPool(String SNM, String Prefix, String Edigit) throws Exception {
        Thread.sleep(1000);
        By SSCCPool = (By.xpath("//div[@col-id='0' and normalize-space()='" + SNM
                + "']/following-sibling::div[@col-id='prefix' and normalize-space()='" + Prefix
                + "']/following-sibling::div[@col-id='extdigit' and normalize-space()='" + Edigit + "']"));
        clickWithRetry(SSCCPool);
        click(ssccPoolPageName);
    }

    public void exportCode() throws Exception {
        waitUtils.waitForVisibility(exportButtonElement);
        Thread.sleep(1000);
        click(exportButtonElement);
        handleSaveAsPopup();
    }

    public String deleteCode()
            throws Exception {
        waitUtils.waitForVisibility(deleteButtonElement);
        Thread.sleep(1000);
        click(deleteButtonElement);
        String TempMessage = "Are you sure you want to delete serial codes ?";
        click(okButton_ConfirmationMessage);
        return TempMessage;
    }

}
