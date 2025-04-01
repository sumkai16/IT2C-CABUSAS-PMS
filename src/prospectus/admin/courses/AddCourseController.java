package prospectus.admin.courses;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dbConnector;
import prospectus.models.Programs;
import prospectus.models.Course;
import prospectus.utilities.utilities;

public class AddCourseController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private TextField courseCodeField;
    @FXML
    private TextField courseDescField;
    @FXML
    private TextField courseUnitsField;
    @FXML
    private ComboBox<Course> prerequisiteComboBox; // Now handles Course objects
    @FXML
    private ComboBox<Programs> programComboBox;
    @FXML
    private Button addCourseButton;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = new dbConnector().getConnection();
        loadPrerequisites();
        loadPrograms();
    }

    private void loadPrerequisites() {
        ObservableList<Course> prerequisiteList = FXCollections.observableArrayList();
        String query = "SELECT c_id, c_code FROM course";

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                prerequisiteList.add(new Course(rs.getInt("c_id"), rs.getString("c_code"), "", 0, null, 0));
            }
            prerequisiteComboBox.setItems(prerequisiteList);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load prerequisites.");
        }
    }


    private void loadPrograms() {
        ObservableList<Programs> programList = FXCollections.observableArrayList();
        String query = "SELECT p_id, p_department FROM program";

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                programList.add(new Programs(rs.getInt("p_id"), rs.getString("p_department"), "", "", ""));
            }
            programComboBox.setItems(programList);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load programs.");
        }
    }



    @FXML
    private void addCourseHandler(ActionEvent event) {
        if (courseCodeField.getText().isEmpty() || courseDescField.getText().isEmpty() || courseUnitsField.getText().isEmpty() || programComboBox.getValue() == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill in all fields.");
            return;
        }

        int courseUnits;
        try {
            courseUnits = Integer.parseInt(courseUnitsField.getText());
            if (courseUnits <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Course units must be a positive number.");
            return;
        }

        Course prerequisite = prerequisiteComboBox.getValue();
        int prerequisiteId = (prerequisite != null) ? prerequisite.getC_id() : 0;

        String query = "INSERT INTO course (c_code, c_desc, c_units, prerequisite_id, program_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, courseCodeField.getText());
            pst.setString(2, courseDescField.getText());
            pst.setInt(3, courseUnits);
            pst.setObject(4, prerequisiteId == 0 ? null : prerequisiteId);
            pst.setInt(5, programComboBox.getValue().getId());

            if (pst.executeUpdate() > 0) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
                clearFields();
            }
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add course.");
        }
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }

    private void clearFields() {
        courseCodeField.clear();
        courseDescField.clear();
        courseUnitsField.clear();
        prerequisiteComboBox.getSelectionModel().clearSelection();
        programComboBox.getSelectionModel().clearSelection();
    }
}
