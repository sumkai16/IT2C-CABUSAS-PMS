package prospectus.user.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;

public class SettingsPageController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label userInfoLabel;
    
    @FXML
    private Pane adminSettingsPane;
    
    @FXML
    private Button changePasswordBtn;
    
    @FXML
    private Button updateContactBtn;
    
    @FXML
    private Button updateProfilePicBtn;
    
    @FXML
    private Button editPersonalInfoBtn;
    
    @FXML
    private Button twoFactorBtn;
    
    @FXML
    private Button sessionSettingsBtn;
    
    @FXML
    private Button manageRolesBtn;
    
    @FXML
    private Button userManagementBtn;
    
    @FXML
    private Button globalSettingsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set user information
        String fullName = UserSession.getFirstName() + " " + UserSession.getLastName();
        String role = UserSession.getRole();
        String status = UserSession.getEnrollmentStatus();
        
        userInfoLabel.setText("Full Name: " + fullName + " | Role: " + role + " | Status: " + status);
        
        // Show admin settings if user is admin
        if ("admin".equalsIgnoreCase(role)) {
            adminSettingsPane.setVisible(true);
        }
    }
    
    @FXML
    private void changePasswordHandler() {
        utilities.loadFXMLWithFade(rootPane, "/prospectus/auth/fxml/ChangePassword.fxml");
    }
    
    @FXML
    private void updateContactHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Contact update functionality will be available in the next update.");
    }
    
    @FXML
    private void updateProfilePicHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Profile picture update functionality will be available in the next update.");
    }
    
    @FXML
    private void editPersonalInfoHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Personal information edit functionality will be available in the next update.");
    }
    
    @FXML
    private void twoFactorHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Two-factor authentication will be available in the next update.");
    }
    
    @FXML
    private void sessionSettingsHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Session settings will be available in the next update.");
    }
    
    @FXML
    private void manageRolesHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Role management will be available in the next update.");
    }
    
    @FXML
    private void userManagementHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "User management will be available in the next update.");
    }
    
    @FXML
    private void globalSettingsHandler() {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Feature Coming Soon", "Global settings will be available in the next update.");
    }
} 