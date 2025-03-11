package controller;

import main.dbConnector;
import utils.hoveer;
import utils.utilities;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.validations;
import utils.utilities;

public class RegisterPageController implements Initializable {

    @FXML
    private TextField firstnameF, lastnameF, emailF, contactF, userFF, middleF;
    @FXML
    private PasswordField pwF;
    @FXML
    private Button register, login;
    private dbConnector db;
    @FXML
    private Label registerBtn11;
    @FXML
    private Label registerBtn1;
    @FXML
    private Pane registerPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        db = new dbConnector();
       
        hoveer hv = new hoveer();
        hv.btnAuth(register);
        hv.btnSwitch(login);
    }
    validations vd = new validations();
     utilities ut = new utilities();
    private static final dbConnector db2 = new dbConnector();
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    @FXML
    private void LoginHandler(ActionEvent event) {
        try {
            utilities.animatePaneTransitionRightToLeft(getClass(), event, "/fxml/LoginPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
     void RegisterOnClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage)registerPane.getScene().getWindow();
        String firstName = firstnameF.getText();
        String middleName = middleF.getText();
        String lastName = lastnameF.getText();
        String emailAddress = emailF.getText();
        String phoneNumber = contactF.getText();
        String username = userFF.getText();
        String password = pwF.getText();
        
        String query = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number,u_username, u_password, u_role, u_status) "
                + "VALUES ( ?, ?, ?, ?, ?, ? ,? , 'Student' , 'Inactive')";
        
        if(!verifyUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
           if(db.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
                System.out.println("User added to database!");
                utilities.showAlert(Alert.AlertType.INFORMATION, "User successfully registered!", "Register Completed!");
                clearFields();
                utilities.switchScene(getClass(), event,  "/fxml/LoginPage.fxml");
            } 
        }  
    }
    public boolean verifyUser(Stage currentStage, String query, String firstName, String middleName, String lastName, String emailAddress, String phoneNumber, String username, String password) throws Exception {
        if(firstName.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error","First name must not be empty");
            return true;
        }
        
        if(lastName.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error","Last name must not be empty");
            return true;
        }
        
        if(emailAddress.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error","Email address must not be empty");
            return true;
        }
        
        if(phoneNumber.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error","Phone number must not be empty");
            return true;
        }
        
        if(username.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Field Error","username must not be empty");
            return true;
        }
        
        if(password.isEmpty()) {
           utilities.showAlert(Alert.AlertType.ERROR, "Field Error","password must not be empty");
            return true;
        }
 
        if(!vd.isValidEmailFormat(emailAddress)) {
           utilities.showAlert(Alert.AlertType.ERROR, "Email Error","Email format is invalid");
            return true;
        }
        
        if(vd.isValidPhoneNumber(phoneNumber)) {
             utilities.showAlert(Alert.AlertType.ERROR, "Phone Number Error","Phone number must only be numbers.");
            return true;
        }
        
        if(vd.isValidPhoneNumberFormat(phoneNumber)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Phone Number Error","Phone number format is invalid, must start with 9");
            return true;
        }
        
        if(password.length() < MINIMUM_PASSWORD_LENGTH) {
            utilities.showAlert(Alert.AlertType.ERROR, "Password Error","Password must be at least 8 characters.");
            return true;
        }
     
        if(vd.isDuplicated("u_username", username)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Username Error","Username already exists");
            return true;
        }
        
        if(vd.isDuplicated("u_email", emailAddress)) {
            utilities.showAlert(Alert.AlertType.ERROR, "Email Error","Email already exist.");
            return true;
        }
        return false;
    }
    private void clearFields() {
        firstnameF.clear();
        lastnameF.clear();
        emailF.clear();
        contactF.clear();
        userFF.clear();
        pwF.clear();
        middleF.clear();
    }
    

    

}