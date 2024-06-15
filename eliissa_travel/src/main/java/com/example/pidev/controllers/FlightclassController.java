package com.example.pidev.controllers;

import com.example.pidev.entities.Airport;
import com.example.pidev.entities.Flightclass;
import com.example.pidev.entities.User;
import com.example.pidev.services.FlightclassDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FlightclassController implements Initializable {

    private final FlightclassDAO flightclassDAO = new FlightclassDAO();

    @FXML
    private TableView<Flightclass> flightclassTableView;

    @FXML
    private TableColumn<Flightclass, String> classNameColumn;

    @FXML
    private TableColumn<Flightclass, String> descriptionColumn;

    @FXML
    private TableColumn<Flightclass, Double> priceColumn;

    @FXML
    private TableColumn<Flightclass, Integer> ticketNumberColumn;


    @FXML
    private TableColumn<Flightclass, String> IdColumn;


    @FXML
    private TextField newClassNameField;

    @FXML
    private TextField newDescriptionField;

    @FXML
    private TextField newPriceField;

    @FXML
    private TextField newTicketNumberField;

    @FXML
    private Button newFlightclassButton;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("FlightclassController initialized");
        configureTableView();
        refreshTableView();

        flightclassTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });


    }

    private void configureTableView() {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Use "id" instead of "Id"
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
    }


    private void refreshTableView() {
        List<Flightclass> flightclasses = flightclassDAO.getAllFlightclasses();
        ObservableList<Flightclass> flightclassObservableList = FXCollections.observableArrayList(flightclasses);
        flightclassTableView.setItems(flightclassObservableList);
    }
    private void populateFields(Flightclass flightclass) {
        newClassNameField.setText(flightclass.getClassName());
        newDescriptionField.setText(flightclass.getDescription());
        newPriceField.setText(String.valueOf(flightclass.getPrice()));
        newTicketNumberField.setText(String.valueOf(flightclass.getTicketNumber()));
    }




/*
    @FXML
    void handleNewFlightclassButton() {
        String className = newClassNameField.getText();
        String description = newDescriptionField.getText();
        double price = Double.parseDouble(newPriceField.getText());
        int ticketNumber = Integer.parseInt(newTicketNumberField.getText());

        Flightclass newFlightclass = new Flightclass(1, className, description, price, ticketNumber);
        flightclassDAO.addFlightclass(newFlightclass);
        refreshTableView();
        clearFields();
    }



    @FXML
    void handleModifyFlightclassButton() {
        Flightclass selectedFlightclass = flightclassTableView.getSelectionModel().getSelectedItem();
        if (selectedFlightclass != null) {
            String className = newClassNameField.getText();
            String description = newDescriptionField.getText();
            double price = Double.parseDouble(newPriceField.getText());
            int ticketNumber = Integer.parseInt(newTicketNumberField.getText());

            selectedFlightclass.setClassName(className);
            selectedFlightclass.setDescription(description);
            selectedFlightclass.setPrice(price);
            selectedFlightclass.setTicketNumber(ticketNumber);

            flightclassDAO.updateFlightclass(selectedFlightclass);
            refreshTableView();
            clearFields();
        }
    }
*/


    @FXML
    void handleNewFlightclassButton() {
        String className = newClassNameField.getText().trim();
        String description = newDescriptionField.getText().trim();
        String priceText = newPriceField.getText().trim();
        String ticketNumberText = newTicketNumberField.getText().trim();

        // Validate input fields
        if (!isValidClassName(className) || !isValidInput(description) || !isValidPositiveDouble(priceText) || !isValidPositiveInteger(ticketNumberText)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid values for all fields.");
            return;
        }

        double price = Double.parseDouble(priceText);
        int ticketNumber = Integer.parseInt(ticketNumberText);


        User loggedInUser = LoginController.getLoggedInUser();

        // Create a new Airport object


        // Set the user who is logged in as the creator of the new airport
        //loggedInUser.getId()


        Flightclass newFlightclass = new Flightclass(loggedInUser.getId(), className, description, price, ticketNumber);
        flightclassDAO.addFlightclass(newFlightclass);


        Notifications notification = Notifications.create()
                .title("FlightClass")
                .text("FlightClass Added successfully ")
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
    void handleModifyFlightclassButton() {
        Flightclass selectedFlightclass = flightclassTableView.getSelectionModel().getSelectedItem();
        if (selectedFlightclass != null) {
            String className = newClassNameField.getText().trim();
            String description = newDescriptionField.getText().trim();
            String priceText = newPriceField.getText().trim();
            String ticketNumberText = newTicketNumberField.getText().trim();

            // Validate input fields
            if (!isValidClassName(className) || !isValidInput(description) || !isValidPositiveDouble(priceText) || !isValidPositiveInteger(ticketNumberText)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid values for all fields.");
                return;
            }

            double price = Double.parseDouble(priceText);
            int ticketNumber = Integer.parseInt(ticketNumberText);

            selectedFlightclass.setClassName(className);
            selectedFlightclass.setDescription(description);
            selectedFlightclass.setPrice(price);
            selectedFlightclass.setTicketNumber(ticketNumber);

            flightclassDAO.updateFlightclass(selectedFlightclass);


            Notifications notification = Notifications.create()
                    .title("FlightClass")
                    .text("FlightClass Updated successfully ")
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

    private boolean isValidInput(String str) {
        return !str.isEmpty(); // Basic validation to check if the string is not empty
    }
    // Method to validate className (contains only letters)
    private boolean isValidClassName(String str) {
        return str.matches("[a-zA-Z]+"); // Only allows letters (no spaces or special characters)
    }

    // Method to validate if a string can be parsed to a positive double
    private boolean isValidPositiveDouble(String str) {
        try {
            double value = Double.parseDouble(str);
            return value > 0; // Check if value is positive
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to validate if a string can be parsed to a positive integer
    private boolean isValidPositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str);
            return value > 0; // Check if value is positive
        } catch (NumberFormatException e) {
            return false;
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
    void handleDeleteFlightclassButton() {
        Flightclass selectedFlightclass = flightclassTableView.getSelectionModel().getSelectedItem();
        if (selectedFlightclass != null) {
            flightclassDAO.deleteFlightclass(selectedFlightclass.getId());
            // Create a notification with styling
            Notifications notification = Notifications.create()
                    .title("FlightClass")
                    .text("FlightClass Deleted successfully ")
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







    private void clearFields() {
        newClassNameField.clear();
        newDescriptionField.clear();
        newPriceField.clear();
        newTicketNumberField.clear();
    }









    @FXML
    private TextField flightClassNameSearchField;

    private void handleFlightClassNameSearch() {
        String searchTerm = flightClassNameSearchField.getText().trim().toLowerCase();
        ObservableList<Flightclass> allFlightClasses = flightclassTableView.getItems();
        ObservableList<Flightclass> filteredFlightClasses = FXCollections.observableArrayList();

        // Filter flight classes where the class name contains the search term
        for (Flightclass flightclass : allFlightClasses) {
            if (flightclass.getClassName().toLowerCase().contains(searchTerm)) {
                filteredFlightClasses.add(flightclass);
            }
        }

        // Update the table view with search result
        flightclassTableView.setItems(filteredFlightClasses);
    }










}
