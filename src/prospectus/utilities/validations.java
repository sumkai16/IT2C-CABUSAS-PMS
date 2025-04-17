package prospectus.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.dbConnector;
import javafx.scene.control.Alert;
import prospectus.auth.controller.RegisterPageController;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validations {
    
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = "^(09\\d{9}|\\+639\\d{9})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    private static final  dbConnector db = new dbConnector();
    public static boolean validateInputs(String firstname, String lastname, String email, String contact, String username, String password) {
        if (firstname.isEmpty() ||
                lastname.isEmpty() ||
                email.isEmpty() || contact.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty()) {
            utilities.showAlert(Alert.AlertType.WARNING, "Validation Error", "All fields are required.");
            return false;
        }
        return true;
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        boolean hasAlpha = false;
        
        for(char c : phoneNumber.toCharArray()) {
            if(Character.isAlphabetic(c)) {
                hasAlpha = true;
            }
        }
        return hasAlpha;
    }
    
    public boolean isValidPhoneNumberFormat(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
    
    public boolean isValidEmailFormat(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    
    public boolean isDuplicated(String column, String value) throws SQLException {
      
        
        try(ResultSet result = db.getData("SELECT " + column + " FROM user")) {    
            while(result.next()) {
                if(value.equals(result.getString(column)))
                    return true;
            }
        }
        return false;
    }
    public static boolean isStudentEnrolled(String username) {
        String query = "SELECT enrollment_status FROM user WHERE u_username = ?";
        try (Connection conn = db.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    String enrollmentStatus = resultSet.getString("enrollment_status");
                    return "Enrolled".equalsIgnoreCase(enrollmentStatus); // Adjust based on your actual enrollment status values
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        return false;
    }
    
}
