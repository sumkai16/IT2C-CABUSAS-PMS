package user.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.UserSession;
import utils.utilities;
public class UserDashboardController implements Initializable {

    @FXML
    private Button home;
    private Button prospectus;
    @FXML
    private Button studentDetails;
    @FXML
    private Button settings;
    @FXML
    private Button logout;

    private Button selectedButton = null; // To track the selected button
    private Label welcome;
    private Label name;
    @FXML
    private BorderPane bgPane;
    @FXML
    private Button enrollmentForm;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        try {
            loadPage("/user/fxml/UserHome.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    private void loadPage(String targetFXML) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
        bgPane.setCenter(root);
    }


    @FXML
    private void logoutOnClick(MouseEvent event) {
        utilities.switchScene(getClass(), event,  "/auth/fxml/LoginPage.fxml");  
    }

    @FXML
    private void homeOnClick(MouseEvent event) throws IOException {
         try {
            loadPage("/user/fxml/UserHome.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }


    @FXML
    private void studentDetailsOnClick(MouseEvent event) throws IOException {
       try {
            loadPage("/user/fxml/UserProfile.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    @FXML
    private void settingsOnClick(MouseEvent event) {
    }

    @FXML
    private void enrollmentOnClickHandler(MouseEvent event) {
    }
}
