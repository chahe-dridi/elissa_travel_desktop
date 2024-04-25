package controllers;

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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Event;
import models.ReservationEvent;
import models.User;
import services.ServiceEvent;
import services.ServiceReservationEvent;
import services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherReservationEvent   implements Initializable {
    ServiceReservationEvent scom = new ServiceReservationEvent();
    ServiceUser serviceUser=new ServiceUser();
    ServiceEvent serviceEvent=new ServiceEvent();
ReservationEvent reservationEvent;
    @FXML
    private AnchorPane nh;

    @FXML
    private Text trole;

    @FXML
    private VBox vbox1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<ReservationEvent> reservationEventList = scom.afficherResrvationEvent();

        vbox1.setFillWidth(true);

        for (ReservationEvent reservationEvent1 : reservationEventList) {
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);
            // Récupérer le nom de user
            User user = serviceUser.getNomById(reservationEvent1.getUser_id());
            String nomUser = user.getNom();

            Label label1 = new Label(nomUser);
            label1.setLayoutX(4.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));

            // Récupérer le prenom de user
            String prenomUser = user.getPrenom();
            Label label2 = new Label(prenomUser);
            label2.setLayoutX(150.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));

            // Récupérer le nom event
            Event event = serviceEvent.getNomEventById(reservationEvent1.getEvent_id());
            String nom_event = event.getNom_event();

            Label label3 = new Label(nom_event);
            label3.setLayoutX(350.0);
            label3.setLayoutY(18.0);
            label3.setPrefHeight(25.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font(16.0));
            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(20.0);
            imageView1.setFitWidth(20.0);
            imageView1.setLayoutX(580);
            imageView1.setLayoutY(20.0);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked((MouseEvent eventt) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText("Êtes-vous sûr de supprimer cette reservation ?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Pane parent = (Pane) pane.getParent();
                    scom.supprimerReservationEvent(reservationEvent1.getId());
                    reservationEventList.remove(reservationEvent1);
                    parent.getChildren().remove(pane);
                }
            });
            Line line = new Line();
            line.setStrokeWidth(0.4);
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(429.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);

            pane.getChildren().addAll(label2, label1, label3, imageView1, line);
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);
    }
            @FXML
    void NavConnexion(ActionEvent event) {

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
    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }

}
