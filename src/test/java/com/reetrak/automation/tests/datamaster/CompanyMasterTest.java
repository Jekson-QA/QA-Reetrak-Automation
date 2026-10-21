package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.CompanyMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CompanyMasterTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String EDIT_COMPANY_SHEET = "testdata.companymaster.sheet";

        @Test(priority = 1, description = "Verification of Company Master page opening successfully.")
        public void VerificationofCompanyMasterPage() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> companyMasterData = pageData(EDIT_COMPANY_SHEET, "Company-Master_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                CompanyMasterPage companyMasterPage = new CompanyMasterPage(driver);
                companyMasterPage.openCompanyMaster();
                Assert.assertEquals(companyMasterPage.getCompanyMasterPageName(),
                                value(companyMasterData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of EDIT button functionality.")
        public void VerificationofEditComapnyPage() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> companyMasterData = pageData(EDIT_COMPANY_SHEET, "Company-Master_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                CompanyMasterPage companyMasterPage = new CompanyMasterPage(driver);
                companyMasterPage.openCompanyMaster();

                companyMasterPage.updateCompany();
                Assert.assertEquals(companyMasterPage.getEditCompanyTitle(),
                                value(companyMasterData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of Update Comapny Details")
        public void VerificationofEditComapnyDetailsfunctionality() throws Exception {

                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> companyMasterData = pageData(EDIT_COMPANY_SHEET, "Company-Master_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                CompanyMasterPage companyMasterPage = new CompanyMasterPage(driver);
                companyMasterPage.openCompanyMaster();

                companyMasterPage.updateCompany();

                companyMasterPage.updateCompanyDetails(value(companyMasterData, "url"),
                                value(companyMasterData, "LogoPath"),
                                value(companyMasterData, "Address1"), value(companyMasterData, "Address2"),
                                value(companyMasterData, "Pin Code"), value(companyMasterData, "City"),
                                value(companyMasterData, "State"), value(companyMasterData, "Country"));

                companyMasterPage.clickSave();
                Assert.assertEquals(companyMasterPage.getnotification(),
                                value(companyMasterData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verification of REFRESH button functionality.")
        public void VerificationofRefreshButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> companyMasterData = pageData(EDIT_COMPANY_SHEET, "Company-Master_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                CompanyMasterPage companyMasterPage = new CompanyMasterPage(driver);
                companyMasterPage.openCompanyMaster();

                companyMasterPage.selectCompany();
                Assert.assertEquals(companyMasterPage.refreshButton(),
                                value(companyMasterData, "Expected Message or Title"));
        }

}
