package com.mycompany.st10467189employeepayrollsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test class for the ST10467189EmployeePayrollSystem.
 * Tests the main system functionality and integration between components.
 */
@DisplayName("Employee Payroll System Integration Tests")
class ST10467189EmployeePayrollSystemTest {

    private List<Employee> staff;
    private Employee fullTimeEmployee;
    private Employee partTimeEmployee;

    @BeforeEach
    void setUp() throws Exception {
        // Access the private static staff field using reflection
        Field staffField = ST10467189EmployeePayrollSystem.class.getDeclaredField("staff");
        staffField.setAccessible(true);
        Object staffObject = staffField.get(null);
        if (staffObject instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<Employee> staffList = (List<Employee>) staffObject;
            staff = staffList;
        } else {
            throw new IllegalStateException("Staff field is not a List");
        }
        
        // Clear the staff list for each test
        staff.clear();
        
        // Create test employees
        fullTimeEmployee = new FullTimeEmployee("John Doe", 101, 25.0);
        partTimeEmployee = new PartTimeEmployee("Jane Smith", 102, 20.0);
        
        // Add employees to staff
        staff.add(fullTimeEmployee);
        staff.add(partTimeEmployee);
    }

    @Nested
    @DisplayName("Staff Management Tests")
    class StaffManagementTests {

        @Test
        @DisplayName("Should initialize staff with employees")
        void shouldInitializeStaffWithEmployees() {
            // Then
            assertEquals(2, staff.size());
            assertTrue(staff.contains(fullTimeEmployee));
            assertTrue(staff.contains(partTimeEmployee));
        }

        @Test
        @DisplayName("Should find employee by ID")
        void shouldFindEmployeeById() throws Exception {
            // Given
            Method findEmployeeMethod = ST10467189EmployeePayrollSystem.class.getDeclaredMethod("findEmployeeById", int.class);
            findEmployeeMethod.setAccessible(true);

            // When
            Employee foundEmployee = (Employee) findEmployeeMethod.invoke(null, 101);

            // Then
            assertNotNull(foundEmployee);
            assertEquals(fullTimeEmployee, foundEmployee);
            assertEquals("John Doe", foundEmployee.getName());
        }

        @Test
        @DisplayName("Should return null when employee not found")
        void shouldReturnNullWhenEmployeeNotFound() throws Exception {
            // Given
            Method findEmployeeMethod = ST10467189EmployeePayrollSystem.class.getDeclaredMethod("findEmployeeById", int.class);
            findEmployeeMethod.setAccessible(true);

            // When
            Employee foundEmployee = (Employee) findEmployeeMethod.invoke(null, 999);

            // Then
            assertNull(foundEmployee);
        }

        @Test
        @DisplayName("Should handle multiple employees with different IDs")
        void shouldHandleMultipleEmployeesWithDifferentIds() throws Exception {
            // Given
            Employee thirdEmployee = new FullTimeEmployee("Bob Johnson", 103, 30.0);
            staff.add(thirdEmployee);
            
            Method findEmployeeMethod = ST10467189EmployeePayrollSystem.class.getDeclaredMethod("findEmployeeById", int.class);
            findEmployeeMethod.setAccessible(true);

            // When & Then
            Employee found1 = (Employee) findEmployeeMethod.invoke(null, 101);
            Employee found2 = (Employee) findEmployeeMethod.invoke(null, 102);
            Employee found3 = (Employee) findEmployeeMethod.invoke(null, 103);

            assertEquals(fullTimeEmployee, found1);
            assertEquals(partTimeEmployee, found2);
            assertEquals(thirdEmployee, found3);
        }
    }

    @Nested
    @DisplayName("Employee Type Integration Tests")
    class EmployeeTypeIntegrationTests {

        @Test
        @DisplayName("Should handle both full-time and part-time employees")
        void shouldHandleBothFullTimeAndPartTimeEmployees() {
            // Then
            assertTrue(fullTimeEmployee instanceof FullTimeEmployee);
            assertTrue(partTimeEmployee instanceof PartTimeEmployee);
            assertTrue(fullTimeEmployee instanceof Employee);
            assertTrue(partTimeEmployee instanceof Employee);
        }

