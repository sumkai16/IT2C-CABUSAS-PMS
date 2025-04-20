package prospectus.admin.programs;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dbConnector;
import prospectus.utilities.utilities;
import prospectus.models.Programs;

public class EditProgramsController implements Initializable {

    private dbConnector db;
    private String selectedProgramId;

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private TextField programNameF;
    @FXML
    private TextField programDescriptionF;
    @FXML
    private TextField programDepartmentF;
    @FXML
    private RadioButton activeRadioButton;
    @FXML
    private RadioButton inactiveRadioButton;
    private ToggleGroup statusToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        statusToggleGroup = new ToggleGroup();
        
        activeRadioButton.setToggleGroup(statusToggleGroup);
        inactiveRadioButton.setToggleGroup(statusToggleGroup);
    }

    public void setProgramData(Programs program) {
        this.selectedProgramId = String.valueOf(program.getId()); 
        loadProgramDetails(selectedProgramId);
        if (program.getStatus().equalsIgnoreCase("Active")) {
            activeRadioButton.setSelected(true);
        } else {
            inactiveRadioButton.setSelected(true);
        }
    }

    private String getSelectedStatus() {
        RadioButton selectedRadioButton = (RadioButton) statusToggleGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : null;
    }

    private void loadProgramDetails(String programId) {
        String query = "SELECT p_program_name, p_desc, p_department FROM program WHERE p_id = ?";
        try {
            db.ensureConnection(); 
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
                pstmt.setString(1, programId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    programNameF.setText(rs.getString("p_program_name"));
                    programDescriptionF.setText(rs.getString("p_desc"));
                    programDepartmentF.setText(rs.getString("p_department"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load program details.");
        }
    }

    @FXML
    private void editProgramHandler(MouseEvent event) {
        if (selectedProgramId == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No program selected!", "Please select a program to update.");
            return;
        }

        if (programNameF.getText().trim().isEmpty() || programDescriptionF.getText().trim().isEmpty() || programDepartmentF.getText().trim().isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Missing Fields!", "All fields must be filled.");
            return;
        }

        String selectedStatus = getSelectedStatus();
        if (selectedStatus == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Missing Status!", "Please select a status (Active/Inactive).");
            return;
        }

        String updateQuery = "UPDATE program SET p_program_name = ?, p_desc = ?, p_department = ?, p_status = ? WHERE p_id = ?";
        
        Object[] params = { programNameF.getText().trim(), programDescriptionF.getText().trim(),
                            programDepartmentF.getText().trim(), selectedStatus, selectedProgramId };

        if (db.updateData(updateQuery, params)) {
            utilities.showAlert(Alert.AlertType.INFORMATION, "Program Updated!", "Program details updated successfully!");
            clearFields();
        } else {
            utilities.showAlert(Alert.AlertType.ERROR, "Update Failed!", "Program update unsuccessful.");
        }
    }

    private void clearFields() {
        programNameF.clear();
        programDescriptionF.clear();
        programDepartmentF.clear();
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }
}
