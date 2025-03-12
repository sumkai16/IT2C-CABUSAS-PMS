/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dbConnector;
import models.User;

/**
 * FXML Controller class
 *
 * @author SCC-COLLEGE
 */
public class EditUserController implements Initializable {
    private dbConnector db;
    private ObservableList<User> userList;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        userList = FXCollections.observableArrayList();  
    
    }    

    @FXML
    private void editUserButtonHandler(ActionEvent event) {
        
    }

    @FXML
    private void dropdownSelecUserHandler(ActionEvent event) throws SQLException {
        String query = "SELECT u_username FROM user";
       String username = null;
        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }
            while (rs.next()) {  
                 username =rs.getString("u_username");               
                userList.add(new User(username));
                userDropdown.getItems().addAll(
                 new MenuItem(username)
        );
            }
            
    }catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
        
        
  
}
