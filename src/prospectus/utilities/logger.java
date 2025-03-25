/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.dbConnector;
import prospectus.models.UserSession;

/**
 *
 * @author axcee
 */
public class logger {
    private static dbConnector db = new dbConnector();

    // Method to add a log entry
    public static void addLog(String username, String action, String description) {
        String query = "INSERT INTO logs (user_id, action, description, date_time) " +
                       "VALUES ((SELECT u_id FROM user WHERE u_username = ?), ?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);  
            pstmt.setString(2, action);
            pstmt.setString(3, description);

            pstmt.executeUpdate();
            System.out.println("Log added successfully.");

        } catch (SQLException e) {
            System.out.println("Error logging action: " + e.getMessage());
        }
    }

}
