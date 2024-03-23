package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Services.AirportDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AirportController implements Initializable {

    private final AirportDAO airportDAO = new AirportDAO();

    @FXML
    private TableView<Airport> airportTableView;

    @FXML
    private TableColumn<Airport, String> airportCodeColumn;

    @FXML
    private TableColumn<Airport, String> airportNameColumn;

    @FXML
    private TableColumn<Airport, String> airportCityColumn;

    @FXML
    private TableColumn<Airport, String> airportCountryColumn;

    @FXML
    private TextField newAirportCodeField;

    @FXML
    private TextField newAirportNameField;

    @FXML
    private TextField newAirportCityField;

    @FXML
    private TextField newAirportCountryField;

    @FXML
    private Button newAirportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("AirportController initialized");
        configureTableView(); // Call to configureTableView() method
        refreshTableView();

        airportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }


    private void configureTableView() {
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        airportNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        airportCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        airportCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }




    @FXML
    void handleNewAirportButton() {
        // Retrieve data from input fields
        String code = newAirportCodeField.getText();
        String name = newAirportNameField.getText();
        String city = newAirportCityField.getText();
        String country = newAirportCountryField.getText();

        // Assuming userId is available in your application, retrieve it accordingly
        //int userId = getUserIdFromSomeSource(); // Implement this method as per your application's logic

        // Create a new Airport object with the retrieved userId
        Airport newAirport = new Airport( 1, code, name, city, country);

        // Add the new airport to the database
        airportDAO.addAirport(newAirport);

        // Refresh the table view to display the updated list of airports
        refreshTableView();

        // Clear input fields after adding airport
        clearFields();
    }


    // Method to refresh the table view with the latest data from the database
    private void refreshTableView() {
        // Retrieve all airports from the database
        List<Airport> airports = airportDAO.getAllAirports();

        // Print out the retrieved airports for debugging
        System.out.println("Retrieved airports: " + airports);

        // Clear existing items in the table
        airportTableView.getItems().clear();

        // Create an observable list from the retrieved airports
        ObservableList<Airport> airportObservableList = FXCollections.observableArrayList(airports);

        // Set the items of the table view
        airportTableView.setItems(airportObservableList);
    }







    @FXML
    void handleModifyAirportButton() {
        Airport selectedAirport = airportTableView.getSelectionModel().getSelectedItem();
        if (selectedAirport != null) {
            // Retrieve modified data from input fields
            String code = newAirportCodeField.getText();
            String name = newAirportNameField.getText();
            String city = newAirportCityField.getText();
            String country = newAirportCountryField.getText();

            // Update the selected airport with modified data
            selectedAirport.setCode(code);
            selectedAirport.setName(name);
            selectedAirport.setCity(city);
            selectedAirport.setCountry(country);

            // Update the airport in the database
            airportDAO.updateAirport(selectedAirport);

            // Refresh the table view to reflect the changes
            refreshTableView();

            // Clear input fields after modification
            clearFields();
        }
    }


    private void populateFields(Airport airport) {
        newAirportCodeField.setText(airport.getCode());
        newAirportNameField.setText(airport.getName());
        newAirportCityField.setText(airport.getCity());
        newAirportCountryField.setText(airport.getCountry());
    }


    @FXML
    void handleDeleteAirportButton() {
        Airport selectedAirport = airportTableView.getSelectionModel().getSelectedItem();
        if (selectedAirport != null) {
            // Delete the selected airport from the database
            airportDAO.deleteAirport(selectedAirport.getId());

            // Refresh the table view to reflect the deletion
            refreshTableView();
        }
    }




    private void clearFields() {
        newAirportCodeField.clear();
        newAirportNameField.clear();
        newAirportCityField.clear();
        newAirportCountryField.clear();
    }

}
