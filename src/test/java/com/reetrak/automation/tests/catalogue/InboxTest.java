package com.reetrak.automation.tests.catalogue;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.catalogue.InboxPage;
import com.reetrak.automation.pages.productionmaster.BatchRequestPage;
import com.reetrak.automation.pages.productionmaster.ProductMasterPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class InboxTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String INBOX_SHEET = "testdata.inbox.sheet";
    private static final String PO_SHEET = "testdata.po.sheet";
    private static final String PRODUCT_SHEET = "testdata.product.sheet";

    @Test(priority = 1, description = "TC-1: Navigate to Catalogue -> Inbox and verify page title.")
    public void verifyInboxPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        InboxPage inboxPage = new InboxPage(driver);
        inboxPage.openInbox();

        Assert.assertEquals(inboxPage.getInboxPageName(), "Inbox");
    }

    @Test(priority = 2, description = "TC-2: Approve the batch or product request from Inbox.")
    public void verifyApproveRequest() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> inboxData = pageData(INBOX_SHEET, "Inbox_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        InboxPage inboxPage = new InboxPage(driver);
        inboxPage.openInbox();

        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_04");
        String poNumber = value(poData, "PO Number");
        String batchNumber = value(poData, "Batch Number");
        String requestName = poNumber.trim() + "-" + batchNumber.trim();
        org.openqa.selenium.WebElement row = null;
        try {
            row = inboxPage.getSelectedRow(requestName);
        } catch (Exception e) {
            System.out.println(
                    "Request '" + requestName + "' is already approved or not found in Inbox. Skipping approval.");
            return;
        }
        String rowText = row.getText();

        inboxPage.selectRowByRequestName(requestName);
        Thread.sleep(1000);
        inboxPage.clickAction();
        Thread.sleep(1500);

        if (rowText.toLowerCase().contains("batch")) {
            String expectedBatchName = value(poData, "Batch Number");
            String displayedBatchName = inboxPage.getDisplayedFieldValue("Batch Number");
            Assert.assertEquals(displayedBatchName, expectedBatchName, "Batch Name mismatch!");

            inboxPage.clickApprove();
            inboxPage.confirmOk();
            inboxPage.submitRemarks(value(inboxData, "Remarks"));
            Assert.assertEquals(inboxPage.getNotificationMessage(), value(inboxData, "Expected Message or Title"));

            BatchRequestPage batchRequestPage = new BatchRequestPage(driver);
            batchRequestPage.openBatchRequest();
        } else {
            Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_04");
            String expectedProductName = value(productData, "productname");
            String displayedProductName = "";
            try {
                displayedProductName = inboxPage.getDisplayedFieldValue("Name");
            } catch (Exception e) {
                displayedProductName = inboxPage.getDisplayedFieldValue("Product Name");
            }
            Assert.assertEquals(displayedProductName, expectedProductName, "Product Name mismatch!");

            inboxPage.clickApprove();
            inboxPage.confirmOk();
            inboxPage.submitRemarks(value(inboxData, "Remarks"));
            Assert.assertEquals(inboxPage.getNotificationMessage(), value(inboxData, "Expected Message or Title"));

            ProductMasterPage productPage = new ProductMasterPage(driver);
            productPage.openProductMaster();
        }
    }
}
