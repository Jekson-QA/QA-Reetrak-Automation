package com.reetrak.automation.reports;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomTestNgReporter implements IReporter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();
    private static final String REPORT_NAME = "custom-testng-report.html";
    private static final Path LIVE_REPORT_PATH = Path.of("test-output", REPORT_NAME).toAbsolutePath().normalize();
    private static final Object LIVE_REPORT_LOCK = new Object();
    private static final List<TestResultRow> LIVE_ROWS = new ArrayList<>();

    public static void resetLiveReport() {
        synchronized (LIVE_REPORT_LOCK) {
            LIVE_ROWS.clear();
        }
    }

    public static void recordResult(ITestResult result) {
        List<TestResultRow> snapshot;
        synchronized (LIVE_REPORT_LOCK) {
            LIVE_ROWS.add(createRow(result));
            snapshot = new ArrayList<>(LIVE_ROWS);
        }

        try {
            writeReport(LIVE_REPORT_PATH, buildHtml(sortedRows(snapshot), LIVE_REPORT_PATH));
        } catch (IOException exception) {
            System.err.println("Unable to update live TestNG report: " + exception.getMessage());
        }
    }

    public static void publishLiveReport() {
        List<TestResultRow> snapshot;
        synchronized (LIVE_REPORT_LOCK) {
            snapshot = new ArrayList<>(LIVE_ROWS);
        }

        try {
            writeReport(LIVE_REPORT_PATH, buildHtml(sortedRows(snapshot), LIVE_REPORT_PATH));
        } catch (IOException exception) {
            System.err.println("Unable to publish live TestNG report: " + exception.getMessage());
        }
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        List<TestResultRow> rows = sortedRows(collectRows(suites));

        Path reportPath = Path.of(outputDirectory, REPORT_NAME);

        try {
            writeReport(reportPath, buildHtml(rows, reportPath));
            Path testOutputReportPath = Path.of("test-output", REPORT_NAME).toAbsolutePath().normalize();
            if (!testOutputReportPath.equals(reportPath.toAbsolutePath().normalize())) {
                writeReport(testOutputReportPath, buildHtml(rows, testOutputReportPath));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write custom TestNG report", exception);
        }
    }

    private List<TestResultRow> collectRows(List<ISuite> suites) {
        List<TestResultRow> rows = new ArrayList<>();

        for (ISuite suite : suites) {
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                ITestContext context = suiteResult.getTestContext();
                addRows(rows, context.getPassedTests().getAllResults(), "PASSED");
                addRows(rows, context.getFailedTests().getAllResults(), "FAILED");
                addRows(rows, context.getSkippedTests().getAllResults(), "SKIPPED");
            }
        }

        return rows;
    }

    private static void writeReport(Path reportPath, String html) throws IOException {
        Files.createDirectories(reportPath.getParent());
        Files.writeString(reportPath, html, StandardCharsets.UTF_8);
        System.out.println("Custom TestNG report generated at: " + reportPath.toAbsolutePath());
    }

    private static List<TestResultRow> sortedRows(List<TestResultRow> rows) {
        rows.sort(Comparator
                .comparingInt(TestResultRow::priority)
                .thenComparing(TestResultRow::suiteName)
                .thenComparing(TestResultRow::testName)
                .thenComparing(TestResultRow::className)
                .thenComparing(TestResultRow::methodName)
                .thenComparingInt(TestResultRow::sortOrder));
        return rows;
    }

    private static String extractTestCaseNo(ITestResult result) {
        String excelTestCase = (String) result.getAttribute("excelTestCase");
        if (excelTestCase != null && !excelTestCase.isBlank()) {
            return excelTestCase.trim();
        }

        String testCaseName = (String) result.getAttribute("testCaseName");
        if (testCaseName != null && !testCaseName.isBlank()) {
            if (testCaseName.contains(":")) {
                String prefix = testCaseName.split(":")[0].trim();
                if (!prefix.isBlank()) {
                    return prefix;
                }
            }
            return testCaseName.trim();
        }

        String desc = result.getMethod().getDescription();
        if (desc != null && !desc.isBlank()) {
            if (desc.contains(":")) {
                String prefix = desc.split(":")[0].trim();
                if (prefix.matches("(?i)TC[-_]?\\d+|\\w+[-_]TC[-_]\\d+")) {
                    return prefix;
                }
            }
        }

        String methodName = result.getMethod().getMethodName();
        if (methodName != null && methodName.contains("_TC_")) {
            int index = methodName.indexOf("_TC_");
            return methodName.substring(index + 1);
        }

        return "P" + result.getMethod().getPriority();
    }

    private static TestResultRow createRow(ITestResult result) {
        String testCaseName = (String) result.getAttribute("testCaseName");
        if (testCaseName == null || testCaseName.isBlank()) {
            testCaseName = defaultString(result.getMethod().getDescription());
        }
        return new TestResultRow(
                statusFrom(result),
                result.getMethod().getPriority(),
                extractTestCaseNo(result),
                result.getTestContext().getSuite().getName(),
                result.getTestContext().getName(),
                result.getMethod().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                testCaseName,
                result.getStartMillis(),
                result.getEndMillis(),
                result.getEndMillis() - result.getStartMillis(),
                buildThrowableSummary(result),
                defaultString((String) result.getAttribute("screenshotPath"))
        );
    }

    private void addRows(List<TestResultRow> rows, Set<ITestResult> results, String status) {
        for (ITestResult result : results) {
            String testCaseName = (String) result.getAttribute("testCaseName");
            if (testCaseName == null || testCaseName.isBlank()) {
                testCaseName = defaultString(result.getMethod().getDescription());
            }
            rows.add(new TestResultRow(
                    status,
                    result.getMethod().getPriority(),
                    extractTestCaseNo(result),
                    result.getTestContext().getSuite().getName(),
                    result.getTestContext().getName(),
                    result.getMethod().getRealClass().getSimpleName(),
                    result.getMethod().getMethodName(),
                    testCaseName,
                    result.getStartMillis(),
                    result.getEndMillis(),
                    result.getEndMillis() - result.getStartMillis(),
                    buildThrowableSummary(result),
                    defaultString((String) result.getAttribute("screenshotPath"))
            ));
        }
    }

    private static String statusFrom(ITestResult result) {
        return switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "PASSED";
            case ITestResult.FAILURE -> "FAILED";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "SKIPPED";
        };
    }

    private static String buildHtml(List<TestResultRow> rows, Path reportPath) {
        int passedCount = countByStatus(rows, "PASSED");
        int failedCount = countByStatus(rows, "FAILED");
        int skippedCount = countByStatus(rows, "SKIPPED");
        int totalCount = rows.size();
        String suiteName = rows.stream()
                .map(TestResultRow::suiteName)
                .filter(name -> name != null && !name.isBlank())
                .findFirst()
                .orElse("Execution Suite");
        long startedAt = rows.stream().mapToLong(TestResultRow::startMillis).min().orElse(0L);
        long endedAt = rows.stream().mapToLong(TestResultRow::endMillis).max().orElse(0L);
        long durationMillis = Math.max(0L, endedAt - startedAt);

        Map<String, List<TestResultRow>> rowsByClass = new LinkedHashMap<>();
        for (TestResultRow row : rows) {
            rowsByClass.computeIfAbsent(row.className(), key -> new ArrayList<>()).add(row);
        }

        StringBuilder sectionHtml = new StringBuilder();
        for (Map.Entry<String, List<TestResultRow>> classEntry : rowsByClass.entrySet()) {
            String className = classEntry.getKey();
            List<TestResultRow> classRows = classEntry.getValue();
            int classPassedCount = countByStatus(classRows, "PASSED");
            int classFailedCount = countByStatus(classRows, "FAILED");
            int classSkippedCount = countByStatus(classRows, "SKIPPED");

            StringBuilder tableRows = new StringBuilder();
            for (TestResultRow row : classRows) {
                tableRows.append("""
                    <tr class="row-%s">
                        <td class="status-cell"><span class="status %s">%s</span></td>
                        <td class="screenshot-cell">%s</td>
                        <td class="testcase-cell"><span class="testcase-badge">%s</span></td>
                        <td class="method-cell">
                            <div class="description-cell">%s</div>
                        </td>
                        <td>%s</td>
                        <td class="error-cell">%s</td>
                    </tr>
                """.formatted(
                    row.status().toLowerCase(),
                    row.status().toLowerCase(),
                    escapeHtml(row.status()),
                    buildScreenshotLink(row.screenshotPath(), reportPath),
                    escapeHtml(row.testCaseNo()),
                    escapeHtml(row.description().isBlank() ? "-" : row.description()),
                    escapeHtml(formatDuration(row.durationMillis())),
                    escapeHtml(row.errorMessage().isBlank() ? "-" : row.errorMessage())
            ));
        }

            sectionHtml.append("""
                    <div class="class-section">
                        <div class="class-header">
                            <div>
                                <h3>%s</h3>
                                <div class="class-meta">Total: %d | Passed: %d | Failed: %d | Skipped: %d</div>
                            </div>
                        </div>
                        <div class="table-shell">
                            <table>
                                <thead>
                                    <tr>
                                        <th class="status-header">Status</th>
                                        <th class="screenshot-header">Screenshot</th>
                                        <th class="testcase-header">Test Case</th>
                                        <th>Test Description</th>
                                        <th>Duration</th>
                                        <th>Error</th>
                                    </tr>
                                </thead>
                                <tbody>
                    %s
                                </tbody>
                            </table>
                        </div>
                    </div>
                    """.formatted(
                    escapeHtml(className),
                    classRows.size(),
                    classPassedCount,
                    classFailedCount,
                    classSkippedCount,
                    tableRows
            ));
        }

        List<TestResultRow> failedRows = rows.stream()
                .filter(row -> row.status().equals("FAILED"))
                .toList();

        StringBuilder failedListHtml = new StringBuilder();
        if (failedRows.isEmpty()) {
            failedListHtml.append("<p class=\"no-failures\">🎉 All tests passed! No failures to display.</p>");
        } else {
            for (TestResultRow row : failedRows) {
                failedListHtml.append("""
                    <div class="failed-item">
                        <div class="failed-meta">
                            <span class="failed-class">%s</span>
                            <span class="failed-method">%s</span>
                            <span class="failed-testcase">%s</span>
                        </div>
                        <div class="failed-cause">%s</div>
                    </div>
                """.formatted(
                    escapeHtml(row.className()),
                    escapeHtml(row.methodName()),
                    escapeHtml(row.testCaseNo()),
                    escapeHtml(row.errorMessage().isBlank() ? "Unknown failure cause" : row.errorMessage())
                ));
            }
        }

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Custom TestNG Report</title>
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
                    <style>
                        :root {
                            --bg: #edf3ef;
                            --panel: rgba(255, 255, 255, 0.9);
                            --panel-strong: #ffffff;
                            --text: #172033;
                            --muted: #64748b;
                            --border: rgba(148, 163, 184, 0.18);
                            --pass: #0f766e;
                            --fail: #c2410c;
                            --skip: #a16207;
                            --link: #0f4c81;
                            --accent: #0f766e;
                            --accent-soft: #e6fffb;
                            --shadow: 0 26px 60px rgba(15, 23, 42, 0.12);
                        }
                        * { box-sizing: border-box; }
                        body {
                            margin: 0;
                            font-family: 'Plus Jakarta Sans', 'Segoe UI', sans-serif;
                            background:
                                radial-gradient(circle at top left, rgba(15, 118, 110, 0.14), transparent 30%),
                                radial-gradient(circle at top right, rgba(15, 76, 129, 0.16), transparent 28%),
                                radial-gradient(circle at bottom left, rgba(217, 119, 6, 0.08), transparent 32%),
                                linear-gradient(180deg, #f8fbf9 0%, var(--bg) 100%);
                            color: var(--text);
                        }
                        .wrap {
                            max-width: 1440px;
                            margin: 0 auto;
                            padding: 36px 22px 48px;
                        }
                        .hero, .panel {
                            background: var(--panel);
                            border: 1px solid var(--border);
                            border-radius: 28px;
                            box-shadow: var(--shadow);
                            backdrop-filter: blur(14px);
                        }
                        .hero {
                            position: relative;
                            overflow: hidden;
                            padding: 36px 36px 30px;
                            margin-bottom: 22px;
                        }
                        .hero::before {
                            content: "";
                            position: absolute;
                            inset: 0;
                            background:
                                linear-gradient(135deg, rgba(15, 118, 110, 0.08), transparent 42%),
                                linear-gradient(315deg, rgba(15, 76, 129, 0.08), transparent 35%),
                                radial-gradient(circle at 85% 12%, rgba(217, 119, 6, 0.12), transparent 24%);
                            pointer-events: none;
                        }
                        .hero::after {
                            content: "";
                            position: absolute;
                            inset: auto -110px -140px auto;
                            width: 280px;
                            height: 280px;
                            border-radius: 50%;
                            background: radial-gradient(circle, rgba(15, 118, 110, 0.12), transparent 68%);
                            pointer-events: none;
                        }
                        .hero-content {
                            position: relative;
                            z-index: 1;
                        }
                        h1, h2, h3 { margin: 0; }
                        h1 {
                            font-size: clamp(32px, 3vw, 42px);
                            letter-spacing: -0.03em;
                            line-height: 1.05;
                        }
                        .subtitle {
                            max-width: 760px;
                            margin-top: 12px;
                            color: var(--muted);
                            font-size: 15px;
                            line-height: 1.7;
                        }
                        .suite-banner {
                            display: inline-flex;
                            align-items: center;
                            margin-top: 10px;
                            padding: 10px 15px;
                            border-radius: 999px;
                            background: linear-gradient(135deg, rgba(15, 118, 110, 0.09), rgba(15, 76, 129, 0.08));
                            border: 1px solid rgba(15, 118, 110, 0.18);
                            color: #0f766e;
                            font-size: 13px;
                            font-weight: 700;
                        }
                        .headline-row {
                            display: flex;
                            justify-content: space-between;
                            gap: 20px;
                            align-items: flex-start;
                        }
                        .report-badge {
                            flex-shrink: 0;
                            padding: 12px 16px;
                            border-radius: 999px;
                            border: 1px solid rgba(15, 118, 110, 0.16);
                            background: linear-gradient(135deg, rgba(15, 118, 110, 0.12), rgba(15, 76, 129, 0.10));
                            color: #0f4c81;
                            font-size: 12px;
                            font-weight: 700;
                            letter-spacing: 0.08em;
                            text-transform: uppercase;
                            box-shadow: 0 10px 24px rgba(15, 23, 42, 0.08);
                        }
                        .metrics {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
                            gap: 14px;
                            margin-top: 26px;
                        }
                        .metric {
                            position: relative;
                            overflow: hidden;
                            padding: 18px 18px 19px;
                            border-radius: 20px;
                            background:
                                linear-gradient(180deg, rgba(255, 255, 255, 0.98), rgba(248, 250, 252, 0.88));
                            border: 1px solid var(--border);
                            box-shadow: 0 14px 32px rgba(15, 23, 42, 0.05);
                            transition: transform 160ms ease, box-shadow 160ms ease;
                        }
                        .metric:hover {
                            transform: translateY(-2px);
                            box-shadow: 0 18px 38px rgba(15, 23, 42, 0.08);
                        }
                        .metric::after {
                            content: "";
                            position: absolute;
                            left: 18px;
                            right: 18px;
                            top: 0;
                            height: 4px;
                            border-radius: 999px;
                            background: linear-gradient(90deg, rgba(15, 118, 110, 0.95), rgba(15, 76, 129, 0.95));
                        }
                        .metric.pass::after { background: linear-gradient(90deg, #12b981, #0f766e); }
                        .metric.fail::after { background: linear-gradient(90deg, #ea580c, #c2410c); }
                        .metric.skip::after { background: linear-gradient(90deg, #d97706, #a16207); }
                        .metric .label {
                            color: var(--muted);
                            font-size: 13px;
                            text-transform: uppercase;
                            letter-spacing: 0.08em;
                        }
                        .metric .value {
                            margin-top: 8px;
                            font-size: 28px;
                            font-weight: 700;
                            line-height: 1.2;
                        }
                        .panel {
                            padding: 30px;
                        }
                        .panel-head {
                            display: flex;
                            justify-content: space-between;
                            gap: 18px;
                            align-items: center;
                            margin-bottom: 2px;
                        }
                        .panel-copy {
                            margin-top: 10px;
                            color: var(--muted);
                            font-size: 14px;
                            max-width: 820px;
                            line-height: 1.7;
                        }
                        .legend {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 10px;
                        }
                        .legend span {
                            display: inline-flex;
                            align-items: center;
                            gap: 8px;
                            padding: 9px 13px;
                            border-radius: 999px;
                            background: linear-gradient(180deg, rgba(248, 250, 252, 0.98), rgba(255, 255, 255, 0.94));
                            border: 1px solid rgba(148, 163, 184, 0.18);
                            font-size: 12px;
                            color: #475569;
                            box-shadow: 0 10px 22px rgba(15, 23, 42, 0.05);
                        }
                        .legend i {
                            width: 10px;
                            height: 10px;
                            border-radius: 50%;
                            display: inline-block;
                        }
                        .legend .pass i { background: var(--pass); }
                        .legend .fail i { background: var(--fail); }
                        .legend .skip i { background: var(--skip); }
                        .table-shell {
                            margin-top: 18px;
                            border-radius: 22px;
                            overflow: hidden;
                            border: 1px solid rgba(148, 163, 184, 0.18);
                            background: var(--panel-strong);
                            box-shadow: 0 18px 42px rgba(15, 23, 42, 0.06);
                        }
                        .class-section {
                            margin-top: 20px;
                            padding: 18px 18px 6px;
                            border: 1px solid rgba(148, 163, 184, 0.16);
                            border-radius: 24px;
                            background: linear-gradient(180deg, rgba(255, 255, 255, 0.84), rgba(248, 250, 252, 0.9));
                            box-shadow: 0 14px 34px rgba(15, 23, 42, 0.04);
                        }
                        .class-header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            gap: 14px;
                            padding-bottom: 14px;
                            border-bottom: 1px solid rgba(148, 163, 184, 0.16);
                        }
                        .class-header h3 {
                            font-size: 19px;
                            color: #0f172a;
                        }
                        .class-meta {
                            margin-top: 6px;
                            color: #64748b;
                            font-size: 13px;
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            table-layout: auto;
                        }
                        th, td {
                            padding: 13px 12px;
                            border-bottom: 1px solid rgba(148, 163, 184, 0.15);
                            text-align: left;
                            vertical-align: top;
                            font-size: 14px;
                        }
                        th {
                            background: linear-gradient(180deg, rgba(248, 250, 252, 0.98), rgba(241, 245, 249, 0.98));
                            color: #64748b;
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.08em;
                            white-space: nowrap;
                            position: sticky;
                            top: 0;
                            z-index: 1;
                        }
                        tbody tr:hover {
                            background: rgba(248, 250, 252, 0.96);
                        }
                        .row-failed {
                            background: linear-gradient(90deg, rgba(194, 65, 12, 0.05), transparent 26%);
                        }
                        .row-passed {
                            background: linear-gradient(90deg, rgba(15, 118, 110, 0.04), transparent 26%);
                        }
                        .row-skipped {
                            background: linear-gradient(90deg, rgba(161, 98, 7, 0.05), transparent 26%);
                        }
                        .status {
                            display: inline-block;
                            min-width: 66px;
                            padding: 6px 10px;
                            border-radius: 999px;
                            color: #fff;
                            text-align: center;
                            font-weight: 700;
                            font-size: 11px;
                            letter-spacing: 0.05em;
                            box-shadow: 0 10px 22px rgba(15, 23, 42, 0.08);
                        }
                        .screenshot-header,
                        .screenshot-cell {
                            width: 72px;
                            text-align: center;
                            padding-left: 4px;
                            padding-right: 4px;
                            white-space: nowrap;
                        }
                        .status-cell {
                            width: 74px;
                            text-align: center;
                            padding-left: 4px;
                            padding-right: 4px;
                        }
                        .status-header {
                            width: 74px;
                            text-align: center;
                            white-space: nowrap;
                        }
                        .testcase-cell {
                            width: 170px;
                            min-width: 150px;
                            text-align: center;
                            padding-left: 4px;
                            padding-right: 4px;
                        }
                        .testcase-header {
                            width: 170px;
                            min-width: 150px;
                            text-align: center;
                            white-space: nowrap;
                        }
                        .screenshot-cell {
                            vertical-align: middle;
                            white-space: nowrap;
                        }
                        .status.passed { background: var(--pass); }
                        .status.failed { background: var(--fail); }
                        .status.skipped { background: var(--skip); }
                        .testcase-badge {
                            display: inline-block;
                            min-width: 80px;
                            padding: 6px 12px;
                            border-radius: 999px;
                            background: #fff4db;
                            border: 1px solid rgba(217, 119, 6, 0.24);
                            color: #9a6700;
                            text-align: center;
                            font-weight: 700;
                            font-size: 12px;
                            letter-spacing: 0.03em;
                            white-space: nowrap;
                            box-shadow: 0 10px 22px rgba(217, 119, 6, 0.08);
                        }
                        .suite-name,
                        .method-name {
                            font-weight: 700;
                            color: #0f172a;
                        }
                        .method-cell {
                            min-width: 300px;
                            width: auto;
                        }
                        .method-name,
                        .description-cell {
                            overflow-wrap: anywhere;
                            word-break: break-word;
                        }
                        .suite-meta {
                            margin-top: 4px;
                            color: var(--muted);
                            font-size: 12px;
                        }
                        .description-cell,
                        .error-cell {
                            line-height: 1.55;
                        }
                        .description-cell {
                            color: #334155;
                        }
                        .error-cell {
                            color: #b91c1c;
                            font-size: 13px;
                        }
                        .screenshot-link {
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            margin: 0 auto;
                            min-width: 54px;
                            padding: 5px 10px;
                            border-radius: 999px;
                            background: linear-gradient(135deg, rgba(15, 118, 110, 0.12), rgba(15, 76, 129, 0.12));
                            border: 1px solid rgba(15, 118, 110, 0.16);
                            color: var(--link);
                            font-size: 11px;
                            font-weight: 700;
                            white-space: nowrap;
                            box-shadow: 0 10px 22px rgba(15, 23, 42, 0.06);
                            transition: transform 140ms ease, box-shadow 140ms ease, background 140ms ease;
                        }
                        .screenshot-link:hover {
                            text-decoration: none;
                            transform: translateY(-1px);
                            box-shadow: 0 14px 24px rgba(15, 23, 42, 0.08);
                            background: linear-gradient(135deg, rgba(15, 118, 110, 0.18), rgba(15, 76, 129, 0.18));
                        }
                        a {
                            color: var(--link);
                            text-decoration: none;
                            font-weight: 600;
                            transition: color 140ms ease, text-decoration-color 140ms ease;
                        }
                        a:hover { text-decoration: underline; }
                        .empty {
                            color: var(--muted);
                            margin-top: 18px;
                            padding: 18px 20px;
                            border: 1px dashed rgba(148, 163, 184, 0.28);
                            border-radius: 18px;
                            background: rgba(248, 250, 252, 0.8);
                        }

                        /* Modal Overlay Styles */
                        .modal-overlay {
                            position: fixed;
                            inset: 0;
                            background: rgba(15, 23, 42, 0.45);
                            backdrop-filter: blur(8px);
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            z-index: 1000;
                            opacity: 0;
                            pointer-events: none;
                            transition: opacity 0.25s ease;
                        }
                        .modal-overlay.active {
                            opacity: 1;
                            pointer-events: all;
                        }
                        .modal-content {
                            background: var(--panel-strong);
                            border: 1px solid var(--border);
                            border-radius: 24px;
                            width: 90%;
                            max-width: 800px;
                            max-height: 80vh;
                            display: flex;
                            flex-direction: column;
                            box-shadow: 0 30px 70px rgba(15, 23, 42, 0.25);
                            transform: translateY(20px);
                            transition: transform 0.25s ease;
                        }
                        .modal-overlay.active .modal-content {
                            transform: translateY(0);
                        }
                        .modal-header {
                            padding: 20px 24px;
                            border-bottom: 1px solid rgba(148, 163, 184, 0.16);
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                        }
                        .modal-header h2 {
                            font-size: 20px;
                            color: var(--fail);
                            margin: 0;
                        }
                        .modal-close {
                            background: transparent;
                            border: none;
                            font-size: 28px;
                            cursor: pointer;
                            color: var(--muted);
                            transition: color 0.15s ease;
                        }
                        .modal-close:hover {
                            color: var(--text);
                        }
                        .modal-body {
                            padding: 24px;
                            overflow-y: auto;
                            flex: 1;
                        }
                        .failed-item {
                            background: linear-gradient(180deg, rgba(254, 242, 242, 0.8), rgba(254, 242, 242, 0.4));
                            border: 1px solid rgba(239, 68, 68, 0.2);
                            border-radius: 16px;
                            padding: 16px;
                            margin-bottom: 16px;
                            box-shadow: 0 4px 12px rgba(220, 38, 220, 0.03);
                        }
                        .failed-meta {
                            display: flex;
                            flex-wrap: wrap;
                            align-items: center;
                            gap: 10px;
                            margin-bottom: 8px;
                        }
                        .failed-class {
                            font-weight: 700;
                            color: var(--text);
                            font-size: 14px;
                        }
                        .failed-method {
                            background: rgba(239, 68, 68, 0.1);
                            color: var(--fail);
                            padding: 4px 10px;
                            border-radius: 6px;
                            font-family: monospace;
                            font-size: 13px;
                            font-weight: 600;
                        }
                        .failed-testcase {
                            background: #fff4db;
                            border: 1px solid rgba(217, 119, 6, 0.24);
                            color: #9a6700;
                            padding: 2px 8px;
                            border-radius: 999px;
                            font-size: 11px;
                            font-weight: 700;
                        }
                        .failed-cause {
                            color: #7f1d1d;
                            font-size: 13px;
                            line-height: 1.6;
                            word-break: break-word;
                            background: rgba(255, 255, 255, 0.7);
                            padding: 12px;
                            border-radius: 10px;
                            border: 1px solid rgba(239, 68, 68, 0.08);
                            font-family: Consolas, Monaco, monospace;
                            max-height: 150px;
                            overflow-y: auto;
                        }
                        .no-failures {
                            text-align: center;
                            color: var(--pass);
                            font-weight: 600;
                            font-size: 16px;
                            padding: 40px 0;
                        }

                        /* Action Button & Filters */
                        .action-btn {
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            padding: 10px 18px;
                            border-radius: 12px;
                            font-size: 13px;
                            font-weight: 700;
                            cursor: pointer;
                            border: none;
                            transition: all 0.2s ease;
                            box-shadow: 0 4px 12px rgba(15, 23, 42, 0.08);
                        }
                        .fail-btn {
                            background: linear-gradient(135deg, #ea580c, #c2410c);
                            color: white;
                        }
                        .fail-btn:hover {
                            transform: translateY(-1px);
                            box-shadow: 0 6px 16px rgba(194, 65, 12, 0.25);
                        }
                        .fail-btn:active {
                            transform: translateY(1px);
                        }
                        .filters {
                            display: flex;
                            gap: 8px;
                            margin-top: 18px;
                            margin-bottom: 6px;
                        }
                        .filter-btn {
                            background: var(--panel-strong);
                            border: 1px solid var(--border);
                            border-radius: 999px;
                            padding: 8px 16px;
                            font-size: 13px;
                            font-weight: 600;
                            color: var(--muted);
                            cursor: pointer;
                            box-shadow: 0 4px 10px rgba(15, 23, 42, 0.03);
                            transition: all 0.18s ease;
                        }
                        .filter-btn:hover {
                            background: rgba(248, 250, 252, 0.98);
                            color: var(--text);
                        }
                        .filter-btn.active {
                            background: var(--accent);
                            color: white;
                            border-color: var(--accent);
                            box-shadow: 0 6px 14px rgba(15, 118, 110, 0.25);
                        }

                        @media (max-width: 900px) {
                            .panel {
                                overflow-x: auto;
                            }
                            .headline-row,
                            .panel-head {
                                flex-direction: column;
                                align-items: flex-start;
                            }
                            table {
                                min-width: 900px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="wrap">
                        <section class="hero">
                            <div class="hero-content">
                                <div class="headline-row">
                                    <div>
                                        <h1>Automation Execution Report</h1>
                                        <div class="suite-banner">Suite: {{suiteName}}</div>
                                        <p class="subtitle">A polished TestNG dashboard with direct screenshot access for passed and failed validations, clearer execution visibility, and a cleaner review experience.</p>
                                    </div>
                                    <div class="report-badge">Quality Snapshot</div>
                                </div>
                                <div class="metrics">
                                    <div class="metric">
                                        <div class="label">Total Tests</div>
                                        <div class="value">{{totalCount}}</div>
                                    </div>
                                    <div class="metric pass">
                                        <div class="label">Passed</div>
                                        <div class="value">{{passedCount}}</div>
                                    </div>
                                    <div class="metric fail">
                                        <div class="label">Failed</div>
                                        <div class="value">{{failedCount}}</div>
                                    </div>
                                    <div class="metric skip">
                                        <div class="label">Skipped</div>
                                        <div class="value">{{skippedCount}}</div>
                                    </div>
                                    <div class="metric">
                                        <div class="label">Started</div>
                                        <div class="value">{{startedAt}}</div>
                                    </div>
                                    <div class="metric">
                                        <div class="label">Duration</div>
                                        <div class="value">{{duration}}</div>
                                    </div>
                                </div>
                            </div>
                        </section>
                        <section class="panel">
                            <div class="panel-head">
                                <div>
                                    <h2>Execution Details</h2>
                                    <div class="panel-copy">Review test outcomes, execution timing, stack details, and open screenshots directly from the report.</div>
                                </div>
                                <div style="display: flex; flex-direction: column; align-items: flex-end; gap: 12px;">
                                    <div class="legend">
                                        <span class="pass"><i></i> Passed</span>
                                        <span class="fail"><i></i> Failed</span>
                                        <span class="skip"><i></i> Skipped</span>
                                    </div>
                                    <div style="display: flex; gap: 10px;">
                                        <button id="view-failures-btn" class="action-btn fail-btn" onclick="toggleFailuresModal()">View Failure Causes</button>
                                    </div>
                                </div>
                            </div>
                            <div class="filters">
                                <button id="filter-all" class="filter-btn active" onclick="filterTests('all')">All Tests ({{totalCount}})</button>
                                <button id="filter-passed" class="filter-btn" onclick="filterTests('passed')">Passed ({{passedCount}})</button>
                                <button id="filter-failed" class="filter-btn" onclick="filterTests('failed')">Failed ({{failedCount}})</button>
                                <button id="filter-skipped" class="filter-btn" onclick="filterTests('skipped')">Skipped ({{skippedCount}})</button>
                            </div>
                            {{sectionHtml}}
                        </section>
                    </div>

                    <!-- Modal for Failed Methods -->
                    <div id="failures-modal" class="modal-overlay">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2>Failed Methods & Causes</h2>
                                <button class="modal-close" onclick="toggleFailuresModal()">&times;</button>
                            </div>
                            <div class="modal-body">
                                {{failedListHtml}}
                            </div>
                        </div>
                    </div>

                    <script>
                        function toggleFailuresModal() {
                            const modal = document.getElementById('failures-modal');
                            modal.classList.toggle('active');
                        }
                        
                        window.addEventListener('click', function(e) {
                            const modal = document.getElementById('failures-modal');
                            if (e.target === modal) {
                                modal.classList.remove('active');
                            }
                        });

                        function filterTests(status) {
                            document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
                            document.getElementById('filter-' + status).classList.add('active');
                            
                            document.querySelectorAll('tbody tr').forEach(row => {
                                if (status === 'all') {
                                    row.style.display = '';
                                } else if (status === 'passed' && row.classList.contains('row-passed')) {
                                    row.style.display = '';
                                } else if (status === 'failed' && row.classList.contains('row-failed')) {
                                    row.style.display = '';
                                } else if (status === 'skipped' && row.classList.contains('row-skipped')) {
                                    row.style.display = '';
                                } else {
                                    row.style.display = 'none';
                                }
                            });
                            
                            document.querySelectorAll('.class-section').forEach(section => {
                                const totalRows = section.querySelectorAll('tbody tr').length;
                                const hiddenRows = section.querySelectorAll('tbody tr[style*="display: none"]').length;
                                if (totalRows === hiddenRows) {
                                    section.style.display = 'none';
                                } else {
                                    section.style.display = '';
                                }
                            });
                        }
                    </script>
                </body>
                </html>
                """;

        html = html.replace("{{suiteName}}", escapeHtml(suiteName))
                   .replace("{{totalCount}}", String.valueOf(totalCount))
                   .replace("{{passedCount}}", String.valueOf(passedCount))
                   .replace("{{failedCount}}", String.valueOf(failedCount))
                   .replace("{{skippedCount}}", String.valueOf(skippedCount))
                   .replace("{{startedAt}}", escapeHtml(formatDateTime(startedAt)))
                   .replace("{{duration}}", escapeHtml(formatDuration(durationMillis)))
                   .replace("{{failedListHtml}}", failedListHtml.toString())
                   .replace("{{sectionHtml}}", rows.isEmpty()
                           ? "<p class=\"empty\">No test results were available for this execution.</p>"
                           : sectionHtml.toString());

        return html;
    }

    private static int countByStatus(List<TestResultRow> rows, String status) {
        return (int) rows.stream().filter(row -> row.status().equals(status)).count();
    }

    private static String buildThrowableSummary(ITestResult result) {
        StringBuilder sb = new StringBuilder();
        if (result.getThrowable() != null) {
            Throwable t = result.getThrowable();
            String message = t.getMessage() != null && !t.getMessage().isBlank()
                    ? t.getMessage().replaceAll("\\s+", " ").trim()
                    : t.toString();
            
            StackTraceElement lineElement = null;
            for (StackTraceElement element : t.getStackTrace()) {
                if (element.getClassName().startsWith("com.reetrak.automation")) {
                    lineElement = element;
                    break;
                }
            }
            
            sb.append(message);
            if (lineElement != null) {
                sb.append(" (at ").append(lineElement.getFileName())
                  .append(":").append(lineElement.getLineNumber())
                  .append(" in method ").append(lineElement.getMethodName()).append(")");
            }
        }
        @SuppressWarnings("unchecked")
        List<String> softErrors = (List<String>) result.getAttribute("softErrors");
        if (softErrors != null && !softErrors.isEmpty()) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append("Soft Errors/Skipped: ").append(String.join("; ", softErrors));
        }
        return sb.toString();
    }

    private static String buildScreenshotLink(String screenshotPath, Path reportPath) {
        if (screenshotPath == null || screenshotPath.isBlank()) {
            return "-";
        }
        Path target = Path.of(screenshotPath).toAbsolutePath().normalize();
        Path reportDirectory = reportPath.toAbsolutePath().normalize().getParent();

        try {
            String relativePath = reportDirectory.relativize(target).toString().replace('\\', '/');
            return "<a class=\"screenshot-link\" href=\"%s\" target=\"_blank\" rel=\"noopener noreferrer\">View</a>"
                    .formatted(escapeHtml(relativePath));
        } catch (IllegalArgumentException exception) {
            return "<a class=\"screenshot-link\" href=\"file:///%s\" target=\"_blank\" rel=\"noopener noreferrer\">View</a>"
                    .formatted(escapeHtml(target.toString().replace('\\', '/')));
        }
    }

    private static String formatDateTime(long epochMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMillis), SYSTEM_ZONE)
                .format(DATE_TIME_FORMATTER);
    }

    private static String formatDuration(long durationMillis) {
        Duration duration = Duration.ofMillis(durationMillis);
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).toSeconds();
        long millis = duration.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return "%02dm %02ds %03dms".formatted(minutes, seconds, millis);
    }

    private static String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private static String defaultString(String value) {
        return value == null ? "" : value;
    }

    private record TestResultRow(String status,
                                 int priority,
                                 String testCaseNo,
                                 String suiteName,
                                 String testName,
                                 String className,
                                 String methodName,
                                 String description,
                                 long startMillis,
                                 long endMillis,
                                 long durationMillis,
                                 String errorMessage,
                                 String screenshotPath) {

        private int sortOrder() {
            return switch (status) {
                case "FAILED" -> 0;
                case "SKIPPED" -> 1;
                default -> 2;
            };
        }
    }
}