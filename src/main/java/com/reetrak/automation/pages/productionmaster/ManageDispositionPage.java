package com.reetrak.automation.pages.productionmaster;

import com.reetrak.automation.pages.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ManageDispositionPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and text()='Manage Disposition']")
    private WebElement manageDispositionMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and text()='Manage Disposition']")
    private WebElement manageDispositionPageName;

    @FindBy(xpath = "//*[@id='LineId']")
    private WebElement lineDropdownMenu;

    @FindBy(xpath = "//*[@id='getBatchfilter']/span")
    private WebElement filterbutton;

    @FindBy(xpath = "//*[@id='batchSelect']")
    private WebElement batchDropdownMenu;

    @FindBy(xpath = "//*[@id='batchLevel']")
    private WebElement batchlevelDropdownMenu;

    @FindBy(xpath = "//*[@id='serialFromStatus']")
    private WebElement currentstatusDropdownMenu;

    @FindBy(xpath = "//*[@id='codeList']")
    private WebElement confirmbutton;

    @FindBy(xpath = "//*[normalize-space(text())='Code List']")
    private WebElement codelist;

    @FindBy(xpath = "//span[contains(text(),'Click to view code')]")
    private WebElement viewcode;

    @FindBy(xpath = "//*[@id='serialToStatus']")
    private WebElement newstatusdropdownMenu;

    @FindBy(xpath = "(//div[contains(@class,'mat-checkbox-inner-container')])[2]")
    private WebElement code1;

    @FindBy(xpath = "(//span[@class='black-code'])[1]")
    private WebElement codeElement;

    @FindBy(xpath = "(//div[contains(@class,'mat-checkbox-inner-container')])[3]")
    private WebElement code2;

    @FindBy(xpath = "//*[@id='submit']")
    private WebElement savebutton;

    @FindBy(xpath = "//app-verify-code-dialog//h4")
    private WebElement confirmationdialog;

    @FindBy(xpath = "//*[@id='ok']")
    private WebElement confimrationdialogok;

    @FindBy(xpath = "//*[@id='remarks']")
    private WebElement remark;

    @FindBy(xpath = "//*[@id='submitRemark']")
    private WebElement remarkok;

    @FindBy(xpath = "(.//span[@class='black-code'])[1]")
    private WebElement getcode;

    @FindBy(xpath = "//*[@id='scanCode']")
    private WebElement scancode;

    @FindBy(xpath = "//input[@placeholder='Search code']")
    private WebElement searchcode;

    @FindBy(xpath = "(//*[@class='col-lg-3 col-md-4 col-sm-6 ng-star-inserted'])[1]")
    private WebElement cofirmcodeview;

    @FindBy(xpath = "//*[@id='search']")
    private WebElement confirmsearch;

    public ManageDispositionPage(WebDriver driver) {
        super(driver);
    }

    public void openManageDispostion() {
        waitUtils.waitForVisibility(productionMasterMenu);
        scrollToElement(productionMasterMenu);
        click(productionMasterMenu);
        scrollToElement(manageDispositionMenu);
        click(manageDispositionMenu);
        waitUtils.waitForVisibility(manageDispositionPageName);
    }

    public String getManageDispositionPageName() {
        return getText(manageDispositionPageName);
    }

    public String getcodedetails() {
        return getText(getcode);
    }

    public void confirmcodelist(String Action, String Line, String Batch, String Level, String CurrentStatus)
            throws InterruptedException {
        selectFromDropDown(lineDropdownMenu, Line);
        Thread.sleep(1000);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, Batch);
        selectFromDropDown(batchlevelDropdownMenu, Level);
        selectFromDropDown(currentstatusDropdownMenu, CurrentStatus);
        click(confirmbutton);
    }

    public void changestatus(String Action, String Line, String Batch, String Level, String CurrentStatus,
            String NewStatus) throws Exception {
        selectFromDropDown(lineDropdownMenu, Line);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, Batch);
        selectFromDropDown(batchlevelDropdownMenu, Level);
        selectFromDropDown(currentstatusDropdownMenu, CurrentStatus);
        click(confirmbutton);
        Thread.sleep(1000);
        click(viewcode);
        Thread.sleep(2000);
        click(code1);
        click(code2);
        Thread.sleep(1000);
        scrollToElement(newstatusdropdownMenu);
        Thread.sleep(1000);
        selectFromDropDown(newstatusdropdownMenu, NewStatus);
        click(savebutton);
        click(confimrationdialogok);
        RemarkandOK(driver, "Test : Status Change Successfully");
    }

    public String getCodeListTitle() {
        return getText(codelist);
    }

    public String getCodeList() {
        return getText(codeElement);
    }

    public void changestatussampletosampledestroy(String Action, String Line, String Batch, String Level,
            String CurrentStatus, String NewStatus) throws Exception {
        selectFromDropDown(lineDropdownMenu, Line);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, Batch);
        selectFromDropDown(batchlevelDropdownMenu, Level);
        selectFromDropDown(currentstatusDropdownMenu, CurrentStatus);
        click(confirmbutton);
        Thread.sleep(1000);
        click(viewcode);
        Thread.sleep(2000);
        click(code1);
        Thread.sleep(1000);
        scrollToElement(newstatusdropdownMenu);
        Thread.sleep(1000);
        selectFromDropDown(newstatusdropdownMenu, NewStatus);
        click(savebutton);
        click(confimrationdialogok);
        RemarkandOK(driver, "Test : Status Change Successfully");
    }

    public String scancode(String Action, String line, String batch,
            String Level, String CurrentStatus, String NewStatus)
            throws Exception {
        selectFromDropDown(lineDropdownMenu, line);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, batch);
        selectFromDropDown(batchlevelDropdownMenu, Level);
        selectFromDropDown(currentstatusDropdownMenu, CurrentStatus);
        click(confirmbutton);
        Thread.sleep(1000);
        click(viewcode);
        Thread.sleep(2000);
        // String tempcode="00"+getCodeList();
        String tempcode = getCodeList();
        if (tempcode.length() == 18) {
            typeAndEnter(scancode, "00" + tempcode);
        } else {
            typeAndEnter(scancode, "21" + tempcode);
        }
        // boolean checked = code1.isSelected();
        Thread.sleep(2000);
        if (code1.isSelected()) {
            return "Not Selected";
        } else {
            return "Selected";
        }
    }

    public String getsntypeFieldStatus() {
        waitUtils.waitForVisibility(code1);
        boolean checked = code1.isSelected();
        if (!checked) {
            return "Disabled";
        } else {
            return "Enabled";
        }
    }

    public String searchcode(String Action, String line, String batch,
            String level, String currentStatus,
            String newStatus) throws Exception {
        selectFromDropDown(lineDropdownMenu, line);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, batch);
        selectFromDropDown(batchlevelDropdownMenu, level);
        selectFromDropDown(currentstatusDropdownMenu, currentStatus);
        click(confirmbutton);
        Thread.sleep(1000);
        click(viewcode);
        String tempcode = getCodeList();
        Thread.sleep(2000);
        type(searchcode, tempcode);
        String tempcode1 = getCodeList();
        if (tempcode.equals(tempcode1)) {
            return "Code Matched";
        } else {
            return "Code Not Matched";
        }
    }

    public String confirmcodesearch(String Action, String Line, String Batch, String Level, String CurrentStatus,
            String NewStatus) throws Exception {
        selectFromDropDown(lineDropdownMenu, Line);
        click(filterbutton);
        selectFromDropDown(batchDropdownMenu, Batch);
        selectFromDropDown(batchlevelDropdownMenu, Level);
        selectFromDropDown(currentstatusDropdownMenu, CurrentStatus);
        click(confirmbutton);
        Thread.sleep(1000);
        click(viewcode);
        Thread.sleep(2000);
        click(code1);
        click(code2);
        Thread.sleep(1000);
        scrollToElement(newstatusdropdownMenu);
        Thread.sleep(1000);
        selectFromDropDown(newstatusdropdownMenu, NewStatus);
        click(savebutton);
        String tempcode = getCodeList();
        Thread.sleep(2000);
        type(confirmsearch, tempcode);
        Thread.sleep(2000);
        String tempcode1 = getconfirmcodedetails();
        if (tempcode.equals(tempcode1)) {
            return "Code Matched";
        } else {
            return "Code Not Matched";
        }
    }

    public String getconfirmcodedetails() {
        return getText(cofirmcodeview);
    }
}
