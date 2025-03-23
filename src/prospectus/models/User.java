package prospectus.models;

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
    private String contact;

    // Updated constructor to include missing fields
    public User(int userID, String firstName, String middleName, String lastName, String email, String userName, String password, String role, String status, String contact) {
        this.userID = userID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.contact = contact;
    }

    // Getters
    public int getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getContact() { return contact; }
}
