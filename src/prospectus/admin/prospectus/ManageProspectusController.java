/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.admin.prospectus;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.text.Utilities;
import main.dbConnector;
import prospectus.models.Prospectus;
import prospectus.utilities.utilities;

/**
 * FXML Controller class
 *
 * @author axcee
 */
public class ManageProspectusController implements Initializable {

    
    @FXML private TableView<Prospectus> tableView;
    @FXML private TableColumn<Prospectus, Integer> idColumn;
    @FXML private TableColumn<Prospectus, String> programColumn;
    @FXML private TableColumn<Prospectus, String> effectiveYear;
    @FXML private TableColumn<Prospectus, String> status;
    
    @FXML
    private Button addProspectus;
    @FXML
    private Button editProspectus;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button refreshButton;
    private dbConnector db = new dbConnector();
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Label totalProspectusLabel;
   
    @FXML
    private TableColumn<Prospectus, String> semesterColumn; // Add this line
    @FXML
    private TableColumn<Prospectus, String> yearLevel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the columns in the table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("prId"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("pDepartment")); // Changed from PDepartment to pDepartment
        effectiveYear.setCellValueFactory(new PropertyValueFactory<>("prEffectiveYear"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        yearLevel.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));

        // Load the prospectus data into the table
        loadProspectusData();
    }

        private void loadProspectusData() {
            ObservableList<Prospectus> data = FXCollections.observableArrayList();

            // SQL query to get one row per effective year (using the first ID)
            String query = "SELECT p.pr_id, prog.p_program_name, prog.p_department, p.pr_effective_year, p.status " +
                          "FROM prospectus p " +
                          "JOIN program prog ON p.program_id = prog.p_id " +
                          "WHERE p.pr_id IN (SELECT MIN(pr_id) FROM prospectus GROUP BY pr_effective_year) " +
                          "ORDER BY p.pr_effective_year DESC";

            try (PreparedStatement stmt = db.getConnection().prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                // Populate the ObservableList with Prospectus objects
                while (rs.next()) {
                    String programName = rs.getString("p_program_name");
                    String department = rs.getString("p_department");
                    String displayName = programName + " (" + department + ")";

                    data.add(new Prospectus(
                        rs.getInt("pr_id"),
                        displayName,
                        rs.getString("pr_effective_year"),
                        rs.getString("status"),
                        "", // Empty year level since we're not using it here
                        ""  // Empty semester since we're not using it here
                    ));
                }

                // Set the items in the table view
                tableView.setItems(data);
                totalProspectusLabel.setText(String.valueOf(data.size()));
            } catch (SQLException e) {
                e.printStackTrace();
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load prospectus data.");
            }
    }
    @FXML
    private void searchHandler(MouseEvent event) {
    }

    @FXML
    private void addProspectus(MouseEvent event) {
        try {
            utilities.loadFXMLWithFade(overlayPane, "/prospectus/admin/prospectus/addProspectus.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Enrollment page: " + ex.getMessage());
        }
    }

    @FXML
    private void editProspectus(MouseEvent event) {
//        Prospectus selectedProspectus = getSelectedProspectus(); // Implement this method to get the selected prospectus
//        if (selectedProspectus != null) {
//            EditProspectusController controller = new EditProspectusController();
//            controller.setProspectus(selectedProspectus);
//            utilities.loadFXMLWithController("/prospectus/admin/prospectus/editProspectus.fxml", controller);
//        } else {
//            utilities.showAlert(Alert.AlertType.WARNING, "Warning", "No prospectus selected.");
//        }
    }

    @FXML
    private void refreshHandler(MouseEvent event) {
    }

    @FXML
    private void viewProspectus(MouseEvent event) {
          Prospectus selectedProspectus = tableView.getSelectionModel().getSelectedItem();
            if (selectedProspectus != null) {
                try {
                    utilities.loadFXMLWithFadeView(overlayPane, "/prospectus/admin/prospectus/viewProspectus.fxml", controller -> {
                        if (controller instanceof ViewProspectusController) {
                            ViewProspectusController viewController = (ViewProspectusController) controller;
                            viewController.setProspectus(selectedProspectus); // Pass the selected prospectus
                        }
                    });
                } catch (Exception ex) {
                    utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open View Prospectus page: " + ex.getMessage());
                }
            } else {
                utilities.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a prospectus to view.");
            }
    }
    
}
