package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Models.ReservationVolAdmin;
import com.example.elissa.Services.ReservationVolAdminDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class ReservationVolAdminController {

    private final ReservationVolAdminDAO reservationDAO = new ReservationVolAdminDAO();

    @FXML
    private TableView<ReservationVolAdmin> reservationTableView;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> reservationIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> reservationVolIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Integer> reservationUserIdColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, Double> reservationTotalPriceColumn;

    @FXML
    private TableColumn<ReservationVolAdmin, String> reservationPaymentMethodColumn;

    @FXML
    private TextField newReservationVolIdField;

    @FXML
    private TextField newReservationUserIdField;

    @FXML
    private TextField newReservationTotalPriceField;

    @FXML
    private TextField newReservationPaymentMethodField;

    @FXML
    private Button addReservationButton;

    @FXML
    private Button updateReservationButton;

    @FXML
    private Button deleteReservationButton;


    @FXML
    private TextField searchReservationField;






    @FXML
    public void initialize() {
        configureTableView();
        refreshTableView();
        setupTableViewListener();
    }

    private void configureTableView() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reservationVolIdColumn.setCellValueFactory(new PropertyValueFactory<>("volId"));
        reservationUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        reservationTotalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        reservationPaymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
    }

    private void refreshTableView() {
        reservationTableView.getItems().clear();
        reservationTableView.getItems().addAll(reservationDAO.getAllReservations());
    }

    private void setupTableViewListener() {
        reservationTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newReservationVolIdField.setText(String.valueOf(newValue.getVolId()));
                newReservationUserIdField.setText(String.valueOf(newValue.getUserId()));
                newReservationTotalPriceField.setText(String.valueOf(newValue.getTotalPrice()));
                newReservationPaymentMethodField.setText(newValue.getPaymentMethod());
            }
        });
    }






    @FXML
    void handleNewReservationButton() {
        String volIdText = newReservationVolIdField.getText().trim();
        String userIdText = newReservationUserIdField.getText().trim();
        String totalPriceText = newReservationTotalPriceField.getText().trim();
        String paymentMethod = newReservationPaymentMethodField.getText().trim();

        // Validate input fields
        if (!isValidPositiveInteger(volIdText) || !isValidPositiveInteger(userIdText) || !isValidPositiveDouble(totalPriceText) || !isValidPaymentMethod(paymentMethod)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid values for all fields.");
            return;
        }

        int volId = Integer.parseInt(volIdText);
        int userId = Integer.parseInt(userIdText);
        double totalPrice = Double.parseDouble(totalPriceText);

        ReservationVolAdmin newReservation = new ReservationVolAdmin(volId, userId, totalPrice, paymentMethod);
        reservationDAO.addReservation(newReservation);


        Notifications notification = Notifications.create()
                .title("Reservation Flight")
                .text("Reservation Flight Added successfully ")
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
    void handleModifyReservationButton() {
        ReservationVolAdmin selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            String volIdText = newReservationVolIdField.getText().trim();
            String userIdText = newReservationUserIdField.getText().trim();
            String totalPriceText = newReservationTotalPriceField.getText().trim();
            String paymentMethod = newReservationPaymentMethodField.getText().trim();

            // Validate input fields
            if (!isValidPositiveInteger(volIdText) || !isValidPositiveInteger(userIdText) || !isValidPositiveDouble(totalPriceText) || !isValidPaymentMethod(paymentMethod)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid values for all fields.");
                return;
            }

            int volId = Integer.parseInt(volIdText);
            int userId = Integer.parseInt(userIdText);
            double totalPrice = Double.parseDouble(totalPriceText);

            selectedReservation.setVolId(volId);
            selectedReservation.setUserId(userId);
            selectedReservation.setTotalPrice(totalPrice);
            selectedReservation.setPaymentMethod(paymentMethod);

            reservationDAO.updateReservation(selectedReservation);

            Notifications notification = Notifications.create()
                    .title("Reservation Flight")
                    .text("Reservation Flight Updated successfully ")
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

    // Method to validate if a string can be parsed to a positive integer
    private boolean isValidPositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str);
            return value > 0; // Check if value is positive
        } catch (NumberFormatException e) {
            return false;
        }
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

    // Method to validate payment method (either "Online" or "Cash" case-insensitive)
    private boolean isValidPaymentMethod(String str) {
        return str.equalsIgnoreCase("Online") || str.equalsIgnoreCase("Cash");
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
    void handleSearchReservationFieldTextChanged() {
        String searchQuery = searchReservationField.getText().trim();
        if (!searchQuery.isEmpty()) {
            // Perform search by query
            List<ReservationVolAdmin> searchResult = reservationDAO.searchReservationByQuery(searchQuery);
            // Update the table view with search result
            updateTableView(searchResult);
        } else {
            // If search query is empty, refresh the table view with all reservations
            refreshTableView();
        }
    }

    private void updateTableView(List<ReservationVolAdmin> searchResult) {
        // Clear existing items in the table
        reservationTableView.getItems().clear();
        // Create an observable list from the search result
        ObservableList<ReservationVolAdmin> reservationObservableList = FXCollections.observableArrayList(searchResult);
        // Set the items of the table view
        reservationTableView.setItems(reservationObservableList);
    }




















    @FXML
    void handleDeleteReservationButton() {
        ReservationVolAdmin selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationDAO.deleteReservation(selectedReservation.getId());
            Notifications notification = Notifications.create()
                    .title("Reservation Flight")
                    .text("Reservation Flight Deleted successfully ")
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
        newReservationVolIdField.clear();
        newReservationUserIdField.clear();
        newReservationTotalPriceField.clear();
        newReservationPaymentMethodField.clear();
    }






















    @FXML
    void handleAirportsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/index.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) reservationTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flight.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) reservationTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightClassButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flightclass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) reservationTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleReservationButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationvoladmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) reservationTableView.getScene().getWindow();
        stage.setScene(scene);
    }







}
