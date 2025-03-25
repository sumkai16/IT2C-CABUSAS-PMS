/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prospectus.models;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author axcee
 */
public class LogEntry {
    private String username;
    private String action;
    private String description;
    private String timestamp;  // Ensure this field exists

    public LogEntry(String username, String action, String description, String timestamp) {
        this.username = username;
        this.action = action;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getUsername() { return username; }
    public String getAction() { return action; }
    public String getDescription() { return description; }
    public String getTimestamp() { return timestamp; } // ✅ Ensure getter exists
}
