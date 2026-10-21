package com.reetrak.automation.tests.productionmaster;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.ManageDispositionPage;

public class ManageDispositionTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String MANAGE_DISPOSITION_SHEET = "testdata.managedisposition.sheet";

        @Test(priority = 1, description = "Verification of Manage Dsipostion page opening successfully.")
        public void verifyManageDispositionPageOpensSuccessfully() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                Assert.assertEquals(
                                manageDispositionPage.getManageDispositionPageName(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of 'Code List' page opening successfully.")
        public void verifyCodelistMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"));
                Assert.assertEquals(manageDispositionPage.getCodeListTitle(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of 'Change Code Status' functionality for Accepted to Sample.")
        public void changestatusfromacceptedtosample() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                manageDispositionPage.changestatus("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(manageDispositionPage.getNotificationMessage(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verification of 'Change Code Status' functionality for Accepted to Reject.")
        public void changestatusfromacceptedtoreject() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                manageDispositionPage.changestatus("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(manageDispositionPage.getNotificationMessage(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 5, description = "Verification of 'Change Code Status' functionality for Accepted to Destroy.")
        public void changestatusfromacceptedtodestroy() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_05");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                manageDispositionPage.changestatus("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(manageDispositionPage.getNotificationMessage(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 6, description = "Verification of 'Change Code Status' functionality for Sample to Sample Destroy.")
        public void changestatusfromsampletosampledestroy() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                manageDispositionPage.changestatussampletosampledestroy("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(manageDispositionPage.getNotificationMessage(),
                                value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 7, description = "Verification of 'Scan Code' functionality on 'Code List' Page.")
        public void scancodefield() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                String TempMessage = manageDispositionPage.scancode("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(TempMessage, value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 8, description = "Verification of 'Seach Code' functionality on 'Code List' Page.")
        public void searchcodefield() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_08");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                String TempMessage = manageDispositionPage.searchcode("Add", value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(TempMessage, value(ManageDispositionData, "Expected Message or Title"));
        }

        @Test(priority = 9, description = "Verification of 'Seach Code' functionality on 'Verify Code detail' Page.")
        public void confirmsearchcodefield() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> ManageDispositionData = pageData(MANAGE_DISPOSITION_SHEET, "MD_TC_09");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                ManageDispositionPage manageDispositionPage = new ManageDispositionPage(driver);
                manageDispositionPage.openManageDispostion();
                // manageDispositionPage.confirmcodelist("Add", value(ManageDispositionData,
                // "Line"),value(ManageDispositionData, "Batch"),value(ManageDispositionData,
                // "Level"),value(ManageDispositionData, "CurrentStatus"));
                String TempMessage = manageDispositionPage.confirmcodesearch("Add",
                                value(ManageDispositionData, "Line"),
                                value(ManageDispositionData, "Batch"), value(ManageDispositionData, "Level"),
                                value(ManageDispositionData, "CurrentStatus"),
                                value(ManageDispositionData, "NewStatus"));
                Assert.assertEquals(TempMessage, value(ManageDispositionData, "Expected Message or Title"));
        }
}