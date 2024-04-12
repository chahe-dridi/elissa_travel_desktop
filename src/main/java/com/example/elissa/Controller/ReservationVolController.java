package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Models.Flight;

import com.example.elissa.Models.Flightclass;

import com.example.elissa.Models.ReservationVolAdmin;
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

        // Set cell value factories
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        // Set cell value factories for new columns
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("flightClass.description"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("flightClass.className"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("heureArrive"));


        // Call the refresh method
        refreshTableres(reservationVolAdminDAO);
    }


    private void refreshTableres(ReservationVolAdminDAO reservationVolAdminDAO) {
        // Retrieve all reservations from the database using the DAO instance
        List<ReservationVolAdmin> ReservationVols = reservationVolAdminDAO.getAllReservations();

        // Print out the retrieved reservations for debugging
        System.out.println("Retrieved reservationvol: " + ReservationVols);

        // Clear existing items in the table
        reservationTable.getItems().clear();

        // Create an observable list from the retrieved reservations
        ObservableList<ReservationVolAdmin> ReservationVolObservableList = FXCollections.observableArrayList(ReservationVols);

        // Set the items of the table view
        reservationTable.setItems(ReservationVolObservableList);
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


    public void populateFlights(List<Flight> flights) {
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