        @Test
        @DisplayName("Should calculate different pay for different employee types")
        void shouldCalculateDifferentPayForDifferentEmployeeTypes() {
            // Given
            double hoursWorked = 10.0; // 8 regular + 2 overtime

            // When
            double fullTimePay = fullTimeEmployee.calculatePay(hoursWorked);
            double partTimePay = partTimeEmployee.calculatePay(hoursWorked);

            // Then
            // Full-time: 10 * 25 = 250 (no overtime)
            // Part-time: 8 * 20 + 2 * 30 = 160 + 60 = 220 (with overtime)
            assertEquals(250.0, fullTimePay, 0.01);
            assertEquals(220.0, partTimePay, 0.01);
        }

        @Test
        @DisplayName("Should return correct employee types")
        void shouldReturnCorrectEmployeeTypes() {
            // When & Then
            assertEquals("FullTimeEmployee", fullTimeEmployee.getEmployeeType());
            assertEquals("PartTimeEmployee", partTimeEmployee.getEmployeeType());
        }
    }

    @Nested
    @DisplayName("Pay Calculation Integration Tests")
    class PayCalculationIntegrationTests {

        @ParameterizedTest
        @CsvSource({
            "0.0, 0.0, 0.0",
            "8.0, 200.0, 160.0",
            "10.0, 250.0, 220.0",
            "12.0, 300.0, 280.0"
        })
        @DisplayName("Should calculate pay correctly for both employee types")
        void shouldCalculatePayCorrectlyForBothEmployeeTypes(double hours, double expectedFullTimePay, double expectedPartTimePay) {
            // When
            double actualFullTimePay = fullTimeEmployee.calculatePay(hours);
            double actualPartTimePay = partTimeEmployee.calculatePay(hours);

            // Then
            assertEquals(expectedFullTimePay, actualFullTimePay, 0.01);
            assertEquals(expectedPartTimePay, actualPartTimePay, 0.01);
        }

        @Test
        @DisplayName("Should calculate monthly salary for both employee types")
        void shouldCalculateMonthlySalaryForBothEmployeeTypes() {
            // Given
            int workingDays = 20;

            // When
            double fullTimeMonthlySalary = fullTimeEmployee.calculateMonthlySalary(workingDays);
            double partTimeMonthlySalary = partTimeEmployee.calculateMonthlySalary(workingDays);

            // Then
            // Full-time: 20 * 8 * 25 = 4000
            // Part-time: 20 * 8 * 20 = 3200
            assertEquals(4000.0, fullTimeMonthlySalary, 0.01);
            assertEquals(3200.0, partTimeMonthlySalary, 0.01);
        }

        @Test
        @DisplayName("Should calculate six month salary for both employee types")
        void shouldCalculateSixMonthSalaryForBothEmployeeTypes() {
            // Given
            int workingDays = 20;

            // When
            double fullTimeSixMonthSalary = fullTimeEmployee.estimateSixMonthSalary(workingDays);
            double partTimeSixMonthSalary = partTimeEmployee.estimateSixMonthSalary(workingDays);

            // Then
            // Full-time: 6 * 4000 = 24000
            // Part-time: 6 * 3200 = 19200
            assertEquals(24000.0, fullTimeSixMonthSalary, 0.01);
            assertEquals(19200.0, partTimeSixMonthSalary, 0.01);
        }
    }

    @Nested
    @DisplayName("Clock In/Out Integration Tests")
    class ClockInOutIntegrationTests {

        @Test
        @DisplayName("Should handle clock in/out for both employee types")
        void shouldHandleClockInOutForBothEmployeeTypes() {
            // Initially both should be clocked out
            assertFalse(fullTimeEmployee.isClockedIn());
            assertFalse(partTimeEmployee.isClockedIn());

            // Clock in both employees
            fullTimeEmployee.clockIn();
            partTimeEmployee.clockIn();

            // Both should be clocked in
            assertTrue(fullTimeEmployee.isClockedIn());
            assertTrue(partTimeEmployee.isClockedIn());

            // Clock out both employees
            fullTimeEmployee.clockOut();
            partTimeEmployee.clockOut();

            // Both should be clocked out
            assertFalse(fullTimeEmployee.isClockedIn());
            assertFalse(partTimeEmployee.isClockedIn());
        }

