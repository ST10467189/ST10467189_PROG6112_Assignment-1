package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SeriesModel Test Suite")
public class SeriesModelTest {
    
    private SeriesModel seriesModel;
    
    @BeforeEach
    void setUp() {
        seriesModel = new SeriesModel();
    }
    
    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        
        @Test
        @DisplayName("Default constructor should initialize with empty strings")
        void testDefaultConstructor() {
            // Test default constructor initializes empty strings
            assertEquals("", seriesModel.getSeriesId(), "SeriesId should be empty string");
            assertEquals("", seriesModel.getSeriesName(), "SeriesName should be empty string");
            assertEquals("", seriesModel.getSeriesAge(), "SeriesAge should be empty string");
            assertEquals("", seriesModel.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should be empty string");
        }
        
        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void testParameterizedConstructor() {
            // Test parameterized constructor with valid data
            SeriesModel testSeries = new SeriesModel("S001", "Test Series", "12", "24");
            
            assertEquals("S001", testSeries.getSeriesId(), "SeriesId should match constructor parameter");
            assertEquals("Test Series", testSeries.getSeriesName(), "SeriesName should match constructor parameter");
            assertEquals("12", testSeries.getSeriesAge(), "SeriesAge should match constructor parameter");
            assertEquals("24", testSeries.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should match constructor parameter");
        }
        
        @Test
        @DisplayName("Parameterized constructor should handle null values")
        void testParameterizedConstructorWithNulls() {
            SeriesModel testSeries = new SeriesModel(null, null, null, null);
            
            assertNull(testSeries.getSeriesId(), "SeriesId should be null");
            assertNull(testSeries.getSeriesName(), "SeriesName should be null");
            assertNull(testSeries.getSeriesAge(), "SeriesAge should be null");
            assertNull(testSeries.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should be null");
        }
    }
    
    @Nested
    @DisplayName("SeriesId Tests")
    class SeriesIdTests {
        
        @Test
        @DisplayName("Should set and get series ID correctly")
        void testSetAndGetSeriesId() {
            seriesModel.setSeriesId("S001");
            assertEquals("S001", seriesModel.getSeriesId(), "SeriesId should be set correctly");
        }
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"S001", "SERIES_123", "S@#$%^&*()"})
        @DisplayName("Should handle various series ID values")
        void testSeriesIdWithVariousValues(String seriesId) {
            seriesModel.setSeriesId(seriesId);
            assertEquals(seriesId, seriesModel.getSeriesId(), "SeriesId should handle: " + seriesId);
        }
        
        @Test
        @DisplayName("Should handle very long series ID")
        void testSeriesIdWithVeryLongString() {
            String longString = "A".repeat(1000);
            seriesModel.setSeriesId(longString);
            assertEquals(longString, seriesModel.getSeriesId(), "SeriesId should handle very long strings");
        }
    }
    
    @Nested
    @DisplayName("SeriesName Tests")
    class SeriesNameTests {
        
