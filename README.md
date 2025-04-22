# Prospectus Management System (PMS)

A JavaFX-based academic prospectus management system that helps educational institutions manage and view course curricula efficiently.

## Features

- **Prospectus Viewing**: View complete course curricula organized by year and semester
- **Course Management**: Track course details including:
  - Course codes
  - Course descriptions
  - Unit counts
  - Prerequisites
- **Year-wise Organization**: Structured view of courses divided into:
  - First Year (1st & 2nd Semester)
  - Second Year (1st & 2nd Semester)
  - Third Year (1st & 2nd Semester)
  - Fourth Year (1st & 2nd Semester)
- **Unit Calculation**: Automatic calculation of total units per semester
- **Department-specific Curricula**: Support for different department prospectuses
- **Modern UI**: Clean and intuitive user interface with responsive design

## Technologies Used

- Java
- JavaFX
- CSS for styling
- SQL Database (for data persistence)

## Setup Instructions

1. **Prerequisites**
   - Java Development Kit (JDK) 8 or higher
   - JavaFX SDK
   - MySQL/MariaDB Database

2. **Database Setup**
   - Create a new database for the application
   - Import the provided SQL schema
   - Configure database connection properties

3. **Application Setup**
   ```bash
   # Clone the repository
   git clone [repository-url]

   # Navigate to project directory
   cd IT2C-CABUSAS-PMS

   # Compile the project
   javac -cp path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src/**/*.java

   # Run the application
   java -cp path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml Main
   ```

## Project Structure

```
src/
├── prospectus/
│   ├── admin/
│   │   └── prospectus/
│   │       ├── ViewProspectusController.java
│   │       └── prospectusTable.css
│   └── models/
│       ├── Prospectus.java
│       └── ProspectusDetails.java
└── ...
```

## Usage

1. Launch the application
2. Navigate to the Prospectus Management section
3. View prospectus details organized by year and semester
4. Each table displays course information including:
   - Course Code
   - Course Description
   - Units
   - Prerequisites

## Contributing

1. Fork the repository
2. Create a new branch (`git checkout -b feature/improvement`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add new feature'`)
5. Push to the branch (`git push origin feature/improvement`)
6. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- JavaFX Community
- Contributors and testers
- [Your Institution Name]

## Contact

For any queries or support, please contact:
- [Your Name]
- [Your Email]
- [Your Institution] 