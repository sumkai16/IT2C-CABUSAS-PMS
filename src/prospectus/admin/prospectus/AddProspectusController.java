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
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;

public class AddProspectusController implements Initializable {

    @FXML
    private Pane backgroundPane;

    @FXML
    private ComboBox<String> selectProgramComboBox;

    @FXML
    private TextField effectiveYearField;

    @FXML
    private ComboBox<String> selectYearLevelComboBox;

    @FXML
    private ComboBox<String> selectSemesterComboBox;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TextField displayCourseInfoHere;
  

    
    @FXML
    private ComboBox<String> selectCoursesCodeComboBox;

    @FXML
    private TableColumn<Course, String> courseCodeColumn; // Specify the type for better type safety
    @FXML
    private TableColumn<Course, String> courseTitleColumn;
    @FXML
    private TableColumn<Course, Integer> unitsColumn;

    private ObservableList<String> programList = FXCollections.observableArrayList();
    private ObservableList<String> courseList = FXCollections.observableArrayList();
    private ObservableList<Course> selectedCourses = FXCollections.observableArrayList();

    private dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPrograms();
        loadCourses();
        loadYearLevels();
        loadSemesters();  
        selectCoursesCodeComboBox.setItems(courseList);
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
        ObservableList<String> yearLevels = FXCollections.observableArrayList("1st Year", "2nd Year", "3rd Year", "4th Year");
        selectYearLevelComboBox.setItems(yearLevels);
    }

    private void loadSemesters() {
        ObservableList<String> semesters = FXCollections.observableArrayList("1st Semester", "2nd Semester");
        selectSemesterComboBox.setItems(semesters);
    }


   @FXML
    private void addCourseHandler(MouseEvent event) {
        String courseCode = selectCoursesCodeComboBox.getValue();

        if (courseCode != null) {
            // Find the course in the selectedCourses list to get the units
            Course selectedCourse = null;
            for (Course course : selectedCourses) {
                if (course.getCode().equals(courseCode)) {
                    selectedCourse = course;
                    break;
                }
            }

            if (selectedCourse != null) {
                // Use the units from the selected course
                int units = selectedCourse.getUnits();
                Course courseToAdd = new Course(courseCode, displayCourseInfoHere.getText(), units);
                selectedCourses.add(courseToAdd);
                courseTableView.getItems().add(courseToAdd);
                clearCourseSelection(); // Clear selection after adding
                System.out.println("Course added!");
            } else {
                utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Selected course not found in the list.");
            }
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please select a course.");
        }
    }

    private void clearCourseSelection() {
        selectCoursesCodeComboBox.getSelectionModel().clearSelection();
        displayCourseInfoHere.clear();
      
    }

    @FXML
    private void removeSelectedHandler(MouseEvent event) {
        Course selectedCourse = courseTableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourses.remove(selectedCourse);
            courseTableView.getItems().remove(selectedCourse);
        }
    }

    @FXML
    private void addProspectusHandler(MouseEvent event) {
        String program = selectProgramComboBox.getValue();
        if (program == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Program selection is required.");
            return;
        }

        String year = effectiveYearField.getText().trim();
        if (!year.matches("\\d{4} - \\d{4}")) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Effective Year must be in the format 'YYYY - YYYY'.");
            return;
        }

        // Assuming you have ComboBoxes or TextFields for year level and semester
        String yearLevel = selectYearLevelComboBox.getValue(); // Replace with your actual UI component
        if (yearLevel == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Year Level selection is required.");
            return;
        }

        String semester = selectSemesterComboBox.getValue(); // Replace with your actual UI component
        if (semester == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Semester selection is required.");
            return;
        }

        if (selectedCourses.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please add at least one course.");
            return;
        }

        // Assuming you have a method to get the current user's ID
        int currentUserId = UserSession.getUserId(); // Implement this method to retrieve the current user's ID

        for (Course course : selectedCourses) {
            String insertQuery = "INSERT INTO prospectus (program_id, course_id, pr_effective_year, status, created_by, year_level, semester) " +
                                 "VALUES ((SELECT p_id FROM program WHERE p_program_name = ?), " +
                                 "(SELECT c_id FROM course WHERE c_code = ?), ?, ?, ?, ?, ?)";

            boolean success = db.insertData(insertQuery, program, course.getCode(), year, "Active", currentUserId, yearLevel, semester);
            if (!success) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add course: " + course.getCode());
                return;
            }
        }

        utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Prospectus added successfully!");
        clearFields();
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
    private void returnHandler(MouseEvent event) {
        // Implement return logic if needed
    }

    @FXML
    private void onCourseSelection(ActionEvent event) {
        String selectedCourseCode = selectCoursesCodeComboBox.getValue();
        if (selectedCourseCode != null) {
            try (Connection conn = db.getConnection();
                 ResultSet rs = conn.createStatement().executeQuery("SELECT c_desc, c_units FROM course WHERE c_code = '" + selectedCourseCode + "'")) {
                if (rs.next()) {
                    String courseTitle = rs.getString("c_desc");
                    int units = rs.getInt("c_units"); // Retrieve units as an integer
                    // Set the TextField with formatted string
                    displayCourseInfoHere.setText("Course: " + courseTitle + " | Units: " + String.valueOf(units));

                    // Create a Course object and add it to selectedCourses
                    Course course = new Course(selectedCourseCode, courseTitle, units);
                    // Check if the course is already in the list to avoid duplicates
                    if (!selectedCourses.contains(course)) {
                        selectedCourses.add(course); // Add the course to the list
                    }
                }
            } catch (SQLException e) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve course info.");
            }
        }
    }

    public class Course {
        private String code;
        private String info;
        private int units;

        public Course(String code, String info, int units) {
            this.code = code;
            this.info = info;
            this.units = units;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public int getUnits() {
            return units;
        }
    }
}