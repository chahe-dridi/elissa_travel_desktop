package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;

import com.example.elissa.Models.Airport;
import com.example.elissa.Services.AirportDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import java.util.regex.Pattern;

public class AirportController implements Initializable {

    private final AirportDAO airportDAO = new AirportDAO();

    @FXML
    private TableView<Airport> airportTableView;

    @FXML
    private TableColumn<Airport, String> airportIdColumn;

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



    @FXML
    private TextField searchAirportCodeField;



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
        airportIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        airportCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        airportNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        airportCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        airportCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
    }




//-----------------------------------

    private boolean isValidInput(String str) {
        return !str.isEmpty() && str.matches("^[a-zA-Z\\s'\"./]+$");
    }

    // Method to display an alert dialog
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //-----------------------------------
    @FXML
    void handleNewAirportButton() {
        // Retrieve data from input fields
        String code = newAirportCodeField.getText().trim();
        String name = newAirportNameField.getText().trim();
        String city = newAirportCityField.getText().trim();
        String country = newAirportCountryField.getText().trim();

        // Validate input fields
        if (!isValidInput(code) || !isValidInput(name) || !isValidInput(city) || !isValidInput(country)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Fields cannot be empty and must contain valid characters.");
            return;
        }

        // Create a new Airport object
        Airport newAirport = new Airport(1, code, name, city, country);

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
            String code = newAirportCodeField.getText().trim();
            String name = newAirportNameField.getText().trim();
            String city = newAirportCityField.getText().trim();
            String country = newAirportCountryField.getText().trim();

            // Validate input fields
            if (!isValidInput(code) || !isValidInput(name) || !isValidInput(city) || !isValidInput(country)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Fields cannot be empty and must contain valid characters.");
                return;
            }

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






    // Method to handle search action when text changes in the search field






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

    @FXML
    void handleSearchAirportFieldTextChanged() {
        String searchCode = searchAirportCodeField.getText().trim();
        if (!searchCode.isEmpty()) {
            // Perform search by code
            List<Airport> searchResult = airportDAO.searchAirportByCode(searchCode);
            // Update the table view with search result
            updateTableView(searchResult);
        } else {
            // If search code is empty, refresh the table view with all airports
            refreshTableView();
        }
    }


    private void updateTableView(List<Airport> searchResult) {
        // Clear existing items in the table
        airportTableView.getItems().clear();
        // Create an observable list from the search result
        ObservableList<Airport> airportObservableList = FXCollections.observableArrayList(searchResult);
        // Set the items of the table view
        airportTableView.setItems(airportObservableList);
    }

    private void clearFields() {
        newAirportCodeField.clear();
        newAirportNameField.clear();
        newAirportCityField.clear();
        newAirportCountryField.clear();
    }











    @FXML
    void handleAirportsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/index.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) airportTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flight.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) airportTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightClassButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flightclass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) airportTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleReservationButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationvoladmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) airportTableView.getScene().getWindow();
        stage.setScene(scene);
    }











}
