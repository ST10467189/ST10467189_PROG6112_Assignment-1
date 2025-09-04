package com.mycompany.st10467189movieseriesapplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Simple Test Runner for Movie Series Application
 * 
 */
public class SimpleTestRunner {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("=== MOVIE SERIES APPLICATION TEST SUITE ===");
        System.out.println("Running tests without external dependencies...\n");
        
        // Run all test suites
        runSeriesModelTests();
        runSeriesTests();
        runIntegrationTests();
        
        // Print summary
        printTestSummary();
    }
    
    private static void runSeriesModelTests() {
        System.out.println("--- SeriesModel Tests ---");
        
        // Test 1: Default constructor
        test("Default constructor initializes empty strings", () -> {
            SeriesModel model = new SeriesModel();
            assertTrue(model.getSeriesId().equals(""), "SeriesId should be empty");
            assertTrue(model.getSeriesName().equals(""), "SeriesName should be empty");
            assertTrue(model.getSeriesAge().equals(""), "SeriesAge should be empty");
            assertTrue(model.getSeriesNumberOfEpisodes().equals(""), "SeriesNumberOfEpisodes should be empty");
        });
        
        // Test 2: Parameterized constructor
        test("Parameterized constructor sets values correctly", () -> {
            SeriesModel model = new SeriesModel("S001", "Test Series", "14", "10");
            assertTrue(model.getSeriesId().equals("S001"), "SeriesId should be S001");
            assertTrue(model.getSeriesName().equals("Test Series"), "SeriesName should be Test Series");
            assertTrue(model.getSeriesAge().equals("14"), "SeriesAge should be 14");
            assertTrue(model.getSeriesNumberOfEpisodes().equals("10"), "SeriesNumberOfEpisodes should be 10");
        });
        
        // Test 3: Setter and getter methods
        test("Setter and getter methods work correctly", () -> {
            SeriesModel model = new SeriesModel();
            model.setSeriesId("S002");
            model.setSeriesName("Another Series");
            model.setSeriesAge("16");
            model.setSeriesNumberOfEpisodes("20");
            
            assertTrue(model.getSeriesId().equals("S002"), "SeriesId should be S002");
            assertTrue(model.getSeriesName().equals("Another Series"), "SeriesName should be Another Series");
            assertTrue(model.getSeriesAge().equals("16"), "SeriesAge should be 16");
            assertTrue(model.getSeriesNumberOfEpisodes().equals("20"), "SeriesNumberOfEpisodes should be 20");
        });
        
        // Test 4: Edge cases
        test("Edge cases handled correctly", () -> {
            SeriesModel model = new SeriesModel();
            model.setSeriesId(null);
            model.setSeriesName("");
            model.setSeriesAge("18");
            model.setSeriesNumberOfEpisodes("100");
            
            assertTrue(model.getSeriesId() == null, "SeriesId should be null");
            assertTrue(model.getSeriesName().equals(""), "SeriesName should be empty");
            assertTrue(model.getSeriesAge().equals("18"), "SeriesAge should be 18");
            assertTrue(model.getSeriesNumberOfEpisodes().equals("100"), "SeriesNumberOfEpisodes should be 100");
        });
    }
    
    private static void runSeriesTests() {
        System.out.println("\n--- Series Class Tests ---");
        
        // Test 1: Capture series with valid data
        test("Capture series with valid data", () -> {
            Series series = new Series();
            String input = "S001\nBreaking Bad\n16\n62\n";
            setSystemInput(input);
            ByteArrayOutputStream output = captureSystemOutput();
            
            series.captureSeries();
            
            String outputStr = output.toString();
            assertTrue(outputStr.contains("Series processed successfully!!!"), "Should show success message");
            assertTrue(outputStr.contains("Enter the series ID:"), "Should prompt for ID");
            assertTrue(outputStr.contains("Enter the series name:"), "Should prompt for name");
            assertTrue(outputStr.contains("Enter the series age restriction (2-18):"), "Should prompt for age");
            assertTrue(outputStr.contains("Enter the number of episodes for Breaking Bad:"), "Should prompt for episodes");
            
            restoreSystemOutput();
        });
        
        // Test 2: Search series found
        test("Search series found", () -> {
            Series series = new Series();
            
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            setSystemInput(captureInput);
            series.captureSeries();
            
            // Then search for it
            String searchInput = "S001\n";
            setSystemInput(searchInput);
            ByteArrayOutputStream output = captureSystemOutput();
            
            series.searchSeries();
            
            String outputStr = output.toString();
            assertTrue(outputStr.contains("SERIES ID: S001"), "Should show series ID");
            assertTrue(outputStr.contains("SERIES NAME: Breaking Bad"), "Should show series name");
            assertTrue(outputStr.contains("SERIES AGE RESTRICTION: 16"), "Should show age restriction");
            assertTrue(outputStr.contains("NUMBER OF EPISODES: 62"), "Should show number of episodes");
            
            restoreSystemOutput();
        });
        
        // Test 3: Search series not found
        test("Search series not found", () -> {
            Series series = new Series();
            String input = "NONEXISTENT\n";
            setSystemInput(input);
            ByteArrayOutputStream output = captureSystemOutput();
            
            series.searchSeries();
            
            String outputStr = output.toString();
            assertTrue(outputStr.contains("Series with Series ID: NONEXISTENT was not found!"), "Should show not found message");
            
            restoreSystemOutput();
        });
        
        // Test 4: Age validation
        test("Age validation works correctly", () -> {
            Series series = new Series();
            String input = "S001\nTest Series\n25\n14\n10\n"; // Invalid age then valid
            setSystemInput(input);
            ByteArrayOutputStream output = captureSystemOutput();
            
            series.captureSeries();
            
            String outputStr = output.toString();
            assertTrue(outputStr.contains("Series processed successfully!!!"), "Should eventually succeed");
            assertTrue(outputStr.contains("You have entered an incorrect age restriction: 25"), "Should show invalid age message");
            assertTrue(outputStr.contains("Please re-enter the series age (2-18):"), "Should prompt for re-entry");
            
            restoreSystemOutput();
        });
    }
    
    private static void runIntegrationTests() {
        System.out.println("\n--- Integration Tests ---");
        
        // Test 1: Complete CRUD cycle
        test("Complete CRUD cycle", () -> {
            Series series = new Series();
            
            // CREATE
            String createInput = "S001\nBreaking Bad\n16\n62\n";
            setSystemInput(createInput);
            series.captureSeries();
            
            // READ
            String searchInput = "S001\n";
            setSystemInput(searchInput);
            ByteArrayOutputStream searchOutput = captureSystemOutput();
            series.searchSeries();
            String searchStr = searchOutput.toString();
            assertTrue(searchStr.contains("SERIES ID: S001"), "Should find created series");
            restoreSystemOutput();
            
            // UPDATE
            String updateInput = "S001\nBetter Call Saul\n18\n50\n";
            setSystemInput(updateInput);
            ByteArrayOutputStream updateOutput = captureSystemOutput();
            series.updateSeries();
            String updateStr = updateOutput.toString();
            assertTrue(updateStr.contains("Series updated successfully!"), "Should update successfully");
            restoreSystemOutput();
            
            // Verify update
            String verifyInput = "S001\n";
            setSystemInput(verifyInput);
            ByteArrayOutputStream verifyOutput = captureSystemOutput();
            series.searchSeries();
            String verifyStr = verifyOutput.toString();
            assertTrue(verifyStr.contains("SERIES NAME: Better Call Saul"), "Should show updated name");
            assertTrue(verifyStr.contains("SERIES AGE RESTRICTION: 18"), "Should show updated age");
            restoreSystemOutput();
            
            // DELETE
            String deleteInput = "S001\ny\n";
            setSystemInput(deleteInput);
            ByteArrayOutputStream deleteOutput = captureSystemOutput();
            series.deleteSeries();
            String deleteStr = deleteOutput.toString();
            assertTrue(deleteStr.contains("Series with Series ID: S001 WAS deleted!"), "Should delete successfully");
            restoreSystemOutput();
            
            // Verify deletion
            String verifyDeleteInput = "S001\n";
            setSystemInput(verifyDeleteInput);
            ByteArrayOutputStream verifyDeleteOutput = captureSystemOutput();
            series.searchSeries();
            String verifyDeleteStr = verifyDeleteOutput.toString();
            assertTrue(verifyDeleteStr.contains("Series with Series ID: S001 was not found!"), "Should not find deleted series");
            restoreSystemOutput();
        });
        
        // Test 2: Multiple series management
        test("Multiple series management", () -> {
            Series series = new Series();
            
            // Add first series
            String input1 = "S001\nBreaking Bad\n16\n62\n";
            setSystemInput(input1);
            series.captureSeries();
            
            // Add second series
            String input2 = "S002\nGame of Thrones\n18\n73\n";
            setSystemInput(input2);
            series.captureSeries();
            
            // Generate report
            ByteArrayOutputStream reportOutput = captureSystemOutput();
            series.seriesReport();
            String reportStr = reportOutput.toString();
            assertTrue(reportStr.contains("Series 1"), "Should show first series");
            assertTrue(reportStr.contains("Series 2"), "Should show second series");
            assertTrue(reportStr.contains("SERIES ID: S001"), "Should show first series ID");
            assertTrue(reportStr.contains("SERIES ID: S002"), "Should show second series ID");
            restoreSystemOutput();
        });
    }
    
    // Test utility methods
    private static void test(String testName, Runnable testFunction) {
        totalTests++;
        try {
            testFunction.run();
            System.out.println("✓ PASS: " + testName);
            passedTests++;
        } catch (Exception e) {
            System.out.println("✗ FAIL: " + testName);
            System.out.println("  Error: " + e.getMessage());
            failedTests++;
        }
    }
    
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    
    private static void setSystemInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
    
    private static ByteArrayOutputStream captureSystemOutput() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        return output;
    }
    
    private static void restoreSystemOutput() {
        System.setOut(System.out);
    }
    
    private static void printTestSummary() {
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? (passedTests * 100 / totalTests) : 0) + "%");
        
        if (failedTests == 0) {
            System.out.println("\n🎉 ALL TESTS PASSED! 🎉");
        } else {
            System.out.println("\n❌ " + failedTests + " TEST(S) FAILED ❌");
        }
    }
}
