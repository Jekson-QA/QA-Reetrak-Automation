package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.BatchRequestPage;
import com.reetrak.automation.utilities.BatchRequestDatabaseUtils;
import com.reetrak.automation.utilities.ConfigReader;

import java.util.Map;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class BatchRequestTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String BATCH_REQUEST_SHEET = "testdata.batchrequest.sheet";
        private static final String PO_SHEET = "testdata.po.sheet";
        private static final String LINE_SHEET = "testdata.line.sheet";

        @Test(priority = 1, description = "Verify that the Batch Request screen opens successfully.")
        public void verifyBatchRequestMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, "BatchRequest_TC_01");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
                batchRequestPage.openBatchRequest();
                Assert.assertEquals(batchRequestPage.getBatchRequestPageName(),
                                value(batchRequestData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verify Batch Load functionality without approve from Inbox.")
        public void verifyLoadBatchFunctionalitywithoutapprove() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, "BatchRequest_TC_02");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
                batchRequestPage.openBatchRequest();

                String currentPONum = value(batchRequestData, "PO Number");
                String hierarchyName = value(batchRequestData, "Hierarchy Name");

                batchRequestPage.loadBatchAndSave(currentPONum, hierarchyName,
                                value(batchRequestData, "Remarks", "ok"), batchRequestData);

                Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                                batchRequestPage.expectedMessage(batchRequestData));
        }

        @Test(priority = 3, description = "Verify Batch Load functionality with approve from Inbox.")
        public void verifyLoadBatchFunctionalitywithapprove() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET, "BatchRequest_TC_03");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
                batchRequestPage.openBatchRequest();

                String currentPONum = value(batchRequestData, "PO Number");
                String hierarchyName = value(batchRequestData, "Hierarchy Name");

                batchRequestPage.loadBatchAndSave(currentPONum, hierarchyName,
                                value(batchRequestData, "Remarks", "ok"), batchRequestData);

                Assert.assertEquals(batchRequestPage.getNotificationMessage(),
                                batchRequestPage.expectedMessage(batchRequestData));
        }

        // @Test(priority = 3, description = "Verify hierarchy approval and batch
        // transfer.")
        // public void verifyApproveAndTransferBatch() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_02");
        // Map<String, String> transferData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_03");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String currentPONum = value(batchRequestData, "PO Number");
        // String currentBatchNum = value(batchRequestData, "Batch Number");
        // String currentLineName = batchRequestPage.getLineName(transferData, lineData,
        // createdPOs);

        // batchRequestPage.handleInboxApproval(currentPONum, currentBatchNum,
        // batchRequestData);
        // batchRequestPage.openBatchRequest();

        // batchRequestPage.transferBatch(currentPONum, value(transferData, "Mode", ""),
        // currentLineName,
        // value(transferData, "Confirmation Message 1", "Transfer batch"),
        // value(transferData, "Remarks", "ok"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(transferData, "Expected Message or Title"));
        // batchRequestPage.closeNotification();
        // }

        // @Test(priority = 4, description = "Verify last saved batch status is
        // Running.")
        // public void verifyLastSavedBatchStatusIsRunning() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_04");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // batchRequestPage.selectBatchByPONumberP2(poNumber);
        // batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch
        // Status", "Running"));
        // Assert.assertEquals(batchRequestPage.getSelectedBatchStatus(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 5, description = "Verify re-transfer validation when Running
        // batch mode is not Online & get batchid for batch execution")
        // public void verifyRetransferValidationForNonOnlineModeBatch() throws
        // Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_05");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // String batchName = batchRequestPage.getBatchNumber(batchRequestData, poData,
        // createdPOs);

        // int batchId = BatchRequestDatabaseUtils.getBatchId(batchName);
        // try {
        // batchRequestPage.selectBatchByPONumberP2(poNumber);
        // batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch
        // Status", "Running"));
        // batchRequestPage.clickReTransfer();
        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title",
        // "Batch re-transfer operation can allow for mode : Online. Please, verify
        // batch mode."));
        // } finally {
        // if (poNumber != null) {
        // BatchRequestDatabaseUtils.updateMode(batchId);
        // }
        // }
        // }

        // @Test(priority = 6, description = "Verify re-transfer works when batch status
        // is Running.")
        // public void verifyRetransferRunningBatch() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestDataP9 = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_09");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_06");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String batchName = batchRequestPage.getBatchNumber(batchRequestDataP9,
        // poData, createdPOs);
        // int batchId = BatchRequestDatabaseUtils.getBatchId(batchName);
        // BatchRequestDatabaseUtils.updateMode(batchId);

        // String lineName = batchRequestPage.getLineName(batchRequestData, lineData,
        // createdPOs);

        // batchRequestPage.retransferRunningBatch(lineName, value(batchRequestData,
        // "Mode", ""),
        // value(batchRequestData, "Confirmation Message 1", "Are you sure want to
        // re-transfer Lot Number"),
        // "Are you sure want to re-transfer batch :",
        // value(batchRequestData, "Remarks", "ok"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // batchRequestPage.selectFirstBatchWithStatus(
        // value(batchRequestData, "Expected Batch Status", "Ready For Transfer"));
        // Assert.assertEquals(batchRequestPage.getSelectedBatchStatus(),
        // value(batchRequestData, "Expected Batch Status", "Ready For Transfer"));
        // }

        // @Test(priority = 7, description = "Verify selected Ready For Transfer batch
        // can be transferred After Re-transfer")
        // public void verifyLastLoadedPOCanBeRE_Transferred() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_07");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // String lineName = batchRequestPage.getLineName(batchRequestData, lineData,
        // createdPOs);

        // batchRequestPage.transferReadyForTransferBatch(poNumber,
        // value(batchRequestData, "Mode", ""), lineName,
        // value(batchRequestData, "Confirmation Message 1", "Transfer batch"),
        // value(batchRequestData, "Remarks", "ok"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 8, description = "Verify batch is not reopened without
        // Temporary Closed status and validation is displayed.")
        // public void verifyReopenValidationForNonTemporaryClosedBatch() throws
        // Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_08");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // batchRequestPage.selectBatchByPONumber(poNumber);
        // batchRequestPage.selectFirstBatchWithStatus(poNumber);
        // batchRequestPage.clickCloseAndReopen();

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 9, description = "Verify batch execution stored procedure
        // runs for selected batch id.")
        // public void verifyBatchExecutionStoredProcedureCanRun() {
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_09");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // String batchName = batchRequestPage.getBatchNumber(batchRequestData, poData,
        // createdPOs);
        // int batchId = BatchRequestDatabaseUtils.getBatchId(batchName);
        // BatchRequestDatabaseUtils.executeBatchExecutionProcedure(batchId);
        // Assert.assertTrue(batchId > 0, "Stored procedure executed for batch id.");
        // }

        // @Test(priority = 10, description = "Verify line validation while reopening
        // batch without selecting line.")
        // public void verifyReopenBatchRequiresLineSelection() throws Exception {
        // if (!BatchRequestDatabaseUtils.isLineSelectionWithReopenBatchEnabled()) {
        // throw new SkipException("Skipping test because
        // LINE_SELECTION_WITH_REOPEN_BATCH setting is disabled.");
        // }
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_10");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // String lineName = batchRequestPage.getLineName(batchRequestData, lineData,
        // createdPOs);

        // batchRequestPage.selectBatchByPONumber(poNumber);
        // batchRequestPage.selectFirstBatchWithStatus(value(batchRequestData, "Batch
        // Status", "Temporary Closed"));
        // batchRequestPage.openReopenDialogForSelectedBatch();
        // batchRequestPage.chooseReopenBatch();
        // batchRequestPage.selectReopenModeAndLine(value(batchRequestData, "Mode", ""),
        // lineName);
        // batchRequestPage.clearReopenLineSelectionAndSave(lineName);

        // Assert.assertEquals(batchRequestPage.getRequiredLineValidationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 11, description = "Verify re-open works when batch status is
        // Temporary Closed.")
        // public void verifyReopenTemporaryClosedBatch() throws Exception {
        // if (!BatchRequestDatabaseUtils.isLineSelectionWithReopenBatchEnabled()) {
        // org.testng.Reporter.getCurrentTestResult().setAttribute("softErrors",
        // java.util.List.of("Bypassed because LINE_SELECTION_WITH_REOPEN_BATCH setting
        // is disabled."));
        // }
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_11");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // String lineName = batchRequestPage.getLineName(batchRequestData, lineData,
        // createdPOs);

        // batchRequestPage.reopenTemporaryClosedBatch(poNumber,
        // value(batchRequestData, "Batch Status", "Temporary Closed"),
        // value(batchRequestData, "Mode", ""), lineName,
        // value(batchRequestData, "Remarks", "Reopen batch"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 12, description = "Verify Re-Initiate for extra quantity
        // batch can be edited and Save.")
        // public void verifyReInitiateExtraQuantityEditSaveAndTransfer() throws
        // Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestDataP9 = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_09");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_12");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // String batchName = batchRequestPage.getBatchNumber(batchRequestDataP9,
        // poData, createdPOs);
        // int batchId = BatchRequestDatabaseUtils.getBatchId(batchName);
        // BatchRequestDatabaseUtils.closeBatch(batchId);

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // batchRequestPage.openBatchRequest();

        // String hierarchyName = value(batchRequestData, "Hierarchy Name");

        // batchRequestPage.editExtraQuantityAndSave(poNumber,
        // value(batchRequestData, "Batch Status", "Re-Initiate for extra quantity"),
        // value(batchRequestData, "Extra Quantity", "1"),
        // value(batchRequestData, "Mode", ""),
        // hierarchyName,
        // value(batchRequestData, "Remarks", "ok"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }

        // @Test(priority = 13, description = "Verify inbox approval and batch transfer
        // for extra quantity batch.")
        // public void verifyApproveAndTransferBatchAfterTopup() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_12");
        // Map<String, String> transferData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_03");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        // Map<String, String> lineData = pageData(LINE_SHEET, "Line-Master_TC_03");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // String batchNumber = batchRequestPage.getBatchNumber(batchRequestData,
        // poData, createdPOs);
        // String transferLineName = batchRequestPage.getLineName(transferData,
        // lineData, createdPOs);

        // boolean isHierarchyEnabled =
        // Boolean.parseBoolean(ConfigReader.getRequired("hierarchy.master.enabled"));
        // if (isHierarchyEnabled) {
        // batchRequestPage.approveBatchInInbox(poNumber, batchNumber, "Approve Batch");
        // } else {
        // batchRequestPage.openBatchRequest();
        // }

        // batchRequestPage.transferBatch(poNumber, value(transferData, "Mode", ""),
        // transferLineName,
        // value(transferData, "Confirmation Message 1", "Transfer batch"),
        // value(transferData, "Remarks", "ok"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(transferData, "Expected Message or Title"));
        // }

        // @Test(priority = 14, description = "Verify batch execution stored procedure
        // runs for selected batch id.")
        // public void verifyBatchExecutionStoredProcedureCanRunAgain() {
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_09");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // String batchName = batchRequestPage.getBatchNumber(batchRequestData, poData,
        // createdPOs);
        // int batchId = BatchRequestDatabaseUtils.getBatchId(batchName);
        // BatchRequestDatabaseUtils.executeBatchExecutionProcedure(batchId);
        // Assert.assertTrue(batchId > 0, "Stored procedure executed for batch id.");
        // }

        // @Test(priority = 15, description = "Verify Permanent close feature")
        // public void verifyPermanentcloseFeature() throws Exception {
        // Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> batchRequestData = pageData(BATCH_REQUEST_SHEET,
        // "BatchRequest_TC_14");
        // Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");

        // LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        // loginPage.open(baseUrl);
        // loginPage.loginAs(value(loginData, "username"), value(loginData,
        // "password"));

        // BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
        // batchRequestPage.openBatchRequest();

        // String poNumber = batchRequestPage.getPONumber(batchRequestData, poData,
        // createdPOs);
        // batchRequestPage.permanentCloseBatch(poNumber,
        // value(batchRequestData, "Batch Status", "Temporary Closed"),
        // value(batchRequestData, "Remarks", "PermanentClose Batch"));

        // Assert.assertEquals(batchRequestPage.getNotificationMessage(),
        // value(batchRequestData, "Expected Message or Title"));
        // }
}
