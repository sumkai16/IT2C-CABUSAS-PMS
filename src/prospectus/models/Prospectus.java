package prospectus.models;

public class Prospectus {
    private int prId;
    private String pDepartment;
    private String prEffectiveYear;
    private String status;
    private String yearLevel;
    private String semester;

    // Constructor with only the necessary fields
    public Prospectus(int prId, String pDepartment, String prEffectiveYear, String status,
                      String yearLevel, String semester) {
        this.prId = prId;
        this.pDepartment = pDepartment;
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