package prospectus.admin.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import prospectus.models.UserSession;
import prospectus.utilities.logger;
import prospectus.utilities.utilities;

public class AdminDashboardController implements Initializable {

    @FXML
    private Button home, prospectus, managePrograms, settings, logout, manageStudent;
    @FXML
    private BorderPane bgPane;
    @FXML
    private Button userManageOnClick;
    @FXML
    private Button courses;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

   

    /**
     * Loads an FXML page with fade-in effect.
     */
    private void loadPage(String fxmlPath) {
        utilities.loadPageWithFade(bgPane, fxmlPath, null);
    }

    @FXML
    private void homeOnClick(MouseEvent event) {
        loadPage("/prospectus/admin/fxml/HomeDashboard.fxml");
    }

    @FXML
    private void userManageOnClick(MouseEvent event) {
        loadPage("/prospectus/admin/manageUsers/manageUsers.fxml");
    }

    @FXML
    private void prospectusOnClick(MouseEvent event) {
        loadPage("/prospectus/admin/prospectus/manageProspectus.fxml");
    }

    @FXML
    private void settingsOnClick(MouseEvent event) {
        loadPage("/prospectus/user/fxml/UserProfile.fxml");
    }

    @FXML
    private void auditLogsHandler(MouseEvent event) {
        loadPage("/prospectus/admin/fxml/AuditLog.fxml");
    }

    @FXML
    private void programsClickHandler(MouseEvent event) {
        loadPage("/prospectus/admin/programs/managePrograms.fxml");
    }

    @FXML
    private void logoutOnClick(MouseEvent event) {
        String username = UserSession.getUsername();
        logger.addLog(username, "Logout", "Admin logged out: " + username);
        UserSession.clearSession();
        utilities.switchScene(getClass(), event, "/prospectus/auth/fxml/LoginPage.fxml");
    }

    @FXML
    private void studentHandler(MouseEvent event) {
        loadPage("/prospectus/admin/students/manageStudents.fxml");
    }

    @FXML
    private void coursesHandler(MouseEvent event) {
        loadPage("/prospectus/admin/courses/manageCourses.fxml");
    }
}
