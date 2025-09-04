package com.mycompany.st10467189movieseriesapplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SeriesModelTest {
    
    private SeriesModel seriesModel;
    
    @BeforeEach
    void setUp() {
        seriesModel = new SeriesModel();
    }
    
    @Test
    void testDefaultConstructor() {
        // Test default constructor initializes empty strings
        assertEquals("", seriesModel.getSeriesId());
        assertEquals("", seriesModel.getSeriesName());
        assertEquals("", seriesModel.getSeriesAge());
        assertEquals("", seriesModel.getSeriesNumberOfEpisodes());
    }
    
    @Test
    void testParameterizedConstructor() {
        // Test parameterized constructor with valid data
        SeriesModel testSeries = new SeriesModel("S001", "Test Series", "12", "24");
        
        assertEquals("S001", testSeries.getSeriesId());
        assertEquals("Test Series", testSeries.getSeriesName());
        assertEquals("12", testSeries.getSeriesAge());
        assertEquals("24", testSeries.getSeriesNumberOfEpisodes());
    }
    
    @Test
    void testSetAndGetSeriesId() {
        // Test setting and getting series ID
        seriesModel.setSeriesId("S001");
        assertEquals("S001", seriesModel.getSeriesId());
        
        // Test with empty string
        seriesModel.setSeriesId("");
        assertEquals("", seriesModel.getSeriesId());
        
        // Test with null (should handle gracefully)
        seriesModel.setSeriesId(null);
        assertNull(seriesModel.getSeriesId());
    }
    
    @Test
    void testSetAndGetSeriesName() {
        // Test setting and getting series name
        seriesModel.setSeriesName("Breaking Bad");
        assertEquals("Breaking Bad", seriesModel.getSeriesName());
        
        // Test with empty string
        seriesModel.setSeriesName("");
        assertEquals("", seriesModel.getSeriesName());
        
        // Test with null
        seriesModel.setSeriesName(null);
        assertNull(seriesModel.getSeriesName());
    }
    
    @Test
    void testSetAndGetSeriesAge() {
        // Test setting and getting series age
        seriesModel.setSeriesAge("16");
        assertEquals("16", seriesModel.getSeriesAge());
        
        // Test with empty string
        seriesModel.setSeriesAge("");
        assertEquals("", seriesModel.getSeriesAge());
        
        // Test with null
        seriesModel.setSeriesAge(null);
        assertNull(seriesModel.getSeriesAge());
    }
    
    @Test
    void testSetAndGetSeriesNumberOfEpisodes() {
        // Test setting and getting number of episodes
        seriesModel.setSeriesNumberOfEpisodes("62");
        assertEquals("62", seriesModel.getSeriesNumberOfEpisodes());
        
        // Test with empty string
        seriesModel.setSeriesNumberOfEpisodes("");
        assertEquals("", seriesModel.getSeriesNumberOfEpisodes());
        
        // Test with null
        seriesModel.setSeriesNumberOfEpisodes(null);
        assertNull(seriesModel.getSeriesNumberOfEpisodes());
    }
    
    @Test
    void testMultipleUpdates() {
        // Test multiple updates to the same object
        seriesModel.setSeriesId("S001");
        seriesModel.setSeriesName("Test Series");
        seriesModel.setSeriesAge("14");
        seriesModel.setSeriesNumberOfEpisodes("10");
        
        assertEquals("S001", seriesModel.getSeriesId());
        assertEquals("Test Series", seriesModel.getSeriesName());
        assertEquals("14", seriesModel.getSeriesAge());
        assertEquals("10", seriesModel.getSeriesNumberOfEpisodes());
        
        // Update some values
        seriesModel.setSeriesName("Updated Series");
        seriesModel.setSeriesNumberOfEpisodes("20");
        
        assertEquals("S001", seriesModel.getSeriesId()); // Should remain unchanged
        assertEquals("Updated Series", seriesModel.getSeriesName());
        assertEquals("14", seriesModel.getSeriesAge()); // Should remain unchanged
        assertEquals("20", seriesModel.getSeriesNumberOfEpisodes());
    }
    
    @Test
    void testEdgeCases() {
        // Test with very long strings
        String longString = "A".repeat(1000);
        seriesModel.setSeriesName(longString);
        assertEquals(longString, seriesModel.getSeriesName());
        
        // Test with special characters
        seriesModel.setSeriesId("S@#$%^&*()");
        assertEquals("S@#$%^&*()", seriesModel.getSeriesId());
        
        // Test with numbers as strings
        seriesModel.setSeriesAge("18");
        assertEquals("18", seriesModel.getSeriesAge());
    }
}

