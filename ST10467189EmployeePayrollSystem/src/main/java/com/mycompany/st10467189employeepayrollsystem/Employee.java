package com.mycompany.st10467189employeepayrollsystem;

/**
 * Parent class representing a generic Employee in the payroll system.
 * This class provides the basic structure and common functionality for all employee types.
 */
public class Employee {
    // Fields with information hiding (private access modifier)
    private String name;
    private int employeeId;
    private double payRate;
    private boolean isClockedIn;

    /**
     * Constructor to initialize an Employee object.
     * 
     * @param name The employee's name
     * @param employeeId The unique employee ID
     * @param payRate The hourly pay rate
     * @throws IllegalArgumentException if name is null/empty, employeeId is negative, or payRate is negative
     */
    public Employee(String name, int employeeId, double payRate) {
        validateInputs(name, employeeId, payRate);
        this.name = name;
        this.employeeId = employeeId;
        this.payRate = payRate;
        this.isClockedIn = false;
    }

    /**
     * Validates constructor inputs
     */
    private void validateInputs(String name, int employeeId, double payRate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (employeeId < 1 || employeeId > 100) {
            throw new IllegalArgumentException("Employee ID must be a number between 1-100");
        }
        if (payRate < 0) {
            throw new IllegalArgumentException("Pay rate cannot be negative");
        }
    }

    // Getters to access the private fields
    public String getName() { 
        return name; 
    }
    
    public int getEmployeeId() { 
        return employeeId; 
    }
    
    public double getPayRate() { 
        return payRate; 
    }
    
    public boolean isClockedIn() { 
        return isClockedIn; 
    }

    /**
     * Clock in the employee
     */
    public void clockIn() {
        this.isClockedIn = true;
    }

    /**
     * Clock out the employee
     */
    public void clockOut() {
        this.isClockedIn = false;
    }
    
    /**
     * Calculate daily pay based on hours worked
     * 
     * @param hoursWorked The number of hours worked
     * @return The calculated pay amount
     * @throws IllegalArgumentException if hoursWorked is negative
     */
    public double calculatePay(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative");
        }
        return hoursWorked * payRate;
    }
    
    /**
     * Calculate monthly salary based on working days
     * 
     * @param workingDays The number of working days in the month
     * @return The calculated monthly salary
     * @throws IllegalArgumentException if workingDays is negative
     */
    public double calculateMonthlySalary(int workingDays) {
        if (workingDays < 0) {
            throw new IllegalArgumentException("Working days cannot be negative");
        }
        // Assuming a standard 8-hour workday
        return calculatePay(workingDays * 8);
    }
    
    /**
     * Estimate six-month salary
     * 
     * @param workingDays The number of working days per month
     * @return The estimated six-month salary
     */
    public double estimateSixMonthSalary(int workingDays) {
        return calculateMonthlySalary(workingDays) * 6;
    }

    /**
     * Get employee type as a string
     * 
     * @return The employee type
     */
    public String getEmployeeType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', id=%d, payRate=%.2f, clockedIn=%s, type=%s}", 
                           name, employeeId, payRate, isClockedIn, getEmployeeType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return employeeId == employee.employeeId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(employeeId);
    }
}
