package com.reetrak.automation.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        InputStream resourceStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (resourceStream == null) {
            return properties;
        }

        try (InputStream inputStream = resourceStream) {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load configuration from " + CONFIG_FILE, exception);
        }
        return properties;
    }

    public static String get(String key, String defaultValue) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue.trim();
        }

        String propertyValue = PROPERTIES.getProperty(key);
        if (propertyValue != null && !propertyValue.trim().isEmpty()) {
            return propertyValue.trim();
        }

        return defaultValue;
    }

    public static String getRequired(String key) {
        String value = get(key, null);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required configuration value for key: " + key);
        }
        return value;
    }

    public static Map<String, String> getAllWithPrefix(String prefix) {
        Map<String, String> values = new LinkedHashMap<>();

        for (String key : PROPERTIES.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                values.put(key.substring(prefix.length()), PROPERTIES.getProperty(key).trim());
            }
        }

        Properties systemProperties = System.getProperties();
        for (String key : systemProperties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String value = systemProperties.getProperty(key);
                if (value != null && !value.trim().isEmpty()) {
                    values.put(key.substring(prefix.length()), value.trim());
                }
            }
        }

        return values;
    }
}