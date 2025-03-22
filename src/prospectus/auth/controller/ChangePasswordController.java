package prospectus.auth.controller;

import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import prospectus.utilities.passwordHasher;

public class ChangePasswordController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button btnChangePassword;
    @FXML
    private Button btnCancel;
    @FXML
    private Label closeBtn;

    private dbConnector db = new dbConnector();
    private utilities utils = new utilities();
    @FXML
    private Pane dialogPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void changeClickHandler() {
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim(); 
        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            utils.showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required.");
            return;
        }

        if (newPassword.length() < 8) {
            utils.showAlert(Alert.AlertType.ERROR, "Password Error", "New password must be at least 8 characters.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            utils.showAlert(Alert.AlertType.ERROR, "Mismatch Error", "New password and confirmation do not match.");
            return;
        }

        try {
            updatePassword(oldPassword, newPassword);
        } catch (SQLException e) {
            utils.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to change password: " + e.getMessage());
        }
    }

    private void updatePassword(String oldPassword, String newPassword) throws SQLException {
        String sql = "UPDATE user SET u_password = ? WHERE u_id = ? AND u_password = ?";
        try (PreparedStatement pst = db.getConnection().prepareStatement(sql)) {
            pst.setString(1, newPassword);
            pst.setInt(2, UserSession.getUserId());
            pst.setString(3, oldPassword);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                utils.showAlert(Alert.AlertType.INFORMATION, "Success", "Password changed successfully.");
                closeOverlay();
            } else {
                utils.showAlert(Alert.AlertType.ERROR, "Error", "Incorrect old password.");
            }
        }
    }

    private void closeOverlay() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));
        fadeOut.play();
    }

    @FXML
    private void closeClickHandler(MouseEvent event) {
        closeOverlay();
    }

    @FXML
    private void cancelOnClickHandler(MouseEvent event) {
        closeOverlay();
    }
}
