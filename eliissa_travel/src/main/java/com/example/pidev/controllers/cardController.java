package com.example.pidev.controllers;

import com.example.pidev.entities.Voyage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class cardController  implements Initializable {

    @FXML
    private Label NomTrip;

    @FXML
    private Button buttonBook;

    @FXML
    private Button buttonRead;

    @FXML
    private ImageView img;
    @FXML
    private Label nomVilleDepart;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void SetData(Voyage voyage){
        NomTrip.setText(voyage.getDestination());
        nomVilleDepart.setText(voyage.getVilledepart());
    }
    @FXML
    private  void buttonBook() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/reservationVoyage.fxml"));
        Parent parent = loader.load();
        reservationVoyageController controller = loader.getController();
        NomTrip.getScene().setRoot(parent);

    }






}
