package prospectus.admin.courses;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.Course;
import prospectus.utilities.adminUtilities;
import prospectus.utilities.utilities;

public class ManageCoursesController implements Initializable {
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterProgram;
    @FXML private ComboBox<String> filterUnits;
    @FXML private Button resetFilters;
    @FXML private Label totalCoursesLabel;
    @FXML private Label withPrereqLabel;
    @FXML private Label noPrereqLabel;
    @FXML private TableView<Course> tableView;
    @FXML private TableColumn<Course, Integer> courseID;
    @FXML private TableColumn<Course, String> courseCode;
    @FXML private TableColumn<Course, String> courseDescription;
    @FXML private TableColumn<Course, Integer> courseUnits;
    @FXML private TableColumn<Course, Integer> prerequisite;
    @FXML private TableColumn<Course, String> program;
    @FXML private Button deleteSelected;
    @FXML private Button exportCSV;
    @FXML private Button assignPrereq;
    
    private ObservableList<Course> courseList;
    private dbConnector db;
    @FXML private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        courseList = FXCollections.observableArrayList();
        setupTableColumns();
        loadCourses();
        populateFilters();
        setupSearchListener();
        
        filterProgram.setOnAction(event -> filterHandler());
        filterUnits.setOnAction(event -> filterHandler());
        TextArea logTextArea = null;
        
