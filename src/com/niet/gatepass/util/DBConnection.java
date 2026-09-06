package com.niet.gatepass.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central JDBC connection utility.
 * Update DB_URL / DB_USER / DB_PASSWORD to match your local MySQL setup.
 * Requires mysql-connector-j jar on the classpath (WebContent/WEB-INF/lib).
 */
public class DBConnection {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/gatepass_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Admin@123"; // change to your MySQL password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Add mysql-connector-j to WEB-INF/lib.", e);
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
