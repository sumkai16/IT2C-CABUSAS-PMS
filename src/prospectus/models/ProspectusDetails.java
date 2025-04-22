package prospectus.models;

public class ProspectusDetails {
    private int pd_id;
    private int pr_id;
    private int course_id;
    private String year_level;
    private String semester;
    private String courseCode;
    private String courseDesc;
    private int units;
    private String prerequisite;

    // Constructor
    public ProspectusDetails(int pd_id, int pr_id, int course_id, String year_level, String semester) {
        this.pd_id = pd_id;
        this.pr_id = pr_id;
        this.course_id = course_id;
        this.year_level = year_level;
        this.semester = semester;
    }

    // Getters and Setters
    public int getPd_id() {
        return pd_id;
    }

    public void setPd_id(int pd_id) {
        this.pd_id = pd_id;
    }

    public int getPr_id() {
        return pr_id;
    }

    public void setPr_id(int pr_id) {
        this.pr_id = pr_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getYear_level() {
        return year_level;
    }

    public void setYear_level(String year_level) {
        this.year_level = year_level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }
}