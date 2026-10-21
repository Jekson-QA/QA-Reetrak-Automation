package com.reetrak.automation.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExcelUtils {

    private static final String TEST_CASE_COLUMN = "Test Case";


    private ExcelUtils() {
    }

// ---------------------New Start--------------------------------
    public static Map<String, String> getRowData(String sheetName, String testCaseName) {
        ensureWorkbookExists();

        Path workbookPath = getWorkbookPath();
        try (InputStream inputStream = Files.newInputStream(workbookPath);
                Workbook workbook = new XSSFWorkbook(inputStream)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            DataFormatter formatter = new DataFormatter();

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalStateException("Sheet not found in Excel file: " +
                        sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing in sheet: " +
                        sheetName);
            }

            int testCaseColumnIndex = getColumnIndex(headerRow, TEST_CASE_COLUMN,
                    formatter, evaluator);
            if (testCaseColumnIndex == -1) {
                throw new IllegalStateException("Column '" + TEST_CASE_COLUMN + "' is missingin sheet: " + sheetName);
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null)
                    continue;

                String currentTestCase = getCellValue(row.getCell(testCaseColumnIndex),
                        formatter, evaluator);

                if (testCaseName.equalsIgnoreCase(currentTestCase)) {
                    return createRowData(headerRow, row, formatter, evaluator);
                }
            }

            throw new IllegalStateException("no test case found in file");

        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel test data.",
                    exception);
        }
    }

    public static java.util.List<String> getTestCaseNames(String sheetName) {
        ensureWorkbookExists();
        java.util.List<String> testCases = new java.util.ArrayList<>();
        Path workbookPath = getWorkbookPath();
        try (InputStream inputStream = Files.newInputStream(workbookPath);
                Workbook workbook = new XSSFWorkbook(inputStream)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            DataFormatter formatter = new DataFormatter();

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return testCases;
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return testCases;
            }

            int testCaseColumnIndex = getColumnIndex(headerRow, TEST_CASE_COLUMN, formatter, evaluator);
            if (testCaseColumnIndex == -1) {
                return testCases;
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null)
                    continue;

                String currentTestCase = getCellValue(row.getCell(testCaseColumnIndex), formatter, evaluator);
                if (currentTestCase != null && !currentTestCase.isBlank()) {
                    testCases.add(currentTestCase);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel test cases.", exception);
        }
        return testCases;
    }

    public static void updateTestCaseCell(String sheetName, String testCaseName,
            String columnName, String value) {
        ensureWorkbookExists();

        Path workbookPath = getWorkbookPath();
        try (Workbook workbook = openWorkbook(workbookPath)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalStateException("Sheet not found in Excel file: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing in sheet: " + sheetName);
            }

            int testCaseColumnIndex = getColumnIndex(headerRow, TEST_CASE_COLUMN, formatter, evaluator);
            if (testCaseColumnIndex == -1) {
                throw new IllegalStateException("Column '" + TEST_CASE_COLUMN + "' is missing in sheet: " + sheetName);
            }

            int targetColumnIndex = getColumnIndex(headerRow, columnName, formatter, evaluator);
            if (targetColumnIndex == -1) {
                targetColumnIndex = headerRow.getLastCellNum();
                headerRow.createCell(targetColumnIndex).setCellValue(columnName);
            }

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String currentTestCase = getCellValue(row.getCell(testCaseColumnIndex), formatter, evaluator);
                if (testCaseName.equalsIgnoreCase(currentTestCase)) {
                    row.createCell(targetColumnIndex).setCellValue(value);
                    try (OutputStream outputStream = Files.newOutputStream(workbookPath)) {
                        workbook.write(outputStream);
                    }
                    return;
                }
            }

            throw new IllegalStateException("no test case found in file");
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to update Excel test data.", exception);
        }
    }
    private static Workbook openWorkbook(Path workbookPath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(workbookPath)) {
            return new XSSFWorkbook(inputStream);
        }
    }

    private static Map<String, String> createRowData(Row headerRow, Row dataRow,
            DataFormatter formatter,
            FormulaEvaluator evaluator) {
        Map<String, String> rowData = new LinkedHashMap<>();

        for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            String columnName = getCellValue(headerRow.getCell(cellIndex), formatter,
                    evaluator);

            if (!columnName.isBlank()) {
                String cellValue = getCellValue(dataRow.getCell(cellIndex), formatter,
                        evaluator);
                rowData.put(columnName, cellValue);
            }
        }
        return rowData;
    }

    private static int getColumnIndex(Row headerRow, String columnName,
            DataFormatter formatter,
            FormulaEvaluator evaluator) {
        for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
            String header = getCellValue(headerRow.getCell(cellIndex), formatter,
                    evaluator);

            if (columnName.equalsIgnoreCase(header)) {
                return cellIndex;
            }
        }
        return -1;
    }

    private static String getCellValue(Cell cell,
            DataFormatter formatter,
            FormulaEvaluator evaluator) {
        if (cell == null)
            return "";

        return formatter.formatCellValue(cell, evaluator).replace('\u00A0', ' ').trim();
    }

