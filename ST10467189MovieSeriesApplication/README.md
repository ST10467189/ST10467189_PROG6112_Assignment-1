# Movie Series Application

A Java console application for managing movie series data with CRUD operations.

## Features

- **Capture Series**: Add new series with validation
- **Search Series**: Find series by ID
- **Update Series**: Modify existing series information
- **Delete Series**: Remove series with confirmation
- **Series Report**: Display all series in a formatted report
- **Age Validation**: Ensures age restrictions are between 2-18

## Project Structure

```
src/
├── main/java/com/mycompany/st10467189movieseriesapplication/
│   ├── Series.java                           # Main business logic
│   ├── SeriesModel.java                      # Data model
│   └── ST10467189MovieSeriesApplication.java # Main application entry point
└── test/java/com/mycompany/st10467189movieseriesapplication/
    ├── SeriesTest.java                       # Unit tests for Series class
    ├── SeriesModelTest.java                  # Unit tests for SeriesModel
    ├── IntegrationTest.java                  # Integration tests
    ├── ST10467189MovieSeriesApplicationTest.java # Application tests
    └── SimpleTestRunner.java                 # Test runner utility
```

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Building and Running

### Compile and Run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.st10467189movieseriesapplication.ST10467189MovieSeriesApplication"
```

### Run Tests
```bash
mvn test
```

### Clean Build
```bash
mvn clean compile
```

## Usage

1. Run the application
2. Enter `1` to launch the menu
3. Choose from the available options:
   - `1` - Capture a new series
   - `2` - Search for a series
   - `3` - Update series age restriction
   - `4` - Delete a series
   - `5` - Print series report
   - `6` - Exit application

## Data Model

Each series contains:
- Series ID (String)
- Series Name (String)
- Age Restriction (2-18)
- Number of Episodes (String)

## Author

ST10467189 Leonard McDermott

## License

This project is for educational purposes.
