package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.LineMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class LineMasterTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String LINE_MASTER_SHEET = "testdata.line.sheet";

        @Test(priority = 1, description = "Verification of Line Master page opening successfully.")
        public void verifyLineMasterMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                Assert.assertEquals(lineMasterPage.getLineMasterPageName(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of ADD button functionality.")
        public void verifyAddButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.clickAddLine();
                Assert.assertEquals(lineMasterPage.getAddLineInformationTitle(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of Add New Line functionality.")
        public void verifyAddNewLineFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name", ""),
                                value(lineMasterData, null, ""),
                                value(lineMasterData, "Level 2", ""),
                                value(lineMasterData, "Level 3", ""),
                                value(lineMasterData, "Level 4", ""),
                                value(lineMasterData, null, ""),
                                value(lineMasterData, "Level 6", ""));
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verification of EDIT button functionality.")
        public void verifyEditButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.openLatestLineForEdit(value(lineMasterData, "Existing Line Name"));
                Assert.assertEquals(lineMasterPage.getUpdateLineInformationTitle(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 5, description = "Verification of Update Line functionality.")
        public void verifyUpdateLineFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_05");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.openLatestLineForEdit(value(lineMasterData, "Existing Line Name"));
                lineMasterPage.updateLineDetails(
                                value(lineMasterData, "New Line Name", ""),
                                value(lineMasterData, "Level 2", ""),
                                value(lineMasterData, "Level 3", ""),
                                value(lineMasterData, "Level 4", ""),
                                value(lineMasterData, "Level 6", ""));
                lineMasterPage.clickSaveForUpdatedLine();
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title", ""));
        }

        @Test(priority = 6, description = "Verification of validation when adding duplicate line.")
        public void verifyDuplicateLineValidation() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name", ""),
                                value(lineMasterData, null, ""),
                                value(lineMasterData, "Level 2", ""),
                                value(lineMasterData, "Level 3", ""),
                                value(lineMasterData, "Level 4", ""),
                                value(lineMasterData, null, ""),
                                value(lineMasterData, "Level 6", ""));
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title", ""));
        }

        @Test(priority = 7, description = "Verification of Line Deactivation functionality.")
        public void verifyDeactivateLineFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                String TempStatus = lineMasterPage.lineActiveDeactiveFunctionality("Deactivate",
                                value(lineMasterData, "Existing Line Name"));
                Assert.assertEquals(TempStatus, value(lineMasterData, "Confirmation Message"));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 8, description = "Verification of Line Activation functionality.")
        public void verifyActivateLineFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_08");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                String TempStatus = lineMasterPage.lineActiveDeactiveFunctionality("Activate",
                                value(lineMasterData, "Existing Line Name"));
                Assert.assertEquals(TempStatus, value(lineMasterData, "Confirmation Message"));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 9, description = "Verification of REFRESH button functionality.")
        public void verificationofRefreshButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_09");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.selectLatestLine(value(lineMasterData, "Existing Line Name"));
                Assert.assertEquals(lineMasterPage.refreshButton(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 10, description = "Verification of Add New Line functionality for 1-2 Level.")
        public void verifyAddNewLineFunctionalityFor12Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_10");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                value(lineMasterData, "Level 1"),
                                value(lineMasterData, "Level 2"),
                                null,
                                null,
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 11, description = "Verification of Add New Line functionality for 1-2-3 Level.")
        public void verifyAddNewLineFunctionalityFor123Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_11");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                value(lineMasterData, "Level 1"),
                                value(lineMasterData, "Level 2"),
                                value(lineMasterData, "Level 3"),
                                null,
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 12, description = "Verification of Add New Line functionality for 1-2-6 Level.")
        public void verifyAddNewLineFunctionalityFor126Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_12");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                value(lineMasterData, "Level 1"),
                                value(lineMasterData, "Level 2"),
                                null,
                                null,
                                null,
                                value(lineMasterData, "Level 6"));
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 13, description = "Verification of Add New Line functionality for 1-2-3-6 Level.")
        public void verifyAddNewLineFunctionalityFor1236Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_13");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                value(lineMasterData, "Level 1"),
                                value(lineMasterData, "Level 2"),
                                value(lineMasterData, "Level 3"),
                                null,
                                null,
                                value(lineMasterData, "Level 6"));
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 14, description = "Verification of Add New Line functionality for 2 Level.")
        public void verifyAddNewLineFunctionalityFor2Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_14");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                null,
                                value(lineMasterData, "Level 2"),
                                null,
                                null,
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 15, description = "Verification of Add New Line functionality for 2-3 Level.")
        public void verifyAddNewLineFunctionalityFor23Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_15");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                null,
                                value(lineMasterData, "Level 2"),
                                value(lineMasterData, "Level 3"),
                                null,
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 16, description = "Verification of Add New Line functionality for 2-3-6 Level.")
        public void verifyAddNewLineFunctionalityFor236Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_16");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                null,
                                value(lineMasterData, "Level 2"),
                                value(lineMasterData, "Level 3"),
                                null,
                                null,
                                value(lineMasterData, "Level 6"));
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 17, description = "Verification of Add New Line functionality for 3 Level.")
        public void verifyAddNewLineFunctionalityFor3Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_17");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                null,
                                null,
                                value(lineMasterData, "Level 3"),
                                null,
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

        @Test(priority = 18, description = "Verification of Add New Line functionality for 2-3-4 Level.")
        public void verifyAddNewLineFunctionalityFor234Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> lineMasterData = pageData(LINE_MASTER_SHEET, "Line-Master_TC_18");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                LineMasterPage lineMasterPage = new LineMasterPage(driver);
                lineMasterPage.openLineMaster();
                lineMasterPage.addLineDetails(
                                value(lineMasterData, "New Line Name"),
                                null,
                                value(lineMasterData, "Level 2"),
                                value(lineMasterData, "Level 3"),
                                value(lineMasterData, "Level 4"),
                                null,
                                null);
                lineMasterPage.clickSaveForNewLine(value(lineMasterData, "New Line Name", ""));
                Assert.assertEquals(lineMasterPage.getNotificationMessage(),
                                value(lineMasterData, "Expected Message or Title"));
        }

}
