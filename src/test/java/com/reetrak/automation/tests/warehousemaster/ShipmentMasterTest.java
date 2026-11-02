package com.reetrak.automation.tests.warehousemaster;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.warehousemaster.ShipmentMasterPage;
import com.reetrak.automation.utilities.ConfigReader;

public class ShipmentMasterTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String SHIPMENT_SHEET = "testdata.shipmentmaster.sheet";
    private static final String PO_SHEET = "testdata.po.sheet";

    @Test(priority = 1, description = "TC-1: Navigate to Warehouse Master -> Shipment Master and verify page title.")
    public void verifyShipmentMasterPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(SHIPMENT_SHEET, "ShipmentMaster_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ShipmentMasterPage shipmentPage = new ShipmentMasterPage(driver);
        shipmentPage.openShipmentMaster();

        Assert.assertEquals(shipmentPage.getShipmentMasterPageName(), value(testData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "TC-2: Open Source/Destination Address tab, click + ADD, verify dialog and fill address details.")
    public void verifyAddShipmentAddressDialogAndFields() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(SHIPMENT_SHEET, "ShipmentMaster_TC_02");
        System.out.println("DEBUG testData: " + testData);

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ShipmentMasterPage shipmentPage = new ShipmentMasterPage(driver);
        shipmentPage.openShipmentMaster();
        shipmentPage.clickSourceDestinationAddressTab();
        shipmentPage.clickAdd();

        Assert.assertTrue(shipmentPage.isSourceDestinationModalDisplayed(),
                "Source/Destination Info modal did not display after clicking + ADD!");

        shipmentPage.clickEpcisTab();
        shipmentPage.fillSourceAddress(
                value(testData, "Source Business Name", ""),
                value(testData, "Source Address1", ""),
                value(testData, "Source Address2", ""),
                value(testData, "Source City", ""),
                value(testData, "Source State", ""),
                value(testData, "Source Postal Code", ""),
                value(testData, "Source Country", ""),
                value(testData, "Source GLN", ""),
                value(testData, "Source SGLN", ""),
                value(testData, "Source Location GLN", ""),
                value(testData, "Source Location SGLN", ""));

        shipmentPage.fillDestinationAddress(
                value(testData, "Dest Business Name", ""),
                value(testData, "Dest Address1", ""),
                value(testData, "Dest Address2", ""),
                value(testData, "Dest City", ""),
                value(testData, "Dest State", ""),
                value(testData, "Dest Postal Code", ""),
                value(testData, "Dest Country", ""),
                value(testData, "Dest GLN", ""),
                value(testData, "Dest SGLN", ""),
                value(testData, "Dest Location GLN", ""),
                value(testData, "Dest Location SGLN", ""));
        shipmentPage.clickSave();
        String expectedMessage = value(testData, "Expected Message or Title",
                "Source/Destination information saved successfully.");
        String actualMessage = shipmentPage.getNotificationMessage();
        Assert.assertTrue(
                expectedMessage.equals(actualMessage)
                        || (actualMessage != null && actualMessage.toLowerCase().contains("already exists")),
                "Expected '" + expectedMessage + "' or already exists notification, but found: " + actualMessage);
    }

    @Test(priority = 3, description = "TC-3: Add shipment (single or multiple based on execution.mode) using batch names from POMaster_TC_04 to POMaster_TC_33.")
    public void verifyAddNewShipmentDialogAndControls() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(SHIPMENT_SHEET, "ShipmentMaster_TC_03");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ShipmentMasterPage shipmentPage = new ShipmentMasterPage(driver);
        shipmentPage.openShipmentMaster();

        String mode = ConfigReader.get("execution.mode", "single");
        String sourceDest = value(testData, "Source Destination", "");
        String remark = value(testData, "Remark", "shipment added");
        String expectedMessage = value(testData, "Expected Message or Title",
                "Shipment Event is generated and saved successfully.");

        shipmentPage.clickShipmentTab();

        if ("multiple".equalsIgnoreCase(mode)) {
            for (int i = 4; i <= 33; i++) {
                String poTestCaseId = String.format("POMaster_TC_%02d", i);
                Map<String, String> poData = pageData(PO_SHEET, poTestCaseId);
                String batchName = value(poData, "Batch Number");
                if (batchName == null || batchName.isBlank()) {
                    continue;
                }
                try {
                    boolean created = addShipment(shipmentPage, batchName, sourceDest, remark, expectedMessage);
                    if (!created) {
                        System.out.println("Shipment for batch " + batchName + " (" + poTestCaseId
                                + ") could not be generated / already generated; clicked Cancel and moving to next shipment.");
                    }
                } catch (Exception e) {
                    System.out.println("Warning: Failed to add shipment for batch " + batchName + " (" + poTestCaseId
                            + "): " + e.getMessage());
                    try {
                        shipmentPage.clickShipmentCancel();
                    } catch (Exception ignored) {
                    }
                }
            }
        } else {
            Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
            String batchName = value(poData, "Batch Number");
            if (batchName == null || batchName.isBlank()) {
                batchName = value(testData, "Batch", "");
            }
            addShipment(shipmentPage, batchName, sourceDest, remark, expectedMessage);
        }
    }

    private boolean addShipment(ShipmentMasterPage shipmentPage, String batchName, String sourceDest, String remark,
            String expectedMessage) {
        if (!shipmentPage.isShipmentModalDisplayed()) {
            shipmentPage.clickAdd();
        }
        Assert.assertTrue(shipmentPage.isShipmentModalDisplayed(),
                "TraceLink/EPCIS Information modal did not open after clicking + ADD on Shipment tab!");

        try {
            shipmentPage.selectBatch(batchName);
        } catch (Exception e) {
            System.out.println("Could not select batch " + batchName + ", clicking Cancel and moving to next shipment: "
                    + e.getMessage());
            shipmentPage.clickShipmentCancel();
            return false;
        }

        if (shipmentPage.isShipmentAlreadyGenerated()) {
            System.out.println("Shipment already generated for batch " + batchName
                    + ". Clicking Cancel and moving to next shipment.");
            shipmentPage.clickShipmentCancel();
            return false;
        }

        shipmentPage.selectSourceDestination(sourceDest);
        shipmentPage.clickCodeList();

        if (!shipmentPage.hasAvailableCodes()) {
            System.out.println("Batch " + batchName
                    + " has no serial numbers (Total S/N : 0/0). Clicking Cancel and moving to next shipment.");
            shipmentPage.clickShipmentCancel();
            return false;
        }

        boolean codesAvailable = shipmentPage.clickToViewCodes();
        if (!codesAvailable) {
            System.out.println("No serialized codes available for batch " + batchName
                    + ". Clicking Cancel and moving to next shipment.");
            shipmentPage.clickShipmentCancel();
            return false;
        }

        shipmentPage.selectAllCodes();
        shipmentPage.clickShipmentSave(remark);

        String actualMessage = shipmentPage.getNotificationMessage();
        if (actualMessage != null
                && (actualMessage.toLowerCase().contains("already") || actualMessage.toLowerCase().contains("exist")
                        || actualMessage.toLowerCase().contains("select the code"))) {
            System.out.println("Notification indicates shipment cannot be completed for batch " + batchName + " ("
                    + actualMessage + "). Clicking Cancel and moving to next shipment.");
            shipmentPage.closeNotification();
            try {
                if (shipmentPage.isShipmentModalDisplayed()) {
                    shipmentPage.clickShipmentCancel();
                }
            } catch (Exception ignored) {
            }
            return false;
        }

        if (expectedMessage != null && !expectedMessage.isBlank()) {
            Assert.assertEquals(actualMessage, expectedMessage);
        }
        shipmentPage.closeNotification();
        return true;
    }
}