        @Test
        @DisplayName("Should set and get series name correctly")
        void testSetAndGetSeriesName() {
            seriesModel.setSeriesName("Breaking Bad");
            assertEquals("Breaking Bad", seriesModel.getSeriesName(), "SeriesName should be set correctly");
        }
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"Breaking Bad", "Game of Thrones", "Stranger Things"})
        @DisplayName("Should handle various series name values")
        void testSeriesNameWithVariousValues(String seriesName) {
            seriesModel.setSeriesName(seriesName);
            assertEquals(seriesName, seriesModel.getSeriesName(), "SeriesName should handle: " + seriesName);
        }
        
        @Test
        @DisplayName("Should handle very long series name")
        void testSeriesNameWithVeryLongString() {
            String longString = "A".repeat(1000);
            seriesModel.setSeriesName(longString);
            assertEquals(longString, seriesModel.getSeriesName(), "SeriesName should handle very long strings");
        }
    }
    
    @Nested
    @DisplayName("SeriesAge Tests")
    class SeriesAgeTests {
        
        @Test
        @DisplayName("Should set and get series age correctly")
        void testSetAndGetSeriesAge() {
            seriesModel.setSeriesAge("16");
            assertEquals("16", seriesModel.getSeriesAge(), "SeriesAge should be set correctly");
        }
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"2", "10", "16", "18", "0", "25", "abc", "12.5"})
        @DisplayName("Should handle various age values as strings")
        void testSeriesAgeWithVariousValues(String age) {
            seriesModel.setSeriesAge(age);
            assertEquals(age, seriesModel.getSeriesAge(), "SeriesAge should handle: " + age);
        }
    }
    
    @Nested
    @DisplayName("SeriesNumberOfEpisodes Tests")
    class SeriesNumberOfEpisodesTests {
        
        @Test
        @DisplayName("Should set and get number of episodes correctly")
        void testSetAndGetSeriesNumberOfEpisodes() {
            seriesModel.setSeriesNumberOfEpisodes("62");
            assertEquals("62", seriesModel.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should be set correctly");
        }
        
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"1", "10", "62", "100", "0", "abc", "12.5", "999999"})
        @DisplayName("Should handle various episode count values")
        void testSeriesNumberOfEpisodesWithVariousValues(String episodes) {
            seriesModel.setSeriesNumberOfEpisodes(episodes);
            assertEquals(episodes, seriesModel.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should handle: " + episodes);
        }
    }
    
    @Nested
    @DisplayName("Multiple Updates Tests")
    class MultipleUpdatesTests {
        
        @Test
        @DisplayName("Should handle multiple updates to the same object")
        void testMultipleUpdates() {
            // Initial setup
            seriesModel.setSeriesId("S001");
            seriesModel.setSeriesName("Test Series");
            seriesModel.setSeriesAge("14");
            seriesModel.setSeriesNumberOfEpisodes("10");
            
            // Verify initial values
            assertAll("Initial values should be set correctly",
                () -> assertEquals("S001", seriesModel.getSeriesId()),
                () -> assertEquals("Test Series", seriesModel.getSeriesName()),
                () -> assertEquals("14", seriesModel.getSeriesAge()),
                () -> assertEquals("10", seriesModel.getSeriesNumberOfEpisodes())
            );
            
            // Update some values
            seriesModel.setSeriesName("Updated Series");
            seriesModel.setSeriesNumberOfEpisodes("20");
            
            // Verify updated values
            assertAll("Updated values should be correct",
                () -> assertEquals("S001", seriesModel.getSeriesId(), "SeriesId should remain unchanged"),
                () -> assertEquals("Updated Series", seriesModel.getSeriesName(), "SeriesName should be updated"),
                () -> assertEquals("14", seriesModel.getSeriesAge(), "SeriesAge should remain unchanged"),
                () -> assertEquals("20", seriesModel.getSeriesNumberOfEpisodes(), "SeriesNumberOfEpisodes should be updated")
            );
        }
        
        @Test
        @DisplayName("Should handle complete object replacement")
        void testCompleteObjectReplacement() {
            // Set initial values
            seriesModel.setSeriesId("S001");
            seriesModel.setSeriesName("Original Series");
            seriesModel.setSeriesAge("12");
            seriesModel.setSeriesNumberOfEpisodes("5");
            
            // Replace all values
            seriesModel.setSeriesId("S002");
            seriesModel.setSeriesName("New Series");
            seriesModel.setSeriesAge("16");
            seriesModel.setSeriesNumberOfEpisodes("20");
            
            // Verify all values are replaced
            assertAll("All values should be replaced",
                () -> assertEquals("S002", seriesModel.getSeriesId()),
                () -> assertEquals("New Series", seriesModel.getSeriesName()),
                () -> assertEquals("16", seriesModel.getSeriesAge()),
                () -> assertEquals("20", seriesModel.getSeriesNumberOfEpisodes())
            );
        }
    }
    
    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {
        
        @Test
        @DisplayName("Should handle very long strings")
        void testVeryLongStrings() {
            String longString = "A".repeat(10000);
            seriesModel.setSeriesName(longString);
            assertEquals(longString, seriesModel.getSeriesName(), "Should handle very long strings");
        }
        
        @Test
        @DisplayName("Should handle special characters")
        void testSpecialCharacters() {
            String specialChars = "S@#$%^&*()_+-=[]{}|;':\",./<>?";
            seriesModel.setSeriesId(specialChars);
            assertEquals(specialChars, seriesModel.getSeriesId(), "Should handle special characters");
        }
        
        @Test
        @DisplayName("Should handle unicode characters")
        void testUnicodeCharacters() {
            String unicodeString = "Série 测试 🎬";
            seriesModel.setSeriesName(unicodeString);
            assertEquals(unicodeString, seriesModel.getSeriesName(), "Should handle unicode characters");
        }
        
        @Test
        @DisplayName("Should handle whitespace-only strings")
        void testWhitespaceOnlyStrings() {
            String whitespace = "   \t\n   ";
            seriesModel.setSeriesName(whitespace);
            assertEquals(whitespace, seriesModel.getSeriesName(), "Should handle whitespace-only strings");
        }
    }
    
    @Nested
    @DisplayName("Object State Tests")
    class ObjectStateTests {
        
        @Test
        @DisplayName("Should maintain object state consistency")
        void testObjectStateConsistency() {
            // Create a series with all fields
            SeriesModel testSeries = new SeriesModel("S001", "Test Series", "14", "10");
            
            // Verify all fields are accessible and consistent
            assertAll("Object state should be consistent",
                () -> assertNotNull(testSeries.getSeriesId()),
                () -> assertNotNull(testSeries.getSeriesName()),
                () -> assertNotNull(testSeries.getSeriesAge()),
                () -> assertNotNull(testSeries.getSeriesNumberOfEpisodes())
            );
        }
        
        @Test
        @DisplayName("Should handle mixed null and non-null values")
        void testMixedNullAndNonNullValues() {
            seriesModel.setSeriesId("S001");
            seriesModel.setSeriesName(null);
            seriesModel.setSeriesAge("16");
            seriesModel.setSeriesNumberOfEpisodes(null);
            
            assertAll("Should handle mixed null and non-null values",
                () -> assertEquals("S001", seriesModel.getSeriesId()),
                () -> assertNull(seriesModel.getSeriesName()),
                () -> assertEquals("16", seriesModel.getSeriesAge()),
                () -> assertNull(seriesModel.getSeriesNumberOfEpisodes())
            );
        }
    }
}
