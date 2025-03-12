package admin.controller;

import auth.controller.RegisterPageController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import main.dbConnector;
import utils.utilities;

public class EditUserController implements Initializable {
    private dbConnector db;
    private ObservableList<String> userList;
    private String selectedUsername; // Store selected user
    private String selectedRole;
    private String selectedStatus;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private MenuButton userDropdown;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        userList = FXCollections.observableArrayList();  
        dropdownSelecUserHandler(); 
    }

    @FXML
    private void dropdownSelecUserHandler() {
        String query = "SELECT u_username FROM user";       
        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }

            userDropdown.getItems().clear();
            while (rs.next()) {
                String username = rs.getString("u_username");
                MenuItem menuItem = new MenuItem(username);

                menuItem.setOnAction(e -> {
                    selectedUsername = username; // Store selected username
                    userDropdown.setText(username);
                    loadUserDetails(username); // Load user details into form
                });
                userDropdown.getItems().add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

        String firstName = firstnameF.getText();
        String middleName = middleF.getText();
        String lastName = lastnameF.getText();
        String emailAddress = emailF.getText();
        String phoneNumber = contactF.getText();
        String username = userFF.getText();
        String role = selectedRole;
        String status = selectedStatus;

        String query = "UPDATE user SET u_fname = ?, u_mname = ?, u_lname = ?, u_email = ?, u_contact_number = ?,u_username = ?, u_role = ?, u_status = ? WHERE u_username = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, middleName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, emailAddress);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, username);
            pstmt.setString(7, role);
            pstmt.setString(8, status);
            pstmt.setString(9, selectedUsername);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "User updated!", "Update Completed!");
                clearFields();
                dropdownSelecUserHandler();
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Update failed!", "User update unsuccessful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        firstnameF.clear();
        lastnameF.clear();
        emailF.clear();
        contactF.clear();
        userFF.clear();
        middleF.clear();
        userDropdown.setText("Select User");
        selectedUsername = null;
        roleSelect.setText("Select Role");
        selectedRole = null;
        statusSelect.setText("Select Status");
        selectedStatus = null;
    }

    @FXML
    private void selectRoleClicked(MouseEvent event) {
        roleSelect.getItems().clear(); 
        String[] roles = {"Student", "Admin"}; 
    
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
}
