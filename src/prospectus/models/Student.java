package prospectus.models;

import java.time.LocalDate;

public class Student {
    private int id; 
    private int userId; 
    private String firstName; 
    private String middleName; 
    private String lastName; 
    private LocalDate birthDate; 
    private String address; 
    private int year; 
    private String program; 
    private String previousSchool; 
    private String gender; 
    private String profileImagePath; 
    private String enrollmentStatus;

    // Constructor
    public Student(int id, int userId, String firstName, String middleName, String lastName, LocalDate birthDate, String address, int year, String program, String previousSchool, String gender, String profileImagePath, String enrollmentStatus) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.year = year;
        this.program = program;
        this.previousSchool = previousSchool; 
        this.gender = gender; 
        this.profileImagePath = profileImagePath; 
        this.enrollmentStatus = enrollmentStatus;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public int getYear() { return year; }
    public String getProgram() { return program; }
    public String getPreviousSchool() { return previousSchool; } 
    public String getGender() { return gender; } 
    public String getProfileImagePath() { return profileImagePath; } 
    public String getEnrollmentStatus() { return enrollmentStatus; }
}