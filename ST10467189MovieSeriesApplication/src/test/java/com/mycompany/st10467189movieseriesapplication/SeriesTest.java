package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

public class SeriesTest {
    
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
    void testCaptureSeriesWithValidData() {
        // Simulate user input for capturing a series
        String input = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series processed successfully!!!"));
        assertTrue(output.contains("Enter the series ID:"));
        assertTrue(output.contains("Enter the series name:"));
        assertTrue(output.contains("Enter the series age restriction (2-18):"));
        assertTrue(output.contains("Enter the number of episodes for Breaking Bad:"));
    }
    
    @Test
    void testCaptureSeriesWithInvalidAgeThenValid() {
        // Simulate user input with invalid age first, then valid
        String input = "S002\nGame of Thrones\n25\n18\n73\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series processed successfully!!!"));
        assertTrue(output.contains("You have entered an incorrect age restriction: 25"));
        assertTrue(output.contains("Please re-enter the series age (2-18):"));
    }
    
    @Test
    void testCaptureSeriesWithNonNumericAgeThenValid() {
        // Simulate user input with non-numeric age first, then valid
        String input = "S003\nStranger Things\nabc\n14\n25\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series processed successfully!!!"));
        assertTrue(output.contains("You have entered an incorrect non-number age restriction!"));
        assertTrue(output.contains("Please re-enter the series age (2-18):"));
    }
    
    @Test
    void testSearchSeriesFound() {
        // First capture a series, then search for it
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now search for the series
        String searchInput = "S001\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        series.searchSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("SERIES ID: S001"));
        assertTrue(output.contains("SERIES NAME: Breaking Bad"));
        assertTrue(output.contains("SERIES AGE RESTRICTION: 16"));
        assertTrue(output.contains("NUMBER OF EPISODES: 62"));
    }
    
    @Test
    void testSearchSeriesNotFound() {
        String input = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.searchSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"));
    }
    
    @Test
    void testUpdateSeriesFound() {
        // First capture a series
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now update the series
        String updateInput = "S001\nBetter Call Saul\n18\n50\n";
        System.setIn(new ByteArrayInputStream(updateInput.getBytes()));
        series.updateSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series updated successfully!"));
    }
    
    @Test
    void testUpdateSeriesNotFound() {
        String input = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.updateSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"));
    }
    
    @Test
    void testUpdateSeriesWithInvalidAgeThenValid() {
        // First capture a series
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now update with invalid age first, then valid
        String updateInput = "S001\nBreaking Bad\n25\n16\n50\n";
        System.setIn(new ByteArrayInputStream(updateInput.getBytes()));
        series.updateSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series updated successfully!"));
        assertTrue(output.contains("You have entered an incorrect age restriction: 25"));
    }
    
    @Test
    void testDeleteSeriesFoundWithConfirmation() {
        // First capture a series
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now delete with confirmation
        String deleteInput = "S001\ny\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        series.deleteSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series ID: S001 WAS deleted!"));
    }
    
    @Test
    void testDeleteSeriesFoundWithoutConfirmation() {
        // First capture a series
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now delete without confirmation
        String deleteInput = "S001\nn\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        series.deleteSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Deletion cancelled."));
    }
    
    @Test
    void testDeleteSeriesNotFound() {
        String input = "NONEXISTENT\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.deleteSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"));
    }
    
    @Test
    void testSeriesReportEmpty() {
        series.seriesReport();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Sample Report Screen Shot"));
        // Should not contain any series details when empty
        assertFalse(output.contains("SERIES ID:"));
    }
    
    @Test
    void testSeriesReportWithData() {
        // First capture a series
        String captureInput = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now generate report
        series.seriesReport();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Sample Report Screen Shot"));
        assertTrue(output.contains("Series 1"));
        assertTrue(output.contains("SERIES ID: S001"));
        assertTrue(output.contains("SERIES NAME: Breaking Bad"));
        assertTrue(output.contains("SERIES AGE RESTRICTION: 16"));
        assertTrue(output.contains("NUMBER OF EPISODES: 62"));
    }
    
    @Test
    void testSeriesReportWithMultipleSeries() {
        // Capture first series
        String captureInput1 = "S001\nBreaking Bad\n16\n62\n";
        System.setIn(new ByteArrayInputStream(captureInput1.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Capture second series
        String captureInput2 = "S002\nGame of Thrones\n18\n73\n";
        System.setIn(new ByteArrayInputStream(captureInput2.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Now generate report
        series.seriesReport();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series 1"));
        assertTrue(output.contains("Series 2"));
        assertTrue(output.contains("SERIES ID: S001"));
        assertTrue(output.contains("SERIES ID: S002"));
        assertTrue(output.contains("SERIES NAME: Breaking Bad"));
        assertTrue(output.contains("SERIES NAME: Game of Thrones"));
    }
    
    @Test
    void testAgeValidationBoundaries() {
        // Test minimum age boundary
        String input1 = "S001\nTest1\n2\n10\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        series.captureSeries();
        
        outputStream.reset();
        
        // Test maximum age boundary
        String input2 = "S002\nTest2\n18\n20\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("Series processed successfully!!!"));
    }
    
    @Test
    void testAgeValidationBelowBoundary() {
        String input = "S001\nTest1\n1\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("You have entered an incorrect age restriction: 1"));
        assertTrue(output.contains("Please re-enter the series age (2-18):"));
    }
    
    @Test
    void testAgeValidationAboveBoundary() {
        String input = "S001\nTest1\n19\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        
        series.captureSeries();
        
        String output = outputStream.toString();
        assertTrue(output.contains("You have entered an incorrect age restriction: 19"));
        assertTrue(output.contains("Please re-enter the series age (2-18):"));
    }
}

