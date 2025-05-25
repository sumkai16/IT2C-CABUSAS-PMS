package prospectus.admin.prospectus;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dbConnector;
import prospectus.models.Prospectus;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.utilities;

public class EditProspectusController implements Initializable {


    @FXML
    private ComboBox<String> selectProgramComboBox;

    @FXML
    private TextField effectiveYearField;

    @FXML
    private ComboBox<String> selectYearLevelComboBox;

    @FXML
    private ComboBox<String> selectSemesterComboBox;

    @FXML
    private TableView<prospectus.models.Course> courseTableView;

    @FXML
    private TextField displayCourseInfoHere;

    @FXML
    private ComboBox<String> selectCoursesCodeComboBox;

    @FXML
    private TableColumn<prospectus.models.Course, String> courseCodeColumn;
    @FXML
    private TableColumn<prospectus.models.Course, String> courseTitleColumn;
    @FXML
    private TableColumn<prospectus.models.Course, Integer> unitsColumn;

    private ObservableList<String> programList = FXCollections.observableArrayList();
    private ObservableList<String> courseList = FXCollections.observableArrayList();
    private ObservableList<prospectus.models.Course> selectedCourses = FXCollections.observableArrayList();

    private dbConnector db = new dbConnector();

    private Prospectus currentProspectus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPrograms();
        loadCourses();
        loadYearLevels();
        loadSemesters();
        selectCoursesCodeComboBox.setItems(courseList);

        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<prospectus.models.Course, String>("c_code"));
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<prospectus.models.Course, String>("c_desc"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<prospectus.models.Course, Integer>("c_units"));

        courseTableView.setItems(selectedCourses);
    }

    public void setProspectus(Prospectus prospectus) {
        this.currentProspectus = prospectus;
        prefillData();
    }

    private void prefillData() {
        if (currentProspectus == null) return;

        selectProgramComboBox.setValue(currentProspectus.getPProgramName());
        effectiveYearField.setText(currentProspectus.getPrEffectiveYear());
        selectYearLevelComboBox.setValue(currentProspectus.getYearLevel());
        selectSemesterComboBox.setValue(currentProspectus.getSemester());

        loadCoursesForProspectus(currentProspectus.getPrId(), currentProspectus.getYearLevel(), currentProspectus.getSemester());
    }

    private void loadCoursesForProspectus(int prId, String yearLevel, String semester) {
        selectedCourses.clear();
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                 "SELECT c.c_id, c.c_code, c.c_desc, c.c_units, c.prerequisite_id FROM prospectus_details pd " +
                 "JOIN course c ON pd.course_id = c.c_id WHERE pd.pr_id = ? AND pd.year_level = ? AND pd.semester = ?")) {
            pst.setInt(1, prId);
            pst.setString(2, yearLevel);
            pst.setString(3, semester);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int c_id = rs.getInt("c_id");
                String code = rs.getString("c_code");
                String desc = rs.getString("c_desc");
                int units = rs.getInt("c_units");
                Integer prerequisiteId = rs.getObject("prerequisite_id") != null ? rs.getInt("prerequisite_id") : null;
                selectedCourses.add(new prospectus.models.Course(c_id, code, desc, units, prerequisiteId));
            }
            courseTableView.setItems(selectedCourses);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load courses for prospectus.");
        }
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
             ResultSet rs = conn.createStatement().executeQuery("SELECT c_code, c_desc FROM course")) {
            while (rs.next()) {
                String courseCode = rs.getString("c_code");
                courseList.add(courseCode);
            }
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load courses.");
        }
    }

    private void loadYearLevels() {
        ObservableList<String> yearLevels = FXCollections.observableArrayList(
            "1st Year", "2nd Year", "3rd Year", "4th Year", "Summer"
        );
        selectYearLevelComboBox.setItems(yearLevels);
    }

    private void loadSemesters() {
        ObservableList<String> semesters = FXCollections.observableArrayList(
            "1st Semester", "2nd Semester", "Summer"
        );
        selectSemesterComboBox.setItems(semesters);

        selectYearLevelComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Summer".equals(newVal)) {
                selectSemesterComboBox.setValue("Summer");
                selectSemesterComboBox.setDisable(true);
            } else {
                selectSemesterComboBox.setDisable(false);
                if ("Summer".equals(selectSemesterComboBox.getValue())) {
                    selectSemesterComboBox.setValue("1st Semester");
                }
            }
            // Reload courses when year level changes
            if (currentProspectus != null && newVal != null && selectSemesterComboBox.getValue() != null) {
                loadCoursesForProspectus(currentProspectus.getPrId(), newVal, selectSemesterComboBox.getValue());
            }
        });

        selectSemesterComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // Reload courses when semester changes
            if (currentProspectus != null && newVal != null && selectYearLevelComboBox.getValue() != null) {
                loadCoursesForProspectus(currentProspectus.getPrId(), selectYearLevelComboBox.getValue(), newVal);
            }
        });
    }

    @FXML
    private void onCourseSelection(ActionEvent event) {
        String selectedCourseCode = selectCoursesCodeComboBox.getValue();
        if (selectedCourseCode != null) {
            try (Connection conn = db.getConnection();
                 ResultSet rs = conn.createStatement().executeQuery("SELECT c_desc, c_units FROM course WHERE c_code = '" + selectedCourseCode + "'")) {
                if (rs.next()) {
                    String courseTitle = rs.getString("c_desc");
                    int units = rs.getInt("c_units");
                    displayCourseInfoHere.setText("Course: " + courseTitle + " | Units: " + units);
                }
            } catch (SQLException e) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve course info.");
            }
        }
    }

    @FXML
    private void addCourseHandler(ActionEvent event) {
        String courseCode = selectCoursesCodeComboBox.getValue();
        if (courseCode != null) {
            try (Connection conn = db.getConnection();
                 ResultSet rs = conn.createStatement().executeQuery("SELECT c_id, c_code, c_desc, c_units, prerequisite_id FROM course WHERE c_code = '" + courseCode + "'")) {
                if (rs.next()) {
                    int c_id = rs.getInt("c_id");
                    String code = rs.getString("c_code");
                    String desc = rs.getString("c_desc");
                    int units = rs.getInt("c_units");
                    Integer prerequisiteId = rs.getObject("prerequisite_id") != null ? rs.getInt("prerequisite_id") : null;
                    prospectus.models.Course courseToAdd = new prospectus.models.Course(c_id, code, desc, units, prerequisiteId);
                    // Check if course already exists in the table
                    boolean isDuplicate = selectedCourses.stream()
                        .anyMatch(course -> course.getC_code().equals(code));
                    if (!isDuplicate) {
                        selectedCourses.add(courseToAdd);
                        courseTableView.setItems(selectedCourses);
                        clearCourseSelection();
                    } else {
                        utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Course already added to the list.");
                    }
                }
            } catch (SQLException e) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add course: " + e.getMessage());
            }
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please select a course.");
        }
    }

   

    @FXML
    private void updateProspectusHandler(ActionEvent event) {
        String program = selectProgramComboBox.getValue();
        if (program == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Program selection is required.");
            return;
        }

        String year = effectiveYearField.getText().trim();
        if (!year.matches("\\d{4}[- ]\\d{4}")) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Effective Year must be in the format 'YYYY-YYYY' or 'YYYY - YYYY'.");
            return;
        }

        String yearLevel = selectYearLevelComboBox.getValue();
        if (yearLevel == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Year Level selection is required.");
            return;
        }

        String semester = selectSemesterComboBox.getValue();
        if (semester == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Semester selection is required.");
            return;
        }

        if (selectedCourses.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please add at least one course.");
            return;
        }

        try (Connection conn = db.getConnection()) {
            String updateProspectusQuery = "UPDATE prospectus SET pr_effective_year = ?, status = 'Active' WHERE pr_id = ?";
            PreparedStatement updatePst = conn.prepareStatement(updateProspectusQuery);
            updatePst.setString(1, year);
            updatePst.setInt(2, currentProspectus.getPrId());
            updatePst.executeUpdate();

            String deleteDetailsQuery = "DELETE FROM prospectus_details WHERE pr_id = ? AND year_level = ? AND semester = ?";
            PreparedStatement deletePst = conn.prepareStatement(deleteDetailsQuery);
            deletePst.setInt(1, currentProspectus.getPrId());
            deletePst.setString(2, yearLevel);
            deletePst.setString(3, semester);
            deletePst.executeUpdate();

            String insertDetailsQuery = "INSERT INTO prospectus_details (pr_id, course_id, year_level, semester) " +
                                        "VALUES (?, (SELECT c_id FROM course WHERE c_code = ?), ?, ?)";
            PreparedStatement detailsPst = conn.prepareStatement(insertDetailsQuery);
            for (prospectus.models.Course course : selectedCourses) {
                detailsPst.setInt(1, currentProspectus.getPrId());
                detailsPst.setString(2, course.getC_code());
                detailsPst.setString(3, yearLevel);
                detailsPst.setString(4, semester);
                detailsPst.addBatch();
            }
            detailsPst.executeBatch();

            logger.addLog(UserSession.getUsername(), "Prospectus", "Prospectus updated successfully: " + UserSession.getUsername());
            utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Prospectus updated successfully!");
            clearFields();

        } catch (SQLException e) {
            logger.addLog(UserSession.getUsername(), "Prospectus", "Failed to update prospectus: " + UserSession.getUsername());
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update prospectus: " + e.getMessage());
        }
    }

    private void clearCourseSelection() {
        selectCoursesCodeComboBox.getSelectionModel().clearSelection();
        displayCourseInfoHere.clear();
    }

    private void clearFields() {
        selectedCourses.clear();
        courseTableView.getItems().clear();
        effectiveYearField.clear();
        selectProgramComboBox.getSelectionModel().clearSelection();
        selectCoursesCodeComboBox.getSelectionModel().clearSelection();
        displayCourseInfoHere.clear();
    }

    @FXML
    private void removeSelectedHandler(ActionEvent event) {
         prospectus.models.Course selectedCourse = courseTableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourses.remove(selectedCourse);
            courseTableView.setItems(selectedCourses);
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a course to remove.");
        }
    }
}
