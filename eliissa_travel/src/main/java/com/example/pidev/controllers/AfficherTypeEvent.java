package com.example.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import com.example.pidev.entities.TypeEvenement;
import com.example.pidev. services.ServiceTypeEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherTypeEvent implements Initializable{

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;

    TypeEvenement typeEvenement;
    private ServiceTypeEvent scomTypeEvenement = new ServiceTypeEvent();

    public void initialize(URL url, ResourceBundle rb) {
        List<TypeEvenement> TypeEvenement = scomTypeEvenement.afficherTypeEvenement();
        vbox1.setFillWidth(true);

        for (TypeEvenement typeEvenement : TypeEvenement){
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);

           /* Label label1 = new Label("ID: " + typeEvenement.getId());
            label1.setLayoutX(15.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));*/

            Label label2 = new Label(typeEvenement.getNom_type());
            label2.setLayoutX(100.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));

            Label label3 = new Label(typeEvenement.getType_description());
            label3.setLayoutX(270.0);
            label3.setLayoutY(18.0);
            label3.setPrefHeight(25.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font(16.0));

            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/Event/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(20.0);
            imageView1.setFitWidth(20.0);
            imageView1.setLayoutX(500.0);
            imageView1.setLayoutY(20.0);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked((MouseEvent event1) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText("Êtes-vous sûr de supprimer cette Event ?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Pane parent = (Pane) pane.getParent();
                    scomTypeEvenement.supprimerTypeEvenement(typeEvenement.getId());
                    TypeEvenement.remove(typeEvenement);

                    parent.getChildren().remove(pane);
                }
            });

            ImageView imageView2 = new ImageView();
            Image image2 = new Image(getClass().getResourceAsStream("/Event/redaction.gif"));
            imageView2.setImage(image2);
            imageView2.setFitHeight(20.0);
            imageView2.setFitWidth(20.0);
            imageView2.setLayoutX(530);
            imageView2.setLayoutY(20.0);
            imageView2.setPickOnBounds(true);
            imageView2.setPreserveRatio(true);
            imageView2.setOnMouseClicked((MouseEvent event) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/ModifierTypeEvent.fxml"));
                    Parent root = loader.load();

                    // Récupérer le contrôleur de la fenêtre de modification
                    ModifierTypeEvent modifierController = loader.getController();

                    // Transmettre les données nécessaires au contrôleur de modification
                    modifierController.initData(typeEvenement.getId(), typeEvenement.getNom_type(), typeEvenement.getType_description());

                    // Changer la scène
                    vbox1.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Line line = new Line();
           /* line.setStrokeWidth(0.4);*/
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(850.0);
            line.setStrokeWidth(2.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);
            line.setStroke(Color.web("#1F1E2F"));

            // Créer une ligne verticale
            Line verticalLine = new Line();
            verticalLine.setStartX(250.0); // Position X de la ligne
            verticalLine.setStartY(0.0); // Position Y de départ de la ligne
            verticalLine.setStrokeWidth(1.0);
            verticalLine.setEndX(250.0); // Position X de fin de la ligne
            verticalLine.setEndY(62.0); // Position Y de fin de la ligne (taille du pane)
            verticalLine.setStroke(Color.web("#1F1E2F")); // Couleur de la ligne



            pane.getChildren().addAll( label2, label3, imageView1, imageView2, line);
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);
    }

    @FXML
    void gotoajoutertype(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/AjouterType.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }



    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }


   /* @FXML
    void goToEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargeument de la vue : " + ex.getMessage());
        }
    }*/

  /*  @FXML
    void goToReservationEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherReservationEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }*/

    @FXML
    void goToTypeEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Event/AfficherTypeEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
    @FXML
    void NavConnexion(ActionEvent event) {

    }
}
