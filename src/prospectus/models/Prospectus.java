package prospectus.models;

public class Prospectus {
    private int prId;
    private String pDepartment;
    private String pProgramName; // New field for program name
    private String prEffectiveYear;
    private String status;
    private String yearLevel;
    private String semester;

    // Updated constructor with program name
    public Prospectus(int prId, String pDepartment, String pProgramName, String prEffectiveYear, String status,
                      String yearLevel, String semester) {
        this.prId = prId;
        this.pDepartment = pDepartment;
        this.pProgramName = pProgramName;
        this.prEffectiveYear = prEffectiveYear;
        this.status = status;
        this.yearLevel = yearLevel;
        this.semester = semester;
    }

    // Getters for TableView binding
    public int getPrId() {
        return prId;
    }

    public String getPDepartment() {
        return pDepartment;
    }

    public String getPProgramName() {
        return pProgramName;
    }

    public String getPrEffectiveYear() {
        return prEffectiveYear;
    }

    public String getStatus() {
        return status;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public String getSemester() {
        return semester;
    }
}
