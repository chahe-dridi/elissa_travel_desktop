package com.example.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.example.pidev.entities.TypeEvenement;
import com.example.pidev.services.ServiceTypeEvent;
import javafx.stage.Stage;


import java.io.IOException;

public class AjouterType  {

    @FXML
    private AnchorPane nh;

    @FXML
    private TextField fx_descriptionevent;

    @FXML
    private TextField fx_typeevent;

    @FXML
    void ajoutertypeevent(ActionEvent event) {
        String nom_type= fx_typeevent.getText();
        String description_type = fx_descriptionevent.getText();




        if (nom_type.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nom de type evenement.");
            alert.show();
            return;
        }

        if (description_type.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer une description de type.");
            alert.show();
            return;
        }

        ServiceTypeEvent sc = new ServiceTypeEvent();



        sc.ajouterTypeEvenement(new TypeEvenement(nom_type,description_type));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Type Evenement ajouté avec succès !");
        alert.showAndWait();
        retournerAAfficherEvent();
    }

   /* private void retournerAAfficherEvent() {
        try {
           FXMLLoader loader = createFXMLLoader("/Airport/base.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);


        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }*/


    private void retournerAAfficherEvent() {
        try {
            FXMLLoader loader = createFXMLLoader("/Airport/base.fxml");
            Parent root = loader.load();
            AnchorPane newPane = new AnchorPane(root); // Create a new AnchorPane and set its content to the loaded FXML
            nh.getScene().setRoot(newPane); // Set the scene's root to the new AnchorPane
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }


    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
    @FXML
    void goToAffiche(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Airport/base.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
}
