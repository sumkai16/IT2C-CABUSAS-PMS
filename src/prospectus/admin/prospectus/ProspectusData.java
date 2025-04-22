package prospectus.admin.prospectus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import prospectus.models.Prospectus;
import prospectus.models.ProspectusDetails;
import main.dbConnector;

public class ProspectusData {
    private static final Logger LOGGER = Logger.getLogger(ProspectusData.class.getName());
    private dbConnector db = new dbConnector();

    public List<Prospectus> getProspectus() {
        List<Prospectus> prospectusList = new ArrayList<>();
        
        String query = "SELECT DISTINCT p.pr_id, prog.p_program_name, prog.p_department, p.pr_effective_year, p.status " +
                      "FROM prospectus p " +
                      "JOIN program prog ON p.program_id = prog.p_id " +
                      "WHERE p.status = 'Active'";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String programName = rs.getString("p_program_name");
                String department = rs.getString("p_department");
                String displayName = programName + " (" + department + ")";
                
                prospectusList.add(new Prospectus(
                    rs.getInt("pr_id"),
                    displayName,
                    rs.getString("pr_effective_year"),
                    rs.getString("status"),
                    "", // Empty year level since we're not using it here
                    ""  // Empty semester since we're not using it here
                ));
            }
            
            LOGGER.info("Retrieved " + prospectusList.size() + " prospectus records");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving prospectus data: " + e.getMessage(), e);
        }
        return prospectusList;
    }

    public List<ProspectusDetails> getProspectusDetails(int prId) {
        List<ProspectusDetails> detailsList = new ArrayList<>();
        
        String query = "SELECT pd.pd_id, pd.pr_id, pd.course_id, pd.year_level, pd.semester, " +
                      "c.c_code AS courseCode, c.c_desc AS courseDesc, c.c_units AS units, " +
                      "COALESCE(prereq.c_code, '') AS prerequisite " +
                      "FROM prospectus_details pd " +
                      "JOIN course c ON pd.course_id = c.c_id " +
                      "LEFT JOIN course prereq ON c.prerequisite_id = prereq.c_id " +
                      "WHERE pd.pr_id = ? " +
                      "ORDER BY CASE " +
                      "    WHEN pd.year_level = '1st Year' THEN 1 " +
                      "    WHEN pd.year_level = '2nd Year' THEN 2 " +
                      "    WHEN pd.year_level = '3rd Year' THEN 3 " +
                      "    WHEN pd.year_level = '4th Year' THEN 4 " +
                      "    ELSE 5 END, " +
                      "CASE " +
                      "    WHEN pd.semester = '1st Semester' THEN 1 " +
                      "    WHEN pd.semester = '2nd Semester' THEN 2 " +
                      "    ELSE 3 END";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int pdId = rs.getInt("pd_id");
                int courseId = rs.getInt("course_id");
                String yearLevel = rs.getString("year_level");
                String semester = rs.getString("semester");
                String courseCode = rs.getString("courseCode");
                String courseDesc = rs.getString("courseDesc");
                int units = rs.getInt("units");
                String prerequisite = rs.getString("prerequisite");

                // Create a new ProspectusDetails object
                ProspectusDetails details = new ProspectusDetails(pdId, prId, courseId, yearLevel, semester);
                details.setCourseCode(courseCode);
                details.setCourseDesc(courseDesc);
                details.setUnits(units);
                details.setPrerequisite(prerequisite);

                detailsList.add(details);
                
                // Log the details for debugging
                LOGGER.info(String.format("Loaded detail: %s - %s, %s, %s", 
                    courseCode, courseDesc, yearLevel, semester));
            }
            
            LOGGER.info("Retrieved " + detailsList.size() + " prospectus details for prId: " + prId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving prospectus details: " + e.getMessage(), e);
        }
        return detailsList;
    }
} 