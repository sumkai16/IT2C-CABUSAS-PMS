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
    @FXML private TableColumn<Prospectus, String> createdBy; 
    @FXML private TableColumn<Prospectus, String> createdAt;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("prId"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
        effectiveYear.setCellValueFactory(new PropertyValueFactory<>("prEffectiveYear"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        loadProspectusData();


    }    
    private void loadProspectusData() {
        ObservableList<Prospectus> data = FXCollections.observableArrayList();

        String query = "SELECT p.pr_id, prog.p_program_name, c.c_code, p.pr_effective_year, p.status, u.u_username, p.created_at " +
                       "FROM prospectus p " +
                       "JOIN program prog ON p.program_id = prog.p_id " +
                       "JOIN course c ON p.course_id = c.c_id " +  // <-- Added space at the end
                       "JOIN user u ON p.created_by = u.u_id";     // <-- This now starts cleanly

        try (PreparedStatement stmt = db.getConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                data.add(new Prospectus(
                    rs.getInt("pr_id"),
                    rs.getString("p_program_name"),
                    rs.getString("c_code"),
                    rs.getString("pr_effective_year"),
                    rs.getString("status"),
                    rs.getString("u_username"),
                    rs.getString("created_at")
                ));
            }

            tableView.setItems(data);
            totalProspectusLabel.setText(String.valueOf(data.size()));
        } catch (SQLException e) {
            e.printStackTrace();
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
    }

    @FXML
    private void refreshHandler(MouseEvent event) {
    }
    
}
