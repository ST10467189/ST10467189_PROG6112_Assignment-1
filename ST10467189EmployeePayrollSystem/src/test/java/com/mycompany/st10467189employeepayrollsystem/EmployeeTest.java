package com.mycompany.st10467189employeepayrollsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for the Employee class.
 * Tests all functionality including constructors, getters, setters, and business logic.
 */
@DisplayName("Employee Tests")
class EmployeeTest {

    private Employee employee;
    private static final String EMPLOYEE_NAME = "John Doe";
    private static final int EMPLOYEE_ID = 101;
    private static final double PAY_RATE = 25.0;

    @BeforeEach
    void setUp() {
        employee = new Employee(EMPLOYEE_NAME, EMPLOYEE_ID, PAY_RATE);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create employee with valid parameters")
        void shouldCreateEmployeeWithValidParameters() {
            // Given & When
            Employee newEmployee = new Employee("Jane Smith", 102, 30.0);

            // Then
            assertNotNull(newEmployee);
            assertEquals("Jane Smith", newEmployee.getName());
            assertEquals(102, newEmployee.getEmployeeId());
            assertEquals(30.0, newEmployee.getPayRate());
            assertFalse(newEmployee.isClockedIn());
        }

        @Test
        @DisplayName("Should throw exception when name is null")
        void shouldThrowExceptionWhenNameIsNull() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(null, EMPLOYEE_ID, PAY_RATE)
            );
            assertEquals("Employee name cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when name is empty")
        void shouldThrowExceptionWhenNameIsEmpty() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("", EMPLOYEE_ID, PAY_RATE)
            );
            assertEquals("Employee name cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception when name is whitespace only")
        void shouldThrowExceptionWhenNameIsWhitespaceOnly() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee("   ", EMPLOYEE_ID, PAY_RATE)
            );
            assertEquals("Employee name cannot be null or empty", exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -100, 0, 101, 200, Integer.MIN_VALUE, Integer.MAX_VALUE})
        @DisplayName("Should throw exception when employee ID is not between 1-100")
        void shouldThrowExceptionWhenEmployeeIdIsNotBetween1And100(int invalidId) {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(EMPLOYEE_NAME, invalidId, PAY_RATE)
            );
            assertEquals("Employee ID must be a number between 1-100", exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.0, -100.0, -0.01})
        @DisplayName("Should throw exception when pay rate is negative")
        void shouldThrowExceptionWhenPayRateIsNegative(double negativePayRate) {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Employee(EMPLOYEE_NAME, EMPLOYEE_ID, negativePayRate)
            );
            assertEquals("Pay rate cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {

        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {
            assertEquals(EMPLOYEE_NAME, employee.getName());
        }

        @Test
        @DisplayName("Should return correct employee ID")
        void shouldReturnCorrectEmployeeId() {
            assertEquals(EMPLOYEE_ID, employee.getEmployeeId());
        }

        @Test
        @DisplayName("Should return correct pay rate")
        void shouldReturnCorrectPayRate() {
            assertEquals(PAY_RATE, employee.getPayRate());
        }

        @Test
        @DisplayName("Should return false for clocked in status initially")
        void shouldReturnFalseForClockedInStatusInitially() {
            assertFalse(employee.isClockedIn());
        }
    }

    @Nested
    @DisplayName("Clock In/Out Tests")
    class ClockInOutTests {

        @Test
        @DisplayName("Should clock in employee")
        void shouldClockInEmployee() {
            // When
            employee.clockIn();

            // Then
            assertTrue(employee.isClockedIn());
        }

        @Test
        @DisplayName("Should clock out employee")
        void shouldClockOutEmployee() {
            // Given
            employee.clockIn();
            assertTrue(employee.isClockedIn());

            // When
            employee.clockOut();

            // Then
            assertFalse(employee.isClockedIn());
        }

        @Test
        @DisplayName("Should allow multiple clock in/out cycles")
        void shouldAllowMultipleClockInOutCycles() {
            // Test multiple cycles
            for (int i = 0; i < 3; i++) {
                employee.clockIn();
                assertTrue(employee.isClockedIn());
                
                employee.clockOut();
                assertFalse(employee.isClockedIn());
            }
        }
    }

    @Nested
    @DisplayName("Pay Calculation Tests")
    class PayCalculationTests {

        @ParameterizedTest
        @CsvSource({
            "0.0, 0.0",
            "1.0, 25.0",
            "8.0, 200.0",
            "10.5, 262.5"
        })
        @DisplayName("Should calculate pay correctly for various hours")
        void shouldCalculatePayCorrectlyForVariousHours(double hoursWorked, double expectedPay) {
            // When
            double actualPay = employee.calculatePay(hoursWorked);

            // Then
            assertEquals(expectedPay, actualPay, 0.01);
        }

        @Test
        @DisplayName("Should throw exception when hours worked is negative")
        void shouldThrowExceptionWhenHoursWorkedIsNegative() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employee.calculatePay(-1.0)
            );
            assertEquals("Hours worked cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Monthly Salary Tests")
    class MonthlySalaryTests {

        @ParameterizedTest
        @CsvSource({
            "0, 0.0",
            "1, 200.0",
            "20, 4000.0",
            "22, 4400.0"
        })
        @DisplayName("Should calculate monthly salary correctly")
        void shouldCalculateMonthlySalaryCorrectly(int workingDays, double expectedSalary) {
            // When
            double actualSalary = employee.calculateMonthlySalary(workingDays);

            // Then
            assertEquals(expectedSalary, actualSalary, 0.01);
        }

        @Test
        @DisplayName("Should throw exception when working days is negative")
        void shouldThrowExceptionWhenWorkingDaysIsNegative() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> employee.calculateMonthlySalary(-1)
            );
            assertEquals("Working days cannot be negative", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Six Month Salary Tests")
    class SixMonthSalaryTests {

        @ParameterizedTest
        @CsvSource({
            "0, 0.0",
            "1, 1200.0",
            "20, 24000.0",
            "22, 26400.0"
        })
        @DisplayName("Should calculate six month salary correctly")
        void shouldCalculateSixMonthSalaryCorrectly(int workingDays, double expectedSalary) {
            // When
            double actualSalary = employee.estimateSixMonthSalary(workingDays);

            // Then
            assertEquals(expectedSalary, actualSalary, 0.01);
        }
    }

    @Nested
    @DisplayName("Employee Type Tests")
    class EmployeeTypeTests {

        @Test
        @DisplayName("Should return correct employee type")
        void shouldReturnCorrectEmployeeType() {
            assertEquals("Employee", employee.getEmployeeType());
        }
    }

    @Nested
    @DisplayName("Object Methods Tests")
    class ObjectMethodsTests {

        @Test
        @DisplayName("Should return correct string representation")
        void shouldReturnCorrectStringRepresentation() {
            // When
            String stringRepresentation = employee.toString();

            // Then
            assertTrue(stringRepresentation.contains("Employee"));
            assertTrue(stringRepresentation.contains(EMPLOYEE_NAME));
            assertTrue(stringRepresentation.contains(String.valueOf(EMPLOYEE_ID)));
            assertTrue(stringRepresentation.contains(String.valueOf(PAY_RATE)));
        }

        @Test
        @DisplayName("Should be equal to employee with same ID")
        void shouldBeEqualToEmployeeWithSameId() {
            // Given
            Employee anotherEmployee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(employee, anotherEmployee);
        }

        @Test
        @DisplayName("Should not be equal to employee with different ID")
        void shouldNotBeEqualToEmployeeWithDifferentId() {
            // Given
            Employee anotherEmployee = new Employee(EMPLOYEE_NAME, 999, PAY_RATE);

            // When & Then
            assertNotEquals(employee, anotherEmployee);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(employee, null);
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            assertNotEquals(employee, "Not an employee");
        }

        @Test
        @DisplayName("Should have same hash code for employees with same ID")
        void shouldHaveSameHashCodeForEmployeesWithSameId() {
            // Given
            Employee anotherEmployee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(employee.hashCode(), anotherEmployee.hashCode());
        }

        @Test
        @DisplayName("Should have different hash code for employees with different ID")
        void shouldHaveDifferentHashCodeForEmployeesWithDifferentId() {
            // Given
            Employee anotherEmployee = new Employee(EMPLOYEE_NAME, 999, PAY_RATE);

            // When & Then
            assertNotEquals(employee.hashCode(), anotherEmployee.hashCode());
        }
    }
}
