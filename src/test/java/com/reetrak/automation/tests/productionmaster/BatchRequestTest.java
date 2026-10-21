package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.BatchRequestPage;
import com.reetrak.automation.pages.catalogue.InboxPage;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

import com.reetrak.automation.utilities.BatchRequestDatabaseUtils;

public class BatchRequestTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String BATCH_REQUEST_SHEET = "testdata.batchrequest.sheet";
    private static final String PO_SHEET = "testdata.po.sheet";
    private static final String PRODUCT_SHEET = "testdata.product.sheet";
    private static final String LINE_SHEET = "testdata.line.sheet";
    private static final String INBOX_SHEET = "testdata.inbox.sheet";
    private static final String HIERARCHY_SHEET = "testdata.hierarchy.sheet";

    private static final String MENU_TEST_CASE = "BatchRequest_TC_01";
    private static final String LOAD_AND_TRANSFER_TEST_CASE = "BatchRequest_TC_02";
    private static final String TRANSFER_LAST_LOADED_PO_TEST_CASE = "BatchRequest_TC_03";
    private static final String RUNNING_STATUS_TEST_CASE = "BatchRequest_TC_04";
    private static final String RETRANSFER_VALIDATION_TEST_CASE = "BatchRequest_TC_05";
    private static final String RETRANSFER_RUNNING_TEST_CASE = "BatchRequest_TC_06";
    private static final String REOPEN_TEMPORARY_CLOSED_TEST_CASE = "BatchRequest_TC_11";
    private static final String REOPEN_VALIDATION_TEST_CASE = "BatchRequest_TC_08";
    private static final String REOPEN_LINE_VALIDATION_TEST_CASE = "BatchRequest_TC_10";
    private static final String EXTRA_QUANTITY_TEST_CASE = "BatchRequest_TC_12";
    private static final String BATCH_EXECUTION_PROCEDURE_TEST_CASE = "BatchRequest_TC_09";
    private static final String TRANSFER_LAST_BATCH_AFTER_RETRANSFER = "BatchRequest_TC_07";
    private static final String PERMANENT_CLOSE_BATCH = "BatchRequest_TC_14";
    private static final String BATCH_ID_COLUMN = "BatchId";

    private static final String RUNNING_STATUS = "Running";
    private static final String TEMPORARY_CLOSED_STATUS = "Temporary Closed";
    private static final String READY_FOR_TRANSFER_STATUS = "Ready For Transfer";
    private static final String RE_INITIATE_EXTRA_QUANTITY_STATUS = "Re-Initiate for extra quantity";

    private Map<String, String> getSourcePOData(Map<String, String> data) {
        String poSource = data.get("PO Source Test Case");
        if (poSource != null && !poSource.isBlank()) {
            return pageData(PO_SHEET, poSource.trim());
        }
        return null;
    }

    private Map<String, String> getSourceLineData(Map<String, String> data) {
        String lineSource = data.get("Line Source Test Case");
        if (lineSource != null && !lineSource.isBlank()) {
            return pageData(LINE_SHEET, lineSource.trim());
        }
        return null;
    }

    private String getPONumber(Map<String, String> data, Map<String, String> poData) {
        if (!createdPOs.isEmpty()) {
            String poSource = data.get("PO Source Test Case");
            if (poSource != null && !poSource.isBlank()) {
                for (java.util.Map<String, String> created : createdPOs) {
                    if (poSource.trim().equalsIgnoreCase(created.get("testCase"))) {
                        return created.get("poNumber");
                    }
                }
            }
            return createdPOs.get(0).get("poNumber");
        }
        if (poData != null) {
            String poNum = poData.get("PO Number");
            if (poNum != null && !poNum.isBlank()) {
                return poNum.trim();
            }
        }
        return value(data, "Existing PO Number", "");
    }

    private String getBatchNumber(Map<String, String> data, Map<String, String> poData) {
        if (!createdPOs.isEmpty()) {
            String poSource = data.get("PO Source Test Case");
            if (poSource != null && !poSource.isBlank()) {
                for (java.util.Map<String, String> created : createdPOs) {
                    if (poSource.trim().equalsIgnoreCase(created.get("testCase"))) {
                        return created.get("batchNumber");
                    }
                }
            }
            return createdPOs.get(0).get("batchNumber");
        }
        if (poData != null) {
            String batchNum = poData.get("Batch Number");
            if (batchNum != null && !batchNum.isBlank()) {
                return batchNum.trim();
            }
        }
        return value(data, "Batch Number", "");
    }

    private String getLineName(Map<String, String> data, Map<String, String> lineData) {
        if (!createdPOs.isEmpty()) {
            String poSource = data.get("PO Source Test Case");
            if (poSource != null && !poSource.isBlank()) {
                for (java.util.Map<String, String> created : createdPOs) {
                    if (poSource.trim().equalsIgnoreCase(created.get("testCase"))) {
                        return created.get("lineName");
                    }
                }
            }
            return createdPOs.get(0).get("lineName");
        }
        if (lineData != null) {
            String lineName = lineData.get("New Line Name");
            if (lineName == null || lineName.isBlank()) {
                lineName = lineData.get("Existing Line Name");
            }
            if (lineName != null && !lineName.isBlank()) {
                return lineName.trim();
            }
        }
        return value(data, "Line", "");
    }

    @Test(priority = 1, description = "Verify that the Batch Request screen opens successfully.")
    public void verifyBatchRequestMenuOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, MENU_TEST_CASE);

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        batchRequestPage.openBatchRequest();
        Assert.assertEquals(batchRequestPage.getBatchRequestPageName(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @DataProvider(name = "batchRequestPOData")
    public Object[][] batchRequestPOData() {
        java.util.List<java.util.Map<String, String>> posToUse = loadPOsFromExcel();
        if (posToUse.isEmpty()) {
            Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, LOAD_AND_TRANSFER_TEST_CASE);
            Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
            Map<String, String> lineData = getSourceLineData(batchRequestData);
            String poNumber = getPONumber(batchRequestData, poData);
            String lineName = getLineName(batchRequestData, lineData);
            java.util.Map<String, String> fallbackPO = new java.util.HashMap<>();
            fallbackPO.put("poNumber", poNumber);
            fallbackPO.put("batchNumber", getBatchNumber(batchRequestData, poData));
            fallbackPO.put("lineName", lineName);
            fallbackPO.put("testCase", "POMaster_TC_04");
            posToUse.add(fallbackPO);
        }
        Object[][] data = new Object[posToUse.size()][1];
        for (int i = 0; i < posToUse.size(); i++) {
            data[i][0] = posToUse.get(i);
        }
        return data;
    }

    @Test(priority = 2, dataProvider = "batchRequestPOData", description = "Verify the full Batch Request load, remark, save and transfer success flow.")
    public void verifyLoadBatchAndSaveFlow(java.util.Map<String, String> poItem) throws Exception {
        String testCase = poItem.get("testCase");
        if (testCase == null || testCase.isBlank()) {
            testCase = "POMaster_TC_04";
        }
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName", "BatchRequest_TC_02 (" + testCase
                + "): Verify the full Batch Request load, remark, save and transfer success flow.");

        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, LOAD_AND_TRANSFER_TEST_CASE);
        String currentPONum = poItem.get("poNumber");
        String currentLineName = poItem.get("lineName");

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));

        batchRequestPage.clickLoadBatch();
        Assert.assertEquals(batchRequestPage.getPODetailsDialogTitle(),
                value(batchRequestData, "Expected PO Details Title"));

        batchRequestPage.selectPOByFilterOnly(currentPONum);
        batchRequestPage.selectFirstBatchWithStatus(currentPONum);
        batchRequestPage.clickPODetailsSave();

        Assert.assertEquals(batchRequestPage.getBatchInformationDialogTitle(),
                value(batchRequestData, "Expected Batch Information Title"));

        Assert.assertEquals(batchRequestPage.getDisplayedLineName(), currentLineName);

        batchRequestPage.selectBatchInformationLine(currentLineName);
        batchRequestPage.clickBatchInformationSave();
        batchRequestPage.selectBatchConfirmationMode(value(batchRequestData, "Mode", ""));
        batchRequestPage.clickBatchConfirmationSave();

        Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_07");
        boolean isHierarchyEnabled = Boolean
                .parseBoolean(com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
        if (isHierarchyEnabled) {
            batchRequestPage.selectHierarchySelection(value(hierarchyData, "Hierarchy Name"));
        }

        batchRequestPage.submitRemarks(value(batchRequestData, "Remarks", "ok"));
        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
        batchRequestPage.closeNotification();
    }

    @Test(priority = 3, dataProvider = "batchRequestPOData", description = "Verify hierarchy approval and batch transfer.")
    public void verifyApproveAndTransferBatch(java.util.Map<String, String> poItem) throws Exception {
        String testCase = poItem.get("testCase");
        if (testCase == null || testCase.isBlank()) {
            testCase = "POMaster_TC_04";
        }
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_03 (" + testCase + "): Verify hierarchy approval and batch transfer.");

        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        String currentPONum = poItem.get("poNumber");
        String currentBatchNum = poItem.get("batchNumber");
        String currentLineName = poItem.get("lineName");

        boolean isHierarchyEnabled = Boolean
                .parseBoolean(com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
        if (isHierarchyEnabled) {
            approveHierarchySelectionInInbox(currentPONum, currentBatchNum);
        }

        BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        batchRequestPage.openBatchRequest();

        Map<String, String> transferData = pageData(BATCH_REQUEST_SHEET, TRANSFER_LAST_LOADED_PO_TEST_CASE);

        batchRequestPage.selectBatchByPONumberP2(currentPONum);
        batchRequestPage.selectFirstBatchWithStatus(currentPONum);

        batchRequestPage.clickTransfer();
        Assert.assertEquals(batchRequestPage.getTransferBatchDetailsDialogTitle(),
                value(transferData, "Expected Transfer Batch Details Title", "Transfer Batch Details"));

        batchRequestPage.selectTransferBatchDetailsModeAndLine(
                value(transferData, "Mode", ""),
                currentLineName);
        batchRequestPage.clickTransferBatchDetailsSave();
        batchRequestPage.confirmTransferBatchAndSubmitRemarks(
                value(transferData, "Confirmation Message 1", "Transfer batch"),
                value(transferData, "Remarks", "ok"));

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(transferData, "Expected Message or Title"));
        batchRequestPage.closeNotification();
    }

    @Test(priority = 4, description = "Verify last saved batch status is Running.")
    public void verifyLastSavedBatchStatusIsRunning() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_04: Verify last saved batch status is Running.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, RUNNING_STATUS_TEST_CASE);
        Map<String, String> poData = getSourcePOData(batchRequestData);
        String poNumber = getPONumber(batchRequestData, poData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
        batchRequestPage.selectBatchByPONumberP2(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", RUNNING_STATUS));
        Assert.assertEquals(batchRequestPage.getSelectedBatchStatus(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 5, description = "Verify re-transfer validation when Running batch mode is not Online & get batchid for batch execution")
    public void verifyRetransferValidationForNonOnlineModeBatch() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_05: Verify re-transfer validation when Running batch mode is not Online & get batchid for batch execution");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, RETRANSFER_VALIDATION_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = getPONumber(batchRequestData, poData);
        String Batchname = getBatchNumber(batchRequestData, poData);

        int batchId = BatchRequestDatabaseUtils.getBatchId(Batchname);
        String selectedPONumber = poNumber;
        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
        try {
            batchRequestPage.selectBatchByPONumberP2(poNumber);
            batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", RUNNING_STATUS));
            batchRequestPage.clickReTransfer();
            Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                    value(batchRequestData, "Expected Message or Title",
                            "Batch re-transfer operation can allow for mode : Online. Please, verify batch mode."));
        } finally {
            if (selectedPONumber != null) {
                BatchRequestDatabaseUtils.updateMode(batchId);
            }
        }
    }

    @Test(priority = 6, description = "Verify re-transfer works when batch status is Running.")
    public void verifyRetransferRunningBatch() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_06: Verify re-transfer works when batch status is Running.");
        Map<String, String> batchRequestDataP9 = pageData(BATCH_REQUEST_SHEET, BATCH_EXECUTION_PROCEDURE_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String Batchname = getBatchNumber(batchRequestDataP9, poData);
        int batchId = BatchRequestDatabaseUtils.getBatchId(Batchname);
        BatchRequestDatabaseUtils.updateMode(batchId);

        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, RETRANSFER_RUNNING_TEST_CASE);
        Map<String, String> lineData = getSourceLineData(batchRequestData);
        String lineName = getLineName(batchRequestData, lineData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
        batchRequestPage.waitForSeconds(5);

        batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", RUNNING_STATUS));
        batchRequestPage.clickReTransfer();
        batchRequestPage.confirmDialogByText(value(batchRequestData, "Confirmation Message 1",
                "Are you sure want to re-transfer Lot Number"));

        batchRequestPage.ReTransferBatchDetailsModeAndLine(
                lineName,
                value(batchRequestData, "Mode", ""));

        batchRequestPage.confirmDialogByText(value(batchRequestData, "Confirmation Message 1",
                "Are you sure want to re-transfer batch :"));

        batchRequestPage.submitRemarks(value(batchRequestData, "Remarks", "ok"));

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
        batchRequestPage.selectFirstBatchWithStatus(
                value(batchRequestData, "Expected Batch Status", READY_FOR_TRANSFER_STATUS));
        Assert.assertEquals(batchRequestPage.getSelectedBatchStatus(),
                value(batchRequestData, "Expected Batch Status", READY_FOR_TRANSFER_STATUS));
    }

    @Test(priority = 7, description = "Verify selected Ready For Transfer batch can be transferred After Re-transfer")
    public void verifyLastLoadedPOCanBeRE_Transferred() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_07: Verify selected Ready For Transfer batch can be transferred After Re-transfer");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, TRANSFER_LAST_BATCH_AFTER_RETRANSFER);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        Map<String, String> lineData = getSourceLineData(batchRequestData);
        String poNumber = getPONumber(batchRequestData, poData);
        String lineName = getLineName(batchRequestData, lineData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));

        batchRequestPage.selectBatchByPONumber(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(poNumber);

        batchRequestPage.clickTransfer();
        Assert.assertEquals(batchRequestPage.getTransferBatchDetailsDialogTitle(),
                value(batchRequestData, "Expected Transfer Batch Details Title", "Transfer Batch Details"));

        batchRequestPage.selectTransferBatchDetailsModeAndLine(
                value(batchRequestData, "Mode", ""),
                lineName);
        batchRequestPage.clickTransferBatchDetailsSave();
        batchRequestPage.confirmDialogByText(value(batchRequestData, "Confirmation Message 1",
                "Transfer batch"));
        batchRequestPage.submitRemarks(value(batchRequestData, "Remarks", "ok"));

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 8, description = "Verify batch is not reopened without Temporary Closed status and validation is displayed.")
    public void verifyReopenValidationForNonTemporaryClosedBatch() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_08: Verify batch is not reopened without Temporary Closed status and validation is displayed.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, REOPEN_VALIDATION_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = getPONumber(batchRequestData, poData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));

        batchRequestPage.selectBatchByPONumber(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(poNumber);
        batchRequestPage.clickCloseAndReopen();

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 9, description = "Verify batch execution stored procedure runs for selected batch id.")
    public void verifyBatchExecutionStoredProcedureCanRun() {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_09: Verify batch execution stored procedure runs for selected batch id.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, BATCH_EXECUTION_PROCEDURE_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String Batchname = getBatchNumber(batchRequestData, poData);
        int batchId = BatchRequestDatabaseUtils.getBatchId(Batchname);
        BatchRequestDatabaseUtils.executeBatchExecutionProcedure(batchId);
        Assert.assertTrue(batchId > 0, "Stored procedure executed for batch id.");
    }

    @Test(priority = 10, description = "Verify line validation while reopening batch without selecting line.")
    public void verifyReopenBatchRequiresLineSelection() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_10: Verify line validation while reopening batch without selecting line.");
        boolean isLineSelectionEnabled = BatchRequestDatabaseUtils.isLineSelectionWithReopenBatchEnabled();
        if (!isLineSelectionEnabled) {
            throw new org.testng.SkipException(
                    "Skipping test because LINE_SELECTION_WITH_REOPEN_BATCH setting is disabled.");
        }
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, REOPEN_LINE_VALIDATION_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = poData.get("PO Number").trim();
        Map<String, String> lineData = getSourceLineData(batchRequestData);
        String lineName = getLineName(batchRequestData, lineData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));

        batchRequestPage.selectBatchByPONumber(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", TEMPORARY_CLOSED_STATUS));
        batchRequestPage.openReopenDialogForSelectedBatch();
        batchRequestPage.chooseReopenBatch();
        batchRequestPage.selectReopenModeAndLine(
                value(batchRequestData, "Mode", ""),
                lineName);
        batchRequestPage.clearReopenLineSelectionAndSave(lineName);
        Assert.assertEquals(batchRequestPage.getRequiredLineValidationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 11, description = "Verify re-open works when batch status is Temporary Closed.")
    public void verifyReopenTemporaryClosedBatch() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_11: Verify re-open works when batch status is Temporary Closed.");
        if (!BatchRequestDatabaseUtils.isLineSelectionWithReopenBatchEnabled()) {
            org.testng.Reporter.getCurrentTestResult().setAttribute("softErrors",
                    java.util.List.of("Bypassed because LINE_SELECTION_WITH_REOPEN_BATCH setting is disabled."));
        }
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, REOPEN_TEMPORARY_CLOSED_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = poData.get("PO Number").trim();
        Map<String, String> lineData = getSourceLineData(batchRequestData);
        String lineName = getLineName(batchRequestData, lineData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));

        batchRequestPage.selectBatchByPONumber(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", TEMPORARY_CLOSED_STATUS));
        batchRequestPage.openReopenDialogForSelectedBatch();
        batchRequestPage.chooseReopenBatch();
        batchRequestPage.selectReopenModeAndLine(
                value(batchRequestData, "Mode", ""),
                lineName);
        batchRequestPage.confirmReopenBatch(value(batchRequestData, "Remarks", "Reopen batch"));

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 12, description = "Verify Re-Initiate for extra quantity batch can be edited and Save.")
    public void verifyReInitiateExtraQuantityEditSaveAndTransfer() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_12: Verify Re-Initiate for extra quantity batch can be edited and Save.");
        Map<String, String> batchRequestDataP9 = pageData(BATCH_REQUEST_SHEET, BATCH_EXECUTION_PROCEDURE_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String Batchname = getBatchNumber(batchRequestDataP9, poData);
        int batchId = BatchRequestDatabaseUtils.getBatchId(Batchname);
        BatchRequestDatabaseUtils.closeBatch(batchId);

        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, EXTRA_QUANTITY_TEST_CASE);
        poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = getPONumber(batchRequestData, poData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
        batchRequestPage.waitForSeconds(5);

        batchRequestPage.selectBatchByPONumberAndStatusForEdit(poNumber,
                value(batchRequestData, "Batch Status", RE_INITIATE_EXTRA_QUANTITY_STATUS));

        batchRequestPage.clickEdit();
        batchRequestPage.updateExtraQuantityAndSave(value(batchRequestData, "Extra Quantity", "1"));

        batchRequestPage.confirmDialogContaining("want to generate");
        batchRequestPage.selectTransferBatchDetailsMode(
                value(batchRequestData, "Mode", ""));
        batchRequestPage.clickBatchConfirmationSave();

        Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_07");
        boolean isHierarchyEnabled = Boolean
                .parseBoolean(com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
        if (isHierarchyEnabled) {
            batchRequestPage.selectHierarchySelection(value(hierarchyData, "Hierarchy Name"));
        }

        batchRequestPage.submitRemarks(value(batchRequestData, "Remarks", "ok"));
        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    @Test(priority = 13, description = "Verify inbox approval and batch transfer for extra quantity batch.")
    public void verifyApproveAndTransferBatchAfterTopup() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_13: Verify inbox approval and batch transfer for extra quantity batch.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, EXTRA_QUANTITY_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = getPONumber(batchRequestData, poData);
        String batchNumber = getBatchNumber(batchRequestData, poData);

        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        boolean isHierarchyEnabled = Boolean
                .parseBoolean(com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
        if (isHierarchyEnabled) {
            approveHierarchySelectionInInbox(poNumber, batchNumber);
        }

        Map<String, String> transferData = pageData(BATCH_REQUEST_SHEET, TRANSFER_LAST_LOADED_PO_TEST_CASE);
        Map<String, String> transferLineData = getSourceLineData(transferData);
        String transferLineName = getLineName(transferData, transferLineData);

        BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        if (!isHierarchyEnabled) {
            batchRequestPage.openBatchRequest();
        }
        batchRequestPage.selectBatchByPONumberP2(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(poNumber);

        batchRequestPage.clickTransfer();
        Assert.assertEquals(batchRequestPage.getTransferBatchDetailsDialogTitle(),
                value(transferData, "Expected Transfer Batch Details Title", "Transfer Batch Details"));

        batchRequestPage.selectTransferBatchDetailsModeAndLine(
                value(transferData, "Mode", ""),
                transferLineName);
        batchRequestPage.clickTransferBatchDetailsSave();
        batchRequestPage.confirmTransferBatchAndSubmitRemarks(
                value(transferData, "Confirmation Message 1", "Transfer batch"),
                value(transferData, "Remarks", "ok"));

        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(transferData, "Expected Message or Title"));
    }

    @Test(priority = 14, description = "Verify batch execution stored procedure runs for selected batch id.")
    public void verifyBatchExecutionStoredProcedureCanRunAgain() {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_14: Verify batch execution stored procedure runs for selected batch id.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, BATCH_EXECUTION_PROCEDURE_TEST_CASE);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String Batchname = getBatchNumber(batchRequestData, poData);
        int batchId = BatchRequestDatabaseUtils.getBatchId(Batchname);
        BatchRequestDatabaseUtils.executeBatchExecutionProcedure(batchId);
        Assert.assertTrue(batchId > 0, "Stored procedure executed for batch id.");
    }

    @Test(priority = 15, description = "Verify Permanent close feature")
    public void verifyPermanentcloseFeature() throws Exception {
        org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                "BatchRequest_TC_15: Verify Permanent close feature.");
        Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, PERMANENT_CLOSE_BATCH);
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = getPONumber(batchRequestData, poData);

        BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
        batchRequestPage.selectBatchByPONumber(poNumber);
        batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch Status", TEMPORARY_CLOSED_STATUS));
        batchRequestPage.openReopenDialogForSelectedBatch();
        batchRequestPage.choosePermanentClose();
        batchRequestPage.confirmPermanentCloseBatch(value(batchRequestData, "Remarks", "PermanentClose Batch"));
        Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                value(batchRequestData, "Expected Message or Title"));
    }

    private void runBatchRequestTestCase(String testCaseId) throws Exception {
        String executionMode = com.reetrak.automation.utilities.ConfigReader.get("execution.mode", "single");
        if ("single".equalsIgnoreCase(executionMode)) {
            throw new org.testng.SkipException("Skipping " + testCaseId + " because execution.mode is single.");
        }
        Map<String, String> poData = pageData(PO_SHEET, testCaseId);

        String poNumber = null;
        String batchNumber = null;
        String line = null;

        // Try to lookup dynamic values from createdPOs first
        if (!createdPOs.isEmpty()) {
            for (java.util.Map<String, String> created : createdPOs) {
                if (testCaseId.equalsIgnoreCase(created.get("testCase"))) {
                    poNumber = created.get("poNumber");
                    batchNumber = created.get("batchNumber");
                    line = created.get("lineName");
                    break;
                }
            }
        }

        // Fallback to Excel static values if not found in createdPOs
        if (poNumber == null || poNumber.isBlank()) {
            poNumber = value(poData, "PO Number");
        }
        if (batchNumber == null || batchNumber.isBlank()) {
            batchNumber = value(poData, "Batch Number");
        }
        if (line == null || line.isBlank()) {
            Map<String, String> lineData = pageData(LINE_SHEET, value(poData, "Line Source Test Case"));
            line = lineData.get("New Line Name");
            if (line == null || line.isBlank()) {
                line = lineData.get("Existing Line Name");
            }
        }
        line = line.trim();

        try {
            BatchRequestPage batchRequestPage = openBatchRequest(pageData(LOGIN_SHEET, "Login-Logout_TC_04"));
            batchRequestPage.clickLoadBatch();
            batchRequestPage.selectPOByFilterOnly(poNumber);
            batchRequestPage.selectFirstBatchWithStatus(poNumber);
            batchRequestPage.clickPODetailsSave();

            Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, LOAD_AND_TRANSFER_TEST_CASE);

            batchRequestPage.selectBatchInformationLine(line);
            batchRequestPage.clickBatchInformationSave();

            String mode = value(batchRequestData, "Mode", "Online");
            batchRequestPage.selectBatchConfirmationMode(mode);
            batchRequestPage.clickBatchConfirmationSave();

            Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_07");
            boolean isHierarchyEnabled = Boolean.parseBoolean(
                    com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
            if (isHierarchyEnabled) {
                batchRequestPage.selectHierarchySelection(value(hierarchyData, "Hierarchy Name"));
            }
            batchRequestPage.submitRemarks(value(batchRequestData, "Remarks", "ok"));
            batchRequestPage.closeNotification();

            if (isHierarchyEnabled) {
                approveHierarchySelectionInInbox(poNumber, batchNumber);
            }

            batchRequestPage.selectBatchByPONumberP2(poNumber);
            batchRequestPage.selectFirstBatchWithStatus(poNumber);
            batchRequestPage.clickTransfer();
            batchRequestPage.selectTransferBatchDetailsModeAndLine(mode, line);
            batchRequestPage.clickTransferBatchDetailsSave();
            Map<String, String> transferData = pageData(BATCH_REQUEST_SHEET, TRANSFER_LAST_LOADED_PO_TEST_CASE);
            batchRequestPage.confirmTransferBatchAndSubmitRemarks(
                    value(transferData, "Confirmation Message 1", "Transfer batch"),
                    value(transferData, "Remarks", "ok"));
            String expectedMsg = value(transferData, "Expected Message or Title");
            if (expectedMsg == null || expectedMsg.isBlank()) {
                expectedMsg = "Batch successfully transferred.";
            }
            Assert.assertEquals(batchRequestPage.getNotificationMessage(), expectedMsg);
            batchRequestPage.closeNotification();

            int batchId = BatchRequestDatabaseUtils.getBatchId(batchNumber);
            BatchRequestDatabaseUtils.executeBatchExecutionProcedure(batchId);

            int finalStatus = BatchRequestDatabaseUtils.getFinalStatus(batchId);
            int isActive = BatchRequestDatabaseUtils.getIsActive(batchId);
            Assert.assertEquals(finalStatus, 2, "Batch finalStatus should be 2 (Completed/Running)");
            Assert.assertEquals(isActive, 4, "Batch isActive should be 4 (Executed)");

            try {
                String poSheetName = com.reetrak.automation.utilities.ConfigReader.getRequired(PO_SHEET);
                com.reetrak.automation.utilities.ExcelUtils.updateTestCaseCell(poSheetName, testCaseId, "Batch Status",
                        "Executed Successfully");
            } catch (Exception e) {
                System.err.println("Warning: Could not update Excel file for " + testCaseId + ": " + e.getMessage());
            }
        } catch (Exception e) {
            try {
                String poSheetName = com.reetrak.automation.utilities.ConfigReader.getRequired(PO_SHEET);
                com.reetrak.automation.utilities.ExcelUtils.updateTestCaseCell(poSheetName, testCaseId, "Batch Status",
                        "Failed");
            } catch (Exception ex) {
                System.err.println("Warning: Could not update Excel file for " + testCaseId + ": " + ex.getMessage());
            }
            throw new org.testng.SkipException(
                    "Skipping test case " + testCaseId + " because of execution failure: " + e.getMessage(), e);
        }
    }

    @Test(priority = 16, description = "Verify batch request load, approve, transfer and execution for TC 05.")
    public void verifyBatchRequest_TC_05() throws Exception {
        runBatchRequestTestCase("POMaster_TC_05");
    }

    @Test(priority = 17, description = "Verify batch request load, approve, transfer and execution for TC 06.")
    public void verifyBatchRequest_TC_06() throws Exception {
        runBatchRequestTestCase("POMaster_TC_06");
    }

    @Test(priority = 18, description = "Verify batch request load, approve, transfer and execution for TC 07.")
    public void verifyBatchRequest_TC_07() throws Exception {
        runBatchRequestTestCase("POMaster_TC_07");
    }

    @Test(priority = 19, description = "Verify batch request load, approve, transfer and execution for TC 08.")
    public void verifyBatchRequest_TC_08() throws Exception {
        runBatchRequestTestCase("POMaster_TC_08");
    }

    @Test(priority = 20, description = "Verify batch request load, approve, transfer and execution for TC 09.")
    public void verifyBatchRequest_TC_09() throws Exception {
        runBatchRequestTestCase("POMaster_TC_09");
    }

    @Test(priority = 21, description = "Verify batch request load, approve, transfer and execution for TC 10.")
    public void verifyBatchRequest_TC_10() throws Exception {
        runBatchRequestTestCase("POMaster_TC_10");
    }

    @Test(priority = 22, description = "Verify batch request load, approve, transfer and execution for TC 11.")
    public void verifyBatchRequest_TC_11() throws Exception {
        runBatchRequestTestCase("POMaster_TC_11");
    }

    @Test(priority = 23, description = "Verify batch request load, approve, transfer and execution for TC 12.")
    public void verifyBatchRequest_TC_12() throws Exception {
        runBatchRequestTestCase("POMaster_TC_12");
    }

    @Test(priority = 24, description = "Verify batch request load, approve, transfer and execution for TC 13.")
    public void verifyBatchRequest_TC_13() throws Exception {
        runBatchRequestTestCase("POMaster_TC_13");
    }

    @Test(priority = 25, description = "Verify batch request load, approve, transfer and execution for TC 14.")
    public void verifyBatchRequest_TC_14() throws Exception {
        runBatchRequestTestCase("POMaster_TC_14");
    }

    @Test(priority = 26, description = "Verify batch request load, approve, transfer and execution for TC 15.")
    public void verifyBatchRequest_TC_15() throws Exception {
        runBatchRequestTestCase("POMaster_TC_15");
    }

    @Test(priority = 27, description = "Verify batch request load, approve, transfer and execution for TC 16.")
    public void verifyBatchRequest_TC_16() throws Exception {
        runBatchRequestTestCase("POMaster_TC_16");
    }

    @Test(priority = 28, description = "Verify batch request load, approve, transfer and execution for TC 17.")
    public void verifyBatchRequest_TC_17() throws Exception {
        runBatchRequestTestCase("POMaster_TC_17");
    }

    @Test(priority = 29, description = "Verify batch request load, approve, transfer and execution for TC 18.")
    public void verifyBatchRequest_TC_18() throws Exception {
        runBatchRequestTestCase("POMaster_TC_18");
    }

    @Test(priority = 30, description = "Verify batch request load, approve, transfer and execution for TC 19.")
    public void verifyBatchRequest_TC_19() throws Exception {
        runBatchRequestTestCase("POMaster_TC_19");
    }

    @Test(priority = 31, description = "Verify batch request load, approve, transfer and execution for TC 20.")
    public void verifyBatchRequest_TC_20() throws Exception {
        runBatchRequestTestCase("POMaster_TC_20");
    }

    @Test(priority = 32, description = "Verify batch request load, approve, transfer and execution for TC 21.")
    public void verifyBatchRequest_TC_21() throws Exception {
        runBatchRequestTestCase("POMaster_TC_21");
    }

    @Test(priority = 33, description = "Verify batch request load, approve, transfer and execution for TC 22.")
    public void verifyBatchRequest_TC_22() throws Exception {
        runBatchRequestTestCase("POMaster_TC_22");
    }

    @Test(priority = 34, description = "Verify batch request load, approve, transfer and execution for TC 23.")
    public void verifyBatchRequest_TC_23() throws Exception {
        runBatchRequestTestCase("POMaster_TC_23");
    }

    @Test(priority = 35, description = "Verify batch request load, approve, transfer and execution for TC 24.")
    public void verifyBatchRequest_TC_24() throws Exception {
        runBatchRequestTestCase("POMaster_TC_24");
    }

    @Test(priority = 36, description = "Verify batch request load, approve, transfer and execution for TC 25.")
    public void verifyBatchRequest_TC_25() throws Exception {
        runBatchRequestTestCase("POMaster_TC_25");
    }

    @Test(priority = 37, description = "Verify batch request load, approve, transfer and execution for TC 26.")
    public void verifyBatchRequest_TC_26() throws Exception {
        runBatchRequestTestCase("POMaster_TC_26");
    }

    @Test(priority = 38, description = "Verify batch request load, approve, transfer and execution for TC 27.")
    public void verifyBatchRequest_TC_27() throws Exception {
        runBatchRequestTestCase("POMaster_TC_27");
    }

    @Test(priority = 39, description = "Verify batch request load, approve, transfer and execution for TC 28.")
    public void verifyBatchRequest_TC_28() throws Exception {
        runBatchRequestTestCase("POMaster_TC_28");
    }

    @Test(priority = 40, description = "Verify batch request load, approve, transfer and execution for TC 29.")
    public void verifyBatchRequest_TC_29() throws Exception {
        runBatchRequestTestCase("POMaster_TC_29");
    }

    @Test(priority = 41, description = "Verify batch request load, approve, transfer and execution for TC 30.")
    public void verifyBatchRequest_TC_30() throws Exception {
        runBatchRequestTestCase("POMaster_TC_30");
    }

    @Test(priority = 42, description = "Verify batch request load, approve, transfer and execution for TC 31.")
    public void verifyBatchRequest_TC_31() throws Exception {
        runBatchRequestTestCase("POMaster_TC_31");
    }

    @Test(priority = 43, description = "Verify batch request load, approve, transfer and execution for TC 32.")
    public void verifyBatchRequest_TC_32() throws Exception {
        runBatchRequestTestCase("POMaster_TC_32");
    }

    @Test(priority = 44, description = "Verify batch request load, approve, transfer and execution for TC 33.")
    public void verifyBatchRequest_TC_33() throws Exception {
        runBatchRequestTestCase("POMaster_TC_33");
    }

    private BatchRequestPage openBatchRequest(Map<String, String> loginData) throws Exception {
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        batchRequestPage.openBatchRequest();
        return batchRequestPage;
    }

    private void approveHierarchySelectionInInbox(String poNumber, String batchNumber) throws Exception {
        InboxPage inboxPage = new InboxPage(driver);
        inboxPage.openInbox();

        String requestName = poNumber + "-" + batchNumber;
        inboxPage.filterRequest(requestName);
        inboxPage.selectRowByRequestName(requestName);
        inboxPage.clickAction();
        inboxPage.clickApprove();
        inboxPage.confirmOk();

        Map<String, String> inboxData = pageData(INBOX_SHEET, "Inbox_TC_01");
        inboxPage.submitRemarks(value(inboxData, "Remarks", "Approve Batch"));
        Assert.assertEquals(inboxPage.getNotificationMessage(),
                value(inboxData, "Expected Message or Title", "Batch request approved successfully."));

        BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        batchRequestPage.openBatchRequest();
    }

    private java.util.List<java.util.Map<String, String>> loadPOsFromExcel() {
        java.util.List<java.util.Map<String, String>> posToUse = new java.util.ArrayList<>(createdPOs);
        if (!posToUse.isEmpty()) {
            return posToUse.subList(0, 1);
        }
        if (posToUse.isEmpty()) {
            try {
                String poSheetName = com.reetrak.automation.utilities.ConfigReader.getRequired(PO_SHEET);
                java.util.List<String> allTCs = com.reetrak.automation.utilities.ExcelUtils
                        .getTestCaseNames(poSheetName);
                for (String tc : allTCs) {
                    Map<String, String> poDataRow = com.reetrak.automation.utilities.ExcelUtils.getRowData(poSheetName,
                            tc);
                    String expectedTitle = poDataRow.get("Expected Message or Title");
                    if (expectedTitle != null && expectedTitle.contains("Add PO")) {
                        String poNum = poDataRow.get("PO Number");
                        String batchNum = poDataRow.get("Batch Number");
                        if (poNum == null || poNum.isBlank() || batchNum == null || batchNum.isBlank()) {
                            String productSource = poDataRow.get("Product Source Test Case");
                            if (productSource != null && !productSource.isBlank()) {
                                Map<String, String> productData = pageData(PRODUCT_SHEET, productSource.trim());
                                String productNameStr = resolveProductName(productData.get("productname"));
                                String basePart = deriveBasePart(productNameStr);
                                poNum = "PO" + basePart + "_01";
                                batchNum = "BN" + basePart + "_01";
                            }
                        }
                        Map<String, String> lineDataRow = getSourceLineData(poDataRow);
                        String lineNameStr = "";
                        if (lineDataRow != null) {
                            lineNameStr = lineDataRow.get("New Line Name");
                            if (lineNameStr == null || lineNameStr.isBlank()) {
                                lineNameStr = lineDataRow.get("Existing Line Name");
                            }
                            if (lineNameStr == null || lineNameStr.isBlank()) {
                                lineNameStr = lineDataRow.get("Line");
                            }
                        }
                        if (lineNameStr == null || lineNameStr.isBlank()) {
                            lineNameStr = poDataRow.get("Line");
                        }
                        if (poNum != null && !poNum.isBlank()) {
                            java.util.Map<String, String> poItem = new java.util.HashMap<>();
                            poItem.put("poNumber", poNum.trim());
                            poItem.put("batchNumber", batchNum != null ? batchNum.trim() : "");
                            poItem.put("lineName", lineNameStr != null ? lineNameStr.trim() : "");
                            poItem.put("testCase", tc);
                            posToUse.add(poItem);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Warning: Could not load POs from Excel sheet: " + e.getMessage());
            }
        }
        return posToUse;
    }

}
