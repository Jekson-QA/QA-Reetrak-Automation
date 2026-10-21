package com.reetrak.automation.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final Duration ZERO_TIMEOUT = Duration.ZERO;
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(60);

    private DriverFactory() {
    }

    public static void initDriver(String browserName) {
        if (DRIVER.get() != null) {
            return;
        }

        String downloadPath = System.getProperty("user.dir") + "\\downloads";
        java.io.File downloadFolder = new java.io.File(downloadPath);
        if (!downloadFolder.exists()) {
            downloadFolder.mkdirs();
        }

        String browser = browserName == null ? "chrome" : browserName.toLowerCase(Locale.ENGLISH);
        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.setAcceptInsecureCerts(true);
                
                firefoxOptions.addPreference("browser.download.folderList", 2);
                firefoxOptions.addPreference("browser.download.dir", downloadPath);
                firefoxOptions.addPreference("browser.download.useDownloadDir", true);
                firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/csv,application/vnd.ms-excel,application/octet-stream");
                firefoxOptions.addPreference("browser.download.manager.showWhenStarting", false);
                
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                // driver = new EdgeDriver(new EdgeOptions());
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--inprivate");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--remote-allow-origins=*");
                options.setAcceptInsecureCerts(true);
                
                java.util.Map<String, Object> edgePrefs = new java.util.HashMap<>();
                edgePrefs.put("download.default_directory", downloadPath);
                edgePrefs.put("download.prompt_for_download", false);
                edgePrefs.put("download.directory_upgrade", true);
                edgePrefs.put("safebrowsing.enabled", false);
                edgePrefs.put("profile.default_content_settings.popups", 0);
                edgePrefs.put("profile.password_manager_leak_detection", false);
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", edgePrefs);
                
                driver = new EdgeDriver(options);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--disable-notifications");
                // chromeOptions.addArguments("--guest");
                chromeOptions.addArguments("--remote-allow-origins=*");
                
                java.util.Map<String, Object> chromePrefs = new java.util.HashMap<>();
                chromePrefs.put("download.default_directory", downloadPath);
                chromePrefs.put("download.prompt_for_download", false);
                chromePrefs.put("download.directory_upgrade", true);
                chromePrefs.put("safebrowsing.enabled", false);
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("profile.password_manager_leak_detection", false);
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ZERO_TIMEOUT);
        pageLoadTimeout(driver);
        DRIVER.set(driver);
    }

    private static void pageLoadTimeout(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}