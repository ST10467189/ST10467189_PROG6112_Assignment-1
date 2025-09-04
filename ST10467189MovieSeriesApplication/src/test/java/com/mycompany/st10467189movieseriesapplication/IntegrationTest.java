package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

@DisplayName("Integration Test Suite")
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
    @DisplayName("Complete CRUD Operations Tests")
    class CompleteCRUDTests {
        
        @Test
        @DisplayName("Should complete full CRUD cycle with multiple series")
        void testCompleteCRUDCycle() {
            // 1. CREATE - Add multiple series
            String createInput1 = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(createInput1);
            resetOutput();
            
            String createInput2 = "S002\nGame of Thrones\n18\n73\n";
            captureSeriesWithInput(createInput2);
            resetOutput();
            
            // 2. READ - Search for series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String searchOutput = getOutput();
            assertAll("Should find and display first series details",
                () -> assertTrue(searchOutput.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(searchOutput.contains("SERIES NAME: Breaking Bad"), "Should show series name"),
                () -> assertTrue(searchOutput.contains("SERIES AGE RESTRICTION: 16"), "Should show age restriction"),
                () -> assertTrue(searchOutput.contains("NUMBER OF EPISODES: 62"), "Should show number of episodes")
            );
            
            resetOutput();
            
            // 3. UPDATE - Update a series
            String updateInput = "S001\nBetter Call Saul\n18\n50\n";
            updateSeriesWithInput(updateInput);
            
            String updateOutput = getOutput();
            assertTrue(updateOutput.contains("Series updated successfully!"), "Should show update success");
            
            resetOutput();
            
            // 4. Verify update by searching again
            String verifyInput = "S001\n";
            searchSeriesWithInput(verifyInput);
            
            String verifyOutput = getOutput();
            assertAll("Should show updated series details",
                () -> assertTrue(verifyOutput.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(verifyOutput.contains("SERIES NAME: Better Call Saul"), "Should show updated name"),
                () -> assertTrue(verifyOutput.contains("SERIES AGE RESTRICTION: 18"), "Should show updated age"),
                () -> assertTrue(verifyOutput.contains("NUMBER OF EPISODES: 50"), "Should show updated episodes")
            );
            
            resetOutput();
            
            // 5. Generate report to see all series
            series.seriesReport();
            
            String reportOutput = getOutput();
            assertAll("Should show report with both series including updated one",
                () -> assertTrue(reportOutput.contains("Series 1"), "Should show first series"),
                () -> assertTrue(reportOutput.contains("Series 2"), "Should show second series"),
                () -> assertTrue(reportOutput.contains("SERIES ID: S001"), "Should show first series ID"),
                () -> assertTrue(reportOutput.contains("SERIES ID: S002"), "Should show second series ID"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Better Call Saul"), "Should show updated name"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Game of Thrones"), "Should show second series name")
            );
            
            resetOutput();
            
            // 6. DELETE - Delete a series
            String deleteInput = "S001\ny\n";
            deleteSeriesWithInput(deleteInput);
            
            String deleteOutput = getOutput();
            assertTrue(deleteOutput.contains("Series with Series ID: S001 WAS deleted!"), "Should show deletion success");
            
            resetOutput();
            
            // 7. Verify deletion by searching
            String verifyDeleteInput = "S001\n";
            searchSeriesWithInput(verifyDeleteInput);
            
            String verifyDeleteOutput = getOutput();
            assertTrue(verifyDeleteOutput.contains("Series with Series ID: S001 was not found!"), "Should not find deleted series");
            
            resetOutput();
            
            // 8. Final report should show only remaining series
            series.seriesReport();
            
            String finalReportOutput = getOutput();
            assertAll("Should show only remaining series in final report",
                () -> assertTrue(finalReportOutput.contains("Series 1"), "Should show only remaining series"),
                () -> assertFalse(finalReportOutput.contains("Series 2"), "Should not show second series number"),
                () -> assertTrue(finalReportOutput.contains("SERIES ID: S002"), "Should show remaining series ID"),
                () -> assertTrue(finalReportOutput.contains("SERIES NAME: Game of Thrones"), "Should show remaining series name")
            );
        }
        
        @ParameterizedTest
        @CsvSource({
            "S001, Breaking Bad, 16, 62, Better Call Saul, 18, 50",
            "S002, Game of Thrones, 18, 73, House of the Dragon, 16, 10",
            "S003, Stranger Things, 14, 25, The Umbrella Academy, 12, 30"
        })
        @DisplayName("Should complete CRUD cycle with various series data")
        void testCompleteCRUDCycleWithVariousData(String originalId, String originalName, String originalAge, 
                                                String originalEpisodes, String updatedName, String updatedAge, 
                                                String updatedEpisodes) {
            // CREATE
            String createInput = String.format("%s\n%s\n%s\n%s\n", originalId, originalName, originalAge, originalEpisodes);
            captureSeriesWithInput(createInput);
            resetOutput();
            
            // READ
            String searchInput = originalId + "\n";
            searchSeriesWithInput(searchInput);
            String searchOutput = getOutput();
            assertTrue(searchOutput.contains("SERIES NAME: " + originalName), "Should find original series");
            resetOutput();
            
            // UPDATE
            String updateInput = String.format("%s\n%s\n%s\n%s\n", originalId, updatedName, updatedAge, updatedEpisodes);
            updateSeriesWithInput(updateInput);
            String updateOutput = getOutput();
            assertTrue(updateOutput.contains("Series updated successfully!"), "Should update successfully");
            resetOutput();
            
            // READ after UPDATE
            searchSeriesWithInput(searchInput);
            String verifyOutput = getOutput();
            assertAll("Should show updated series details",
                () -> assertTrue(verifyOutput.contains("SERIES NAME: " + updatedName), "Should show updated name"),
                () -> assertTrue(verifyOutput.contains("SERIES AGE RESTRICTION: " + updatedAge), "Should show updated age"),
                () -> assertTrue(verifyOutput.contains("NUMBER OF EPISODES: " + updatedEpisodes), "Should show updated episodes")
            );
            resetOutput();
            
            // DELETE
            String deleteInput = originalId + "\ny\n";
            deleteSeriesWithInput(deleteInput);
            String deleteOutput = getOutput();
            assertTrue(deleteOutput.contains("Series with Series ID: " + originalId + " WAS deleted!"), "Should delete successfully");
            resetOutput();
            
            // READ after DELETE
            searchSeriesWithInput(searchInput);
            String finalSearchOutput = getOutput();
            assertTrue(finalSearchOutput.contains("Series with Series ID: " + originalId + " was not found!"), "Should not find deleted series");
        }
    }
    
    @Nested
    @DisplayName("Data Persistence Tests")
    class DataPersistenceTests {
        
        @Test
        @DisplayName("Should maintain data persistence within session")
        void testDataPersistenceWithinSession() {
            // Add series
            String createInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(createInput);
            resetOutput();
            
            // Search for series
            String searchInput = "S001\n";
            searchSeriesWithInput(searchInput);
            
            String searchOutput = getOutput();
            assertAll("Should find series after creation",
                () -> assertTrue(searchOutput.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(searchOutput.contains("SERIES NAME: Test Series"), "Should show series name")
            );
            
            resetOutput();
            
            // Update series
            String updateInput = "S001\nUpdated Series\n16\n20\n";
            updateSeriesWithInput(updateInput);
            resetOutput();
            
            // Search again to verify persistence
            String searchAgainInput = "S001\n";
            searchSeriesWithInput(searchAgainInput);
            
            String searchAgainOutput = getOutput();
            assertAll("Should show updated series details after update",
                () -> assertTrue(searchAgainOutput.contains("SERIES ID: S001"), "Should show series ID"),
                () -> assertTrue(searchAgainOutput.contains("SERIES NAME: Updated Series"), "Should show updated name"),
                () -> assertTrue(searchAgainOutput.contains("SERIES AGE RESTRICTION: 16"), "Should show updated age"),
                () -> assertTrue(searchAgainOutput.contains("NUMBER OF EPISODES: 20"), "Should show updated episodes")
            );
        }
        
        @Test
        @DisplayName("Should maintain multiple series data within session")
        void testMultipleSeriesDataPersistence() {
            // Add multiple series
            String[] seriesData = {
                "S001\nBreaking Bad\n16\n62\n",
                "S002\nGame of Thrones\n18\n73\n",
                "S003\nStranger Things\n14\n25\n"
            };
            
            for (String data : seriesData) {
                captureSeriesWithInput(data);
                resetOutput();
            }
            
            // Verify all series can be found
            for (int i = 0; i < seriesData.length; i++) {
                String seriesId = "S00" + (i + 1);
                String searchInput = seriesId + "\n";
                searchSeriesWithInput(searchInput);
                
                String output = getOutput();
                assertTrue(output.contains("SERIES ID: " + seriesId), "Should find series " + seriesId);
                resetOutput();
            }
        }
    }
    
    @Nested
    @DisplayName("Multiple Series Management Tests")
    class MultipleSeriesManagementTests {
        
        @Test
        @DisplayName("Should manage multiple series simultaneously")
        void testMultipleSeriesManagement() {
            // Add first series
            String createInput1 = "S001\nBreaking Bad\n16\n62\n";
            captureSeriesWithInput(createInput1);
            resetOutput();
            
            // Add second series
            String createInput2 = "S002\nGame of Thrones\n18\n73\n";
            captureSeriesWithInput(createInput2);
            resetOutput();
            
            // Add third series
            String createInput3 = "S003\nStranger Things\n14\n25\n";
            captureSeriesWithInput(createInput3);
            resetOutput();
            
            // Generate report
            series.seriesReport();
            
            String reportOutput = getOutput();
            assertAll("Should show all three series in report",
                () -> assertTrue(reportOutput.contains("Series 1"), "Should show first series"),
                () -> assertTrue(reportOutput.contains("Series 2"), "Should show second series"),
                () -> assertTrue(reportOutput.contains("Series 3"), "Should show third series"),
                () -> assertTrue(reportOutput.contains("SERIES ID: S001"), "Should show first series ID"),
                () -> assertTrue(reportOutput.contains("SERIES ID: S002"), "Should show second series ID"),
                () -> assertTrue(reportOutput.contains("SERIES ID: S003"), "Should show third series ID"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Breaking Bad"), "Should show first series name"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Game of Thrones"), "Should show second series name"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Stranger Things"), "Should show third series name")
            );
        }
        
        @Test
        @DisplayName("Should handle complex operations with multiple series")
        void testComplexOperationsWithMultipleSeries() {
            // Add multiple series
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
            
            // Update one series
            String updateInput = "S002\nHouse of the Dragon\n16\n10\n";
            updateSeriesWithInput(updateInput);
            resetOutput();
            
            // Delete one series
            String deleteInput = "S003\ny\n";
            deleteSeriesWithInput(deleteInput);
            resetOutput();
            
            // Generate final report
            series.seriesReport();
            
            String reportOutput = getOutput();
            assertAll("Should show correct series after complex operations",
                () -> assertTrue(reportOutput.contains("Series 1"), "Should show first series"),
                () -> assertTrue(reportOutput.contains("Series 2"), "Should show second series"),
                () -> assertTrue(reportOutput.contains("Series 3"), "Should show third series"),
                () -> assertFalse(reportOutput.contains("Series 4"), "Should not show fourth series number"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: Breaking Bad"), "Should show first series"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: House of the Dragon"), "Should show updated second series"),
                () -> assertTrue(reportOutput.contains("SERIES NAME: The Office"), "Should show fourth series"),
                () -> assertFalse(reportOutput.contains("SERIES NAME: Stranger Things"), "Should not show deleted series")
            );
        }
    }
    
    @Nested
    @DisplayName("Error Handling and Recovery Tests")
    class ErrorHandlingTests {
        
        @Test
        @DisplayName("Should handle operations on non-existent series gracefully")
        void testErrorHandlingAndRecovery() {
            // Try to search for non-existent series
            String searchInput = "NONEXISTENT\n";
            searchSeriesWithInput(searchInput);
            
            String searchOutput = getOutput();
            assertTrue(searchOutput.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for search");
            
            resetOutput();
            
            // Try to update non-existent series
            String updateInput = "NONEXISTENT\n";
            updateSeriesWithInput(updateInput);
            
            String updateOutput = getOutput();
            assertTrue(updateOutput.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for update");
            
            resetOutput();
            
            // Try to delete non-existent series
            String deleteInput = "NONEXISTENT\n";
            deleteSeriesWithInput(deleteInput);
            
            String deleteOutput = getOutput();
            assertTrue(deleteOutput.contains("Series with Series ID: NONEXISTENT was not found!"), 
                "Should show not found message for delete");
            
            resetOutput();
            
            // Now add a real series and verify it works
            String createInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(createInput);
            
            String createOutput = getOutput();
            assertTrue(createOutput.contains("Series processed successfully!!!"), 
                "Should successfully create series after error handling");
        }
        
        @Test
        @DisplayName("Should handle multiple error scenarios and recover")
        void testMultipleErrorScenariosAndRecovery() {
            // Try multiple invalid operations
            String[] invalidOperations = {
                "NONEXISTENT1\n", "NONEXISTENT2\n", "NONEXISTENT3\n"
            };
            
            for (String operation : invalidOperations) {
                searchSeriesWithInput(operation);
                String output = getOutput();
                assertTrue(output.contains("was not found!"), "Should handle invalid search gracefully");
                resetOutput();
            }
            
            // Now perform valid operations
            String createInput = "S001\nValid Series\n14\n10\n";
            captureSeriesWithInput(createInput);
            String createOutput = getOutput();
            assertTrue(createOutput.contains("Series processed successfully!!!"), 
                "Should work normally after error handling");
        }
    }
    
    @Nested
    @DisplayName("Age Validation Integration Tests")
    class AgeValidationIntegrationTests {
        
        @Test
        @DisplayName("Should handle age validation in various scenarios")
        void testAgeValidationIntegration() {
            // Test with valid age
            String validInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(validInput);
            
            String validOutput = getOutput();
            assertTrue(validOutput.contains("Series processed successfully!!!"), 
                "Should accept valid age");
            
            resetOutput();
            
            // Test with invalid age then valid
            String invalidThenValidInput = "S002\nTest Series 2\n25\n14\n15\n";
            captureSeriesWithInput(invalidThenValidInput);
            
            String invalidThenValidOutput = getOutput();
            assertAll("Should handle invalid age and then accept valid age",
                () -> assertTrue(invalidThenValidOutput.contains("Series processed successfully!!!"), 
                    "Should eventually succeed"),
                () -> assertTrue(invalidThenValidOutput.contains("You have entered an incorrect age restriction: 25"), 
                    "Should show invalid age message")
            );
            
            resetOutput();
            
            // Test with non-numeric age then valid
            String nonNumericThenValidInput = "S003\nTest Series 3\nabc\n16\n20\n";
            captureSeriesWithInput(nonNumericThenValidInput);
            
            String nonNumericThenValidOutput = getOutput();
            assertAll("Should handle non-numeric age and then accept valid age",
                () -> assertTrue(nonNumericThenValidOutput.contains("Series processed successfully!!!"), 
                    "Should eventually succeed"),
                () -> assertTrue(nonNumericThenValidOutput.contains("You have entered an incorrect non-number age restriction!"), 
                    "Should show non-numeric error message")
            );
        }
        
        @Test
        @DisplayName("Should handle age validation during updates")
        void testAgeValidationDuringUpdates() {
            // First create a series
            String createInput = "S001\nTest Series\n14\n10\n";
            captureSeriesWithInput(createInput);
            resetOutput();
            
            // Try to update with invalid age
            String updateInput = "S001\nUpdated Series\n25\n14\n20\n";
            updateSeriesWithInput(updateInput);
            
            String updateOutput = getOutput();
            assertAll("Should handle invalid age during update",
                () -> assertTrue(updateOutput.contains("Series updated successfully!"), 
                    "Should eventually update successfully"),
                () -> assertTrue(updateOutput.contains("You have entered an incorrect age restriction: 25"), 
                    "Should show invalid age message")
            );
        }
    }
}
