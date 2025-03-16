package user.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.UserSession;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class UserProfileController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label enrollmentStatus;
    @FXML
    private Label role;
    @FXML
    private AnchorPane rootPane; // Ensure this is correctly linked in FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String firstName = UserSession.getFirstName();  
        String lastName = UserSession.getLastName();
        String u_role = UserSession.getRole();
        String e_status = UserSession.getEnrollmentStatus();
        int getID = UserSession.getUserId();

        id.setText("ID: " + getID);
        name.setText("Name: " + firstName + " " + lastName + "!");
        role.setText("Role: " + u_role);
        enrollmentStatus.setText(e_status);
    }

    @FXML
    private void changeProfileOnClickHandler(MouseEvent event) {
       
    }

    @FXML
    private void changePasswordOnClickHandler(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/auth/fxml/ChangePassword.fxml"));
            AnchorPane overlay = loader.load();          
            rootPane.getChildren().add(overlay);            
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), overlay);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
