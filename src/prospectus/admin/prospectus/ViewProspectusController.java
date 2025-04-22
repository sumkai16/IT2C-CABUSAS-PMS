package prospectus.admin.prospectus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadProspectus();
    }

    private void setupTableColumns() {
        // First Year, First Semester
        initializeColumn(courseID1st1st, "courseCode");
        initializeColumn(courseCode1st1st, "courseDesc");
        initializeColumn(units1st1st, "units");
        initializeColumn(prerequisite1st1st, "prerequisite");

        // First Year, Second Semester
        initializeColumn(courseID1st2nd, "courseCode");
        initializeColumn(courseCode1st2nd, "courseDesc");
        initializeColumn(units1st2nd, "units");
        initializeColumn(prerequisite1st2nd, "prerequisite");

        // Second Year, First Semester
        initializeColumn(courseID2nd1st, "courseCode");
        initializeColumn(courseCode2nd1st, "courseDesc");
        initializeColumn(units2nd1st, "units");
        initializeColumn(prerequisite2nd1st, "prerequisite");

        // Second Year, Second Semester
        initializeColumn(courseID2nd2nd, "courseCode");
        initializeColumn(courseCode2nd2nd, "courseDesc");
        initializeColumn(units2nd2nd, "units");
        initializeColumn(prerequisite2nd2nd, "prerequisite");

        // Third Year, First Semester
        initializeColumn(courseID3rd1st, "courseCode");
        initializeColumn(courseCode3rd1st, "courseDesc");
        initializeColumn(units3rd1st, "units");
        initializeColumn(prerequisite3rd1st, "prerequisite");

        // Third Year, Second Semester
        initializeColumn(courseID3rd2nd, "courseCode");
        initializeColumn(courseCode3rd2nd, "courseDesc");
        initializeColumn(units3rd2nd, "units");
        initializeColumn(prerequisite3rd2nd, "prerequisite");

        // Fourth Year, First Semester
        initializeColumn(courseID4th1st, "courseCode");
        initializeColumn(courseCode4th1st, "courseDesc");
        initializeColumn(units4th1st, "units");
        initializeColumn(prerequisite4th1st, "prerequisite");

        // Fourth Year, Second Semester
        initializeColumn(courseID4th2nd, "courseCode");
        initializeColumn(courseCode4th2nd, "courseDesc");
        initializeColumn(units4th2nd, "units");
        initializeColumn(prerequisite4th2nd, "prerequisite");
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
        
        loadProspectusDetails(prospectus.getPrId());
    }

    private void loadProspectusDetails(int prId) {
        List<ProspectusDetails> details = prospectusData.getProspectusDetails(prId);
        System.out.println("Loaded details for prospectus ID " + prId + ": " + details.size() + " records found.");
        loadFirstYearTables(details);
        loadSecondYearTables(details);
        loadThirdYearTables(details);
        loadFourthYearTables(details);
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

    private void setSemesterTable(TableView<ProspectusDetails> table, Label label, List<ProspectusDetails> allDetails, String year, String sem) {
        List<ProspectusDetails> filtered = allDetails.stream()
                .filter(d -> {
                    String detailYear = d.getYear_level().trim();
                    String detailSem = d.getSemester().trim();
                    
                    // Debug print
                    System.out.println("Comparing - Detail: [" + detailYear + ", " + detailSem + 
                                     "] with Expected: [" + year + ", " + sem + "]");
                    
                    return detailYear.equalsIgnoreCase(year) && detailSem.equalsIgnoreCase(sem);
                })
                .collect(Collectors.toList());

        System.out.println("Filtered " + year + " " + sem + ": " + filtered.size() + " courses");
        filtered.forEach(d -> System.out.println("Filtered course: " + d.getCourseCode() + " - " + d.getCourseDesc()));

        table.setItems(FXCollections.observableArrayList(filtered));

        int totalUnits = filtered.stream().mapToInt(ProspectusDetails::getUnits).sum();
        label.setText(totalUnits + "");
    }
   
}