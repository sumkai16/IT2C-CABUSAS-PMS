package admin.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.hoveer;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import utils.utilities;

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
    private Button manageUser;
    @FXML
    public BorderPane bgPane;

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
        hv.addHoverEffect(manageUser); 
    }
    
    private void loadPage(String targetFXML) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(targetFXML));
        bgPane.setCenter(root);
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
    private void homeOnClick(MouseEvent event) throws IOException {
        loadPage("/admin/fxml/HomeDashboard.fxml");
    }

    @FXML
    private void UserManageOnClick(MouseEvent event) throws IOException {
       loadPage("/admin/fxml/UserTable.fxml");

    }
}
