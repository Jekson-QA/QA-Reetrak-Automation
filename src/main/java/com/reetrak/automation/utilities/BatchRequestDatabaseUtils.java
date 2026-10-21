package com.reetrak.automation.utilities;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BatchRequestDatabaseUtils {

    private static final String BATCH_REQUEST_TABLE = "batchrequest";
    private static final String BATCH_REQUEST_ID_COLUMN = "id";
    private static final String BATCH_REQUEST_MODE_COLUMN = "mode";
    private static final int ONLINE_MODE_VALUE = 0;
    private static final String EXECUTION_PROCEDURE = "BatchExecutionSOP";

    private BatchRequestDatabaseUtils() {
    }

    // public static void updateMode(int batchId) {
    // String sql = "UPDATE " + BATCH_REQUEST_TABLE
    // + " SET " + BATCH_REQUEST_MODE_COLUMN + " = ?"
    // + " WHERE " + BATCH_REQUEST_ID_COLUMN + " = ?";

    // try (Connection connection = openConnection();
    // PreparedStatement statement = connection.prepareStatement(sql)) {
    // statement.setInt(1, ONLINE_MODE_VALUE);
    // statement.setInt(2, batchId);
    // int updatedRows = statement.executeUpdate();
    // if (updatedRows == 0) {
    // throw new IllegalStateException("No " + BATCH_REQUEST_TABLE + " row updated
    // for batch id: " + batchId);
    // }
    // } catch (SQLException exception) {
    // throw new IllegalStateException("Unable to update batch mode for batch id: "
    // + batchId, exception);
    // }
    // }
    public static void updateMode(int batchId) {
        String updateBatchRequest = "UPDATE batchrequest SET mode=0 WHERE id = ?";
        try (Connection connection = openConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement updateStmt = connection.prepareStatement(updateBatchRequest);) {
                updateStmt.setInt(1, batchId);
                updateStmt.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to close batch with id: " + batchId, e);
        }
    }

    public static int getBatchId(String batchName) {
        String sql = "SELECT id FROM batchrequest WHERE batchName = ?";
        try (Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, batchName.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new IllegalStateException(
                    "No batchrequest row found for batch name: " + batchName);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to get batch id for batch name: " + batchName, e);
        }
    }

    public static int getFinalStatus(int batchId) {
        String sql = "SELECT finalStatus FROM batchrequest WHERE id = ?";
        try (Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, batchId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("finalStatus");
                }
            }
            throw new IllegalStateException("No batchrequest row found for batch id: " + batchId);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get finalStatus for batch id: " + batchId, e);
        }
    }

    public static int getIsActive(int batchId) {
        String sql = "SELECT isactive FROM batchrequest WHERE id = ?";
        try (Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, batchId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("isactive");
                }
            }
            throw new IllegalStateException("No batchrequest row found for batch id: " + batchId);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get isactive for batch id: " + batchId, e);
        }
    }

    public static boolean isLineSelectionWithReopenBatchEnabled() {
        String sql = "SELECT field_value FROM common_setting WHERE field_name = 'LINE_SELECTION_WITH_REOPEN_BATCH'";
        try (Connection connection = openConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                String val = rs.getString("field_value");
                if (val != null) {
                    val = val.trim();
                    return "true".equalsIgnoreCase(val) || "1".equals(val) || "yes".equalsIgnoreCase(val);
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println(
                    "Warning: Unable to get LINE_SELECTION_WITH_REOPEN_BATCH setting from database. Defaulting to false. Error: "
                            + e.getMessage());
            return false;
        }
    }

    public static void closeBatch(int batchId) {
        String updateBatchRequest = "UPDATE batchrequest SET finalStatus = 7 WHERE id = ?";
        String deleteLineBatchManager = "DELETE FROM line_batch_manager WHERE batchid = ?";
        try (Connection connection = openConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement updateStmt = connection.prepareStatement(updateBatchRequest);

                    PreparedStatement deleteStmt = connection.prepareStatement(deleteLineBatchManager)) {
                updateStmt.setInt(1, batchId);
                updateStmt.executeUpdate();
                deleteStmt.setInt(1, batchId);
                deleteStmt.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to close batch with id: " + batchId, e);
        }
    }

    public static void executeBatchExecutionProcedure(int batchId) {
        String dbType = ConfigReader.get("database.type", "mysql").toLowerCase();
        String dbName = ConfigReader.get("database.name", "");

        String scriptPath;
        if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
            scriptPath = "scripts/stored-procedures/batch_execution_mssql.sql";
        } else {
            scriptPath = "scripts/stored-procedures/batch_execution_mysql.sql";
        }

        String sql = null;
        try {
            sql = readScriptFromClasspath(scriptPath);
        } catch (Exception e) {
            System.err.println("Could not load script " + scriptPath
                    + ", falling back to stored procedure call. Error: " + e.getMessage());
        }

        if (sql != null && !sql.trim().isEmpty()) {
            String resolvedSql = sql
                    .replace("${databaseName}", dbName)
                    .replace("${batchId}", String.valueOf(batchId));

            try (Connection connection = openConnection();
                    java.sql.Statement statement = connection.createStatement()) {
                statement.execute(resolvedSql);
            } catch (SQLException exception) {
                throw new IllegalStateException("Unable to execute SQL script " + scriptPath
                        + " for batch id: " + batchId, exception);
            }
        } else {
            try (Connection connection = openConnection();
                    CallableStatement statement = connection.prepareCall("{call " + EXECUTION_PROCEDURE + "(?)}")) {
                statement.setInt(1, batchId);
                statement.execute();
            } catch (SQLException exception) {
                throw new IllegalStateException("Unable to execute " + EXECUTION_PROCEDURE
                        + " for batch id: " + batchId, exception);
            }
        }
    }

    private static String readScriptFromClasspath(String resourcePath) {
        try (InputStream inputStream = BatchRequestDatabaseUtils.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("SQL script file not found on classpath: " + resourcePath);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to read SQL script file from classpath: " + resourcePath, ex);
        }
    }

    private static Connection openConnection() throws SQLException {
        loadDriverIfConfigured();
        return DriverManager.getConnection(buildJdbcUrl(),
                ConfigReader.getRequired("database.username"),
                ConfigReader.get("database.password", ""));
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

        throw new IllegalStateException("Unsupported database.type: " + type);
    }

    private static void loadDriverIfConfigured() {
        String driverClass = ConfigReader.get("database.driver-class", "");
        if (driverClass.isBlank()) {
            String type = ConfigReader.get("database.type", "").toLowerCase();
            if ("mysql".equals(type)) {
                driverClass = "com.mysql.cj.jdbc.Driver";
            } else if ("mssql".equals(type) || "sqlserver".equals(type)) {
                driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            }
        }

        if (driverClass.isBlank()) {
            return;
        }

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("Database JDBC driver is not available: " + driverClass, exception);
        }
    }

    public static boolean checkProductExists(String productCode) {
        String sql = "SELECT COUNT(*) FROM product WHERE code = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productCode.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to check product existence for: " + productCode, e);
        }
        return false;
    }

    public static java.util.Map<String, Object> getProductDetails(String productCode) {
        String sql = "SELECT id, companyPrefixID, gtin1, gtin2, gtin3, gtin4, gtin5, gtin6 FROM product WHERE code = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productCode.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    java.util.Map<String, Object> details = new java.util.HashMap<>();
                    details.put("id", rs.getLong("id"));
                    details.put("companyPrefixID", rs.getObject("companyPrefixID"));
                    details.put("gtin1", rs.getString("gtin1"));
                    details.put("gtin2", rs.getString("gtin2"));
                    details.put("gtin3", rs.getString("gtin3"));
                    details.put("gtin4", rs.getString("gtin4"));
                    details.put("gtin5", rs.getString("gtin5"));
                    details.put("gtin6", rs.getString("gtin6"));
                    return details;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get product details for: " + productCode, e);
        }
        return null;
    }

    public static java.util.Map<String, Object> getCompanyPrefixDetails(String name) {
        String sql = "SELECT cp.id, pcm.type AS targetType, cp.pcmType "
                + "FROM companyprefix cp "
                + "LEFT JOIN productcompanymapping pcm ON cp.pcmType = pcm.id "
                + "WHERE cp.name = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    java.util.Map<String, Object> details = new java.util.HashMap<>();
                    details.put("id", rs.getLong("id"));
                    Object typeObj = rs.getObject("targetType");
                    int typeVal = (typeObj != null) ? rs.getInt("targetType") : rs.getInt("pcmType");
                    details.put("pcmType", typeVal);
                    details.put("type", typeVal);
                    return details;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get company prefix details for: " + name, e);
        }
        return null;
    }

    public static void updateProductCompanyPrefix(String productCode, long targetCompanyPrefixId) {
        String sql = "UPDATE product SET companyPrefixID = ? WHERE code = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, targetCompanyPrefixId);
            statement.setString(2, productCode.trim());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update companyPrefixID for product code: " + productCode, e);
        }
    }

    public static void updateExtLevelCodesType(int level, long productId, String gtin, int type) {
        String tableName = "ext_level" + level + "tp_codes";
        String sql = "UPDATE " + tableName + " SET type = ? WHERE productid = ? AND gtin = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, type);
            statement.setLong(2, productId);
            statement.setString(3, gtin);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update " + tableName + " for productid: " + productId, e);
        }
    }

    public static int getExtLevelCodesCount(int level, long productId, String gtin) {
        String tableName = "ext_level" + level + "tp_codes";
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE productid = ? AND gtin = ?";
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, productId);
            statement.setString(2, gtin.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to get code count for " + tableName + " and productid: " + productId, e);
        }
        return 0;
    }

    public static void updateEpcisEventConfiguration() {
        String[] queries = {
            "UPDATE epcis_event SET status=0,specimen=0,isactive=1 WHERE id=25",
            "UPDATE epcis_event SET status=0,specimen=1,isactive=1 WHERE id=26",
            "UPDATE epcis_event SET status=1,specimen=1,isactive=1 WHERE id=27",
            "UPDATE epcis_event SET status=1,specimen=2,isactive=0 WHERE id=28",
            "UPDATE epcis_event SET status=2,specimen=0,isactive=1 WHERE id=29",
            "UPDATE epcis_event SET status=-1,specimen=-1,isactive=0 WHERE id=30",
            "UPDATE epcis_event SET status=1,specimen=0,isactive=1 WHERE id=31",
            "UPDATE epcis_event SET status=1,specimen=0,isactive=1 WHERE id=32",
            "UPDATE epcis_event SET status=-1,specimen=-1,isactive=0 WHERE id=33",
            "UPDATE epcis_event SET status=-1,specimen=-1,isactive=1 WHERE id=34"
        };
        try (Connection conn = openConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            for (String sql : queries) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            System.out.println("epcis_event configuration updated successfully in database.");
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update epcis_event table in database", e);
        }
    }

    public static String getFilePathFromBusinessRuleDetails(String companyPrefixName) {
        String pcmSql = "SELECT pcmType FROM companyprefix WHERE name LIKE ? OR name = ?";
        String filePathSql = "SELECT filepath FROM businessruledetails WHERE id = ?";
        try (Connection conn = openConnection()) {
            int pcmType = 2;
            try (PreparedStatement stmt = conn.prepareStatement(pcmSql)) {
                String searchName = (companyPrefixName != null && !companyPrefixName.isBlank()) ? companyPrefixName.trim() : "TRACELINK 14";
                stmt.setString(1, "%" + searchName + "%");
                stmt.setString(2, searchName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int typeVal = rs.getInt("pcmType");
                        if (typeVal > 0) {
                            pcmType = typeVal;
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("pcmType lookup warning: " + e.getMessage());
            }

            try (PreparedStatement stmt = conn.prepareStatement(filePathSql)) {
                stmt.setInt(1, pcmType);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String filePath = rs.getString("filepath");
                        System.out.println("Retrieved businessruledetails filepath for id=" + pcmType + ": " + filePath);
                        return filePath;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to query businessruledetails filepath: " + e.getMessage());
        }
        return null;
    }

}