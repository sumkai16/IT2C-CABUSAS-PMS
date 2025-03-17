package auth.controller;

import main.dbConnector;
import utils.utilities;
import utils.validations;
import utils.PasswordHasher;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterPageController implements Initializable {

    @FXML
    private TextField firstnameF, lastnameF, emailF, contactF, userFF, middleF;
    @FXML
    private PasswordField pwF;
    @FXML
    private Button register, login;
    @FXML
    private Label registerBtn11, registerBtn1;
    @FXML
    private Pane registerPane;

    private dbConnector db;
    private validations vd = new validations();
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
    }

    @FXML
    void RegisterOnClickHandler(MouseEvent event) throws SQLException {
        Stage currentStage = (Stage) registerPane.getScene().getWindow();
        String firstName = firstnameF.getText().trim();
        String middleName = middleF.getText().trim();
        String lastName = lastnameF.getText().trim();
        String emailAddress = emailF.getText().trim();
        String phoneNumber = contactF.getText().trim();
        String username = userFF.getText().trim();
        String password = pwF.getText().trim();

        if (!verifyUser(currentStage, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
            try {
                // Hash the password before storing it
                String hashedPassword = PasswordHasher.hashPassword(password);

                String query = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_password, u_role, u_status, enrollment_status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, 'User', 'Inactive', 'Not Enrolled')";

                if (db.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, hashedPassword)) {
                    utilities.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Registration Completed!");
                    clearFields();
                    utilities.switchScene(getClass(), event, "/auth/fxml/LoginPage.fxml");
                }
            } catch (Exception ex) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to register user: " + ex.getMessage());
            }
        }
    }

    public boolean verifyUser(Stage currentStage, String firstName, String middleName, String lastName, String emailAddress, String phoneNumber, String username, String password) throws SQLException {
        if (firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error", "All fields are required.");
            return true;
        }

        if (!vd.isValidEmailFormat(emailAddress)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Email Error", "Invalid email format.");
            return true;
        }

        if (!vd.isValidPhoneNumber(phoneNumber)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Phone Number Error", "Phone number must contain only numbers.");
            return true;
        }

        if (!vd.isValidPhoneNumberFormat(phoneNumber)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Phone Number Error", "Invalid phone number format. Must start with 9.");
            return true;
        }

        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            utilities.showAlert(Alert.AlertType.ERROR, "Password Error", "Password must be at least 8 characters.");
            return true;
        }

        if (vd.isDuplicated("u_username", username)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Username Error", "Username already exists.");
            return true;
        }

        if (vd.isDuplicated("u_email", emailAddress)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Email Error", "Email already exists.");
            return true;
        }

        return false;
    }

    private void clearFields() {
        firstnameF.clear();
        lastnameF.clear();
        emailF.clear();
        contactF.clear();
        userFF.clear();
        pwF.clear();
        middleF.clear();
    }

    @FXML
    private void LoginHandler(MouseEvent event) {
        try {
            utilities.animatePaneTransitionRightToLeft(getClass(), event, "/auth/fxml/LoginPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }
}
