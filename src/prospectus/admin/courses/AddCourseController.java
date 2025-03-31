package prospectus.admin.courses;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dbConnector;
import prospectus.utilities.utilities;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

public class AddCourseController implements Initializable {

    @FXML
    private AnchorPane overlayPane;
    @FXML
    private Pane backgroundPane;
    @FXML
    private TextField courseCodeField;
    @FXML
    private TextField courseDescField;
    @FXML
    private TextField courseUnitsField;
    @FXML
    private ComboBox<String> prerequisiteComboBox;
    @FXML
    private ComboBox<ProgramEntry> programComboBox;
    @FXML
    private Button addCourseButton;

    private dbConnector db;
    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new dbConnector();
        conn = db.getConnection();
        loadPrerequisites();
        loadPrograms();
    }

    private void loadPrerequisites() {
        ObservableList<String> prerequisiteList = FXCollections.observableArrayList();
        String query = "SELECT c_code FROM course";

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                prerequisiteList.add(rs.getString("c_code"));
            }
            prerequisiteComboBox.setItems(prerequisiteList);
        } catch (SQLException e) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load prerequisites: " + e.getMessage());
        }
    }

    private void loadPrograms() {
        ObservableList<ProgramEntry> programList = FXCollections.observableArrayList();
        String query = "SELECT p_id, p_department FROM program";

        try {
            if (db == null || db.getConnection() == null) {
                System.out.println("Database connection is null!");
                return;
            }

            Connection conn = db.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("p_id");
                String department = rs.getString("p_department");
                programList.add(new ProgramEntry(id, department));
            }

            programComboBox.setItems(programList);

            programComboBox.setCellFactory(lv -> new ListCell<ProgramEntry>() {
                @Override
                protected void updateItem(ProgramEntry item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getDepartment());
                    }
                }
            });

            programComboBox.setConverter(new StringConverter<ProgramEntry>() {
                @Override
                public String toString(ProgramEntry programEntry) {
                    if (programEntry == null) {
                        return null;
                    } else {
                        return programEntry.getDepartment();
                    }
                }

                @Override
                public ProgramEntry fromString(String string) {
                    return null;
                }
            });

            System.out.println("Programs loaded: " + programList.size());
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load programs: " + e.getMessage());
        }
    }

    @FXML
    private void addCourseHandler(ActionEvent event) {
        String courseCode = courseCodeField.getText().trim();
        String courseDesc = courseDescField.getText().trim();
        String courseUnitsText = courseUnitsField.getText().trim();
        String prerequisite = prerequisiteComboBox.getSelectionModel().getSelectedItem();
        ProgramEntry selectedProgram = programComboBox.getSelectionModel().getSelectedItem();

        if (courseCode.isEmpty() || courseDesc.isEmpty() || courseUnitsText.isEmpty() || selectedProgram == null) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Please fill in all required fields.");
            return;
        }

        int courseUnits;
        try {
            courseUnits = Integer.parseInt(courseUnitsText);
            if (courseUnits <= 0) {
                utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Course units must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            utilities.showAlert(Alert.AlertType.WARNING, "Input Error", "Invalid number format for course units.");
            return;
        }

        String query = "INSERT INTO course (c_code, c_desc, c_units, prerequisite_id, program_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, courseCode);
            pst.setString(2, courseDesc);
            pst.setInt(3, courseUnits);

            if (prerequisite == null || prerequisite.trim().isEmpty()) {
                pst.setNull(4, java.sql.Types.VARCHAR);
            } else {
                pst.setString(4, prerequisite);
            }

            pst.setInt(5, selectedProgram.getId());

            int result = pst.executeUpdate();
            if (result > 0) {
                utilities.showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add course: " + e.getMessage());
        }
    }

    @FXML
    private void returnHandler(MouseEvent event) {
        utilities.closeOverlay(overlayPane);
    }

    private void clearFields() {
        courseCodeField.clear();
        courseDescField.clear();
        courseUnitsField.clear();
        prerequisiteComboBox.getSelectionModel().clearSelection();
        programComboBox.getSelectionModel().clearSelection();
    }

    public class ProgramEntry {
        private int id;
        private String department;

        public ProgramEntry(int id, String department) {
            this.id = id;
            this.department = department;
        }

        public int getId() {
            return id;
        }

        public String getDepartment() {
            return department;
        }

        @Override
        public String toString() {
            return department;
        }
    }
}