        adminUtilities utilities = new adminUtilities(db, logTextArea);

        
        utilities.setupDoubleClickListener(tableView, selectedCourse -> {
            if (selectedCourse instanceof Course) {
                viewCourse((Course) selectedCourse); // Call the edit method
            }
        });
    }
    public void viewCourse(Course course) {
        System.out.println("Test Double Click");
        try {
            utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/courses/courseInfo.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load add course page: " + ex.getMessage());
        }
    }
    private void setupSearchListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim().toLowerCase();
            ObservableList<Course> filteredList = searchCourses(courseList, searchText);
            tableView.setItems(filteredList);
        });
    }

    private void setupTableColumns() {
        courseID.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        courseCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));
        courseDescription.setCellValueFactory(new PropertyValueFactory<>("c_desc"));
        courseUnits.setCellValueFactory(new PropertyValueFactory<>("c_units"));
        prerequisite.setCellValueFactory(new PropertyValueFactory<>("prerequisiteCode"));
        program.setCellValueFactory(new PropertyValueFactory<>("programDepartment"));
    }

    private void loadCourses() {
        String query = "SELECT c.*, p.p_department AS program_department, " +
                       "prereq.c_code AS prerequisite_code " +
                       "FROM course c " +
                       "LEFT JOIN program p ON c.program_id = p.p_id " +
                       "LEFT JOIN course prereq ON c.prerequisite_id = prereq.c_id";

        try (ResultSet resultSet = db.getData(query)) {
            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("c_id"),
                        resultSet.getString("c_code"),
                        resultSet.getString("c_desc"),
                        resultSet.getInt("c_units"),
                        resultSet.getInt("prerequisite_id"),
                        resultSet.getInt("program_id")
                );

                course.setProgramDepartment(resultSet.getString("program_department"));
                String prerequisiteCode = resultSet.getString("prerequisite_code");
                course.setPrerequisiteCode(prerequisiteCode != null ? prerequisiteCode : ""); // Set to empty string if null

                courseList.add(course);
            }
            tableView.setItems(courseList);
            updateCourseStats();
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Error loading courses: " + e.getMessage());
        }
    }

    private void populateFilters() {
        ObservableList<String> programs = FXCollections.observableArrayList();
        ObservableList<String> units = FXCollections.observableArrayList();
        
        try {
            ResultSet resultSet = db.getData("SELECT DISTINCT p.p_department FROM program p JOIN course c ON p.p_id = c.program_id");
            while (resultSet.next()) {
                programs.add(resultSet.getString("p_department"));
            }
            filterProgram.setItems(programs);
            
            resultSet = db.getData("SELECT DISTINCT c_units FROM course");
            while (resultSet.next()) {
                units.add(String.valueOf(resultSet.getInt("c_units")));
            }
            filterUnits.setItems(units);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Error loading filters: " + e.getMessage());
        }
    }

    private void updateCourseStats() {
        try {
            totalCoursesLabel.setText(getCount("SELECT COUNT(*) FROM course"));
            withPrereqLabel.setText(getCount("SELECT COUNT(*) FROM course WHERE prerequisite_id IS NOT NULL"));
            noPrereqLabel.setText(getCount("SELECT COUNT(*) FROM course WHERE prerequisite_id IS NULL"));
        } catch (SQLException e) {
            utilities.showAlert(Alert .AlertType.ERROR, "Error", "Error updating stats: " + e.getMessage());
        }
    }

    private String getCount(String query) throws SQLException {
        ResultSet resultSet = db.getData(query);
        return resultSet.next() ? String.valueOf(resultSet.getInt(1)) : "0";
    }

    @FXML
    private void filterProgramHandler(MouseEvent event) {
        filterHandler();
    }

    @FXML
    private void filterUnitsHandler(MouseEvent event) {
        filterHandler();
    }

    private void filterHandler() {
        String programDept = filterProgram.getValue();
        String units = filterUnits.getValue();

       
        StringBuilder query = new StringBuilder("SELECT c.*, p.p_department AS program_department FROM course c LEFT JOIN program p ON c.program_id = p.p_id WHERE 1=1");

        
        if (programDept != null && !programDept.isEmpty()) {
            query.append(" AND p.p_department = '").append(programDept).append("'");
        }
        if (units != null && !units.isEmpty()) {
            query.append(" AND c.c_units = ").append(units);
        }

        
        filterCourses(query.toString());
    }

    private void filterCourses(String query) {
        ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
        try (ResultSet resultSet = db.getData(query)) {
            while (resultSet.next()) {
                Course course = new Course(
                        resultSet.getInt("c_id"),
                        resultSet.getString("c_code"),
                        resultSet.getString("c_desc"),
                        resultSet.getInt("c_units"),
                        resultSet.getInt("prerequisite_id"),
                        resultSet.getInt("program_id")
                );

                course.setProgramDepartment(resultSet.getString("program_department"));
                filteredCourses.add(course);
            }

            if (filteredCourses.isEmpty()) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "No Results", "No courses found matching the selected filters.");
            }
            tableView.setItems(filteredCourses); // Set the filtered courses to the TableView
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Error filtering courses: " + e.getMessage());
        }
    }
    @FXML
    private void sortByCourseCodeHandler(MouseEvent event) {
        tableView.getSortOrder().setAll(courseCode);
    }

    @FXML
    private void sortByUnitsHandler(MouseEvent event) {
        tableView.getSortOrder().setAll(courseUnits);
    }

    @FXML
    private void sortByProgramHandler(MouseEvent event) {
        tableView.getSortOrder().setAll(program);
    }

    @FXML
    private void addCourseHandler(MouseEvent event) {
        try {
            utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/courses/addCourse.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load add course page: " + ex.getMessage());
        }
    }

    @FXML
    private void editCourseHandler(MouseEvent event) {
        Course selectedCourse = tableView.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a course to edit.");
            return;
        }

        utilities.loadFXMLWithFadeEdit(rootPane, "/prospectus/admin/courses/editCourse.fxml", controller -> {
            EditCourseController editController = (EditCourseController) controller;
            editController.setCourseData(selectedCourse);
        });
    }

    @FXML
    private void resetFilterHandler(MouseEvent event) {
        filterProgram.setValue(null);
        filterUnits.setValue(null);
//        loadCourses();
    }

    @FXML
    private void deleteSelectedHandler(MouseEvent event) {
        // Implementation for deleting selected course
    }

    private ObservableList<Course> searchCourses(ObservableList<Course> courses, String searchText) {
        if (searchText.isEmpty()) {
            return courses; // Return the full list if the search text is empty
        }

        return courses.filtered(course -> 
            String.valueOf(course.getC_id()).toLowerCase().contains(searchText) || 
            (course.getC_code() != null && course.getC_code().toLowerCase().contains(searchText)) || 
            (course.getC_desc() != null && course.getC_desc().toLowerCase().contains(searchText)) || 
            String.valueOf(course.getC_units()).toLowerCase().contains(searchText) ||
            (course.getPrerequisiteCode() != null && course.getPrerequisiteCode().toLowerCase().contains(searchText)) || 
            (course.getProgramDepartment() != null && course.getProgramDepartment().toLowerCase().contains(searchText))
        );
    }
}