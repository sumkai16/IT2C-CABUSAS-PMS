package prospectus.admin.courses;

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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import main.dbConnector;
import prospectus.models.Course;
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
    @FXML private TableColumn<Course, Integer> program;
    @FXML private Button deleteSelected;
    @FXML private Button exportCSV;
    @FXML private Button assignPrereq;
    
    private dbConnector db;
    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        setupTableColumns();
        loadCourses();
        populateFilters();
    }
    
    private void setupTableColumns() {
        courseID.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        courseCode.setCellValueFactory(new PropertyValueFactory<>("c_code"));
        courseDescription.setCellValueFactory(new PropertyValueFactory<>("c_desc"));
        courseUnits.setCellValueFactory(new PropertyValueFactory<>("c_units"));
        prerequisite.setCellValueFactory(new PropertyValueFactory<>("prerequisite_id"));
        program.setCellValueFactory(new PropertyValueFactory<>("program_id"));
    }
    
    private void loadCourses() {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        String query = "SELECT * FROM course";
        try (ResultSet resultSet = db.getData(query)) {
            while (resultSet.next()) {
                courses.add(new Course(
                        resultSet.getInt("c_id"),
                        resultSet.getString("c_code"),
                        resultSet.getString("c_desc"),
                        resultSet.getInt("c_units"),
                        resultSet.getInt("prerequisite_id"),
                        resultSet.getInt("program_id")
                ));
            }
            tableView.setItems(courses);
            updateCourseStats();
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR,"Error","Error loading courses: " + e.getMessage());
        }
    }

    private void populateFilters() {
        ObservableList<String> programs = FXCollections.observableArrayList();
        ObservableList<String> units = FXCollections.observableArrayList();
        
        try {
            ResultSet resultSet = db.getData("SELECT DISTINCT program_id FROM course");
            while (resultSet.next()) {
                programs.add(String.valueOf(resultSet.getInt("program_id")));
            }
            filterProgram.setItems(programs);
            
            resultSet = db.getData("SELECT DISTINCT c_units FROM course");
            while (resultSet.next()) {
                units.add(String.valueOf(resultSet.getInt("c_units")));
            }
            filterUnits.setItems(units);
        } catch (SQLException e) {
           utilities.showAlert(Alert.AlertType.ERROR,"Error","Error loading filters: " + e.getMessage());
        }
    }

    private void updateCourseStats() {
        try {
            totalCoursesLabel.setText(getCount("SELECT COUNT(*) FROM course"));
            withPrereqLabel.setText(getCount("SELECT COUNT(*) FROM course WHERE prerequisite_id IS NOT NULL"));
            noPrereqLabel.setText(getCount("SELECT COUNT(*) FROM course WHERE prerequisite_id IS NULL"));
        } catch (SQLException e) {
           utilities.showAlert(Alert.AlertType.ERROR,"Error","Error updating stats: " + e.getMessage());
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
        String program = filterProgram.getValue();
        String units = filterUnits.getValue();
        String query = "SELECT * FROM course WHERE 1=1";
        
        if (program != null) query += " AND program_id = " + program;
        if (units != null) query += " AND c_units = " + units;
        
        filterCourses(query);
    }

    private void filterCourses(String query) {
        ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
        try (ResultSet resultSet = db.getData(query)) {
            while (resultSet.next()) {
                filteredCourses.add(new Course(
                        resultSet.getInt("c_id"),
                        resultSet.getString("c_code"),
                        resultSet.getString("c_desc"),
                        resultSet.getInt("c_units"),
                        resultSet.getInt("prerequisite_id"),
                        resultSet.getInt("program_id")
                ));
            }
            tableView.setItems(filteredCourses);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR,"Error","Error filtering courses: " + e.getMessage());
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
            System.out.println("Add Course Test");
           utilities.loadFXMLWithFade(rootPane, "/prospectus/admin/courses/addCourse.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Scene Error", "Failed to load login page: " + ex.getMessage());
        }
    }

    @FXML
    private void editCourseHandler(MouseEvent event) {
        utilities.showAlert(Alert.AlertType.INFORMATION, "Info", "Edit Course clicked");
    }

    @FXML
    private void resetFilterHandler(MouseEvent event) {
    }

    @FXML
    private void deleteSelectedHandler(MouseEvent event) {
    }
}
