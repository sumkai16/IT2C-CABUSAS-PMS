/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.student.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import prospectus.utilities.utilities;
import prospectus.models.Prospectus;
import prospectus.models.UserSession;
import main.dbConnector;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class StudentDashboardController implements Initializable {

    private static final Logger logger = Logger.getLogger(StudentDashboardController.class.getName());
    private final dbConnector db = new dbConnector();
    
    @FXML
    private BorderPane bgPane;
    @FXML
    private Button home;
    @FXML
    private Button studentDetails;
    @FXML
    private Button settings;
    @FXML
    private Button logout;
    @FXML
    private Pane overlayPane;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

     private void loadPage(String fxmlPath) {
        utilities.loadPageWithFade(bgPane, fxmlPath, null);
    }

    @FXML
    private void homeOnClick(MouseEvent event) {
        loadPage("/prospectus/student/fxml/studentHome.fxml");
    }

    @FXML
    private void studentDetailsOnClick(MouseEvent event) {
        loadPage("/prospectus/user/fxml/UserProfile.fxml");
    }

    @FXML
    private void settingsOnClick(MouseEvent event) {
       loadPage("/prospectus/user/fxml/SettingsPage.fxml");
    }

    @FXML
    private void logoutOnClick(MouseEvent event) {
        utilities.switchScene(getClass(), event, "/prospectus/auth/fxml/LoginPage.fxml");
    }

    @FXML
    private void prospectusViewHandler(MouseEvent event) {
        loadPage("/prospectus/admin/prospectus/viewProspectus.fxml");
    }

   

    
}
