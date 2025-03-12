package auth.controller;

import main.dbConnector;
import utils.utilities;
import utils.hoveer;
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
import models.UserSession;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Button loginBtn;
    @FXML
    private Button register;

    private dbConnector db;
    @FXML
    private Label registerBtn11;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        hoveer hv = new hoveer();
        hv.btnAuth(loginBtn);
        hv.btnSwitch(register);
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
//                String fxmlPath = role.equalsIgnoreCase("Admin") ? "/fxml/AdminDashboard.fxml" : "/fxml/UserDashboard.fxml";      

            String fxmlPath;
            if(role.equalsIgnoreCase("Admin")) {
              utilities.switchScene(getClass(), event,  "/admin/fxml/AdminDashboard.fxml");
            } else {
                utilities.switchScene(getClass(), event,  "/user/fxml/UserDashboard.fxml");
            }

                
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Er23ror", "Database connection failed: " + ex.getMessage());
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashbodasard: " + ex.getMessage());
        }
    }

    public String authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT u_id, u_fname, u_lname, u_role FROM user WHERE u_username = ? AND u_password = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("u_id");
                    String firstName = rs.getString("u_fname");
                    String lastName = rs.getString("u_lname");
                    String role = rs.getString("u_role"); // Get user role first

                    // ✅ Store user data in session BEFORE returning
                    UserSession.createSession(userId, firstName, lastName);

                    return role; // ✅ Now return role
                }
            }
        }
        return null;
    }


    @FXML
    private void registerHandler(ActionEvent event) {
        try {
            utilities.animatePaneTransitionLeftToRight(getClass(), event, "/auth/fxml/RegisterPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Register page: " + ex.getMessage());
        }
    }
}
