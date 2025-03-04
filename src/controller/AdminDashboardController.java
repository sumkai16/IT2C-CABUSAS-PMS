package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.hoveer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AdminDashboardController implements Initializable {

    

    @FXML
    private Button home;
    @FXML
    private Button prospectus;
    @FXML
    private Button managePrograms;
    @FXML
    private Button settings;
    @FXML
    private Button logout;
    @FXML
    private Button manageStudent;
    @FXML
    private Button genReport;
    @FXML
    private Button userPage;
    @FXML
    private Pane contentPane;
    @FXML
    private Button manageUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hoveer hv = new hoveer();
        hv.constant(home);
        hv.addHoverEffect(home);
        hv.addHoverEffect(prospectus);
        hv.addHoverEffect(managePrograms);
        hv.addHoverEffect(settings);
        hv.addHoverEffect(logout);
        hv.addHoverEffect(manageStudent);
        hv.addHoverEffect(genReport); 
        hv.addHoverEffect(userPage); 
    }

   

    @FXML
    private void homeOnClick(ActionEvent event) {
    }

    @FXML
    private void prospectusOnClick(ActionEvent event) {
    }


    @FXML
    private void settingsOnClick(ActionEvent event) {
    }

    @FXML
    private void logoutOnClick(ActionEvent event) {
    }

    @FXML
    private void ProgramsOnClick(ActionEvent event) {
    }

    @FXML
    private void manageStudentOnClick(ActionEvent event) {
    }

    @FXML
    private void GenReportsOnClick(ActionEvent event) {
    }

    @FXML
    private void UserManageOnClick(ActionEvent event) {
       
    }   
}
