package com.hotel.util;

import com.hotel.config.DatabaseConfig;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private Properties properties;

    private DatabaseConnection() {
        try {
            loadProperties();
            initializeConnection();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void loadProperties() throws Exception {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                properties.load(input);
            }
        }

        applyDefault("db.url", resolveConfigValue("DB_URL", DatabaseConfig.URL));
        applyDefault("db.username", resolveConfigValue("DB_USERNAME", DatabaseConfig.USERNAME));
        applyDefault("db.password", resolveConfigValue("DB_PASSWORD", DatabaseConfig.PASSWORD));
        applyDefault("db.driver", resolveConfigValue("DB_DRIVER", DatabaseConfig.DRIVER));
    }

    private void initializeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("db.driver"));
        this.connection = DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
        this.connection.setAutoCommit(true);
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeConnection();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to get DB connection", e);
        }
        return connection;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to close DB connection", e);
        }
    }

    public static synchronized void shutdown() {
        if (instance != null) {
            instance.closeConnection();
            instance = null;
        }
    }

    private void applyDefault(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            properties.setProperty(key, defaultValue);
        }
    }

    private String resolveConfigValue(String envKey, String fallback) {
        String value = System.getProperty(envKey);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(envKey);
        }
        if (value == null || value.trim().isEmpty()) {
            value = fallback;
        }
        return value;
    }
}
