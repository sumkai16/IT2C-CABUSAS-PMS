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
                      "c.c_code as courseCode, c.c_desc as courseDesc, c.c_units as units, " +
                      "(SELECT c2.c_code FROM course c2 WHERE c2.c_id = c.prerequisite_id) as prerequisite " +
                      "FROM prospectus_details pd " +
                      "JOIN course c ON pd.course_id = c.c_id " +
                      "WHERE pd.pr_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, prId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProspectusDetails details = new ProspectusDetails(
                    rs.getInt("pd_id"),
                    rs.getInt("pr_id"),
                    rs.getInt("course_id"),
                    rs.getString("year_level"),
                    rs.getString("semester")
                );
                details.setCourseCode(rs.getString("courseCode"));
                details.setCourseDesc(rs.getString("courseDesc"));
                details.setUnits(rs.getInt("units"));
                details.setPrerequisite(rs.getString("prerequisite"));
                detailsList.add(details);

                System.out.println("Loaded course: " + details.getCourseCode() + " - " + details.getCourseDesc());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailsList;
    }

  
} 