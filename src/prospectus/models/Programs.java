package prospectus.models;

import javafx.scene.control.MenuButton;

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

    public static int getProgramIdFromName(MenuButton menuButton) {
        if (menuButton.getText() == null || menuButton.getText().isEmpty()) {
            System.out.println("No program selected!");
            return -1; // Return -1 if no selection
        }

        // Assuming menu text holds the program name
        String programName = menuButton.getText();

        // Convert the program name to an ID (Example: Fetch from DB)
        int programId = fetchProgramIdFromDatabase(programName);

        return programId;
    }

    private static int fetchProgramIdFromDatabase(String programName) {
        // Placeholder: Replace with actual database query
        if (programName.equals("BSIT")) return 1;
        if (programName.equals("BSCS")) return 2;
        return -1; // Default if not found
    }
}