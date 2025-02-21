package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add hover and click effects to buttons
        hoveer hv = new hoveer();
        hv.addHoverEffect(home);
        hv.addHoverEffect(prospectus);
        hv.addHoverEffect(studentDetails);
        hv.addHoverEffect(settings);
        hv.addHoverEffect(logout);
    }

    // Method to apply hover and selection effects
  

    // Method to switch scenes while keeping the custom title bar
    public void switchScene(Class<?> clazz, Event evt, String targetFXML) {
        try {
            utilities.switchScene(clazz, evt, targetFXML);
        } catch (Exception ex) {
            utilities.showAlert(javafx.scene.control.Alert.AlertType.ERROR, "Error", "Failed to switch scene: " + ex.getMessage());
        }
    }

    @FXML
    private void homeOnClick(ActionEvent event) {
        switchScene(UserDashboardController.class, event, "/fxml/Home.fxml");
    }

    @FXML
    private void prospectusOnClick(ActionEvent event) {
        switchScene(UserDashboardController.class, event, "/fxml/Prospectus.fxml");
    }

    @FXML
    private void studentDetailsOnClick(ActionEvent event) {
        switchScene(UserDashboardController.class, event, "/fxml/StudentDetails.fxml");
    }

    @FXML
    private void settingsOnClick(ActionEvent event) {
        switchScene(UserDashboardController.class, event, "/fxml/Settings.fxml");
    }

    @FXML
    private void logoutOnClick(ActionEvent event) {
        switchScene(UserDashboardController.class, event, "/fxml/Login.fxml");
    }
}
