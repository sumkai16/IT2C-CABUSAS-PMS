package prospectus.user.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;
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
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utilities.loadFXMLWithFade(rootPane, "/prospectus/user/fxml/WelcomeSection.fxml");
        try {
            loadPage("/prospectus/user/fxml/UserHome.fxml");
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
        utilities.switchScene(getClass(), event,  "/prospectus/auth/fxml/LoginPage.fxml");  
    }

    @FXML
    private void homeOnClick(MouseEvent event) throws IOException {
         try {
            loadPage("/prospectus/user/fxml/UserHome.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }


    @FXML
    private void studentDetailsOnClick(MouseEvent event) throws IOException {
       try {
            loadPage("/prospectus/user/fxml/UserProfile.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }

    @FXML
    private void settingsOnClick(MouseEvent event) {
    }

    @FXML
    private void enrollmentOnClickHandler(MouseEvent event) {
        try {
            loadPage("/prospectus/user/fxml/enrollmentForm.fxml");
        } catch (IOException ex) {
             utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load dashboard: " + ex.getMessage());
        }
    }
}
