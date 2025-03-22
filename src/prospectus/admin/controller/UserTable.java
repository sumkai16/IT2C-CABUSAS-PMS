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
        loadDataFromDatabase();
    }
    
    private void loadDataFromDatabase() {
        String query = "SELECT u_id, u_fname, u_mname, u_lname, u_role, u_status FROM user";
        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("u_id");
                String firstName = rs.getString("u_fname");
                String middleName = rs.getString("u_mname");
                String lastName = rs.getString("u_lname");
                String role = rs.getString("u_role");
                String status =rs.getString("u_status");

                userList.add(new User(id, firstName, middleName, lastName, role, status));
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
             loadPage("/prospectus/admin/fxml/addUser.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
    private void editIconHandler(MouseEvent event) {
        addIcon.setVisible(false);
        editIcon.setVisible(false);
        try {  
             loadPage("/prospectus/admin/fxml/editUser.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }
}
    
