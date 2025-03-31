/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.user.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import prospectus.models.Programs;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Add items to the Sex Menu
        MenuItem male = new MenuItem("Male");
        MenuItem female = new MenuItem("Female");

        male.setOnAction(this::selectSex);
        female.setOnAction(this::selectSex);

        sexMenu.getItems().addAll(male, female);

        // Add items to the Program Menu (Example Programs)
        MenuItem bsit = new MenuItem("BSIT");
        MenuItem bscs = new MenuItem("BSCS");
        MenuItem beed = new MenuItem("BEED");

        bsit.setOnAction(this::selectProgram);
        bscs.setOnAction(this::selectProgram);
        beed.setOnAction(this::selectProgram);

        programMenu.getItems().addAll(bsit, bscs, beed);
    }    

    private void selectSex(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        sexMenu.setText(selectedItem.getText()); // Set the selected value to MenuButton
    }

    private void selectProgram(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getSource();
        programMenu.setText(selectedItem.getText()); // Set the selected value to MenuButton
    }


    @FXML
    private void returnHandler(MouseEvent event) {
         utilities.closeOverlay(overlayPane);
    }

    @FXML
    private void submitEnrollmentHandler(MouseEvent event) {
                // Validate input fields
        if (fnameField.getText().isEmpty() || lnameField.getText().isEmpty() ||
            bdateField.getValue() == null || addressField.getText().isEmpty() || 
            yearField.getText().isEmpty()) {

            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill out all fields.");
            return;
        }

        // Get input values
        String fname = fnameField.getText();
        String mname = mnameField.getText();
        String lname = lnameField.getText();
        String birthdate = bdateField.getValue().toString();
        String address = addressField.getText();
        String year = yearField.getText();
        int userId = UserSession.getUserId(); // Fetch the logged-in user ID

        // Assuming program_id is retrieved correctly
        int programId = Programs.getDepartment(programMenu); 

        // Correct SQL Queries
        String enrollQuery = "INSERT INTO enrollment (userID, prog_id, enrollment_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateRoleQuery = "UPDATE user SET u_role = 'Student',enrollment_status = 'Enrolled'  WHERE u_id = ?";

        try {
            db = new dbConnector(); // Ensure `dbConnector` has `getConnection()`
            Connection conn = db.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Insert Enrollment Record
            PreparedStatement pstEnroll = conn.prepareStatement(enrollQuery);
            pstEnroll.setInt(1, userId);
            pstEnroll.setInt(2, programId);
            pstEnroll.executeUpdate();

            // Update User Role to Student
            PreparedStatement pstUpdate = conn.prepareStatement(updateRoleQuery);
            pstUpdate.setInt(1, userId);
            pstUpdate.executeUpdate();

            conn.commit(); // Commit transaction
            conn.close();

            utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Enrollment Successful! You are now Enrolled.");
            utilities.switchScene(getClass(), event, "/prospectus/student/fxml/StudentDashboard.fxml"); // Redirect to Dashboard

        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Enrollment failed: " + ex.getMessage());
        }

    }

    
}
