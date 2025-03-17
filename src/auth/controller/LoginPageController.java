package auth.controller;

import main.dbConnector;
import utils.utilities;
import utils.PasswordHasher;
import models.UserSession;
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

public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Button loginBtn;
    @FXML
    private Label registerbtn;

    private dbConnector db;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
    }
    
    @FXML
    private void LoginOnClickHandler(MouseEvent event) {
         String username = userF.getText().trim();
        String password = passF.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Validation Error", "Username and password cannot be empty.");
            return;
        }

        try {
            String[] userInfo = authenticateUser(username, password);

            if (userInfo != null) {
                String role = userInfo[0];
                String enrollmentStatus = userInfo[1];

                utilities.showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back!");

                if (role.equalsIgnoreCase("Admin")) {
                    utilities.switchScene(getClass(), event, "/admin/fxml/AdminDashboard.fxml");
                } else if (enrollmentStatus.equalsIgnoreCase("Not Enrolled")) {
                    utilities.switchScene(getClass(), event, "/user/fxml/UserDashboard.fxml");
                } else {
                    utilities.switchScene(getClass(), event, "/student/fxml/StudentDashboard.fxml");
                }
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Database connection failed: " + ex.getMessage());
        }
    }
    

    public String[] authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT u_id, u_fname, u_lname, u_role, enrollment_status, u_password FROM user WHERE u_username = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, username);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("u_password");
                    if (PasswordHasher.hashPassword(password).equals(storedHash)) {
                        return new String[]{rs.getString("u_role"), rs.getString("enrollment_status")};
                    }
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
            ex.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Register page: " + ex.getMessage());
        }
    }

    
}

