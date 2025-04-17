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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import main.dbConnector;
import prospectus.models.Student;

public class ManageStudentsController implements Initializable {

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> middleNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Integer> yearColumn;
    @FXML
    private TableColumn<Student, String> programColumn;
    @FXML
    private Label totalStudentsLabel;
    @FXML
    private Label withPrereqLabel;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterProgram;
    @FXML
    private ComboBox<Integer> filterYear;
    @FXML
    private Button resetFilters;
    @FXML
    private Button resetFilters1;
    private dbConnector db;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        middleNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMiddleName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        yearColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
        programColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProgram()));

        loadStudents();
    }

    private void loadStudents() {
     String query = "SELECT s.s_id, s.u_id, s.s_fname, s.s_mname, s.s_lname, s.s_bdate, s.s_address, s.s_year, p.p_department " +
                    "FROM student s " +
                    "JOIN program p ON s.s_program = p.p_id";  // Adjust the join condition as necessary

     try {
         ResultSet rs = db.getData(query);
         if (rs == null) {
             System.out.println("ResultSet is null. Check database connection.");
             return;
         }

         studentList.clear(); // Clear the existing list before loading new data

         while (rs.next()) {
             int id = rs.getInt("s_id");
             int userId = rs.getInt("u_id");
             String firstName = rs.getString("s_fname");
             String middleName = rs.getString("s_mname");
             String lastName = rs.getString("s_lname");
             LocalDate birthDate = rs.getDate("s_bdate").toLocalDate(); 
             String address = rs.getString("s_address");
             int year = rs.getInt("s_year");
             String department = rs.getString("p_department"); // Get the department name instead of program ID

             // Create a new Student object and add it to the list
             studentList.add(new Student(id, userId, firstName, middleName, lastName, birthDate, address, year, department));
         }

         tableView.setItems(studentList);
         totalStudentsLabel.setText("Total Students: " + studentList.size());

     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

    @FXML
    private void addStudentHandler(MouseEvent event) {
        System.out.println("Add Student clicked");
        
    }

    @FXML
    private void editStudentHandler(MouseEvent event) {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            System.out.println("Edit Student clicked for: " + selectedStudent.getFirstName());
        } else {
            System.out.println("No student selected for editing.");
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
}