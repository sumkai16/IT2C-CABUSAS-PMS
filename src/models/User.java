package models;

public class User {
    private int userID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String role;
    private String status;

    public User(int userID, String firstName, String middleName, String lastName, String role, String status) {
        this.userID = userID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
//        this.email = email;
//        this.userName = userName;
//        this.password = password;
        this.role = role;
        this.status = status;
    }

    public User(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
//    public String getEmail() { return email; }
//    public String getUserName() { return userName; }
//    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
}
