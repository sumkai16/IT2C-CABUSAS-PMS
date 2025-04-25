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
import prospectus.utilities.logger;
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
        loadPage("/prospectus/user/fxml/UserHome.fxml");
    }
   
     private void loadPage(String fxmlPath) {
        utilities.loadPageWithFade(bgPane, fxmlPath, null);
    }



    @FXML
    private void logoutOnClick(MouseEvent event) {
        String username = UserSession.getUsername(); 
        logger.addLog(username, "Logout", "User logged out: " + username);
        UserSession.clearSession(); 
        utilities.switchScene(getClass(), event,  "/prospectus/auth/fxml/LoginPage.fxml");  
    }

    @FXML
    private void homeOnClick(MouseEvent event) throws IOException {
        loadPage("/prospectus/user/fxml/UserHome.fxml");
    }


    @FXML
    private void studentDetailsOnClick(MouseEvent event) throws IOException {
        loadPage("/prospectus/user/fxml/UserProfile.fxml");
    }

    @FXML
    private void settingsOnClick(MouseEvent event) {
        loadPage("/prospectus/user/fxml/SettingsPage.fxml");
    }

    @FXML
    private void enrollmentOnClickHandler(MouseEvent event) {
        loadPage("/prospectus/user/fxml/enrollmentForm.fxml");
    }
}
