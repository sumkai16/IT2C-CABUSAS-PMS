package prospectus.user.controller;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import prospectus.models.UserSession;
import javafx.animation.FadeTransition;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import prospectus.utilities.utilities;

public class UserProfileController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label enrollmentStatus;
    @FXML
    private Label role;
    @FXML
    private AnchorPane rootPane; // Ensure this is correctly linked in FXML
    @FXML
    private ImageView profileImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String firstName = UserSession.getFirstName();  
        String lastName = UserSession.getLastName();
        String u_role = UserSession.getRole();
        String e_status = UserSession.getEnrollmentStatus();
        int getID = UserSession.getUserId();
        String profilePath = UserSession.getProfileImage();

        id.setText("User ID:\t" + getID);
        name.setText("Name:\t" + firstName + " " + lastName);
        role.setText("Role:\t" + u_role);
        enrollmentStatus.setText(e_status);

        // ✅ Debug: Print profile path
        System.out.println("Profile path from session: " + profilePath);

        // ✅ Load profile image dynamically
        if (profilePath != null && !profilePath.trim().isEmpty()) {
            try {
                Image img;
                File imgFile = new File(profilePath);

                if (imgFile.exists()) {
                    // ✅ Convert file path to URI for JavaFX Image
                    img = new Image(imgFile.toURI().toString());
                } else {
                    throw new Exception("Image not found: " + profilePath);
                }
                profileImage.setImage(img);
            } catch (Exception e) {
                System.out.println("Error loading profile image: " + e.getMessage());
                profileImage.setImage(new Image(new File("src/prospectus/images/users/default-user.png").toURI().toString()));
            }
        } else {
            System.out.println("Profile image path is null or empty. Using default image.");
            profileImage.setImage(new Image(new File("src/prospectus/images/users/default-user.png").toURI().toString()));
        }

    }


    @FXML
    private void changePasswordOnClickHandler(MouseEvent event) {
        utilities.loadFXMLWithFade(rootPane, "/prospectus/auth/fxml/ChangePassword.fxml");
    }

    @FXML
    private void recoveryPhraseHandler(MouseEvent event) {
        utilities.loadFXMLWithFade(rootPane, "/prospectus/auth/fxml/SecretRecoveryPhrase.fxml");
    }

    @FXML
    private void profilechangeHandler(MouseEvent event) {
    }
}
