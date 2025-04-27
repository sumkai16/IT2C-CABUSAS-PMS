package prospectus.student.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import prospectus.utilities.UserSession;
import prospectus.utilities.utilities;

public class StudentProfileController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label program;

    @FXML
    private Label enrollmentStatus;

    @FXML
    private PasswordField currentPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button updateContactBtn;

    private File selectedImageFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load user information from UserSession
        name.setText(UserSession.getFullName());
        id.setText("User ID: " + UserSession.getUserId());
        program.setText(UserSession.getProgram());
        enrollmentStatus.setText(UserSession.isEnrolled() ? "Enrolled" : "Not Enrolled");
        
        // Load profile image
        String imagePath = UserSession.getProfileImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Image image = new Image(new File(imagePath).toURI().toString());
                profileImage.setImage(image);
            } catch (Exception e) {
                // Load default image if specified image is invalid
                loadDefaultImage();
            }
        } else {
            loadDefaultImage();
        }

        // Initialize contact information
        emailField.setText(UserSession.getEmail());
        phoneField.setText(UserSession.getPhoneNumber());
    }

    private void loadDefaultImage() {
        try {
            Image defaultImage = new Image(getClass().getResourceAsStream("/prospectus/images/default-profile.png"));
            profileImage.setImage(defaultImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectProfileHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedImageFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (selectedImageFile != null) {
            try {
                Image image = new Image(selectedImageFile.toURI().toString());
                profileImage.setImage(image);
                // TODO: Save the image path to the database
            } catch (Exception e) {
                e.printStackTrace();
                utilities.showAlert(Alert.AlertType.ERROR,"Error", "Failed to load the selected image.");
            }
        }
    }

    @FXML
    private void changePasswordHandler() {
        String currentPass = currentPassword.getText();
        String newPass = newPassword.getText();

        if (currentPass.isEmpty() || newPass.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR,"Error", "Please fill in all password fields.");
            return;
        }

        // TODO: Implement password change logic
        // 1. Verify current password
        // 2. Update password in database
        // 3. Show success message
        utilities.showAlert(Alert.AlertType.INFORMATION,"Success", "Password changed successfully.");
        currentPassword.clear();
        newPassword.clear();
    }

    @FXML
    private void updateContactHandler() {
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (email.isEmpty() || phone.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR,"Error", "Please fill in all contact fields.");
            return;
        }

        // TODO: Implement contact information update logic
        // 1. Update email and phone in database
        // 2. Update UserSession
        // 3. Show success message
        utilities.showAlert(Alert.AlertType.INFORMATION,"Success", "Contact information updated successfully.");
    }
} 