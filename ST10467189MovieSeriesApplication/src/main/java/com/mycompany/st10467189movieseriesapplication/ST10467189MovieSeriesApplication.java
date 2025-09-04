
package com.mycompany.st10467189movieseriesapplication;

/**
 * Movie Series Application - Main Class
 * 
 * This application provides a comprehensive movie series management system
 * with full CRUD (Create, Read, Update, Delete) operations.
 * 
 * Features:
 * - Capture new series with validation
 * - Search for existing series
 * - Update series information
 * - Delete series with confirmation
 * - Generate comprehensive reports
 * - Age restriction validation (2-18)
 * - Input validation and error handling
 * 
 * @author ST10467189 Leonard McDermott
 * 
 * // REFERENCE LIST //
 * 
 * References
 * 
 * AI Assistant Collaboration. (2025, January 15). JUnit 5 test suite development and enhancement. 
 * Retrieved from https://github.com/junit-team/junit5
 * 
 * JUnit Team. (2025). JUnit 5 User Guide. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/user-guide/
 * 
 * JUnit Team. (2025). JUnit 5 API Documentation. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/api/
 * 
 * Maven Central Repository. (2025). JUnit Jupiter Dependencies. Retrieved January 15, 2025, from 
 * https://mvnrepository.com/artifact/org.junit.jupiter
 * 
 * Oracle Corporation. (2025). Java Documentation - Collections Framework. Retrieved January 15, 2025, from 
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 * 
 * Oracle Corporation. (2025). Java Documentation - Scanner Class. Retrieved January 15, 2025, from 
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html
 * 
 * Oracle Corporation. (2025). Java Documentation - Input/Output Streams. Retrieved January 15, 2025, from 
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/package-summary.html
 * 
 * Test Implementation Details:
 * 
 * SeriesModelTest - Unit tests for SeriesModel class (2025, January 15)
 * - Constructor testing (default and parameterized)
 * - Getter and setter method validation
 * - Edge case handling (null values, empty strings, special characters)
 * - Object state consistency testing
 * 
 * SeriesTest - Unit tests for Series class (2025, January 15)
 * - Capture series functionality with age validation
 * - Search series operations
 * - Update series with validation
 * - Delete series with confirmation
 * - Series report generation
 * - Input validation and error handling
 * 
 * IntegrationTest - Integration tests for complete workflows (2025, January 15)
 * - Full CRUD operation cycles
 * - Data persistence within sessions
 * - Multiple series management
 * - Error handling and recovery scenarios
 * - Age validation integration testing
 * 
 * ST10467189MovieSeriesApplicationTest - Main application tests (2025, January 15)
 * - Application entry point testing
 * - Menu navigation testing
 * - Error handling for invalid inputs
 * - Complete application workflow testing
 * 
 * SimpleTestRunner - Custom test runner for basic testing (2025, January 15)
 * - Alternative testing approach without external dependencies
 * - Manual test execution and reporting
 * 
 * Loop Implementations (2025, January 15):
 * - While loops for age validation and input retry mechanisms
 * - For-each loops for iterating through series collections
 * - Enhanced for loops for processing multiple test data sets
 * - Do-while loops for menu navigation and user interaction
 * 
 * // END REFERENCE LIST //
 */
import java.util.Scanner;

public class ST10467189MovieSeriesApplication {

    public static void main(String[] args) {

        Series seriesApp = new Series();
        Scanner mainScanner = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.println("LATEST SERIES - 2026");
            System.out.println("***********************************");
            System.out.println("Enter (1) to launch menu or any other key to exit");
            userInput = mainScanner.nextLine();

            if (!userInput.equals("1")) {
                break;
            }

            // Main menu loop
            while (true) {
                System.out.println("Please select one of the following menu items:");
                System.out.println("(1) Capture a new series.");
                System.out.println("(2) Search for a series.");
                System.out.println("(3) Update series age restriction");
                System.out.println("(4) Delete a series.");
                System.out.println("(5) Print series report - 2025");
                System.out.println("(6) Exit Application.");
                System.out.println("Enter your choice (1-6): ");

                String choice = mainScanner.nextLine();

                switch (choice) {
                    case "1":
                        seriesApp.captureSeries();
                        break;
                    case "2":
                        seriesApp.searchSeries();
                        break;
                    case "3":
                        seriesApp.updateSeries();
                        break;
                    case "4":
                        seriesApp.deleteSeries();
                        break;
                    case "5":
                        seriesApp.seriesReport();
                        break;
                    case "6":
                        seriesApp.exitSeriesApplication();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }

                System.out.println("Enter (1) to continue with menu or any other key to exit");
                String continueInput = mainScanner.nextLine();
                if (!continueInput.equals("1")) {
                    break;
                }
            }
        }
        
        // Clean up resources
        mainScanner.close();
        seriesApp.closeScanner();
        System.out.println("Thank you for using the Movie Series Application!");
    }
}
    

