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
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.print.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

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

    @FXML private TableView<ProspectusDetails> tableviewSummer;
    private TableColumn<ProspectusDetails, String> courseCodeSummer;
    @FXML private TableColumn<ProspectusDetails, String> courseDescSummer;
    @FXML private TableColumn<ProspectusDetails, Integer> unitsSummer;
    @FXML private TableColumn<ProspectusDetails, String> prerequisiteSummer;
    @FXML private Label totalUnitsSummer;

    @FXML private TableView<ProspectusDetails> tableViewAllCourses;
    @FXML private TableColumn<ProspectusDetails, String> courseCodeAllCourses;
    @FXML private TableColumn<ProspectusDetails, String> courseDescAllCourses;
    @FXML private TableColumn<ProspectusDetails, Integer> unitsAllCourses;
    @FXML private TableColumn<ProspectusDetails, String> prerequisiteAllCourses;
    private Label totalUnitsAllCourses;

    @FXML private Label program1stYear;
    @FXML private Label effectiveYear1stYear;
    private Label program2nd;
    private Label effectiveYear2nd;
    private Label program3rdYear;
    private Label effectiveYear3rdYear;
    private Label program4thYear;
    private Label effectiveYear4thYear;

    @FXML private TabPane tabPane;

    private final ProspectusData prospectusData = new ProspectusData();
    private Prospectus prospectus;
    private dbConnector db = new dbConnector();
    @FXML
    private TableColumn<?, ?> courseCodeSUmmer;
   
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing ViewProspectusController...");
        setupTableColumns();
        System.out.println("Table columns initialized");
        loadProspectus();
        System.out.println("Prospectus loaded");
        logger.addLog(UserSession.getUsername(), "Prospectus", "Viewed Prospectus.: " + UserSession.getUsername());
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

        // Setup Summer columns
        if (courseCodeSummer != null) courseCodeSummer.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        if (courseDescSummer != null) courseDescSummer.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        if (unitsSummer != null) unitsSummer.setCellValueFactory(new PropertyValueFactory<>("units"));
        if (prerequisiteSummer != null) prerequisiteSummer.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));

        // Setup All Courses columns
        if (courseCodeAllCourses != null) courseCodeAllCourses.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        if (courseDescAllCourses != null) courseDescAllCourses.setCellValueFactory(new PropertyValueFactory<>("courseDesc"));
        if (unitsAllCourses != null) unitsAllCourses.setCellValueFactory(new PropertyValueFactory<>("units"));
        if (prerequisiteAllCourses != null) prerequisiteAllCourses.setCellValueFactory(new PropertyValueFactory<>("prerequisite"));
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
            // Set default values if no data is found
            setEmptyProspectus();
        }
    }

    private void setEmptyProspectus() {
        String defaultProgram = "No Program Selected";
        String defaultYear = "----";
        
        program1stYear.setText(defaultProgram);
        effectiveYear1stYear.setText(defaultYear);
        program2nd.setText(defaultProgram);
        effectiveYear2nd.setText(defaultYear);
        program3rdYear.setText(defaultProgram);
        effectiveYear3rdYear.setText(defaultYear);
        program4thYear.setText(defaultProgram);
        effectiveYear4thYear.setText(defaultYear);
    }

    public void setProspectus(Prospectus prospectus) {
        if (prospectus == null) {
            setEmptyProspectus();
            return;
        }

        this.prospectus = prospectus;
        String programText = prospectus.getPDepartment() != null ? prospectus.getPDepartment() : "No Program";
        String yearText = prospectus.getPrEffectiveYear() != null ? prospectus.getPrEffectiveYear() : "----";

        // Set program and effective year for all tabs
        if (program1stYear != null) program1stYear.setText(programText);
        if (effectiveYear1stYear != null) effectiveYear1stYear.setText(yearText);
        if (program2nd != null) program2nd.setText(programText);
        if (effectiveYear2nd != null) effectiveYear2nd.setText(yearText);
        if (program3rdYear != null) program3rdYear.setText(programText);
        if (effectiveYear3rdYear != null) effectiveYear3rdYear.setText(yearText);
        if (program4thYear != null) program4thYear.setText(programText);
        if (effectiveYear4thYear != null) effectiveYear4thYear.setText(yearText);

        loadAllProspectusDetails(prospectus.getPrId()); // Load all details
    }

