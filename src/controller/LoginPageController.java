package controller;

import main.dbConnector;
import utils.utilities;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Button loginBtn;
    @FXML
    private Label registerBtn;

    private dbConnector db;
    @FXML
    private Label registerBtn11;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();  // Initialize the database connection
    }

    @FXML
    private void LoginOnClickHandler(ActionEvent event) {
        String username = userF.getText().trim();
        String password = passF.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        try {
            String role = authenticateUser(username, password);
            if (role != null) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back!");

                // Switch to the correct dashboard
                String fxmlPath = role.equalsIgnoreCase("admin") ? "/fxml/AdminDashboard.fxml" : "/fxml/UserDashboard.fxml";
                utilities.switchScene(getClass(), event, fxmlPath);
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred: " + ex.getMessage());
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    // Method to authenticate user and retrieve role
    private String authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT u_role FROM user WHERE u_username = ? AND u_password = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("u_role");  // Return user role if authentication is successful
            }
        }
        return null;
    }

    @FXML
    private void RegisterBtnOnClickHandler(MouseEvent event) {
        try {
            utilities.switchScene(getClass(), event, "/fxml/RegisterPage.fxml");  // Load Register Page
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Register page: " + ex.getMessage());
        }
    }
}
