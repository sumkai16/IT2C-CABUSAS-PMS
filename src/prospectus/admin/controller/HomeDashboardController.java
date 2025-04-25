/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.admin.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.dbConnector;
import prospectus.models.UserSession;

/**
 * FXML Controller class
 *
 * @author SCC-COLLEGE
 */
public class HomeDashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label numberOfPrograms;
    @FXML
    private Label numberOfCourses;
    @FXML
    private Label numberOfStudents;
    @FXML
    private Label numberOfUsers;
    @FXML
    private Label numberOfProspectus;
    @FXML
    private Label adminNameLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private ListView<String> activityListView;
    
    private static dbConnector db = new dbConnector();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set admin name
        adminNameLabel.setText(UserSession.getFirstName());
        
        // Set up real-time clock
        setupClock();
        
        // Load dashboard data
        loadCounts();
        
        // Load recent activities
        loadRecentActivities();
    }    
    
    private void setupClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            timeLabel.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private void loadCounts() {
        String countProgramsQuery = "SELECT COUNT(*) FROM program";
        String countCoursesQuery = "SELECT COUNT(*) FROM course";
        String countStudentsQuery = "SELECT COUNT(*) FROM user WHERE u_role = 'Student'";
        String countUsersQuery = "SELECT COUNT(*) FROM user";
        String countProspectusQuery = "SELECT COUNT(*) FROM prospectus";

        try {
            db = new dbConnector();
            Connection conn = db.getConnection();

            // Load programs count
            PreparedStatement pst1 = conn.prepareStatement(countProgramsQuery);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                numberOfPrograms.setText(String.valueOf(rs1.getInt(1)));
            }

            // Load courses count
            PreparedStatement pst2 = conn.prepareStatement(countCoursesQuery);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                numberOfCourses.setText(String.valueOf(rs2.getInt(1)));
            }

            // Load students count
            PreparedStatement pst3 = conn.prepareStatement(countStudentsQuery);
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) {
                numberOfStudents.setText(String.valueOf(rs3.getInt(1)));
            }

            // Load users count
            PreparedStatement pst4 = conn.prepareStatement(countUsersQuery);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                numberOfUsers.setText(String.valueOf(rs4.getInt(1)));
            }

            // Load prospectus count
            PreparedStatement pst5 = conn.prepareStatement(countProspectusQuery);
            ResultSet rs5 = pst5.executeQuery();
            if (rs5.next()) {
                numberOfProspectus.setText(String.valueOf(rs5.getInt(1)));
            }

            // Close resources
            rs1.close();
            rs2.close();
            rs3.close();
            rs4.close();
            rs5.close();
            pst1.close();
            pst2.close();
            pst3.close();
            pst4.close();
            pst5.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error fetching counts: " + ex.getMessage());
        }
    }

    private void loadRecentActivities() {
        String query = "SELECT * FROM logs ORDER BY date_time DESC LIMIT 10";
        try {
            Connection conn = db.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String activity = String.format("[%s] %s - %s",
                    rs.getTimestamp("date_time").toLocalDateTime().format(DateTimeFormatter.ofPattern("MM/dd HH:mm")),
                    rs.getString("action"),
                    rs.getString("description")
                );
                activityListView.getItems().add(activity);
            }

            rs.close();
            pst.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error loading activities: " + ex.getMessage());
        }
    }
}
