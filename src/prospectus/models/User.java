package prospectus.models;

public class User {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String role;
    private String status;
    private String contact;
    private String profileImagePath;
//    private String enrollmentStatus;

    // Constructor
    public User(int id, String firstName, String middleName, String lastName, String email, 
                String username, String password, String role, String status, 
                String contact, String profileImagePath) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.contact = contact;
        this.profileImagePath = profileImagePath;
//        this.enrollmentStatus = enrollStatus;
    }

    // Getters and Setters (Add these if needed)
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getContact() { return contact; }
    public String getProfileImagePath() { return profileImagePath; }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
//    public String getEnrollmentStatus() {
//        return enrollmentStatus;
//    }
}



