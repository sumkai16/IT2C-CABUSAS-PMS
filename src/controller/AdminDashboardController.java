package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import main.dbConnector;
import models.User;
import utils.utilities;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import utils.hoveer;

public class AdminDashboardController implements Initializable {

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
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> statusColumn;

    private ObservableList<User> userList;
    private dbConnector db;

    @FXML
    private Button home;
    @FXML
    private Button prospectus;
    @FXML
    private Button managePrograms;
    @FXML
    private Button settings;
    @FXML
    private Button logout;
    @FXML
    private Button manageStudent;
    @FXML
    private Button genReport;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();  // Initialize the database connection
        userList = FXCollections.observableArrayList();

        // Configure table columns to match User model properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));  // Automatically set to "Registered"

        // Load data from database
        loadDataFromDatabase();
        
        hoveer hv = new hoveer();
        hv.addHoverEffect(home);
        hv.addHoverEffect(prospectus);
        hv.addHoverEffect(managePrograms);
        hv.addHoverEffect(settings);
        hv.addHoverEffect(logout);
        hv.addHoverEffect(manageStudent);
        hv.addHoverEffect(genReport);
    }

    private void loadDataFromDatabase() {
        String query = "SELECT u_id, u_fname, u_mname, u_lname, u_email, u_username, u_password, u_role FROM user";
        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null");
                return;
            }

            while (rs.next()) {
                int id = rs.getInt("u_id");
                String firstName = rs.getString("u_fname");
                String middleName = rs.getString("u_mname");
                String lastName = rs.getString("u_lname");
                String email = rs.getString("u_email");
                String userName = rs.getString("u_username");
                String password = rs.getString("u_password");
                String role = rs.getString("u_role");
                String status = "Registered";

                userList.add(new User(id, firstName, middleName, lastName, email, userName, password, role, status));
            }

            tableView.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Scene switch using AppUtils
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) {
        try {
            utilities.switchScene(clazz, evt, targetFXML);
        } catch (Exception ex) {
            utilities.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "Failed to switch scene: " + ex.getMessage());
        }
    }

    @FXML
    private void homeOnClick(ActionEvent event) {
    }

    @FXML
    private void prospectusOnClick(ActionEvent event) {
    }

    @FXML
    private void studentDetailsOnClick(ActionEvent event) {
    }

    @FXML
    private void settingsOnClick(ActionEvent event) {
    }

    @FXML
    private void logoutOnClick(ActionEvent event) {
    }
}
