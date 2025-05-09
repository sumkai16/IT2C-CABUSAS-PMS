package prospectus.auth.controller;

import main.dbConnector;
import prospectus.utilities.utilities;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.passwordHasher;
import prospectus.utilities.validations;
import prospectus.utilities.utilities;

public class RegisterPageController implements Initializable {

    @FXML
    private TextField firstnameF, lastnameF, emailF, contactF, userFF, middleF;
    @FXML
    private PasswordField pwF;
    private dbConnector db;
    @FXML
    private Label registerBtn11;
    @FXML
    private Pane registerPane;
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        db = new dbConnector();
      
      
    }
    validations vd = new validations();
     utilities ut = new utilities();
    private static final dbConnector db2 = new dbConnector();
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
   
    @FXML
    void RegisterOnClickHandler(MouseEvent event) throws Exception {
        Stage currentStage = (Stage) registerPane.getScene().getWindow();
        String firstName = firstnameF.getText();
        String middleName = middleF.getText();
        String lastName = lastnameF.getText();
        String emailAddress = emailF.getText();
        String phoneNumber = contactF.getText();
        String username = userFF.getText();
        String password = pwF.getText();
        String hashedPassword = passwordHasher.hashPassword(password);

        RecoveryPhraseGenerator generator = new RecoveryPhraseGenerator();
        String recoveryPhrase = generator.generateUniqueRecoveryPhrase(); // Get unique phrase

        String query = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number, u_username, u_password, u_role, u_status, enrollment_status, recovery_phrase, u_image) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, 'User ', 'Inactive', 'Not Enrolled', ?, 'src/prospectus/images/users/default-user.png')";

        if (!verifyUser (currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
            if (db.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, hashedPassword, recoveryPhrase)) {
                System.out.println("User  added to database!");
                
                utilities.showAlert(Alert.AlertType.INFORMATION, "User  successfully registered!", 
                    "Register Completed!" + "\n\nNOTE: Please save your recovery phrase, check it in your profile");
                utilities.loadFXMLWithFade(rootPane, "/prospectus/auth/fxml/SecretRecoveryPhrase.fxml");
                clearFields();
                utilities.switchScene(getClass(), event, "/prospectus/auth/fxml/LoginPage.fxml");
                logger.addLog(username, "User  Registration", "New user registered: " + username);       
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

    @FXML
    private void LoginHandler(MouseEvent event) {
         try {
            utilities.animatePaneFadeTransition(getClass(), event, "/prospectus/auth/fxml/LoginPage.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to loaad login page: " + ex.getMessage());
        }
    }
    

    

}