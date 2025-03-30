package prospectus.models;

public class UserSession {
    private static int userId;
    private static String firstName;
    private static String lastName;
    private static String name;
    private static String userRole;
    private static String en_status;
    private static String userStatus;
    private static String userName;
    private static String profileImagePath;  

    public static void createSession(int id, String fName, String lName, String role, String u_status, String enrollment_status, String username, String imagePath) {
        userId = id;
        firstName = fName;
        lastName = lName;
        userRole = role;
        en_status = enrollment_status;
        userStatus = u_status;
        userName = username;
        profileImagePath = imagePath;  

       
        System.out.println("Session Profile Path Set: " + profileImagePath);
    }

    public static String getFirstName() {
        return firstName;
    }
    public static String getUserStatus() {
        return userStatus;
    }
    public static String getLastName() {
        return lastName;
    }
    public static String getRole() {
        return userRole;
    }
    public static String getEnrollmentStatus() {
        return en_status;
    }
    public static String getUsername() {
        return userName;
    }
    public static int getUserId() {
        return userId;
    }
    public static String getProfileImage() {
        return profileImagePath;  
    }

    public static void clearSession() {
        userId = 0;
        firstName = null;
        lastName = null;
        userRole = null;
        en_status = null;
        userStatus = null;
        userName = null;
        name = null;
        profileImagePath = null;  
    }

    public static String name() {
        return name;
    }
}