// ---------------------New End--------------------------------



    private static Path getWorkbookPath() {
        String workbookPath = ConfigReader.get("testdata.file.path", "src/test/resources/testdata/ui-test-data.xlsx");
        return Paths.get(workbookPath);
    }

    private static void ensureWorkbookExists() {
        Path workbookPath = getWorkbookPath();
        if (Files.exists(workbookPath)) {
            return;
        }

        try {
            Files.createDirectories(workbookPath.getParent());
            createStarterWorkbook(workbookPath);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create starter Excel file.", exception);
        }
    }

    private static void createStarterWorkbook(Path workbookPath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            createLoginSheet(workbook, ConfigReader.get("testdata.login.sheet", "LoginPage"));
            createEditCompanySheet(workbook, ConfigReader.get("testdata.edit.company.sheet", "EditCompanyPage"));

            try (OutputStream outputStream = Files.newOutputStream(workbookPath)) {
                workbook.write(outputStream);
            }
        }
    }

    private static void createLoginSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        writeRow(sheet, 0, "testCase", "username", "password", "expectedMessage",
                "expectedUsernameMessage", "expectedPasswordMessage", "lockCount");
        writeRow(sheet, 1, "blank_credentials", "", "", "",
                "Please enter valid user Name", "Please enter valid Password", "");
        writeRow(sheet, 2, "invalid_username", "InvalidUser", "password", "InvalidUser does not exists.", "", "", "");
        writeRow(sheet, 3, "invalid_password", "Tadmin", "InvalidPassword", "LOG IN", "", "", "");
        writeRow(sheet, 4, "valid_login", "Tadmin", "password", "Dashboard", "", "", "");
        writeRow(sheet, 5, "logout", "Tadmin", "password", "LOG IN", "", "", "");
        writeRow(sheet, 6, "locked_user", "Tadmin", "InvalidPassword", "Please Check Entered User Account Is Locked",
                "", "", "6");
        writeRow(sheet, 7, "deactive_user", "jadmin", "password", "Entered User Is De-Active", "", "", "");
    }

    private static void createEditCompanySheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.createSheet(sheetName);
        writeRow(sheet, 0, "testCase", "url", "logoPath", "address1", "address2",
                "pinCode", "city", "state", "country", "action", "expectedMessage");
        writeRow(sheet, 1, "update_company", "WWW.Jekson.co.in", "D:\\jeksonvisionlogo1.png",
                "304, SARKHEJ-BAVLA HIGHWAY", "CHANGODAR", "382213", "AHMEDABAD",
                "GUJARAT", "INDIA", "Save", "Company details Successfully updated.");
    }

    private static void writeRow(Sheet sheet, int rowIndex, String... values) {
        Row row = sheet.createRow(rowIndex);
        for (int valueIndex = 0; valueIndex < values.length; valueIndex++) {
            row.createCell(valueIndex).setCellValue(values[valueIndex]);
        }
    }
}