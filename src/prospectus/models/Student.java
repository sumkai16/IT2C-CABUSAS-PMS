
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

    // Constructor
    public Student(int id, int userId, String firstName, String middleName, String lastName, LocalDate birthDate, String address, int year, String program) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.year = year;
        this.program = program;
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
}