package com.example.elissa.Controller;

import com.example.elissa.Models.Flight;
import com.example.elissa.Services.FlightDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.time.format.DateTimeFormatter;





public class FlightController implements Initializable {

    private final FlightDAO flightDAO = new FlightDAO();

    @FXML
    private TableView<Flight> flightTableView;

    @FXML
    private TableColumn<Flight, Integer> flightIdColumn;

    @FXML
    private TableColumn<Flight, Integer> flightAirportDepartIdColumn;

    @FXML
    private TableColumn<Flight, Integer> flightAirportArriveIdColumn;

    @FXML
    private TableColumn<Flight, Integer> flightVolclassIdColumn;

    @FXML
    private TableColumn<Flight, Integer> flightUserIdColumn;

    @FXML
    private TableColumn<Flight, String> flightCompagnieAerienneColumn;

    @FXML
    private TableColumn<Flight, LocalDateTime> flightHeureDepartColumn;

    @FXML
    private TableColumn<Flight, LocalDateTime> flightHeureArriveColumn;

    @FXML
    private TableColumn<Flight, Boolean> flightDisponibleColumn;

    @FXML
    private TextField newFlightAirportDepartIdField;

    @FXML
    private TextField newFlightAirportArriveIdField;

    @FXML
    private TextField newFlightVolclassIdField;

    @FXML
    private TextField newFlightUserIdField;

    @FXML
    private TextField newFlightCompagnieAerienneField;

    @FXML
    private DatePicker newFlightDepartureDateField;

    @FXML
    private TextField newFlightDepartureTimeField;

    @FXML
    private DatePicker newFlightArrivalDateField;

    @FXML
    private TextField newFlightArrivalTimeField;

    @FXML
    private CheckBox newFlightDisponibleCheckbox;

    @FXML
    private Button newFlightButton;

    @FXML
    private Button modifyFlightButton;

    @FXML
    private Button deleteFlightButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableView();
        refreshTableView();

        // Add listener to handle selection changes in the table view
        flightTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void configureTableView() {
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        flightAirportDepartIdColumn.setCellValueFactory(new PropertyValueFactory<>("airportDepartId"));
        flightAirportArriveIdColumn.setCellValueFactory(new PropertyValueFactory<>("airportArriveId"));
        flightVolclassIdColumn.setCellValueFactory(new PropertyValueFactory<>("volclassId"));
        flightUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        flightCompagnieAerienneColumn.setCellValueFactory(new PropertyValueFactory<>("compagnieAerienne"));
        flightHeureDepartColumn.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
        flightHeureArriveColumn.setCellValueFactory(new PropertyValueFactory<>("heureArrive"));
        flightDisponibleColumn.setCellValueFactory(new PropertyValueFactory<>("disponible"));
    }

    private void refreshTableView() {
        List<Flight> flights = flightDAO.getAllFlights();
        ObservableList<Flight> flightObservableList = FXCollections.observableArrayList(flights);
        flightTableView.setItems(flightObservableList);
    }









