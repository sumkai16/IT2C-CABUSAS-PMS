package prospectus.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.User;
import prospectus.utilities.utilities;
import prospectus.admin.controller.AdminDashboardController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
public class UserTable implements Initializable {

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
    
    @FXML
    private ImageView addIcon;
    @FXML
    private BorderPane bgPane;
    @FXML
    private ImageView editIcon;
    private User selectedUser;
    @FXML
    private AnchorPane rootPane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
       
        userList = FXCollections.observableArrayList();
        // Ensure columns are linked to User model fields
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView.setOnMouseClicked(event -> handleRowSelection());
        loadDataFromDatabase();
        
    }
    private void handleRowSelection() {
        selectedUser = tableView.getSelectionModel().getSelectedItem();
    }

    private void loadDataFromDatabase() {
        String query = "SELECT u_id, u_fname, u_mname, u_lname, u_email, u_username, u_password, u_role, u_status, u_contact_number FROM user";

        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }

            userList.clear(); // Clear existing data to avoid duplication

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

                // Use the updated User constructor with all fields
                userList.add(new User(id, firstName, middleName, lastName, email, username, password, role, status, contact));
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

    private void loadPage(String targetFXML) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
        bgPane.setCenter(root);
      
    }

    @FXML
    private void addUserHandler(MouseEvent event) {    
        addIcon.setVisible(false);
        editIcon.setVisible(false);
        try {
           utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/fxml/addUser.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
    private void editIconHandler(MouseEvent event) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No user selected!", "Please select a user to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/prospectus/admin/fxml/editUser.fxml"));
            Parent root = loader.load();

            // Get controller and pass the selected user
            EditUserController editController = loader.getController();
            editController.setUserData(selectedUser);

            bgPane.setCenter(root);

        } catch (IOException ex) {
            ex.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load edit user page: " + ex.getMessage());
        }
    }


}
    
