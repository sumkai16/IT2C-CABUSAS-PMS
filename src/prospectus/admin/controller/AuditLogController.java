package prospectus.admin.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.dbConnector;

public class AuditLogController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> logsListView;
    @FXML
    private Label totalLogsLabel;
    @FXML
    private Label dateRangeLabel;

    private final ObservableList<String> logEntries = FXCollections.observableArrayList();
    private static final dbConnector db = new dbConnector();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing AuditLogController...");
        logsListView.setItems(logEntries);
        updateDateRangeLabel();  
        loadLogs();
    }

    private void updateDateRangeLabel() {
        LocalDateTime now = LocalDateTime.now();
        String month = now.getMonth().toString();
        dateRangeLabel.setText(month.substring(0, 1) + month.substring(1).toLowerCase());
    }

    public void loadLogs() {
        logEntries.clear();
        
        try (Connection conn = db.getConnection()) {
            if (conn == null) {
                System.out.println("[Error] Database connection failed!");
                return;
            }

            String query = "SELECT user.u_username, logs.action, logs.description, logs.date_time " +
                         "FROM logs " +
                         "JOIN user ON logs.user_id = user.u_id " +
                         "ORDER BY logs.date_time DESC";

            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {

                int totalLogs = 0;
                while (rs.next()) {
                    String username = rs.getString("u_username");
                    String action = rs.getString("action");
                    String description = rs.getString("description");
                    LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();

                    String formattedEntry = formatLogEntry(dateTime, username, action, description);
                    logEntries.add(formattedEntry);
                    totalLogs++;
                }

                totalLogsLabel.setText("Total Entries: " + totalLogs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logEntries.add("[Error] Failed to load logs: " + e.getMessage());
        }
    }

    private String formatLogEntry(LocalDateTime dateTime, String username, String action, String description) {
        String formattedDate = dateTime.format(DATE_FORMATTER);
        return String.format("%s\n%s • %s\n%s",
            formattedDate,
            username,
            action,
            description
        );
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            loadLogs();
            return;
        }

        try (Connection conn = db.getConnection()) {
            if (conn == null) {
                System.out.println("[Error] Database connection failed!");
                return;
            }

            String query = "SELECT user.u_username, logs.action, logs.description, logs.date_time " +
                         "FROM logs " +
                         "JOIN user ON logs.user_id = user.u_id " +
                         "WHERE LOWER(user.u_username) LIKE ? " +
                         "OR LOWER(logs.action) LIKE ? " +
                         "OR LOWER(logs.description) LIKE ? " +
                         "ORDER BY logs.date_time DESC";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                String searchPattern = "%" + searchText + "%";
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);

                ResultSet rs = pstmt.executeQuery();
                logEntries.clear();

                int totalLogs = 0;
                while (rs.next()) {
                    String username = rs.getString("u_username");
                    String action = rs.getString("action");
                    String description = rs.getString("description");
                    LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();

                    String formattedEntry = formatLogEntry(dateTime, username, action, description);
                    logEntries.add(formattedEntry);
                    totalLogs++;
                }

                totalLogsLabel.setText("Found Entries: " + totalLogs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            logEntries.add("[Error] Failed to search logs: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        searchField.clear();
        loadLogs();
    }
}
