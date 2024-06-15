package com.example.pidev.controllers;

import com.example.pidev.entities.*;
import com.example.pidev.services.AirportDAO;
import com.example.pidev.services.FlightDAO;
import com.example.pidev.services.FlightclassDAO;
import com.example.pidev.services.ReservationVolAdminDAO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


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


//return in my reservation
    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the showflightclient.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/front.fxml"));
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


  //
  /*  @FXML
    private void handleMyReservationsButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/reservationvolclient.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) reserveButton.getScene().getWindow();

            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            Scene scene = new Scene(root, currentWidth, currentHeight); // Set the same dimensions as the current scene

            stage.setScene(scene);

            ReservationVolController controller = loader.getController();
            controller.populateReservations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/reservationinformationvol.fxml"));
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



// -----------------------------------creates show flight -------------------


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
        currentRow.setSpacing(20); // Set spacing between flight entries

        AirportDAO airportDAO = new AirportDAO();
        FlightclassDAO flightclassDAO = new FlightclassDAO();

        // Iterate over each flight to populate flight entries
        for (Flight flight : flights) {
            VBox flightEntry = createFlightEntry(flight, airportDAO, flightclassDAO);
            if (flightEntry != null) {
                currentRow.getChildren().add(flightEntry);
                flightCount++;

                // Check if currentRow is full based on flightsPerRow
                if (flightCount % flightsPerRow == 0) {
                    flightContainer.getChildren().add(currentRow);
                    currentRow = new HBox(); // Reset currentRow for the next row
                    currentRow.setSpacing(20); // Set spacing between flight entries
                }
            }
        }

        // Add the last currentRow if it's not empty
        if (!currentRow.getChildren().isEmpty()) {
            flightContainer.getChildren().add(currentRow);
        }
    }

    // Helper m
    //
    //
    // ethod to create a flight entry (VBox) with appropriate styling


    private boolean userHasReservation(Flight flight) {
        // Retrieve reservations made by the logged-in user
        User loggedInUser = LoginController.getLoggedInUser();
        ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();
        List<ReservationVolAdmin> userReservations = reservationDAO.getReservationsByUserId(loggedInUser.getId());

        // Check if any reservation matches the flight
        for (ReservationVolAdmin reservation : userReservations) {
            if (reservation.getVolId() == flight.getId()) {
                return true; // User has a reservation for this flight
            }
        }
        return false; // User does not have a reservation for this flight
    }


//--------------------show flight------------------------------------------


    private VBox createFlightEntry(Flight flight, AirportDAO airportDAO, FlightclassDAO flightclassDAO)  {
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
        /*Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(event -> handleViewReservationButtonClick(flight));
        viewReservationButton.setAlignment(Pos.CENTER);*/





        Button viewReservationButton = new Button("View Reservation");
        viewReservationButton.setOnAction(event -> {
            // Check if the current user has already reserved this flight
            if (userHasReservation(flight)) {
                // Display an alert indicating that the user already has a reservation
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Existing Reservation");
                alert.setHeaderText("You already have a reservation for this flight.");
                alert.setContentText("You cannot make multiple reservations for the same flight.");
                alert.showAndWait();
            } else {
                // Allow the user to make a reservation
                handleViewReservationButtonClick(flight);
            }
        });
        viewReservationButton.setAlignment(Pos.CENTER);









        // Create and configure flightEntry VBox
        VBox flightEntry = new VBox();
        flightEntry.setSpacing(10);
        flightEntry.setPadding(new Insets(10));
        flightEntry.setAlignment(Pos.CENTER_LEFT);
        flightEntry.setBackground(new Background(new BackgroundFill(
                LinearGradient.valueOf("linear-gradient(to bottom, #80b3ff, #00A43E)"),
                new CornerRadii(10), null)));
        flightEntry.setMaxWidth(250); // Set maximum width for consistent sizing

        // Add labels and button to the flightEntry VBox
        Label departureLabel = new Label("Departure: " + departureAirport.getName() + " (" + departureAirport.getCity() + ", " + departureAirport.getCountry() + ")");
        Label arrivalLabel = new Label("Arrival: " + arrivalAirport.getName() + " (" + arrivalAirport.getCity() + ", " + arrivalAirport.getCountry() + ")");
        Label airlineLabel = new Label("Airline: " + flight.getCompagnieAerienne());
        Label departureTimeLabel = new Label("Departure Time: " + flight.getHeureDepart().format(dateTimeFormatter));
        Label arrivalTimeLabel = new Label("Arrival Time: " + flight.getHeureArrive().format(dateTimeFormatter));
        Label classLabel = new Label(classLabelText);
        Label priceLabel = new Label(priceLabelText);



        Label Degrees = new Label();
        Label City = new Label();
        Label Time = new Label();




        try {
            // Fetch weather information based on arrival airport
            String country = arrivalAirport.getCity().replaceAll("\\s", "");
            System.out.println("Formatted country name: " + country);

            Connection connection = new Connection(arrivalAirport.getCity()); // Assuming this is your weather API connection
            String temperature = connection.getTemp_C_Api(); // Example method to get temperature from weather API
            System.out.println("Formatted temperature name: " + temperature);
            String cityName = connection.getCityNameApi(); // Example method to get city name from weather API
            System.out.println("Formatted cityName name: " + cityName);

            String localTime = connection.getLocalTime(); // Example method to get local time from weather API
            System.out.println("Formatted localTime name: " + localTime);
            // Create labels to display weather information
            Label temperatureLabel = new Label("Temperature: " + temperature + "°C");

            // Add weather labels to the flight entry VBox
            flightEntry.getChildren().addAll(
                    // Your existing labels...
                    temperatureLabel

            );
            Font labelFont = Font.font("Arial", FontWeight.BOLD, 12);
            // Set font styles and wrapping for weather labels
            temperatureLabel.setFont(labelFont);

            temperatureLabel.setWrapText(true);

        } catch (IOException e) {
            // Handle weather API connection error
            System.out.println("Failed to connect to weather API: " + e.getMessage());
            // You may want to display a placeholder or handle this error differently
        }



       // changeIconWeather(connectionParis);


        //Degrees.setText(connectionParis.getTemp_C_Api() + "°C");
        //City.setText("City: " + connectionParis.getCityNameApi());
      //  Humidity.setProgress(Double.parseDouble(connectionParis.getHumidityApi())/100);
     //   Time.setText(connectionParis.getLocalTime());



        // Set font styles and wrapping
        Font labelFont = Font.font("Arial", FontWeight.BOLD, 12);
        departureLabel.setFont(labelFont);
        arrivalLabel.setFont(labelFont);
        airlineLabel.setFont(labelFont);
        departureTimeLabel.setFont(labelFont);
        arrivalTimeLabel.setFont(labelFont);
        classLabel.setFont(labelFont);
        priceLabel.setFont(labelFont);

