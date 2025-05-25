package prospectus.admin.students;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;
import prospectus.models.Student;
import prospectus.utilities.utilities;

public class ManageStudentsController implements Initializable {
    
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> firstNameColumn;
    @FXML private TableColumn<Student, String> middleNameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, Integer> yearColumn;
    @FXML private TableColumn<Student, String> programColumn;
    @FXML private TableColumn<Student, String> enrollmentStatusColumn;
    @FXML private Label totalStudentsLabel;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private dbConnector db;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterProgram;
    @FXML
    private ComboBox<Integer> filterYear;
    @FXML
    private Button resetFilters;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Label withPrereqLabel;
    @FXML
    private Button resetFilters1;
    @FXML
    private Button approvestudent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();

        // Set cell value factories
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
        enrollmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("enrollmentStatus"));

        // Load student data
        loadStudents();

        // Debugging output
        for (Student s : studentList) {
            System.out.println("Student loaded: " + s.getFirstName());
        }
    }

    void loadStudents() {
        studentList.clear();

        String query = "SELECT " +
"                s.s_id, s.u_id, s.s_fname, s.s_mname, s.s_lname, s.s_bdate," +
"                s.s_address, s.s_year, p.p_department, s.previous_school," +
"                s.s_sex, s.s_image, u.enrollment_status" +
"            FROM student s" +
"            JOIN program p ON s.s_program = p.p_id" +
"            JOIN user u ON s.u_id = u.u_id";

        try (ResultSet rs = db.getData(query)) {
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("s_id"),
                    rs.getInt("u_id"),
                    rs.getString("s_fname"),
                    rs.getString("s_mname"),
                    rs.getString("s_lname"),
                    rs.getDate("s_bdate").toLocalDate(),
                    rs.getString("s_address"),
                    rs.getInt("s_year"),
                    rs.getString("p_department"),
                    rs.getString("previous_school"),
                    rs.getString("s_sex"),
                    rs.getString("s_image"),
                    rs.getString("enrollment_status")
                );
                studentList.add(student);
            }

            tableView.setItems(studentList);
            totalStudentsLabel.setText(String.valueOf(studentList.size()));
            System.out.println("Loaded students: " + studentList.size());

        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load students: " + e.getMessage());
        }
    }




    @FXML
    private void editStudentHandler(MouseEvent event) {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                utilities.loadFXMLWithFadeEdit(overlayPane, "/prospectus/admin/students/editStudent.fxml", controller -> {
                    if (controller instanceof EditStudentController) {
                        EditStudentController editController = (EditStudentController) controller;
                        editController.setStudentData(selectedStudent, this); // Pass the current instance
                    }
                });
            } catch (Exception ex) {
                utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Edit Student page: " + ex.getMessage());
            }
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to edit.");
        }
    }

    @FXML
    private void filterProgramHandler(MouseEvent event) {
        String selectedProgram = filterProgram.getValue();
        if (selectedProgram != null) {
            ObservableList<Student> filteredList = FXCollections.observableArrayList();
            for (Student student : studentList) {
                if (student.getProgram().equals(selectedProgram)) {
                    filteredList.add(student);
                }
            }
            tableView.setItems(filteredList);
        }
    }

    @FXML
    private void filterYearHandler(MouseEvent event) {
        Integer selectedYear = filterYear.getValue();
        if (selectedYear != null) {
            ObservableList<Student> filteredList = FXCollections.observableArrayList();
            for (Student student : studentList) {
                if (student.getYear() == selectedYear) {
                    filteredList.add(student);
                }
            }
            tableView.setItems(filteredList);
        }
    }

    @FXML
    private void resetFilterHandler(MouseEvent event) {
        filterProgram.getSelectionModel().clearSelection();
        filterYear.getSelectionModel().clearSelection();
        tableView.setItems(studentList);
    }

    @FXML
    private void searchHandler(MouseEvent event) {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Student> searchResults = FXCollections.observableArrayList();
        for (Student student : studentList) {
            if (student.getFirstName().toLowerCase().contains(searchText) ||
                student.getLastName().toLowerCase().contains(searchText)) {
                searchResults.add(student);
            }
        }
        tableView.setItems(searchResults);
    }

    @FXML
    private void enrollStudentHandler(MouseEvent event) {
        try {
            utilities.loadFXMLWithFade(overlayPane, "/prospectus/admin/student/enrollmentForm.fxml");
        } catch (Exception ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Enrollment page: " + ex.getMessage());
        }
    }

    @FXML
    private void approveStudentHandler(MouseEvent event) {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            if ("pending".equalsIgnoreCase(selectedStudent.getEnrollmentStatus())) {
                String updateQuery = "UPDATE user SET enrollment_status = 'Enrolled' WHERE u_id = ?";
                boolean success = db.updateData(updateQuery, selectedStudent.getUserId());
                if (success) {
                    utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Student enrollment status updated to enrolled.");
                    loadStudents(); // Refresh the table
                } else {
                    utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update enrollment status.");
                }
            } else {
                utilities.showAlert(Alert.AlertType.WARNING, "Invalid Status", "Only students with pending status can be approved.");
            }
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a student to approve.");
        }
    }
}