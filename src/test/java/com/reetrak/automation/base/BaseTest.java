package com.reetrak.automation.base;

import com.reetrak.automation.factory.DriverFactory;
import com.reetrak.automation.reports.CustomTestNgReporter;
import com.reetrak.automation.utilities.ConfigReader;
import com.reetrak.automation.utilities.ExcelUtils;
import com.reetrak.automation.utilities.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class BaseTest {

    public static final java.util.List<java.util.Map<String, String>> addedProducts = new java.util.ArrayList<>();
    public static final java.util.List<java.util.Map<String, String>> createdPOs = new java.util.ArrayList<>();

    protected WebDriver driver;
    protected String baseUrl;

    @BeforeSuite(alwaysRun = true)
    public void resetCustomReport() {
        CustomTestNgReporter.resetLiveReport();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        checkAndThrowSkip(method);
        String browser = ConfigReader.get("browser", "chrome");
        baseUrl = ConfigReader.get("base.url", "https://www.saucedemo.com/");
        DriverFactory.initDriver(browser);
        driver = DriverFactory.getDriver();
    }

    private void checkAndThrowSkip(Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();

        if ("ProductMasterTest".equals(className)) {
            if (methodName.startsWith("verifyAddProduct_TC_") && !methodName.equals("verifyAddProduct_TC_04")) {
                String mode = ConfigReader.get("execution.mode", "single");
                if ("single".equalsIgnoreCase(mode)) {
                    throw new org.testng.SkipException("Skipping " + methodName + " because execution.mode is single.");
                }
            }
        } else if ("POMasterTest".equals(className)) {
            if (methodName.startsWith("verifyAddPO_TC_") && !methodName.equals("verifyAddPO_TC_03")) {
                String mode = ConfigReader.get("execution.mode", "single");
                if ("single".equalsIgnoreCase(mode)) {
                    throw new org.testng.SkipException("Skipping " + methodName + " because execution.mode is single.");
                }
            }
        } else if ("BatchRequestTest".equals(className)) {
            if (methodName.startsWith("verifyBatchRequest_TC_")) {
                String mode = ConfigReader.get("execution.mode", "single");
                if ("single".equalsIgnoreCase(mode)) {
                    throw new org.testng.SkipException("Skipping " + methodName + " because execution.mode is single.");
                }
            }

        } else if ("HierarchyMasterTest".equals(className)) {
            boolean isHierarchyEnabled = Boolean.parseBoolean(
                    com.reetrak.automation.utilities.ConfigReader.get("hierarchy.master.enabled", "true"));
            if (!isHierarchyEnabled) {
                throw new org.testng.SkipException(
                        "Skipping HierarchyMasterTest because Hierarchy Master is disabled in config.");
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null && shouldCaptureScreenshot(result)) {
            String screenshotPath = ScreenshotUtils.capture(
                    driver,
                    result.getMethod().getMethodName(),
                    result.isSuccess());
            result.setAttribute("screenshotPath", screenshotPath);
        }
        CustomTestNgReporter.recordResult(result);
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void publishCustomReport() {
        CustomTestNgReporter.publishLiveReport();
    }

    private boolean shouldCaptureScreenshot(ITestResult result) {
        return result.getStatus() == ITestResult.SUCCESS || result.getStatus() == ITestResult.FAILURE;
    }

    protected Map<String, String> pageData(String sheetConfigKey, String testCaseName) {
        String sheetName = ConfigReader.getRequired(sheetConfigKey);
        if (org.testng.Reporter.getCurrentTestResult() != null && !"testdata.login.sheet".equals(sheetConfigKey)) {
            org.testng.Reporter.getCurrentTestResult().setAttribute("excelTestCase", testCaseName);
        }
        return ExcelUtils.getRowData(sheetName, testCaseName);
    }

    protected String value(Map<String, String> data, String key) {
        String value = data.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required test data for key: " + key);
        }
        value = value.trim();
        return value;
    }

    protected String value(Map<String, String> data, String key, String defaultValue) {
        String value = data.get(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    protected String getAndSyncLineName(String testCaseSheetConfig, String testCaseName) {
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        String lineSource = poData.get("Line Source Test Case");
        if (lineSource == null || lineSource.isBlank()) {
            poData = pageData("testdata.po.sheet", "POMaster_TC_03");
            lineSource = poData.get("Line Source Test Case");
        }
        Map<String, String> lineData = pageData("testdata.line.sheet", lineSource);
        String line = lineData.get("New Line Name");
        if (line == null || line.isBlank()) {
            line = lineData.get("Existing Line Name");
        }
        if (line == null || line.isBlank()) {
            throw new IllegalStateException(
                    "Line name (New Line Name or Existing Line Name) is missing for source: " + lineSource);
        }
        line = line.trim();

        try {
            String sheetName = ConfigReader.getRequired(testCaseSheetConfig);
            ExcelUtils.updateTestCaseCell(sheetName, testCaseName, "Line", line);
        } catch (Exception e) {
            System.out.println("Warning: Could not update Excel file for " + testCaseName + ": " + e.getMessage());
        }
        return line;
    }

    protected String deriveBasePart(String productName) {
        if (productName == null)
            return "";

        String originalName = getOriginalProductName(productName);

        String cleaned = originalName;
        if (cleaned.startsWith("Test_PRD_")) {
            cleaned = cleaned.substring("Test_PRD_".length());
        }
        cleaned = cleaned.replace("SA_S_", "S_");
        cleaned = cleaned.replace("SER_S_", "S_");
        cleaned = cleaned.replace("S_SER_", "S_");
        cleaned = cleaned.replace("SA_", "");
        cleaned = cleaned.replace("SER_", "");
        return cleaned;
    }

    protected String resolveProductName(String originalName) {
        if (originalName == null || originalName.isBlank()) {
            return originalName;
        }
        try {
            String productSheetName = com.reetrak.automation.utilities.ConfigReader
                    .getRequired("testdata.product.sheet");
            java.util.List<String> allTCs = com.reetrak.automation.utilities.ExcelUtils
                    .getTestCaseNames(productSheetName);
            for (String tc : allTCs) {
                Map<String, String> rowData = com.reetrak.automation.utilities.ExcelUtils.getRowData(productSheetName,
                        tc);
                String existingName = rowData.get("Existing Product Name");
                if (originalName.equalsIgnoreCase(existingName)) {
                    String updatedName = rowData.get("productname");
                    if (updatedName != null && !updatedName.isBlank()) {
                        return updatedName.trim();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not resolve product name from Excel: " + e.getMessage());
        }
        return originalName;
    }

    protected String getOriginalProductName(String productName) {
        if (productName == null || productName.isBlank()) {
            return productName;
        }
        try {
            String productSheetName = com.reetrak.automation.utilities.ConfigReader
                    .getRequired("testdata.product.sheet");
            java.util.List<String> allTCs = com.reetrak.automation.utilities.ExcelUtils
                    .getTestCaseNames(productSheetName);
            for (String tc : allTCs) {
                Map<String, String> rowData = com.reetrak.automation.utilities.ExcelUtils.getRowData(productSheetName,
                        tc);
                String currentName = rowData.get("productname");
                if (productName.equalsIgnoreCase(currentName)) {
                    String existingName = rowData.get("Existing Product Name");
                    if (existingName != null && !existingName.isBlank()) {
                        return existingName.trim();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not get original product name from Excel: " + e.getMessage());
        }
        return productName;
    }
}