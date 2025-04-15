package prospectus.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private int c_id;
    private String c_code;
    private String c_desc;
    private int c_units;
    private Integer prerequisite_id; // Can be null
    private int program_id;
    private String programDepartment; // Added field for program department name
    private String prerequisiteCode; // New field for prerequisite course code

    // Constructor
    public Course(int c_id, String c_code, String c_desc, int c_units, Integer prerequisite_id, int program_id) {
        this.c_id = c_id;
        this.c_code = c_code;
        this.c_desc = c_desc;
        this.c_units = c_units;
        this.prerequisite_id = prerequisite_id;
        this.program_id = program_id;
        this.programDepartment = ""; // Default empty string
        this.prerequisiteCode = ""; // Default empty string
    }

    // Getters and Setters
    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getC_desc() {
        return c_desc;
    }

    public void setC_desc(String c_desc) {
        this.c_desc = c_desc;
    }

    public int getC_units() {
        return c_units;
    }

    public void setC_units(int c_units) {
        this.c_units = c_units;
    }

    public Integer getPrerequisite_id() {
        return prerequisite_id;
    }

    public void setPrerequisite_id(Integer prerequisite_id) {
        this.prerequisite_id = prerequisite_id;
    }

    public int getProgram_id() {
        return program_id;
    }

    public void setProgram_id(int program_id) {
        this.program_id = program_id;
    }
    
    // New getter and setter for program department
    public String getProgramDepartment() {
        return programDepartment;
    }
    
    public void setProgramDepartment(String programDepartment) {
        this.programDepartment = programDepartment;
    }

    @Override
    public String toString() {
        return c_code + " - " + c_desc;
    }
    public String getPrerequisiteCode() {
        return (prerequisiteCode == null || prerequisiteCode.isEmpty()) ? "None" : prerequisiteCode;
    }

    public void setPrerequisiteCode(String prerequisiteCode) {
        this.prerequisiteCode = prerequisiteCode;
    }

    
}

