package prospectus.models;

import javafx.scene.control.MenuButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.dbConnector;

public class Programs {
    private int id;
    private String programName;
    private String description;
    private String department;
    private String status;

    // Constructor
    public Programs(int id, String programName, String description, String department, String status) {
        this.id = id;
        this.programName = programName;
        this.description = description;
        this.department = department;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getProgramName() {
        return programName;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartment() {
        return department;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Retrieve Program ID from MenuButton selection
    public static int getProgramIdFromName(MenuButton menuButton) {
        if (menuButton.getText() == null || menuButton.getText().isEmpty()) {
            System.out.println("No program selected!");
            return -1; // Return -1 if no selection
        }

        String programName = menuButton.getText();
        return fetchProgramIdFromDatabase(programName);
    }

    // Fetch Program ID from Database
    private static int fetchProgramIdFromDatabase(String programName) {
        int programId = -1;
        dbConnector db = new dbConnector();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            String query = "SELECT id FROM programs WHERE program_name = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, programName);
            rs = pst.executeQuery();

            if (rs.next()) {
                programId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return programId;
    }
    @Override
    public String toString() {
        return programName; // Ensure ComboBox displays only the program name
    }

}
