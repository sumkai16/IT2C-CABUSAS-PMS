/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.admin.programs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class AddProgramsController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private TextField programNameF;
    @FXML
    private TextField programDepartmentF;
    @FXML
    private TextField programDescriptionF;
    @FXML
    private Button addUser;
    private dbConnector db;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       db = new dbConnector();
    }    
    
    @FXML
     private void addProgramHandler(MouseEvent event) {
        Stage currentStage = (Stage) backgroundPane.getScene().getWindow();
        String programName = programNameF.getText();
        String programDescription = programDescriptionF.getText();
        String programDepartment = programDepartmentF.getText();
        String username = UserSession.getUsername();

        if (programName.isEmpty() || programDescription.isEmpty() || programDepartment.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
            return;
        }

        String query = "INSERT INTO program (p_program_name, p_desc, p_department, p_status) VALUES (?, ?, ?, 'Active')";

        if (db.insertData(query, programName, programDescription, programDepartment)) {
            logger.addLog(UserSession.getUsername(), "Program", "Program added successfully!: " + UserSession.getUsername());
            utilities.showAlert(Alert.AlertType.INFORMATION, "Program successfully added!", "Added Completed!");

            clearFields();
        } else {
            logger.addLog(UserSession.getUsername(), "Program", "Attempted to add Program.: " + UserSession.getUsername());
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add program!");
        }
    }

    @FXML
    private void returnHandler(MouseEvent event) {
         utilities.closeOverlay(overlayPane);
    }
    private void clearFields() {
        programNameF.clear();
        programDepartmentF.clear();
        programDescriptionF.clear(); 
    }
}
