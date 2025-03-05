package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.dbConnector;
import javafx.scene.control.Alert;

public class validations {
    
    private static final dbConnector db = new dbConnector();

    public static boolean validateInputs(String firstname, String lastname, String email, String contact, String username, String password) {
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || contact.isEmpty() || username.isEmpty() || password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return false;
        }
        return true;
    }

    public static boolean isDuplicate(String column, String value) {
        String sql = "SELECT COUNT(*) FROM user WHERE " + column + " = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, value);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            utilities.showAlert(Alert.AlertType.ERROR, "Database Err434or", "An error occurred: " + ex.getMessage());
        }
        return false;
    }
}
