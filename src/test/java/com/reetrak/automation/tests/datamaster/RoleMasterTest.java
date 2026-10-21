package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.RoleMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class RoleMasterTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String ROLE_SHEET = "testdata.role.sheet";

    @Test(priority = 1, description = "Verification of Role Master page opening successfully.")
    public void verifyRoleMasterMenuOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_01");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        Assert.assertEquals(roleMasterPage.getRoleMasterPageName(), value(roledata, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "Verification of ADD button functionality.")
    public void verifyAddRoleButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_02");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.addRole();
        Assert.assertEquals(roleMasterPage.getaddEditNewRolePageTitle(), value(roledata, "Expected Message or Title"));
    }

    @Test(priority = 3, description = "Verification of Add a new 'Plant Admin' role functionality by 'Assigned All' button.")
    public void verifyAddRoleUsingAssignAllOption() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_03");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.addRoleByAssignedAllButton(value(roledata, "Role Name"), value(roledata, "Hierarchy Order"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
        String RoleStatus = roleMasterPage.getRoleStatus(value(roledata, "Role Name"));
        Assert.assertTrue(RoleStatus.equalsIgnoreCase(value(roledata, "Role Status")),
                "Expected role status to match (case-insensitive). Found: " + RoleStatus);
    }

    @Test(priority = 4, description = "Verification of Add a new role functionality by Assigning individual rights.")
    public void verifyAddRoleWithIndividualRights() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_04");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.addNewRoleForCustomRights(value(roledata, "Role Name"), value(roledata, "Hierarchy Order"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
        String RoleStatus = roleMasterPage.getRoleStatus(value(roledata, "Role Name"));
        Assert.assertTrue(RoleStatus.equalsIgnoreCase(value(roledata, "Role Status")),
                "Expected role status to match (case-insensitive). Found: " + RoleStatus);

    }

    @Test(priority = 5, description = "Verification of EDIT button functionality.")
    public void verifyEditRoleButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_05");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        String EditRolePageTitle = roleMasterPage.editRole(value(roledata, "Role Name"));
        Assert.assertEquals(EditRolePageTitle, value(roledata, "Expected Message or Title"));
    }

    @Test(priority = 6, description = "Verification of Updating a role functionality by assigning and unassigning individual rights.")
    public void verifyUpdateRoleWithAssignedAndUnassignedRights() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_06");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.updateRoleForCustomRights(value(roledata, "Existing Role Name"), value(roledata, "Role Name"),
                value(roledata, "Hierarchy Order"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
    }

    @Test(priority = 7, description = "Verification of Validation when attempting to update a role with invalid or incomplete data.")
    public void verifyValidationOnInvalidRoleUpdate() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_07");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.updateRoleByUnAssignedAllButton(value(roledata, "Role Name"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
    }

    @Test(priority = 8, description = "Verification of Role Deactivation functionality.")
    public void verifyDeactivateRoleFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_08");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        String activeConfirmationMessage = roleMasterPage.deactiveRole(value(roledata, "Role Name"));
        Assert.assertEquals(activeConfirmationMessage, value(roledata, "Confirmation Message"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
        roleMasterPage.clickOnPageTitle();
        String roleStatus = roleMasterPage.getRoleStatus(value(roledata, "Role Name"));
        Assert.assertTrue(roleStatus.equalsIgnoreCase(value(roledata, "Role Status")),
                "Expected role status to match (case-insensitive). Found: " + roleStatus);
    }

    @Test(priority = 9, description = "Verification of Role Activation functionality.")
    public void verifyActivateRoleFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_09");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        String activeConfirmationMessage = roleMasterPage.activeRole(value(roledata, "Role Name"));
        Assert.assertEquals(activeConfirmationMessage, value(roledata, "Confirmation Message"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
        roleMasterPage.clickOnPageTitle();
        String roleStatus = roleMasterPage.getRoleStatus(value(roledata, "Role Name"));
        Assert.assertTrue(roleStatus.equalsIgnoreCase(value(roledata, "Role Status")),
                "Expected role status to match (case-insensitive). Found: " + roleStatus);
    }

    @Test(priority = 10, description = "Verification of Add a new 'QA Admin' role functionality by 'Assigned All' button.")
    public void verifyAddRoleUsingAssignAllOption1() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_10");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.addRoleByAssignedAllButton(value(roledata, "Role Name"), value(roledata, "Hierarchy Order"));
        Assert.assertEquals(roleMasterPage.getNotificationMessage(), value(roledata, "Expected Message or Title"));
        String RoleStatus = roleMasterPage.getRoleStatus(value(roledata, "Role Name"));
        Assert.assertTrue(RoleStatus.equalsIgnoreCase(value(roledata, "Role Status")),
                "Expected role status to match (case-insensitive). Found: " + RoleStatus);
    }

    @Test(priority = 11, description = "Verification of REFRESH button functionality.")
    public void verifyRefreshButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> roledata = pageData(ROLE_SHEET, "Role-Master_TC_11");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RoleMasterPage roleMasterPage = new RoleMasterPage(driver);
        roleMasterPage.openRoleMaster();
        roleMasterPage.selectExistingRole(value(roledata, "Role Name"));
        Assert.assertEquals(roleMasterPage.refreshButton(), value(roledata, "Expected Message or Title"));
    }

}
