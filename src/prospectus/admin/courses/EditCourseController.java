package prospectus.admin.courses;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.Course;
import prospectus.utilities.utilities;

public class EditCourseController implements Initializable {

    @FXML
    private TextField courseIdField;
    @FXML
    private TextField courseCodeField;
    @FXML
    private TextField courseDescField;
    @FXML
    private TextField courseUnitsField;
    @FXML
    private ComboBox<String> prerequisiteComboBox;
    private ComboBox<String> programComboBox;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Button updateButton;
    
    private ImageView courseImage;
    private dbConnector db;
    private int selectedCourseId;
    
    // Maps to store ID-to-value relationships
    private Map<String, Integer> prereqMap = new HashMap<>();
    private Map<String, Integer> programMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        
        courseIdField.setEditable(false);
        courseIdField.setDisable(true);
        
        
        loadPrerequisites();
        loadPrograms();
    }
    
    
    public void setCourseData(Course course) {
        if (course == null) {
            return;
        }
        
        this.selectedCourseId = course.getC_id();
        
        loadCourseDetails(course.getC_code());
    }
    
    
    private void loadPrerequisites() {
        ObservableList<String> prerequisites = FXCollections.observableArrayList();
        prerequisites.add("None"); 
        prereqMap.put("None", 0);
        
        String query = "SELECT c_id, c_code FROM course ORDER BY c_code";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int courseId = rs.getInt("c_id");
                String courseCode = rs.getString("c_code");
                prerequisites.add(courseCode);
                prereqMap.put(courseCode, courseId);
            }
            
            prerequisiteComboBox.setItems(prerequisites);
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Error loading prerequisites.");
        }
    }
    
    
    private void loadPrograms() {
        ObservableList<String> programs = FXCollections.observableArrayList();
        
        String query = "SELECT p_id, p_department FROM program ORDER BY p_department";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                int programId = rs.getInt("p_id");
                String programName = rs.getString("p_department");
                programs.add(programName);
                programMap.put(programName, programId);
            }
            
            programComboBox.setItems(programs);
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Error loading programs.");
        }
    }
    
    
    private void loadCourseDetails(String courseCode) {
        if (courseCode == null || courseCode.isEmpty()) {
            return;
        }

        String query = "SELECT c.*, pre.c_code as prereq_code " +
                       "FROM course c " +
                       "LEFT JOIN course pre ON c.prerequisite_id = pre.c_id " +
                       "WHERE c.c_code = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                selectedCourseId = rs.getInt("c_id");
                courseIdField.setText(String.valueOf(selectedCourseId));
                courseCodeField.setText(rs.getString("c_code"));
                courseDescField.setText(rs.getString("c_desc"));
                courseUnitsField.setText(String.valueOf(rs.getInt("c_units")));

                String prereqCode = rs.getString("prereq_code");
                if (prereqCode == null) {
                    prerequisiteComboBox.setValue("None");
                } else {
                    prerequisiteComboBox.setValue(prereqCode);
                }

                System.out.println("Course details loaded successfully for: " + courseCode);
            } else {
                System.out.println("No course found with code: " + courseCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Error loading course details.");
        }
    }

    @FXML
    private void updateCourseHandler(MouseEvent event) {
        String courseCode = courseCodeField.getText().trim();
        String courseDesc = courseDescField.getText().trim();
        int courseUnits;
        Integer prerequisiteId; // Changed from int to Integer to allow NULL

        try {
            courseUnits = Integer.parseInt(courseUnitsField.getText().trim());
            if (courseUnits <= 0) {
                utilities.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Course units must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.ERROR, " Invalid Input", "Course units must be a number.");
            return;
        }

        String prereqValue = prerequisiteComboBox.getValue();
        if (prereqValue == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Prerequisite must be selected.");
            return;
        }

        // Set prerequisiteId to NULL if "None" is selected
        prerequisiteId = "None".equals(prereqValue) ? null : prereqMap.get(prereqValue);

        if (courseCode.isEmpty() || courseDesc.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all required fields.");
            return;
        }

        if (prerequisiteId != null && prerequisiteId == selectedCourseId) {
            utilities.showAlert(Alert.AlertType.ERROR, "Invalid Prerequisite", 
                          "A course cannot be a prerequisite for itself.");
            return;
        }

        // Update the query to handle NULL prerequisite
        String query = "UPDATE course SET c_code = ?, c_desc = ?, c_units = ?, prerequisite_id = ? WHERE c_id = ?";
        boolean success = db.updateData(query, courseCode, courseDesc, courseUnits, prerequisiteId, selectedCourseId);

        if (success) {
            utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully.");
            utilities.closeOverlay(overlayPane); 
        } else {
            utilities.showAlert(Alert.AlertType.ERROR, "Update Failed", "Error updating the course.");
        }
    }
    
   
    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }

   
    @FXML
    private void selectPrerequisiteHandler(MouseEvent event) {
        
    }

   
}

