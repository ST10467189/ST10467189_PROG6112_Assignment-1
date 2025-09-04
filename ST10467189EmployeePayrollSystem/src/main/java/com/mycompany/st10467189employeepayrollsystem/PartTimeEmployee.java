package com.mycompany.st10467189employeepayrollsystem;

import javax.swing.JOptionPane;

/**
 * Child class representing a Part-Time Employee in the payroll system.
 * Part-time employees can work overtime and receive overtime pay at 1.5x the regular rate.
 */
public class PartTimeEmployee extends Employee {
    
    private double overtimeHours;
    private static final double OVERTIME_RATE_MULTIPLIER = 1.5;
    private static final double STANDARD_DAILY_HOURS = 8.0;

    /**
     * Constructor calling the superclass constructor.
     * 
     * @param name The employee's name
     * @param employeeId The unique employee ID
     * @param payRate The hourly pay rate
     */
    public PartTimeEmployee(String name, int employeeId, double payRate) {
        super(name, employeeId, payRate);
        this.overtimeHours = 0;
    }
    
    /**
     * Add overtime hours to the employee's record
     * 
     * @param hours The number of overtime hours to add
     * @throws IllegalArgumentException if hours is negative
     */
    public void addOvertimeHours(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Overtime hours cannot be negative");
        }
        this.overtimeHours += hours;
        JOptionPane.showMessageDialog(null, 
            this.getName() + " has logged " + hours + " hours of overtime.", 
            "Overtime", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Calculate daily pay for part-time employees including overtime.
     * Regular hours (up to 8) are paid at normal rate.
     * Overtime hours (over 8) are paid at 1.5x the normal rate.
     * 
     * @param hoursWorked The number of hours worked
     * @return The calculated pay amount including overtime
     */
    @Override
    public double calculatePay(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative");
        }
        
        double regularHours = Math.min(hoursWorked, STANDARD_DAILY_HOURS);
        double overtimeHours = Math.max(0, hoursWorked - STANDARD_DAILY_HOURS);
        
        double regularPay = regularHours * getPayRate();
        double overtimePay = overtimeHours * (getPayRate() * OVERTIME_RATE_MULTIPLIER);
        
        return regularPay + overtimePay;
    }
    
    /**
     * Get the total overtime pay based on accumulated overtime hours
     * 
     * @return The total overtime pay
     */
    public double getOvertimePay() {
        return this.overtimeHours * (getPayRate() * OVERTIME_RATE_MULTIPLIER);
    }
    
    /**
     * Get the total accumulated overtime hours
     * 
     * @return The total overtime hours
     */
    public double getOvertimeHours() {
        return this.overtimeHours;
    }
    
    /**
     * Reset overtime hours to zero
     */
    public void resetOvertimeHours() {
        this.overtimeHours = 0;
    }

    /**
     * Get the employee type
     * 
     * @return "PartTimeEmployee"
     */
    @Override
    public String getEmployeeType() {
        return "PartTimeEmployee";
    }

    @Override
    public String toString() {
        return String.format("PartTimeEmployee{name='%s', id=%d, payRate=%.2f, clockedIn=%s, overtimeHours=%.2f}", 
                           getName(), getEmployeeId(), getPayRate(), isClockedIn(), overtimeHours);
    }
}
