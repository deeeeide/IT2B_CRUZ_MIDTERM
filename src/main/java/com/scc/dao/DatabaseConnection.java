package com.canteen.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Connects to the new SQLite database file (SchoolCanteenItems.db) in the project root
    private static final String URL = "jdbc:sqlite:SchoolCanteenItems.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        // Enable foreign key constraints as required
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }
}