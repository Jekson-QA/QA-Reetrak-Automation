package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.ExportBatchDetailsPage;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

public class ExportBatchDetailsTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String EXPORT_BATCH_DETAILS_SHEET = "testdata.exportbatchdetails.sheet";
    private static final String EXPORT_BATCH_STATUS_SHEET = "testdata.exportbatchstatus.sheet";

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
    }

    @Test(priority = 1, description = "TC-1: Open Export Batch Details page.")
    public void verifyExportBatchDetailsPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(EXPORT_BATCH_DETAILS_SHEET, "ExportBatchDetails_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ExportBatchDetailsPage page = new ExportBatchDetailsPage(driver);
        page.openExportBatchDetail();

        Assert.assertEquals(page.getExportBatchDetailPageName(), value(testData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "TC-2: Select all dropdowns and click filter button.")
    public void verifyFilterButtonOperation() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        // Map<String, String> productData = pageData("testdata.product.sheet",
        // "Product-Master_TC_04");
        Map<String, String> statusData = pageData(EXPORT_BATCH_STATUS_SHEET, "ExportBatchDetails_TC_02");
        Map<String, String> testData = pageData(EXPORT_BATCH_DETAILS_SHEET, "ExportBatchDetails_TC_02");

        // String exportFormatType = value(productData, "serialmanager");
        String exportFormatType = value(statusData, "serialmanager");
        String status = value(statusData, "Status");
        String postingStatus = value(statusData, "Posting Status");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ExportBatchDetailsPage page = new ExportBatchDetailsPage(driver);
        page.openExportBatchDetail();

        page.selectExportFormatType(exportFormatType);
        page.selectStatus(status);
        page.selectPostingStatus(postingStatus);

        page.clickFilter();

        String expectedError = value(testData, "Expected Form Error");
        List<String> errors = page.getValidationErrors();
        boolean hasError = false;
        for (String err : errors) {
            if (err.toLowerCase().contains(expectedError.toLowerCase())) {
                hasError = true;
                break;
            }
        }
        Assert.assertTrue(hasError,
                "Expected validation containing '" + expectedError + "' was not found! Actual errors: " + errors);
    }

    @Test(priority = 3, description = "TC-3: Select all data, click generate, and verify success confirmation.")
    public void verifyExportBatchGeneration() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        // Map<String, String> productData = pageData("testdata.product.sheet",
        // "Product-Master_TC_04");
        Map<String, String> statusData = pageData(EXPORT_BATCH_STATUS_SHEET, "ExportBatchDetails_TC_03");

        // String exportFormatType = value(productData, "serialmanager");
        String exportFormatType = value(statusData, "serialmanager");
        String status = value(statusData, "Status");
        String postingStatus = value(statusData, "Posting Status");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ExportBatchDetailsPage page = new ExportBatchDetailsPage(driver);
        page.openExportBatchDetail();

        page.selectExportFormatType(exportFormatType);
        page.selectStatus(status);
        page.selectPostingStatus(postingStatus);
        page.clickFilter();

        page.selectBatch(createdBatch);
        page.clickSelectAll();
        page.clickGenerate();

        page.handlePostConfirmation("Generate batch details");

        Assert.assertTrue(page.isSuccessMessageDisplayed() || page.isNotificationGreen(),
                "Success confirmation message was not displayed or not in green!");
    }

    @Test(priority = 4, description = "TC-4: Try to post already posted batch and verify 'No Data Found' validation.")
    public void verifyAlreadyPostedBatchValidation() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        // Map<String, String> productData = pageData("testdata.product.sheet",
        // "Product-Master_TC_04");
        Map<String, String> statusData = pageData(EXPORT_BATCH_STATUS_SHEET, "ExportBatchDetails_TC_04");

        // String exportFormatType = value(productData, "serialmanager");
        String exportFormatType = value(statusData, "serialmanager");
        String status = value(statusData, "Status");
        String postingStatus = value(statusData, "Posting Status");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ExportBatchDetailsPage page = new ExportBatchDetailsPage(driver);
        page.openExportBatchDetail();

        page.selectExportFormatType(exportFormatType);
        page.selectStatus(status);
        page.selectPostingStatus(postingStatus);
        page.clickFilter();

        try {
            page.selectBatch(createdBatch);
            page.clickSelectAll();
            page.clickGenerate();
            page.handlePostConfirmation("Generate batch details already posted");
        } catch (Exception e) {
            // Catch error if batch is already hidden/unselectable in Unposted state
        }

        String expectedError = value(statusData, "Expected Notification");
        boolean hasError = false;

        // Check if there is a notification
        try {
            String notification = page.getNotificationMessage();
            System.out.println("Notification message: " + notification);
            String normalizedActual = notification.toLowerCase().replaceAll("\\s+", " ");
            String normalizedExpected = expectedError.toLowerCase().replaceAll("\\s+", " ");
            if (normalizedActual.contains(normalizedExpected)) {
                hasError = true;
            }
        } catch (Exception e) {
            // Ignore
        }

        // Check if there are form validation errors
        if (!hasError) {
            List<String> errors = page.getValidationErrors();
            System.out.println("Validation errors: " + errors);
            String normalizedExpected = expectedError.toLowerCase().replaceAll("\\s+", " ");
            for (String err : errors) {
                String normalizedActual = err.toLowerCase().replaceAll("\\s+", " ");
                if (normalizedActual.contains(normalizedExpected)) {
                    hasError = true;
                    break;
                }
            }
        }

        Assert.assertTrue(hasError, "Expected validation containing '" + expectedError + "' was not found!");
    }

}
