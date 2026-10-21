package com.reetrak.automation.pages.productionmaster;

import com.reetrak.automation.pages.BasePage;
import com.reetrak.automation.pages.catalogue.InboxPage;
import com.reetrak.automation.utilities.ConfigReader;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductMasterPage extends BasePage {

    @FindBy(xpath = "//*[@id='title-RTK_PRODUCTION_MASTER']/p")
    private WebElement productionMasterMenu;

    @FindBy(xpath = "//span[@class='sidebar-normal' and normalize-space()='Product Master']")
    private WebElement productMasterMenu;

    @FindBy(xpath = "//span[@style='text-transform: capitalize;' and normalize-space()='Product Master']")
    private WebElement productMasterPageName;

    @FindBy(xpath = "//button[@id='add']")
    private WebElement addProductButton;

    @FindBy(xpath = "//button[@id='edit']")
    private WebElement editButton;

    @FindBy(xpath = "//button[@id='active']")
    private WebElement deactiveProductButton;

    @FindBy(xpath = "//button[@id='deactive']")
    private WebElement activeProductButton;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement addProductSectionTitle;

    @FindBy(xpath = "//h4[contains(@class,'card-title-3d')]")
    private WebElement EditProductSectionTitle;

    @FindBy(xpath = "//*[@id='expandAll']")
    private WebElement expandAllButton;

    @FindBy(xpath = "//*[@id='collapseAll']")
    private WebElement collapseAllButton;

    @FindBy(xpath = "//input[@id='quickFilterInput']")
    private WebElement serachInput;

    @FindBy(xpath = "//button[@id='submitRemark']")
    private WebElement okButton_on_active_deactiveConfirmationMessage;

    @FindBy(xpath = "//button[@id='copy']")
    private WebElement copyButton;

    // -------- Product - Product Details Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//input[@id='name']")
    private WebElement productName;

    @FindBy(xpath = "//input[@id='code']")
    private WebElement productCode;

    @FindBy(xpath = "//input[@id='intmatno']")
    private WebElement productCustomerCode;

    @FindBy(xpath = "//input[@id='strength']")
    private WebElement productStrength;

    @FindBy(xpath = "//input[@id='dosageForm']")
    private WebElement productDosageForm;

    @FindBy(xpath = "//mat-select[@id='companyPrefixID']")
    private WebElement serialManagerDropdown;

    @FindBy(xpath = "//input[@id='prodDesc']")
    private WebElement productDescription;

    @FindBy(xpath = "//mat-select[@id='complianceId']")
    private WebElement complianceDropdown;

    @FindBy(xpath = "//input[@id='country']")
    private WebElement productCountry;

    @FindBy(xpath = "//input[@id='unitprice']")
    private WebElement productUnitPrice;

    @FindBy(xpath = "//input[@id='packsize']")
    private WebElement productPackSize;

    @FindBy(xpath = "//mat-select[@id='packagingType']")
    private WebElement packagingTypeDropdown;

    @FindBy(xpath = "//mat-select[@id='skutype']")
    private WebElement tradeTypeDropdown;

    @FindBy(xpath = "//span[contains(normalize-space(), 'Level Information')]")
    private WebElement next1Button;

    @FindBy(xpath = "//span[contains(normalize-space(), 'Other Information')]")
    private WebElement next2Button;

    @FindBy(xpath = "//span[contains(normalize-space(), 'Extra Fields')]")
    private WebElement next3Button;

    // @FindBy(xpath = "//span[text()=' 2. Level Information']")
    // private WebElement levelInformationTitle;

    @FindBy(xpath = "//mat-select[@id='noOfLevel']")
    private WebElement noOfLevelsDropdown;

    // -------- Change Level dialog Locators

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//mat-select[@id='Select No. Of Level']")
    // private WebElement checkbox_for_changing_levels;

    // -------- New Reetrak UI-------
    @FindBy(xpath = "//mat-select[@id='level']")
    private WebElement checkbox_for_changing_levels;

    @FindBy(xpath = "//button[@id='addthreshold']")
    private WebElement saveButton_on_changing_levels;

    // -------- Product - Level-1 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level0']")
    private WebElement level1Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue0']")
    private WebElement level1GTIN;

    @FindBy(xpath = "//input[@id='packsize0']")
    private WebElement level1PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption0']")
    private WebElement level1PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength0']")
    private WebElement level1PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired0']")
    private WebElement level1RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold0']")
    private WebElement level1ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn0']")
    private WebElement level1PRNinformation;

    // -------- Product - Level-2 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level1']")
    private WebElement level2Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue1']")
    private WebElement level2GTIN;

    @FindBy(xpath = "//input[@id='packsize1']")
    private WebElement level2PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption1']")
    private WebElement level2PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength1']")
    private WebElement level2PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired1']")
    private WebElement level2RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold1']")
    private WebElement level2ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn1']")
    private WebElement level2PRNinformation;

    // -------- Product - Level-3 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level2']")
    private WebElement level3Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue2']")
    private WebElement level3GTIN;

    @FindBy(xpath = "//input[@id='packsize2']")
    private WebElement level3PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption2']")
    private WebElement level3PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength2']")
    private WebElement level3PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired2']")
    private WebElement level3RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold2']")
    private WebElement level3ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn2']")
    private WebElement level3PRNinformation;

    // -------- Product - Level-4 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level3']")
    private WebElement level4Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue3']")
    private WebElement level4GTIN;

    @FindBy(xpath = "//input[@id='packsize3']")
    private WebElement level4PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption3']")
    private WebElement level4PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength3']")
    private WebElement level4PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired3']")
    private WebElement level4RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold3']")
    private WebElement level4ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn3']")
    private WebElement level4PRNinformation;

    // -------- Product - Level-5 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level4']")
    private WebElement level5Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue4']")
    private WebElement level5GTIN;

    @FindBy(xpath = "//input[@id='packsize4']")
    private WebElement level5PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption4']")
    private WebElement level5PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength4']")
    private WebElement level5PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired4']")
    private WebElement level5RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold4']")
    private WebElement level5ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn4']")
    private WebElement level5PRNinformation;

    // -------- Product - Level-6 Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='level5']")
    private WebElement level6Dropdown;

    @FindBy(xpath = "//input[@id='tradevalue5']")
    private WebElement level6GTIN;

    @FindBy(xpath = "//input[@id='packsize5']")
    private WebElement level6PackSize;

    @FindBy(xpath = "//mat-select[@id='packagingLevelCaption5']")
    private WebElement level6PackagingLevelDropdown;

    @FindBy(xpath = "//mat-select[@id='tradevalueprefixlength5']")
    private WebElement level6PrefixLengthDropdown;

    @FindBy(xpath = "//input[@id='snRequired5']")
    private WebElement level6RequiredSN;

    @FindBy(xpath = "//input[@id='snThreshold5']")
    private WebElement level6ThresholdSN;

    @FindBy(xpath = "//button[@id='layoutBtn5']")
    private WebElement level6PRNinformation;

    // -------- Product - PRN Information Locators
    // -------------------------------------------------------------------------------

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='prnFileInput' and @type='file']")
    // private WebElement prnFileInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "(//button[.//i[contains(@class,'fa-file-text')]]/ancestor::div//input[@type='file'])[1]")
    private WebElement prnFileInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='imageFileInput' and @type='file']")
    // private WebElement imageFileInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "(//button[.//i[contains(@class,'fa-file-text')]]/ancestor::div//input[@type='file'])[2]")
    private WebElement imageFileInput;

    @FindBy(xpath = "//button[@id='addthreshold']")
    private WebElement addPRNinfoButton;

    // -------- Product - Partial PRN Information Locators
    // -------------------------------------------------------------------------------

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//mat-select[@id='Partial SSCCundefined']")
    // private WebElement partialDropdown;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//mat-select[@id='Partial SSCC']")
    private WebElement partialDropdown;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//mat-select[@id='SSCC Poolundefined']")
    // private WebElement partialSSCCPoolDropDown;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//mat-select[@id='SSCC Pool']")
    private WebElement partialSSCCPoolDropDown;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='partialPrnFileInput' and @type='file']")
    // private WebElement partialPRNFileInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "(//button[.//i[contains(@class,'fa-file-text')]]/ancestor::div//input[@type='file'])[3]")
    private WebElement partialPRNFileInput;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "//input[@id='partialImageFileInput' and @type='file']")
    // private WebElement partialImageFileInput;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "(//button[.//i[contains(@class,'fa-file-text')]]/ancestor::div//input[@type='file'])[4]")
    private WebElement partialImageFileInput;

    // -------- Product - Other Information Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//mat-select[@id='codeType']")
    private WebElement productFormTypeDropdown;

    @FindBy(xpath = "//mat-select[@id='barcodeDateFormat']")
    private WebElement barcodeDateDropdown;

    @FindBy(xpath = "//mat-select[@id='ocrDateFormat']")
    private WebElement ocrExpDateDropdown;

    @FindBy(xpath = "//mat-select[@id='ocrDateFormat1']")
    private WebElement ocrMfgDateDropdown;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement saveButton;

    // ----- Old Reetrak UI-------
    // @FindBy(xpath = "(//button[@id='close1'])[2]")
    // private WebElement closeButton;

    // ----- New Reetrak UI-------
    @FindBy(xpath = "//button[@id='close1']")
    private WebElement closeButton;

    // -------- Hierarchy - Locators
    // -------------------------------------------------------------------------------

    @FindBy(xpath = "//h4[normalize-space()='Hierarchy Selection']")
    private WebElement hierarchyTitle;

    @FindBy(xpath = "//mat-select[@id='selectHierarachy']")
    private WebElement hierarchyDropdown;

    @FindBy(xpath = "//button[@id='OK']")
    private WebElement hierarchyOKButton;

    private void handleHierarchySelection(String hierarchyName) {
        boolean isHierarchyEnabled = Boolean.parseBoolean(ConfigReader.getRequired("hierarchy.master.enabled"));
        if (isHierarchyEnabled) {
            if (hierarchyName != null && !hierarchyName.isBlank()) {
                waitUtils.waitForVisibility(hierarchyTitle);
                waitUtils.customewaitForVisibility(hierarchyDropdown, 60);
                try {
                    selectFromDropDown(hierarchyDropdown, hierarchyName);
                } catch (Exception e) {
                    throw new RuntimeException(">>> Hierarchy name is Invalid in excel, pls. check.....");
                }
                waitUtils.waitForVisibility(hierarchyOKButton);
                click(hierarchyOKButton);
            } else {
                throw new RuntimeException(">>> Hierarchy name is blank in Excel Sheet, Pls check");
            }
        }
    }

    private void handleInboxApproval(String productName, String productCode, Map<String, String> excelData)
            throws Exception {
        boolean isHierarchyEnabled = Boolean.parseBoolean(ConfigReader.getRequired("hierarchy.master.enabled"));
        if (isHierarchyEnabled && excelData != null) {
            String approveVal = excelData.get("approve");
            if (approveVal == null)
                approveVal = excelData.get("Approve");
            if (approveVal == null)
                approveVal = excelData.get("APPROVE");
            if ("Yes".equalsIgnoreCase(approveVal)) {
                approveProductInInbox(productName, productCode);
            }
        }
    }

    // -----------------------------------------------------------------------------------------------

    public ProductMasterPage(WebDriver driver) {
        super(driver);
    }

    public void openProductMaster() {
        boolean isExpanded = false;
        try {
            isExpanded = productMasterMenu.isDisplayed();
        } catch (Exception e) {
            isExpanded = false;
        }
        if (!isExpanded) {
            waitUtils.waitForVisibility(productionMasterMenu);
            scrollToElement(productionMasterMenu);
            click(productionMasterMenu);
        }
        scrollToElement(productMasterMenu);
        click(productMasterMenu);
        waitUtils.waitForVisibility(productMasterPageName);
    }

    public void CloseProductMaster() {
        boolean isExpanded = false;
        try {
            isExpanded = productMasterMenu.isDisplayed();
        } catch (Exception e) {
            isExpanded = false;
        }
        if (isExpanded) {
            waitUtils.waitForVisibility(productionMasterMenu);
            scrollToElement(productionMasterMenu);
            click(productionMasterMenu);
            waitUtils.waitForInvisibility(productMasterMenu);
        }
    }

    public String getProductMasterPageName() {
        return getText(productMasterPageName);
    }

    public void clickAddProduct() {
        waitUtils.waitForVisibility(addProductButton);
        click(addProductButton);
        waitUtils.waitForVisibility(productName);
    }

    public String getAddProductInfoTitle() {
        waitUtils.waitForVisibility(addProductSectionTitle);
        return getText(addProductSectionTitle);
    }

    public String getEditProductInfoTitle() {
        waitUtils.waitForVisibility(EditProductSectionTitle);
        return getText(EditProductSectionTitle);
    }

    public boolean isSaveButtonEnabled() {
        waitUtils.waitForVisibility(saveButton);
        return saveButton.isEnabled();
    }

    public void clickCollapseAll() {
        waitUtils.waitForVisibility(collapseAllButton);
        scrollToElement(collapseAllButton);
        click(collapseAllButton);
    }

    public void clickClose() {
        waitUtils.waitForVisibility(closeButton);
        click(closeButton);
        waitUtils.waitForVisibility(productMasterPageName);
    }

    // ------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------

    public void AddProduct(
            String name,
            String code,
            String cCode,
            String strength,
            String dosageForm,
            String serialManager,
            String description,
            String compliance,
            String country,
            String unitPrice,
            String packSize,
            String packagingType,
            String tradeType,
            String noOfLevels,
            String productFormType,
            String barcodeDate,
            String ocrExpDate,
            String ocrMfgDate,
            String hierarchyName,
            java.util.Map<String, String> excelData) throws Exception {

        // Step 1: Open Add Product Form
        click(addProductButton);

        // Step 2: Fill Section 1 - Add New Product (Common & Mandatory)
        if (name != null && !name.isBlank())
            type(productName, name);
        if (code != null && !code.isBlank())
            type(productCode, code);
        if (cCode != null && !cCode.isBlank())
            type(productCustomerCode, cCode);
        if (strength != null && !strength.isBlank())
            type(productStrength, strength);
        if (dosageForm != null && !dosageForm.isBlank())
            type(productDosageForm, dosageForm);
        if (packagingType != null && !packagingType.equals("Non Serialization") && serialManager != null
                && !serialManager.isBlank()) {
            selectFromDropDown(serialManagerDropdown, serialManager);
        }
        if (description != null && !description.isBlank())
            type(productDescription, description);
        if (compliance != null && !compliance.isBlank())
            selectFromDropDown(complianceDropdown, compliance);
        if (country != null && !country.isBlank())
            type(productCountry, country);
        if (unitPrice != null && !unitPrice.isBlank())
            type(productUnitPrice, unitPrice);
        if (packSize != null && !packSize.isBlank())
            type(productPackSize, packSize);
        if (packagingType != null && !packagingType.isBlank())
            selectFromDropDown(packagingTypeDropdown, packagingType);
        if (tradeType != null && !tradeType.isBlank())
            selectFromDropDown(tradeTypeDropdown, tradeType);
        click(next1Button);
        waitUtils.waitForVisibility(noOfLevelsDropdown);
        char[] level = noOfLevels.toCharArray();
        // System.out.println("Level >> " + level[0]);
        // System.out.println("Level >> " + level[1]);
        // System.out.println("Level >> " + level[2]);
        System.out.println("No Of Levels >> " + noOfLevels);
        int levelcount = level.length;
        System.out.println("Level Count >> " + levelcount);

        // Step 3: Fill Section 2 - Level Information (Dynamic based on noOfLevels -//
        // Approach 2)
        if (noOfLevels != null && !noOfLevels.isBlank()) {
            selectFromDropDown(noOfLevelsDropdown, Integer.toString(levelcount));

            int ssccPoolIndex = 1;

            for (int i = 0; i < levelcount; i++) {
                int j = Character.getNumericValue(level[i]);
                System.out.println(">>>> UI Row " + (i + 1) + " filling Excel Level " + j + " data");
                System.out.println(">>>> Value of i " + i);
                System.out.println(">>>> Value of j " + j);

                boolean isSsccLevel = (j >= 3);
                int currentSsccIndex = ssccPoolIndex;
                if (isSsccLevel) {
                    ssccPoolIndex++;
                }

                boolean isNonSerialization = "Non Serialization".equalsIgnoreCase(packagingType);
                boolean isPartialApplicable = (i > 0) && !isNonSerialization;

                String partialVal = excelData.get("level" + j + "Partial");
                if (partialVal == null)
                    partialVal = excelData.get("level" + j + "partial");
                String partialPoolVal = excelData.get("level" + j + "partialpool");
                if (partialPoolVal == null)
                    partialPoolVal = excelData.get("level" + j + "Partialpool");
                String partialPrnVal = excelData.get("level" + j + "partialprn");
                if (partialPrnVal == null)
                    partialPrnVal = excelData.get("level" + j + "Partialprn");
                String partialImageVal = excelData.get("level" + j + "partialimage");
                if (partialImageVal == null)
                    partialImageVal = excelData.get("level" + j + "Partialimage");

                switch (i) {
                    case 0:
                        fillLevelIfPresent(level1Dropdown, level1GTIN, level1PackSize,
                                level1PackagingLevelDropdown, level1PrefixLengthDropdown,
                                level1RequiredSN, level1ThresholdSN,
                                level1PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 1:
                        fillLevelIfPresent(level2Dropdown, level2GTIN, level2PackSize,
                                level2PackagingLevelDropdown, level2PrefixLengthDropdown,
                                level2RequiredSN, level2ThresholdSN,
                                level2PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 2:
                        fillLevelIfPresent(level3Dropdown, level3GTIN, level3PackSize,
                                level3PackagingLevelDropdown, level3PrefixLengthDropdown,
                                level3RequiredSN, level3ThresholdSN,
                                level3PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 3:
                        fillLevelIfPresent(level4Dropdown, level4GTIN, level4PackSize,
                                level4PackagingLevelDropdown, level4PrefixLengthDropdown,
                                level4RequiredSN, level4ThresholdSN,
                                level4PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 4:
                        fillLevelIfPresent(level5Dropdown, level5GTIN, level5PackSize,
                                level5PackagingLevelDropdown, level5PrefixLengthDropdown,
                                level5RequiredSN, level5ThresholdSN,
                                level5PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 5:
                        fillLevelIfPresent(level6Dropdown, level6GTIN, level6PackSize,
                                level6PackagingLevelDropdown, level6PrefixLengthDropdown,
                                level6RequiredSN, level6ThresholdSN,
                                level6PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"),
                                excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"),
                                excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"),
                                excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"),
                                excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"),
                                excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable,
                                partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    default:
                        System.out.println("Unsupported UI row index: " + i);
                        break;
                }
            }
        }

        // Step 4: Fill Section 3 - Other Information (Common & Mandatory)
        scrollToElement(next2Button);
        click(next2Button);
        waitUtils.waitForVisibility(productFormTypeDropdown);
        if (productFormType != null && !productFormType.isBlank()) {
            selectFromDropDown(productFormTypeDropdown, productFormType);
        }
        if (barcodeDate != null && !barcodeDate.isBlank()) {
            selectFromDropDown(barcodeDateDropdown, barcodeDate);
        }
        if (ocrExpDate != null && !ocrExpDate.isBlank()) {
            selectFromDropDown(ocrExpDateDropdown, ocrExpDate);
        }
        if (ocrMfgDate != null && !ocrMfgDate.isBlank()) {
            selectFromDropDown(ocrMfgDateDropdown, ocrMfgDate);
        }

        // Step 5: Save Product
        scrollToElement(saveButton);
        click(saveButton);

        handleHierarchySelection(hierarchyName);

        // Step 7: Handle Remarks
        RemarkandOK(driver, "Test : Product " + name + " added successfully");

        // Step 8: Conditional Inbox Approval based on Excel 'Approve' column
        handleInboxApproval(name, code, excelData);
    }

    private void fillLevelIfPresent(
            WebElement levelDropdown,
            WebElement gtinInput,
            WebElement packSizeInput,
            WebElement packagingLevelDropdown,
            WebElement prefixLengthDropdown,
            WebElement requiredSNInput,
            WebElement thresholdSNInput,
            WebElement prnInfoIcon,
            boolean isSsccLevel,
            int ssccPoolIndex,
            String levelVal,
            String tradeVal,
            String packSizeVal,
            String packagingLevelVal,
            String prefixLengthVal,
            String requiredSNVal,
            String thresholdSNVal,
            String prnFileLocation,
            String imageFilePath,
            String ssccPoolVal,
            boolean isPartialApplicable,
            String partialVal,
            String partialPoolVal,
            String partialPrnVal,
            String partialImageVal,
            boolean isNonSerialization) throws Exception {

        scrollToElement(levelDropdown);
        if (levelVal != null && !levelVal.isBlank()) {
            selectFromDropDown(levelDropdown, levelVal);
        }

        boolean isSsccPoolSelected = false;
        if (!isNonSerialization) {
            if (isSsccLevel) {
                if (ssccPoolVal != null && !ssccPoolVal.isBlank()) {
                    try {
                        WebElement ssccPoolDropdown = driver.findElement(
                                org.openqa.selenium.By
                                        .xpath("(//mat-select[contains(@id,'SSCC') or contains(@id,'sscc') or @formcontrolname='ssccPoolId'])["
                                                + ssccPoolIndex + "]"));
                        System.out.println("ssccPoolDropdown: " + ssccPoolDropdown);
                        waitUtils.waitForVisibility(ssccPoolDropdown);
                        selectFromDropDown(ssccPoolDropdown, ssccPoolVal);
                        isSsccPoolSelected = true;
                        System.out
                                .println("Successfully selected SSCC Pool: " + ssccPoolVal + " using index: "
                                        + ssccPoolIndex);
                    } catch (Exception e) {
                        System.out.println(
                                "Could not select SSCC Pool at index " + ssccPoolIndex + ": " + e.getMessage());
                        throw new RuntimeException(
                                "Failed to select SSCC Pool '" + ssccPoolVal + "' at index " + ssccPoolIndex + ": "
                                        + e.getMessage(),
                                e);
                    }
                } else {
                    try {
                        org.openqa.selenium.By resetBtnLocator = org.openqa.selenium.By
                                .xpath("//button[@id='resetSSCCPoolBtn" + ssccPoolIndex + "']");
                        java.util.List<WebElement> resetBtns = driver.findElements(resetBtnLocator);
                        if (!resetBtns.isEmpty() && resetBtns.get(0).isDisplayed()) {
                            click(resetBtns.get(0));
                            System.out.println("Clicked RESET SSCC Pool button at index: " + ssccPoolIndex);
                            Thread.sleep(500);
                        }
                    } catch (Exception e) {
                        System.out.println("No active SSCC Pool RESET button found/needed at index " + ssccPoolIndex
                                + ": " + e.getMessage());
                    }
                }
            }
        }

        if (tradeVal != null && !tradeVal.isBlank()) {
            type(gtinInput, tradeVal);
        }
        if (packSizeVal != null && !packSizeVal.isBlank()) {
            type(packSizeInput, packSizeVal);
        }

        if (!isNonSerialization) {
            if (packagingLevelVal != null && !packagingLevelVal.isBlank()) {
                selectFromDropDown(packagingLevelDropdown, packagingLevelVal);
            }

            boolean hasPrefixOrSnData = (prefixLengthVal != null && !prefixLengthVal.isBlank())
                    || (requiredSNVal != null && !requiredSNVal.isBlank())
                    || (thresholdSNVal != null && !thresholdSNVal.isBlank());

            if (hasPrefixOrSnData && !isSsccPoolSelected) {
                boolean isDisabled = false;
                try {
                    String disabledAttr = prefixLengthDropdown.getAttribute("aria-disabled");
                    isDisabled = "true".equalsIgnoreCase(disabledAttr) || !prefixLengthDropdown.isEnabled();
                } catch (Exception e) {
                    isDisabled = false;
                }

                if (isDisabled || isSsccLevel) {
                    try {
                        org.openqa.selenium.By resetBtnLocator = org.openqa.selenium.By.xpath(
                                "//button[@id='resetSSCCPoolBtn" + ssccPoolIndex
                                        + "'] | //button[contains(@id,'resetSSCCPool')]");
                        java.util.List<WebElement> resetBtns = driver.findElements(resetBtnLocator);
                        for (WebElement resetBtn : resetBtns) {
                            if (resetBtn.isDisplayed() && resetBtn.isEnabled()) {
                                click(resetBtn);
                                System.out.println(
                                        "Reset SSCC pool to enable Prefix Length dropdown at index: " + ssccPoolIndex);
                                Thread.sleep(500);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("No active SSCC Pool RESET button needed/found at index " + ssccPoolIndex
                                + ": " + e.getMessage());
                    }
                }

                if (prefixLengthVal != null && !prefixLengthVal.isBlank()) {
                    selectFromDropDown(prefixLengthDropdown, prefixLengthVal);
                }
                if (requiredSNVal != null && !requiredSNVal.isBlank()) {
                    type(requiredSNInput, requiredSNVal);
                }
                if (thresholdSNVal != null && !thresholdSNVal.isBlank()) {
                    type(thresholdSNInput, thresholdSNVal);
                }
            }
        }

        boolean hasPrnData = (prnFileLocation != null && !prnFileLocation.isBlank())
                || (imageFilePath != null && !imageFilePath.isBlank())
                || (partialPrnVal != null && !partialPrnVal.isBlank())
                || (partialImageVal != null && !partialImageVal.isBlank())
                || (partialVal != null && !partialVal.isBlank());

        if (prnInfoIcon != null && hasPrnData) {
            scrollToElement(prnInfoIcon);
            click(prnInfoIcon); // Click PRN Info Icon

            if (prnFileLocation != null && !prnFileLocation.isBlank()) {
                Thread.sleep(500);
                prnFileInput.sendKeys(prnFileLocation);
            }
            if (imageFilePath != null && !imageFilePath.isBlank()) {
                Thread.sleep(500);
                imageFileInput.sendKeys(imageFilePath);
            }

            if (isPartialApplicable && partialVal != null && !partialVal.isBlank()) {
                String targetPartial = partialVal.trim();
                String currentPartial = "";
                try {
                    currentPartial = partialDropdown.getText().trim();
                } catch (Exception e) {
                    currentPartial = "";
                }

                boolean isSameValue = currentPartial.equalsIgnoreCase(targetPartial);

                if (!isSameValue) {
                    if (targetPartial.equalsIgnoreCase("Yes")) {
                        selectFromDropDown(partialDropdown, "Yes");
                        Thread.sleep(500);
                        if (partialPoolVal != null && !partialPoolVal.isBlank()) {
                            selectFromDropDown(partialSSCCPoolDropDown, partialPoolVal);
                        }
                        if (partialPrnVal != null && !partialPrnVal.isBlank()) {
                            Thread.sleep(500);
                            partialPRNFileInput.sendKeys(partialPrnVal);
                        }
                        if (partialImageVal != null && !partialImageVal.isBlank()) {
                            Thread.sleep(500);
                            partialImageFileInput.sendKeys(partialImageVal);
                        }
                    } else if (targetPartial.equalsIgnoreCase("No")) {
                        selectFromDropDown(partialDropdown, "No");
                        Thread.sleep(500);
                    }
                } else if (targetPartial.equalsIgnoreCase("Yes")) {
                    if (partialPoolVal != null && !partialPoolVal.isBlank()) {
                        selectFromDropDown(partialSSCCPoolDropDown, partialPoolVal);
                    }
                    if (partialPrnVal != null && !partialPrnVal.isBlank()) {
                        Thread.sleep(500);
                        partialPRNFileInput.sendKeys(partialPrnVal);
                    }
                    if (partialImageVal != null && !partialImageVal.isBlank()) {
                        Thread.sleep(500);
                        partialImageFileInput.sendKeys(partialImageVal);
                    }
                }
            }

            try {
                waitUtils.waitForVisibility(addPRNinfoButton);
                scrollToElement(addPRNinfoButton);
                try {
                    click(addPRNinfoButton);
                } catch (Exception e) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",
                            addPRNinfoButton);
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Could not click UPDATE on Layout Information dialog: " + e.getMessage());
                throw new RuntimeException("Failed to click UPDATE on Layout Information dialog: " + e.getMessage(), e);
            }
        }
    }

    // ----------------------------------------------------------------------

    public void selectExistingProduct(String existingName, String existingCode) throws Exception {
        type(serachInput, existingName);
        Thread.sleep(1000);
        org.openqa.selenium.By productLocator = org.openqa.selenium.By.xpath(
                "//div[@role='row' and .//div[@role='gridcell' and @col-id='name' and normalize-space()='"
                        + existingName
                        + "'] and .//div[@role='gridcell' and @col-id='code' and normalize-space()='" + existingCode
                        + "']]");
        waitUtils.waitForVisibility(driver.findElement(productLocator));
        click(driver.findElement(productLocator));
        Thread.sleep(1000);
        click(productMasterPageName);
    }

    public void UpdateProduct(
            String existingName,
            String existingCode,
            String name,
            String code,
            String cCode,
            String serialManager,
            String description,
            String compliance,
            String country,
            String unitPrice,
            String packSize,
            String packagingType,
            String tradeType,
            String noOfLevels,
            String productFormType,
            String barcodeDate,
            String ocrExpDate,
            String ocrMfgDate,
            String hierarchyName,
            java.util.Map<String, String> excelData) throws Exception {

        // Step 1: Select existing product row and open Edit Form
        selectExistingProduct(existingName, existingCode);
        waitUtils.waitForVisibility(editButton);
        click(editButton);
        waitUtils.waitForVisibility(EditProductSectionTitle);

        // Step 2: Update Section 1 - Product Details
        if (name != null && !name.isBlank())
            type(productName, name);
        if (code != null && !code.isBlank())
            type(productCode, code);
        if (cCode != null && !cCode.isBlank())
            type(productCustomerCode, cCode);
        if (packagingType != null && !packagingType.equals("Non Serialization") && serialManager != null
                && !serialManager.isBlank()) {
            selectFromDropDown(serialManagerDropdown, serialManager);
        }
        if (description != null && !description.isBlank())
            type(productDescription, description);
        if (compliance != null && !compliance.isBlank())
            selectFromDropDown(complianceDropdown, compliance);
        if (country != null && !country.isBlank())
            type(productCountry, country);
        if (unitPrice != null && !unitPrice.isBlank())
            type(productUnitPrice, unitPrice);
        if (packSize != null && !packSize.isBlank())
            type(productPackSize, packSize);
        if (packagingType != null && !packagingType.isBlank())
            selectFromDropDown(packagingTypeDropdown, packagingType);
        if (tradeType != null && !tradeType.isBlank())
            selectFromDropDown(tradeTypeDropdown, tradeType);

        click(next1Button);
        waitUtils.waitForVisibility(noOfLevelsDropdown);

        // Step 3: Update Section 2 - Level Information (Standard update - same level
        // count)
        if (noOfLevels != null && !noOfLevels.isBlank()) {
            char[] level = noOfLevels.toCharArray();
            int levelcount = level.length;

            // Reset level rows to "Fifth" (Original UpdateProduct logic)
            for (int i = 0; i < levelcount; i++) {
                WebElement currentLevelDropdown = null;
                switch (i) {
                    case 0:
                        currentLevelDropdown = level1Dropdown;
                        break;
                    case 1:
                        currentLevelDropdown = level2Dropdown;
                        break;
                    case 2:
                        currentLevelDropdown = level3Dropdown;
                        break;
                    case 3:
                        currentLevelDropdown = level4Dropdown;
                        break;
                    case 4:
                        currentLevelDropdown = level5Dropdown;
                        break;
                    case 5:
                        currentLevelDropdown = level6Dropdown;
                        break;
                }
                if (currentLevelDropdown != null) {
                    try {
                        scrollToElement(currentLevelDropdown);
                        Thread.sleep(500);
                        selectFromDropDown(currentLevelDropdown, "Fifth");
                    } catch (Exception e) {
                        System.out.println("Reset level row " + i + " to Fifth failed: " + e.getMessage());
                        throw new RuntimeException("Failed to reset level row " + i + " to Fifth: " + e.getMessage(),
                                e);
                    }
                }
            }

            int ssccPoolIndex = 1;

            for (int i = 0; i < levelcount; i++) {
                int j = Character.getNumericValue(level[i]);
                boolean isSsccLevel = (j >= 3);
                int currentSsccIndex = ssccPoolIndex;
                if (isSsccLevel)
                    ssccPoolIndex++;

                boolean isNonSerialization = "Non Serialization".equalsIgnoreCase(packagingType);
                boolean isPartialApplicable = (i > 0) && !isNonSerialization;

                String partialVal = excelData.get("level" + j + "Partial");
                if (partialVal == null)
                    partialVal = excelData.get("level" + j + "partial");
                String partialPoolVal = excelData.get("level" + j + "partialpool");
                if (partialPoolVal == null)
                    partialPoolVal = excelData.get("level" + j + "Partialpool");
                String partialPrnVal = excelData.get("level" + j + "partialprn");
                if (partialPrnVal == null)
                    partialPrnVal = excelData.get("level" + j + "partialprn");
                String partialImageVal = excelData.get("level" + j + "partialimage");
                if (partialImageVal == null)
                    partialImageVal = excelData.get("level" + j + "Partialimage");

                switch (i) {
                    case 0:
                        fillLevelIfPresent(level1Dropdown, level1GTIN, level1PackSize,
                                level1PackagingLevelDropdown, level1PrefixLengthDropdown,
                                level1RequiredSN, level1ThresholdSN, level1PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 1:
                        fillLevelIfPresent(level2Dropdown, level2GTIN, level2PackSize,
                                level2PackagingLevelDropdown, level2PrefixLengthDropdown,
                                level2RequiredSN, level2ThresholdSN, level2PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 2:
                        fillLevelIfPresent(level3Dropdown, level3GTIN, level3PackSize,
                                level3PackagingLevelDropdown, level3PrefixLengthDropdown,
                                level3RequiredSN, level3ThresholdSN, level3PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 3:
                        fillLevelIfPresent(level4Dropdown, level4GTIN, level4PackSize,
                                level4PackagingLevelDropdown, level4PrefixLengthDropdown,
                                level4RequiredSN, level4ThresholdSN, level4PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 4:
                        fillLevelIfPresent(level5Dropdown, level5GTIN, level5PackSize,
                                level5PackagingLevelDropdown, level5PrefixLengthDropdown,
                                level5RequiredSN, level5ThresholdSN, level5PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 5:
                        fillLevelIfPresent(level6Dropdown, level6GTIN, level6PackSize,
                                level6PackagingLevelDropdown, level6PrefixLengthDropdown,
                                level6RequiredSN, level6ThresholdSN, level6PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                }
            }
        }

        // Step 4: Update Section 3 - Other Information
        scrollToElement(next2Button);
        click(next2Button);
        waitUtils.waitForVisibility(productFormTypeDropdown);
        if (productFormType != null && !productFormType.isBlank())
            selectFromDropDown(productFormTypeDropdown, productFormType);
        if (barcodeDate != null && !barcodeDate.isBlank())
            selectFromDropDown(barcodeDateDropdown, barcodeDate);
        if (ocrExpDate != null && !ocrExpDate.isBlank())
            selectFromDropDown(ocrExpDateDropdown, ocrExpDate);
        if (ocrMfgDate != null && !ocrMfgDate.isBlank())
            selectFromDropDown(ocrMfgDateDropdown, ocrMfgDate);

        // Step 5: Save Updated Product
        scrollToElement(saveButton);
        click(saveButton);

        handleHierarchySelection(hierarchyName);

        // Step 6: Handle Remarks Confirmation
        RemarkandOK(driver, "Test : Product " + name + " updated successfully");

        // Step 7: Conditional Inbox Approval based on Excel 'Approve' column
        handleInboxApproval(name, code, excelData);
    }

    public void updateProductLevel(
            String existingName,
            String existingCode,
            String name,
            String code,
            String cCode,
            String serialManager,
            String description,
            String compliance,
            String country,
            String unitPrice,
            String packSize,
            String packagingType,
            String tradeType,
            String noOfLevels,
            String productFormType,
            String barcodeDate,
            String ocrExpDate,
            String ocrMfgDate,
            String hierarchyName,
            java.util.Map<String, String> excelData) throws Exception {

        // Step 1: Select existing product row and open Edit Form
        selectExistingProduct(existingName, existingCode);
        waitUtils.waitForVisibility(editButton);
        click(editButton);
        waitUtils.waitForVisibility(EditProductSectionTitle);

        // Step 2: Update Section 1 - Product Details
        if (name != null && !name.isBlank())
            type(productName, name);
        if (code != null && !code.isBlank())
            type(productCode, code);
        if (cCode != null && !cCode.isBlank())
            type(productCustomerCode, cCode);
        if (packagingType != null && !packagingType.equals("Non Serialization") && serialManager != null
                && !serialManager.isBlank()) {
            selectFromDropDown(serialManagerDropdown, serialManager);
        }
        if (description != null && !description.isBlank())
            type(productDescription, description);
        if (compliance != null && !compliance.isBlank())
            selectFromDropDown(complianceDropdown, compliance);
        if (country != null && !country.isBlank())
            type(productCountry, country);
        if (unitPrice != null && !unitPrice.isBlank())
            type(productUnitPrice, unitPrice);
        if (packSize != null && !packSize.isBlank())
            type(productPackSize, packSize);
        if (packagingType != null && !packagingType.isBlank())
            selectFromDropDown(packagingTypeDropdown, packagingType);
        if (tradeType != null && !tradeType.isBlank())
            selectFromDropDown(tradeTypeDropdown, tradeType);

        click(next1Button);
        waitUtils.waitForVisibility(noOfLevelsDropdown);

        // Step 3: Update Section 2 - Level Information (Level Addition/Removal +1 or
        // -1)
        if (noOfLevels != null && !noOfLevels.isBlank()) {
            char[] level = noOfLevels.toCharArray();
            int requestedLevelCount = level.length;

            int existingLevelCount = 0;
            try {
                String initialLevelText = noOfLevelsDropdown.getText();
                if (initialLevelText != null && !initialLevelText.isBlank()) {
                    existingLevelCount = Integer.parseInt(initialLevelText.replaceAll("[^0-9]", ""));
                }
            } catch (Exception e) {
                System.out.println("Could not read existing level count from dropdown: " + e.getMessage());
                throw new RuntimeException("Failed to read existing level count from dropdown: " + e.getMessage(), e);
            }

            if (existingLevelCount > 0 && requestedLevelCount != existingLevelCount) {
                int levelDelta = Math.abs(requestedLevelCount - existingLevelCount);
                if (levelDelta > 1) {
                    String validationMsg = ">>> Validation Error: Cannot increase or decrease levels by more than 1 level at a time. (Existing levels: "
                            + existingLevelCount + ", Requested levels: " + requestedLevelCount + ")";
                    System.err.println(validationMsg);
                    throw new IllegalArgumentException(validationMsg);
                }
            }

            selectFromDropDown(noOfLevelsDropdown, Integer.toString(requestedLevelCount));

            // Handle "Change Level" Modal Dialog (only opens when adding or removing
            // levels)
            try {
                waitUtils.customewaitForVisibility(checkbox_for_changing_levels, 5);
                if (checkbox_for_changing_levels.isDisplayed()) {
                    String levelToSelect = "";

                    // First check excelData if explicit existing level combination is provided
                    String existingNoOfLevels = excelData.get("existingnooflevels");
                    if (existingNoOfLevels == null)
                        existingNoOfLevels = excelData.get("existingNoOfLevels");
                    if (existingNoOfLevels == null)
                        existingNoOfLevels = excelData.get("Existing No Of Levels");
                    if (existingNoOfLevels == null)
                        existingNoOfLevels = excelData.get("Existing No. Of Level");

                    if (existingNoOfLevels != null && !existingNoOfLevels.isBlank()) {
                        if (requestedLevelCount < existingLevelCount) {
                            for (char c : existingNoOfLevels.toCharArray()) {
                                if (noOfLevels.indexOf(c) == -1) {
                                    levelToSelect = String.valueOf(c);
                                    break;
                                }
                            }
                        } else if (requestedLevelCount > existingLevelCount) {
                            for (char c : noOfLevels.toCharArray()) {
                                if (existingNoOfLevels.indexOf(c) == -1) {
                                    levelToSelect = String.valueOf(c);
                                    break;
                                }
                            }
                        }
                    }

                    // Dynamic fallback: Open dropdown and inspect visible mat-options
                    if (levelToSelect.isBlank()) {
                        click(checkbox_for_changing_levels);
                        Thread.sleep(500);
                        java.util.List<WebElement> options = driver
                                .findElements(org.openqa.selenium.By.xpath("//mat-option"));
                        for (WebElement option : options) {
                            String optText = option.getText().trim();
                            String digitOnly = optText.replaceAll("[^0-9]", "");
                            if (!digitOnly.isEmpty()) {
                                if (requestedLevelCount < existingLevelCount) {
                                    // Removal: pick option whose digit is NOT in target noOfLevels
                                    if (noOfLevels.indexOf(digitOnly) == -1) {
                                        levelToSelect = optText;
                                        break;
                                    }
                                } else {
                                    // Addition: pick option whose digit IS in target noOfLevels
                                    if (noOfLevels.indexOf(digitOnly) != -1) {
                                        levelToSelect = optText;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (!levelToSelect.isBlank()) {
                        selectFromDropDown(checkbox_for_changing_levels, levelToSelect);
                    } else {
                        selectFromDropDown(checkbox_for_changing_levels, Integer.toString(requestedLevelCount));
                    }

                    waitUtils.waitForVisibility(saveButton_on_changing_levels);
                    click(saveButton_on_changing_levels);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Error handling Change Level dialog: " + e.getMessage());
                throw new RuntimeException("Failed to handle Change Level dialog: " + e.getMessage(), e);
            }

            int ssccPoolIndex = 1;

            for (int i = 0; i < requestedLevelCount; i++) {
                int j = Character.getNumericValue(level[i]);
                boolean isSsccLevel = (j >= 3);
                int currentSsccIndex = ssccPoolIndex;
                if (isSsccLevel)
                    ssccPoolIndex++;

                boolean isNonSerialization = "Non Serialization".equalsIgnoreCase(packagingType);
                boolean isPartialApplicable = (i > 0) && !isNonSerialization;

                String partialVal = excelData.get("level" + j + "Partial");
                if (partialVal == null)
                    partialVal = excelData.get("level" + j + "partial");
                String partialPoolVal = excelData.get("level" + j + "partialpool");
                if (partialPoolVal == null)
                    partialPoolVal = excelData.get("level" + j + "Partialpool");
                String partialPrnVal = excelData.get("level" + j + "partialprn");
                if (partialPrnVal == null)
                    partialPrnVal = excelData.get("level" + j + "partialprn");
                String partialImageVal = excelData.get("level" + j + "partialimage");
                if (partialImageVal == null)
                    partialImageVal = excelData.get("level" + j + "Partialimage");

                switch (i) {
                    case 0:
                        fillLevelIfPresent(level1Dropdown, level1GTIN, level1PackSize,
                                level1PackagingLevelDropdown, level1PrefixLengthDropdown,
                                level1RequiredSN, level1ThresholdSN, level1PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 1:
                        fillLevelIfPresent(level2Dropdown, level2GTIN, level2PackSize,
                                level2PackagingLevelDropdown, level2PrefixLengthDropdown,
                                level2RequiredSN, level2ThresholdSN, level2PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 2:
                        fillLevelIfPresent(level3Dropdown, level3GTIN, level3PackSize,
                                level3PackagingLevelDropdown, level3PrefixLengthDropdown,
                                level3RequiredSN, level3ThresholdSN, level3PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 3:
                        fillLevelIfPresent(level4Dropdown, level4GTIN, level4PackSize,
                                level4PackagingLevelDropdown, level4PrefixLengthDropdown,
                                level4RequiredSN, level4ThresholdSN, level4PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 4:
                        fillLevelIfPresent(level5Dropdown, level5GTIN, level5PackSize,
                                level5PackagingLevelDropdown, level5PrefixLengthDropdown,
                                level5RequiredSN, level5ThresholdSN, level5PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                    case 5:
                        fillLevelIfPresent(level6Dropdown, level6GTIN, level6PackSize,
                                level6PackagingLevelDropdown, level6PrefixLengthDropdown,
                                level6RequiredSN, level6ThresholdSN, level6PRNinformation,
                                isSsccLevel, currentSsccIndex,
                                excelData.get("level" + j + "drop"), excelData.get("level" + j + "trade"),
                                excelData.get("level" + j + "packsize"), excelData.get("level" + j + "packaginglevel"),
                                excelData.get("level" + j + "prefix"), excelData.get("level" + j + "required"),
                                excelData.get("level" + j + "threshold"), excelData.get("level" + j + "prn"),
                                excelData.get("level" + j + "image"), excelData.get("level" + j + "ssccpool"),
                                isPartialApplicable, partialVal, partialPoolVal, partialPrnVal, partialImageVal,
                                isNonSerialization);
                        break;
                }
            }
        }

        // Step 4: Update Section 3 - Other Information
        scrollToElement(next2Button);
        click(next2Button);
        waitUtils.waitForVisibility(productFormTypeDropdown);
        if (productFormType != null && !productFormType.isBlank())
            selectFromDropDown(productFormTypeDropdown, productFormType);
        if (barcodeDate != null && !barcodeDate.isBlank())
            selectFromDropDown(barcodeDateDropdown, barcodeDate);
        if (ocrExpDate != null && !ocrExpDate.isBlank())
            selectFromDropDown(ocrExpDateDropdown, ocrExpDate);
        if (ocrMfgDate != null && !ocrMfgDate.isBlank())
            selectFromDropDown(ocrMfgDateDropdown, ocrMfgDate);

        // Step 5: Save Updated Product
        scrollToElement(saveButton);
        click(saveButton);

        handleHierarchySelection(hierarchyName);

        // Step 6: Handle Remarks Confirmation
        RemarkandOK(driver, "Test : Product " + name + " updated successfully");

        // Step 7: Conditional Inbox Approval based on Excel 'Approve' column
        handleInboxApproval(name, code, excelData);
    }

    public void deactivateProduct(String name, String code) throws Exception {
        selectExistingProduct(name, code);
        waitUtils.waitForVisibility(deactiveProductButton);
        click(deactiveProductButton);
        // String deactiveMessage = "Do you want to deactivate : " + name + " ?";
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test : Product " + name + " deactivated successfully");
        // return deactiveMessage;
    }

    public void activateProduct(String name, String code) throws Exception {
        selectExistingProduct(name, code);
        waitUtils.waitForVisibility(activeProductButton);
        click(activeProductButton);
        // String activateMessage = "Do you want to activate : " + name + " ?";
        click(okButton_on_active_deactiveConfirmationMessage);
        RemarkandOK(driver, "Test : Product " + name + " activated successfully");
        // return activateMessage;
    }

    public void copyProduct(
            String existingName,
            String existingCode,
            String newName,
            String newCode,
            String newCustomerCode,
            String hierarchyName) throws Exception {
        copyProduct(existingName, existingCode, newName, newCode, newCustomerCode, hierarchyName, null);
    }

    public void copyProduct(
            String existingName,
            String existingCode,
            String newName,
            String newCode,
            String newCustomerCode,
            String hierarchyName,
            Map<String, String> excelData) throws Exception {

        // Step 1: Select existing product row from grid
        selectExistingProduct(existingName, existingCode);

        // Step 2: Click Copy button
        waitUtils.waitForVisibility(copyButton);
        click(copyButton);

        // Step 3: Enter new Product Code and Customer Code from Excel
        waitUtils.waitForVisibility(productName);
        if (newName != null && !newName.isBlank()) {
            type(productName, newName);
        }
        if (newCode != null && !newCode.isBlank()) {
            type(productCode, newCode);
        }
        if (newCustomerCode != null && !newCustomerCode.isBlank()) {
            type(productCustomerCode, newCustomerCode);
        }

        // Step 4: Save Copied Product
        scrollToElement(saveButton);
        click(saveButton);

        // Step 5: Handle Hierarchy Selection if hierarchy master is enabled
        handleHierarchySelection(hierarchyName);

        // Step 6: Handle Confirmation Remarks
        RemarkandOK(driver, "Test : Product copied successfully with new code " + newCode);

        // Step 7: Conditional Inbox Approval based on Excel 'Approve' column
        handleInboxApproval(newName, newCode, excelData);
    }

    /**
     * Approves product hierarchy request in Inbox module after adding or updating a
     * product.
     * 
     * @param productName Name of the product
     * @param productCode Code of the product
     * @throws Exception
     */
    public void approveProductInInbox(String productName, String productCode) throws Exception {
        InboxPage inboxPage = new InboxPage(driver);

        // Step 1: Open INBOX module
        inboxPage.openInbox();

        // Step 2: Search request by entering Product Code in search field
        inboxPage.filterRequest(productCode);
        Thread.sleep(1000);

        // Step 3: Select request row using dynamic XPath (ProductName-ProductCode)
        String requestName = productName.trim() + "-" + productCode.trim();
        By requestRowLocator = By.xpath(
                "//div[@role='row' and .//div[@role='gridcell' and @col-id='requestName' and normalize-space()='"
                        + requestName + "']]");

        WebElement requestRow = waitUtils.waitForVisibility(driver.findElement(requestRowLocator));
        click(requestRow);
        Thread.sleep(1000);

        // Step 4: Click Action button to open Hierarchy Information dialog
        inboxPage.clickAction();
        Thread.sleep(1500);

        // Step 5: Click Approve button on Hierarchy Information dialog
        inboxPage.clickApprove();
        Thread.sleep(1000);

        // Step 6: Click OK button on confirmation dialog
        inboxPage.confirmOk();

        // Step 7: Enter remarks and click OK on remarks dialog to complete approval
        inboxPage.submitRemarks("Test : Product " + productName + " approved successfully");
    }
}
