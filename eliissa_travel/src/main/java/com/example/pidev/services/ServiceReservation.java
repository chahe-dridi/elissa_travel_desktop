package com.example.pidev.services;


import com.example.pidev.entities.Reservation;
import com.example.pidev.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceReservation {

    private final Connection connection;

    public ServiceReservation() {
        this.connection = MyDB.getInstance().getCnx();
    }

    public void ajouterReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservation_hotel (hotel_id, date_arrive, date_depart, email, nom, pre_nom, distination, chambr_e) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getHotelId());
            statement.setDate(2,reservation.getDateArrive());
            statement.setDate(3, reservation.getDateDepart());
            statement.setString(4, reservation.getEmail());
            statement.setString(5, reservation.getNom());
            statement.setString(6, reservation.getPreNom());
            statement.setString(7, reservation.getDistination());
            statement.setString(8, reservation.getChambr_e());
            statement.executeUpdate();
        }
    }

    public void supprimerReservation(int id) throws SQLException {
        String query = "DELETE FROM reservation_hotel WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation_hotel";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Date dateArrive = resultSet.getDate("date_arrive");
                Date dateDepart = resultSet.getDate("date_depart");
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("pre_nom");
                String destination = resultSet.getString("distination");
                String chambre = resultSet.getString("chambr_e");
                Reservation reservation = new Reservation( dateArrive, dateDepart, email, nom, prenom, destination, chambre);
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}
