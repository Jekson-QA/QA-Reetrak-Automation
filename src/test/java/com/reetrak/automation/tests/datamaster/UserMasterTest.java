package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.UserMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class UserMasterTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String USER_MASTER_SHEET = "testdata.UserMaster.sheet";

    @Test(priority = 1, description = "Verification of User Master page opening successfully.")
    public void verifyUserMasterMenuOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_01");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        Assert.assertEquals(userMasterPage.getUserMasterPageName(), value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "Verification of ADD button functionality.")
    public void verifyAddUserButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_02");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.addUser();
        Assert.assertTrue(userMasterPage.getAddButtonStatus().equalsIgnoreCase(value(UserMasterData, "Button Status")),
                "Expected Add button status to match (case-insensitive)");
        Assert.assertEquals(userMasterPage.getAddEditUserPageTitle(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 3, description = "Verification of Add New User functionality.")
    public void verifyAddUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_03");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.AddorUpdateUser("Add", value(UserMasterData, "User Name"),
                value(UserMasterData, "First Name"), value(UserMasterData, "Last Name"),
                value(UserMasterData, "Role"), value(UserMasterData, "Designation"),
                value(UserMasterData, "Email"), value(UserMasterData, "Phone"));
        String Username = userMasterPage.getUserName();
        String Token = userMasterPage.getToken();
        userMasterPage.clickOnDownloadButton();
        String UserStatus = userMasterPage.getUserStatus(value(UserMasterData, "User Name"));
        Assert.assertTrue(UserStatus.equalsIgnoreCase(value(UserMasterData, "User Status")),
                "Expected user status to match (case-insensitive). Found: " + UserStatus);
        loginPage.logoutAs();
        loginPage.loginAs(Username, Token);
        loginPage.setPassword(Token, "password", "password");
        loginPage.loginAs(Username, "password");
        Assert.assertEquals(loginPage.getDashbaordTitle(), value(UserMasterData, "Expected Message or Title"));
        // loginPage.logoutAs();
    }

    @Test(priority = 4, description = "Verification of EDIT button functionality.")
    public void verifyEditUserButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_04");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.editUser(value(UserMasterData, "User Name"));
        Assert.assertEquals(userMasterPage.getAddEditUserPageTitle(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 5, description = "Verification of the 'User Name' field.")
    public void verifyUserNameFieldIsDisabled() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_05");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.editUser(value(UserMasterData, "User Name"));
        Assert.assertTrue(
                userMasterPage.getUsernameFieldStatus().equalsIgnoreCase(value(UserMasterData, "Button Status")),
                "Expected Username field status to match (case-insensitive)");
    }

    @Test(priority = 6, description = "Verification of validation when the user is already logged in and an attempt to re-generate the token for the same user.")
    public void validateTokenRegenerationForLoggedInUser() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_06");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.clickOnRegenerateKeyButton(value(UserMasterData, "User Name"));
        String FinalMessage = userMasterPage.getNotificationMessage().replaceAll("\\(.*?\\)", "").trim();
        Assert.assertEquals(FinalMessage, value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 7, description = "Verification of 'Regenerate Key' functionality.")
    public void verifyRegenerateKeyFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_07");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs("Padmin", "password");
        loginPage.logoutAs();
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        String ConfirmationMessage = userMasterPage.regenerateKeyFunctionality(value(UserMasterData, "User Name"));
        Assert.assertEquals(ConfirmationMessage, value(UserMasterData, "Confirmation Message"));
        String Username = userMasterPage.getUserName();
        String Token = userMasterPage.getToken();
        userMasterPage.clickOnDownloadButton();
        loginPage.logoutAs();
        loginPage.loginAs(Username, Token);
        loginPage.setPassword(Token, "password1", "password1");
        loginPage.loginAs(Username, "password1");
        Assert.assertEquals(loginPage.getDashbaordTitle(), value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 8, description = "Verification of 'Forced Logout' functionality.")
    public void verifyForcedLogoutFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_08");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);

        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        String ConfirmationMessage = userMasterPage.forcedLogoutFunctionality(value(UserMasterData, "User Name"));
        Assert.assertEquals(ConfirmationMessage, value(UserMasterData, "Confirmation Message"));
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 9, description = "Verification of validation when attempting to add a duplicate user.")
    public void verifyValidationMessageOnDuplicateUserCreation() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_09");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.AddorUpdateUser("Add", value(UserMasterData, "User Name"),
                value(UserMasterData, "First Name"), value(UserMasterData, "Last Name"),
                value(UserMasterData, "Role"), value(UserMasterData, "Designation"),
                value(UserMasterData, "Email"), value(UserMasterData, "Phone"));
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 10, description = "Verification of Updating an existing user functionality.")
    public void verifyUpdateExistingUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_10");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.AddorUpdateUser("Update", value(UserMasterData, "User Name"),
                value(UserMasterData, "First Name"), value(UserMasterData, "Last Name"),
                value(UserMasterData, "Role"), value(UserMasterData, "Designation"),
                value(UserMasterData, "Email"), value(UserMasterData, "Phone"));
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 11, description = "Verification of 'Change Password' functionality.")
    public void verifyChangePasswordFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_11");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.changePasswordFunctionality(value(UserMasterData, "User Name"), "Password@8");
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 12, description = "Verification of User Deactivation functionality.")
    public void verifyDeactivateUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_12");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        String ConfirmationMessage = userMasterPage.userActiveDeactiveFunctionality("Deactivate",
                value(UserMasterData, "User Name"));
        Assert.assertEquals(ConfirmationMessage, value(UserMasterData, "Confirmation Message"));
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 13, description = "Verification of User Activation functionality.")
    public void verifyActivateUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_13");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        String ConfirmationMessage = userMasterPage.userActiveDeactiveFunctionality("Activate",
                value(UserMasterData, "User Name"));
        Assert.assertEquals(ConfirmationMessage, value(UserMasterData, "Confirmation Message"));
        Assert.assertEquals(userMasterPage.getNotificationMessage(),
                value(UserMasterData, "Expected Message or Title"));
    }

    @Test(priority = 14, description = "Verification of Add New QA User functionality.")
    public void verifyAddQAUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_14");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.AddorUpdateUser("Add", value(UserMasterData, "User Name"),
                value(UserMasterData, "First Name"), value(UserMasterData, "Last Name"),
                value(UserMasterData, "Role"), value(UserMasterData, "Designation"),
                value(UserMasterData, "Email"), value(UserMasterData, "Phone"));
        String Username = userMasterPage.getUserName();
        String Token = userMasterPage.getToken();
        userMasterPage.clickOnDownloadButton();
        String UserStatus = userMasterPage.getUserStatus(value(UserMasterData, "User Name"));
        Assert.assertTrue(UserStatus.equalsIgnoreCase(value(UserMasterData, "User Status")),
                "Expected user status to match (case-insensitive). Found: " + UserStatus);
        loginPage.logoutAs();
        loginPage.loginAs(Username, Token);
        loginPage.setPassword(Token, "password", "password");
        loginPage.loginAs(Username, "password");
        Assert.assertEquals(loginPage.getDashbaordTitle(), value(UserMasterData, "Expected Message or Title"));
        loginPage.logoutAs();
    }

    @Test(priority = 15, description = "Verification of Add New End-User User functionality.")
    public void verifyAddEndUserUserFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_15");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.AddorUpdateUser("Add", value(UserMasterData, "User Name"),
                value(UserMasterData, "First Name"), value(UserMasterData, "Last Name"),
                value(UserMasterData, "Role"), value(UserMasterData, "Designation"),
                value(UserMasterData, "Email"), value(UserMasterData, "Phone"));
        String Username = userMasterPage.getUserName();
        String Token = userMasterPage.getToken();
        userMasterPage.clickOnDownloadButton();
        String UserStatus = userMasterPage.getUserStatus(value(UserMasterData, "User Name"));
        Assert.assertTrue(UserStatus.equalsIgnoreCase(value(UserMasterData, "User Status")),
                "Expected user status to match (case-insensitive). Found: " + UserStatus);
        loginPage.logoutAs();
        loginPage.loginAs(Username, Token);
        loginPage.setPassword(Token, "password", "password");
        loginPage.loginAs(Username, "password");
        Assert.assertEquals(loginPage.getDashbaordTitle(), value(UserMasterData, "Expected Message or Title"));
        loginPage.logoutAs();
    }

    @Test(priority = 16, description = "Verification of REFRESH button functionality.")
    public void verifyRefreshButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> UserMasterData = pageData(USER_MASTER_SHEET, "User-Master_TC_16");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        UserMasterPage userMasterPage = new UserMasterPage(driver);
        userMasterPage.openUserMaster();
        userMasterPage.selectExisingUser(value(UserMasterData, "User Name"));
        Assert.assertEquals(userMasterPage.refreshButton(),
                value(UserMasterData, "Expected Message or Title"));
    }

}
