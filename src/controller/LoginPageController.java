/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class LoginPageController implements Initializable {

    @FXML
    private TextField userF;
    @FXML
    private PasswordField passF;
    @FXML
    private Label registerBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Label registerBtn1;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RegisterBtnOnClickHandler(javafx.scene.input.MouseEvent event) {
        
    }

    @FXML
    private void LoginOnClickHandler(javafx.scene.input.MouseEvent event) {
    }

    @FXML
    private void LoginOnClickHandler(ActionEvent event) {
    }
    
}