        @Test
        @DisplayName("Should maintain independent clock status for different employees")
        void shouldMaintainIndependentClockStatusForDifferentEmployees() {
            // Clock in only full-time employee
            fullTimeEmployee.clockIn();

            // Full-time should be in, part-time should be out
            assertTrue(fullTimeEmployee.isClockedIn());
            assertFalse(partTimeEmployee.isClockedIn());

            // Clock in part-time employee
            partTimeEmployee.clockIn();

            // Both should be in
            assertTrue(fullTimeEmployee.isClockedIn());
            assertTrue(partTimeEmployee.isClockedIn());

            // Clock out only full-time employee
            fullTimeEmployee.clockOut();

            // Full-time should be out, part-time should be in
            assertFalse(fullTimeEmployee.isClockedIn());
            assertTrue(partTimeEmployee.isClockedIn());
        }
    }

    @Nested
    @DisplayName("Overtime Integration Tests")
    class OvertimeIntegrationTests {

        @Test
        @DisplayName("Should handle overtime for part-time employees only")
        void shouldHandleOvertimeForPartTimeEmployeesOnly() {
            // Given
            PartTimeEmployee ptEmployee = (PartTimeEmployee) partTimeEmployee;

            // When
            ptEmployee.addOvertimeHours(5.0);

            // Then
            assertEquals(5.0, ptEmployee.getOvertimeHours(), 0.01);
            assertEquals(150.0, ptEmployee.getOvertimePay(), 0.01); // 5 * 20 * 1.5
        }

        @Test
        @DisplayName("Should not affect full-time employee overtime calculations")
        void shouldNotAffectFullTimeEmployeeOvertimeCalculations() {
            // Given
            double hoursWorked = 10.0;

            // When
            double fullTimePay = fullTimeEmployee.calculatePay(hoursWorked);

            // Then
            // Full-time should not get overtime pay
            assertEquals(250.0, fullTimePay, 0.01); // 10 * 25 = 250
        }

        @Test
        @DisplayName("Should calculate overtime pay correctly in daily pay")
        void shouldCalculateOvertimePayCorrectlyInDailyPay() {
            // Given
            PartTimeEmployee ptEmployee = (PartTimeEmployee) partTimeEmployee;
            double hoursWorked = 10.0; // 8 regular + 2 overtime

            // When
            double dailyPay = ptEmployee.calculatePay(hoursWorked);

            // Then
            // Regular: 8 * 20 = 160, Overtime: 2 * 30 = 60, Total: 220
            assertEquals(220.0, dailyPay, 0.01);
        }
    }

    @Nested
    @DisplayName("System Constants Tests")
    class SystemConstantsTests {

        @Test
        @DisplayName("Should use standard working days constant")
        void shouldUseStandardWorkingDaysConstant() throws Exception {
            // Given
            Field workingDaysField = ST10467189EmployeePayrollSystem.class.getDeclaredField("STANDARD_WORKING_DAYS");
            workingDaysField.setAccessible(true);
            int standardWorkingDays = (int) workingDaysField.get(null);

            // When
            double monthlySalary = fullTimeEmployee.calculateMonthlySalary(standardWorkingDays);

            // Then
            assertEquals(20, standardWorkingDays);
            assertEquals(4000.0, monthlySalary, 0.01); // 20 * 8 * 25
        }
    }

    @Nested
    @DisplayName("Error Handling Integration Tests")
    class ErrorHandlingIntegrationTests {

        @Test
        @DisplayName("Should handle invalid employee creation gracefully")
        void shouldHandleInvalidEmployeeCreationGracefully() {
            // When & Then
            assertThrows(IllegalArgumentException.class, 
                () -> new Employee(null, 999, 25.0));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new FullTimeEmployee("Test", -1, 25.0));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new PartTimeEmployee("Test", 999, -1.0));
        }

        @Test
        @DisplayName("Should handle invalid pay calculations gracefully")
        void shouldHandleInvalidPayCalculationsGracefully() {
            // When & Then
            assertThrows(IllegalArgumentException.class, 
                () -> fullTimeEmployee.calculatePay(-1.0));
            
            assertThrows(IllegalArgumentException.class, 
                () -> partTimeEmployee.calculatePay(-1.0));
            
            assertThrows(IllegalArgumentException.class, 
                () -> fullTimeEmployee.calculateMonthlySalary(-1));
        }

        @Test
        @DisplayName("Should handle invalid overtime operations gracefully")
        void shouldHandleInvalidOvertimeOperationsGracefully() {
            // Given
            PartTimeEmployee ptEmployee = (PartTimeEmployee) partTimeEmployee;

            // When & Then
            assertThrows(IllegalArgumentException.class, 
                () -> ptEmployee.addOvertimeHours(-1.0));
        }
    }
}
