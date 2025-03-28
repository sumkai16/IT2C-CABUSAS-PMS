package prospectus.admin.manageUsers;

import prospectus.auth.controller.RegisterPageController;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.dbConnector;
import prospectus.models.User;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.passwordHasher;
import prospectus.utilities.utilities;

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
    private Button editUser;
    @FXML
    private MenuButton roleSelect;
    @FXML
    private MenuButton statusSelect;
    @FXML
    private AnchorPane overlayPane;

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
        userFF.setText(user.getUserName());  
        selectedUsername = user.getUserName();
        selectedRole = user.getRole();
        selectedStatus = user.getStatus();

        roleSelect.setText(selectedRole != null ? selectedRole : "Select Role");
        statusSelect.setText(selectedStatus != null ? selectedStatus : "Select Status");
    }

    //load details sa mga textfield
    private void loadUserDetails(String username) {
        String query = "SELECT u_fname, u_mname, u_lname, u_email, u_contact_number,u_username, u_role, u_status FROM user WHERE u_username = ?";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editUserButtonHandler(ActionEvent event) {
        if (selectedUsername == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No user selected!", "Please select a user to update.");
            return;
        }
        if (selectedRole == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No role selected!", "Please select a role to update.");
            return;
        }
        if (selectedStatus == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No status selected!", "Please select a status to update.");
            return;
        }

        // Get updated user details
        String firstName = firstnameF.getText().trim();
        String middleName = middleF.getText().trim();
        String lastName = lastnameF.getText().trim();
        String emailAddress = emailF.getText().trim();
        String phoneNumber = contactF.getText().trim();
        String newUsername = userFF.getText().trim(); // Updated username
        String role = selectedRole;
        String status = selectedStatus;

        // Validation: Ensure required fields are filled
        if (firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty() || newUsername.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Missing Fields!", "All fields must be filled.");
            return;
        }

        String fetchQuery = "SELECT u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_role, u_status FROM user WHERE u_username = ?";

        try (PreparedStatement fetchStmt = db.getConnection().prepareStatement(fetchQuery)) {
            fetchStmt.setString(1, selectedUsername); // Use selected user for fetching
            ResultSet rs = fetchStmt.executeQuery();

            if (rs.next()) {
                StringBuilder logDetails = new StringBuilder();
                logDetails.append("Updated user: ").append(selectedUsername).append(". Changes: ");

                if (!firstName.equals(rs.getString("u_fname"))) logDetails.append("[First Name: ").append(rs.getString("u_fname")).append(" -> ").append(firstName).append("] ");
                if (!middleName.equals(rs.getString("u_mname"))) logDetails.append("[Middle Name: ").append(rs.getString("u_mname")).append(" -> ").append(middleName).append("] ");
                if (!lastName.equals(rs.getString("u_lname"))) logDetails.append("[Last Name: ").append(rs.getString("u_lname")).append(" -> ").append(lastName).append("] ");
                if (!emailAddress.equals(rs.getString("u_email"))) logDetails.append("[Email: ").append(rs.getString("u_email")).append(" -> ").append(emailAddress).append("] ");
                if (!phoneNumber.equals(rs.getString("u_contact_number"))) logDetails.append("[Phone: ").append(rs.getString("u_contact_number")).append(" -> ").append(phoneNumber).append("] ");
                if (!newUsername.equals(rs.getString("u_username"))) logDetails.append("[Username: ").append(rs.getString("u_username")).append(" -> ").append(newUsername).append("] ");
                if (!role.equals(rs.getString("u_role"))) logDetails.append("[Role: ").append(rs.getString("u_role")).append(" -> ").append(role).append("] ");
                if (!status.equals(rs.getString("u_status"))) logDetails.append("[Status: ").append(rs.getString("u_status")).append(" -> ").append(status).append("] ");

                String updateQuery = "UPDATE user SET u_fname = ?, u_mname = ?, u_lname = ?, u_email = ?, u_contact_number = ?, u_username = ?, u_role = ?, u_status = ? WHERE u_username = ?";

                try (PreparedStatement pstmt = db.getConnection().prepareStatement(updateQuery)) {
                    pstmt.setString(1, firstName);
                    pstmt.setString(2, middleName);
                    pstmt.setString(3, lastName);
                    pstmt.setString(4, emailAddress);
                    pstmt.setString(5, phoneNumber);
                    pstmt.setString(6, newUsername);
                    pstmt.setString(7, role);
                    pstmt.setString(8, status);
                    pstmt.setString(9, selectedUsername);

                    int rowsAffected = pstmt.executeUpdate();
                    String adminUsername = UserSession.getUsername();
                    if (rowsAffected > 0) {
                        logger.addLog(adminUsername, "Update", logDetails.toString());
                        utilities.showAlert(Alert.AlertType.INFORMATION, "User Updated!", "Update Completed!");

                        // Update selectedUsername after successful edit
                        selectedUsername = newUsername;

                        clearFields();
                    } else {
                        logger.addLog(adminUsername, "Update", "User update attempted but no changes were made.");
                        utilities.showAlert(Alert.AlertType.ERROR, "Update Failed!", "User update unsuccessful.");
                    }
                }
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
    }

    @FXML
    private void selectRoleClicked(MouseEvent event) {
        roleSelect.getItems().clear(); 
        String[] roles = {"User", "Admin"}; 
    
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
}
