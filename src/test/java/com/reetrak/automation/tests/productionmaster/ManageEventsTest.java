package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.ManageEventsPage;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

public class ManageEventsTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String MANAGE_EVENTS_SHEET = "testdata.manageevents.sheet";

    @Test(priority = 1, description = "TC-1: Navigate to Production Master -> Manage Events and verify page title.")
    public void verifyManageEventsPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(MANAGE_EVENTS_SHEET, "ManageEvents_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ManageEventsPage page = new ManageEventsPage(driver);
        page.openManageEvents();

        Assert.assertEquals(page.getManageEventsPageName(), value(testData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "TC-2: Select line, click BATCH button, select batch and click RESET button.")
    public void verifyResetButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(MANAGE_EVENTS_SHEET, "ManageEvents_TC_02");

        String line = getAndSyncLineName(MANAGE_EVENTS_SHEET, "ManageEvents_TC_02");
        String createdBatch = value(poData, "Batch Number");
        String generatedType = value(testData, "Generated Type");
        String exportFormat = value(testData, "Export Format");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ManageEventsPage page = new ManageEventsPage(driver);
        page.openManageEvents();

        page.selectLine(line);
        page.clickBatch();
        page.selectBatch(createdBatch);
        page.selectGeneratedType(generatedType);
        page.selectExportFormat(exportFormat);

        page.clickReset();
        Assert.assertEquals(page.getManageEventsPageName(), "Manage Events");
    }

    @Test(priority = 3, description = "TC-3: Post event details and verify success confirmation notification.")
    public void verifyPostEventGeneration() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(MANAGE_EVENTS_SHEET, "ManageEvents_TC_03");

        String line = getAndSyncLineName(MANAGE_EVENTS_SHEET, "ManageEvents_TC_03");
        String generatedType = value(testData, "Generated Type");
        String exportFormat = value(testData, "Export Format");
        String remarks = value(testData, "Remarks");
        String expectedNotify = value(testData, "Expected Notification");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ManageEventsPage page = new ManageEventsPage(driver);
        page.openManageEvents();

        page.selectLine(line);
        page.clickBatch();
        page.selectBatch(createdBatch);
        page.selectGeneratedType(generatedType);
        page.selectExportFormat(exportFormat);

        page.clickPost();
        page.handlePostConfirmation(remarks);

        Assert.assertTrue(page.isSuccessMessageDisplayed() || page.isNotificationGreen(),
                "Success confirmation message containing '" + expectedNotify + "' was not displayed!");
    }

    @Test(priority = 4, description = "TC-4: Attempt posting without selecting batch or mandatory fields and verify validation errors.")
    public void verifyPostEventGenerationWithMissingFields() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");

        String line = getAndSyncLineName(MANAGE_EVENTS_SHEET, "ManageEvents_TC_04");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ManageEventsPage page = new ManageEventsPage(driver);
        page.openManageEvents();

        page.selectLine(line);
        page.clickBatch();
        // Do not select batch, generated type, or export format and directly click Post
        page.clickPost();

        List<String> errors = page.getValidationErrors();
        Assert.assertFalse(errors.isEmpty(),
                "Expected validation error messages to be displayed, but none were found!");
        System.out.println("Validation errors captured: " + errors);
    }

    @Test(priority = 5, description = "TC-5: Post event details with Manual Batch and Aggregation Event selection and verify success notification.")
    public void verifyManualBatchAggregationEventPost() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(MANAGE_EVENTS_SHEET, "ManageEvents_TC_05");

        String line = getAndSyncLineName(MANAGE_EVENTS_SHEET, "ManageEvents_TC_05");
        String generatedType = value(testData, "Generated Type");
        String exportFormat = value(testData, "Export Format");
        String event = value(testData, "Event");
        String remarks = value(testData, "Remarks");
        String expectedNotify = value(testData, "Expected Notification");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ManageEventsPage page = new ManageEventsPage(driver);
        page.openManageEvents();

        page.selectLine(line);
        page.clickBatch();
        page.selectBatch(createdBatch);
        page.selectGeneratedType(generatedType);
        page.selectExportFormat(exportFormat);
        page.selectEvent(event);
        page.clickCodeList();
        page.clickSelectAllCodes();

        page.clickPost();
        page.handlePostConfirmation(remarks);

        Assert.assertTrue(page.isSuccessMessageDisplayed() || page.isNotificationGreen(),
                "Success confirmation message containing '" + expectedNotify + "' was not displayed!");
    }
}
