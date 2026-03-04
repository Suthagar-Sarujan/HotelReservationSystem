package com.hotel.config;

public final class DatabaseConfig {
    public static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "password";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private DatabaseConfig() {
    }
}
