package com.example.pidev.controllers;

import com.example.pidev.entities.Hotel;
import com.example.pidev.services.ServiceHotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ListeController {

    private ServiceHotel serviceHotel = new ServiceHotel();

    @FXML
    private TableView<Hotel> tableView;
    @FXML
    private TextField chercherhotels;

    @FXML
    private TableColumn<Hotel, String> nomColumn;
    @FXML
    private TableColumn<Hotel, String> lieuColumn;
    @FXML
    private TableColumn<Hotel, String> telephoneColumn;
    @FXML
    private TableColumn<Hotel, String> emailColumn;
    @FXML
    private TableColumn<Hotel, String> descriptionColumn;
    @FXML
    private TableColumn<Hotel, String> etatColumn;
    @FXML
    private TableColumn<Hotel, Integer> nb_chambreColumn;
    @FXML
    private TableColumn<Hotel, String> imageColumn;

    public void initialize() {
        // Initialize TableView columns
        nomColumn.setCellValueFactory(data -> data.getValue().nom_hotelProperty());
        lieuColumn.setCellValueFactory(data -> data.getValue().lieuProperty());
        telephoneColumn.setCellValueFactory(data -> data.getValue().tel_hotelProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().disc_hotelProperty());
        etatColumn.setCellValueFactory(data -> data.getValue().etat_hotelProperty());
        nb_chambreColumn.setCellValueFactory(data -> data.getValue().nb_chambreProperty().asObject());
        imageColumn.setCellValueFactory(data -> data.getValue().imageProperty());

        // Load data into TableView
        tableView.setItems(serviceHotel.getAllHotels());
    }


    @FXML
    void supprimer(ActionEvent event) {
        Hotel selectedHotel = tableView.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            // Remove the selected hotel from the TableView
            tableView.getItems().remove(selectedHotel);
            // Delete the selected hotel from the database
            serviceHotel.supprimerHotel(selectedHotel.getId());

    }

}


    @FXML
    void switchTohotel(ActionEvent event) {

        try {
            // Charger le fichier FXML de la vue Hotel.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hotel/hotel.fxml"));
            AnchorPane page = loader.load();

            // Récupérer le contrôleur associé à la vue Hotel.fxml
            HotelController controller = loader.getController();

            // Afficher la nouvelle vue dans la fenêtre principale
            Scene scene = new Scene(page);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void rechercherhotel() {
        String nomHotel = chercherhotels.getText();
        List<Hotel> hotels = serviceHotel.rechercherHotels(nomHotel);
        ObservableList<Hotel> observableList = FXCollections.observableArrayList(hotels);
        tableView.setItems(observableList);
    }

}
