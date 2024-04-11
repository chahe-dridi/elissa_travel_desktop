package com.example.elissa.Controller;

import com.example.elissa.Models.Airport;
import com.example.elissa.Models.Flightclass;
import com.example.elissa.Services.FlightclassDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ticketNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ticketNumber"));
    }

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

    private void refreshTableView() {
        List<Flightclass> flightclasses = flightclassDAO.getAllFlightclasses();
        ObservableList<Flightclass> flightclassObservableList = FXCollections.observableArrayList(flightclasses);
        flightclassTableView.setItems(flightclassObservableList);
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

    private void populateFields(Flightclass flightclass) {
        newClassNameField.setText(flightclass.getClassName());
        newDescriptionField.setText(flightclass.getDescription());
        newPriceField.setText(String.valueOf(flightclass.getPrice()));
        newTicketNumberField.setText(String.valueOf(flightclass.getTicketNumber()));
    }

    @FXML
    void handleDeleteFlightclassButton() {
        Flightclass selectedFlightclass = flightclassTableView.getSelectionModel().getSelectedItem();
        if (selectedFlightclass != null) {
            flightclassDAO.deleteFlightclass(selectedFlightclass.getId());
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
    void handleAirportsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/index.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightclassTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightsButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flight.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightclassTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleFlightClassButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/flightclass.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightclassTableView.getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    void handleReservationButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/elissa/Airport/reservationvoladmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) flightclassTableView.getScene().getWindow();
        stage.setScene(scene);
    }









}
