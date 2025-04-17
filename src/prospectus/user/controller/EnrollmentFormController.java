package prospectus.user.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;

public class EnrollmentFormController implements Initializable {

    @FXML
    private TextField fnameField;
    @FXML
    private TextField mnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private DatePicker bdateField;
    @FXML
    private MenuButton sexMenu;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField yearField;
    @FXML
    private MenuButton programMenu;
    @FXML
    private Button submitButton;
    @FXML
    private Pane bgPane;
    private static dbConnector db = new dbConnector();
    @FXML
    private AnchorPane overlayPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPrograms();

        MenuItem male = new MenuItem("Male");
        MenuItem female = new MenuItem("Female");
        male.setOnAction(this::selectSex);
        female.setOnAction(this::selectSex);
        sexMenu.getItems().addAll(male, female);
        
    }

    private void loadPrograms() {
        String query = "SELECT p_id, p_program_name FROM program WHERE p_status = 'active'";
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            programMenu.getItems().clear();
            while (rs.next()) {
                int programId = rs.getInt("p_id");
                String programName = rs.getString("p_program_name");
                MenuItem programItem = new MenuItem(programName);
                programItem.setOnAction(event -> {
                    programMenu.setText(programName);
                    programMenu.setUserData(programId);
                });
                programMenu.getItems().add(programItem);
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load programs: " + ex.getMessage());
        }
    }

    private void selectSex(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        sexMenu.setText(selectedItem.getText());
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }

    @FXML
    private void submitEnrollmentHandler(MouseEvent event) {
        if (fnameField.getText().isEmpty() || lnameField.getText().isEmpty() ||
            bdateField.getValue() == null || addressField.getText().isEmpty() || 
            yearField.getText().isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill out all fields.");
            return;
        }

        String fname = fnameField.getText();
        String mname = mnameField.getText();
        String lname = lnameField.getText();
        LocalDate birthdate = bdateField.getValue(); // Use LocalDate directly
        String address = addressField.getText();
        int year = Integer.parseInt(yearField.getText()); // Convert to int
        int userId = UserSession.getUserId();

        Integer programId = (Integer) programMenu.getUserData();
        if (programId == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a valid program.");
            return;
        }

        String enrollQuery = "INSERT INTO enrollment (userID, prog_id, enrollment_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateRoleQuery = "UPDATE user SET u_role = 'Student', enrollment_status = 'Enrolled' WHERE u_id = ?";
        String insertStudentQuery = "INSERT INTO student (u_id, s_fname, s_mname, s_lname, s_bdate, s_address, s_year, s_program) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null; // Declare conn outside the try block

        try {
            conn = db.getConnection(); // Initialize the connection
            conn.setAutoCommit(false); // Set auto-commit to false

            try (PreparedStatement pstEnroll = conn.prepareStatement(enrollQuery);
                 PreparedStatement pstUpdate = conn.prepareStatement(updateRoleQuery);
                 PreparedStatement pstInsertStudent = conn.prepareStatement(insertStudentQuery)) {

                // Insert into enrollment table
                pstEnroll.setInt(1, userId);
                pstEnroll.setInt(2, programId);
                pstEnroll.executeUpdate();

                // Update user role and enrollment status
                pstUpdate.setInt(1, userId);
                pstUpdate.executeUpdate();

                // Insert into students table
                pstInsertStudent.setInt(1, userId);
                pstInsertStudent.setString(2, fname);
                pstInsertStudent.setString(3, mname);
                pstInsertStudent.setString(4, lname);
                pstInsertStudent.setDate(5, java.sql.Date.valueOf(birthdate)); // Convert LocalDate to java.sql.Date
                pstInsertStudent.setString(6, address);
                pstInsertStudent.setInt(7, year);
                pstInsertStudent.setInt(8, programId); // Assuming programId corresponds to s_program
                pstInsertStudent.executeUpdate();

                // Commit the transaction
                conn.commit();
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Enrollment Successful! You are now Enrolled.");
                utilities.switchScene(getClass(), event, "/prospectus/student/fxml/StudentDashboard.fxml");

            } catch (SQLException ex) {
                // Handle the exception and roll back the transaction
                if (conn != null) {
                    try {
                        conn.rollback(); // Roll back the transaction
                    } catch (SQLException rollbackEx) {
                        utilities.showAlert(Alert.AlertType.ERROR, "Rollback Error", "Rollback failed: " + rollbackEx.getMessage());
                    }
                }
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Enrollment failed: " + ex.getMessage());
            }

        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Connection Error", "Could not connect to the database: " + e.getMessage());
        } finally {
            // Close the connection if it was opened
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    utilities.showAlert(Alert.AlertType.ERROR, "Connection Close Error", "Could not close the connection: " + closeEx.getMessage());
                }
            }
        }
    }
}
