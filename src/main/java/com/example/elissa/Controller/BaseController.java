package com.example.elissa.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

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
            Parent fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/example/elissa/Airport/index.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }catch (IOException ex){
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    public void goto_airport(ActionEvent actionEvent) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/example/elissa/Airport/index.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void goto_reservation_flight(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/example/elissa/Airport/reservationvoladmin.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

    public void goto_flight_class(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/example/elissa/Airport/flightclass.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

    public void goto_flights(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/src/main/resources/com/example/elissa/Airport/flight.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
}
