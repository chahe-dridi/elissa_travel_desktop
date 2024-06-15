package com.example.pidev.controllers;



import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseController implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFXML("/Airport/base_ellisa.fxml");
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goto_airport(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/index.fxml");
    }

    public void goto_reservation_flight(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/reservationvoladmin.fxml");
    }

    public void goto_flight_class(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/flightclass.fxml");
    }

    public void goto_flights(ActionEvent actionEvent) throws IOException {
        loadFXML("/Airport/flight.fxml");
    }

    /*private void loadFXML(String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Parent fxml = fxmlLoader.load();

        // Add fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), fxml);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        contentArea.getChildren().setAll(fxml);
    }*/
    private void loadFXML(String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        Parent fxml = fxmlLoader.load();

        // Set initial position outside the content area
        fxml.setTranslateX(-contentArea.getWidth());

        // Add fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), fxml);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Add translate transition to slide in from the left
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), fxml);
        translateTransition.setToX(0);

        // Combine fade and translate transitions
        ParallelTransition parallelTransition = new ParallelTransition(fxml, fadeTransition, translateTransition);
        parallelTransition.play();

        contentArea.getChildren().setAll(fxml);
    }



    public void go_back_to_dashbaord(ActionEvent actionEvent) throws IOException {
        loadFXML("/Dashboard.fxml");
    }

    public void go_to_reservation_event(ActionEvent actionEvent) throws IOException{
        loadFXML("/Event/AfficherReservationEvent.fxml");
    }

    public void gototypeevent(ActionEvent actionEvent) throws IOException{
        loadFXML("/Event/AfficherTypeEvent.fxml");

    }

    public void gotoeventyesmine(ActionEvent actionEvent) throws IOException{
        loadFXML("/Event/AfficherEvent.fxml");

    }
    public void go_to_login_discooenct(ActionEvent actionEvent) throws IOException {
        loadFXML("/login.fxml");
    }
    public void goto_trip(ActionEvent actionEvent) throws IOException {
        loadFXML("/Voyage/index.fxml");
    }
    public void goto_trip_program(ActionEvent actionEvent) throws IOException {
        loadFXML("/Voyage/programmeIndex.fxml");
    }
    public void goto_trip_reservation(ActionEvent actionEvent) throws IOException {
        loadFXML("/Voyage/afficheReservation.fxml");
    }


    public void goto_hotel(ActionEvent actionEvent) throws IOException{
        loadFXML("/Hotel/Liste.fxml");
    }


    @FXML
    private Button goto_sig;

    public void goto_signout_from_admin(ActionEvent actionEvent)throws IOException {
        //loadFXML("/Login.fxml");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) goto_sig.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



}
