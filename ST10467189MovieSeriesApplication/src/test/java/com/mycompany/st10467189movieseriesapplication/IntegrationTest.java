package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

public class IntegrationTest {
    
    private Series series;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    
    @BeforeEach
    void setUp() {
        series = new Series();
        System.setOut(new PrintStream(outputStream));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outputStream.reset();
    }
    
    @Test
    void testCompleteCRUDCycle() {
        // Test complete Create, Read, Update, Delete cycle
        
        // 1. CREATE - Add multiple series
        String createInput1 = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(createInput1.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        String createInput2 = "S002\nGame of Thrones\n18\n73\n";
        System.setIn(new ByteArrayInputStream(createInput2.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // 2. READ - Search for series
        String searchInput = "S001\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        series.searchSeries();
        
        String searchOutput = outputStream.toString();
        assertTrue(searchOutput.contains("SERIES ID: S001"));
        assertTrue(searchOutput.contains("SERIES NAME: Breaking Bad"));
        assertTrue(searchOutput.contains("SERIES AGE RESTRICTION: 16"));
        assertTrue(searchOutput.contains("NUMBER OF EPISODES: 62"));
        
        outputStream.reset();
        
        // 3. UPDATE - Update a series
        String updateInput = "S001\nBetter Call Saul\n18\n50\n";
        System.setIn(new ByteArrayInputStream(updateInput.getBytes()));
        series.updateSeries();
        
        String updateOutput = outputStream.toString();
        assertTrue(updateOutput.contains("Series updated successfully!"));
        
        outputStream.reset();
        
        // 4. Verify update by searching again
        String verifyInput = "S001\n";
        System.setIn(new ByteArrayInputStream(verifyInput.getBytes()));
        series.searchSeries();
        
        String verifyOutput = outputStream.toString();
        assertTrue(verifyOutput.contains("SERIES ID: S001"));
        assertTrue(verifyOutput.contains("SERIES NAME: Better Call Saul"));
        assertTrue(verifyOutput.contains("SERIES AGE RESTRICTION: 18"));
        assertTrue(verifyOutput.contains("NUMBER OF EPISODES: 50"));
        
        outputStream.reset();
        
        // 5. Generate report to see all series
        series.seriesReport();
        
        String reportOutput = outputStream.toString();
        assertTrue(reportOutput.contains("Series 1"));
        assertTrue(reportOutput.contains("Series 2"));
        assertTrue(reportOutput.contains("SERIES ID: S001"));
        assertTrue(reportOutput.contains("SERIES ID: S002"));
        assertTrue(reportOutput.contains("SERIES NAME: Better Call Saul")); // Updated name
        assertTrue(reportOutput.contains("SERIES NAME: Game of Thrones"));
        
        outputStream.reset();
        
        // 6. DELETE - Delete a series
        String deleteInput = "S001\ny\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        series.deleteSeries();
        
        String deleteOutput = outputStream.toString();
        assertTrue(deleteOutput.contains("Series with Series ID: S001 WAS deleted!"));
        
        outputStream.reset();
        
        // 7. Verify deletion by searching
        String verifyDeleteInput = "S001\n";
        System.setIn(new ByteArrayInputStream(verifyDeleteInput.getBytes()));
        series.searchSeries();
        
        String verifyDeleteOutput = outputStream.toString();
        assertTrue(verifyDeleteOutput.contains("Series with Series ID: S001 was not found!"));
        
        outputStream.reset();
        
        // 8. Final report should show only remaining series
        series.seriesReport();
        
        String finalReportOutput = outputStream.toString();
        assertTrue(finalReportOutput.contains("Series 1"));
        assertFalse(finalReportOutput.contains("Series 2")); // Should only show one series now
        assertTrue(finalReportOutput.contains("SERIES ID: S002"));
        assertTrue(finalReportOutput.contains("SERIES NAME: Game of Thrones"));
    }
    
    @Test
    void testDataPersistenceWithinSession() {
        // Test that data persists within the same session
        
        // Add series
        String createInput = "S001\nTest Series\n14\n10\n";
        System.setIn(new ByteArrayInputStream(createInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Search for series
        String searchInput = "S001\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        series.searchSeries();
        
        String searchOutput = outputStream.toString();
        assertTrue(searchOutput.contains("SERIES ID: S001"));
        assertTrue(searchOutput.contains("SERIES NAME: Test Series"));
        
        outputStream.reset();
        
        // Update series
        String updateInput = "S001\nUpdated Series\n16\n20\n";
        System.setIn(new ByteArrayInputStream(updateInput.getBytes()));
        series.updateSeries();
        
        outputStream.reset();
        
        // Search again to verify persistence
        String searchAgainInput = "S001\n";
        System.setIn(new ByteArrayInputStream(searchAgainInput.getBytes()));
        series.searchSeries();
        
        String searchAgainOutput = outputStream.toString();
        assertTrue(searchAgainOutput.contains("SERIES ID: S001"));
        assertTrue(searchAgainOutput.contains("SERIES NAME: Updated Series"));
        assertTrue(searchAgainOutput.contains("SERIES AGE RESTRICTION: 16"));
        assertTrue(searchAgainOutput.contains("NUMBER OF EPISODES: 20"));
    }
    
    @Test
    void testMultipleSeriesManagement() {
        // Test managing multiple series simultaneously
        
        // Add first series
        String createInput1 = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(createInput1.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Add second series
        String createInput2 = "S002\nGame of Thrones\n18\n73\n";
        System.setIn(new ByteArrayInputStream(createInput2.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Add third series
        String createInput3 = "S003\nStranger Things\n14\n25\n";
        System.setIn(new ByteArrayInputStream(createInput3.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Generate report
        series.seriesReport();
        
        String reportOutput = outputStream.toString();
        assertTrue(reportOutput.contains("Series 1"));
        assertTrue(reportOutput.contains("Series 2"));
        assertTrue(reportOutput.contains("Series 3"));
        assertTrue(reportOutput.contains("SERIES ID: S001"));
        assertTrue(reportOutput.contains("SERIES ID: S002"));
        assertTrue(reportOutput.contains("SERIES ID: S003"));
        assertTrue(reportOutput.contains("SERIES NAME: Breaking Bad"));
        assertTrue(reportOutput.contains("SERIES NAME: Game of Thrones"));
        assertTrue(reportOutput.contains("SERIES NAME: Stranger Things"));
    }
    
    @Test
    void testErrorHandlingAndRecovery() {
        // Test error handling and recovery scenarios
        
        // Try to search for non-existent series
        String searchInput = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        series.searchSeries();
        
        String searchOutput = outputStream.toString();
        assertTrue(searchOutput.contains("Series with Series ID: NONEXISTENT was not found!"));
        
        outputStream.reset();
        
        // Try to update non-existent series
        String updateInput = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(updateInput.getBytes()));
        series.updateSeries();
        
        String updateOutput = outputStream.toString();
        assertTrue(updateOutput.contains("Series with Series ID: NONEXISTENT was not found!"));
        
        outputStream.reset();
        
        // Try to delete non-existent series
        String deleteInput = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        series.deleteSeries();
        
        String deleteOutput = outputStream.toString();
        assertTrue(deleteOutput.contains("Series with Series ID: NONEXISTENT was not found!"));
        
        outputStream.reset();
        
        // Now add a real series and verify it works
        String createInput = "S001\nTest Series\n14\n10\n";
        System.setIn(new ByteArrayInputStream(createInput.getBytes()));
        series.captureSeries();
        
        String createOutput = outputStream.toString();
        assertTrue(createOutput.contains("Series processed successfully!!!"));
    }
    
    @Test
    void testAgeValidationIntegration() {
        // Test age validation in various scenarios
        
        // Test with valid age
        String validInput = "S001\nTest Series\n14\n10\n";
        System.setIn(new ByteArrayInputStream(validInput.getBytes()));
        series.captureSeries();
        
        String validOutput = outputStream.toString();
        assertTrue(validOutput.contains("Series processed successfully!!!"));
        
        outputStream.reset();
        
        // Test with invalid age then valid
        String invalidThenValidInput = "S002\nTest Series 2\n25\n14\n15\n";
        System.setIn(new ByteArrayInputStream(invalidThenValidInput.getBytes()));
        series.captureSeries();
        
        String invalidThenValidOutput = outputStream.toString();
        assertTrue(invalidThenValidOutput.contains("Series processed successfully!!!"));
        assertTrue(invalidThenValidOutput.contains("You have entered an incorrect age restriction: 25"));
        
        outputStream.reset();
        
        // Test with non-numeric age then valid
        String nonNumericThenValidInput = "S003\nTest Series 3\nabc\n16\n20\n";
        System.setIn(new ByteArrayInputStream(nonNumericThenValidInput.getBytes()));
        series.captureSeries();
        
        String nonNumericThenValidOutput = outputStream.toString();
        assertTrue(nonNumericThenValidOutput.contains("Series processed successfully!!!"));
        assertTrue(nonNumericThenValidOutput.contains("You have entered an incorrect non-number age restriction!"));
    }
}

