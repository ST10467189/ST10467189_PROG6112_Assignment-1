package com.mycompany.st10467189employeepayrollsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test class for the FullTimeEmployee class.
 * Tests inheritance from Employee and specific FullTimeEmployee functionality.
 */
@DisplayName("FullTimeEmployee Tests")
class FullTimeEmployeeTest {

    private FullTimeEmployee fullTimeEmployee;
    private static final String EMPLOYEE_NAME = "John Doe";
    private static final int EMPLOYEE_ID = 101;
    private static final double PAY_RATE = 25.0;

    @BeforeEach
    void setUp() {
        fullTimeEmployee = new FullTimeEmployee(EMPLOYEE_NAME, EMPLOYEE_ID, PAY_RATE);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create full-time employee with valid parameters")
        void shouldCreateFullTimeEmployeeWithValidParameters() {
            // Given & When
            FullTimeEmployee newEmployee = new FullTimeEmployee("Jane Smith", 102, 30.0);

            // Then
            assertNotNull(newEmployee);
            assertEquals("Jane Smith", newEmployee.getName());
            assertEquals(102, newEmployee.getEmployeeId());
            assertEquals(30.0, newEmployee.getPayRate());
            assertFalse(newEmployee.isClockedIn());
        }

        @Test
        @DisplayName("Should inherit validation from parent class")
        void shouldInheritValidationFromParentClass() {
            // When & Then
            assertThrows(IllegalArgumentException.class, 
                () -> new FullTimeEmployee(null, EMPLOYEE_ID, PAY_RATE));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new FullTimeEmployee(EMPLOYEE_NAME, -1, PAY_RATE));
            
            assertThrows(IllegalArgumentException.class, 
                () -> new FullTimeEmployee(EMPLOYEE_NAME, EMPLOYEE_ID, -1.0));
        }
    }

    @Nested
    @DisplayName("Inheritance Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Should be instance of Employee")
        void shouldBeInstanceOfEmployee() {
            assertTrue(fullTimeEmployee instanceof Employee);
        }

        @Test
        @DisplayName("Should inherit all Employee methods")
        void shouldInheritAllEmployeeMethods() {
            // Test inherited getters
            assertEquals(EMPLOYEE_NAME, fullTimeEmployee.getName());
            assertEquals(EMPLOYEE_ID, fullTimeEmployee.getEmployeeId());
            assertEquals(PAY_RATE, fullTimeEmployee.getPayRate());
            assertFalse(fullTimeEmployee.isClockedIn());

            // Test inherited clock in/out functionality
            fullTimeEmployee.clockIn();
            assertTrue(fullTimeEmployee.isClockedIn());
            
            fullTimeEmployee.clockOut();
            assertFalse(fullTimeEmployee.isClockedIn());

            // Test inherited salary calculations
            assertEquals(200.0, fullTimeEmployee.calculateMonthlySalary(1), 0.01);
            assertEquals(1200.0, fullTimeEmployee.estimateSixMonthSalary(1), 0.01);
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
            "10.0, 250.0",
            "12.5, 312.5"
        })
        @DisplayName("Should calculate pay at regular rate for all hours")
        void shouldCalculatePayAtRegularRateForAllHours(double hoursWorked, double expectedPay) {
            // When
            double actualPay = fullTimeEmployee.calculatePay(hoursWorked);

            // Then
            assertEquals(expectedPay, actualPay, 0.01);
        }

        @Test
        @DisplayName("Should use same calculation as parent class")
        void shouldUseSameCalculationAsParentClass() {
            // Given
            Employee parentEmployee = new Employee(EMPLOYEE_NAME, EMPLOYEE_ID, PAY_RATE);
            double hoursWorked = 8.5;

            // When
            double fullTimePay = fullTimeEmployee.calculatePay(hoursWorked);
            double parentPay = parentEmployee.calculatePay(hoursWorked);

            // Then
            assertEquals(parentPay, fullTimePay, 0.01);
        }

        @Test
        @DisplayName("Should throw exception when hours worked is negative")
        void shouldThrowExceptionWhenHoursWorkedIsNegative() {
            // When & Then
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fullTimeEmployee.calculatePay(-1.0)
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
            assertEquals("FullTimeEmployee", fullTimeEmployee.getEmployeeType());
        }
    }

    @Nested
    @DisplayName("Object Methods Tests")
    class ObjectMethodsTests {

        @Test
        @DisplayName("Should return correct string representation")
        void shouldReturnCorrectStringRepresentation() {
            // When
            String stringRepresentation = fullTimeEmployee.toString();

            // Then
            assertTrue(stringRepresentation.contains("Employee"));
            assertTrue(stringRepresentation.contains(EMPLOYEE_NAME));
            assertTrue(stringRepresentation.contains(String.valueOf(EMPLOYEE_ID)));
            assertTrue(stringRepresentation.contains(String.valueOf(PAY_RATE)));
        }

        @Test
        @DisplayName("Should be equal to another FullTimeEmployee with same ID")
        void shouldBeEqualToAnotherFullTimeEmployeeWithSameId() {
            // Given
            FullTimeEmployee anotherEmployee = new FullTimeEmployee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(fullTimeEmployee, anotherEmployee);
        }

        @Test
        @DisplayName("Should be equal to Employee with same ID")
        void shouldBeEqualToEmployeeWithSameId() {
            // Given
            Employee employee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(fullTimeEmployee, employee);
        }

        @Test
        @DisplayName("Should have same hash code as Employee with same ID")
        void shouldHaveSameHashCodeAsEmployeeWithSameId() {
            // Given
            Employee employee = new Employee("Different Name", EMPLOYEE_ID, 50.0);

            // When & Then
            assertEquals(fullTimeEmployee.hashCode(), employee.hashCode());
        }
    }

    @Nested
    @DisplayName("Monthly and Six Month Salary Tests")
    class SalaryTests {

        @Test
        @DisplayName("Should calculate monthly salary correctly")
        void shouldCalculateMonthlySalaryCorrectly() {
            // When
            double monthlySalary = fullTimeEmployee.calculateMonthlySalary(20);

            // Then
            assertEquals(4000.0, monthlySalary, 0.01); // 20 days * 8 hours * 25.0 rate
        }

        @Test
        @DisplayName("Should calculate six month salary correctly")
        void shouldCalculateSixMonthSalaryCorrectly() {
            // When
            double sixMonthSalary = fullTimeEmployee.estimateSixMonthSalary(20);

            // Then
            assertEquals(24000.0, sixMonthSalary, 0.01); // 6 * 4000.0
        }
    }
}
