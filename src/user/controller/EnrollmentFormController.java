/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class EnrollmentFormController implements Initializable {

    @FXML
    private TextField fnameField;
    @FXML
    private TextField mnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private DatePicker bdateField;
    @FXML
    private MenuButton sexMenu;
    @FXML
    private TextArea addressField;
    @FXML
    private TextField yearField;
    @FXML
    private MenuButton programMenu;
    @FXML
    private Button submitButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void selectSex(ActionEvent event) {
        
    }

    @FXML
    private void selectProgram(ActionEvent event) {
        
    }
    
}
