package prospectus.user.controller;

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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;
import prospectus.utilities.validations;

import static prospectus.utilities.validations.isStudentEnrolledById;

public class EnrollmentFormController implements Initializable {

    @FXML private TextField fnameField;
    @FXML private TextField mnameField;
    @FXML private TextField lnameField;
    @FXML private DatePicker bdateField;
    @FXML private TextField addressField;
    @FXML private TextField yearField;
    @FXML private Button submitButton;
    @FXML private Pane bgPane;
    @FXML private AnchorPane overlayPane;
    @FXML private RadioButton maleRbtn;
    @FXML private RadioButton femaleRbtn;
    @FXML private ComboBox<String> selectProgram;
    @FXML private TextField prevSchool;
    @FXML private ImageView profileImage;
    @FXML private TextField userIDField;
    private ToggleGroup sexToggleGroup;
    private String photoFilePath;
    private static final dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPrograms();
        sexToggleGroup = new ToggleGroup();
        maleRbtn.setToggleGroup(sexToggleGroup);
        femaleRbtn.setToggleGroup(sexToggleGroup);
    }

    private void loadPrograms() {
        String query = "SELECT p_id, p_program_name, p_department FROM program WHERE p_status = 'active'";
        ObservableList<String> programList = FXCollections.observableArrayList();

        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String programName = rs.getString("p_program_name");
                String department = rs.getString("p_department");
                // Format: "Program Name (Department)"
                programList.add(programName + " (" + department + ")");
            }
            selectProgram.setItems(programList);
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load programs: " + ex.getMessage());
        }
    }

    @FXML
    private void submitEnrollmentHandler(MouseEvent event) {
        if (!isInputValid()) return;

        String userIdText = userIDField.getText();
        if (userIdText.isEmpty()) {
            utilities.showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a User ID to enroll the student.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdText);
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Input Error", "User ID must be a valid number.");
            return;
        }

        if (!userExists(userId)) {
            utilities.showAlert(Alert.AlertType.ERROR, "User Not Found", "The provided User ID does not exist.");
            return;
        }
        if (validations.isStudentEnrolledById(userId)) {
            utilities.showAlert(Alert.AlertType.WARNING, "Already Enrolled", "This user is already enrolled.");
            return;
        }


        String fname = fnameField.getText();
        String mname = mnameField.getText();
        String lname = lnameField.getText();
        LocalDate birthdate = bdateField.getValue();
        String address = addressField.getText();
        int year = Integer.parseInt(yearField.getText());
        String previousSchool = prevSchool.getText();
        String selectedProgram = selectProgram.getValue();

        if (selectedProgram == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select a valid program.");
            return;
        }

        int programId = getProgramId(selectedProgram);
        if (programId == -1) {
            utilities.showAlert(Alert.AlertType.ERROR, "Selection Error", "Invalid program selected.");
            return;
        }

        String sex = getSelectedGender();
        if (sex == null) {
            utilities.showAlert(Alert.AlertType.ERROR, "Missing Gender!", "Please select a gender (Male/Femail).");
            return;
        }

        if (photoFilePath == null || photoFilePath.isEmpty()) {
            photoFilePath = "src/prospectus/images/student/default-user.png";
        }

        enrollStudent(userId, fname, mname, lname, birthdate, address, sex, year, programId, previousSchool);
    }

    private boolean userExists(int userId) {
        String query = "SELECT u_id FROM user WHERE u_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId);
            return pst.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isInputValid() {
        return !(fnameField.getText().isEmpty() || lnameField.getText().isEmpty() ||
                 bdateField.getValue() == null || addressField.getText().isEmpty() ||
                 yearField.getText().isEmpty() || prevSchool.getText().isEmpty() ||
                 selectProgram.getValue() == null);
    }

    private String getSelectedGender() {
        RadioButton selectedRadioButton = (RadioButton) sexToggleGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : null;
    }

    private int getProgramId(String programDisplay) {
        // Extract program name from the display format "Program Name (Department)"
        String programName = programDisplay.split("\\s*\\(")[0].trim();
        
        String query = "SELECT p_id FROM program WHERE p_program_name = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, programName);
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt("p_id") : -1;
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve program ID: " + ex.getMessage());
            return -1;
        }
    }

    private void enrollStudent(int userId, String fname, String mname, String lname, LocalDate birthdate, String address, String sex, int year, int programId, String previousSchool) {
        String enrollQuery = "INSERT INTO enrollment (userID, prog_id, enrollment_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateUserQuery = "UPDATE user SET u_role = ?, enrollment_status = ? WHERE u_id = ?";
        String studentQuery = "INSERT INTO student (u_id, s_fname, s_mname, s_lname, s_bdate, s_address, s_sex, s_year, s_program, previous_school, s_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            String currentRole = "Student";
            try (PreparedStatement pstRole = conn.prepareStatement("SELECT u_role FROM user WHERE u_id = ?")) {
                pstRole.setInt(1, userId);
                ResultSet rsRole = pstRole.executeQuery();
                if (rsRole.next() && rsRole.getString("u_role").equalsIgnoreCase("Admin")) {
                    currentRole = "Admin";
                }
            }

            try (PreparedStatement pstEnroll = conn.prepareStatement(enrollQuery);
                 PreparedStatement pstUpdate = conn.prepareStatement(updateUserQuery);
                 PreparedStatement pstInsertStudent = conn.prepareStatement(studentQuery)) {

                pstEnroll.setInt(1, userId);
                pstEnroll.setInt(2, programId);
                pstEnroll.executeUpdate();

                pstUpdate.setString(1, currentRole);
                pstUpdate.setString(2, "Enrolled");
                pstUpdate.setInt(3, userId);
                pstUpdate.executeUpdate();

                pstInsertStudent.setInt(1, userId);
                pstInsertStudent.setString(2, fname);
                pstInsertStudent.setString(3, mname);
                pstInsertStudent.setString(4, lname);
                pstInsertStudent.setDate(5, java.sql.Date.valueOf(birthdate));
                pstInsertStudent.setString(6, address);
                pstInsertStudent.setString(7, sex);
                pstInsertStudent.setInt(8, year);
                pstInsertStudent.setInt(9, programId);
                pstInsertStudent.setString(10, previousSchool);
                pstInsertStudent.setString(11, photoFilePath);
                pstInsertStudent.executeUpdate();

                conn.commit();
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Enrollment Successful! You are now Enrolled.");
                clearFields();
            } catch (SQLException ex) {
                conn.rollback();
                utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Enrollment failed: " + ex.getMessage());
            }
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Error", "Could not connect to the database: " + e.getMessage());
        }
    }

    @FXML
    private void selectProfileHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (selectedFile != null) saveProfileImage(selectedFile);
    }

    private void saveProfileImage(File selectedFile) {
        try {
            String destinationFolder = "src/prospectus/images/student/";
            File destDir = new File(destinationFolder);
            if (!destDir.exists()) destDir.mkdirs();

            File destinationFile = new File(destDir, selectedFile.getName());
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            profileImage.setImage(new Image(destinationFile.toURI().toString()));
            photoFilePath = destinationFolder + destinationFile.getName();
        } catch (IOException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to save profile photo.");
        }
    }

    @FXML
    private void removeProfileHandler(MouseEvent event) {
        profileImage.setImage(null);
        photoFilePath = null;
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        clearFields();
        utilities.closeOverlay(overlayPane);
    }

    private void clearFields() {
        fnameField.clear();
        mnameField.clear();
        lnameField.clear();
        bdateField.setValue(null);
        addressField.clear();
        yearField.clear();
        prevSchool.clear();
        selectProgram.getSelectionModel().clearSelection();
        userIDField.clear();
        profileImage.setImage(null);
        photoFilePath = null;
    }
}