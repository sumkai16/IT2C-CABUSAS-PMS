package models;

public class UserSession {
    private static int userId;
    private static String firstName;
    private static String lastName;

    public static void createSession(int id, String fName, String lName) {
        userId = id;
        firstName = fName;
        lastName = lName;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static int getUserId() {
        return userId;
    }

    public static void clearSession() {
        userId = 0;
        firstName = null;
        lastName = null;
    }
}
