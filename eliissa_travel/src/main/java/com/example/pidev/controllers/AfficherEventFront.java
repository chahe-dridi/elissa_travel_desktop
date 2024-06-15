package com.example.pidev.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import com.example.pidev.entities.TypeEvenement;
import com.example.pidev.entities.Event;
import com.example.pidev.entities.ReservationEvent;
import com.example.pidev.entities.User;
import com.example.pidev. services.ServiceTypeEvent;
import com.example.pidev. services.ServiceEvent;
import com.example.pidev. services.ServiceReservationEvent;






import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEventFront implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<TypeEvenement> typeevent; // Update the type of the ComboBox

    private final ServiceEvent sa = new ServiceEvent();
    private final ServiceReservationEvent serviceReservationEvent = new ServiceReservationEvent();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate typeevent ComboBox
        ServiceTypeEvent serviceTypeEvent = new ServiceTypeEvent();
        ObservableList<TypeEvenement> typeList = serviceTypeEvent.afficherTypeEvenement2();
        typeevent.setItems(typeList);
        typeevent.setConverter(new StringConverter<TypeEvenement>() {
            @Override
            public String toString(TypeEvenement type) {
                return type.getNom_type();
            }

            @Override
            public TypeEvenement fromString(String string) {
                return null;
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchEvents(newValue);
        });

        loadAllEvents();
    }

    private void loadAllEvents() {
        List<Event> eventList = sa.afficherEvent();
        displayEvents(eventList);
    }

    private void searchEvents(String eventName) {
        if (eventName == null || eventName.isEmpty()) {
            loadAllEvents();
        } else {
            List<Event> searchResults = sa.rechercherEventParNom(eventName);
            displayEvents(searchResults);
        }
    }

    private void displayEvents(List<Event> events) {
        gridPane.getChildren().clear();

        int col = 0;
        int row = 0;

        for (Event event : events) {
            BorderPane eventPane = createEventPane(event);
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

        Label eventNameLabel = new Label("Nom Evenement : " + event.getNom_event());
        eventNameLabel.getStyleClass().add("event-name-label");

        ImageView image = createEventImage(event);
        Label price = createEventPrice(event);
        Label nbr = createEventNbr(event);

        VBox imageAndPriceContainer = new VBox(image, price, nbr);
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
            String fileName = event.getImageevent(); // Obtenir le nom du fichier
            String fileUrl = "file:C:/Users/msi/Desktop/elissa_travel+all+5_v0.5/wala/src/main/resources/Event/" + fileName; // Chemin complet vers le fichier
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

    private Label createEventNbr(Event event) {
        Label nbr = new Label();
        nbr.setText("nbr des tickets " + event.getNbrticketsdispo());
        nbr.getStyleClass().add("nbr-label");
        return nbr;
    }

    private ImageView createReservationImageView(Event event) {
        ImageView reservationImageView = new ImageView();
        reservationImageView.setFitHeight(30.0);
        reservationImageView.setFitWidth(30.0);
        reservationImageView.setPreserveRatio(true);

        Image reservationImage = new Image(getClass().getResourceAsStream("/Event/reservation.png"));
        reservationImageView.setImage(reservationImage);

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

        // Vérifiez si des tickets sont disponibles pour cet événement
        if (event.getNbrticketsdispo() > 0) {
            // S'il y a des tickets disponibles, envoyez le SMS et mettez à jour l'affichage
             String msg = "Une réservation a été faite pour l'événement : " + event.getNom_event();
             serviceReservationEvent.envoyerSMS("+21695281478", msg);

            // Si des tickets sont disponibles, ouvrez la page eventemail.fxml pour envoyer le message

            List<Event> updatedEventList = sa.afficherEvent();
            // Clear the current gridPane content
            gridPane.getChildren().clear();
            // Rebuild the gridPane with updated event data
            int col = 0;
            int row = 0;
            for (Event updatedEvent : updatedEventList) {
                BorderPane updatedEventPane = createEventPane(updatedEvent);
                BorderPane.setMargin(updatedEventPane, new Insets(20));
                gridPane.add(updatedEventPane, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            }
            // Affiche une alerte pour confirmer la réservation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Événement réservé");
            alert.setHeaderText(null);
            alert.setContentText("Vous avez réservé l'événement : " + event.getNom_event());
            alert.showAndWait();
            openEventEmailPage();

        } else {
            // Si aucun ticket n'est disponible, affichez un message approprié
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tickets non disponibles");
            alert.setHeaderText(null);
            alert.setContentText("Désolé, aucun ticket n'est disponible pour cet événement.");
            alert.showAndWait();

            // Envoyer un message SMS approprié lorsque l'événement est plein
            String fullEventMsg = "L'événement " + event.getNom_event() + " est complet. Veuillez le supprimer de l'affichage.";
             serviceReservationEvent.envoyerSMS("+21695281478", fullEventMsg);
        }
    }

    // Ajoutez la méthode pour ouvrir la page eventemail.fxml :
    private void openEventEmailPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/eventemail.fxml"));

            Parent root = loader.load();

            // Créez une nouvelle scène avec la racine chargée et affichez-la
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotofilter(ActionEvent event) {
        List<Event> eventList = sa.afficherEvent();
        Collections.sort(eventList, Comparator.comparingInt(Event::getPrixentre));
        displayEvents(eventList);
    }

    @FXML
    void filterByEventType(ActionEvent event) {
        TypeEvenement selectedType = typeevent.getValue();
        if (selectedType != null) {
            int typeId = selectedType.getId();
            List<Event> filteredEvents = sa.rechercherEventParType(typeId);
            displayEvents(filteredEvents);
        } else {
            loadAllEvents();
        }
    }
}
