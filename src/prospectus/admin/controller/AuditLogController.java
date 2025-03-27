package prospectus.admin.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.dbConnector;

public class AuditLogController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TextArea logTextArea;

    private static dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       System.out.println("Initializing AuditLogController...");
        if (logTextArea == null) {
            System.out.println("logTextArea is NULL!");
        } else {
            System.out.println("logTextArea is properly initialized.");
        }
        loadLogs();
    }

    public void loadLogs() {
        try (Connection conn = db.getConnection()) {
            if (conn == null) {
                System.out.println("Database connection failed!");
                return;
            }

            String query = "SELECT user.u_username, logs.action, logs.description, logs.date_time " +
                           "FROM logs " +
                           "JOIN user ON logs.user_id = user.u_id " +
                           "ORDER BY logs.date_time DESC";

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            logTextArea.clear(); // Clear previous logs

            while (rs.next()) {
                String username = rs.getString("u_username");
                String action = rs.getString("action");
                String description = rs.getString("description");
                String dateTime = rs.getTimestamp("date_time").toString();

                String logEntry = formatLogEntry(dateTime, username, action, description);
                logTextArea.appendText(logEntry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logTextArea.appendText("Error loading logs: " + e.getMessage() + "\n");
        }
    }
    public String formatLogEntry(String dateTime, String username, String action, String description) {
        return "[" + dateTime + "]\t[" + username + "]\t[" + action + "]\t[" + description + "]\n";
    }


    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadLogs(); // Reload full logs if search is empty
            return;
        }

        StringBuilder filteredLogs = new StringBuilder();

        String query = "SELECT user.u_username, logs.action, logs.description, logs.date_time " +
                       "FROM logs " +
                       "JOIN user ON logs.user_id = user.u_id " +
                       "ORDER BY logs.date_time DESC";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");

            while (rs.next()) {
                String username = rs.getString("u_username");
                String action = rs.getString("action");
                String description = rs.getString("description");
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                String formattedTimestamp = dateTime.format(formatter);

                String logEntry = "[" + formattedTimestamp + "] " + username + " - " + action + ": " + description + "\n";

                if (logEntry.toLowerCase().contains(searchText)) {
                    filteredLogs.append(logEntry);
                }
            }

            logTextArea.setText(filteredLogs.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            logTextArea.appendText("Error searching logs: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadLogs(); // Reload logs from database
    }
}
