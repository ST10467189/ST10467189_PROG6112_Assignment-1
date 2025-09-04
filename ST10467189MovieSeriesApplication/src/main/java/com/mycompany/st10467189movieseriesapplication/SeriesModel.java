package com.mycompany.st10467189movieseriesapplication;
/**
 *
 * @author ST10467189 Leonard McDermott
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

