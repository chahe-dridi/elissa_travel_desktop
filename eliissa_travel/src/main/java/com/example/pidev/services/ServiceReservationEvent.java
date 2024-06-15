package com.example.pidev.services;

import com.example.pidev.controllers.LoginController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.example.pidev.utils.MyDB;
import com.example.pidev.entities.ReservationEvent;
import com.example.pidev.entities.Event;
import com.example.pidev.entities.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceReservationEvent implements IServiceReservationEvent {
    private final Connection cnx = MyDB.getInstance().getCnx();

    @Override
    public void ajouterReservationEvent(ReservationEvent reservationEvent) {
        try {
            // Vérifiez d'abord si des tickets sont disponibles
            User loggedInUser = LoginController.getLoggedInUser();
            String checkTicketsQuery = "SELECT nbrticketsdispo FROM event WHERE id = ?";
            try (PreparedStatement checkPs = cnx.prepareStatement(checkTicketsQuery)) {
                checkPs.setInt(1, reservationEvent.getEvent_id());
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int nbrTicketsDispo = rs.getInt("nbrticketsdispo");
                        if (nbrTicketsDispo > 0) {



                            // S'il y a des tickets disponibles, effectuez la réservation
                            String insertReservationQuery = "INSERT INTO `reservation_event`(`user_id`, `event_id`) VALUES (?,?)";
                            try (PreparedStatement ps = cnx.prepareStatement(insertReservationQuery)) {
                                ps.setInt(1, loggedInUser.getId());
                                ps.setInt(2, reservationEvent.getEvent_id());
                                ps.executeUpdate();
                                System.out.println("Réservation ajoutée avec succès!");

                                // Mettre à jour le nombre de tickets disponibles
                                String updateEventQuery = "UPDATE `event` SET `nbrticketsdispo` = `nbrticketsdispo` - 1 WHERE `id` = ?";
                                try (PreparedStatement updatePs = cnx.prepareStatement(updateEventQuery)) {
                                    updatePs.setInt(1, reservationEvent.getEvent_id());
                                    updatePs.executeUpdate();
                                    System.out.println("Nombre de tickets disponibles mis à jour");
                                }
                            }
                        } else {
                            System.out.println("Aucun ticket disponible pour cet événement !");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReservationEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<ReservationEvent> afficherResrvationEvent() {
        List<ReservationEvent> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation_event";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                ReservationEvent reservation = new ReservationEvent(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("event_id")
                );
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReservationEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservations;
    }

    @Override
    public void modifierReservationEvent(ReservationEvent reservationEvent) {
        try {
            String req = "UPDATE `reservation_event` SET `user_id`=?, `event_id`=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, reservationEvent.getUser_id());
                st.setInt(2, reservationEvent.getEvent_id());
                st.setInt(3, reservationEvent.getId());
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Réservation modifiée avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour la réservation avec l'ID " + reservationEvent.getId());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReservationEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerReservationEvent(int id) {
        try {
            String req = "DELETE FROM `reservation_event` WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Réservation supprimée avec succès");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReservationEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 public void envoyerSMS(String numeroDestinataire, String message) {
        // Remplacez les valeurs suivantes par vos propres informations Twilio
        String ACCOUNT_SID = "";
        String AUTH_TOKEN = "";
        String TWILIO_NUMERO = "+12176693891";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message twilioMessage = Message.creator(
                        new PhoneNumber(numeroDestinataire),
                        new PhoneNumber(TWILIO_NUMERO),
                        message)
                .create();

        System.out.println("SMS envoyé avec succès avec l'ID : " + twilioMessage.getSid() );
    }


}
