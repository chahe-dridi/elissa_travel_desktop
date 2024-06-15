package com.example.pidev.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontController implements Initializable {


    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFXML("/Airport/front_ellisa.fxml");



        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadFXML(String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Parent fxml = fxmlLoader.load();

        // Add fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), fxml);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        contentArea.getChildren().setAll(fxml);
    }

    public void goto_flights_client(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/showflightclient.fxml");

    }

    public void goto_reservation_client_flight(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/reservationvolclient.fxml");

    }

    public void gotoEventFront(ActionEvent actionEvent) throws IOException  {
        loadFXML("/Event/AfficherEventFront.fxml");

    }

    public void gotoprofileuser(ActionEvent actionEvent)  throws IOException{
        loadFXML("/Profile.fxml");

    }
    public void goto_trip_client(ActionEvent actionEvent) throws IOException {
        loadFXML("/Voyage/afficheClient.fxml");
    }


    public void goto_reservvation_hotel(ActionEvent actionEvent) throws IOException {
        loadFXML("/Hotel/Reservation.fxml");

    }

    public void go_out_from_app(ActionEvent actionEvent) throws IOException{
        loadFXML("/Login.fxml");
    }
}
