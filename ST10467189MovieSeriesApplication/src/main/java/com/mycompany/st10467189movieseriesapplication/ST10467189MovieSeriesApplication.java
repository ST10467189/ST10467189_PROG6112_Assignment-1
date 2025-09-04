
package com.mycompany.st10467189movieseriesapplication;

/**
 *
 * @author ST10467189 Leonard McDermott
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

/**
 * Reference List (APA 7th Edition)
 * Date Retrieved: 31 August 2025
 *
 * ChatGPT. (2025, January 15). JUnit 5 test suite development and enhancement. 
 * OpenAI. Retrieved August 31, 2025, from https://github.com/junit-team/junit5
 *
 * JUnit Team. (2025). JUnit 5 user guide. 
 * Retrieved August 31, 2025, from https://junit.org/junit5/docs/current/user-guide/
 *
 * JUnit Team. (2025). JUnit 5 API documentation. 
 * Retrieved August 31, 2025, from https://junit.org/junit5/docs/current/api/
 *
 * Maven Central Repository. (2025). JUnit Jupiter dependencies. 
 * Retrieved August 31, 2025, from https://mvnrepository.com/artifact/org.junit.jupiter
 *
 * Oracle Corporation. (2025). Java documentation – Collections framework. 
 * Retrieved August 31, 2025, from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/ArrayList.html
 *
 * Oracle Corporation. (2025). Java documentation – Scanner class. 
 * Retrieved August 31, 2025, from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html
 *
 * Oracle Corporation. (2025). Java documentation – Input/Output streams. 
 * Retrieved August 31, 2025, from https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/package-summary.html
 */
    

