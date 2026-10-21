package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.HierarchyMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class HierarchyMasterTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String HIERARCHY_SHEET = "testdata.hierarchy.sheet";

        @org.testng.annotations.BeforeClass(alwaysRun = true)
        public void checkHierarchyConfig() {
                boolean isHierarchyEnabled = Boolean.parseBoolean(
                                com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
                if (!isHierarchyEnabled) {
                        throw new org.testng.SkipException(
                                        "Skipping HierarchyMasterTest because Hierarchy Master is disabled in config.");
                }
        }

        @Test(priority = 1, description = "Verification of Hierarchy Master page opening successfully.")
        public void verifyHierarchyMasterMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_01");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                Assert.assertEquals(
                                hierarchyMasterPage.getHierarchyMasterPageName(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of ADD button functionality.")
        public void verifyAddButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_02");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addHierarchyButton();
                Assert.assertEquals(hierarchyMasterPage.getAddEditHierarchyPageTitle(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of SAVE button functionality without entering hierarchy details.")
        public void verifySavebuttonWithEmptyHierarchyInfo() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_03");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addHierarchyButton();
                Assert.assertEquals(hierarchyMasterPage.savebuttonstatus(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verification of Adding a new Role-Based Hierarchy for Products.")
        public void verifyAddHierarchyFunctionality1() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_04");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addNewHierarchy(value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 5, description = "Verification of Adding a new Role-Based Hierarchy for Batches.")
        public void verifyAddHierarchyFunctionality2() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_05");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addNewHierarchy(value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 6, description = "Verification of Adding a new User-Based Hierarchy for Products.")
        public void verifyAddHierarchyFunctionality3() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_06");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addNewHierarchy(value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 7, description = "Verification of Adding a new User-Based Hierarchy for Batches.")
        public void verifyAddHierarchyFunctionality4() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_07");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addNewHierarchy(value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 8, description = "Verification of validation when adding duplicate hierarchy.")
        public void verifyValidationWhenAddingDuplicateHierarchy() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_08");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.addNewHierarchy(value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 9, description = "Verification of EDIT button functionality.")
        public void verifyEditButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_09");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.selectHierarchy(value(hierarchyData, "Existing Hierarchy Name"));
                hierarchyMasterPage.editHierarchyButton();
                Assert.assertEquals(hierarchyMasterPage.getAddEditHierarchyPageTitle(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 10, description = "Verification of Update Hierarchy functionality.")
        public void verifyUpdateHierarchyFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_10");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.updateHierarchy(value(hierarchyData, "Existing Hierarchy Name"),
                                value(hierarchyData, "Hierarchy Name"),
                                value(hierarchyData, "Hierarchy For"),
                                value(hierarchyData, "Hierarchy Type"), value(hierarchyData, "Creator"),
                                value(hierarchyData, "Reviewer"), value(hierarchyData, "Approver"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 11, description = "Verification of Hierarchy Deactivation functionality.")
        public void verifyDeactivateHierarchyFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_11");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                String TempMessage = hierarchyMasterPage.hierarchyActiveDeactiveFunctionality("Deactivate",
                                value(hierarchyData, "Existing Hierarchy Name"));
                Assert.assertEquals(TempMessage, value(hierarchyData, "Confirmation Message"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 12, description = "Verification of Hierarchy Activation functionality.")
        public void verifyActivateHierarchyFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_12");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                String TempMessage = hierarchyMasterPage.hierarchyActiveDeactiveFunctionality("Activate",
                                value(hierarchyData, "Existing Hierarchy Name"));
                Assert.assertEquals(TempMessage, value(hierarchyData, "Confirmation Message"));
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 13, description = "Verification of Reset OR Remove the Reviewer functionality.")
        public void verifyResetFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_13");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                String TempMessage = hierarchyMasterPage.resetFunctionality(
                                value(hierarchyData, "Existing Hierarchy Name"));
                Assert.assertEquals(TempMessage, "Reset Successfully.");
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 14, description = "Verification of REFRESH button functionality.")
        public void verifyRefreshButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_14");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                hierarchyMasterPage.selectHierarchy(value(hierarchyData, "Existing Hierarchy Name"));
                Assert.assertEquals(hierarchyMasterPage.refreshButton(),
                                value(hierarchyData, "Expected Message or Title"));
        }

        @Test(priority = 15, description = "Verification of Reset OR Remove the Reviewer functionality.")
        public void verifyResetFunctionality1() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> hierarchyData = pageData(HIERARCHY_SHEET, "Hierarchy-Master_TC_15");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                HierarchyMasterPage hierarchyMasterPage = new HierarchyMasterPage(driver);
                hierarchyMasterPage.openHierarchyMaster();

                String TempMessage = hierarchyMasterPage.resetFunctionality(
                                value(hierarchyData, "Existing Hierarchy Name"));
                Assert.assertEquals(TempMessage, "Reset Successfully.");
                Assert.assertEquals(hierarchyMasterPage.getNotificationMessage(),
                                value(hierarchyData, "Expected Message or Title"));
        }

}
