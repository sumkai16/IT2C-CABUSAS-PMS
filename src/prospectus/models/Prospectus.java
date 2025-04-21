package prospectus.models;

/**
 *
 * @author axcee
 */
public class Prospectus {
    private int prId;
    private String program;
    private String course;
    private String prEffectiveYear;
    private String status;
    private String createdBy;
    private String yearLevel; // New field
    private String semester; // New field

    // Constructor
    public Prospectus(int prId, String program, String course, String prEffectiveYear, String status, String createdBy, String yearLevel, String semester) {
        this.prId = prId;
        this.program = program;
        this.course = course;
        this.prEffectiveYear = prEffectiveYear;
        this.status = status;
        this.createdBy = createdBy;
        this.yearLevel = yearLevel; // Initialize new field
        this.semester = semester; // Initialize new field
    }

    // Getters and Setters
    public int getPrId() {
        return prId;
    }

    public void setPrId(int prId) {
        this.prId = prId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPrEffectiveYear() {
        return prEffectiveYear;
    }

    public void setPrEffectiveYear(String prEffectiveYear) {
        this.prEffectiveYear = prEffectiveYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getYearLevel() { // New getter
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) { // New setter
        this.yearLevel = yearLevel;
    }

    public String getSemester() { // New getter
        return semester;
    }

    public void setSemester(String semester) { // New setter
        this.semester = semester;
    }
}