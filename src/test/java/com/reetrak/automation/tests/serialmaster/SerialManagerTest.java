package com.reetrak.automation.tests.serialmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.serialmaster.SerialManagerPage;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

public class SerialManagerTest extends BaseTest {
        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String SERIAL_MANAGER_SHEET = "testdata.SerialManager.sheet";

        @Test(priority = 1, description = "Verify that the Serial Manager menu opens successfully.")
        public void verifySerialManagerMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                Assert.assertEquals(serialManagerPage.getSerialMasterPageName(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verify the functionality of the Add Serial Manager button.")
        public void verifyAddSerialManagerButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.addSerialManager();
                Assert.assertEquals(serialManagerPage.getAddEditSerialManagerPageTitle(),
                                value(SerialManagerData, "Expected Message or Title"));
                Assert.assertEquals(serialManagerPage.getSaveButtonStatus(), value(SerialManagerData, "Button Status"));
        }

        @Test(priority = 3, description = "Verify the functionality of the Add JEKSON Serial Manager.")
        public void verifyAddSNMAnagerFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"), value(SerialManagerData, "SSCC Pool 1"),
                                value(SerialManagerData, "SSCC Pool 2"),
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                value(SerialManagerData, "Partial SSCC Pool 2"));
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verify the functionality of the Add JEKSON Serial Manager.")
        public void verifyAddSNMAnager1Functionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                value(SerialManagerData, "SSCC Pool 2"),
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                value(SerialManagerData, "Partial SSCC Pool 2"));
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 5, description = "Verify the functionality of the Add Serial Manager with same Full and Partial")
        public void verifyAddSNsamefullandpartialFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_05");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManagersamefullandpartial("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"), value(SerialManagerData, "SSCC Pool 1"),
                                value(SerialManagerData, "SSCC Pool 2"),
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                value(SerialManagerData, "Partial SSCC Pool 2"));
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 6, description = "Verify the functionality of the Edit User Button.")
        public void verifyEditSNMAnager1Functionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.editSNmanager(value(SerialManagerData, "Name"));
                Assert.assertEquals(serialManagerPage.getAddEditSerialManagerPageTitle(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 7, description = "Verify that the Serial code type and Serial Code length field is disabled.")
        public void verifysntypeandlengthFieldIsDisabled() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.editSNmanager(value(SerialManagerData, "Name"));
                Assert.assertEquals(serialManagerPage.getsntypeFieldStatus(),
                                value(SerialManagerData, "Button Status"));
                Assert.assertEquals(serialManagerPage.getsnlengthFieldStatus(),
                                value(SerialManagerData, "Button Status"));
        }

        @Test(priority = 8, description = "Verify the functionality of updating an existing SN.")
        public void verifyUpdateExistingUserFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_08");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.editSNmanager(value(SerialManagerData, "Existing Name"));
                serialManagerPage.UpdateSerialManager("Update", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                value(SerialManagerData, "Partial SSCC Pool 1"));
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 9, description = "Verify the functionality of deactivating a SN Manager.")
        public void verifyDeactivateUserFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_09");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                String ConfirmationMessage = serialManagerPage.serialmanagerActiveDeactiveFunctionality("Deactivate",
                                value(SerialManagerData, "Name"));
                Assert.assertEquals(ConfirmationMessage, value(SerialManagerData, "Confirmation Message"));
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 10, description = "Verify the functionality of activating a SN Manager.")
        public void verifyActivateUserFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_10");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                String ConfirmationMessage = serialManagerPage.serialmanagerActiveDeactiveFunctionality("Activate",
                                value(SerialManagerData, "Name"));
                Assert.assertEquals(ConfirmationMessage, value(SerialManagerData, "Confirmation Message"));
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 11, description = "Verify the functionality of the Add duplicate Serial Manager.")
        public void verifyAddduplicate1Functionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_11");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"), value(SerialManagerData, "SSCC Pool 1"),
                                value(SerialManagerData, "SSCC Pool 2"),
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                value(SerialManagerData, "Partial SSCC Pool 2"));
                // Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 12, description = "Verify the functionality of the Refresh Serial Manager.")
        public void verifyrefresh() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_12");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                // Thread.sleep(2000);
                String TempMessage = serialManagerPage.refreshButton(value(SerialManagerData, "Name"));
                Assert.assertEquals(TempMessage, value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 13, description = "Verify the functionality of the Add TRACELINK Serial Manager.")
        public void verifyAddTracelinkSerialManagerFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_13");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                null,
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                null);
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 14, description = "Verify the functionality of the Add REESOURCE Serial Manager.")
        public void verifyAddResourceSerialManagerFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_14");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                null,
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                null);
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 15, description = "Verify the functionality of the Add ATTP Serial Manager.")
        public void verifyAddAttpSerialManagerFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_15");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                null,
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                null);
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

        @Test(priority = 16, description = "Verify the functionality of the Add RFXCEL Serial Manager.")
        public void verifyAddRfxcelSerialManagerFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> SerialManagerData = pageData(SERIAL_MANAGER_SHEET, "Serial-Master_TC_16");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
                SerialManagerPage serialManagerPage = new SerialManagerPage(driver);
                serialManagerPage.openSerialManager();
                serialManagerPage.AddSerialManager("Add", value(SerialManagerData, "Name"),
                                value(SerialManagerData, "Serial Code Type"),
                                value(SerialManagerData, "Serial Code Length"),
                                value(SerialManagerData, "Serial Master"),
                                value(SerialManagerData, "SSCC Pool 1"),
                                null,
                                value(SerialManagerData, "Partial SSCC Pool 1"),
                                null);
                Thread.sleep(2000);
                Assert.assertEquals(serialManagerPage.getNotificationMessage(),
                                value(SerialManagerData, "Expected Message or Title"));
        }

}
