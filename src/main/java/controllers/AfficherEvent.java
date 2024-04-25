package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import models.Event;
import models.TypeEvenement;
import services.ServiceEvent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ServiceTypeEvent;

public class AfficherEvent implements Initializable {

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;
    ServiceEvent scom = new ServiceEvent();
    Event event;
    private ServiceTypeEvent scomTypeEvenement = new ServiceTypeEvent();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Event> eventList = scom.afficherEvent();
        vbox1.setFillWidth(true);

        for (Event event : eventList) {
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);

            Label label1 = new Label("ID: " + event.getId());
            label1.setLayoutX(15.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));

            Label label2 = new Label(event.getNom_event());
            label2.setLayoutX(100.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));



            Label label3 = new Label(event.getAdresse_event());
            label3.setLayoutX(230.0);
            label3.setLayoutY(18.0);
            label3.setPrefHeight(25.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font(16.0));

            Label label5 = new Label(String.valueOf(event.getNbrticketsdispo()));
            label5.setLayoutX(350.0);
            label5.setLayoutY(18.0);
            label5.setPrefHeight(25.0);
            label5.setPrefWidth(200.0);
            label5.setFont(new Font(16.0));

            Label label6 = new Label(String.valueOf(event.getDatedebut_event()));
            label6.setLayoutX(450.0);
            label6.setLayoutY(18.0);
            label6.setPrefHeight(25.0);
            label6.setPrefWidth(200.0);
            label6.setFont(new Font(16.0));


            Label label7 = new Label(String.valueOf(event.getDatefinevent()));
            label7.setLayoutX(550.0);
            label7.setLayoutY(18.0);
            label7.setPrefHeight(25.0);
            label7.setPrefWidth(200.0);
            label7.setFont(new Font(16.0));



            Label label8 = new Label(String.valueOf(event.getPrixentre()));
            label8.setLayoutX(650.0);
            label8.setLayoutY(18.0);
            label8.setPrefHeight(25.0);
            label8.setPrefWidth(200.0);
            label8.setFont(new Font(16.0));


// Récupérer le nom de la typeEvent
            TypeEvenement category = scomTypeEvenement.getTypeEventById(event.getType_evenement_id());
            String typeEvent = category.getNom_type();

            Label label10 = new Label(typeEvent);
            label10.setLayoutX(750.0);
            label10.setLayoutY(18.0);
            label10.setPrefHeight(25.0);
            label10.setPrefWidth(200.0);
            label10.setFont(new Font(16.0));

            Label label11 = new Label(String.valueOf(event.getUser_id()));
            label11.setLayoutX(850.0);
            label11.setLayoutY(18.0);
            label11.setPrefHeight(25.0);
            label11.setPrefWidth(200.0);
            label11.setFont(new Font(16.0));
            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(20.0);
            imageView1.setFitWidth(20.0);
            imageView1.setLayoutX(900.0);
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
                    scom.supprimerEvent(event.getId());
                    eventList.remove(event);
                    parent.getChildren().remove(pane);
                }
            });

            ImageView imageView2 = new ImageView();
            Image image2 = new Image(getClass().getResourceAsStream("/redaction.gif"));
            imageView2.setImage(image2);
            imageView2.setFitHeight(20.0);
            imageView2.setFitWidth(20.0);
            imageView2.setLayoutX(920.0);
            imageView2.setLayoutY(20.0);
            imageView2.setPickOnBounds(true);
            imageView2.setPreserveRatio(true);
            imageView2.setOnMouseClicked((MouseEvent event1) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
                    Parent root = loader.load();

                    // Récupérer le contrôleur de la fenêtre de modification
                    ModifierEvent modifierController = loader.getController();

                    // Transmettre les données nécessaires au contrôleur de modification
                    modifierController.initData(event.getId(), event.getNom_event(), event.getAdresse_event(), event.getNbrticketsdispo(), event.getDatedebut_event(), event.getDatefinevent(),event.getPrixentre(),event.getImageevent(),event.getType_evenement_id(),event.getUser_id());

                    // Changer la scène
                    vbox1.getScene().setRoot(root);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            Line line = new Line();
            line.setStrokeWidth(0.4);
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(850.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);
            pane.getChildren().addAll(label1, label2, label3, label5, label6,label7,label8 ,label10,label11, imageView1, imageView2, line);
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);
    }

    @FXML
    void goToAjouterEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    @FXML
    void goToMenu(ActionEvent event) {

        try {
            FXMLLoader loader = createFXMLLoader("/Menu.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
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
    void goToEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    @FXML
    void goToReservationEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherReservationEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    @FXML
    void goToTypeEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherTypeEvent.fxml");
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


