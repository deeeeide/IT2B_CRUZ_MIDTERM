package com.canteen.dao;

import com.canteen.model.ScenarioEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public ScenarioDao() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS canteen_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_name TEXT NOT NULL,
                category TEXT NOT NULL,
                price REAL NOT NULL,
                stock INTEGER NOT NULL
            );
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database setup error: " + e.getMessage());
        }
    }

    public boolean addFoodItem(ScenarioEntity item) {
        String sql = "INSERT INTO canteen_items (item_name, category, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.itemName());
            pstmt.setString(2, item.category());
            pstmt.setDouble(3, item.price());
            pstmt.setInt(4, item.stock());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> getAllFoodItems() {
        List<ScenarioEntity> items = new ArrayList<>();
        String sql = "SELECT * FROM canteen_items";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return items;
    }
}