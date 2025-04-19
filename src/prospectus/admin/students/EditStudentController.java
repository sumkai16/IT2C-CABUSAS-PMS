
package prospectus.admin.students;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import prospectus.utilities.utilities;
import main.dbConnector;
import prospectus.models.Student;

public class EditStudentController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Pane bgPane;
    @FXML
    private TextField fnameField;
    @FXML
    private TextField mnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private DatePicker bdateField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField yearField;
    @FXML
    private ComboBox<String> selectProgram; // Specify the type for ComboBox
    @FXML
    private TextField prevSchool;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button submitButton;
    @FXML
    private RadioButton maleRbtn;
    @FXML
    private RadioButton femaleRbtn;

    private dbConnector db;
    private String photoFilePath; 
    private String selectedStudentId; 
    private ToggleGroup sexToggleGroup;
    private Map<String, Integer> programMap = new HashMap<>(); // To map program names to IDs
    private ManageStudentsController manageStudentsController; // Reference to ManageStudentsController

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        sexToggleGroup = new ToggleGroup();
        maleRbtn.setToggleGroup(sexToggleGroup);
        femaleRbtn.setToggleGroup(sexToggleGroup);
        loadPrograms(); 
    }

    public void setStudentData(Student student, ManageStudentsController manageStudentsController) {
        this.selectedStudentId = String.valueOf(student.getId());
        this.manageStudentsController = manageStudentsController; // Store the reference
        fnameField.setText(student.getFirstName());
        mnameField.setText(student.getMiddleName());
        lnameField.setText(student.getLastName());
        bdateField.setValue(student.getBirthDate()); 
        addressField.setText(student.getAddress());
        yearField.setText(String.valueOf(student.getYear())); 
        prevSchool.setText(student.getPreviousSchool());
        selectProgram.setValue(student.getProgram()); 

        if (student.getGender().equalsIgnoreCase("Male")) {
            maleRbtn.setSelected(true);
        } else {
            femaleRbtn.setSelected(true);
        }

        if (student.getProfileImagePath() != null) {
            File imageFile = new File(student.getProfileImagePath());
            if (imageFile.exists()) {
                profileImage.setImage(new Image(imageFile.toURI().toString()));
            } else {
                setDefaultProfileImage();
            }
        } else {
            setDefaultProfileImage();
        }
    }

    private void setDefaultProfileImage() {
        String defaultImagePath = "src/prospectus/images/students/default-user.png";
        File defaultImageFile = new File(defaultImagePath);
        if (defaultImageFile.exists()) {
            profileImage.setImage(new Image(defaultImageFile.toURI().toString()));
        } else {
            utilities.showAlert(Alert.AlertType.WARNING, "Image Error", "Default profile image not found.");
        }
    }

    private void loadPrograms() {
    String query = "SELECT p_id, p_department FROM program ORDER BY p_department";

    try (ResultSet resultSet = db.getData(query)) {
        ObservableList<String> programs = FXCollections.observableArrayList();

        while (resultSet.next()) {
            String programName = resultSet.getString("p_department");
            programs.add(programName);
            programMap.put(programName, resultSet.getInt("p_id")); // Store program ID in map
        }

        selectProgram.setItems(programs);
    } catch (SQLException e) {
        e.printStackTrace();
        utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Error loading programs: " + e.getMessage());
    }
}


    @FXML
    private void selectProfileHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(bgPane.getScene().getWindow());
        if (selectedFile != null) {
            try {
                String destinationFolder = "src/prospectus/images/students/";
                File destDir = new File(destinationFolder);
                if (!destDir.exists()) {
                    destDir.mkdirs(); 
                }

                File destinationFile = new File(destDir, selectedFile.getName());

                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                photoFilePath = destinationFile.getAbsolutePath(); 
                profileImage.setImage(new Image(destinationFile.toURI().toString())); 
            } catch (IOException e) {
                utilities.showAlert(Alert.AlertType.ERROR, "Error", "Could not save the image. Please try again.");
            }
        }
    }

    @FXML
    private void removeProfileHandler(MouseEvent event) {
        profileImage.setImage(null); 
        photoFilePath = null; 
    }

    @FXML
    private void submitEditHandler(MouseEvent event) {
        String firstName = fnameField.getText().trim();
        String middleName = mnameField.getText().trim();
        String lastName = lnameField.getText().trim();
        String birthDate = null;
        LocalDate selectedDate = bdateField.getValue();

        if (selectedDate != null) {
            birthDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        String address = addressField.getText().trim();
        String yearLevel = yearField.getText().trim();
        String program = selectProgram.getValue();
        String previousSchool = prevSchool.getText().trim();
        String gender = maleRbtn.isSelected() ? "Male" : "Female";

        if (firstName.isEmpty() || lastName.isEmpty() || program == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all required fields and select a profile image.");
            return;
        }

        if (yearLevel.isEmpty() || !yearLevel.matches("\\d+")) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid year level (numeric).");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearLevel);
            if (year < 1 || year > 4) {
                utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Year level must be between 1 and 4.");
                return;
            }
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Year level must be a valid number.");
            return;
        }

        if (selectedDate != null && selectedDate.isAfter(LocalDate.now())) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "Birth date cannot be in the future.");
            return;
        }

        if (photoFilePath == null || photoFilePath.isEmpty()) {
            photoFilePath = "src/prospectus/images/students/default-user.png";
        }

        // ✅ Get the program_id from the map
        Integer programId = programMap.get(program);
        if (programId == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Invalid Program", "Selected program is invalid.");
            return;
        }

        try (Connection connection = db.getConnection()) {
            String sql = "UPDATE student SET s_fname = ?, s_mname = ?, s_lname = ?, s_bdate = ?, s_address = ?, s_year = ?, s_program = ?, previous_school = ?, s_sex = ?, s_image = ? WHERE s_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, middleName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, birthDate);
            preparedStatement.setString(5, address);
            preparedStatement.setInt(6, year);
            preparedStatement.setInt(7, programId); // ✅ Use the program ID here
            preparedStatement.setString(8, previousSchool);
            preparedStatement.setString(9, gender);
            preparedStatement.setString(10, photoFilePath);
            preparedStatement.setString(11, selectedStudentId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Student information updated successfully.");
                manageStudentsController.loadStudents(); 
                clearFields();
            } else {
                utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update student information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
        }
    }
    private void clearFields() {
        fnameField.clear();
        mnameField.clear();
        lnameField.clear();
        bdateField.setValue(null);
        addressField.clear();
        yearField.clear();
        prevSchool.clear();
        selectProgram.setValue(null);
        sexToggleGroup.selectToggle(null);
        profileImage.setImage(new Image(new File("src/prospectus/images/students/default-user.png").toURI().toString()));
        photoFilePath = null;
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);

    }


}