package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/prospectus"; // Update your database name
    private static final String DB_USER = "root"; // Update your DB username if needed
    private static final String DB_PASSWORD = ""; // Update your DB password if needed

    private Connection connect;

    // Constructor: Establish connection
    public dbConnector() {
        try {
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Can't connect to database: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Ensure the connection is always open before using it
    public void ensureConnection() throws SQLException {
        if (connect == null || connect.isClosed()) {
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
    }

    // Function to save data
    public boolean insertData(String query, Object... values) {
        try {
            ensureConnection(); // Ensure connection is open

            try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    pstmt.setObject(i + 1, values[i]); // Set values dynamically
                }
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0; // Returns true if insertion was successful
            }
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Function to retrieve data
    public ResultSet getData(String sql) throws SQLException {
        ensureConnection(); // Ensure connection is open
        Statement stmt = connect.createStatement();
        return stmt.executeQuery(sql);
    }

    // Function to update data
    public boolean updateData(String query, Object... params) {
        try {
            ensureConnection(); // Ensure connection is open

            try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Function to get the current connection
    public Connection getConnection() {
        try {
            ensureConnection(); // Ensure connection is open
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
