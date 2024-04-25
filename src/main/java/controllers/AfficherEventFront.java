package  controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Event;
import models.ReservationEvent;
import services.ServiceEvent;
import services.ServiceReservationEvent;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEventFront implements Initializable {

    @FXML
    private GridPane gridPane;
    private final ServiceEvent sa = new ServiceEvent();
    private final ServiceReservationEvent serviceReservationEvent = new ServiceReservationEvent(); // Instanciez le service de réservation

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Event> eventList = sa.afficherEvent();

        gridPane.setHgap(10); // Augmentez l'espace horizontal entre les cartes
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        int col = 0;
        int row = 0;

        for (Event event : eventList) {
            BorderPane eventPane = createEventPane(event);

            // Ajouter des marges autour de chaque BorderPane pour créer un espace entre les cartes
            BorderPane.setMargin(eventPane, new Insets(20));

            gridPane.add(eventPane, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    private BorderPane createEventPane(Event event) {
        BorderPane eventPane = new BorderPane();
        eventPane.getStyleClass().add("event-pane");
        eventPane.setPrefSize(200, 300);
        eventPane.setPadding(new Insets(10));
        eventPane.setEffect(new DropShadow(5.0, Color.gray(0.5)));

        Label eventNameLabel = new Label("Nom Evenement : "+event.getNom_event());
        eventNameLabel.getStyleClass().add("event-name-label");

        ImageView image = createEventImage(event);
        Label price = createEventPrice(event);

        VBox imageAndPriceContainer = new VBox(image, price);
        imageAndPriceContainer.setAlignment(Pos.CENTER);

        eventPane.setTop(eventNameLabel);
        eventPane.setAlignment(eventNameLabel, Pos.CENTER);
        eventPane.setCenter(imageAndPriceContainer);

        ImageView reservationImageView = createReservationImageView(event);
        eventPane.setBottom(reservationImageView);
        eventPane.setAlignment(reservationImageView, Pos.BOTTOM_RIGHT);

        return eventPane;
    }

    private ImageView createEventImage(Event event) {
        ImageView image = new ImageView();
        image.setFitHeight(150.0);
        image.setFitWidth(200.0);
        image.setPreserveRatio(true);

        try {
            File uploadedFile = new File(event.getImageevent());
            String fileUrl = uploadedFile.toURI().toString();
            Image imageSource = new Image(fileUrl);
            image.setImage(imageSource);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'erreur de chargement de l'image ici
        }

        return image;
    }

    private Label createEventPrice(Event event) {
        Label price = new Label();
        price.setText("PRIX : " + event.getPrixentre() + " DT");
        price.getStyleClass().add("price-label");
        return price;
    }

    private ImageView createReservationImageView(Event event) {
        ImageView reservationImageView = new ImageView();
        reservationImageView.setFitHeight(30.0);
        reservationImageView.setFitWidth(30.0);
        reservationImageView.setPreserveRatio(true);

        Image reservationImage = new Image(getClass().getResourceAsStream("/reservation.png"));
        reservationImageView.setImage(reservationImage);

        Tooltip tooltip = new Tooltip("Réserver l'événement");
        Tooltip.install(reservationImageView, tooltip);

        reservationImageView.setOnMouseClicked(eventt -> handleReservationClick(event)); // Passer l'événement lors du clic

        return reservationImageView;
    }

    private void handleReservationClick(Event event) {
        // Créez un objet ReservationEvent avec les ID appropriés
        ReservationEvent reservationEvent = new ReservationEvent();
        reservationEvent.setUser_id(1); // 1 est l'ID utilisateur statique
        reservationEvent.setEvent_id(event.getId());

        // Enregistrez la réservation en utilisant le service de réservation
        serviceReservationEvent.ajouterReservationEvent(reservationEvent);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Événement réservé");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez réservé l'événement : " + event.getNom_event());
        alert.showAndWait();
    }
}
