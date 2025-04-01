package prospectus.user.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String birthdate = bdateField.getValue().toString();
        String address = addressField.getText();
        String year = yearField.getText();
        int userId = UserSession.getUserId();

        Integer programId = (Integer) programMenu.getUserData();
        if (programId == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a valid program.");
            return;
        }

        String enrollQuery = "INSERT INTO enrollment (userID, prog_id, enrollment_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateRoleQuery = "UPDATE user SET u_role = 'Student', enrollment_status = 'Enrolled' WHERE u_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement pstEnroll = conn.prepareStatement(enrollQuery);
             PreparedStatement pstUpdate = conn.prepareStatement(updateRoleQuery)) {

            conn.setAutoCommit(false);

            pstEnroll.setInt(1, userId);
            pstEnroll.setInt(2, programId);
            pstEnroll.executeUpdate();

            pstUpdate.setInt(1, userId);
            pstUpdate.executeUpdate();

            conn.commit();
            utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Enrollment Successful! You are now Enrolled.");
            utilities.switchScene(getClass(), event, "/prospectus/student/fxml/StudentDashboard.fxml");

        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Enrollment failed: " + ex.getMessage());
        }
    }
}
