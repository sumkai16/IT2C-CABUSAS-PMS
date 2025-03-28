package prospectus.admin.programs;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.Programs;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 */
public class ManageProgramsController implements Initializable {

    @FXML
    private TableView<Programs> tableView; // ✅ FIX: Explicit type

    @FXML
    private TableColumn<Programs, Integer> programId; // ✅ FIX: Correct type
    @FXML
    private TableColumn<Programs, String> programName;
    @FXML
    private TableColumn<Programs, String> programDescription;
    @FXML
    private TableColumn<Programs, String> programDepartment;
    @FXML
    private TableColumn<Programs, String> statusColumn;

    @FXML
    private ImageView addIcon;
    @FXML
    private ImageView editIcon;
    @FXML
    private AnchorPane rootPane;

    private ObservableList<Programs> programsList;
    private dbConnector db;
    private Programs selectedProgram;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        programsList = FXCollections.observableArrayList();

        // ✅ FIX: PropertyValueFactory should match the exact field names in Programs model
        programId.setCellValueFactory(new PropertyValueFactory<>("id")); 
        programName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        programDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        programDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        
        tableView.setOnMouseClicked(event -> handleRowSelection());

        
        loadDataFromDatabase();
    }

    
    private void handleRowSelection() {
        selectedProgram = tableView.getSelectionModel().getSelectedItem();
        if (selectedProgram != null) {
            System.out.println("Selected Program: " + selectedProgram.getProgramName());
        }
    }

    private void loadDataFromDatabase() {
        String query = "SELECT p_id, p_program_name, p_desc, p_department, p_status FROM program";

        try {
            ResultSet rs = db.getData(query);
            if (rs == null) {
                System.out.println("ResultSet is null. Check database connection.");
                return;
            }

            programsList.clear(); 

            while (rs.next()) {
                int id = rs.getInt("p_id");
                String programName = rs.getString("p_program_name");
                String description = rs.getString("p_desc");
                String department = rs.getString("p_department");
                String status = rs.getString("p_status");

               
                programsList.add(new Programs(id, programName, description, department, status));
            }

            tableView.setItems(programsList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addProgramsHandler(MouseEvent event) { 
        try {
            utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/programs/addPrograms.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load add programs page: " + ex.getMessage());
        }
    }

    @FXML
    private void editProgramsHandler(MouseEvent event) {
        if (selectedProgram == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a program to edit.");
            return;
        }

        System.out.println("Editing: " + selectedProgram.getProgramName());
       
    }
}
