package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Services.AirportDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportStatController {

    private final AirportDAO airportDAO = new AirportDAO();
    @FXML
    private PieChart airportPieChart;

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
    }




}



