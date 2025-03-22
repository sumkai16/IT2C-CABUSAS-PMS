/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.student.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class StudentDashboardController implements Initializable {

    @FXML
    private BorderPane bgPane;
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
    private Button selectedButton = null;
    /**
     * Initializes the controller class.
     */
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
    private void homeOnClick(MouseEvent event) {
        try {
            loadPage("/user/fxml/UserHome.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    @FXML
    private void prospectusOnClick(MouseEvent event) {
    }

    @FXML
    private void studentDetailsOnClick(MouseEvent event) {
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
    private void logoutOnClick(MouseEvent event) {
         utilities.switchScene(getClass(), event,  "/auth/fxml/LoginPage.fxml");  
    }
    
}
