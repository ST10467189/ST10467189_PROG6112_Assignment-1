package com.mycompany.st10467189employeepayrollsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for the PartTimeEmployee class.
 * Tests inheritance from Employee and specific PartTimeEmployee functionality including overtime.
 */
@DisplayName("PartTimeEmployee Tests")
class PartTimeEmployeeTest {

    private PartTimeEmployee partTimeEmployee;
    private static final String EMPLOYEE_NAME = "Jane Smith";
    private static final int EMPLOYEE_ID = 102;
    private static final double PAY_RATE = 20.0;
    private static final double OVERTIME_RATE_MULTIPLIER = 1.5;
    private static final double STANDARD_DAILY_HOURS = 8.0;

    @BeforeEach
    void setUp() {
        partTimeEmployee = new PartTimeEmployee(EMPLOYEE_NAME, EMPLOYEE_ID, PAY_RATE);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create part-time employee with valid parameters")
        void shouldCreatePartTimeEmployeeWithValidParameters() {
            // Given & When
            PartTimeEmployee newEmployee = new PartTimeEmployee("John Doe", 103, 25.0);

            // Then
            assertNotNull(newEmployee);
            assertEquals("John Doe", newEmployee.getName());
            assertEquals(103, newEmployee.getEmployeeId());
            assertEquals(25.0, newEmployee.getPayRate());
            assertFalse(newEmployee.isClockedIn());
            assertEquals(0.0, newEmployee.getOvertimeHours());
        }

        @Test
        @DisplayName("Should inherit validation from parent class")
        void shouldInheritValidationFromParentClass() {
            // When & Then
            assertThrows(IllegalArgumentException.class, 
                () -> new PartTimeEmployee(null, EMPLOYEE_ID, PAY_RATE));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new PartTimeEmployee(EMPLOYEE_NAME, -1, PAY_RATE));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new PartTimeEmployee(EMPLOYEE_NAME, EMPLOYEE_ID, -1.0));
        }
    }

    @Nested
    @DisplayName("Inheritance Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Should be instance of Employee")
        void shouldBeInstanceOfEmployee() {
            assertTrue(partTimeEmployee instanceof Employee);
        }

        @Test
        @DisplayName("Should inherit all Employee methods")
        void shouldInheritAllEmployeeMethods() {
            // Test inherited getters
            assertEquals(EMPLOYEE_NAME, partTimeEmployee.getName());
            assertEquals(EMPLOYEE_ID, partTimeEmployee.getEmployeeId());
            assertEquals(PAY_RATE, partTimeEmployee.getPayRate());
            assertFalse(partTimeEmployee.isClockedIn());

            // Test inherited clock in/out functionality
            partTimeEmployee.clockIn();
            assertTrue(partTimeEmployee.isClockedIn());
            
            partTimeEmployee.clockOut();
            assertFalse(partTimeEmployee.isClockedIn());

            // Test inherited salary calculations
            assertEquals(160.0, partTimeEmployee.calculateMonthlySalary(1), 0.01);
            assertEquals(960.0, partTimeEmployee.estimateSixMonthSalary(1), 0.01);
        }
    }

    @Nested
    @DisplayName("Overtime Hours Tests")
    class OvertimeHoursTests {

        @Test
        @DisplayName("Should start with zero overtime hours")
        void shouldStartWithZeroOvertimeHours() {
            assertEquals(0.0, partTimeEmployee.getOvertimeHours());
        }

        @ParameterizedTest
        @ValueSource(doubles = {1.0, 2.5, 5.0, 10.0})
        @DisplayName("Should add overtime hours correctly")
        void shouldAddOvertimeHoursCorrectly(double overtimeHours) {
            // When
            partTimeEmployee.addOvertimeHours(overtimeHours);

            // Then
            assertEquals(overtimeHours, partTimeEmployee.getOvertimeHours(), 0.01);
        }

        @Test
        @DisplayName("Should accumulate overtime hours")
        void shouldAccumulateOvertimeHours() {
            // When
            partTimeEmployee.addOvertimeHours(2.0);
            partTimeEmployee.addOvertimeHours(3.0);
            partTimeEmployee.addOvertimeHours(1.5);

            // Then
            assertEquals(6.5, partTimeEmployee.getOvertimeHours(), 0.01);
        }

        @Test
        @DisplayName("Should throw exception when adding negative overtime hours")
        void shouldThrowExceptionWhenAddingNegativeOvertimeHours() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> partTimeEmployee.addOvertimeHours(-1.0)
            );
            assertEquals("Overtime hours cannot be negative", exception.getMessage());
        }

        @Test
        @DisplayName("Should reset overtime hours to zero")
        void shouldResetOvertimeHoursToZero() {
            // Given
            partTimeEmployee.addOvertimeHours(5.0);
            assertEquals(5.0, partTimeEmployee.getOvertimeHours());

            // When
            partTimeEmployee.resetOvertimeHours();

            // Then
            assertEquals(0.0, partTimeEmployee.getOvertimeHours());
        }
    }

    @Nested
    @DisplayName("Overtime Pay Tests")
    class OvertimePayTests {

        @Test
        @DisplayName("Should calculate overtime pay correctly")
        void shouldCalculateOvertimePayCorrectly() {
            // Given
            partTimeEmployee.addOvertimeHours(4.0);
            double expectedOvertimePay = 4.0 * PAY_RATE * OVERTIME_RATE_MULTIPLIER; // 4 * 20 * 1.5 = 120

            // When
            double actualOvertimePay = partTimeEmployee.getOvertimePay();

            // Then
            assertEquals(expectedOvertimePay, actualOvertimePay, 0.01);
        }

        @Test
        @DisplayName("Should return zero overtime pay when no overtime hours")
        void shouldReturnZeroOvertimePayWhenNoOvertimeHours() {
            // When
            double overtimePay = partTimeEmployee.getOvertimePay();

            // Then
            assertEquals(0.0, overtimePay, 0.01);
        }
    }

    @Nested
    @DisplayName("Pay Calculation Tests")
    class PayCalculationTests {

        @ParameterizedTest
        @CsvSource({
            "0.0, 0.0",
            "1.0, 20.0",
            "8.0, 160.0",
            "9.0, 190.0",  // STANDARD_DAILY_HOURS * 20 + 1 * 30 = 160 + 30 = 190
            "10.0, 220.0", // STANDARD_DAILY_HOURS * 20 + 2 * 30 = 160 + 60 = 220
            "12.5, 295.0"  // STANDARD_DAILY_HOURS * 20 + 4.5 * 30 = 160 + 135 = 295
        })
        @DisplayName("Should calculate pay with overtime correctly")
        void shouldCalculatePayWithOvertimeCorrectly(double hoursWorked, double expectedPay) {
            // When
            double actualPay = partTimeEmployee.calculatePay(hoursWorked);

            // Then
            assertEquals(expectedPay, actualPay, 0.01);
        }

        @Test
        @DisplayName("Should calculate regular pay for hours up to 8")
        void shouldCalculateRegularPayForHoursUpTo8() {
            // When
            double payFor8Hours = partTimeEmployee.calculatePay(STANDARD_DAILY_HOURS);

            // Then
            assertEquals(160.0, payFor8Hours, 0.01); // STANDARD_DAILY_HOURS * 20 = 160
        }

        @Test
        @DisplayName("Should calculate overtime pay for hours over 8")
        void shouldCalculateOvertimePayForHoursOver8() {
            // When
            double payFor10Hours = partTimeEmployee.calculatePay(10.0);

            // Then
            // Regular: STANDARD_DAILY_HOURS * 20 = 160, Overtime: 2 * 30 = 60, Total: 220
            assertEquals(220.0, payFor10Hours, 0.01);
        }

        @Test
        @DisplayName("Should throw exception when hours worked is negative")
        void shouldThrowExceptionWhenHoursWorkedIsNegative() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> partTimeEmployee.calculatePay(-1.0)
            );
            assertEquals("Hours worked cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Employee Type Tests")
    class EmployeeTypeTests {

        @Test
        @DisplayName("Should return correct employee type")
        void shouldReturnCorrectEmployeeType() {
            assertEquals("PartTimeEmployee", partTimeEmployee.getEmployeeType());
        }
    }

    @Nested
    @DisplayName("Object Methods Tests")
    class ObjectMethodsTests {

        @Test
        @DisplayName("Should return correct string representation")
        void shouldReturnCorrectStringRepresentation() {
            // Given
            partTimeEmployee.addOvertimeHours(2.5);

            // When
            String stringRepresentation = partTimeEmployee.toString();

            // Then
            assertTrue(stringRepresentation.contains("PartTimeEmployee"));
            assertTrue(stringRepresentation.contains(EMPLOYEE_NAME));
            assertTrue(stringRepresentation.contains(String.valueOf(EMPLOYEE_ID)));
            assertTrue(stringRepresentation.contains(String.valueOf(PAY_RATE)));
            assertTrue(stringRepresentation.contains("2.5")); // overtime hours
        }

        @Test
        @DisplayName("Should be equal to another PartTimeEmployee with same ID")
        void shouldBeEqualToAnotherPartTimeEmployeeWithSameId() {
            // Given
            PartTimeEmployee anotherEmployee = new PartTimeEmployee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(partTimeEmployee, anotherEmployee);
        }

        @Test
        @DisplayName("Should be equal to Employee with same ID")
        void shouldBeEqualToEmployeeWithSameId() {
            // Given
            Employee employee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(partTimeEmployee, employee);
        }

        @Test
        @DisplayName("Should have same hash code as Employee with same ID")
        void shouldHaveSameHashCodeAsEmployeeWithSameId() {
            // Given
            Employee employee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(partTimeEmployee.hashCode(), employee.hashCode());
        }
    }

    @Nested
    @DisplayName("Monthly and Six Month Salary Tests")
    class SalaryTests {

        @Test
        @DisplayName("Should calculate monthly salary correctly")
        void shouldCalculateMonthlySalaryCorrectly() {
            // When
            double monthlySalary = partTimeEmployee.calculateMonthlySalary(20);

            // Then
            assertEquals(3200.0, monthlySalary, 0.01); // 20 days * STANDARD_DAILY_HOURS * 20.0 rate
        }

        @Test
        @DisplayName("Should calculate six month salary correctly")
        void shouldCalculateSixMonthSalaryCorrectly() {
            // When
            double sixMonthSalary = partTimeEmployee.estimateSixMonthSalary(20);

            // Then
            assertEquals(19200.0, sixMonthSalary, 0.01); // 6 * 3200.0
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle exactly 8 hours worked")
        void shouldHandleExactly8HoursWorked() {
            // When
            double pay = partTimeEmployee.calculatePay(STANDARD_DAILY_HOURS);

            // Then
            assertEquals(160.0, pay, 0.01); // No overtime
        }

        @Test
        @DisplayName("Should handle zero hours worked")
        void shouldHandleZeroHoursWorked() {
            // When
            double pay = partTimeEmployee.calculatePay(0.0);

            // Then
            assertEquals(0.0, pay, 0.01);
        }

        @Test
        @DisplayName("Should handle fractional overtime hours")
        void shouldHandleFractionalOvertimeHours() {
            // When
            double pay = partTimeEmployee.calculatePay(STANDARD_DAILY_HOURS + 0.5);

            // Then
            // Regular: STANDARD_DAILY_HOURS * 20 = 160, Overtime: 0.5 * 30 = 15, Total: 175
            assertEquals(175.0, pay, 0.01);
        }
    }
}