//--
        /*Degrees.setFont(labelFont);
        City.setFont(labelFont);
        Time.setFont(labelFont);*/
//--


        departureLabel.setWrapText(true);
        arrivalLabel.setWrapText(true);
        airlineLabel.setWrapText(true);
        departureTimeLabel.setWrapText(true);
        arrivalTimeLabel.setWrapText(true);
        classLabel.setWrapText(true);
        priceLabel.setWrapText(true);
//--
      /*  Degrees.setWrapText(true);
        City.setWrapText(true);
        Time.setWrapText(true);*/
//--
        flightEntry.getChildren().addAll(
                departureLabel,
                arrivalLabel,
                airlineLabel,
                departureTimeLabel,
                arrivalTimeLabel,
                classLabel,
                priceLabel,

                viewReservationButton
        );

        // Apply shadow effect
        DropShadow dropShadow = new DropShadow(10, Color.GRAY);
        flightEntry.setEffect(dropShadow);





        return flightEntry;
    }
















































    //---------
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/reservationinformationvol.fxml"));
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

  /*  private void createReservation(Flight flight, String paymentMethod) {
        // Assuming ReservationVolAdminDAO has a method to create a new reservation
        ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();
        ReservationVolAdmin newReservation = new ReservationVolAdmin();

        User loggedInUser = LoginController.getLoggedInUser();




        newReservation.setVolId(flight.getId());
        newReservation.setUserId(loggedInUser.getId());

        // Retrieve the volclassId associated with the flight
        int volclassId = flight.getVolclassId();

//changed
        FlightclassDAO flightClassDAO = new FlightclassDAO();
        Flightclass flightClass = flightClassDAO.getFlightClassById(volclassId);


        if (volclassId != 0) {
            // Assuming there's a method in ReservationVolAdminDAO to fetch FlightClass by volclassId
        //    FlightclassDAO flightClassDAO = new FlightclassDAO();
        //    Flightclass flightClass = flightClassDAO.getFlightClassById(volclassId);

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
        AirportDAO airportDAO = new AirportDAO();
        Airport departureAirport = airportDAO.findById(flight.getAirportArriveId() );

        Airport departureArrive = airportDAO.findById(flight.getAirportArriveId() );


        String htmlContent = "<html>\n<head>\n<style>\nbody {\nfont-family: Arial, sans-serif;\nline-height: 1.6;\nbackground-color: #f4f4f4;\npadding: 20px;\nmargin: 0;\n}\n.container {\nmax-width: 600px;\nmargin: 0 auto;\nbackground-color: #fff;\npadding: 20px;\nborder-radius: 8px;\nbox-shadow: 0 0 20px rgba(0, 0, 0, 0.1);\n}\nh2 {\ncolor: #333;\nfont-size: 24px;\nmargin-bottom: 20px;\n}\np {\nmargin-bottom: 15px;\nfont-size: 16px;\n}\nul {\nlist-style: none;\npadding: 0;\nmargin-bottom: 20px;\n}\nli {\nmargin-bottom: 10px;\nfont-size: 16px;\n}\nstrong {\nfont-weight: bold;\n}\n.thank-you {\nfont-size: 16px;\nmargin-top: 20px;\n}\n</style>\n</head>\n<body>\n<div class='container'>\n<h2>Dear " +loggedInUser.getLast_name()    + " "  +loggedInUser.getFirst_name()   + ",</h2>\n<p>We are delighted to confirm your flight reservation. Below are the details:</p>\n<ul>\n<li><strong>Departure Country:</strong> " +  departureAirport.getCountry() + "</li>\n<li><strong>Departure City:</strong> " + departureAirport.getCity() + "</li>\n<li><strong>Arrival Country:</strong> " + departureArrive.getCountry() + "</li>\n<li><strong>Arrival City:</strong> " + departureArrive.getCity() + "</li>\n<li><strong>Departure Time:</strong> " + flight.getHeureDepart() + "</li>\n<li><strong>Arrival Time:</strong> " + flight.getHeureArrive() + "</li>\n<li><strong>Departure Airport:</strong> " + departureAirport.getName() + "</li>\n<li><strong>Arrival Airport:</strong> " + departureArrive.getName() + "</li>\n<li><strong>Class Name:</strong> " + flightClass.getClassName() + "</li>\n<li><strong>Class Description:</strong> " + flightClass.getDescription() + "</li>\n<li><strong>Total Price:</strong> " + newReservation.getTotalPrice() + "</li>\n</ul>\n<p class='thank-you'>Thank you for choosing " + flight.getCompagnieAerienne() + ". We look forward to serving you on board.</p>\n<p>Best regards,<br> " + flight.getCompagnieAerienne() + " Team</p>\n</div>\n</body>\n</html>";
        // Save the reservation to the database
        reservationDAO.addReservation(newReservation);
       // EmailSender.AjoutCommentaireEmail("chaher.dridi.100@gmail.com",htmlContent);
        // Display confirmation message

        EmailSender.AjoutCommentaireEmail(loggedInUser.getEmail(),htmlContent);

        showReservationConfirmation();
    }*/
