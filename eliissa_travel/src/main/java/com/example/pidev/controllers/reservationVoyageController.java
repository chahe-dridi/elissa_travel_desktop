package com.example.pidev.controllers;

import com.example.pidev.entities.ReservationVoyage;
import com.example.pidev.services.reservationVoyageDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class reservationVoyageController implements Initializable {

    @FXML
    private Button DoneReservation;


    @FXML
    private TextField Remail;

    @FXML
    private TextField Rnom;

    @FXML
    private TextField Rnumberticket;

    @FXML
    private TextField Rprenom;
    @FXML
    private Button btnReturn;


    reservationVoyageDAO reservationVoyageDAO = new reservationVoyageDAO();


    @FXML
    void createReservation(ActionEvent event) throws IOException {

        // Récupérer les valeurs des champs de texte
        String nom = Rnom.getText();
        String prenom = Rprenom.getText();
        int numberticket = Integer.parseInt(Rnumberticket.getText());
        String email = Remail.getText();

        ReservationVoyage reservationVoyage = new ReservationVoyage(nom, prenom, numberticket, email);


        reservationVoyageDAO.addReservationVoyage(reservationVoyage);
        sendConfirmationEmail(reservationVoyage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/afficheClient.fxml"));
        Parent parent = loader.load();
        afficheClientController controller = loader.getController();
        Rnom.getScene().setRoot(parent);


    }

    private void sendConfirmationEmail(ReservationVoyage reservationVoyage) {
        // Paramètres de configuration pour le serveur SMTP (remplacez-les par vos propres informations)
        String host = "smtp.gmail.com";
        String username = "walahamdi0@gmail.com";
        String password = "ezqcncluccewfspa";

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Session SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reservationVoyage.getEmail()));
            message.setSubject("Confirmation de réservation numéro" + reservationVoyage.getVoyageId());
            message.setText("Bonjour " + reservationVoyage.getNom() + " " + reservationVoyage.getPrenom() + ",\n\nVotre réservation a été confirmée avec succès.");

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de confirmation envoyé avec succès.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
        }
    }


    private void clearFields() {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}






