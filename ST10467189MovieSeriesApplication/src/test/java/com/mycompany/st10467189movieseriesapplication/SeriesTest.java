package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

@DisplayName("Series Class Test Suite")
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
    
    // Helper methods for common test operations
    private void setSystemInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
    
    private void resetOutput() {
        outputStream.reset();
    }
    
    private String getOutput() {
        return outputStream.toString();
    }
    
    private void captureSeriesWithInput(String input) {
        setSystemInput(input);
        series.captureSeries();
    }
    
    private void searchSeriesWithInput(String input) {
        setSystemInput(input);
        series.searchSeries();
    }
    
    private void updateSeriesWithInput(String input) {
        setSystemInput(input);
        series.updateSeries();
    }
    
    private void deleteSeriesWithInput(String input) {
        setSystemInput(input);
        series.deleteSeries();
    }
    
    @Nested
    @DisplayName("Capture Series Tests")
    class CaptureSeriesTests {
        
        @Test
        @DisplayName("Should capture series with valid data")
        void testCaptureSeriesWithValidData() {
            String input = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should show all expected prompts and success message",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should show success message"),
                () -> assertTrue(output.contains("Enter the series ID:"), "Should prompt for series ID"),
                () -> assertTrue(output.contains("Enter the series name:"), "Should prompt for series name"),
                () -> assertTrue(output.contains("Enter the series age restriction (2-18):"), "Should prompt for age restriction"),
                () -> assertTrue(output.contains("Enter the number of episodes for Breaking Bad:"), "Should prompt for episodes with series name")
            );
        }
        
        @ParameterizedTest
        @CsvSource({
            "S001, Breaking Bad, 16, 62",
            "S002, Game of Thrones, 18, 73",
            "S003, Stranger Things, 14, 25",
            "S004, The Office, 12, 201"
        })
        @DisplayName("Should capture series with various valid data combinations")
        void testCaptureSeriesWithVariousValidData(String seriesId, String seriesName, String age, String episodes) {
            String input = String.format("%s\n%s\n%s\n%s\n", seriesId, seriesName, age, episodes);
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should successfully capture series with valid data",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should show success message"),
                () -> assertTrue(output.contains("Enter the number of episodes for " + seriesName + ":"), "Should include series name in episode prompt")
            );
        }
        
        @Test
        @DisplayName("Should handle invalid age then valid age")
        void testCaptureSeriesWithInvalidAgeThenValid() {
            String input = "S002\nGame of Thrones\n25\n18\n73\n";
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should handle invalid age and then accept valid age",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should eventually succeed"),
                () -> assertTrue(output.contains("You have entered an incorrect age restriction: 25"), "Should show invalid age message"),
                () -> assertTrue(output.contains("Please re-enter the series age (2-18):"), "Should prompt for re-entry")
            );
        }
        
        @Test
        @DisplayName("Should handle non-numeric age then valid age")
        void testCaptureSeriesWithNonNumericAgeThenValid() {
            String input = "S003\nStranger Things\nabc\n14\n25\n";
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should handle non-numeric age and then accept valid age",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should eventually succeed"),
                () -> assertTrue(output.contains("You have entered an incorrect non-number age restriction!"), "Should show non-numeric error"),
                () -> assertTrue(output.contains("Please re-enter the series age (2-18):"), "Should prompt for re-entry")
            );
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"1", "19", "0", "-5", "100"})
        @DisplayName("Should reject invalid age values")
        void testCaptureSeriesWithInvalidAgeValues(String invalidAge) {
            String input = String.format("S001\nTest Series\n%s\n14\n10\n", invalidAge);
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should reject invalid age and then accept valid age",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should eventually succeed"),
                () -> assertTrue(output.contains("You have entered an incorrect age restriction: " + invalidAge), "Should show invalid age message"),
                () -> assertTrue(output.contains("Please re-enter the series age (2-18):"), "Should prompt for re-entry")
            );
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"abc", "xyz", "12.5", "age", "!"})
        @DisplayName("Should reject non-numeric age values")
        void testCaptureSeriesWithNonNumericAgeValues(String nonNumericAge) {
            String input = String.format("S001\nTest Series\n%s\n14\n10\n", nonNumericAge);
            captureSeriesWithInput(input);
            
            String output = getOutput();
            assertAll("Should reject non-numeric age and then accept valid age",
                () -> assertTrue(output.contains("Series processed successfully!!!"), "Should eventually succeed"),
                () -> assertTrue(output.contains("You have entered an incorrect non-number age restriction!"), "Should show non-numeric error"),
                () -> assertTrue(output.contains("Please re-enter the series age (2-18):"), "Should prompt for re-entry")
            );
        }
        
        @Test
        @DisplayName("Should handle boundary age values correctly")
        void testCaptureSeriesWithBoundaryAgeValues() {
            // Test minimum age (2)
            String input1 = "S001\nTest Series 1\n2\n10\n";
            captureSeriesWithInput(input1);
            String output1 = getOutput();
            assertTrue(output1.contains("Series processed successfully!!!"), "Should accept minimum age 2");
            
            resetOutput();
            
            // Test maximum age (18)
            String input2 = "S002\nTest Series 2\n18\n20\n";
            captureSeriesWithInput(input2);
            String output2 = getOutput();
            assertTrue(output2.contains("Series processed successfully!!!"), "Should accept maximum age 18");
        }
    }
    
    @Nested
    @DisplayName("Search Series Tests")
    class SearchSeriesTests {
        
        @Test
        @DisplayName("Should find and display series details when series exists")
        void testSearchSeriesFound() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now search for the series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String output = getOutput();
            assertAll("Should display all series details when found",
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Breaking Bad"), "Should show series name"),
                () -> assertTrue(output.contains("SERIES AGE RESTRICTION: 16"), "Should show age restriction"),
                () -> assertTrue(output.contains("NUMBER OF EPISODES: 62"), "Should show number of episodes")
            );
        }
        
        @Test
        @DisplayName("Should show not found message when series does not exist")
        void testSearchSeriesNotFound() {
            String input = "NONEXISTENT\n";
            searchSeriesWithInput(input);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for non-existent series");
        }
        
        @Test
        @DisplayName("Should find correct series when multiple series exist")
        void testSearchSeriesWithMultipleSeries() {
            // Capture first series
            String captureInput1 = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput1);
            resetOutput();
            
            // Capture second series
            String captureInput2 = "S002\nGame of Thrones\n18\n73\n";
            captureSeriesWithInput(captureInput2);
            resetOutput();
            
            // Search for first series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String output = getOutput();
            assertAll("Should find correct series when multiple exist",
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show correct series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Breaking Bad"), "Should show correct series name"),
                () -> assertFalse(output.contains("SERIES ID: S002"), "Should not show other series ID"),
                () -> assertFalse(output.contains("SERIES NAME: Game of Thrones"), "Should not show other series name")
            );
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "S999", "INVALID", "s001"})
        @DisplayName("Should show not found message for various non-existent series IDs")
        void testSearchSeriesWithVariousNonExistentIds(String nonExistentId) {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Search for non-existent series
            String searchInput = nonExistentId + "\n";
            searchSeriesWithInput(searchInput);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: " + nonExistentId + " was not found!"), 
                "Should show not found message for: " + nonExistentId);
        }
    }
    
    @Nested
    @DisplayName("Update Series Tests")
    class UpdateSeriesTests {
        
        @Test
        @DisplayName("Should update series successfully when series exists")
        void testUpdateSeriesFound() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now update the series
            String updateInput = "S001\nBetter Call Saul\n18\n50\n";
            updateSeriesWithInput(updateInput);
            
            String output = getOutput();
            assertTrue(output.contains("Series updated successfully!"), "Should show update success message");
        }
        
        @Test
        @DisplayName("Should show not found message when updating non-existent series")
        void testUpdateSeriesNotFound() {
            String input = "NONEXISTENT\n";
            updateSeriesWithInput(input);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for non-existent series");
        }
        
        @Test
        @DisplayName("Should handle invalid age during update then accept valid age")
        void testUpdateSeriesWithInvalidAgeThenValid() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now update with invalid age first, then valid
            String updateInput = "S001\nBreaking Bad\n25\n16\n50\n";
            updateSeriesWithInput(updateInput);
            
            String output = getOutput();
            assertAll("Should handle invalid age and then update successfully",
                () -> assertTrue(output.contains("Series updated successfully!"), "Should eventually update successfully"),
                () -> assertTrue(output.contains("You have entered an incorrect age restriction: 25"), "Should show invalid age message")
            );
        }
        
        @Test
        @DisplayName("Should verify update by searching for updated series")
        void testUpdateSeriesAndVerify() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Update the series
            String updateInput = "S001\nBetter Call Saul\n18\n50\n";
            updateSeriesWithInput(updateInput);
            resetOutput();
            
            // Search for the updated series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String output = getOutput();
            assertAll("Should show updated series details",
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Better Call Saul"), "Should show updated name"),
                () -> assertTrue(output.contains("SERIES AGE RESTRICTION: 18"), "Should show updated age"),
                () -> assertTrue(output.contains("NUMBER OF EPISODES: 50"), "Should show updated episodes")
            );
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"1", "19", "0", "-5", "100"})
        @DisplayName("Should reject invalid age values during update")
        void testUpdateSeriesWithInvalidAgeValues(String invalidAge) {
            // First capture a series
            String captureInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Try to update with invalid age
            String updateInput = String.format("S001\nUpdated Series\n%s\n14\n20\n", invalidAge);
            updateSeriesWithInput(updateInput);
            
            String output = getOutput();
            assertAll("Should reject invalid age and then accept valid age",
                () -> assertTrue(output.contains("Series updated successfully!"), "Should eventually update successfully"),
                () -> assertTrue(output.contains("You have entered an incorrect age restriction: " + invalidAge), "Should show invalid age message")
            );
        }
    }
    
    @Nested
    @DisplayName("Delete Series Tests")
    class DeleteSeriesTests {
        
        @Test
        @DisplayName("Should delete series when user confirms deletion")
        void testDeleteSeriesFoundWithConfirmation() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now delete with confirmation
            String deleteInput = "S001\ny\n";
            deleteSeriesWithInput(deleteInput);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: S001 WAS deleted!"), 
                "Should show deletion success message");
        }
        
        @Test
        @DisplayName("Should cancel deletion when user does not confirm")
        void testDeleteSeriesFoundWithoutConfirmation() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now delete without confirmation
            String deleteInput = "S001\nn\n";
            deleteSeriesWithInput(deleteInput);
            
            String output = getOutput();
            assertTrue(output.contains("Deletion cancelled."), "Should show cancellation message");
        }
        
        @Test
        @DisplayName("Should show not found message when deleting non-existent series")
        void testDeleteSeriesNotFound() {
            String input = "NONEXISTENT\n";
            deleteSeriesWithInput(input);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for non-existent series");
        }
        
        @Test
        @DisplayName("Should verify deletion by searching for deleted series")
        void testDeleteSeriesAndVerify() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Delete the series
            String deleteInput = "S001\ny\n";
            deleteSeriesWithInput(deleteInput);
            resetOutput();
            
            // Try to search for the deleted series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: S001 was not found!"), 
                "Should not find deleted series");
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"Y", "YES", "yes", "Yes"})
        @DisplayName("Should delete series with various confirmation formats")
        void testDeleteSeriesWithVariousConfirmations(String confirmation) {
            // First capture a series
            String captureInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Delete with various confirmation formats
            String deleteInput = "S001\n" + confirmation + "\n";
            deleteSeriesWithInput(deleteInput);
            
            String output = getOutput();
            assertTrue(output.contains("Series with Series ID: S001 WAS deleted!"), 
                "Should delete with confirmation: " + confirmation);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"N", "NO", "no", "No", "anything", ""})
        @DisplayName("Should cancel deletion with various non-confirmation inputs")
        void testDeleteSeriesWithVariousNonConfirmations(String nonConfirmation) {
            // First capture a series
            String captureInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Try to delete with non-confirmation
            String deleteInput = "S001\n" + nonConfirmation + "\n";
            deleteSeriesWithInput(deleteInput);
            
            String output = getOutput();
            assertTrue(output.contains("Deletion cancelled."), 
                "Should cancel deletion with input: " + nonConfirmation);
        }
    }
    
    @Nested
    @DisplayName("Series Report Tests")
    class SeriesReportTests {
        
        @Test
        @DisplayName("Should show empty report when no series exist")
        void testSeriesReportEmpty() {
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should show report header but no series details when empty",
                () -> assertTrue(output.contains("Sample Report Screen Shot"), "Should show report header"),
                () -> assertFalse(output.contains("SERIES ID:"), "Should not contain any series details when empty")
            );
        }
        
        @Test
        @DisplayName("Should show report with single series")
        void testSeriesReportWithData() {
            // First capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Now generate report
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should show complete report for single series",
                () -> assertTrue(output.contains("Sample Report Screen Shot"), "Should show report header"),
                () -> assertTrue(output.contains("Series 1"), "Should show series number"),
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Breaking Bad"), "Should show series name"),
                () -> assertTrue(output.contains("SERIES AGE RESTRICTION: 16"), "Should show age restriction"),
                () -> assertTrue(output.contains("NUMBER OF EPISODES: 62"), "Should show number of episodes")
            );
        }
        
        @Test
        @DisplayName("Should show report with multiple series")
        void testSeriesReportWithMultipleSeries() {
            // Capture first series
            String captureInput1 = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput1);
            resetOutput();
            
            // Capture second series
            String captureInput2 = "S002\nGame of Thrones\n18\n73\n";
            captureSeriesWithInput(captureInput2);
            resetOutput();
            
            // Now generate report
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should show report with multiple series",
                () -> assertTrue(output.contains("Series 1"), "Should show first series number"),
                () -> assertTrue(output.contains("Series 2"), "Should show second series number"),
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show first series ID"),
                () -> assertTrue(output.contains("SERIES ID: S002"), "Should show second series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Breaking Bad"), "Should show first series name"),
                () -> assertTrue(output.contains("SERIES NAME: Game of Thrones"), "Should show second series name")
            );
        }
        
        @Test
        @DisplayName("Should show report with many series")
        void testSeriesReportWithManySeries() {
            // Capture multiple series
            String[] seriesData = {
                "S001\nBreaking Bad\n16\n62\n",
                "S002\nGame of Thrones\n18\n73\n",
                "S003\nStranger Things\n14\n25\n",
                "S004\nThe Office\n12\n201\n"
            };
            
            for (String data : seriesData) {
                captureSeriesWithInput(data);
                resetOutput();
            }
            
            // Generate report
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should show report with many series",
                () -> assertTrue(output.contains("Series 1"), "Should show series 1"),
                () -> assertTrue(output.contains("Series 2"), "Should show series 2"),
                () -> assertTrue(output.contains("Series 3"), "Should show series 3"),
                () -> assertTrue(output.contains("Series 4"), "Should show series 4"),
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show S001"),
                () -> assertTrue(output.contains("SERIES ID: S002"), "Should show S002"),
                () -> assertTrue(output.contains("SERIES ID: S003"), "Should show S003"),
                () -> assertTrue(output.contains("SERIES ID: S004"), "Should show S004")
            );
        }
        
        @Test
        @DisplayName("Should show updated series in report after update")
        void testSeriesReportAfterUpdate() {
            // Capture a series
            String captureInput = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput);
            resetOutput();
            
            // Update the series
            String updateInput = "S001\nBetter Call Saul\n18\n50\n";
            updateSeriesWithInput(updateInput);
            resetOutput();
            
            // Generate report
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should show updated series details in report",
                () -> assertTrue(output.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Better Call Saul"), "Should show updated name"),
                () -> assertTrue(output.contains("SERIES AGE RESTRICTION: 18"), "Should show updated age"),
                () -> assertTrue(output.contains("NUMBER OF EPISODES: 50"), "Should show updated episodes")
            );
        }
        
        @Test
        @DisplayName("Should not show deleted series in report")
        void testSeriesReportAfterDeletion() {
            // Capture first series
            String captureInput1 = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(captureInput1);
            resetOutput();
            
            // Capture second series
            String captureInput2 = "S002\nGame of Thrones\n18\n73\n";
            captureSeriesWithInput(captureInput2);
            resetOutput();
            
            // Delete first series
            String deleteInput = "S001\ny\n";
            deleteSeriesWithInput(deleteInput);
            resetOutput();
            
            // Generate report
            series.seriesReport();
            
            String output = getOutput();
            assertAll("Should not show deleted series in report",
                () -> assertTrue(output.contains("Series 1"), "Should show only remaining series"),
                () -> assertFalse(output.contains("Series 2"), "Should not show second series number"),
                () -> assertFalse(output.contains("SERIES ID: S001"), "Should not show deleted series ID"),
                () -> assertTrue(output.contains("SERIES ID: S002"), "Should show remaining series ID"),
                () -> assertTrue(output.contains("SERIES NAME: Game of Thrones"), "Should show remaining series name")
            );
        }
    }
}
