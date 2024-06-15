package com.example.pidev.controllers;

import com.example.pidev.entities.Chambre;
import com.example.pidev.entities.Reservation;
import com.example.pidev.services.ServiceChambre;
import com.example.pidev.services.ServiceReservation;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    private ServiceChambre serviceChambre = new ServiceChambre();
    private ServiceReservation serviceReservation = new ServiceReservation();
    private Connection connection;

    // Vos identifiants Twilio
    private static final String ACCOUNT_SID = "";
    private static final String AUTH_TOKEN = "";
    // Le numéro Twilio à partir duquel vous envoyez le SMS
    private static final String TWILIO_PHONE_NUMBER = "+14244041721";

    @FXML
    private ComboBox<String> chambrech;

    @FXML
    private DatePicker date_arr;

    @FXML
    private DatePicker date_depart;

    @FXML
    private TextField destination;

    @FXML
    private TextField email_u;

    @FXML
    private ComboBox<String> hotelCh;

    @FXML
    private TextField nom_u;

    @FXML
    private TextField prenom_u;

    @FXML
    private TextField numTel;

    @FXML
    private Label reser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elissa_travel", "root", "");
            initializeHotelComboBox();
            initializeChambreComboBox();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Impossible de se connecter à la base de données.");
        }
    }

    private void initializeHotelComboBox() {
        ObservableList<String> hotelNames = serviceChambre.getAllHotelNames();
        hotelCh.setItems(hotelNames);
    }

    private void initializeChambreComboBox() {
        ObservableList<Chambre> chambres = serviceChambre.getAllChambres();
        for (Chambre chambre : chambres) {
            chambrech.getItems().add(chambre.getTypeChambre());
        }
    }

    @FXML
    void reserver(ActionEvent event) throws SQLException {
        String nom = nom_u.getText();
        String prenom = prenom_u.getText();
        String email = email_u.getText();
        String dest = destination.getText();
        String hotel = hotelCh.getValue();
        String chambre = chambrech.getValue();
        Date dateArrive = Date.valueOf(date_arr.getValue());
        Date dateDepart = Date.valueOf(date_depart.getValue());

        Reservation reservation = new Reservation(dateDepart, dateArrive, nom, prenom, email, dest, chambre);

        serviceReservation.ajouterReservation(reservation);

        // Envoi du SMS de confirmation
        sendConfirmationSMS(reservation);
    }

    private void sendConfirmationSMS(Reservation reservation) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Le numéro de téléphone du destinataire
        String recipientPhoneNumber = numTel.getText();

        // Votre message de confirmation
        String messageBody = "Votre réservation a été confirmée. Merci!";

        // Envoi du SMS
        Message message = Message.creator(
                        new PhoneNumber(recipientPhoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        messageBody)
                .create();

        // Affichage de l'identifiant du message envoyé
        System.out.println("Message SID: " + message.getSid());
    }

    @FXML
    void switchToReservations(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hotel/ReservationListe.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la fenêtre (stage) actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre et l'afficher
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
