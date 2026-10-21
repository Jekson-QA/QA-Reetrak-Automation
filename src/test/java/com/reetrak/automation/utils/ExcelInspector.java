package com.reetrak.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

public class ExcelInspector {

    public static void main(String[] args) throws Exception {
        String excelPath = "src/test/resources/testdata/ui-test-data.xlsx";
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet("ProductMaster");
            if (sheet == null) {
                System.out.println("Sheet ProductMaster not found!");
                return;
            }
            Row headerRow = sheet.getRow(0);
            System.out.println("\n==================== SCANNING Product-Master_TC_21 / ROW 22 ====================");

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                String tcId = getVal(row, headerRow, "Test Case ID");
                if (tcId.isEmpty()) tcId = getVal(row, headerRow, "TC ID");
                if (tcId.isEmpty()) tcId = getVal(row, headerRow, "TestCaseID");

                if (tcId.equalsIgnoreCase("Product-Master_TC_21") || (r + 1) == 22) {
                    System.out.println("Found Row " + (r + 1) + " [" + tcId + "]:");
                    String noOfLevels = getVal(row, headerRow, "nooflevels");
                    System.out.println("  nooflevels: '" + noOfLevels + "'");

                    for (int level = 1; level <= 6; level++) {
                        String prn = getVal(row, headerRow, "level" + level + "prn");
                        String img = getVal(row, headerRow, "level" + level + "image");
                        String partial = getVal(row, headerRow, "level" + level + "partial");
                        if (partial.isEmpty()) partial = getVal(row, headerRow, "level" + level + "Partial");
                        String pPrn = getVal(row, headerRow, "level" + level + "partialprn");
                        String pImg = getVal(row, headerRow, "level" + level + "partialimage");
                        String drop = getVal(row, headerRow, "level" + level + "drop");

                        System.out.println("  Level " + level + " (drop='" + drop + "'):");
                        System.out.println("     prn: '" + prn + "'");
                        System.out.println("     img: '" + img + "'");
                        System.out.println("     partial: '" + partial + "'");
                        System.out.println("     partialprn: '" + pPrn + "'");
                        System.out.println("     partialimage: '" + pImg + "'");

                        boolean hasPrnData = !prn.isEmpty() || !img.isEmpty() || !pPrn.isEmpty() || !pImg.isEmpty() || partial.equalsIgnoreCase("Yes");
                        if (hasPrnData) {
                            System.out.println("     --> hasPrnData = TRUE! prnIsBlank=" + prn.isEmpty());
                        } else {
                            System.out.println("     --> hasPrnData = false");
                        }
                    }
                }
            }
            System.out.println("=================================================================================\n");
        }
    }

    private static String getVal(Row row, Row headerRow, String colName) {
        for (int c = 0; c < headerRow.getLastCellNum(); c++) {
            Cell hCell = headerRow.getCell(c);
            if (hCell != null && colName.equalsIgnoreCase(hCell.toString().trim())) {
                Cell valCell = row.getCell(c);
                return valCell != null ? valCell.toString().trim() : "";
            }
        }
        return "";
    }
}
