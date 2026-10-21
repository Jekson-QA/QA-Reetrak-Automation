package com.reetrak.automation.tests.reetrakadmin;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.reetrakadmin.reetrakadminPage;

public class reetrakadminTest extends BaseTest {

        private static final String REETRAK_ADMIN_SHEET = "testdata.reetrakadmin.sheet";

        @Test(priority = 1, description = "Verification of Add Company Details functionality.")
        public void verifyAddCompanyFunctionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.addCompany(
                                value(reetrakAdminData, "Name/Username"),
                                value(reetrakAdminData, "Prefix/FirstName"),
                                value(reetrakAdminData, "SN Length/LastName"),
                                value(reetrakAdminData, "URL/Designation"),
                                value(reetrakAdminData, "LogoPath/Email"),
                                value(reetrakAdminData, "Address1"),
                                value(reetrakAdminData, "Address2"),
                                value(reetrakAdminData, "Pin Code"),
                                value(reetrakAdminData, "City"),
                                value(reetrakAdminData, "State"),
                                value(reetrakAdminData, "Country"));
                Assert.assertEquals(reetrakadminPage.getNotificationMessage(),
                                value(reetrakAdminData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of Add License Details functionality.")
        public void verifyAddLicenseFunctionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.addLicense(
                                value(reetrakAdminData, "LicenseKey/Phone"));
                Assert.assertEquals(reetrakadminPage.getNotificationMessage(),
                                value(reetrakAdminData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of Server Restart functionality.")
        public void verifyServerRestartFunctionality() throws Exception {
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                // Call restartService after the assertions of Test Case 2
                // reetrakadminPage.restartService("Apache Tomcat 8.5 Tomcat8_Reetrak");
                reetrakadminPage.restartService("Apache Tomcat 8.5 Tomcat8");
                loginPage.open(baseUrl);
                Assert.assertEquals(loginPage.getLoginTitle(), "LOG IN",
                                "User should be redirected to the Login page.");
        }

        @Test(priority = 4, description = "Verification of Update Role functionality.")
        public void verifyUpdateRoleByAssignedAllButtonFunctionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.updateRoleByAssignedAllButton();
                Assert.assertEquals(reetrakadminPage.getNotificationMessage(),
                                value(reetrakAdminData, "Expected Message or Title"));
        }

        @Test(priority = 5, description = "Verification of Add User-1 functionality.")
        public void verifyAddUser1Functionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_05");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.adduser(
                                value(reetrakAdminData, "Name/Username"),
                                value(reetrakAdminData, "Prefix/FirstName"),
                                value(reetrakAdminData, "SN Length/LastName"),
                                value(reetrakAdminData, "URL/Designation"),
                                value(reetrakAdminData, "LogoPath/Email"),
                                value(reetrakAdminData, "LicenseKey/Phone"));
                String Username = reetrakadminPage.getUserName();
                String Token = reetrakadminPage.getToken();
                reetrakadminPage.clickOnDownloadButton();
                loginPage.logoutAs();
                loginPage.loginAs(Username, Token);
                loginPage.setPassword(Token, "password", "password");
                loginPage.loginAs(Username, "password");
                Assert.assertEquals(loginPage.getDashbaordTitle(),
                                value(reetrakAdminData, "Expected Message or Title"));
                loginPage.logoutAs();
        }

        @Test(priority = 6, description = "Verification of Add User-2 functionality.")
        public void verifyAddUser2Functionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.adduser(
                                value(reetrakAdminData, "Name/Username"),
                                value(reetrakAdminData, "Prefix/FirstName"),
                                value(reetrakAdminData, "SN Length/LastName"),
                                value(reetrakAdminData, "URL/Designation"),
                                value(reetrakAdminData, "LogoPath/Email"),
                                value(reetrakAdminData, "LicenseKey/Phone"));
                String Username = reetrakadminPage.getUserName();
                String Token = reetrakadminPage.getToken();
                reetrakadminPage.clickOnDownloadButton();
                loginPage.logoutAs();
                loginPage.loginAs(Username, Token);
                loginPage.setPassword(Token, "password", "password");
                loginPage.loginAs(Username, "password");
                Assert.assertEquals(loginPage.getDashbaordTitle(),
                                value(reetrakAdminData, "Expected Message or Title"));
                loginPage.logoutAs();
        }

        @Test(priority = 7, description = "Verification of Add User-3 functionality.")
        public void verifyAddUser3Functionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.adduser(
                                value(reetrakAdminData, "Name/Username"),
                                value(reetrakAdminData, "Prefix/FirstName"),
                                value(reetrakAdminData, "SN Length/LastName"),
                                value(reetrakAdminData, "URL/Designation"),
                                value(reetrakAdminData, "LogoPath/Email"),
                                value(reetrakAdminData, "LicenseKey/Phone"));
                String Username = reetrakadminPage.getUserName();
                String Token = reetrakadminPage.getToken();
                reetrakadminPage.clickOnDownloadButton();
                loginPage.logoutAs();
                loginPage.loginAs(Username, Token);
                loginPage.setPassword(Token, "password", "password");
                loginPage.loginAs(Username, "password");
                Assert.assertEquals(loginPage.getDashbaordTitle(),
                                value(reetrakAdminData, "Expected Message or Title"));
                loginPage.logoutAs();
        }

        @Test(priority = 8, description = "Verification of Deactivate User functionality.")
        public void verifyDeactivateUserFunctionality() throws Exception {
                Map<String, String> reetrakAdminData = pageData(REETRAK_ADMIN_SHEET, "ReetrakAdmin_TC_08");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs("Reetrakadmin", "password");
                reetrakadminPage reetrakadminPage = new reetrakadminPage(driver);
                reetrakadminPage.userDeactiveFunctionality(
                                value(reetrakAdminData, "Name/Username"));
        }

}
