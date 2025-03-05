package controller;

import main.dbConnector;
import utils.hoveer;
import utils.utilities;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utils.validations;

public class RegisterPageController implements Initializable {

    @FXML
    private TextField firstnameF, lastnameF, emailF, contactF, userFF, middleF;
    @FXML
    private PasswordField pwF;
    @FXML
    private Button register, login;

    private dbConnector db;
    @FXML
    private Label registerBtn11;
    @FXML
    private Label registerBtn1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        hoveer hv = new hoveer();
        hv.btnAuth(register);
        hv.btnSwitch(login);
    }

    @FXML
    private void LoginHandler(ActionEvent event) {
        try {
            utilities.animatePaneTransitionRightToLeft(getClass(), event, "/fxml/LoginPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
    private void RegisterOnClickHandler(MouseEvent event) {
        String firstname = firstnameF.getText().trim();
        String lastname = lastnameF.getText().trim();
        String email = emailF.getText().trim();
        String contact = contactF.getText().trim();
        String username = userFF.getText().trim();
        String password = pwF.getText().trim();
        String middle = middleF.getText().trim();

        if (!validations.validateInputs(firstname, lastname, email, contact, username, password)) {
            return;
        }

        try {
            if (validations.isDuplicate("u_email", email)) {
                utilities.showAlert(Alert.AlertType.ERROR, "Duplicate Email", "This email is already registered.");
                return;
            }

            if (validations.isDuplicate("u_username", username)) {
                utilities.showAlert(Alert.AlertType.ERROR, "Duplicate Username", "This username is already taken.");
                return;
            }

            String sql = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_password, u_role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
                pst.setString(1, firstname);
                pst.setString(2, middle);
                pst.setString(3, lastname);
                pst.setString(4, email);
                pst.setString(5, contact);
                pst.setString(6, username);
                pst.setString(7, password);
                pst.setString(8, "user");
                pst.executeUpdate();
                utilities.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registered!");
                clearFields();
            }
        } catch (SQLException ex) {
                         ex.printStackTrace();

//            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + ex.getMessage());
        }
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
}