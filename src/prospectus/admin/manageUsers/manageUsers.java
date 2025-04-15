package prospectus.admin.manageUsers;


import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.User;
import prospectus.utilities.utilities;

public class manageUsers implements Initializable {

    private dbConnector db;
    private ObservableList<User> userList;

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> firstNameColumn;
    @FXML
    private TableColumn<User, String> middleNameColumn;
    @FXML
    private TableColumn<User, String> lastNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> statusColumn;
    
    private User selectedUser;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<?> filterByRole;
    @FXML
    private ComboBox<?> sortBy;
    @FXML
    private Button refreshBtn;
    @FXML
    private Button searchButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
       
        userList = FXCollections.observableArrayList();
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView.setOnMouseClicked(event -> handleRowSelection());
        loadDataFromDatabase();
        
         //realtime search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim().toLowerCase();
            ObservableList<User> filteredList = searchUsers(userList, searchText);
            tableView.setItems(filteredList);
        });
        
    }
    private void handleRowSelection() {
        selectedUser = tableView.getSelectionModel().getSelectedItem();
    }

    private void loadDataFromDatabase() {
        String query = "SELECT u_id, u_fname, u_mname, u_lname, u_email, u_username, u_password, u_role, u_status, u_contact_number, u_image FROM user";

        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }

            userList.clear(); 

            while (rs.next()) {
                int id = rs.getInt("u_id");
                String firstName = rs.getString("u_fname");
                String middleName = rs.getString("u_mname");
                String lastName = rs.getString("u_lname");
                String email = rs.getString("u_email");
                String username = rs.getString("u_username");
                String password = rs.getString("u_password");
                String role = rs.getString("u_role");
                String status = rs.getString("u_status");
                String contact = rs.getString("u_contact_number");
                String profileImagePath = rs.getString("u_image");

                // check if image is dili null
                if (profileImagePath == null || profileImagePath.isEmpty()) {
                    profileImagePath = "/prospectus/images/default-user.png"; 
                }

                userList.add(new User(id, firstName, middleName, lastName, email, username, password, role, status, contact, profileImagePath));
            }

            if (tableView != null) {
                tableView.setItems(userList);
            } else {
                System.out.println("TableView is null. Check FXML bindings.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    private void clearSearch(ActionEvent event) {
//        searchField.clear(); 
//        tableView.setItems(userList); 
//    }

    @FXML
    private void refreshTable(ActionEvent event) {
        loadDataFromDatabase(); 
    }


    @FXML
    private void addUserHandler(MouseEvent event) {
        try {
            System.out.println("Add User Test");
           utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/manageUsers/addUser.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
    private void editUserHandler(MouseEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No user selected!", "Please select a user to edit.");
            return;
        }

        utilities.loadFXMLWithFadeEdit(rootPane, "/prospectus/admin/manageUsers/editUser.fxml", controller -> {
            EditUserController editController = (EditUserController) controller;
            editController.setUserData(selectedUser);
        });
    }

    @FXML
    private void filterByHandler(MouseEvent event) {
        String selectedRole = (String) filterByRole.getValue();

        if (selectedRole == null || selectedRole.equals("All")) {
              tableView.setItems(userList);
        } else {
            ObservableList<User> filteredList = userList.filtered(user -> user.getRole().equalsIgnoreCase(selectedRole));
            tableView.setItems(filteredList);
        }
    }

    @FXML
    private void sortByHandler(MouseEvent event) {
        String selectedSort = (String) sortBy.getValue();

        if (selectedSort == null) return;

        userList.sort((u1, u2) -> {
            switch (selectedSort) {
                case "First Name": return u1.getFirstName().compareToIgnoreCase(u2.getFirstName());
                case "Last Name": return u1.getLastName().compareToIgnoreCase(u2.getLastName());
                case "Role": return u1.getRole().compareToIgnoreCase(u2.getRole());
                case "Status": return u1.getStatus().compareToIgnoreCase(u2.getStatus());
                default: return 0;
            }
        });

        tableView.setItems(userList);
    }

    @FXML
    private void searchButtonHandler(MouseEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();
        ObservableList<User> filteredList = searchUsers(userList, searchText);
        tableView.setItems(filteredList);
    }
    private ObservableList<User> searchUsers(ObservableList<User> users, String searchText) {
        if (searchText.isEmpty()) {
            return users;
        }

        return users.filtered(user -> 
            user.getFirstName().toLowerCase().contains(searchText) ||
            user.getLastName().toLowerCase().contains(searchText) ||
            user.getUsername().toLowerCase().contains(searchText) ||
            user.getRole().toLowerCase().contains(searchText) ||
            user.getStatus().toLowerCase().contains(searchText)
        );
    }




}
    