//my reservation stripe


    private void createReservation(Flight flight, String paymentMethod) {
        // Assuming ReservationVolAdminDAO has a method to create a new reservation
        ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();
        ReservationVolAdmin newReservation = new ReservationVolAdmin();

        User loggedInUser = LoginController.getLoggedInUser();

        newReservation.setVolId(flight.getId());
        newReservation.setUserId(loggedInUser.getId());

        // Retrieve the volclassId associated with the flight
        int volclassId = flight.getVolclassId();

        // Retrieve the flight class associated with the flight
        FlightclassDAO flightClassDAO = new FlightclassDAO();
        Flightclass flightClass = flightClassDAO.getFlightClassById(volclassId);

        if (flightClass != null) {
            // Decrement the ticket count for the flight class by one
            if (flightClass.getTicketNumber() > 0) {
                flightClass.setTicketNumber(flightClass.getTicketNumber() - 1);
                // Update the flight class in the database
                flightClassDAO.updateFlightclass(flightClass);

                // Use the price from the FlightClass as the totalPrice for the reservation
                newReservation.setTotalPrice(flightClass.getPrice());
            } else {
                // Handle the case where ticket number is already 0
                // Optionally, you can throw an exception or handle it accordingly
                // For now, setting total price to 0
                newReservation.setTotalPrice(0.0);
            }
        } else {
            // Handle the case where FlightClass is not available or set a default price
            // For now, setting total price to 0
            newReservation.setTotalPrice(0.0);
        }

        newReservation.setPaymentMethod(paymentMethod);

        // Retrieve departure and arrival airports
        AirportDAO airportDAO = new AirportDAO();
        Airport departureAirport = airportDAO.findById(flight.getAirportDepartId());
        Airport arrivalAirport = airportDAO.findById(flight.getAirportArriveId());

        // Construct HTML content for email confirmation
        String htmlContent = "<html>\n<head>\n<style>\nbody {\nfont-family: Arial, sans-serif;\nline-height: 1.6;\nbackground-color: #f4f4f4;\npadding: 20px;\nmargin: 0;\n}\n.container {\nmax-width: 600px;\nmargin: 0 auto;\nbackground-color: #fff;\npadding: 20px;\nborder-radius: 8px;\nbox-shadow: 0 0 20px rgba(0, 0, 0, 0.1);\n}\nh2 {\ncolor: #333;\nfont-size: 24px;\nmargin-bottom: 20px;\n}\np {\nmargin-bottom: 15px;\nfont-size: 16px;\n}\nul {\nlist-style: none;\npadding: 0;\nmargin-bottom: 20px;\n}\nli {\nmargin-bottom: 10px;\nfont-size: 16px;\n}\nstrong {\nfont-weight: bold;\n}\n.thank-you {\nfont-size: 16px;\nmargin-top: 20px;\n}\n</style>\n</head>\n<body>\n<div class='container'>\n<h2>Dear " + loggedInUser.getLast_name() + " " + loggedInUser.getFirst_name() + ",</h2>\n<p>We are delighted to confirm your flight reservation. Below are the details:</p>\n<ul>\n<li><strong>Departure Country:</strong> " + departureAirport.getCountry() + "</li>\n<li><strong>Departure City:</strong> " + departureAirport.getCity() + "</li>\n<li><strong>Arrival Country:</strong> " + arrivalAirport.getCountry() + "</li>\n<li><strong>Arrival City:</strong> " + arrivalAirport.getCity() + "</li>\n<li><strong>Departure Time:</strong> " + flight.getHeureDepart() + "</li>\n<li><strong>Arrival Time:</strong> " + flight.getHeureArrive() + "</li>\n<li><strong>Departure Airport:</strong> " + departureAirport.getName() + "</li>\n<li><strong>Arrival Airport:</strong> " + arrivalAirport.getName() + "</li>\n<li><strong>Class Name:</strong> " + flightClass.getClassName() + "</li>\n<li><strong>Class Description:</strong> " + flightClass.getDescription() + "</li>\n<li><strong>Total Price:</strong> " + newReservation.getTotalPrice() + "</li>\n</ul>\n<p class='thank-you'>Thank you for choosing " + flight.getCompagnieAerienne() + ". We look forward to serving you on board.</p>\n<p>Best regards,<br> " + flight.getCompagnieAerienne() + " Team</p>\n</div>\n</body>\n</html>";

        // Save the reservation to the database
        reservationDAO.addReservation(newReservation);

        // Send email confirmation to the user
        EmailSender.AjoutCommentaireEmail(loggedInUser.getEmail(), htmlContent);

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
//stripe chanigng nowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww

   /* @FXML
    private void handleReservationButtonClick() {
        if (selectedFlight != null && paymentMethodChoiceBox.getValue() != null) {
            String paymentMethod = paymentMethodChoiceBox.getValue();
            createReservation(selectedFlight, paymentMethod);

            // After creating the reservation, navigate back to showflightclient.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/front.fxml"));
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
    }*/




    private static final String STRIPE_SECRET_KEY = "sk_test_51PEBa4KUnX10vD0TSSNwa3Dpw6nHPFGxNjNrNFmOyq24YQ8o7L6XeBGxXq9J2MWsuPaauvlzMAmQFzGEdVI0jDcf00reyQoBSr";





    @FXML
    private TextField cardNumberField; // Reference to the TextField for card number input

    @FXML
    private TextField cvcField; // Reference to the TextField for CVV/CVC input
//



    @FXML
    private void handleReservationButtonClick() {
        if (selectedFlight != null && paymentMethodChoiceBox.getValue() != null && !cardNumberField.getText().isEmpty() && !cvcField.getText().isEmpty()) {
            String paymentMethod = paymentMethodChoiceBox.getValue();
            String cardNumber = cardNumberField.getText(); // Get the card number input
            String cvc = cvcField.getText(); // Get the CVV/CVC input

            // Retrieve the total price from the priceLabel and remove "$" symbol
            String priceText = priceLabel.getText().replace("$", "");
            double totalPrice = Double.parseDouble(priceText);

            Stripe.apiKey = STRIPE_SECRET_KEY;

            try {
                PaymentIntentCreateParams.Builder builder = new PaymentIntentCreateParams.Builder()
                        .setCurrency("eur")
                        .setAmount((long) (totalPrice * 100))
                        .setPaymentMethod("pm_card_visa")
                        .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                        .setReturnUrl("https://example.com/checkout/success")
                        .setConfirm(true);
                PaymentIntentCreateParams params = builder.build();

                PaymentIntent paymentIntent = PaymentIntent.create(params);

                // Validate cardNumber and cvc here

                createReservation(selectedFlight, paymentMethod);

                // After creating the reservation, navigate back to showflightclient.fxml
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/front.fxml"));
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
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*@FXML
    public void initialize() {

        FlightDAO flightDAO = new FlightDAO();
        List<Flight> flights = flightDAO.getAllFlights();
        populateFlights(flights);



       // List<ReservationVolAdmin> reservationVols = reservationDAO.getAllReservations();
        populateReservations();


    }*/
    @FXML
    public void initialize() {
        if (reservationScrollPane != null) {
            populateReservations();
        } else if (scrollPane != null) {
            FlightDAO flightDAO = new FlightDAO();
            List<Flight> flights = flightDAO.getAllFlights();
            populateFlights(flights);

        }
    }














































//------------my reservation


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
       // List<ReservationVolAdmin> reservationVols = reservationDAO.getAllReservations();
        int loggedInUserId = LoginController.getLoggedInUser().getId();

        // Retrieve reservations for the logged-in user from the database using the DAO instance
        List<ReservationVolAdmin> reservationVols = reservationDAO.getReservationsByUserId(loggedInUserId);

        // Clear existing content in the scroll pane
        VBox reservationsContainer = new VBox(); // Create a VBox to hold reservation entries
        reservationsContainer.setSpacing(15); // Set spacing between reservation nodes




        for (ReservationVolAdmin reservation : reservationVols) {
            // Create a custom node (e.g., VBox) to represent each reservation
            VBox reservationNode = createReservationNode(reservation);
            HBox.setHgrow(reservationNode, Priority.ALWAYS); // Allow reservation node to stretch horizontally

            // Add the custom node to the VBox
            reservationsContainer.getChildren().add(reservationNode);
        }

        // Set the VBox as the content of the scroll pane
        reservationScrollPane.setContent(reservationsContainer);
    }
//my reservation
    private VBox createReservationNode(ReservationVolAdmin reservation) {
        VBox reservationNode = new VBox();
        reservationNode.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: lightgrey; -fx-border-width: 1px;");
        reservationNode.setPadding(new Insets(15));
        reservationNode.setMaxWidth(Double.MAX_VALUE); // Ensure VBox stretches horizontally

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.5));
        reservationNode.setEffect(dropShadow);
        User loggedInUser = LoginController.getLoggedInUser();

        if (reservation != null) {
            Label userIdLabel = new Label("User Email: " + loggedInUser.getEmail());
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

        HBox.setHgrow(reservationNode, Priority.ALWAYS);
        return reservationNode;
    }
































































//-----------------------------------------------------------------------



























//-----------------------------------------------------------------------





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
