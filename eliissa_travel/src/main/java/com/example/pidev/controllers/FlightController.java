package com.example.pidev.controllers;

import com.example.pidev.entities.Airport;
import com.example.pidev.entities.Flight;
import com.example.pidev.entities.User;
import com.example.pidev.services.AirportDAO;
import com.example.pidev.services.FlightDAO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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


    private final AirportDAO airportDAO = new AirportDAO(); // Assuming you have an AirportDAO

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableView();
        configureAirportColumns();
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
    private void configureAirportColumns()  {
        // Configure Departure Airport ID column
        flightAirportDepartIdColumn.setCellFactory(column -> {
            return new ComboBoxTableCell<>(new StringConverter<Integer>() {
                @Override
                public String toString(Integer airportId) {
                    Airport airport = null;
                    try {
                        airport = airportDAO.getAirportById(airportId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return (airport != null) ? airport.getCode() : ""; // Get airport code
                }

                @Override
                public Integer fromString(String airportCode) {
                    Airport airport = null;
                    try {
                        airport = airportDAO.getAirportByCode(airportCode);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return (airport != null) ? airport.getId() : 0; // Get airport ID
                }
            });
        });

        // Configure Arrival Airport ID column (similar to Departure)
        flightAirportArriveIdColumn.setCellFactory(column -> {
            return new ComboBoxTableCell<>(new StringConverter<Integer>() {
                @Override
                public String toString(Integer airportId) {
                    Airport airport = null;
                    try {
                        airport = airportDAO.getAirportById(airportId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return (airport != null) ? airport.getCode() : ""; // Get airport code
                }

                @Override
                public Integer fromString(String airportCode) {
                    Airport airport = null;
                    try {
                        airport = airportDAO.getAirportByCode(airportCode);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    return (airport != null) ? airport.getId() : 0; // Get airport ID
                }
            });
        });
    }

    private void refreshTableView() {
        List<Flight> flights = flightDAO.getAllFlights();
        ObservableList<Flight> flightObservableList = FXCollections.observableArrayList(flights);
        flightTableView.setItems(flightObservableList);
    }








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
        if (airportDepartIdText.isEmpty() || airportArriveIdText.isEmpty() || volclassIdText.isEmpty()
                || userIdText.isEmpty() || compagnieAerienne.isEmpty() || departureDate == null
                || departureTimeText.isEmpty() || arrivalDate == null || arrivalTimeText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all fields.");
            return;
        }

        int airportDepartId = Integer.parseInt(airportDepartIdText);
        int airportArriveId = Integer.parseInt(airportArriveIdText);
        int volclassId = Integer.parseInt(volclassIdText);
        int userId = Integer.parseInt(userIdText);
        if (airportDepartId == airportArriveId) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Departure airport cannot be the same as arrival airport.");
            return;
        }
        // Validate and parse departure date-time
        LocalDateTime heureDepart;
        try {
            heureDepart = LocalDateTime.of(departureDate, LocalTime.parse(departureTimeText));
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Time Format", "Departure time must be in HH:mm format.");
            return;
        }

        // Validate and parse arrival date-time
        LocalDateTime heureArrive;
        try {
            heureArrive = LocalDateTime.of(arrivalDate, LocalTime.parse(arrivalTimeText));
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Time Format", "Arrival time must be in HH:mm format.");
            return;
        }

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


        User loggedInUser = LoginController.getLoggedInUser();




        // Create a new Flight object
        Flight newFlight = new Flight(airportDepartId, airportArriveId, volclassId, loggedInUser.getId(),
                compagnieAerienne, heureDepart, heureArrive, disponible);

        // Add the new flight
        flightDAO.addFlight(newFlight);
        Notifications notification = Notifications.create()
                .title("Flight")
                .text("Flight Added successfully ")
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .graphic(null) // No graphic
                .darkStyle() // Use dark style for better visibility
                .hideCloseButton(); // Hide close button

// Apply the CSS styling directly
        //notification.showInformation(); // Show the notification as information style

// Apply the CSS styling directly
        notification.show();
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

            if (airportDepartId == airportArriveId) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Departure airport cannot be the same as arrival airport.");
                return;
            }

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


            Notifications notification = Notifications.create()
                    .title("Flight")
                    .text("Flight Updated successfully ")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .graphic(null) // No graphic
                    .darkStyle() // Use dark style for better visibility
                    .hideCloseButton(); // Hide close button

// Apply the CSS styling directly
            //notification.showInformation(); // Show the notification as information style

// Apply the CSS styling directly
            notification.show();
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
    void handleGeneratePdfButton(ActionEvent event) {
        try (PdfWriter writer = new PdfWriter("flights.pdf");
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            document.add(new Paragraph("List of Flights"));
            Table table = new Table(9); // 9 columns for flight information
            table.addCell("ID");
            table.addCell("Departure Airport");
            table.addCell("Arrival Airport");
            table.addCell("Class");
            table.addCell("User ID");
            table.addCell("Airline");
            table.addCell("Departure Time");
            table.addCell("Arrival Time");
            table.addCell("Available");

            ObservableList<Flight> flights = flightTableView.getItems();
            // Retrieve flight data from the TableView

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            for (Flight flight : flights) {
                table.addCell(String.valueOf(flight.getId()));
                table.addCell(String.valueOf(flight.getAirportDepartId()));
                table.addCell(String.valueOf(flight.getAirportArriveId()));
                table.addCell(String.valueOf(flight.getVolclassId()));
                table.addCell(String.valueOf(flight.getUserId()));
                table.addCell(flight.getCompagnieAerienne());
                table.addCell(flight.getHeureDepart().format(formatter));
                table.addCell(flight.getHeureArrive().format(formatter));
                table.addCell(flight.isDisponible() ? "Yes" : "No");
            }

            document.add(table);

            System.out.println("PDF generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






















    @FXML
    void handleDeleteFlightButton() {
        Flight selectedFlight = flightTableView.getSelectionModel().getSelectedItem();
        if (selectedFlight != null) {
            int id = selectedFlight.getId();
            flightDAO.deleteFlight(id);

            Notifications notification = Notifications.create()
                    .title("Flight")
                    .text("Flight Deleted successfully ")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .graphic(null) // No graphic
                    .darkStyle() // Use dark style for better visibility
                    .hideCloseButton(); // Hide close button

// Apply the CSS styling directly
            //notification.showInformation(); // Show the notification as information style

// Apply the CSS styling directly
            notification.show();
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
    private TextField departureAirportSearchField;

    @FXML
    void handleDepartureAirportSearch() {
        String searchAirportCode = departureAirportSearchField.getText().trim().toUpperCase();
        if (!searchAirportCode.isEmpty()) {
            // Filter flights where the departure airport code partially matches the search term
            List<Flight> filteredFlights = flightTableView.getItems().filtered(flight -> {
                Airport departureAirport = null;
                try {
                    departureAirport = airportDAO.getAirportById(flight.getAirportDepartId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return departureAirport != null && departureAirport.getCode().toUpperCase().contains(searchAirportCode);
            });
            // Update the table view with search result
            ObservableList<Flight> flightObservableList = FXCollections.observableArrayList(filteredFlights);
            flightTableView.setItems(flightObservableList);
        } else {
            // If search text is empty, refresh the table view with all flights
            refreshTableView();
        }
    }













}
