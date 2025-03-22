package prospectus.admin.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import prospectus.utilities.utilities;
import main.dbConnector;
import prospectus.utilities.validations;
import prospectus.auth.controller.RegisterPageController;
import prospectus.auth.controller.RegisterPageController;
import javafx.scene.control.MenuButton;
/**
 * FXML Controller class
 *
 * @author axcee
 */
public class AddUserController implements Initializable {

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
    private PasswordField pwF;
    @FXML
    private TextField middleF;
    @FXML
    private Button addUser;

    private dbConnector db;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane backgroundPane;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
    }
    RegisterPageController rp = new RegisterPageController();
    @FXML
    private void addUserHandler(ActionEvent event) throws Exception {
        Stage currentStage = (Stage)backgroundPane.getScene().getWindow();
        String firstName = firstnameF.getText();
        String middleName = middleF.getText();
        String lastName = lastnameF.getText();
        String emailAddress = emailF.getText();
        String phoneNumber = contactF.getText();
        String username = userFF.getText();
        String password = pwF.getText();
        
        String query = "INSERT INTO user (u_fname, u_mname, u_lname, u_email, u_contact_number,u_username, u_password, u_role, u_status,enrollment_status) "
                + "VALUES ( ?, ?, ?, ?, ?, ? ,? , 'Student' , 'Inactive', 'Not Enrolled')";
        
        if(!rp.verifyUser(currentStage, query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
           if(db.insertData(query, firstName, middleName, lastName, emailAddress, phoneNumber, username, password)) {
                System.out.println("User added to database!");
                utilities.showAlert(Alert.AlertType.INFORMATION, "User successfully added!", "Added Completed!");
                clearFields();
                utilities.switchScene(getClass(), event,  "/prospectus/admin/fxml/AdminDashboard.fxml");
            } 
        }  
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
