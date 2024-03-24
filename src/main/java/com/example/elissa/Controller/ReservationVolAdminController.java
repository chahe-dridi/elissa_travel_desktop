package com.example.elissa.Controller;

import com.example.elissa.Models.ReservationVolAdmin;
import com.example.elissa.Services.ReservationVolAdminDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;








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
        int volId = Integer.parseInt(newReservationVolIdField.getText());
        int userId = Integer.parseInt(newReservationUserIdField.getText());
        double totalPrice = Double.parseDouble(newReservationTotalPriceField.getText());
        String paymentMethod = newReservationPaymentMethodField.getText();

        ReservationVolAdmin newReservation = new ReservationVolAdmin(volId, userId, totalPrice, paymentMethod);
        reservationDAO.addReservation(newReservation);
        refreshTableView();
        clearFields();
    }

    @FXML
    void handleModifyReservationButton() {
        ReservationVolAdmin selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            int volId = Integer.parseInt(newReservationVolIdField.getText());
            int userId = Integer.parseInt(newReservationUserIdField.getText());
            double totalPrice = Double.parseDouble(newReservationTotalPriceField.getText());
            String paymentMethod = newReservationPaymentMethodField.getText();

            selectedReservation.setVolId(volId);
            selectedReservation.setUserId(userId);
            selectedReservation.setTotalPrice(totalPrice);
            selectedReservation.setPaymentMethod(paymentMethod);

            reservationDAO.updateReservation(selectedReservation);
            refreshTableView();
            clearFields();
        }
    }

    @FXML
    void handleDeleteReservationButton() {
        ReservationVolAdmin selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationDAO.deleteReservation(selectedReservation.getId());
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
