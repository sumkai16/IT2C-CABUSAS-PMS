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
import javafx.scene.control.Alert;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.utilities;
import javafx.scene.control.cell.PropertyValueFactory;

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
        
        // Set up table columns
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("info"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        
        // Set the items for the table
        courseTableView.setItems(selectedCourses);
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

        // Add listener to handle summer semester selection
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
                    int units = rs.getInt("c_units"); // Retrieve units as an integer
                    // Set the TextField with formatted string
                    displayCourseInfoHere.setText("Course: " + courseTitle + " | Units: " + String.valueOf(units));
                }
            } catch (SQLException e) {
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve course info.");
            }
        }
    }

    @FXML
    private void addCourseHandler(MouseEvent event) {
        String courseCode = selectCoursesCodeComboBox.getValue();
        if (courseCode != null) {
            try (Connection conn = db.getConnection();
                 ResultSet rs = conn.createStatement().executeQuery("SELECT c_desc, c_units FROM course WHERE c_code = '" + courseCode + "'")) {
                if (rs.next()) {
                    String courseTitle = rs.getString("c_desc");
                    int units = rs.getInt("c_units");
                    
                    // Create and add the course
                    Course courseToAdd = new Course(courseCode, courseTitle, units);
                    if (!selectedCourses.contains(courseToAdd)) {
                        
                        courseTableView.getItems().add(courseToAdd);
                        clearCourseSelection();
                        System.out.println("Course added!");
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
        if (!year.matches("\\d{4}[- ]\\d{4}")) { // Accepts "2023-2024" or "2023 - 2024"
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

        int currentUserId = UserSession.getUserId(); // Get the current user's ID

        // Step 1: Insert into the prospectus table
        String insertProspectusQuery = "INSERT INTO prospectus (program_id, pr_effective_year, status, created_by) " +
                                        "VALUES ((SELECT p_id FROM program WHERE p_program_name = ?), ?, 'Active', ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(insertProspectusQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, program);
            pst.setString(2, year);
            pst.setInt(3, currentUserId); // Set the created_by value
            pst.executeUpdate();

            // Get the generated pr_id
            ResultSet generatedKeys = pst.getGeneratedKeys();
            if (generatedKeys.next()) {
                int prId = generatedKeys.getInt(1); // Get the generated pr_id

                // Step 2: Insert into the prospectus_details table
                String insertDetailsQuery = "INSERT INTO prospectus_details (pr_id, course_id, year_level, semester) " +
                                             "VALUES (?, (SELECT c_id FROM course WHERE c_code = ?), ?, ?)";
                try (PreparedStatement detailsPst = conn.prepareStatement(insertDetailsQuery)) {
                    for (Course course : selectedCourses) {
                        detailsPst.setInt(1, prId); // Set the pr_id
                        detailsPst.setString(2, course.getCode()); // Set the course code
                        detailsPst.setString(3, yearLevel); // Set the year level
                        detailsPst.setString(4, semester); 
                        detailsPst.addBatch(); // Add to batch
                    }
                    detailsPst.executeBatch(); // Execute batch insert for details
                }
                logger.addLog(UserSession.getUsername(), "Prospectus", "Prospectus added successfully!: " + UserSession.getUsername());
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Prospectus added successfully!");
                clearFields(); // Clear fields after successful insertion
            }
        } catch (SQLException e) {
            logger.addLog(UserSession.getUsername(), "Prospectus", "Attempted to add prospectus.: " + UserSession.getUsername());
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add prospectus: " + e.getMessage());
        }
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