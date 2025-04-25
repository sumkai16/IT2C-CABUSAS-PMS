package prospectus.admin.manageUsers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.dbConnector;
import prospectus.models.User;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.passwordHasher;
import prospectus.utilities.utilities;
import prospectus.utilities.validations;

public class EditUserController implements Initializable {
    private dbConnector db;
    private ObservableList<String> userList;
    private String selectedUsername; // Store selected user
    private String selectedRole;
    private String selectedStatus;
    
    @FXML
    private Pane backgroundPane;
    
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
    private TextField middleF;
    @FXML
    private MenuButton roleSelect;
    @FXML
    private MenuButton statusSelect;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private ImageView profileImage;
    private String photoFilePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        userList = FXCollections.observableArrayList();     
    }
    
    public void setUserData(User user) {
        firstnameF.setText(user.getFirstName());
        middleF.setText(user.getMiddleName());
        lastnameF.setText(user.getLastName());
        emailF.setText(user.getEmail());  
        contactF.setText(user.getContact());  
        userFF.setText(user.getUsername());  
        selectedUsername = user.getUsername();
        selectedRole = user.getRole();
        selectedStatus = user.getStatus();

        roleSelect.setText(selectedRole != null ? selectedRole : "Select Role");
        statusSelect.setText(selectedStatus != null ? selectedStatus : "Select Status");

        // Load user profile image
        if (user.getProfileImagePath() != null && !user.getProfileImagePath().isEmpty()) {
            File imageFile = new File(user.getProfileImagePath());
            if (imageFile.exists()) {
                profileImage.setImage(new Image(imageFile.toURI().toString()));
            } else {
                profileImage.setImage(new Image("/prospectus/images/users/default-user.png")); // Default image
            }
        } else {
            profileImage.setImage(new Image("/prospectus/images/users/default-user.png")); 
        }
    }

    // Load user details from the database, including the profile image
    private void loadUserDetails(String username) {
        String query = "SELECT u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_role, u_status, u_image FROM user WHERE u_username = ?";

        try (Connection conn = db.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                firstnameF.setText(rs.getString("u_fname"));
                middleF.setText(rs.getString("u_mname"));
                lastnameF.setText(rs.getString("u_lname"));
                emailF.setText(rs.getString("u_email"));
                contactF.setText(rs.getString("u_contact_number"));
                userFF.setText(rs.getString("u_username"));
                selectedRole = rs.getString("u_role");
                selectedStatus = rs.getString("u_status");

                // Set the role and status dropdown text
                roleSelect.setText(selectedRole != null ? selectedRole : "Select Role");
                statusSelect.setText(selectedStatus != null ? selectedStatus : "Select Status");

                // Load user profile image
                String imagePath = rs.getString("u_image");
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        profileImage.setImage(new Image(imageFile.toURI().toString()));
                    } else {
                        profileImage.setImage(new Image("/prospectus/images/users/default-user.png")); // Default image
                    }
                } else {
                    profileImage.setImage(new Image("/prospectus/images/users/default-user.png")); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getExistingProfileImage(String username) {
        String query = "SELECT u_image FROM user WHERE u_username = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("u_image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "src/prospectus/images/users/default-user.png"; 
    }

    @FXML
    private void editUserButtonHandler(ActionEvent event) {
        if (selectedUsername == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No user selected!", "Please select a user to update.");
            return;
        }

        if (firstnameF.getText().trim().isEmpty() || lastnameF.getText().trim().isEmpty() ||
            emailF.getText().trim().isEmpty() || contactF.getText().trim().isEmpty() || userFF.getText().trim().isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Missing Fields!", "All fields must be filled.");
            return;
        }

        // Check if the role is changed to "Student" and validate enrollment
        boolean isStudentRole = "Student".equals(selectedRole);
        boolean isEnrolled = validations.isStudentEnrolled(selectedUsername); // Use the provided method

        if (isStudentRole && !isEnrolled) {
            utilities.showAlert(Alert.AlertType.WARNING, "Enrollment Check", "The student is not enrolled!");
            return; // Prevent further execution if the user is not enrolled
        }

        // Existing code for handling photo paths and updating user...
        if (photoFilePath == null || photoFilePath.isEmpty()) {
            photoFilePath = getExistingProfileImage(selectedUsername);
        }

        if (photoFilePath == null || photoFilePath.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Please select a profile photo.");
            return;
        }

        String destinationPath = "src/prospectus/images/users/" + new File(photoFilePath).getName();
        try {
            Files.copy(Paths.get(photoFilePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to save photo.");
            return;
        }

        // Prepare the update query using dbConnector's update method
        String updateQuery = "UPDATE user SET u_fname = ?, u_mname = ?, u_lname = ?, u_email = ?, u_contact_number = ?, u_username = ?, u_role = ?, u_status = ?, u_image = ? WHERE u_username = ?";
        
        try (Connection conn = db.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, firstnameF.getText().trim());
            pstmt.setString(2, middleF.getText().trim());
            pstmt.setString(3, lastnameF.getText().trim());
            pstmt.setString(4, emailF.getText().trim());
            pstmt.setString(5, contactF.getText().trim());
            pstmt.setString(6, userFF.getText().trim());
            pstmt.setString(7, selectedRole); 
            pstmt.setString(8, selectedStatus);
            pstmt.setString(9, destinationPath);
            pstmt.setString(10, selectedUsername);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.addLog(UserSession.getUsername(), "User", "User updated Successfully!: " + UserSession.getUsername());
                utilities.showAlert(Alert.AlertType.INFORMATION, "User Updated!", "Profile updated successfully!");
                clearFields();
            } else {
                logger.addLog(UserSession.getUsername(), "User", "Attempted to update User.: " + UserSession.getUsername());
                utilities.showAlert(Alert.AlertType.ERROR, "Update Failed!", "User update unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the user.");
        }
    }

    private void clearFields() {
        firstnameF.clear();
        lastnameF.clear();
        emailF.clear();
        contactF.clear();
        userFF.clear();
        middleF.clear();

        selectedUsername = null;
        roleSelect.setText("Select Role");
        selectedRole = null;
        statusSelect.setText("Select Status");
        selectedStatus = null;
        profileImage.setImage(null);
        photoFilePath = null;
    }

    @FXML
    private void selectRoleClicked(MouseEvent event) {
        roleSelect.getItems().clear(); 
        String[] roles = {"User", "Admin", "Student"}; 
    
        for (String role : roles) {
            MenuItem menuItem = new MenuItem(role);
            menuItem.setOnAction(e -> {
                roleSelect.setText(role);
                selectedRole = role; 
            });
            roleSelect.getItems().add(menuItem);
        }     
    }

    @FXML
    private void selectStatusClicked(MouseEvent event) {
        statusSelect.getItems().clear();
        String[] statuses = {"Active", "Inactive"}; 
        for (String status : statuses) {
            MenuItem menuItem = new MenuItem(status);
            menuItem.setOnAction(e -> {
                statusSelect.setText(status);
                selectedStatus = status; 
            });
            statusSelect.getItems().add(menuItem);
        }
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
