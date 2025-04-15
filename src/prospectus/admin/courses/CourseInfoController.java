/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.admin.courses;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class CourseInfoController implements Initializable {

    @FXML
    private Label courseId;
    @FXML
    private Label description;
    @FXML
    private Label prerequisite;
    @FXML
    private Label courseCode;
    @FXML
    private Label units;
    @FXML
    private Label program;
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
    private void outHandler(MouseEvent event) {
          utilities.closeOverlay(overlayPane);
    }
    
}
