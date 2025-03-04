package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.dbConnector;
import models.User;
import utils.utilities;

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
    private ImageView deleteIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView addIcon;

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
        String query = "SELECT u_id, u_fname, u_mname, u_lname, u_role FROM user";
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
                String status = "Active";

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

    @FXML
    private void deleteHandler(MouseEvent event) {
    }

    @FXML
    private void editHandler(MouseEvent event) {
    }

    @FXML
    private void addHandler(MouseEvent event) {
        try {
            utilities.switchScene(getClass(), event, "/fxml/addUser.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }
}
