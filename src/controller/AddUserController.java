package controller;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.utilities;
import main.dbConnector;
import utils.validations;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class AddUserController implements Initializable {

    @FXML
    private TextField firstnameF;
    @FXML
    private TextField lastnameF;
    @FXML
    private TextField emailF;
    @FXML
    private TextField contactF;
    @FXML
    private TextField userFF;
    @FXML
    private PasswordField pwF;
    @FXML
    private TextField middleF;
    @FXML
    private Button addUser;

    private dbConnector db;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane backgroundPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
    }

    @FXML
    private void addUserHandler(ActionEvent event) {
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
            try (Connection conn = db.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

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
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + ex.getMessage());
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
