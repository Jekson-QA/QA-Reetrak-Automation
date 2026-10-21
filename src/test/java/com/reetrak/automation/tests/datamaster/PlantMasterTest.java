package com.reetrak.automation.tests.datamaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.datamaster.PlantMasterPage;

import com.reetrak.automation.base.BaseTest;

import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class PlantMasterTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String PLANT_SHEET = "testdata.plant.sheet";
        private static final String ADD_PLANT_TEST_CASE = "Plant-Master_TC_05";
        private static final String UPDATE_PLANT_TEST_CASE = "Plant-Master_TC_08";

        @DataProvider(name = "addPlantData")
        public Object[][] addPlantData() {
                Map<String, String> plantData = pageData(PLANT_SHEET, ADD_PLANT_TEST_CASE);
                return new Object[][] {
                                {
                                                value(plantData, "plantname", ""),
                                                value(plantData, "plantcode", ""),
                                                value(plantData, "licenseNumber", ""),
                                                value(plantData, "licenseagency", ""),
                                                value(plantData, "licStateOrRegion", ""),
                                                value(plantData, "glnprefix", ""),
                                                value(plantData, "glnnumber", ""),
                                                value(plantData, "address1", ""),
                                                value(plantData, "address2", ""),
                                                value(plantData, "pincode", ""),
                                                value(plantData, "city", ""),
                                                value(plantData, "state", ""),
                                                value(plantData, "country", ""),
                                                value(plantData, "phone", ""),
                                                value(plantData, "email", ""),
                                                value(plantData, "Expected Message or Title", "")
                                }
                };
        }

        @DataProvider(name = "updatePlantData")
        public Object[][] updatePlantData() {
                Map<String, String> plantData = pageData(PLANT_SHEET, UPDATE_PLANT_TEST_CASE);
                return new Object[][] {
                                {
                                                value(plantData, "plantname", ""),
                                                value(plantData, "licenseNumber", ""),
                                                value(plantData, "licenseagency", ""),
                                                value(plantData, "licStateOrRegion", ""),
                                                value(plantData, "glnprefix", ""),
                                                value(plantData, "glnnumber", ""),
                                                value(plantData, "address1", ""),
                                                value(plantData, "address2", ""),
                                                value(plantData, "pincode", ""),
                                                value(plantData, "city", ""),
                                                value(plantData, "state", ""),
                                                value(plantData, "country", ""),
                                                value(plantData, "phone", ""),
                                                value(plantData, "email", ""),
                                                value(plantData, "Expected Message or Title", "")
                                }
                };
        }

        @Test(priority = 1, description = "Verification of Plant Master page opening successfully.")

        public void verifyPlantMasterMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                Assert.assertEquals(plantMasterPage.getplantMasterPageName(), value(plantdata,
                                "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verification of ADD button functionality.")

        public void verifyAddButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.Addplant();
                Assert.assertEquals(plantMasterPage.getaddPlantInfoTitle(), value(plantdata,
                                "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verification of SAVE button functionality without entering plant details.")
        public void verifySavebuttonWithEmptyPlantInfo() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.Addplant();
                Assert.assertEquals(plantMasterPage.savebuttonstatus(), value(plantdata, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verification of CANCEL button functionality.")
        public void verifyCancelButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.Addplant();
                plantMasterPage.clickCancel();
                Assert.assertEquals(plantMasterPage.getplantMasterPageName(), value(plantdata,
                                "Expected Message or Title"));
        }

        @Test(priority = 5, dataProvider = "addPlantData", description = "Verification of Add New Plant functionality.")
        public void verifyAddPlantFunctionality(
                        String plantname,
                        String plantcode,
                        String licenseNumber,
                        String licenseagency,
                        String licStateOrRegion,
                        String glnprefix,
                        String glnnumber,
                        String address1,
                        String address2,
                        String pincode,
                        String city,
                        String state,
                        String country,
                        String phone,
                        String email,
                        String expectedMessage) throws Exception {

                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.Addplant();
                plantMasterPage.addPlantDetails(
                                plantname,
                                plantcode,
                                licenseNumber,
                                licenseagency,
                                licStateOrRegion,
                                glnprefix,
                                glnnumber,
                                address1,
                                address2,
                                pincode,
                                city,
                                state,
                                country,
                                phone,
                                email);

                plantMasterPage.clickSave();
                // Assert.assertEquals(plantPage.getNotificationMessage(),
                // value(plantdata,"expectedMessage"));
                Assert.assertEquals(plantMasterPage.getNotificationMessage(), expectedMessage);
        }

        @Test(priority = 6, description = "Verification of the 'Plant Code' field.")
        public void verifyPlantCodeFieldIsDisabled() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.openPlantForEdit();
                Assert.assertEquals(plantMasterPage.getPlantCodeFieldStatus(),
                                value(plantdata, "Expected Message or Title"));

        }

        @Test(priority = 7, description = "Verification of EDIT button functionality.")
        public void verifyEditButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.openPlantForEdit();
                Assert.assertEquals(plantMasterPage.getUpdatePlantTitle(),
                                value(plantdata, "Expected Message or Title"));
        }

        @Test(priority = 8, dataProvider = "updatePlantData", description = "Verification of Update Plant functionality.")
        public void verifyUpdatePlantFunctionality(
                        String plantname,
                        String licenseNumber,
                        String licenseagency,
                        String licStateOrRegion,
                        String glnprefix,
                        String glnnumber,
                        String address1,
                        String address2,
                        String pincode,
                        String city,
                        String state,
                        String country,
                        String phone,
                        String email,
                        String expectedMessage) throws Exception {

                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.openPlantForEdit();
                plantMasterPage.updatePlantDetails(
                                plantname,
                                licenseNumber,
                                licenseagency,
                                licStateOrRegion,
                                glnprefix,
                                glnnumber,
                                address1,
                                address2,
                                pincode,
                                city,
                                state,
                                country,
                                phone,
                                email);

                plantMasterPage.clickSave();
                Assert.assertEquals(plantMasterPage.getNotificationMessage(), expectedMessage);
        }

        @Test(priority = 9, description = "Verification of the 'Same as Company Master' checkbox functionality.")
        public void verifySameAsCompanyMasterCheckboxFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_09");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.sameascompanyaddress();
                plantMasterPage.clickSave();
                Assert.assertEquals(plantMasterPage.getNotificationMessage(),
                                value(plantdata, "Expected Message or Title"));
        }

        @Test(priority = 10, description = "Verification of Plant deactivation functionality.")
        public void verifyDeactivatePlantFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_10");
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                String deactiveConfirmationMessage = plantMasterPage.deactivePlant();
                Assert.assertEquals(deactiveConfirmationMessage, value(plantdata, "Confirmation Message"));
                Assert.assertEquals(plantMasterPage.getNotificationMessage(),
                                value(plantdata, "Expected Message or Title"));
                // Assert.assertEquals(plantMasterPage.getPlantStatus(), value(plantdata, "Plant
                // Status"));
        }

        @Test(priority = 11, description = "Verification of validation when attempting to edit a deactivated plant.")
        public void verifyValidationOnEditDeactivatedPlant() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_11");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                plantMasterPage.openPlantForEdit();
                Assert.assertEquals(plantMasterPage.getNotificationMessage(),
                                value(plantdata, "Expected Message or Title"));
        }

        @Test(priority = 12, description = "Verification of Plant activation functionality.")
        public void verifyActivatePlantFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_12");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                String activeConfirmationMessage = plantMasterPage.activeplant();
                Assert.assertEquals(activeConfirmationMessage, value(plantdata, "Confirmation Message"));
                Assert.assertEquals(plantMasterPage.getNotificationMessage(),
                                value(plantdata, "Expected Message or Title"));
                // Assert.assertEquals(plantMasterPage.getPlantStatus(), value(plantdata, "Plant
                // Status"));
        }

        @Test(priority = 13, description = "Verification of REFRESH button functionality.")
        public void verifyRefreshButtonFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> plantdata = pageData(PLANT_SHEET, "Plant-Master_TC_13");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                PlantMasterPage plantMasterPage = new PlantMasterPage(driver);
                plantMasterPage.openPlantMaster();
                Assert.assertEquals(plantMasterPage.refreshButton(), value(plantdata, "Expected Message or Title"));
        }

}
