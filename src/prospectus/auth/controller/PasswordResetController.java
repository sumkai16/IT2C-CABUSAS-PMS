/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.auth.controller;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.dbConnector;
import prospectus.utilities.passwordHasher;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class PasswordResetController implements Initializable {

    @FXML
    private Button btnResetPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Button btnBack;
    private dbConnector db = new dbConnector(); 
    @FXML
    private AnchorPane overlayPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToLogin(MouseEvent event) {
        closeOverlay();
    }

    @FXML
    private void resetPassword(MouseEvent event) throws NoSuchAlgorithmException {
        String username = txtUsername.getText().trim();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String hashedconfirmedPassword = passwordHasher.hashPassword(newPassword);
        if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            utilities.showAlert( Alert.AlertType.ERROR,"Error", "All fields are required!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            utilities.showAlert( Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            return;
        }

        updatePassword(username, hashedconfirmedPassword);
    }
    
    private void updatePassword(String username, String newPassword) {
        String query = "UPDATE user SET u_password = ? WHERE LOWER(u_username) = LOWER(?)";

        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully!" );
                closeOverlay();
                backToLogin(null);
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Error", "User not found!" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Database error occurred!");
        }
    }
     private void closeOverlay() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));
        fadeOut.play();
    }
}
