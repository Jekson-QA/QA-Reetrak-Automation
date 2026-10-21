package com.reetrak.automation.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final String SCREENSHOT_DIRECTORY = "test-output/screenshots";
    private static final DateTimeFormatter RUN_FOLDER_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
    private static final DateTimeFormatter FILE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
    private static final String RUN_FOLDER = RUN_FOLDER_FORMATTER.format(LocalDateTime.now());

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver,
                                 String methodName,
                                 boolean passed) {
        String timestamp = FILE_FORMATTER.format(LocalDateTime.now());
        String resultFolder = passed ? "Pass" : "Fail";
        String fileName = sanitize(methodName) + "_" + timestamp + ".png";
        Path destinationPath = Path.of(SCREENSHOT_DIRECTORY, RUN_FOLDER, resultFolder, fileName);
        File destination = destinationPath.toFile();

        try {
            FileUtils.forceMkdirParent(destination);
            Thread.sleep(2000);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, destination);
            return destination.getAbsolutePath();
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + methodName, exception);
        }
    }

    private static String sanitize(String value) {
        if (value == null || value.isBlank()) {
            return "unknown";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}