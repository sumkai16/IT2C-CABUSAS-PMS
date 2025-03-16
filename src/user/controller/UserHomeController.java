/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.UserSession;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class UserHomeController implements Initializable {

    @FXML
    private Label welcome;
    @FXML
    private Label name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String firstName;    
        firstName = UserSession.getFirstName();  
        welcome.setText("Hello,");
        name.setText(firstName + "!");
    }    
    
}
