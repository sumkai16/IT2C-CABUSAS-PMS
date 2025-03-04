package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import models.UserSession;
import utils.utilities;
import utils.hoveer;
public class UserDashboardController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private Button prospectus;
    @FXML
    private Button studentDetails;
    @FXML
    private Button settings;
    @FXML
    private Button logout;

    private Button selectedButton = null; // To track the selected button
    @FXML
    private Label welcome;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        hoveer hv = new hoveer();
        hv.addHoverEffect(home);
        hv.addHoverEffect(prospectus);
        hv.addHoverEffect(studentDetails);
        hv.addHoverEffect(settings);
        hv.addHoverEffect(logout);
        
        String firstName, lastName;
        firstName = UserSession.getFirstName();
        lastName = UserSession.getLastName();
        
        welcome.setText("Welcome, " + firstName + " " + lastName + "!");
    }

   
  

    // Method to switch scenes while keeping the custom title bar
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) {
        try {
            utilities.switchScene(clazz, evt, targetFXML);
        } catch (Exception ex) {
            utilities.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "Failed to switch scene: " + ex.getMessage());
        }
    }

    @FXML
    private void homeOnClick(ActionEvent event) throws Exception {
        utilities.switchScene(UserDashboardController.class, event, "/fxml/Home.fxml");
    }

    @FXML
    private void prospectusOnClick(ActionEvent event) throws Exception {
        utilities.switchScene(UserDashboardController.class, event, "/fxml/Prospectus.fxml");
    }

    @FXML
    private void studentDetailsOnClick(ActionEvent event) throws Exception {
        utilities.switchScene(UserDashboardController.class, event, "/fxml/StudentDetails.fxml");
    }

    @FXML
    private void settingsOnClick(ActionEvent event) throws Exception {
        utilities.switchScene(UserDashboardController.class, event, "/fxml/Settings.fxml");
    }

    @FXML
    private void logoutOnClick(ActionEvent event) throws Exception {
        utilities.switchScene(UserDashboardController.class, event, "/fxml/Login.fxml");
    }
}
