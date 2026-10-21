package com.reetrak.automation.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DatabaseUtils {

    private DatabaseUtils() {
    }

    private static Connection openConnection() throws SQLException {
        loadDriverIfConfigured();
        return DriverManager.getConnection(buildJdbcUrl(),
                ConfigReader.getRequired("database.username"),
                ConfigReader.get("database.password", ""));
    }

    private static void loadDriverIfConfigured() {
        String driverClass = ConfigReader.get("database.driver", "");
        if (!driverClass.isBlank()) {
            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Failed to load configured JDBC driver: " + driverClass, e);
            }
        }
    }

    private static String buildJdbcUrl() {
        String configuredUrl = ConfigReader.get("database.url", "");
        if (!configuredUrl.isBlank()) {
            return configuredUrl;
        }

        String type = ConfigReader.getRequired("database.type").toLowerCase();
        String host = ConfigReader.getRequired("database.ip");
        String database = ConfigReader.getRequired("database.name");

        if ("mysql".equals(type)) {
            String port = ConfigReader.getRequired("database.port");
            return "jdbc:mysql://" + host + ":" + port + "/" + database + "?allowMultiQueries=true";
        }
        if ("mssql".equals(type) || "sqlserver".equals(type)) {
            String port = ConfigReader.get("database.port", "");
            String instance = ConfigReader.get("database.instance", "");
            if (instance.isBlank()) {
                instance = ConfigReader.get("database.instanceName", "");
            }

            if (port.isBlank() && instance.isBlank()) {
                throw new IllegalStateException("Either database.port or database.instance must be configured for mssql");
            }

            StringBuilder urlBuilder = new StringBuilder("jdbc:sqlserver://").append(host);
            if (!instance.isBlank()) {
                urlBuilder.append("\\").append(instance);
            }
            if (!port.isBlank()) {
                urlBuilder.append(":").append(port);
            }
            urlBuilder.append(";databaseName=").append(database);
            urlBuilder.append(";encrypt=false;trustServerCertificate=true");
            return urlBuilder.toString();
        }

        throw new IllegalArgumentException("Unsupported database.type: " + type);
    }

    /**
     * Fetches a setting value string from a key-value DB table.
     * Example: DatabaseUtils.getDbSettingValue("common_setting", "field_name", "field_value", "HIERARCHY_MASTER")
     */
    public static String getDbSettingValue(String tableName, String keyColumn, String valueColumn, String keyValue) {
        String sql = "SELECT " + valueColumn + " FROM " + tableName + " WHERE " + keyColumn + " = ?";
        try (Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, keyValue.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(valueColumn);
                }
            }
        } catch (Exception e) {
            System.err.println("Warning: DB setting lookup failed for " + tableName + ": " + e.getMessage());
        }
        return null;
    }
}
