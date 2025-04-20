package prospectus.admin.prospectus;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import main.dbConnector;
import prospectus.utilities.utilities;

public class AddProspectusController implements Initializable {

    @FXML
    private Pane backgroundPane;
    @FXML
    private ComboBox<String> selectProgramComboBox;
    @FXML
    private ListView<String> courseAddedList;
    @FXML
    private ComboBox<String> selectCoursesComboBox;
    @FXML
    private RadioButton active;
    @FXML
    private RadioButton inactive;
    @FXML
    private RadioButton archive;
    @FXML
    private TextField effectiveYearField;
    @FXML
    private TextField createdByField;

    ObservableList<String> programList = FXCollections.observableArrayList();
    ObservableList<String> courseList = FXCollections.observableArrayList();
    ObservableList<String> selectedCourses = FXCollections.observableArrayList();

    private dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPrograms();
        loadCourses();
        courseAddedList.setItems(selectedCourses);
    }

    private void loadPrograms() {
        try (Connection conn = db.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT p_program_name FROM program")) {
            while (rs.next()) {
                programList.add(rs.getString("p_program_name"));
            }
            selectProgramComboBox.setItems(programList);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load programs.");
        }
    }

    private void loadCourses() {
        try (Connection conn = db.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT c_code FROM course")) {
            while (rs.next()) {
                courseList.add(rs.getString("c_code"));
            }
            selectCoursesComboBox.setItems(courseList);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load courses.");
        }
    }

    @FXML
    private void addCourseHandler(MouseEvent event) {
        String course = selectCoursesComboBox.getValue();
        if (course != null && !selectedCourses.contains(course)) {
            selectedCourses.add(course);
        }
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        // Add your navigation logic here (e.g., return to previous page)
    }

    @FXML
    private void addProspectusHandler(MouseEvent event) {
        // Check for null or invalid input before proceeding
        if (selectProgramComboBox == null || selectProgramComboBox.getValue() == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Program selection is required.");
            return;
        }

        String program = selectProgramComboBox.getValue();
        String year = effectiveYearField.getText().trim();

        // Ensure effective year is not empty
        if (year.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Effective Year is required.");
            return;
        }

        String status = active.isSelected() ? "Active" :
                        inactive.isSelected() ? "Inactive" :
                        archive.isSelected() ? "Archive" : "";

        if (status.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Status must be selected.");
            return;
        }

        String createdByText = createdByField.getText().trim();

        // Validate createdByField
        if (createdByText.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Created By is required.");
            return;
        }

        // Convert createdByText to integer, ensure it's valid
        int createdBy;
        try {
            createdBy = Integer.parseInt(createdByText);
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Input Error", "Created By must be a valid number.");
            return;
        }

        // Validate selected courses
        if (selectedCourses == null || selectedCourses.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill all fields and add at least one course.");
            return;
        }

        // Proceed with database insertions
        for (String course : selectedCourses) {
            String insertQuery = "INSERT INTO prospectus (program_id, course_id, pr_effective_year, status, created_by, created_at, updated_at) " +
                                 "VALUES ((SELECT p_id FROM program WHERE p_program_name = ?), " +
                                 "(SELECT c_id FROM course WHERE c_code = ?), ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

            boolean success = db.insertData(insertQuery, program, course, year, status, createdBy);
            if (!success) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add course: " + course);
                return;
            }
        }

        utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Prospectus added successfully!");
        clearFields();
    }

    private void clearFields(){
        selectedCourses.clear();
        courseAddedList.setItems(selectedCourses);
        effectiveYearField.clear();
        createdByField.clear();
        selectProgramComboBox.getSelectionModel().clearSelection();
        selectCoursesComboBox.getSelectionModel().clearSelection();
        active.setSelected(false);
        inactive.setSelected(false);
        archive.setSelected(false);
    }
}
