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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.scene.control.Alert;

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


    public void populateReservations() {
        // Create an instance of ReservationVolAdminDAO
        ReservationVolAdminDAO reservationVolAdminDAO = new ReservationVolAdminDAO();
        FlightDAO flightDAO = new FlightDAO(); // Assuming you have a FlightDAO

        // Set cell value factories
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("heureArrive"));

        // Call the refresh method
        refreshTableres(reservationVolAdminDAO, flightDAO);
    }

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


 /*   public void populateFlights(List<Flight> flights) {
        if (flightContainer == null) {
            System.out.println("Flight container is null");
            return;
        }

        flightContainer.getChildren().clear();

        int flightsPerRow = 3;
        int flightCount = 0;
        HBox currentRow = new HBox();

        for (Flight flight : flights) {
            VBox flightEntry = new VBox();
            flightEntry.setSpacing(10);

            Label departureAirportLabel = new Label("Departure Airport: " + flight.getAirportDepartId());
            Label arrivalAirportLabel = new Label("Arrival Airport: " + flight.getAirportArriveId());
            Label airlineLabel = new Label("Airline: " + flight.getCompagnieAerienne());
            Label departureTimeLabel = new Label("Departure Time: " + flight.getHeureDepart().format(dateTimeFormatter));
            Label arrivalTimeLabel = new Label("Arrival Time: " + flight.getHeureArrive().format(dateTimeFormatter));

            // Retrieve the volclassId from the flight
            int volclassId = flight.getVolclassId();

            // Retrieve FlightClass based on volclassId
            FlightclassDAO flightclassDAO = new FlightclassDAO();
            Flightclass flightClass = flightclassDAO.getFlightClassById(volclassId);

            // Create labels for FlightClass details if available
            String classLabelText = "Class: " + (flightClass != null ? flightClass.getClassName() : "N/A");
            String priceLabelText = "Price: $" + (flightClass != null ? flightClass.getPrice() : "N/A");

            Label classLabel = new Label(classLabelText);
            Label priceLabel = new Label(priceLabelText);

            Button viewReservationButton = new Button("View Reservation");
            viewReservationButton.setOnAction(event -> handleViewReservationButtonClick(flight));

            // Add labels and button to the flightEntry VBox
            flightEntry.getChildren().addAll(
                    departureAirportLabel, arrivalAirportLabel, airlineLabel,
                    departureTimeLabel, arrivalTimeLabel, classLabel, priceLabel, viewReservationButton
            );

            // Add the flightEntry to the currentRow HBox
            currentRow.getChildren().add(flightEntry);
            flightCount++;

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
    }*/



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
            // Create a VBox for each flight entry
            VBox flightEntry = createFlightEntry(flight, airportDAO, flightclassDAO);

            // Add flightEntry to the currentRow HBox
            currentRow.getChildren().add(flightEntry);
            flightCount++;

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
        VBox flightEntry = new VBox();
        flightEntry.setSpacing(10);

        // Retrieve departure airport details
        Airport departureAirport = airportDAO.findById(flight.getAirportDepartId());
        if (departureAirport == null) {
            System.out.println("Departure airport not found for flight: " + flight.getId());
            return flightEntry; // Return empty flight entry or handle error
        }

        // Retrieve arrival airport details
        Airport arrivalAirport = airportDAO.findById(flight.getAirportArriveId());
        if (arrivalAirport == null) {
            System.out.println("Arrival airport not found for flight: " + flight.getId());
            return flightEntry; // Return empty flight entry or handle error
        }

        // Debugging: Log airport details
        System.out.println("Departure Airport: " + departureAirport.getName() + ", " + departureAirport.getCity());
        System.out.println("Arrival Airport: " + arrivalAirport.getName() + ", " + arrivalAirport.getCity());

        // Flight details
        System.out.println("Airline: " + flight.getCompagnieAerienne());
        System.out.println("Departure Time: " + flight.getHeureDepart().format(dateTimeFormatter));
        System.out.println("Arrival Time: " + flight.getHeureArrive().format(dateTimeFormatter));

        // Retrieve FlightClass based on volclassId
        int volclassId = flight.getVolclassId();
        Flightclass flightClass = flightclassDAO.getFlightClassById(volclassId);

        // Create labels for FlightClass details if available
        String classLabelText = "Class: " + (flightClass != null ? flightClass.getClassName() : "N/A");
        String priceLabelText = "Price: $" + (flightClass != null ? flightClass.getPrice() : "N/A");

        Label classLabel = new Label(classLabelText);
        Label priceLabel = new Label(priceLabelText);

        // Button to view reservation
        Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(event -> handleViewReservationButtonClick(flight));

        // Add labels and button to the flightEntry VBox
        flightEntry.getChildren().addAll(
                new Label("Departure: " + departureAirport.getName()  ),
                new Label(   " (" + departureAirport.getCity() + ", " + departureAirport.getCountry() + ")"),
                new Label("Arrival: " + arrivalAirport.getName()  ),
                new Label(  " (" + arrivalAirport.getCity() + ", " + arrivalAirport.getCountry() + ")"),

                new Label("Airline: " + flight.getCompagnieAerienne()),
                new Label("Departure Time: " + flight.getHeureDepart().format(dateTimeFormatter)),
                new Label("Arrival Time: " + flight.getHeureArrive().format(dateTimeFormatter)),
                classLabel,
                priceLabel,
                viewReservationButton
        );


        // Apply styling to the flightEntry VBox
        flightEntry.setStyle("-fx-background-color: #3A7E49; -fx-padding: 20; -fx-spacing: 10; -fx-border-radius: 5;");
        flightEntry.setPrefSize(300, 200); // Set preferred size for uniformity

        return flightEntry;
    }






  /*  private void initializeTableColumns() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
    }*/











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

            // Retrieve FlightClass associated with selectedFlight
            Flightclass flightClass = flightclassDAO.getFlightClassById(selectedFlight.getVolclassId());

            if (flightClass != null) {
                // Update labels with FlightClass details
                classLabel.setText("Class: " + flightClass.getClassName());
                priceLabel.setText("Price: $" + flightClass.getPrice());
            } else {
                // Handle case where FlightClass is not found
                classLabel.setText("Class: N/A");
                priceLabel.setText("Price: N/A");
            }

            departureAirportLabel.setText("Departure Airport: " + selectedFlight.getAirportDepartId());
            arrivalAirportLabel.setText("Arrival Airport: " + selectedFlight.getAirportArriveId());
            airlineLabel.setText("Airline: " + selectedFlight.getCompagnieAerienne());
            departureTimeLabel.setText("Departure Time: " + selectedFlight.getHeureDepart().format(dateTimeFormatter));
            arrivalTimeLabel.setText("Arrival Time: " + selectedFlight.getHeureArrive().format(dateTimeFormatter));
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
}
