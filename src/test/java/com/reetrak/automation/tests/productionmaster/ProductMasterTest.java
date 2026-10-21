package com.reetrak.automation.tests.productionmaster;

import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.productionmaster.ProductMasterPage;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;

public class ProductMasterTest extends BaseTest {

        private static final String LOGIN_SHEET = "testdata.login.sheet";
        private static final String PRODUCT_SHEET = "testdata.product.sheet";

        private String expectedMessage(Map<String, String> data) {
                String approveVal = value(data, "approve", value(data, "Approve", value(data, "APPROVE", "")));
                if ("Yes".equalsIgnoreCase(approveVal)) {
                        String inboxMsg = value(data, "Expected Message From Inbox", "");
                        if (inboxMsg != null && !inboxMsg.isBlank()) {
                                return inboxMsg;
                        }
                }
                return value(data, "Expected Message or Title", "");
        }

        @Test(priority = 1, description = "Verify that the Product Master menu opens successfully.")
        public void verifyProductMasterMenuOpens() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_01");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                Assert.assertEquals(productPage.getProductMasterPageName(),
                                value(productData, "Expected Message or Title"));
        }

        @Test(priority = 2, description = "Verify that the Add Product button opens the product form.")
        public void verifyAddButtonOnProductMasterPage() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_02");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.clickAddProduct();
                Assert.assertEquals(productPage.getAddProductInfoTitle(),
                                value(productData, "Expected Message or Title"));
        }

        @Test(priority = 3, description = "Verify that the Save button is disabled when no product data is entered.")
        public void verifySaveButtonWithEmptyProductInfo() throws Exception {
                org.testng.Reporter.getCurrentTestResult().setAttribute("testCaseName",
                                "Product-Master_TC_03: Verify that the Save button is disabled when no product data is entered.");
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_03");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.clickAddProduct();
                // productPage.clickCollapseAll();
                String actualStatus = productPage.isSaveButtonEnabled() ? "Save button should be enabled"
                                : "Save button is disabled";
                Assert.assertEquals(actualStatus, value(productData, "Expected Message or Title"));
        }

        @Test(priority = 4, description = "Verify that a new product can be added successfully (1 Level).")
        public void verifyAddProductFunctionality_1Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_04");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 5, description = "Verify that a new product can be added successfully (1-2 Level).")
        public void verifyAddProductFunctionality_1_2Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_05");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 6, description = "Verify that a new product can be added successfully (1-2-3 Level).")
        public void verifyAddProductFunctionality_1_2_3Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_06");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 7, description = "Verify that a new product can be added successfully (1-2-3 Level with 3rd-SSCC).")
        public void verifyAddProductFunctionality_1_2_3LevelWith3rdSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_07");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 8, description = "Verify that a new product can be added successfully (1-2-3-6 Level with 6th-SSCC).")
        public void verifyAddProductFunctionality_1_2_3_6LevelWith6thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_08");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 9, description = "Verify that a new product can be added successfully (2 Level).")
        public void verifyAddProductFunctionality_2Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_09");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 10, description = "Verify that a new product can be added successfully (2-3 Levels).")
        public void verifyAddProductFunctionality_2_3Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_10");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        // -------------------------------------------

        @Test(priority = 11, description = "Verify that a new product can be added successfully (2-3 Level with 3rd-SSCC).")
        public void verifyAddProductFunctionality_2_3LevelWith3rdSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_11");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 12, description = "Verify that a new product can be added successfully (2-3-4 Levels).")
        public void verifyAddProductFunctionality_2_3_4Level() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_12");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 13, description = "Verify that a new product can be added successfully (2-3-4 Levels with 4th-SSCC).")
        public void verifyAddProductFunctionality_2_3_4LevelWith4thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_13");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 14, description = "Verify that a new product can be added successfully (2-3-6 Levels with 6th-SSCC).")
        public void verifyAddProductFunctionality_2_3_6LevelWith6thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_14");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 15, description = "Verify that a new product can be added successfully (2-3-4-6 Levels with 6th-SSCC).")
        public void verifyAddProductFunctionality_2_3_4_6LevelWith6thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_15");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 16, description = "Verify that a new product with multiple SSCC can be added successfully (2-3-4-6 Levels with 4th & 6th-SSCC).")
        public void verifyAddProductFunctionality_2_3_4_6LevelWith4th_6thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_16");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 17, description = "Verify that a new product with multiple SSCC can be added successfully (1-2-3-6 Levels with 3rd & 6th-SSCC).")
        public void verifyAddProductFunctionality_1_2_3_6LevelWith3rd_6thSSCC() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_17");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 18, description = "Verify that a new Non-Serialization product can be added successfully (1-2-3 Level).")
        public void verifyAddProductFunctionality_1_2_3Level_NonSerialization() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_18");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 19, description = "Verify that a new Non-Serialization product can be added successfully (2-3-4 Level).")
        public void verifyAddProductFunctionality_2_3_4Level_NonSerialization() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET, "Product-Master_TC_19");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.AddProduct(
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "strength", ""),
                                value(productData, "dosageForm", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 20, description = "Verify copying an existing product with new product code and customer code.")
        public void verifyCopyProductFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_20");

                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.copyProduct(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""),
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);

                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 21, description = "Verify that an active product can be deactivated successfully.")
        public void verifyDeactivateProductFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_21");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.deactivateProduct(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""));
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 22, description = "Verify that a deactivated product can be activated successfully.")
        public void verifyActivateProductFunctionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_22");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.activateProduct(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""));
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 23, description = "Verify that an existing product can be updated from (1-2-3-6 Level) to (2-3-4-6 Level).")
        public void verifyUpdateProductFunctionality_1_2_3_6_to_2_3_4_6_Level()
                        throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_23");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.UpdateProduct(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""),
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 24, description = "Verify that an existing product can be updated by removing a single level (-1 Level).")
        public void verifyUpdateProductSingleLevelRemove_Functionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_24");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.updateProductLevel(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""),
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

        @Test(priority = 25, description = "Verify that an existing product can be updated by adding a single level (+1 Level).")

        public void verifyUpdateProductSingleLevelAdd_Functionality() throws Exception {
                Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
                Map<String, String> productData = pageData(PRODUCT_SHEET,
                                "Product-Master_TC_25");
                LoginLogoutPage loginPage = new LoginLogoutPage(driver);
                loginPage.open(baseUrl);
                loginPage.loginAs(value(loginData, "username"), value(loginData,
                                "password"));

                ProductMasterPage productPage = new ProductMasterPage(driver);
                productPage.openProductMaster();
                productPage.updateProductLevel(
                                value(productData, "Existing Product Name", ""),
                                value(productData, "Existing Product Code", ""),
                                value(productData, "productname", ""),
                                value(productData, "productcode", ""),
                                value(productData, "customercode", ""),
                                value(productData, "serialmanager", ""),
                                value(productData, "description", ""),
                                value(productData, "compliance", ""),
                                value(productData, "country", ""),
                                value(productData, "unitprice", ""),
                                value(productData, "packagesize", ""),
                                value(productData, "packagingtype", ""),
                                value(productData, "tradetype", ""),
                                value(productData, "nooflevels", ""),
                                value(productData, "productformtype", ""),
                                value(productData, "barcodedate", ""),
                                value(productData, "ocrexpdate", ""),
                                value(productData, "ocrmfgdate", ""),
                                value(productData, "Hierarchy Name", ""),
                                productData);
                Assert.assertEquals(productPage.getNotificationMessage(),
                                expectedMessage(productData));
        }

}
