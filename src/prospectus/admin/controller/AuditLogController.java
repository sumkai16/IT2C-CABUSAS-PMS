package prospectus.admin.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import prospectus.models.LogEntry;
import main.dbConnector;

public class AuditLogController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<LogEntry> logsTable;
    @FXML
    private TableColumn<LogEntry, String> colUsername;
    @FXML
    private TableColumn<LogEntry, String> colAction;
    @FXML
    private TableColumn<LogEntry, String> colDescription;
    @FXML
    private TableColumn<LogEntry, String> colTimestamp;

    private ObservableList<LogEntry> logList = FXCollections.observableArrayList();
    private static dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        loadLogs();
    }

    public void loadLogs() {
        String query = "SELECT user.u_username, logs.action, logs.description, logs.date_time " +
                       "FROM logs " +
                       "JOIN user ON logs.user_id = user.u_id " +
                       "ORDER BY logs.date_time DESC";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            logList.clear();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss"); // Example: Mar 16, 2025 14:30:00

            while (rs.next()) {
                String username = rs.getString("u_username");
                String action = rs.getString("action");
                String description = rs.getString("description");

                // Convert SQL Timestamp to LocalDateTime and format
                LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
                String formattedTimestamp = dateTime.format(formatter);

                logList.add(new LogEntry(username, action, description, formattedTimestamp));
            }

            logsTable.setItems(logList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error loading logs: " + e.getMessage());
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {
            logsTable.setItems(logList); // Reset to full list
            return;
        }

        ObservableList<LogEntry> filteredList = FXCollections.observableArrayList();
        for (LogEntry log : logList) {
            if (log.getUsername().toLowerCase().contains(searchText) ||
                log.getAction().toLowerCase().contains(searchText) ||
                log.getDescription().toLowerCase().contains(searchText)) { // ✅ Include description in search
                filteredList.add(log);
            }
        }

        logsTable.setItems(filteredList); // Show filtered results
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadLogs(); // Reload logs from database
    }
}
