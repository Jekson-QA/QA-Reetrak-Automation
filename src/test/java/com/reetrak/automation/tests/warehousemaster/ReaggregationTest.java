package com.reetrak.automation.tests.warehousemaster;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.warehousemaster.ReaggregationPage;

public class ReaggregationTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String REAGGREGATION_SHEET = "testdata.reaggregation.sheet";

    @BeforeClass
    public void setupExcelSheet() {
        String filePath = com.reetrak.automation.utilities.ConfigReader.get("testdata.file.path",
                "src/test/resources/testdata/ui-test-data.xlsx");
        java.io.File file = new java.io.File(filePath);
        if (!file.exists()) {
            return;
        }
        try (java.io.FileInputStream fis = new java.io.FileInputStream(file);
                org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(fis)) {

            String sheetName = "Reaggregation";
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                org.apache.poi.ss.usermodel.Row r = sheet.getRow(0);
                if (r == null || r.getCell(0) == null || !r.getCell(0).getStringCellValue().equals("Test Case")) {
                    int index = workbook.getSheetIndex(sheetName);
                    workbook.removeSheetAt(index);
                    sheet = null;
                }
            }
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);

                // Write Header Row
                org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Test Case");
                header.createCell(3).setCellValue("Line");
                header.createCell(4).setCellValue("Batch Level");
                header.createCell(5).setCellValue("Scan Code");
                header.createCell(6).setCellValue("Expected Message or Title");

                // Write TC_01 row
                org.apache.poi.ss.usermodel.Row tc1 = sheet.createRow(1);
                tc1.createCell(0).setCellValue("Reaggregation_TC_01");
                tc1.createCell(6).setCellValue("Repacking");

                // Write TC_02 row
                org.apache.poi.ss.usermodel.Row tc2 = sheet.createRow(2);
                tc2.createCell(0).setCellValue("Reaggregation_TC_02");
                tc2.createCell(3).setCellValue("PACKAGING LINE 1");
                tc2.createCell(4).setCellValue("Tertiary");
                tc2.createCell(5).setCellValue("");
                tc2.createCell(6).setCellValue("Success");

                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                    workbook.write(fos);
                }
                System.out.println("Created and initialized Reaggregation sheet in physical Excel file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1, description = "TC-1: Navigate to Warehouse Master -> Repacking -> Reaggregation and verify page opens.")
    public void verifyReaggregationPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(REAGGREGATION_SHEET, "Reaggregation_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ReaggregationPage page = new ReaggregationPage(driver);
        page.openRepacking();
        page.clickReaggregationTab();

        Assert.assertEquals(page.getRepackingPageName(), value(testData, "Expected Message or Title"));
    }

    @Test(priority = 2, description = "TC-2: Select Line, Batch, Level, click Confirm, select deaggregated code, enter existing parent code and Save.")
    public void verifyReaggregationConfirmation() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(REAGGREGATION_SHEET, "Reaggregation_TC_02");

        String line = getAndSyncLineName(REAGGREGATION_SHEET, "Reaggregation_TC_02");
        String level = value(testData, "Batch Level");
        String createdBatch = value(poData, "Batch Number");

        // Dynamically get parent (existing parent code) and child code from downloaded
        // report
        String parentCode = get18DigitCodeFromDownloadedFile(createdBatch);
        Assert.assertNotNull(parentCode, "Failed to dynamically extract 18-digit parent code!");

        java.util.List<String> childCodes = getChildCodesFromCSV(createdBatch, parentCode);
        Assert.assertFalse(childCodes.isEmpty(), "Failed to dynamically extract deaggregated child codes!");
        String childCodeToReaggregate = childCodes.get(0);

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        ReaggregationPage page = new ReaggregationPage(driver);
        page.openRepacking();
        page.clickReaggregationTab();

        page.selectLine(line);
        page.clickBatch();
        page.selectBatch(createdBatch);
        page.selectLevel(level);

        page.clickConfirm();

        // Close the "Verify Code detail" popup dialog that opens after clicking Confirm
        // (if any)
        page.clickDialogOk();

        // Click to view codes link to expand the child codes checklist inline
        page.clickClickToViewCodes();

        // Select the deaggregated child code checkbox
        page.selectCodeInDialog(childCodeToReaggregate);

        // Select Parent Type as "Existing Parent Code" and enter the parent code
        page.selectParentType("Existing Parent Code");
        page.enterParentCode(parentCode);

        // Click on SAVE button and perform reaggregation
        page.clickSave();
        page.handleSaveConfirmation("Test : Reaggregation Completed");

        // Get the notification message and verify success
        String actualMsg = page.getNotificationMessage();
        System.out.println("Notification message: " + actualMsg);

        page.clickLastButton();

        String msgLower = actualMsg.toLowerCase();
        if (msgLower.contains("successfully") ||
                msgLower.contains("success") ||
                msgLower.contains("completed") ||
                msgLower.contains("done")) {
            System.out.println("Verification passed: " + actualMsg);
        } else {
            Assert.fail("Reaggregation failed! Expected success message, but received: " + actualMsg);
        }
    }

    private String get18DigitCodeFromDownloadedFile(String batchName) throws Exception {
        java.io.File destDir = new java.io.File(System.getProperty("user.dir"), "downloads");
        java.io.File[] files = destDir.listFiles();
        java.io.File latestFile = null;
        long lastModified = 0;
        if (files != null) {
            for (java.io.File file : files) {
                String name = file.getName();
                if (name.toLowerCase().contains(batchName.toLowerCase()) && name.toLowerCase().contains("relationship report") && name.endsWith(".csv")) {
                    if (file.lastModified() > lastModified) {
                        lastModified = file.lastModified();
                        latestFile = file;
                    }
                }
            }
        }

        if (latestFile == null) {
            throw new IllegalStateException("No downloaded Relationship Report CSV file found for batch: " + batchName);
        }

        System.out.println("Reading code from downloaded file: " + latestFile.getAbsolutePath());

        java.util.regex.Pattern prefixSuffixPattern = java.util.regex.Pattern.compile("\\d+-([A-Za-z0-9]+)\\(\\d+\\)");
        java.util.regex.Pattern digitPattern = java.util.regex.Pattern.compile("\\b\\d{18}\\b");

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(latestFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Try to find the prefix-suffix format first
                java.util.regex.Matcher prefixSuffixMatcher = prefixSuffixPattern.matcher(line);
                if (prefixSuffixMatcher.find()) {
                    String code = prefixSuffixMatcher.group(1);
                    System.out.println("Extracted code (prefix-suffix format) from CSV: " + code);
                    return code;
                }

                // Fallback to standard 18-digit format
                java.util.regex.Matcher digitMatcher = digitPattern.matcher(line);
                if (digitMatcher.find()) {
                    String code = digitMatcher.group();
                    System.out.println("Extracted 18-digit code from CSV: " + code);
                    return code;
                }
            }
        }
        throw new IllegalStateException("Could not find any matching code in file: " + latestFile.getAbsolutePath());
    }

    private java.util.List<String> getChildCodesFromCSV(String batchName, String parentCode) throws Exception {
        java.io.File destDir = new java.io.File(System.getProperty("user.dir"), "downloads");
        java.io.File[] files = destDir.listFiles();
        java.io.File latestFile = null;
        long lastModified = 0;
        if (files != null) {
            for (java.io.File file : files) {
                String name = file.getName();
                if (name.contains(batchName) && name.contains("Relationship Report") && name.endsWith(".csv")) {
                    if (file.lastModified() > lastModified) {
                        lastModified = file.lastModified();
                        latestFile = file;
                    }
                }
            }
        }

        if (latestFile == null) {
            throw new IllegalStateException("No downloaded Relationship Report CSV file found.");
        }

        java.util.List<String> children = new java.util.ArrayList<>();

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(latestFile))) {
            String line;
            int parentColIndex = -1;
            boolean parentFound = false;

            while ((line = reader.readLine()) != null) {
                java.util.List<String> columns = parseCsvLine(line);
                if (!parentFound) {
                    for (int i = 0; i < columns.size(); i++) {
                        String cell = columns.get(i);
                        if (cell.contains(parentCode)) {
                            parentFound = true;
                            parentColIndex = i;
                            break;
                        }
                    }
                } else {
                    boolean stop = false;
                    for (int i = 0; i <= parentColIndex; i++) {
                        if (i < columns.size() && !columns.get(i).isEmpty()) {
                            stop = true;
                            break;
                        }
                    }
                    if (stop) {
                        break;
                    }

                    int childColIndex = parentColIndex + 1;
                    if (childColIndex < columns.size()) {
                        String childCell = columns.get(childColIndex);
                        if (!childCell.isEmpty()) {
                            String extracted = extractCode(childCell);
                            if (!extracted.isEmpty()) {
                                children.add(extracted);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Extracted child codes for parent " + parentCode + ": " + children);
        return children;
    }

    private java.util.List<String> parseCsvLine(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result;
    }

    private String extractCode(String cellValue) {
        if (cellValue == null || cellValue.isEmpty()) {
            return "";
        }
        if (cellValue.contains("(")) {
            int firstParen = cellValue.indexOf('(');
            String beforeParen = cellValue.substring(0, firstParen);
            int lastHyphen = beforeParen.lastIndexOf('-');
            if (lastHyphen >= 0) {
                return beforeParen.substring(lastHyphen + 1).trim();
            }
            return beforeParen.trim();
        }
        if (cellValue.contains("-")) {
            int lastHyphen = cellValue.lastIndexOf('-');
            String partAfter = cellValue.substring(lastHyphen + 1);
            if (partAfter.length() <= 3 && partAfter.matches("\\d+")) {
                String beforeLastHyphen = cellValue.substring(0, lastHyphen);
                int prevHyphen = beforeLastHyphen.lastIndexOf('-');
                if (prevHyphen >= 0) {
                    return beforeLastHyphen.substring(prevHyphen + 1).trim();
                }
                return beforeLastHyphen.trim();
            }
        }
        return cellValue;
    }
}
