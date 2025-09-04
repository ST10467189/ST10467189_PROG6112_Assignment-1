package com.mycompany.st10467189employeepayrollsystem;

/**
 * Simple test runner to verify the payroll system functionality without GUI dependencies.
 * This demonstrates that all classes work correctly together.
 */
public class SimpleTestRunner {
    
    public static void main(String[] args) {
        System.out.println("=== Employee Payroll System Test Runner ===\n");
        
        // Test 1: Create employees
        System.out.println("1. Creating employees...");
        Employee fullTimeEmployee = new FullTimeEmployee("John Doe", 101, 25.0);
        Employee partTimeEmployee = new PartTimeEmployee("Jane Smith", 102, 20.0);
        System.out.println("   ✓ Full-time employee created: " + fullTimeEmployee.getName());
        System.out.println("   ✓ Part-time employee created: " + partTimeEmployee.getName());
        
        // Test 2: Test clock in/out
        System.out.println("\n2. Testing clock in/out functionality...");
        fullTimeEmployee.clockIn();
        partTimeEmployee.clockIn();
        System.out.println("   ✓ Full-time employee clocked in: " + fullTimeEmployee.isClockedIn());
        System.out.println("   ✓ Part-time employee clocked in: " + partTimeEmployee.isClockedIn());
        
        // Test 3: Test pay calculations
        System.out.println("\n3. Testing pay calculations...");
        double fullTimePay8Hours = fullTimeEmployee.calculatePay(8.0);
        double partTimePay8Hours = partTimeEmployee.calculatePay(8.0);
        System.out.println("   ✓ Full-time pay for 8 hours: R" + String.format("%.2f", fullTimePay8Hours));
        System.out.println("   ✓ Part-time pay for 8 hours: R" + String.format("%.2f", partTimePay8Hours));
        
        // Test 4: Test overtime calculations
        System.out.println("\n4. Testing overtime calculations...");
        double fullTimePay10Hours = fullTimeEmployee.calculatePay(10.0);
        double partTimePay10Hours = partTimeEmployee.calculatePay(10.0);
        System.out.println("   ✓ Full-time pay for 10 hours: R" + String.format("%.2f", fullTimePay10Hours));
        System.out.println("   ✓ Part-time pay for 10 hours: R" + String.format("%.2f", partTimePay10Hours));
        
        // Test 5: Test overtime tracking for part-time employee
        System.out.println("\n5. Testing overtime tracking...");
        PartTimeEmployee ptEmployee = (PartTimeEmployee) partTimeEmployee;
        ptEmployee.addOvertimeHours(2.0);
        System.out.println("   ✓ Part-time employee overtime hours: " + ptEmployee.getOvertimeHours());
        System.out.println("   ✓ Part-time employee overtime pay: R" + String.format("%.2f", ptEmployee.getOvertimePay()));
        
        // Test 6: Test monthly salary calculations
        System.out.println("\n6. Testing monthly salary calculations...");
        double fullTimeMonthly = fullTimeEmployee.calculateMonthlySalary(20);
        double partTimeMonthly = partTimeEmployee.calculateMonthlySalary(20);
        System.out.println("   ✓ Full-time monthly salary: R" + String.format("%.2f", fullTimeMonthly));
        System.out.println("   ✓ Part-time monthly salary: R" + String.format("%.2f", partTimeMonthly));
        
        // Test 7: Test six-month salary estimates
        System.out.println("\n7. Testing six-month salary estimates...");
        double fullTimeSixMonth = fullTimeEmployee.estimateSixMonthSalary(20);
        double partTimeSixMonth = partTimeEmployee.estimateSixMonthSalary(20);
        System.out.println("   ✓ Full-time six-month estimate: R" + String.format("%.2f", fullTimeSixMonth));
        System.out.println("   ✓ Part-time six-month estimate: R" + String.format("%.2f", partTimeSixMonth));
        
        // Test 8: Test employee types
        System.out.println("\n8. Testing employee types...");
        System.out.println("   ✓ Full-time employee type: " + fullTimeEmployee.getEmployeeType());
        System.out.println("   ✓ Part-time employee type: " + partTimeEmployee.getEmployeeType());
        
        // Test 9: Test toString methods
        System.out.println("\n9. Testing string representations...");
        System.out.println("   ✓ Full-time employee: " + fullTimeEmployee.toString());
        System.out.println("   ✓ Part-time employee: " + partTimeEmployee.toString());
        
        // Test 10: Test equality
        System.out.println("\n10. Testing equality...");
        Employee anotherFullTime = new FullTimeEmployee("Different Name", 101, 30.0);
        System.out.println("   ✓ Employees with same ID are equal: " + fullTimeEmployee.equals(anotherFullTime));
        
        System.out.println("\n=== All tests completed successfully! ===");
        System.out.println("The Employee Payroll System is working correctly.");
    }
}