 /*   @FXML
    void handleNewFlightButton() {
        int airportDepartId = Integer.parseInt(newFlightAirportDepartIdField.getText());
        int airportArriveId = Integer.parseInt(newFlightAirportArriveIdField.getText());
        int volclassId = Integer.parseInt(newFlightVolclassIdField.getText());
        int userId = Integer.parseInt(newFlightUserIdField.getText());
        String compagnieAerienne = newFlightCompagnieAerienneField.getText();

        // Parse departure date and time
        LocalDateTime heureDepart = LocalDateTime.of(
                newFlightDepartureDateField.getValue(),
                LocalTime.parse(newFlightDepartureTimeField.getText())
        );

        // Parse arrival date and time
        LocalDateTime heureArrive = LocalDateTime.of(
                newFlightArrivalDateField.getValue(),
                LocalTime.parse(newFlightArrivalTimeField.getText())
        );

        boolean disponible = newFlightDisponibleCheckbox.isSelected();

        Flight newFlight = new Flight(airportDepartId, airportArriveId, volclassId, userId, compagnieAerienne, heureDepart, heureArrive, disponible);
        flightDAO.addFlight(newFlight);
        refreshTableView();
        clearFields();
    }

    @FXML
    void handleModifyFlightButton() {
        Flight selectedFlight = flightTableView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            int id = selectedFlight.getId();
            int airportDepartId = Integer.parseInt(newFlightAirportDepartIdField.getText());
            int airportArriveId = Integer.parseInt(newFlightAirportArriveIdField.getText());
            int volclassId = Integer.parseInt(newFlightVolclassIdField.getText());
            int userId = Integer.parseInt(newFlightUserIdField.getText());
            String compagnieAerienne = newFlightCompagnieAerienneField.getText();

            // Define custom date-time formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // Parse departure date-time
            LocalDateTime heureDepart = LocalDateTime.parse(newFlightDepartureDateField.getValue() + " " + newFlightDepartureTimeField.getText(), formatter);

            // Parse arrival date-time
            LocalDateTime heureArrive = LocalDateTime.parse(newFlightArrivalDateField.getValue() + " " + newFlightArrivalTimeField.getText(), formatter);

            boolean disponible = newFlightDisponibleCheckbox.isSelected();

            Flight modifiedFlight = new Flight(id, airportDepartId, airportArriveId, volclassId, userId, compagnieAerienne, heureDepart, heureArrive, disponible);
            flightDAO.updateFlight(modifiedFlight);
            refreshTableView();
            clearFields();
        }
    }*/



    @FXML
    void handleNewFlightButton() {
        String airportDepartIdText = newFlightAirportDepartIdField.getText().trim();
        String airportArriveIdText = newFlightAirportArriveIdField.getText().trim();
        String volclassIdText = newFlightVolclassIdField.getText().trim();
        String userIdText = newFlightUserIdField.getText().trim();
        String compagnieAerienne = newFlightCompagnieAerienneField.getText().trim();
        LocalDate departureDate = newFlightDepartureDateField.getValue();
        String departureTimeText = newFlightDepartureTimeField.getText().trim();
        LocalDate arrivalDate = newFlightArrivalDateField.getValue();
        String arrivalTimeText = newFlightArrivalTimeField.getText().trim();

        // Validate input fields
        if (airportDepartIdText.isEmpty() || airportArriveIdText.isEmpty() || volclassIdText.isEmpty() || userIdText.isEmpty() || compagnieAerienne.isEmpty() || departureDate == null || departureTimeText.isEmpty() || arrivalDate == null || arrivalTimeText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all fields.");
            return;
        }

        int airportDepartId = Integer.parseInt(airportDepartIdText);
        int airportArriveId = Integer.parseInt(airportArriveIdText);
        int volclassId = Integer.parseInt(volclassIdText);
        int userId = Integer.parseInt(userIdText);

        // Parse departure date-time
        LocalDateTime heureDepart = LocalDateTime.of(departureDate, LocalTime.parse(departureTimeText));

        // Parse arrival date-time
        LocalDateTime heureArrive = LocalDateTime.of(arrivalDate, LocalTime.parse(arrivalTimeText));

        // Validate departure and arrival date-time
        if (heureDepart.isBefore(LocalDateTime.now())) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Departure date-time must be today or later.");
            return;
        }

        if (heureArrive.isBefore(heureDepart)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Arrival date-time must be after departure date-time.");
            return;
        }

        boolean disponible = newFlightDisponibleCheckbox.isSelected();

