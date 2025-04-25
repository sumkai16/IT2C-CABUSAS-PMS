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
import javafx.scene.layout.Pane;
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
    private Label closeBtn;
    @FXML
    private AnchorPane overlayPane;
    private static UserProfileController instance;
    @FXML
    private Pane bgPane;
    @FXML
    private Button okay;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleProceedToEnrollment(MouseEvent event) {
         utilities.closeOverlay(overlayPane);
    }
   
    @FXML
    private void closeClickHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }
    
}
