package com.example.elissa.Controller;



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

public class BaseController implements Initializable {

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFXML("/src/main/resources/com/example/elissa/Airport/index.fxml");
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goto_airport(ActionEvent actionEvent) throws IOException {
        loadFXML("/src/main/resources/com/example/elissa/Airport/index.fxml");
    }

    public void goto_reservation_flight(ActionEvent actionEvent) throws IOException {
        loadFXML("/src/main/resources/com/example/elissa/Airport/reservationvoladmin.fxml");
    }

    public void goto_flight_class(ActionEvent actionEvent) throws IOException {
        loadFXML("/src/main/resources/com/example/elissa/Airport/flightclass.fxml");
    }

    public void goto_flights(ActionEvent actionEvent) throws IOException {
        loadFXML("/src/main/resources/com/example/elissa/Airport/flight.fxml");
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
}
