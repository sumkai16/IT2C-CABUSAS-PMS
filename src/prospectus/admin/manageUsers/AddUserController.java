package prospectus.admin.manageUsers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import prospectus.utilities.utilities;
import main.dbConnector;
import prospectus.utilities.validations;
import prospectus.auth.controller.RegisterPageController;
import prospectus.auth.controller.RegisterPageController;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import prospectus.auth.controller.RecoveryPhraseGenerator;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.passwordHasher;
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
    private Pane backgroundPane;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private ImageView profileImage;
    private String photoFilePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        
    }
    RegisterPageController rp = new RegisterPageController();
    @FXML
    private void addUserHandler(ActionEvent event) throws Exception {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        String firstName = firstnameF.getText();
        String middleName = middleF.getText();
        String lastName = lastnameF.getText();
        String emailAddress = emailF.getText();
        String phoneNumber = contactF.getText();
        String username = userFF.getText();
        String password = pwF.getText();
        String hashedEnteredPassword = passwordHasher.hashPassword(password);
        RecoveryPhraseGenerator generator = new RecoveryPhraseGenerator();
        String recoveryPhrase = generator.generateUniqueRecoveryPhrase();
       
        String profileImagePath = (photoFilePath != null) ? photoFilePath : "src/prospectus/images/users/default-user.png";

        // Updated query to include the profile image path
        String query = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_password, u_role, u_status, enrollment_status,recovery_phrase, u_image) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'User', 'Inactive', 'Not Enrolled',?, ?)";

        if (!rp.verifyUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
            if (db.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, hashedEnteredPassword,recoveryPhrase, profileImagePath)) {
                System.out.println("User added to database!");

                String usernamelog = UserSession.getUsername();
                logger.addLog(usernamelog, "User Added", "Admin added a user " + username);

                utilities.showAlert(Alert.AlertType.INFORMATION, "User successfully added!", "Added Completed!");
                clearFields();
                
            }
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
        profileImage.setImage(null);
        photoFilePath = null;
    }
     
    @FXML
    private void returnHandler(MouseEvent event) {
         utilities.closeOverlay(overlayPane);
    }

    @FXML
    private void selectProfileHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Define the destination folder path relative to your project
                String destinationFolder = "src/prospectus/images/users/";
                File destDir = new File(destinationFolder);

                if (!destDir.exists()) {
                    destDir.mkdirs(); // Create directory if it doesn't exist
                }

                // Create the destination file with the original file's name
                File destinationFile = new File(destDir, selectedFile.getName());

                // Copy the selected image to the destination folder
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Load and display the image
                Image image = new Image(destinationFile.toURI().toString());
                profileImage.setImage(image);

                // Store the relative path for database update
                 photoFilePath = destinationFolder + destinationFile.getName();

                // Log image selection (optional)
                System.out.println("Profile image saved at: " + photoFilePath);

            } catch (IOException e) {
                e.printStackTrace();
                utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to save profile photo.");
            }
        }
    }

    @FXML
    private void removeProfileHandler(MouseEvent event) {
        profileImage.setImage(null);
        photoFilePath = null;
    }

    
    
}
