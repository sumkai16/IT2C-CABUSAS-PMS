package prospectus.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import main.dbConnector;

public class logger {

    private static final dbConnector db = new dbConnector();

    /**
     * Logs an action made by a user.
     *
     * @param username    Username of the user performing the action
     * @param action      Type of action (e.g., "Login", "Enroll", "Update")
     * @param description Detailed description of the action
     */
    public static void addLog(String username, String action, String description) {
        String query = "INSERT INTO logs (user_id, action, description, date_time) " +
                       "VALUES ((SELECT u_id FROM user WHERE u_username = ?), ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, action);
            pstmt.setString(3, description);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
                System.out.println("[✔] Log entry added | User: " + username +
                                   " | Action: " + action +
                                   " | Time: " + timestamp);
            }

        } catch (SQLException e) {
            System.err.println("[✘] Failed to log action for user '" + username + "'");
            System.err.println("    Error: " + e.getMessage());
        }
    }
}
