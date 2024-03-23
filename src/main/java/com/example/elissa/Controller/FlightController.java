package com.example.elissa.Controller;

import com.example.elissa.Models.Flight;
import com.example.elissa.Services.FlightDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

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
    private Button newFlightButton;

    @FXML
    private Button modifyFlightButton;

    @FXML
    private Button deleteFlightButton;

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
    private TextField newFlightHeureDepartField;

    @FXML
    private TextField newFlightHeureArriveField;

    @FXML
    private CheckBox newFlightDisponibleCheckbox;

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

    @FXML
    void handleNewFlightButton() {
        int airportDepartId = Integer.parseInt(newFlightAirportDepartIdField.getText());
        int airportArriveId = Integer.parseInt(newFlightAirportArriveIdField.getText());
        int volclassId = Integer.parseInt(newFlightVolclassIdField.getText());
        int userId = Integer.parseInt(newFlightUserIdField.getText());
        String compagnieAerienne = newFlightCompagnieAerienneField.getText();
        LocalDateTime heureDepart = LocalDateTime.parse(newFlightHeureDepartField.getText()); // Assuming format is compatible
        LocalDateTime heureArrive = LocalDateTime.parse(newFlightHeureArriveField.getText()); // Assuming format is compatible
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
            LocalDateTime heureDepart = LocalDateTime.parse(newFlightHeureDepartField.getText()); // Assuming format is compatible
            LocalDateTime heureArrive = LocalDateTime.parse(newFlightHeureArriveField.getText()); // Assuming format is compatible
            boolean disponible = newFlightDisponibleCheckbox.isSelected();

            Flight modifiedFlight = new Flight(id, airportDepartId, airportArriveId, volclassId, userId, compagnieAerienne, heureDepart, heureArrive, disponible);
            flightDAO.updateFlight(modifiedFlight);
            refreshTableView();
            clearFields();
        }
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
    private void populateFields(Flight flight) {
        newFlightAirportDepartIdField.setText(String.valueOf(flight.getAirportDepartId()));
        newFlightAirportArriveIdField.setText(String.valueOf(flight.getAirportArriveId()));
        newFlightVolclassIdField.setText(String.valueOf(flight.getVolclassId()));
        newFlightUserIdField.setText(String.valueOf(flight.getUserId()));
        newFlightCompagnieAerienneField.setText(flight.getCompagnieAerienne());
        newFlightHeureDepartField.setText(flight.getHeureDepart().toString());
        newFlightHeureArriveField.setText(flight.getHeureArrive().toString());
        newFlightDisponibleCheckbox.setSelected(flight.isDisponible());
    }

    private void clearFields() {
        newFlightAirportDepartIdField.clear();
        newFlightAirportArriveIdField.clear();
        newFlightVolclassIdField.clear();
        newFlightUserIdField.clear();
        newFlightCompagnieAerienneField.clear();
        newFlightHeureDepartField.clear();
        newFlightHeureArriveField.clear();
        newFlightDisponibleCheckbox.setSelected(false);
    }
}
