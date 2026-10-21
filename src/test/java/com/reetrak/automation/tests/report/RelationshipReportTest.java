package com.reetrak.automation.tests.report;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reetrak.automation.base.BaseTest;
import com.reetrak.automation.pages.LoginLogoutPage;
import com.reetrak.automation.pages.report.RelationshipReportPage;

public class RelationshipReportTest extends BaseTest {

    private static final String LOGIN_SHEET = "testdata.login.sheet";
    private static final String RELATIONSHIP_REPORT_SHEET = "testdata.relationshipreport.sheet";

    @Test(priority = 1, description = "TC-1: Open Relationship Report screen and verify page title.")
    public void verifyRelationshipReportPageOpens() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> testData = pageData(RELATIONSHIP_REPORT_SHEET, "RelationshipReport_TC_01");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RelationshipReportPage page = new RelationshipReportPage(driver);
        page.openRelationshipReport();

        Assert.assertEquals(page.getRelationshipReportPageName(), "Relationship Report");
    }

    @Test(priority = 2, description = "TC-2: Select the values as per attached image and click on generate button.")
    public void verifyReportGeneration() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(RELATIONSHIP_REPORT_SHEET, "RelationshipReport_TC_02");

        String line = getAndSyncLineName(RELATIONSHIP_REPORT_SHEET, "RelationshipReport_TC_02");
        String batchLevel = value(testData, "Batch Level");
        String code = value(testData, "Code", "");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RelationshipReportPage page = new RelationshipReportPage(driver);
        page.openRelationshipReport();

        page.selectLine(line);
        page.clickFilter();

        page.selectBatch(createdBatch);
        page.selectBatchLevel(batchLevel);
        page.setCode(code);

        page.clickGenerate();

        // Verify page still contains the Report master details
        Assert.assertEquals(page.getRelationshipReportPageName(), "Relationship Report");
    }

    @Test(priority = 3, description = "TC-3: Click on CSV button and select With Salable in Salable selection dropdown.")
    public void verifyCSVExportWithSalable() throws Exception {
        Map<String, String> loginData = pageData(LOGIN_SHEET, "Login-Logout_TC_04");
        Map<String, String> poData = pageData("testdata.po.sheet", "POMaster_TC_04");
        Map<String, String> testData = pageData(RELATIONSHIP_REPORT_SHEET, "RelationshipReport_TC_03");

        String line = getAndSyncLineName(RELATIONSHIP_REPORT_SHEET, "RelationshipReport_TC_03");
        String batchLevel = value(testData, "Batch Level");
        String code = value(testData, "Code", "");
        String salableSelection = value(testData, "Salable Selection");
        String createdBatch = value(poData, "Batch Number");

        LoginLogoutPage loginPage = new LoginLogoutPage(driver);
        loginPage.open(baseUrl);
        loginPage.loginAs(value(loginData, "username"), value(loginData, "password"));

        RelationshipReportPage page = new RelationshipReportPage(driver);
        page.openRelationshipReport();

        page.selectLine(line);
        page.clickFilter();

        page.selectBatch(createdBatch);
        page.selectBatchLevel(batchLevel);
        page.setCode(code);

        page.clickGenerate();

        page.clickCSV();
        page.selectSalableSelection(salableSelection);
        page.submitSalableSelection();
        page.handleSaveAsPopup();

        waitForDownloadAndMove(createdBatch);

        Assert.assertEquals(page.getRelationshipReportPageName(), "Relationship Report");
    }

    private boolean isFileClosed(java.io.File file) {
        try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
            int unused = fis.read();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private java.io.File checkAndRenameTmpFile(java.io.File dir, String batchName) {
        if (dir == null || !dir.exists())
            return null;
        java.io.File[] files = dir.listFiles();
        if (files == null)
            return null;
        for (java.io.File file : files) {
            if (file.getName().toLowerCase().endsWith(".tmp")) {
                if (System.currentTimeMillis() - file.lastModified() < 600000) {
                    if (file.exists() && file.length() > 0 && isFileClosed(file)) {
                        try {
                            String content = java.nio.file.Files.readString(file.toPath());
                            if (content.contains(batchName)) {
                                java.io.File renamedFile = new java.io.File(dir,
                                        "Relationship Report_" + batchName + ".csv");
                                if (renamedFile.exists()) {
                                    renamedFile.delete();
                                }
                                if (file.renameTo(renamedFile)) {
                                    System.out.println("Successfully renamed .tmp file " + file.getName() + " to "
                                            + renamedFile.getName());
                                    return renamedFile;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Warning: Failed to read/rename .tmp file " + file.getName() + ": "
                                    + e.getMessage());
                        }
                    }
                }
            }
        }
        return null;
    }

    private void waitForDownloadAndMove(String batchName) throws Exception {
        java.io.File workspaceDir = new java.io.File(System.getProperty("user.dir"), "downloads");
        java.io.File homeDownloadsDir = new java.io.File(System.getProperty("user.home"), "Downloads");
        if (!workspaceDir.exists()) {
            workspaceDir.mkdirs();
        }

        java.io.File downloadedFile = null;
        boolean foundInHome = false;
        int timeoutSeconds = 30;

        for (int i = 0; i < timeoutSeconds; i++) {
            // Check workspace downloads folder for standard CSV
            java.io.File[] wsFiles = workspaceDir.listFiles();
            if (wsFiles != null) {
                for (java.io.File file : wsFiles) {
                    String name = file.getName();
                    if (name.toLowerCase().contains(batchName.toLowerCase())
                            && name.toLowerCase().contains("relationship report")
                            && !name.endsWith(".tmp") && !name.endsWith(".crdownload")) {
                        if (System.currentTimeMillis() - file.lastModified() < 600000) {
                            if (file.exists() && file.length() > 0 && isFileClosed(file)) {
                                downloadedFile = file;
                                foundInHome = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (downloadedFile != null)
                break;

            // Check home Downloads folder for standard CSV
            java.io.File[] homeFiles = homeDownloadsDir.listFiles();
            if (homeFiles != null) {
                for (java.io.File file : homeFiles) {
                    String name = file.getName();
                    if (name.toLowerCase().contains(batchName.toLowerCase())
                            && name.toLowerCase().contains("relationship report")
                            && !name.endsWith(".tmp") && !name.endsWith(".crdownload")) {
                        if (System.currentTimeMillis() - file.lastModified() < 600000) {
                            if (file.exists() && file.length() > 0 && isFileClosed(file)) {
                                downloadedFile = file;
                                foundInHome = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (downloadedFile != null)
                break;

            // Check workspace downloads for .tmp files
            java.io.File renamedWs = checkAndRenameTmpFile(workspaceDir, batchName);
            if (renamedWs != null) {
                downloadedFile = renamedWs;
                foundInHome = false;
                break;
            }

            // Check home Downloads for .tmp files
            java.io.File renamedHome = checkAndRenameTmpFile(homeDownloadsDir, batchName);
            if (renamedHome != null) {
                downloadedFile = renamedHome;
                foundInHome = true;
                break;
            }

            Thread.sleep(1000);
        }

        Assert.assertNotNull(downloadedFile, "Downloaded report file for batch '" + batchName
                + "' was not found in either workspace or home Downloads folder after " + timeoutSeconds + " seconds!");

        if (foundInHome) {
            java.io.File destFile = new java.io.File(workspaceDir, downloadedFile.getName());
            if (destFile.exists()) {
                destFile.delete();
            }
            boolean moved = downloadedFile.renameTo(destFile);
            if (!moved) {
                java.nio.file.Files.copy(downloadedFile.toPath(), destFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                downloadedFile.delete();
            }
            Assert.assertTrue(destFile.exists(),
                    "Failed to move downloaded file to workspace: " + destFile.getAbsolutePath());
            System.out.println("Downloaded file successfully moved from home Downloads to workspace: "
                    + destFile.getAbsolutePath());
        } else {
            System.out.println(
                    "Downloaded file successfully located directly in workspace: " + downloadedFile.getAbsolutePath());
        }
    }
}