private void loadAllProspectusDetails(int prId) {
        List<ProspectusDetails> details = prospectusData.getProspectusDetails(prId);

        // Normalize year_level and semester values to match expected filter strings
        for (ProspectusDetails detail : details) {
            String normalizedYearLevel = detail.getYear_level().trim().toLowerCase();
            String normalizedSemester = detail.getSemester().trim().toLowerCase();

            if (normalizedYearLevel.equals("1st year") || normalizedYearLevel.equals("first year")) {
                detail.setYear_level("1st Year");
            } else if (normalizedYearLevel.equals("2nd year") || normalizedYearLevel.equals("second year")) {
                detail.setYear_level("2nd Year");
            } else if (normalizedYearLevel.equals("3rd year") || normalizedYearLevel.equals("third year")) {
                detail.setYear_level("3rd Year");
            } else if (normalizedYearLevel.equals("4th year") || normalizedYearLevel.equals("fourth year")) {
                detail.setYear_level("4th Year");
            } else if (normalizedYearLevel.equals("summer")) {
                detail.setYear_level("Summer");
            }

            if (normalizedSemester.equals("1st semester") || normalizedSemester.equals("first semester")) {
                detail.setSemester("1st Semester");
            } else if (normalizedSemester.equals("2nd semester") || normalizedSemester.equals("second semester")) {
                detail.setSemester("2nd Semester");
            } else if (normalizedSemester.equals("summer")) {
                detail.setSemester("Summer");
            }
        }

        loadFirstYearTables(details);
        loadSecondYearTables(details);
        loadThirdYearTables(details);
        loadFourthYearTables(details);
        loadSummerTable(details);
        loadAllCoursesTable(details);
    }

private void loadFirstYearTables(List<ProspectusDetails> details) {
        System.out.println("Loading First Year Tables");
        setSemesterTable(tableView1st1st, units1st1stSem, details, "1st Year", "1st Semester");
        setSemesterTable(tableView1st2nd, units1st2ndSem, details, "1st Year", "2nd Semester");
    }

    private void loadSecondYearTables(List<ProspectusDetails> details) {
        System.out.println("Loading Second Year Tables");
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

    private void loadSummerTable(List<ProspectusDetails> details) {
        // Filter for summer courses
        List<ProspectusDetails> summerCourses = details.stream()
                .filter(d -> "Summer".equals(d.getYear_level()) || "Summer".equals(d.getSemester()))
                .collect(Collectors.toList());

        if (tableviewSummer != null) {
            tableviewSummer.getItems().clear();
            ObservableList<ProspectusDetails> observableList = FXCollections.observableArrayList(summerCourses);
            tableviewSummer.setItems(observableList);
            
            // Calculate total units for summer
            int totalUnits = summerCourses.stream()
                    .mapToInt(ProspectusDetails::getUnits)
                    .sum();
            if (totalUnitsSummer != null) {
                totalUnitsSummer.setText(String.valueOf(totalUnits));
            }
        }
    }

private void setSemesterTable(TableView<ProspectusDetails> table, Label label, 
                                List<ProspectusDetails> allDetails, String year, String sem) {
        System.out.println("Setting table for " + year + " " + sem);

        // Debug print all year_level and semester values
        System.out.println("All courses year_level and semester values:");
        for (ProspectusDetails d : allDetails) {
            System.out.println("Year Level: '" + d.getYear_level() + "', Semester: '" + d.getSemester() + "'");
        }

        List<ProspectusDetails> filtered = allDetails.stream()
                .filter(d -> {
                    String normalizedYear = d.getYear_level() != null ? d.getYear_level().trim().toLowerCase() : "";
                    String normalizedSem = d.getSemester() != null ? d.getSemester().trim().toLowerCase() : "";
                    boolean matches = normalizedYear.equals(year.toLowerCase()) && normalizedSem.equals(sem.toLowerCase());
                    if (matches) {
                        System.out.println("Found match: " + d.getCourseCode() + " - " + d.getCourseDesc() + " with normalized year: '" + normalizedYear + "' and semester: '" + normalizedSem + "'");
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

    private void loadAllCoursesTable(List<ProspectusDetails> details) {
        if (tableViewAllCourses != null) {
            tableViewAllCourses.getItems().clear();
            ObservableList<ProspectusDetails> observableList = FXCollections.observableArrayList(details);
            tableViewAllCourses.setItems(observableList);

            // Calculate total units for all courses
            int totalUnits = details.stream()
                    .mapToInt(ProspectusDetails::getUnits)
                    .sum();
            if (totalUnitsAllCourses != null) {
                totalUnitsAllCourses.setText(String.valueOf(totalUnits));
            }
        }
    }
    
    @FXML
    private void handlePrint(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Show printer dialog
            boolean proceed = job.showPrintDialog(tabPane.getScene().getWindow());
            
            if (proceed) {
                // Get current tab content
                Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
                ScrollPane scrollPane = (ScrollPane) selectedTab.getContent();
                Node content = scrollPane.getContent();

                // Calculate the scale to fit content to page
                PageLayout pageLayout = job.getJobSettings().getPageLayout();
                double scaleX = pageLayout.getPrintableWidth() / content.getBoundsInParent().getWidth();
                double scaleY = pageLayout.getPrintableHeight() / content.getBoundsInParent().getHeight();
                double scale = Math.min(scaleX, scaleY);

                // Apply scaling
                Scale scaling = new Scale(scale, scale);
                content.getTransforms().add(scaling);

                // Print the content
                boolean success = job.printPage(content);
                
                // Remove scaling after printing
                content.getTransforms().remove(scaling);

                if (success) {
                    job.endJob();
                    showAlert("Print Success", "The prospectus has been sent to the printer.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Print Failed", "Failed to print the prospectus.", Alert.AlertType.ERROR);
                }
            }
        } else {
            showAlert("Printer Error", "No printer found or printer system not available.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}