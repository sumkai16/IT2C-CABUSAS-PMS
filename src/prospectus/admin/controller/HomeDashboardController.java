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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.dbConnector;

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
    private static dbConnector db = new dbConnector();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         loadCounts();
    }    
    private void loadCounts() {
        String countProgramsQuery = "SELECT COUNT(*) FROM program";
        String countCoursesQuery = "SELECT COUNT(*) FROM course";
        String countStudentsQuery = "SELECT COUNT(*) FROM user WHERE u_role = 'Student'";

        try {
             db = new dbConnector();
            Connection conn = db.getConnection();

           
            PreparedStatement pst1 = conn.prepareStatement(countProgramsQuery);
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) {
                numberOfPrograms.setText(String.valueOf(rs1.getInt(1)));
            }

            
            PreparedStatement pst2 = conn.prepareStatement(countCoursesQuery);
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) {
                numberOfCourses.setText(String.valueOf(rs2.getInt(1)));
            }

            
            PreparedStatement pst3 = conn.prepareStatement(countStudentsQuery);
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) {
                numberOfStudents.setText(String.valueOf(rs3.getInt(1)));
            }

            
            rs1.close();
            rs2.close();
            rs3.close();
            pst1.close();
            pst2.close();
            pst3.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Error fetching counts: " + ex.getMessage());
        }
    }

}
