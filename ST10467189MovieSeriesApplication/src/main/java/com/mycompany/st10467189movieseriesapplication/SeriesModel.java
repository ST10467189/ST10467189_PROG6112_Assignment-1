package com.mycompany.st10467189movieseriesapplication;

/**
 * Series Data Model Class
 * 
 * This class represents the data model for a movie series, containing
 * all necessary properties and methods for data management.
 * 
 * @author ST10467189 Leonard McDermott
 * 
 * // REFERENCE LIST //
 * 
 * References
 * 
 * AI Assistant Collaboration. (2025, January 15). JUnit 5 test suite development with comprehensive unit testing. 
 * Retrieved from https://github.com/junit-team/junit5
 * 
 * JUnit Team. (2025). JUnit 5 User Guide - Assertions. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
 * 
 * JUnit Team. (2025). JUnit 5 User Guide - Parameterized Tests. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
 * 
 * JUnit Team. (2025). JUnit 5 API - NullAndEmptySource. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/NullAndEmptySource.html
 * 
 * JUnit Team. (2025). JUnit 5 API - ValueSource. Retrieved January 15, 2025, from 
 * https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/ValueSource.html
 * 
 * Oracle Corporation. (2025). Java Documentation - Object Class. Retrieved January 15, 2025, from 
 * https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html
 * 
 * JUnit Test Coverage Implementation (2025, January 15):
 * 
 * - Constructor testing (default and parameterized)
 * - Getter and setter method validation
 * - Edge case handling (null values, empty strings, special characters)
 * - Object state consistency testing
 * - Mixed null and non-null value testing
 * - Unicode and whitespace handling
 * 
 * Test Features Used (2025, January 15):
 * - @NullAndEmptySource for null and empty value testing
 * - @ValueSource for various input value testing
 * - assertAll() for grouped assertions with individual failure messages
 * - Comprehensive test coverage with descriptive test names
 * 
 * // END REFERENCE LIST //
 */
public class SeriesModel {
    private String seriesId;
    private String seriesName;
    private String seriesAge;
    private String seriesNumberOfEpisodes;
    
    // Default constructor
    public SeriesModel() {
        this.seriesId = "";
        this.seriesName = "";
        this.seriesAge = "";
        this.seriesNumberOfEpisodes = "";
    }
    
    // Parameterized constructor
    public SeriesModel(String seriesId, String seriesName, String seriesAge, String seriesNumberOfEpisodes) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.seriesAge = seriesAge;
        this.seriesNumberOfEpisodes = seriesNumberOfEpisodes;
    }
    
    // Getters
    public String getSeriesId() { return seriesId; }
    public String getSeriesName() { return seriesName; }
    public String getSeriesAge() { return seriesAge; }
    public String getSeriesNumberOfEpisodes() { return seriesNumberOfEpisodes; }
    
    // Setters
    public void setSeriesId(String seriesId) { this.seriesId = seriesId; }
    public void setSeriesName(String seriesName) { this.seriesName = seriesName; }
    public void setSeriesAge(String seriesAge) { this.seriesAge = seriesAge; }
    public void setSeriesNumberOfEpisodes(String seriesNumberOfEpisodes) { this.seriesNumberOfEpisodes = seriesNumberOfEpisodes; }
}
