package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Services.AirportDAO;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportStatController {

    private final AirportDAO airportDAO = new AirportDAO();
    @FXML
    private PieChart airportPieChart;
    @FXML
    private Label totalAirportsLabel;

    @FXML
    void initialize() throws SQLException {
        refreshPieChart();
    }

    @FXML
    void refreshPieChart() throws SQLException {
        // Retrieve all airports from the database
        List<Airport> airports = airportDAO.getAllAirports();

        // Create a map to store country-wise airport counts
        Map<String, Integer> airportCounts = new HashMap<>();

        // Count the number of airports in each country
        for (Airport airport : airports) {
            String country = airport.getCountry();
            airportCounts.put(country, airportCounts.getOrDefault(country, 0) + 1);
        }

        // Create an observable list of pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Add data to the pie chart
        for (Map.Entry<String, Integer> entry : airportCounts.entrySet()) {
            String country = entry.getKey();
            int count = entry.getValue();
            // Create a label with both country name and count
            String label = String.format("%s (%d)", country, count);
            pieChartData.add(new PieChart.Data(label, count));
        }

        // Set the data to the pie chart
        airportPieChart.setData(pieChartData);

        // Update total airports label with animation
        int totalAirports = airports.size();
        totalAirportsLabel.setText("Total Airports: " + totalAirports);
        animateTotalAirportsLabel();
    }

    private void animateTotalAirportsLabel() {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), totalAirportsLabel);
        tt.setByY(20);
        tt.play();
        totalAirportsLabel.getStyleClass().add("shown");
    }
}
