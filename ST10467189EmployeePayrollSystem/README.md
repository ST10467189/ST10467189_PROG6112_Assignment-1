# Employee Payroll System

A comprehensive Java-based employee payroll management system that handles different types of employees, calculates various pay scenarios, and provides a user-friendly console interface.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Employee Types](#employee-types)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Testing](#testing)
- [Technical Details](#technical-details)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

The Employee Payroll System is a Java application designed to manage employee data and calculate various payroll scenarios. It supports both full-time and part-time employees with different pay calculation methods, including overtime handling for part-time employees.

## ✨ Features

### Core Functionality
- **Employee Management**: Add and manage different types of employees
- **Clock In/Out System**: Track employee work status
- **Pay Calculations**: 
  - Daily pay calculation
  - Monthly salary estimation
  - Six-month salary projection
- **Overtime Handling**: Automatic overtime calculation for part-time employees
- **Interactive Menu System**: User-friendly console interface

### Employee Types
- **Full-Time Employees**: Standard hourly rate for all hours worked
- **Part-Time Employees**: Regular rate for first 8 hours, 1.5x rate for overtime

### System Features
- **Input Validation**: Comprehensive error handling and input validation
- **Comprehensive Testing**: Full test suite with JUnit 5
- **Object-Oriented Design**: Clean inheritance hierarchy and polymorphism
- **Maven Build System**: Easy dependency management and building

## 📁 Project Structure

```
ST10467189EmployeePayrollSystem/
├── src/
│   ├── main/java/com/mycompany/st10467189employeepayrollsystem/
│   │   ├── ST10467189EmployeePayrollSystem.java    # Main application class
│   │   ├── Employee.java                           # Base employee class
│   │   ├── FullTimeEmployee.java                   # Full-time employee implementation
│   │   └── PartTimeEmployee.java                   # Part-time employee implementation
│   └── test/java/com/mycompany/st10467189employeepayrollsystem/
│       ├── EmployeeTest.java                       # Employee class tests
│       ├── FullTimeEmployeeTest.java               # Full-time employee tests
│       ├── PartTimeEmployeeTest.java               # Part-time employee tests
│       ├── ST10467189EmployeePayrollSystemTest.java # Integration tests
│       └── SimpleTestRunner.java                   # Test runner utility
├── target/                                         # Compiled classes and test reports
├── pom.xml                                         # Maven configuration
└── README.md                                       # This file
```

## 👥 Employee Types

### Full-Time Employee
- **Pay Structure**: Regular hourly rate for all hours worked
- **Overtime**: No overtime pay (all hours at regular rate)
- **Use Case**: Standard 9-to-5 employees

### Part-Time Employee
- **Pay Structure**: 
  - Regular rate for first 8 hours
  - 1.5x rate for hours over 8 (overtime)
- **Overtime Tracking**: Automatic overtime calculation and tracking
- **Use Case**: Flexible schedule employees

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git (for cloning the repository)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ST10467189EmployeePayrollSystem
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java
   ```
   
   Or run the JAR file:
   ```bash
   mvn package
   java -jar target/ST10467189EmployeePayrollSystem-1.0-SNAPSHOT.jar
   ```

## 💻 Usage

### Running the Application

1. **Start the application** using one of the methods above
2. **View the staff list** - The system displays current employees
3. **Select from the menu**:
   - **Option 1**: Clock In/Out employees
   - **Option 2**: Calculate daily pay for an employee
   - **Option 3**: Calculate monthly salary (individual or all staff)
   - **Option 4**: Estimate 6-month salary (individual or all staff)
   - **Option 5**: Exit the program

### Sample Workflow

```
Welcome to the Employee Payroll System!
=====================================

Current Staff:
ID      Name                    Type            Pay Rate
------------------------------------------------
1       John Doe                FullTimeEmployee        R200.00/hr
2       Jane Smith              PartTimeEmployee        R200.00/hr
3       Peter Jones             FullTimeEmployee        R200.00/hr
------------------------------------------------

Please select an option:
1. Clock In/Out
2. Calculate Daily Pay for an Employee
3. Calculate Monthly Salary (Individual/All)
4. Estimate 6-Month Salary (Individual/All)
5. Exit
Enter your choice (1-5): 
```

### Example Calculations

**Daily Pay Example:**
- Full-time employee working 10 hours: `10 × R200 = R2,000`
- Part-time employee working 10 hours: `8 × R200 + 2 × R300 = R2,200`

**Monthly Salary Example (20 working days):**
- Full-time employee: `20 × 8 × R200 = R32,000`
- Part-time employee: `20 × 8 × R200 = R32,000` (no overtime in monthly calculation)

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmployeeTest

# Run with detailed output
mvn test -Dtest=*Test -Dmaven.test.failure.ignore=true
```

### Test Coverage

The project includes comprehensive test coverage:

- **Unit Tests**: Individual class testing
- **Integration Tests**: System-wide functionality testing
- **Edge Case Testing**: Boundary conditions and error handling
- **Parameterized Tests**: Multiple input scenarios

### Test Structure

- `EmployeeTest.java`: Tests base Employee class functionality
- `FullTimeEmployeeTest.java`: Tests full-time employee specific features
- `PartTimeEmployeeTest.java`: Tests part-time employee and overtime features
- `ST10467189EmployeePayrollSystemTest.java`: Integration tests for the main system

## 🔧 Technical Details

### Architecture

- **Object-Oriented Design**: Inheritance hierarchy with Employee as base class
- **Polymorphism**: Different pay calculations based on employee type
- **Encapsulation**: Private fields with public getters and validation
- **Exception Handling**: Comprehensive input validation and error handling

### Key Classes

#### Employee (Base Class)
- Common employee properties and methods
- Basic pay calculation
- Clock in/out functionality
- Input validation

#### FullTimeEmployee
- Extends Employee
- Standard pay calculation (no overtime)
- Regular hourly rate for all hours

#### PartTimeEmployee
- Extends Employee
- Overtime pay calculation (1.5x rate after 8 hours)
- Overtime tracking and management

#### ST10467189EmployeePayrollSystem
- Main application class
- User interface and menu system
- Employee management
- Pay calculation coordination

### Dependencies

- **Java 17**: Modern Java features and performance
- **JUnit 5**: Comprehensive testing framework
- **Maven**: Build automation and dependency management

### Build Configuration

The project uses Maven with the following key configurations:
- Java 17 compilation target
- JUnit 5 for testing
- Executable JAR with main class specification
- Surefire plugin for test execution

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java naming conventions
- Add comprehensive tests for new features
- Update documentation for any API changes
- Ensure all tests pass before submitting

## 📄 License

This project is developed as part of academic coursework. Please refer to your institution's guidelines for usage and distribution.

## 📞 Support

For questions or issues:
1. Check the test files for usage examples
2. Review the inline code documentation
3. Create an issue in the repository

---

**Note**: This system was developed with AI assistance and follows academic programming standards. The code includes comprehensive documentation and testing to ensure reliability and maintainability.

