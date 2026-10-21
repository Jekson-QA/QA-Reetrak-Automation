package com.reetrak.automation.tests.serialmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.serialmaster.SSCCPoolPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SSCCPoolTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String SSCC_POOL_SHEET = "testdata.ssccpool.sheet";

    @Test(priority = 1, description = "Verification of SSCC Pool page opening successfully.")
    public void verifySSCCPoolMenuOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        Assert.assertEquals(
                ssccPoolPage.getSSCCPoolPageName(),
                value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "Verification of ADD button functionality.")
    public void verifyAddButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_02");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        String TempPageTitle = ssccPoolPage.getAddSSCCPoolTitle();
        Assert.assertEquals(TempPageTitle, value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 3, description = "Verification of Add new SSCC Pool functionality.")
    public void verifyAddSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_03");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 4, description = "Verification of Update existing SSCC Pool functionality.")
    public void verifyUpdateSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_04");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.UpdateSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"),
                value(ssccPoolData, "Existing Serial Manager"), value(ssccPoolData, "Existing Prefix"),
                value(ssccPoolData, "Existing Ext Digit"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 5, description = "Verification of Deactivate SSCC Pool functionality.")
    public void verifyDeactivateSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_05");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        // String deactiveConfirmationMessage =
        // ssccPoolPage.deactiveSSCCPool(value(ssccPoolData, "Existing Serial
        // Manager"),value(ssccPoolData, "Existing Prefix"), value(ssccPoolData,
        // "Existing Ext Digit"));
        String deactiveConfirmationMessage = ssccPoolPage.deactiveSSCCPool(
                value(ssccPoolData, "Existing Serial Manager"), value(ssccPoolData, "Existing Ext Digit"),
                value(ssccPoolData, "Existing Prefix"));
        Assert.assertEquals(deactiveConfirmationMessage, value(ssccPoolData, "Confirmation Message"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 6, description = "Verification of Activate SSCC Pool functionality.")
    public void verifyActivateSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_06");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        String activeConfirmationMessage = ssccPoolPage.activeSSCCPool(value(ssccPoolData, "Existing Serial Manager"),
                value(ssccPoolData, "Existing Ext Digit"), value(ssccPoolData, "Existing Prefix"));
        Assert.assertEquals(activeConfirmationMessage, value(ssccPoolData, "Confirmation Message"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 7, description = "Verification of validation when adding duplicate SSCC Pool.")
    public void verifyAddDuplicateSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_07");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 8, description = "Verification of Add 2nd SSCC Pool functionality.")
    public void verifyAdd2ndSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_08");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 9, description = "Verification of Add 3rd SSCC Pool functionality.")
    public void verifyAdd3rdSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_09");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 10, description = "Verification of Add 4th SSCC Pool functionality.")
    public void verifyAdd4thSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_10");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 11, description = "Verification of REFRESH button functionality.")
    public void verifyRefreshButtonFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_11");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.selectSSCCPool(value(ssccPoolData, "Existing Serial Manager"),
                value(ssccPoolData, "Existing Prefix"), value(ssccPoolData, "Existing Ext Digit"));
        Assert.assertEquals(ssccPoolPage.refreshButton(), value(ssccPoolData, "Expected Message or Title"));
    }

    // ------------------------------------------------------------------------
    @Test(priority = 12, description = "Verification of Add 1st Reesource SSCC Pool functionality.")
    public void verifyAdd1stReesourceSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_12");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 13, description = "Verification of Add 2nd Reesource SSCC Pool functionality.")
    public void verifyAdd2ndReesourceSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_13");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 14, description = "Verification of Add 1st Tracelink SSCC Pool functionality.")
    public void verifyAdd1stTracelinkSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_14");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 15, description = "Verification of Add 2nd Tracelink SSCC Pool functionality.")
    public void verifyAdd2ndTracelinkSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_15");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 16, description = "Verification of Add 1st RFXCEL SSCC Pool functionality.")
    public void verifyAdd1stRFXCELSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_16");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 17, description = "Verification of Add 2nd RFXCEL SSCC Pool functionality.")
    public void verifyAdd2ndRFXCELSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_17");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 18, description = "Verification of Add 1st ATTP SSCC Pool functionality.")
    public void verifyAdd1stATTPSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_18");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

    @Test(priority = 19, description = "Verification of Add 2nd ATTP SSCC Pool functionality.")
    public void verifyAdd2ndATTPSSCCPoolFunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> ssccPoolData = pageData(SSCC_POOL_SHEET, "SSCC-Pool_TC_19");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));
        SSCCPoolPage ssccPoolPage = new SSCCPoolPage(driver);
        ssccPoolPage.openSSCCPool();
        ssccPoolPage.AddSSCCPool(value(ssccPoolData, "Serial Manager"),
                value(ssccPoolData, "Ext Digit"), value(ssccPoolData, "Prefix"),
                value(ssccPoolData, "Required"), value(ssccPoolData, "Threshold"));
        Assert.assertEquals(ssccPoolPage.getNotificationMessage(), value(ssccPoolData, "Expected Message or Title"));
    }

}
