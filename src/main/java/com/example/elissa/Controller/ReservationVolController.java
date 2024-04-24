package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Models.Flight;

import com.example.elissa.Models.Flightclass;

import com.example.elissa.Models.ReservationVolAdmin;
import com.example.elissa.Services.AirportDAO;
import com.example.elissa.Services.FlightDAO;
import com.example.elissa.Services.FlightclassDAO;
import com.example.elissa.Services.ReservationVolAdminDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationVolController {

    @FXML
    private VBox flightContainer;

    @FXML
    private ScrollPane scrollPane;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    private Flight selectedFlight;


    @FXML
    private Label flightInfoLabel;

    @FXML
    private Button reserveButton;




    public Flight getSelectedFlight() {
        return selectedFlight;
    }

  /*  public void setSelectedFlight(Flight flight) {
        this.selectedFlight = flight;
    }*/



    @FXML
    private Button backButton;














    @FXML
    private TableView<ReservationVolAdmin> reservationTable;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> reservationIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> flightIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> userIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Double> totalPriceColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> paymentMethodColumn;





    private ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();




    @FXML
    private TableColumn<ReservationVolAdmin, String> descriptionColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> classNameColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> departureTimeColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> arrivalTimeColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> departureAirportColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> arrivalAirportColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> cityColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> countryColumn;



    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the showflightclient.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/showflightclient.fxml"));
            Parent root = loader.load();

            // Get the stage from the back button's scene
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the current scene's root to the loaded FXML root
            Scene scene = backButton.getScene();
            scene.setRoot(root);

            // Restore the previous size of the window
            stage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleMyReservationsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationvolclient.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) reserveButton.getScene().getWindow();

            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            Scene scene = new Scene(root, currentWidth, currentHeight);

            stage.setScene(scene);

            ReservationVolController controller = loader.getController();
            controller.populateReservations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//------------------------------------------------------------

    private void refreshTableres(ReservationVolAdminDAO reservationVolAdminDAO, FlightDAO flightDAO) {
        // Retrieve all reservations from the database using the DAO instance
        List<ReservationVolAdmin> reservationVols = reservationVolAdminDAO.getAllReservations();

        // Print out the retrieved reservations for debugging
        System.out.println("Retrieved reservationvol: " + reservationVols);

        // Clear existing items in the table
        reservationTable.getItems().clear();

        // Create an observable list from the retrieved reservations
        ObservableList<ReservationVolAdmin> reservationVolObservableList = FXCollections.observableArrayList();

        for (ReservationVolAdmin reservation : reservationVols) {
            // Retrieve Flight based on vol_id
            Flight flight = flightDAO.getFlightByVolId(reservation.getVolId());
            reservation.setFlight(flight); // Set the Flight in ReservationVolAdmin

            // Add to observable list
            reservationVolObservableList.add(reservation);
        }

        // Set the items of the table view
        reservationTable.setItems(reservationVolObservableList);
    }





    @FXML
    private void handleViewReservationButtonClick(Flight flight) {
        try {
            // Load the reservation details FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationinformationvol.fxml"));
            Parent reservationDetailsRoot = loader.load();

            // Get the controller associated with the reservation details FXML file
            ReservationVolController reservationVolController = loader.getController();


            // Set the selected flight in the reservation details controller
            reservationVolController.setSelectedFlight(flight);

            // Replace the content of the current scene with the reservation details scene
            Scene currentScene = flightContainer.getScene();
            currentScene.setRoot(reservationDetailsRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    private void populateFlights(List<Flight> flights) {
        // Ensure flightContainer is initialized
        if (flightContainer == null) {
            System.out.println("Flight container is null");
            return;
        }

        // Clear existing content in flightContainer
        flightContainer.getChildren().clear();

        // Define layout settings
        int flightsPerRow = 3;
        int flightCount = 0;
        HBox currentRow = new HBox();

        AirportDAO airportDAO = new AirportDAO();
        FlightclassDAO flightclassDAO = new FlightclassDAO();

        // Iterate over each flight to populate flight entries
        for (Flight flight : flights) {
            VBox flightEntry = createFlightEntry(flight, airportDAO, flightclassDAO);
            if (flightEntry != null) {
                currentRow.getChildren().add(flightEntry);
                flightCount++;
            }

            // Check if currentRow is full based on flightsPerRow
            if (flightCount % flightsPerRow == 0) {
                flightContainer.getChildren().add(currentRow);
                currentRow = new HBox(); // Reset currentRow for the next row
            }
        }


        // Add the last currentRow if it's not full but contains flights
        if (flightCount % flightsPerRow != 0) {
            flightContainer.getChildren().add(currentRow);
        }
    }

    // Helper method to create a flight entry (VBox) with appropriate styling


    private VBox createFlightEntry(Flight flight, AirportDAO airportDAO, FlightclassDAO flightclassDAO) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Retrieve departure airport details
        Airport departureAirport = airportDAO.findById(flight.getAirportDepartId());
        if (departureAirport == null) {
            System.out.println("Departure airport not found for flight: " + flight.getId());
            return null; // Return null to indicate empty flight entry
        }

        // Retrieve arrival airport details
        Airport arrivalAirport = airportDAO.findById(flight.getAirportArriveId());
        if (arrivalAirport == null) {
            System.out.println("Arrival airport not found for flight: " + flight.getId());
            return null; // Return null to indicate empty flight entry
        }

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Check if the flight's departure time is in the future (from now onwards)
        if (flight.getHeureDepart().isBefore(now)) {
            System.out.println("Flight with ID " + flight.getId() + " has already departed.");
            return null; // Return null to indicate empty flight entry
        }

        // FlightClass details
        int volclassId = flight.getVolclassId();
        Flightclass flightClass = flightclassDAO.getFlightClassById(volclassId);

        // Create labels for FlightClass details if available
        String classLabelText = "Class: " + (flightClass != null ? flightClass.getClassName() : "N/A");
        String priceLabelText = "Price: $" + (flightClass != null ? flightClass.getPrice() : "N/A");

        // Button to view reservation
        Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(event -> handleViewReservationButtonClick(flight));

        // Create and configure flightEntry VBox
        VBox flightEntry = new VBox();
        flightEntry.setSpacing(10);

        // Add labels and button to the flightEntry VBox
        flightEntry.getChildren().addAll(
                new Label("Departure: " + departureAirport.getName() + " (" + departureAirport.getCity() + ", " + departureAirport.getCountry() + ")"),
                new Label("Arrival: " + arrivalAirport.getName() + " (" + arrivalAirport.getCity() + ", " + arrivalAirport.getCountry() + ")"),
                new Label("Airline: " + flight.getCompagnieAerienne()),
                new Label("Departure Time: " + flight.getHeureDepart().format(dateTimeFormatter)),
                new Label("Arrival Time: " + flight.getHeureArrive().format(dateTimeFormatter)),
                new Label(classLabelText),
                new Label(priceLabelText),
                viewReservationButton
        );

        // Apply styling to the flightEntry VBox
        flightEntry.setStyle("-fx-background-color: #3A7E49; -fx-padding: 20; -fx-spacing: 10; -fx-border-radius: 5;");
        flightEntry.setPrefSize(300, 200); // Set preferred size for uniformity

        return flightEntry;
    }










    @FXML
    private Label departureAirportLabel;

    @FXML
    private Label arrivalAirportLabel;

    @FXML
    private Label airlineLabel;

    @FXML
    private Label departureTimeLabel;

    @FXML
    private Label arrivalTimeLabel;

    @FXML
    private Label classLabel;

    @FXML
    private Label priceLabel;
    public void setSelectedFlight(Flight flight) {
        this.selectedFlight = flight;
        updateFlightDetails();
    }


    private void updateFlightDetails() {
        if (selectedFlight != null) {
            FlightclassDAO flightclassDAO = new FlightclassDAO();
            AirportDAO airportDAO = new AirportDAO();

            // Retrieve FlightClass associated with selectedFlight
            Flightclass flightClass = flightclassDAO.getFlightClassById(selectedFlight.getVolclassId());

            if (flightClass != null) {
                // Update labels with FlightClass details
                classLabel.setText(flightClass.getClassName());
                priceLabel.setText("$" + flightClass.getPrice());
            } else {
                // Handle case where FlightClass is not found
                classLabel.setText("Class: N/A");
                priceLabel.setText("Price: N/A");
            }

            // Retrieve Departure Airport details
            Airport departureAirport = airportDAO.findById(selectedFlight.getAirportDepartId());
            if (departureAirport != null) {
                departureAirportLabel.setText(departureAirport.getName());
                departureAirportLabel.setText(departureAirportLabel.getText() + " (" + departureAirport.getCity() + ", " + departureAirport.getCountry() + ")");
            } else {
                departureAirportLabel.setText("Departure Airport: N/A");
            }

            // Retrieve Arrival Airport details
            Airport arrivalAirport = airportDAO.findById(selectedFlight.getAirportArriveId());
            if (arrivalAirport != null) {
                arrivalAirportLabel.setText(arrivalAirport.getName());
                arrivalAirportLabel.setText(arrivalAirportLabel.getText() + " (" + arrivalAirport.getCity() + ", " + arrivalAirport.getCountry() + ")");
            } else {
                arrivalAirportLabel.setText("Arrival Airport: N/A");
            }

            // Update Airline and Time details
            airlineLabel.setText(selectedFlight.getCompagnieAerienne());

            // Format departure and arrival time with date and time
            DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            LocalDateTime departureDateTime = selectedFlight.getHeureDepart();
            if (departureDateTime != null) {
                departureTimeLabel.setText(departureDateTime.format(fullDateTimeFormatter));
            } else {
                departureTimeLabel.setText("Departure Time: N/A");
            }

            LocalDateTime arrivalDateTime = selectedFlight.getHeureArrive();
            if (arrivalDateTime != null) {
                arrivalTimeLabel.setText(arrivalDateTime.format(fullDateTimeFormatter));
            } else {
                arrivalTimeLabel.setText("Arrival Time: N/A");
            }
        }
    }























    @FXML
    private void handleViewReservationButtonClick() {
        if (selectedFlight != null) {
            openReservationDetailsPage();
        }
    }

    private void openReservationDetailsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationinformationvol.fxml"));
            Parent root = loader.load();

            // Get the controller associated with the reservation information FXML file
            ReservationVolController controller = loader.getController();

            // Set the selected flight in the new controller instance
            controller.setSelectedFlight(selectedFlight);

            // Create a new stage for the reservation details page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private ChoiceBox<String> paymentMethodChoiceBox;

    private void createReservation(Flight flight, String paymentMethod) {
        // Assuming ReservationVolAdminDAO has a method to create a new reservation
        ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();
        ReservationVolAdmin newReservation = new ReservationVolAdmin();
        newReservation.setVolId(flight.getId());
        newReservation.setUserId(1);

        // Retrieve the volclassId associated with the flight
        int volclassId = flight.getVolclassId();

        if (volclassId != 0) {
            // Assuming there's a method in ReservationVolAdminDAO to fetch FlightClass by volclassId
            FlightclassDAO flightClassDAO = new FlightclassDAO();
            Flightclass flightClass = flightClassDAO.getFlightClassById(volclassId);

            if (flightClass != null) {
                // Use the price from the FlightClass as the totalPrice for the reservation
                newReservation.setTotalPrice(flightClass.getPrice());
            } else {
                // Handle the case where FlightClass is not available or set a default price
                newReservation.setTotalPrice(0.0); // Set a default price or handle appropriately
            }
        } else {
            // Handle the case where volclassId is not valid or set a default price
            newReservation.setTotalPrice(0.0); // Set a default price or handle appropriately
        }

        newReservation.setPaymentMethod(paymentMethod);

        // Save the reservation to the database
        reservationDAO.addReservation(newReservation);

        // Display confirmation message
        showReservationConfirmation();
    }

    private void showReservationConfirmation() {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Reservation Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Reservation successfully created!");

        // Show and wait for user response
        confirmationAlert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("Reservation confirmed."));
    }


    @FXML
    private void handleReservationButtonClick() {
        if (selectedFlight != null && paymentMethodChoiceBox.getValue() != null) {
            String paymentMethod = paymentMethodChoiceBox.getValue();
            createReservation(selectedFlight, paymentMethod);

            // After creating the reservation, navigate back to showflightclient.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/showflightclient.fxml"));
                Parent root = loader.load();

                // Get the stage from the reserve button's scene
                Stage stage = (Stage) reserveButton.getScene().getWindow();

                // Set the current scene's root to the loaded FXML root
                Scene scene = reserveButton.getScene();
                scene.setRoot(root);

                // Restore the previous size of the window
                stage.sizeToScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {

        FlightDAO flightDAO = new FlightDAO();
        List<Flight> flights = flightDAO.getAllFlights();
        populateFlights(flights);


    }


















































    @FXML
    private ScrollPane reservationScrollPane;

    private ReservationVolAdmin selectedReservation;


    @FXML
    private void handleUpdateReservation() {
        if (selectedReservation != null && selectedReservation.getVolId() != 0) {
            int reservationId = selectedReservation.getId(); // Retrieve the reservation ID
            int flightId = selectedReservation.getVolId(); // Retrieve the flight ID associated with the reservation

            // Retrieve the Flight associated with the selected reservation using the flight ID
            Flight selectedFlight = getFlightById(flightId);

            if (selectedFlight != null) {
                LocalDateTime departureDateTime = selectedFlight.getHeureDepart();
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Calculate the minimum allowable departure time (48 hours from now)
                LocalDateTime minAllowableDepartureTime = currentDateTime.plusHours(48);

                // Check if departure is more than 48 hours from now
                if (departureDateTime != null && departureDateTime.isBefore(minAllowableDepartureTime)) {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Cannot update reservation within 48 hours of departure.");
                    return;
                }
            }

            // Display a ChoiceBox dialog to update the payment method
            ChoiceBox<String> paymentMethodChoiceBox = new ChoiceBox<>();
            paymentMethodChoiceBox.getItems().addAll("Cash", "Online");
            paymentMethodChoiceBox.setValue(selectedReservation.getPaymentMethod());

            Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);
            updateAlert.setTitle("Update Payment Method");
            updateAlert.setHeaderText(null);
            updateAlert.getDialogPane().setContent(paymentMethodChoiceBox);

            updateAlert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    String newPaymentMethod = paymentMethodChoiceBox.getValue();
                    selectedReservation.setPaymentMethod(newPaymentMethod);

                    try {
                        // Update the reservation in the database using its ID
                        reservationDAO.updateReservation(selectedReservation);

                        showAlert(Alert.AlertType.INFORMATION, "Success", null, "Reservation updated successfully.");

                        // Refresh reservations display
                        populateReservations();
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to update reservation: " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please select a reservation with valid flight information to update.");
        }
    }



    @FXML
    private void handleDeleteReservation() {
        if (selectedReservation != null) {
            System.out.println("Selected Reservation: " + selectedReservation);

            // Retrieve the reservation ID from the selected reservation
            int reservationId = selectedReservation.getId();

            // Retrieve the idVol (flight ID) associated with the selected reservation
            int flightId = selectedReservation.getVolId();

            // Retrieve the Flight associated with the selected reservation using the flight ID
            Flight selectedFlight = getFlightById(flightId);
            System.out.println("Selected flight: " + selectedFlight);

            if (selectedFlight != null) {
                LocalDateTime departureDateTime = selectedFlight.getHeureDepart();
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Calculate the minimum allowable departure time (48 hours from now)
                LocalDateTime minAllowableDepartureTime = currentDateTime.plusHours(48);

                // Check if departure is more than 48 hours from now
                if (departureDateTime != null && departureDateTime.isBefore(minAllowableDepartureTime)) {
                    showAlert(Alert.AlertType.ERROR, "Error", null, "Cannot delete reservation within 48 hours of departure.");
                    return;
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this reservation?");
            alert.setContentText("This action cannot be undone.");

            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    try {
                        // Delete the reservation from the database using its ID
                        reservationDAO.deleteReservation(reservationId);

                        // Display success message
                        showAlert(Alert.AlertType.INFORMATION, "Success", null, "Reservation deleted successfully.");

                        // Refresh the displayed reservations
                        populateReservations();
                    } catch (Exception e) {
                        // Display error message if deletion fails
                        showAlert(Alert.AlertType.ERROR, "Error", null, "Failed to delete reservation: " + e.getMessage());
                    }
                }
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", null, "Please select a reservation to delete.");
        }
    }

    private Flight getFlightById(int flightId) {
        // Retrieve Flight details from database using flightId
        FlightDAO flightDAO = new FlightDAO();
        return flightDAO.getFlightById(flightId); // Implement this method in FlightDAO
    }



    private void populateReservations() {
        // Retrieve all reservations from the database using the DAO instance
        List<ReservationVolAdmin> reservationVols = reservationDAO.getAllReservations();

        // Clear existing content in the scroll pane
        VBox reservationsContainer = new VBox(); // Create a VBox to hold reservation entries

        for (ReservationVolAdmin reservation : reservationVols) {
            // Create a custom node (e.g., VBox) to represent each reservation
            VBox reservationNode = createReservationNode(reservation);

            // Add the custom node to the VBox
            reservationsContainer.getChildren().add(reservationNode);
        }

        // Set the VBox as the content of the scroll pane
        reservationScrollPane.setContent(reservationsContainer);
    }

    private VBox createReservationNode(ReservationVolAdmin reservation) {
        VBox reservationNode = new VBox();
        reservationNode.setSpacing(10);
        reservationNode.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: lightgrey; -fx-border-width: 1px;");
        reservationNode.setPadding(new Insets(10));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.5));
        reservationNode.setEffect(dropShadow);

        if (reservation != null) {
            Label userIdLabel = new Label("User ID: " + reservation.getUserId());
            Label totalPriceLabel = new Label("Total Price: $" + String.format("%.2f", reservation.getTotalPrice()));
            Label paymentMethodLabel = new Label("Payment Method: " + reservation.getPaymentMethod());

            int volId = reservation.getVolId(); // Get the volId associated with the reservation

            // Retrieve Flight information using volId
            Flight reservationFlight = getFlightByVolId(volId);

            if (reservationFlight != null) {
                // Retrieve departure and arrival airport details
                Airport departureAirport = getDepartureAirport(reservationFlight.getAirportDepartId());
                Airport arrivalAirport = getArrivalAirport(reservationFlight.getAirportArriveId());

                if (departureAirport != null && arrivalAirport != null) {
                    // Display flight details
                    Label flightNameLabel = new Label("Flight Name: " + departureAirport.getName());
                    Label departureLabel = new Label("Departure: " + departureAirport.getCity() + ", " + departureAirport.getCountry() +
                            " at " + formatDateTime(reservationFlight.getHeureDepart()));
                    Label arrivalLabel = new Label("Arrival: " + arrivalAirport.getCity() + ", " + arrivalAirport.getCountry() +
                            " at " + formatDateTime(reservationFlight.getHeureArrive()));

                    // Add flight details to the reservationNode
                    reservationNode.getChildren().addAll(userIdLabel, totalPriceLabel, paymentMethodLabel, flightNameLabel, departureLabel, arrivalLabel);
                } else {
                    // Handle case where airport details are not found
                    Label errorLabel = new Label("Error: Airport details not found for this flight.");
                    reservationNode.getChildren().add(errorLabel);
                }
            } else {
                // Handle case where Flight is null or not found
                Label errorLabel = new Label("Error: Flight information not available for this reservation.");
                reservationNode.getChildren().add(errorLabel);
            }

            Button updateButton = new Button("Update");
            updateButton.setOnAction(event -> {
                selectedReservation = reservation; // Set the selected reservation for update
                handleUpdateReservation(); // Handle update operation
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                selectedReservation = reservation; // Set the selected reservation for deletion
                handleDeleteReservation(); // Handle delete operation
            });

            // Add labels and buttons to the reservationNode
            reservationNode.getChildren().addAll(updateButton, deleteButton);
        } else {
            // Handle case where reservation is null
            Label errorLabel = new Label("Error: Reservation details not available.");
            reservationNode.getChildren().add(errorLabel);
        }

        VBox.setVgrow(reservationNode, Priority.ALWAYS); // Allow VBox to stretch vertically
        return reservationNode;



    }


















    private String formatDateTime(LocalDateTime dateTime) {
        // Format LocalDateTime to a user-friendly string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }




    private Airport getArrivalAirport(int airportId) {
        // Retrieve Arrival Airport details from the database using airportId
        AirportDAO airportDAO = new AirportDAO();
        return airportDAO.findById(airportId); // Implement this method in AirportDAO
    }


    private Flight getFlightByVolId(int volId) {
        // Retrieve Flight details from database using volId
        FlightDAO flightDAO = new FlightDAO();
        return flightDAO.getFlightByVolId(volId); // Implement this method in FlightDAO
    }

    private Airport getDepartureAirport(int airportId) {
        // Retrieve Airport details from database using airportId
        AirportDAO airportDAO = new AirportDAO();
        return airportDAO.findById(airportId); // Implement this method in AirportDAO
    }




    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
