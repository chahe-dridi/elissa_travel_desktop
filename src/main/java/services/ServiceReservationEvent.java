package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.ReservationEvent;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceReservationEvent implements IServiceReservationEvent {
    private final Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouterReservationEvent(ReservationEvent reservationEvent) {
        String req = "INSERT INTO `reservation_event`(`user_id`, `event_id`) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, reservationEvent.getUser_id());
            ps.setInt(2, reservationEvent.getEvent_id());
            ps.executeUpdate();
            System.out.println("Réservation ajoutée avec succès!");
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

}
