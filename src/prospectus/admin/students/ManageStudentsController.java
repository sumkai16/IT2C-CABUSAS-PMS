/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.admin.students;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class ManageStudentsController implements Initializable {

    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> firstNameColumn;
    @FXML
    private TableColumn<?, ?> middleNameColumn;
    @FXML
    private TableColumn<?, ?> lastNameColumn;
    @FXML
    private TableColumn<?, ?> yearColumn;
    @FXML
    private TableColumn<?, ?> programColumn;
    @FXML
    private Label totalStudentsLabel;
    @FXML
    private Label withPrereqLabel;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<?> filterProgram;
    @FXML
    private ComboBox<?> filterYear;
    @FXML
    private Button resetFilters;
    @FXML
    private Button resetFilters1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addStudentHandler(MouseEvent event) {
    }

    @FXML
    private void editStudentHandler(MouseEvent event) {
    }

    @FXML
    private void filterProgramHandler(MouseEvent event) {
    }

    @FXML
    private void filterYearHandler(MouseEvent event) {
    }

    @FXML
    private void resetFilterHandler(MouseEvent event) {
    }

    @FXML
    private void searchHandler(MouseEvent event) {
    }
    
}
