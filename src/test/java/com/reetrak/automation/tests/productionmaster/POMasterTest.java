package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.POMasterPage;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

public class POMasterTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String PO_SHEET = "testdata.po.sheet";

    @Test(priority = 1, description = "Verify that the PO Master menu opens successfully.")
    public void verifyPOMasterMenuOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_01");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        POMasterPage poMasterPage = new POMasterPage(driver);
        poMasterPage.openPOMaster();
        Assert.assertEquals(poMasterPage.getPOMasterPageName(), value(poData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "Verify that a new PO can be added successfully.")
    public void verifyAddPOfunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_02");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        POMasterPage poMasterPage = new POMasterPage(driver);
        poMasterPage.openPOMaster();
        poMasterPage.AddorUpdatePO("Add",
                null,
                value(poData, "PO Number"),
                value(poData, "Batch Number"),
                value(poData, "MFG Date"),
                value(poData, "EXP Date"),
                value(poData, "Batch Size"),
                value(poData, "Product"),
                value(poData, "Line"));
        Assert.assertEquals(poMasterPage.getNotificationMessage(), value(poData, "Expected Message or Title"));
    }

    @Test(priority = 3, description = "Verify that a existing PO can be updated successfully.")
    public void verifyEditPOfunctionality() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData(PO_SHEET, "POMaster_TC_03");
        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        POMasterPage poMasterPage = new POMasterPage(driver);
        poMasterPage.openPOMaster();
        poMasterPage.AddorUpdatePO("Update",
                value(poData, "Existing PO Number"),
                value(poData, "PO Number"),
                value(poData, "Batch Number"),
                value(poData, "MFG Date"),
                value(poData, "EXP Date"),
                value(poData, "Batch Size"),
                value(poData, "Product"),
                value(poData, "Line"));
        Assert.assertEquals(poMasterPage.getNotificationMessage(), value(poData, "Expected Message or Title"));
    }

}
