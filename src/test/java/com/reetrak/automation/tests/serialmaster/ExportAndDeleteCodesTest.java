package com.reetrak.automation.tests.serialmaster;

import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.serialmaster.ExportAndDeleteCodesPage;
import com.reetrak.automation.pages.LoginLogoutPage;
import org.testng.Assert;

import java.util.Map;

public class ExportAndDeleteCodesTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String EXPORT_DELETE_SHEET = "testdata.ExportAndDelete.sheet";

        @Test(priority = 1, description = "Verification of EXPORT SSCC Codes functionality.")
        public void verifyExportCodeFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ssccPoolData = pageData(EXPORT_DELETE_SHEET, "Export-Delete_TC_1");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ExportAndDeleteCodesPage exportAndDeleteCodesPage = new ExportAndDeleteCodesPage(driver);
                exportAndDeleteCodesPage.openSSCCPool();
                exportAndDeleteCodesPage.selectSSCCPool(value(ssccPoolData, "Existing Serial Manager"),
                                value(ssccPoolData, "Existing Prefix"), value(ssccPoolData, "Existing Ext Digit"));
                exportAndDeleteCodesPage.clickEditSSCCPool();
                exportAndDeleteCodesPage.exportCode();
                Assert.assertEquals(exportAndDeleteCodesPage.getNotificationMessage(),
                                value(ssccPoolData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of DELETE SSCC Codes functionality.")
        public void verifyDeleteCodeFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ssccPoolData = pageData(EXPORT_DELETE_SHEET, "Export-Delete_TC_2");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ExportAndDeleteCodesPage exportAndDeleteCodesPage = new ExportAndDeleteCodesPage(driver);
                exportAndDeleteCodesPage.openSSCCPool();
                exportAndDeleteCodesPage.selectSSCCPool(value(ssccPoolData, "Existing Serial Manager"),
                                value(ssccPoolData, "Existing Prefix"), value(ssccPoolData, "Existing Ext Digit"));
                exportAndDeleteCodesPage.clickEditSSCCPool();
                Assert.assertEquals(exportAndDeleteCodesPage.deleteCode(), value(ssccPoolData, "Confirmation Message"));
                Assert.assertEquals(exportAndDeleteCodesPage.getNotificationMessage(),
                                value(ssccPoolData, "Expected Message or Title"));
        }
}
