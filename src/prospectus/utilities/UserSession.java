package prospectus.utilities;

public class UserSession {
    private static String userId;
    private static String fullName;
    private static String program;
    private static boolean isEnrolled;
    private static String profileImagePath;
    private static String email;
    private static String phoneNumber;

    public static void setUserData(String id, String name, String prog, boolean enrolled, String imagePath, String emailAddr, String phone) {
        userId = id;
        fullName = name;
        program = prog;
        isEnrolled = enrolled;
        profileImagePath = imagePath;
        email = emailAddr;
        phoneNumber = phone;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getFullName() {
        return fullName;
    }

    public static String getProgram() {
        return program;
    }

    public static boolean isEnrolled() {
        return isEnrolled;
    }

    public static String getProfileImagePath() {
        return profileImagePath;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }
} 