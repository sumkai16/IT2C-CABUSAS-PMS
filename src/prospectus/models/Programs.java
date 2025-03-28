/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.models;

/**
 *
 * @author axcee
 */
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
}