        Flight newFlight = new Flight(airportDepartId, airportArriveId, volclassId, userId, compagnieAerienne, heureDepart, heureArrive, disponible);
        flightDAO.addFlight(newFlight);
        refreshTableView();
        clearFields();
    }

    @FXML
    void handleModifyFlightButton() {
        Flight selectedFlight = flightTableView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            int id = selectedFlight.getId();
            String airportDepartIdText = newFlightAirportDepartIdField.getText().trim();
            String airportArriveIdText = newFlightAirportArriveIdField.getText().trim();
            String volclassIdText = newFlightVolclassIdField.getText().trim();
            String userIdText = newFlightUserIdField.getText().trim();
            String compagnieAerienne = newFlightCompagnieAerienneField.getText().trim();
            LocalDate departureDate = newFlightDepartureDateField.getValue();
            String departureTimeText = newFlightDepartureTimeField.getText().trim();
            LocalDate arrivalDate = newFlightArrivalDateField.getValue();
            String arrivalTimeText = newFlightArrivalTimeField.getText().trim();

            // Validate input fields
            if (airportDepartIdText.isEmpty() || airportArriveIdText.isEmpty() || volclassIdText.isEmpty() || userIdText.isEmpty() || compagnieAerienne.isEmpty() || departureDate == null || departureTimeText.isEmpty() || arrivalDate == null || arrivalTimeText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all fields.");
                return;
            }

            int airportDepartId = Integer.parseInt(airportDepartIdText);
            int airportArriveId = Integer.parseInt(airportArriveIdText);
            int volclassId = Integer.parseInt(volclassIdText);
            int userId = Integer.parseInt(userIdText);

            // Parse departure date-time
            LocalDateTime heureDepart = LocalDateTime.of(departureDate, LocalTime.parse(departureTimeText));

            // Parse arrival date-time
            LocalDateTime heureArrive = LocalDateTime.of(arrivalDate, LocalTime.parse(arrivalTimeText));

            // Validate departure and arrival date-time
            if (heureDepart.isBefore(LocalDateTime.now())) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Departure date-time must be today or later.");
                return;
            }

            if (heureArrive.isBefore(heureDepart)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Arrival date-time must be after departure date-time.");
                return;
            }

            boolean disponible = newFlightDisponibleCheckbox.isSelected();

            Flight modifiedFlight = new Flight(id, airportDepartId, airportArriveId, volclassId, userId, compagnieAerienne, heureDepart, heureArrive, disponible);
            flightDAO.updateFlight(modifiedFlight);
            refreshTableView();
            clearFields();
        }
    }

    // Method to display an alert dialog
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


















    @FXML
    void handleDeleteFlightButton() {
        Flight selectedFlight = flightTableView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            int id = selectedFlight.getId();
            flightDAO.deleteFlight(id);
            refreshTableView();
        }
    }

    // Method to populate input fields with flight data
    // Updated method to populate input fields with flight data
    private void populateFields(Flight flight) {
        newFlightAirportDepartIdField.setText(String.valueOf(flight.getAirportDepartId()));
        newFlightAirportArriveIdField.setText(String.valueOf(flight.getAirportArriveId()));
        newFlightVolclassIdField.setText(String.valueOf(flight.getVolclassId()));
        newFlightUserIdField.setText(String.valueOf(flight.getUserId()));
        newFlightCompagnieAerienneField.setText(flight.getCompagnieAerienne());

        // Separate departure date and time
        newFlightDepartureDateField.setValue(flight.getHeureDepart().toLocalDate());
        newFlightDepartureTimeField.setText(flight.getHeureDepart().toLocalTime().toString());

        // Separate arrival date and time
        newFlightArrivalDateField.setValue(flight.getHeureArrive().toLocalDate());
        newFlightArrivalTimeField.setText(flight.getHeureArrive().toLocalTime().toString());

        newFlightDisponibleCheckbox.setSelected(flight.isDisponible());
    }

    private void clearFields() {
        newFlightAirportDepartIdField.clear();
        newFlightAirportArriveIdField.clear();
        newFlightVolclassIdField.clear();
        newFlightUserIdField.clear();
        newFlightCompagnieAerienneField.clear();

        newFlightArrivalTimeField.clear();
        newFlightDisponibleCheckbox.setSelected(false);
    }









    @FXML
    void handleAirportsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/index.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flight.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightClassButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flightclass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleReservationButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationvoladmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightTableView.getScene().getWindow();
        stage.setScene(scene);
    }





}
