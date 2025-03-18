package auth.controller;

import main.dbConnector;
import utils.utilities;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import models.UserSession;
import utils.passwordHasher;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Button loginBtn;

    private dbConnector db;
    @FXML
    private Label registerbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
    }

    @FXML
    private void LoginOnClickHandler(ActionEvent event) throws NoSuchAlgorithmException {
        String username = userF.getText().trim();
        String password = passF.getText().trim();
        String hashedEnteredPassword = passwordHasher.hashPassword(password);

        if (username.isEmpty() || password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        try {
            String[] userInfo = authenticateUser(username, hashedEnteredPassword);

            if (userInfo != null) {
                String role = userInfo[0];
                String u_status = userInfo[1]; // Fetching user status
                String e_status = userInfo[2]; // Fetching enrollment status

                if (u_status.equalsIgnoreCase("Inactive")) {
                    utilities.showAlert(Alert.AlertType.WARNING, "Account Inactive", "Your account is currently inactive. Please contact the administrator.");
                    return; // Prevent login
                }

                utilities.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back!");

                if (role.equalsIgnoreCase("Admin")) {
                    utilities.switchScene(getClass(), event, "/admin/fxml/AdminDashboard.fxml");
                } else if (e_status.equalsIgnoreCase("Not Enrolled")) {
                    utilities.switchScene(getClass(), event, "/user/fxml/UserDashboard.fxml"); 
                } else {
                    utilities.switchScene(getClass(), event, "/student/fxml/StudentDashboard.fxml");
                }
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Database connection failed: " + ex.getMessage());
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }



    public String[] authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT u_id, u_fname, u_lname, u_role, u_status, enrollment_status FROM user WHERE u_username = ? AND u_password = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("u_id");
                    String firstName = rs.getString("u_fname");
                    String lastName = rs.getString("u_lname");
                    String role = rs.getString("u_role");
                    String u_status = rs.getString("u_status"); 
                    String enrollment_status = rs.getString("enrollment_status"); 

                    // Store session
                    UserSession.createSession(userId, firstName, lastName, role, u_status, enrollment_status);

                    return new String[]{role, u_status, enrollment_status}; 
                }
            }
        }
        return null; 
    }



    @FXML
    private void registerHandler(MouseEvent event) {
         try {
            utilities.animatePaneTransitionLeftToRight(getClass(), event, "/auth/fxml/RegisterPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Faialed to open Register page: " + ex.getMessage());
        }
    }
}
