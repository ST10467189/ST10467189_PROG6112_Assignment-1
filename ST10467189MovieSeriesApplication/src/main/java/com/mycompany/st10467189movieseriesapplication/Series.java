package com.mycompany.st10467189movieseriesapplication;
/**
 *
 * @author ST10467189 Leonard McDermott
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Series {

    private ArrayList<SeriesModel> seriesCollection = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // 1.10 Minimum methods
    public void captureSeries() {
        // Implementation for capturing a new series
        SeriesModel newSeries = new SeriesModel();
        System.out.println("Enter the series ID: ");
        newSeries.setSeriesId(scanner.nextLine());
        
        System.out.println("Enter the series name: ");
        newSeries.setSeriesName(scanner.nextLine());
        
        // Handling age restriction validation
        while(true) {
            try {
                System.out.println("Enter the series age restriction (2-18): ");
                String ageInput = scanner.nextLine();
                int age = Integer.parseInt(ageInput);
                if (age >= 2 && age <= 18) {
                    newSeries.setSeriesAge(ageInput);
                    break;
                } else {
                    System.out.println("You have entered an incorrect age restriction: " + ageInput);
                    System.out.println("Please re-enter the series age (2-18): ");
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered an incorrect non-number age restriction!");
                System.out.println("Please re-enter the series age (2-18): ");
            }
        }

        System.out.println("Enter the number of episodes for " + newSeries.getSeriesName() + ": ");
        newSeries.setSeriesNumberOfEpisodes(scanner.nextLine());

        seriesCollection.add(newSeries);
        System.out.println("Series processed successfully!!!");
    }

    public void searchSeries() {
        // Implementation for searching a series by ID
        System.out.println("Enter the series ID to search: ");
        String searchId = scanner.nextLine();
        boolean found = false;
        for (SeriesModel series : seriesCollection) {
            if (series.getSeriesId().equals(searchId)) {
                System.out.println("SERIES ID: " + series.getSeriesId());
                System.out.println("SERIES NAME: " + series.getSeriesName());
                System.out.println("SERIES AGE RESTRICTION: " + series.getSeriesAge());
                System.out.println("NUMBER OF EPISODES: " + series.getSeriesNumberOfEpisodes());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Series with Series ID: " + searchId + " was not found!");
        }
    }

    public void updateSeries() {
        // Implementation for updating a series by ID
        System.out.println("Enter the series ID to update: ");
        String updateId = scanner.nextLine();
        boolean found = false;
        for (SeriesModel series : seriesCollection) {
            if (series.getSeriesId().equals(updateId)) {
                System.out.println("Enter the new series name: ");
                series.setSeriesName(scanner.nextLine());
                
                // Handling age restriction validation
                while(true) {
                    try {
                        System.out.println("Enter the new age restriction (2-18): ");
                        String ageInput = scanner.nextLine();
                        int age = Integer.parseInt(ageInput);
                        if (age >= 2 && age <= 18) {
                            series.setSeriesAge(ageInput);
                            break;
                        } else {
                            System.out.println("You have entered an incorrect age restriction: " + ageInput);
                            System.out.println("Please re-enter the series age (2-18): ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("You have entered an incorrect non-number age restriction!");
                        System.out.println("Please re-enter the series age (2-18): ");
                    }
                }
                
                System.out.println("Enter the new number of episodes: ");
                series.setSeriesNumberOfEpisodes(scanner.nextLine());
                System.out.println("Series updated successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Series with Series ID: " + updateId + " was not found!");
        }
    }

    public void deleteSeries() {
        // Implementation for deleting a series by ID
        System.out.println("Enter the series ID to delete: ");
        String deleteId = scanner.nextLine();
        SeriesModel seriesToDelete = null;
        for (SeriesModel series : seriesCollection) {
            if (series.getSeriesId().equals(deleteId)) {
                seriesToDelete = series;
                break;
            }
        }

        if (seriesToDelete != null) {
            System.out.println("Are you sure you want to delete series " + deleteId + " from the system? Yes (y) to delete.");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("y")) {
                seriesCollection.remove(seriesToDelete);
                System.out.println("Series with Series ID: " + deleteId + " WAS deleted!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Series with Series ID: " + deleteId + " was not found!");
        }
    }

    public void seriesReport() {
        // Implementation for printing a report of all series
        System.out.println("Sample Report Screen Shot");
        int seriesCount = 1;
        for (SeriesModel series : seriesCollection) {
            System.out.println("Series " + seriesCount++);
            System.out.println("-------------------------------------");
            System.out.println("SERIES ID: " + series.getSeriesId());
            System.out.println("SERIES NAME: " + series.getSeriesName());
            System.out.println("SERIES AGE RESTRICTION: " + series.getSeriesAge());
            System.out.println("NUMBER OF EPISODES: " + series.getSeriesNumberOfEpisodes());
            System.out.println();
        }
    }
    
    public void exitSeriesApplication() {
        // Exits the application
        System.out.println("Exiting the application. Goodbye!");
        scanner.close();
        System.exit(0);
    }
    
    // Method to close scanner when done
    public void closeScanner() {
        scanner.close();
    }
}