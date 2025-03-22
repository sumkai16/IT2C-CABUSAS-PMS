/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.user.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class Welcome_sectionController implements Initializable {

    @FXML
    private Button proceedEnrollmentBtn;
    @FXML
    private Button proceedtoDashboard;
    @FXML
    private Label closeBtn;
    @FXML
    private AnchorPane overlayPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleProceedToEnrollment(MouseEvent event) {
         try {
            utilities.switchScene(getClass(), event, "/prospectus/user/fxml/enrollmentForm.fxml"); 
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Enrollment page: " + ex.getMessage());
        }
    }

    @FXML
    private void proceedToDashboardHandler(MouseEvent event) {
        try {
            utilities.switchScene(getClass(), event, "/prospectus/user/fxml/UserDashboard.fxml"); 
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open User Dashboard: " + ex.getMessage());
        }
       
    }
     private void closeOverlay() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), overlayPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> overlayPane.setVisible(false));
        fadeOut.play();
    }
    @FXML
    private void closeClickHandler(MouseEvent event) {
        closeOverlay();
    }
    
}
