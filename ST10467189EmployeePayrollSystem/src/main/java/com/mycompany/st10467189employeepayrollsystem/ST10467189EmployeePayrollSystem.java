package com.mycompany.st10467189employeepayrollsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Employee Payroll System.
 * This class handles the user interface and coordinates all payroll operations.
 */
public class ST10467189EmployeePayrollSystem {
    
    // An ArrayList to store Employee objects. Using ArrayList is more flexible than a fixed-size array.
    private static final List<Employee> staff = new ArrayList<>();
    private static final int STANDARD_WORKING_DAYS = 20;
    private static final Scanner scanner = new Scanner(System.in);
    
    // The main method where the program execution begins.
    public static void main(String[] args) {
        
        // Populate the staff with some initial employee data.
        initializeStaff();
        
        System.out.println("Welcome to the Employee Payroll System!");
        System.out.println("=====================================");
        displayStaffList();
        
        boolean isRunning = true;
        while (isRunning) {
            String choice = displayMenu();
            
            // Use a switch statement to handle the user's menu choice.
            switch (choice) {
                case "1":
                    clockInOut();
                    break;
                case "2":
                    calculateDailyPay();
                    break;
                case "3":
                    calculateMonthlySalary();
                    break;
                case "4":
                    estimateSixMonthSalary();
                    break;
                case "5":
                    System.out.println("Exiting the program. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
    
    // A method to initialize the staff with some sample data.
    private static void initializeStaff() {
        staff.add(new FullTimeEmployee("John Doe", 1, 200.0));
        staff.add(new PartTimeEmployee("Jane Smith", 2, 200.0));
        staff.add(new FullTimeEmployee("Peter Jones", 3, 200.0));
    }
    
    // A method to display the current staff list.
    private static void displayStaffList() {
        System.out.println("\nCurrent Staff:");
        System.out.println("ID\tName\t\t\tType\t\tPay Rate");
        System.out.println("------------------------------------------------");
        for (Employee employee : staff) {
            System.out.printf("%d\t%-20s\t%-15s\tR%.2f/hr%n", 
                employee.getEmployeeId(), 
                employee.getName(), 
                employee.getEmployeeType(),
                employee.getPayRate());
        }
        System.out.println("------------------------------------------------");
    }
    
    // Displays the main menu options to the user and returns their choice.
    private static String displayMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Clock In/Out");
        System.out.println("2. Calculate Daily Pay for an Employee");
        System.out.println("3. Calculate Monthly Salary (Individual/All)");
        System.out.println("4. Estimate 6-Month Salary (Individual/All)");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Method to handle the clock-in/out logic.
     */
    private static void clockInOut() {
        try {
            System.out.print("Enter employee ID (1-3): ");
            String idString = scanner.nextLine().trim();
            
            if (idString.isEmpty()) {
                System.out.println("No ID entered. Returning to main menu.");
                return;
            }
            
            int id = Integer.parseInt(idString);
            
            Employee employee = findEmployeeById(id);
            
            if (employee == null) {
                System.out.println("Employee not found. Available IDs: 1, 2, 3");
                return;
            }
            
            if (employee.isClockedIn()) {
                employee.clockOut();
                System.out.println(employee.getName() + " has been clocked out.");
            } else {
                employee.clockIn();
                System.out.println(employee.getName() + " has been clocked in.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid number (1, 2, or 3).");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Method to calculate and display an employee's daily pay.
     */
    private static void calculateDailyPay() {
        try {
            System.out.print("Enter employee ID (1-3): ");
            String idString = scanner.nextLine().trim();
            
            if (idString.isEmpty()) {
                System.out.println("No ID entered. Returning to main menu.");
                return;
            }
            
            int id = Integer.parseInt(idString);
            
            Employee employee = findEmployeeById(id);
            
            if (employee == null) {
                System.out.println("Employee not found. Available IDs: 1, 2, 3");
                return;
            }
            
            System.out.print("Enter hours worked for the day: ");
            String hoursString = scanner.nextLine().trim();
            
            if (hoursString.isEmpty()) {
                System.out.println("No hours entered. Returning to main menu.");
                return;
            }
            
            double hoursWorked = Double.parseDouble(hoursString);
            if (hoursWorked < 0) {
                System.out.println("Hours worked cannot be negative.");
                return;
            }
            
            // Overtime option is only applicable for part-time employees.
            if (employee instanceof PartTimeEmployee && hoursWorked > 8) {
                System.out.print("Is there any overtime? (y/n): ");
                String overtimeChoice = scanner.nextLine().trim().toLowerCase();
                if (overtimeChoice.equals("y") || overtimeChoice.equals("yes")) {
                    PartTimeEmployee ptEmployee = (PartTimeEmployee) employee;
                    ptEmployee.addOvertimeHours(hoursWorked - 8); // Assuming 8 hours is standard day
                }
            }
            
            double dailyPay = employee.calculatePay(hoursWorked);
            
            System.out.println("\n--- Daily Pay Report ---");
            System.out.println("Employee Name: " + employee.getName());
            System.out.println("Employee Type: " + employee.getEmployeeType());
            System.out.printf("Hours Worked: %.2f%n", hoursWorked);
            System.out.printf("Pay Rate: R%.2f/hr%n", employee.getPayRate());
            System.out.printf("Total Pay for the day: R%.2f%n", dailyPay);
            System.out.println("------------------------");
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number for ID and hours.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    
    // Method to calculate and display monthly salary for one or all employees.
    private static void calculateMonthlySalary() {
        System.out.print("Calculate for (1) single employee or (2) all staff? ");
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
            try {
                System.out.print("Enter employee ID (1-3): ");
                String idString = scanner.nextLine().trim();
                
                if (idString.isEmpty()) {
                    System.out.println("No ID entered. Returning to main menu.");
                    return;
                }
                
                int id = Integer.parseInt(idString);
                
                Employee employee = findEmployeeById(id);
                if (employee != null) {
                    double salary = employee.calculateMonthlySalary(STANDARD_WORKING_DAYS);
                    System.out.println("\n--- Monthly Salary Report ---");
                    System.out.println("Employee Name: " + employee.getName());
                    System.out.printf("Monthly Salary: R%.2f%n", salary);
                    System.out.println("-----------------------------");
                } else {
                    System.out.println("Employee not found. Available IDs: 1, 2, 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a valid number (1, 2, or 3).");
            }
        } else if (choice.equals("2")) {
            System.out.println("\n--- Monthly Salary Report for All Staff ---");
            for (Employee employee : staff) {
                double salary = employee.calculateMonthlySalary(STANDARD_WORKING_DAYS);
                System.out.printf("Name: %-15s | Monthly Salary: R%.2f%n", employee.getName(), salary);
            }
            System.out.println("-------------------------------------------");
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }
    
    // Method to estimate the salary over a six-month period.
    private static void estimateSixMonthSalary() {
        System.out.print("Estimate for (1) single employee or (2) all staff? ");
        String choice = scanner.nextLine().trim();
        
        if (choice.equals("1")) {
            try {
                System.out.print("Enter employee ID (1-3): ");
                String idString = scanner.nextLine().trim();
                
                if (idString.isEmpty()) {
                    System.out.println("No ID entered. Returning to main menu.");
                    return;
                }
                
                int id = Integer.parseInt(idString);
                
                Employee employee = findEmployeeById(id);
                if (employee != null) {
                    double sixMonthEstimate = employee.estimateSixMonthSalary(STANDARD_WORKING_DAYS);
                    System.out.println("\n--- 6-Month Salary Estimate ---");
                    System.out.println("Employee Name: " + employee.getName());
                    System.out.printf("6-Month Estimated Salary: R%.2f%n", sixMonthEstimate);
                    System.out.println("-------------------------------");
                } else {
                    System.out.println("Employee not found. Available IDs: 1, 2, 3");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID. Please enter a valid number (1, 2, or 3).");
            }
        } else if (choice.equals("2")) {
            System.out.println("\n--- 6-Month Salary Estimate for All Staff ---");
            for (Employee employee : staff) {
                double sixMonthEstimate = employee.estimateSixMonthSalary(STANDARD_WORKING_DAYS);
                System.out.printf("Name: %-15s | 6-Month Estimate: R%.2f%n", employee.getName(), sixMonthEstimate);
            }
            System.out.println("---------------------------------------------");
        } else {
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }
    
    // A helper method to find an employee by their ID.
    private static Employee findEmployeeById(int id) {
        for (Employee employee : staff) {
            if (employee.getEmployeeId() == id) {
                return employee;
            }
        }
        return null; // Return null if no employee is found.
    }
}

/**

References:

This program was developed with the assistance of an AI large language model.

[1] Google. (2025). Gemini AI Model.

[Generated on September 3, 2025].

[The AI model was used to generate the class hierarchy and methods for calculations.]

[2] Oracle. (2025). Java Platform, Standard Edition & Java Development Kit. API Specification, javax.swing.JOptionPane.

Retrieved from https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/JOptionPane.html

[3] Deitel, P. J., & Deitel, H. M. (2020). Java How to Program, Early Objects. Pearson Education.

[This textbook provides foundational knowledge on object-oriented programming in Java, including inheritance,

classes, and data structures used in this program.]
*/
