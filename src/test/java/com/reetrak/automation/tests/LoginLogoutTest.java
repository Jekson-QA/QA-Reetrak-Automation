package com.reetrak.automation.tests;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class LoginLogoutTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";

    @Test(priority = 1, description = "Verification of Login when username and password are blank.")
    public void loginWithBlankUsernameAndPassword() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_01");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username", ""), value(loginData, "password", ""));

        Assert.assertEquals(loginPage.getUsernameTitle(), value(loginData, "Expected Validation-1"),
                "User should remain on the login page when username and password are blank.");

        Assert.assertEquals(loginPage.getPasswordTitle(), value(loginData, "Expected Validation-2"),
                "User should remain on the login page when username and password are blank.");
    }

    @Test(priority = 2, description = "Verification of Login when username is invalid and password is valid.")
    public void loginWithInvalidUsernameAndValidPassword() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_02");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        Assert.assertEquals(loginPage.getNotificationMessage(), value(loginData, "Expected Message or Title"),
                "User should remain on the login page when username is invalid.");
    }

    @Test(priority = 3, description = "Verification of Login when username is valid and password is invalid.")
    public void loginWithValidUsernameAndInvalidPassword() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_03");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        Assert.assertEquals(loginPage.getNotificationMessage(), value(loginData, "Expected Message or Title"));
    }

    @Test(priority = 4, description = "Verification of Login when username is valid and password is valid.")
    public void loginWithvalidUsernameAndPassword() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        Assert.assertEquals(loginPage.getDashbaordTitle(), value(loginData, "Expected Message or Title"),
                "User should be navigated to the products page after login.");
    }

    @Test(priority = 5, description = "Verification of Logout functionality.")
    public void logoutverification() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_05");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        loginPage.logoutAs();

        Assert.assertEquals(loginPage.getLoginTitle(), value(loginData, "Expected Message or Title"),
                "User should be navigated to the products page after login.");
    }

    @Test(priority = 6, description = "Verification of user lock upon invalid password more than 3 times.")
    public void verifyUserGetLockedUponInvalidPassword() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_06");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);

        int y = Integer.parseInt(value(loginData, "User Lockout Attempts Count"));

        for (int x = 0; x < y; x++) {
            loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
            int z = (y - x) - 1;
            // String actualText = loginPage.getLockedusernameTitle();
            String expectedText = "Incorrect password, Remaining attempt left: " + z;
            if (z > 0) {

                System.out.println(expectedText);
                Assert.assertTrue(loginPage.getNotificationMessage().contains(expectedText),
                        "User should remain on the login page when password is invalid.");

            } else {
                System.out.println(loginPage.getNotificationMessage());
                Assert.assertEquals(loginPage.getNotificationMessage(), value(loginData, "Expected Message or Title"),
                        "User should remain on the login page when password is invalid.");
            }

        }
    }

    @Test(priority = 7, description = "Login verification when user is deactivated")
    public void deactiveUserVerification() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_07");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        Assert.assertEquals(loginPage.getNotificationMessage(), value(loginData, "Expected Message or Title"),
                "User should remain on the login page when the account is deactivated.");
    }

    @Test(priority = 8, description = "Verification of Login when user is Locked.")
    public void lockedUserVerification() throws InterruptedException {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_08");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        Assert.assertEquals(loginPage.getNotificationMessage(), value(loginData, "Expected Message or Title"),
                "User should remain on the login page when the account is deactivated.");
    }

}