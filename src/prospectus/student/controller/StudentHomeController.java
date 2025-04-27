package prospectus.student.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.dbConnector;
import prospectus.models.UserSession;
import prospectus.utilities.utilities;

public class StudentHomeController implements Initializable {
    private static final Logger logger = Logger.getLogger(StudentHomeController.class.getName());
    private final dbConnector db = new dbConnector();
    
    @FXML
    private Label studentName;
    @FXML
    private Label programName;
    @FXML
    private Label yearLevel;
    @FXML
    private Label enrollmentStatus;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Subject> currentSubjectsTable;
    @FXML
    private TableColumn<Subject, String> subjectCodeColumn;
    @FXML
    private TableColumn<Subject, String> subjectNameColumn;
    @FXML
    private TableColumn<Subject, Integer> unitsColumn;
    @FXML
    private VBox timelineContainer;
    @FXML
    private VBox announcementsContainer;
    
    private ObservableList<Subject> currentSubjects = FXCollections.observableArrayList();
    @FXML
    private AnchorPane overlayPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadStudentData();
            setupTableColumns();
            loadCurrentSubjects();
            loadAcademicTimeline();
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard data: " + ex.getMessage());
            ex.printStackTrace();
        }
    }    

    private void loadStudentData() throws SQLException {
        
        String query = "SELECT u.u_fname, u.u_lname, p.p_program_name, s.s_year, u.enrollment_status " +
                      "FROM user u " +
                      "JOIN student s ON u.u_id = s.u_id " +
                      "JOIN program p ON s.s_program = p.p_id " +
                      "WHERE u.u_id = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, UserSession.getUserId());
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String firstName = rs.getString("u_fname");
                String lastName = rs.getString("u_lname");
                String program = rs.getString("p_program_name");
                String year = rs.getString("s_year");
                String status = rs.getString("enrollment_status");
                
                studentName.setText(firstName + " " + lastName);
                programName.setText(program);
                yearLevel.setText("Year " + year);
                enrollmentStatus.setText(status);
                
            } 
        }
    }

    private void setupTableColumns() {
        subjectCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        currentSubjectsTable.setItems(currentSubjects);
    }

    private void loadCurrentSubjects() throws SQLException {
        int userId = UserSession.getUserId();
        logger.log(Level.INFO, "Loading current subjects for user ID: {0}", userId);

        String query = "SELECT c.c_code, c.c_desc, c.c_units " +
                       "FROM course c " +
                       "JOIN prospectus_details pd ON pd.course_id = c.c_id " + // Join course with prospectus_details
                       "JOIN enrollment e ON e.prog_id = pd.pr_id " + // Join enrollment with prospectus_details
                       "WHERE e.userID = ? AND pd.semester = e.semester"; // Ensure semester matches

        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, userId); // Set the user ID
            ResultSet rs = pst.executeQuery();

            currentSubjects.clear(); // Clear the existing subjects
            while (rs.next()) {
                currentSubjects.add(new Subject(
                    rs.getString("c_code"),
                    rs.getString("c_desc"),
                    rs.getInt("c_units")
                ));
            }

            logger.log(Level.INFO, "Loaded {0} current subjects", currentSubjects.size());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error loading current subjects: {0}", ex.getMessage());
            throw ex; // Rethrow to handle in the calling method
        }
    }

    private void loadAcademicTimeline() throws SQLException {
        
        String query = "SELECT DISTINCT e.enrollment_date " +
                      "FROM enrollment e " +
                      "WHERE e.userID = ? " +
                      "ORDER BY e.enrollment_date";
        
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, UserSession.getUserId());
            ResultSet rs = pst.executeQuery();
            
            timelineContainer.getChildren().clear();
            while (rs.next()) {
                Label timelineItem = new Label(
                    "Enrolled: " + rs.getTimestamp("enrollment_date").toLocalDateTime().toLocalDate()
                );
                timelineItem.setStyle("-fx-text-fill: #12192C; -fx-font-size: 14px;");
                timelineContainer.getChildren().add(timelineItem);
            }
        }
    }

    @FXML
    private void prospectusViewHandler(MouseEvent event) {
        utilities.loadFXMLWithFade(overlayPane, "/prospectus/admin/prospectus/viewProspectus.fxml");
    }

    @FXML
    private void studentDetailsOnClick(MouseEvent event) {
        utilities.loadFXMLWithFade(overlayPane, "/prospectus/student/fxml/StudentProfile.fxml");
    }
    
    // Inner class for Subject data
    public static class Subject {
        private final String code;
        private final String name;
        private final int units;

        public Subject(String code, String name, int units) {
            this.code = code;
            this.name = name;
            this.units = units;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public int getUnits() { return units; }
    }
}