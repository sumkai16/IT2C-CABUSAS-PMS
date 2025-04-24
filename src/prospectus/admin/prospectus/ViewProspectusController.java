package prospectus.admin.prospectus;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dbConnector;
import prospectus.models.Prospectus;
import prospectus.models.ProspectusDetails;

public class ViewProspectusController implements Initializable {

    // TableViews and Labels per semester
    @FXML private TableView<ProspectusDetails> tableView1st1st;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID1st1st;
    @FXML private TableColumn<ProspectusDetails, String> courseCode1st1st;
    @FXML private TableColumn<ProspectusDetails, Integer> units1st1st;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite1st1st;
    @FXML private Label units1st1stSem;

    @FXML private TableView<ProspectusDetails> tableView1st2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID1st2nd;
    @FXML private TableColumn<ProspectusDetails, String> courseCode1st2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> units1st2nd;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite1st2nd;
    @FXML private Label units1st2ndSem;

    @FXML private TableView<ProspectusDetails> tableView2nd1st;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID2nd1st;
    @FXML private TableColumn<ProspectusDetails, String> courseCode2nd1st;
    @FXML private TableColumn<ProspectusDetails, Integer> units2nd1st;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite2nd1st;
    @FXML private Label units2nd1stSem;

    @FXML private TableView<ProspectusDetails> tableView2nd2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID2nd2nd;
    @FXML private TableColumn<ProspectusDetails, String> courseCode2nd2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> units2nd2nd;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite2nd2nd;
    @FXML private Label units2nd2ndSem;

    @FXML private TableView<ProspectusDetails> tableView3rd1st;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID3rd1st;
    @FXML private TableColumn<ProspectusDetails, String> courseCode3rd1st;
    @FXML private TableColumn<ProspectusDetails, Integer> units3rd1st;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite3rd1st;
    @FXML private Label units3rd1stSem;

    @FXML private TableView<ProspectusDetails> tableView3rd2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID3rd2nd;
    @FXML private TableColumn<ProspectusDetails, String> courseCode3rd2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> units3rd2nd;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite3rd2nd;
    @FXML private Label units3rd2ndSem;

    @FXML private TableView<ProspectusDetails> tableView4th1st;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID4th1st;
    @FXML private TableColumn<ProspectusDetails, String> courseCode4th1st;
    @FXML private TableColumn<ProspectusDetails, Integer> units4th1st;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite4th1st;
    @FXML private Label units4th1stSem;

    @FXML private TableView<ProspectusDetails> tableView4th2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> courseID4th2nd;
    @FXML private TableColumn<ProspectusDetails, String> courseCode4th2nd;
    @FXML private TableColumn<ProspectusDetails, Integer> units4th2nd;
    @FXML private TableColumn<ProspectusDetails, String> prerequisite4th2nd;
    @FXML private Label units4th2ndSem;

    @FXML private Label program1stYear;
    @FXML private Label effectiveYear1stYear;
    @FXML private Label program2nd;
    @FXML private Label effectiveYear2nd;
    @FXML private Label program3rdYear;
    @FXML private Label effectiveYear3rdYear;
    @FXML private Label program4thYear;
    @FXML private Label effectiveYear4thYear;

