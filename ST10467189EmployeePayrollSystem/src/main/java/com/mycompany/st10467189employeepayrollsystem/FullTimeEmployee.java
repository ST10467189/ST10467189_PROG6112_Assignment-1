package com.mycompany.st10467189employeepayrollsystem;

/**
 * Child class representing a Full-Time Employee in the payroll system.
 * Full-time employees work standard hours and receive regular pay rates.
 */
public class FullTimeEmployee extends Employee {
    
    /**
     * Constructor calling the superclass constructor.
     * 
     * @param name The employee's name
     * @param employeeId The unique employee ID
     * @param payRate The hourly pay rate
     */
    public FullTimeEmployee(String name, int employeeId, double payRate) {
        super(name, employeeId, payRate);
    }

    /**
     * Calculate daily pay for full-time employees.
     * Full-time employees get regular pay rate for all hours worked.
     * 
     * @param hoursWorked The number of hours worked
     * @return The calculated pay amount
     */
    @Override
    public double calculatePay(double hoursWorked) {
        return super.calculatePay(hoursWorked);
    }

    /**
     * Get the employee type
     * 
     * @return "FullTimeEmployee"
     */
    @Override
    public String getEmployeeType() {
        return "FullTimeEmployee";
    }
}