    private final ProspectusData prospectusData = new ProspectusData();
    private Prospectus prospectus;
    private dbConnector db = new dbConnector();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing ViewProspectusController...");
        setupTableColumns();
        System.out.println("Table columns initialized");
        loadProspectus();
        System.out.println("Prospectus loaded");
    }

    private void setupTableColumns() {
        // First Year, First Semester
        courseID1st1st.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode1st1st.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units1st1st.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite1st1st.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // First Year, Second Semester
        courseID1st2nd.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode1st2nd.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units1st2nd.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite1st2nd.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Second Year, First Semester
        courseID2nd1st.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode2nd1st.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units2nd1st.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite2nd1st.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Second Year, Second Semester
        courseID2nd2nd.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode2nd2nd.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units2nd2nd.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite2nd2nd.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Third Year, First Semester
        courseID3rd1st.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode3rd1st.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units3rd1st.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite3rd1st.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Third Year, Second Semester
        courseID3rd2nd.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode3rd2nd.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units3rd2nd.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite3rd2nd.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Fourth Year, First Semester
        courseID4th1st.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode4th1st.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units4th1st.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite4th1st.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Fourth Year, Second Semester
        courseID4th2nd.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCode4th2nd.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        units4th2nd.setCellValueFactory(new PropertyValueFactory<>("units"));
        prerequisite4th2nd.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));
    }

    private <T> void initializeColumn(TableColumn<ProspectusDetails, T> column, String property) {
        column.setCellValueFactory(new PropertyValueFactory<>(property));
    }

    private void loadProspectus() {
        List<Prospectus> prospectusList = prospectusData.getProspectus();
        System.out.println("Prospectus list size: " + prospectusList.size());
        if (!prospectusList.isEmpty()) {
            setProspectus(prospectusList.get(0));
        } else {
            System.out.println("No prospectus data found.");
        }
    }

    public void setProspectus(Prospectus prospectus) {
        this.prospectus = prospectus;
        String programText = prospectus.getPDepartment();
        String yearText = prospectus.getPrEffectiveYear();

        // Set program and effective year for all tabs
        program1stYear.setText(programText);
        effectiveYear1stYear.setText(yearText);
        program2nd.setText(programText);
        effectiveYear2nd.setText(yearText);
        program3rdYear.setText(programText);
        effectiveYear3rdYear.setText(yearText);
        program4thYear.setText(programText);
        effectiveYear4thYear.setText(yearText);

        loadAllProspectusDetails(prospectus.getPrId()); // Load all details
    }

       private void loadAllProspectusDetails(int prId) {
        // Get all prospectus details for the given effective year
        String query = "SELECT pd.*, c.c_code, c.c_desc, c.c_units, " +
                      "prereq.c_code as prereq_code " +
                      "FROM prospectus p " +
                      "JOIN prospectus_details pd ON p.pr_id = pd.pr_id " +
                      "JOIN course c ON pd.course_id = c.c_id " +
                      "LEFT JOIN course prereq ON c.prerequisite_id = prereq.c_id " +
                      "WHERE p.pr_effective_year = (SELECT pr_effective_year FROM prospectus WHERE pr_id = ?) " +
                      "ORDER BY pd.year_level, pd.semester, c.c_code";

        List<ProspectusDetails> details = new ArrayList<>();
        
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, prId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ProspectusDetails detail = new ProspectusDetails(
                    rs.getInt("pd_id"),
                    rs.getInt("pr_id"),
                    rs.getInt("course_id"),
                    rs.getString("year_level"),
                    rs.getString("semester")
                );
                
                // Set additional fields
                detail.setCourseCode(rs.getString("c_code"));
                detail.setCourseDesc(rs.getString("c_desc"));
                detail.setUnits(rs.getInt("c_units"));
                detail.setPrerequisite(rs.getString("prereq_code") != null ? rs.getString("prereq_code") : "None");
                
                details.add(detail);
            }
            
            // Load all year levels at once
            loadFirstYearTables(details);
            loadSecondYearTables(details);
            loadThirdYearTables(details);
            loadFourthYearTables(details);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadFirstYearTables(List<ProspectusDetails> details) {
        setSemesterTable(tableView1st1st, units1st1stSem, details, "1st Year", "1st Semester");
        setSemesterTable(tableView1st2nd, units1st2ndSem, details, "1st Year", "2nd Semester");
    }

    private void loadSecondYearTables(List<ProspectusDetails> details) {
        setSemesterTable(tableView2nd1st, units2nd1stSem, details, "2nd Year", "1st Semester");
        setSemesterTable(tableView2nd2nd, units2nd2ndSem, details, "2nd Year", "2nd Semester");
    }

    private void loadThirdYearTables(List<ProspectusDetails> details) {
        setSemesterTable(tableView3rd1st, units3rd1stSem, details, "3rd Year", "1st Semester");
        setSemesterTable(tableView3rd2nd, units3rd2ndSem, details, "3rd Year", "2nd Semester");
    }

    private void loadFourthYearTables(List<ProspectusDetails> details) {
        setSemesterTable(tableView4th1st, units4th1stSem, details, "4th Year", "1st Semester");
        setSemesterTable(tableView4th2nd, units4th2ndSem, details, "4th Year", "2nd Semester");
    }

    private void setSemesterTable(TableView<ProspectusDetails> table, Label label, 
                                List<ProspectusDetails> allDetails, String year, String sem) {
        System.out.println("Setting table for " + year + " " + sem);

        List<ProspectusDetails> filtered = allDetails.stream()
                .filter(d -> {
                    boolean matches = d.getYear_level().trim().equalsIgnoreCase(year) && 
                                    d.getSemester().trim().equalsIgnoreCase(sem);
                    if (matches) {
                        System.out.println("Found match: " + d.getCourseCode() + " - " + d.getCourseDesc());
                    }
                    return matches;
                })
                .collect(Collectors.toList());

        System.out.println("Filtered " + year + " " + sem + ": " + filtered.size() + " courses");

        // Clear the table first
        table.getItems().clear();

        // Add the filtered items
        ObservableList<ProspectusDetails> observableList = FXCollections.observableArrayList(filtered);
        table.setItems(observableList);

        // Force the table to refresh
        table.refresh();

        // Calculate total units
        int totalUnits = filtered.stream()
                .mapToInt(ProspectusDetails::getUnits)
                .sum();
        label.setText(String.valueOf(totalUnits));
    }
    
   
}