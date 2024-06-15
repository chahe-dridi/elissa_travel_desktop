package com.example.pidev.services;


import com.example.pidev.entities.ReservationVoyage;
import com.example.pidev.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reservationVoyageDAO {

    private Connection conn;

    public reservationVoyageDAO() { conn = MyDB.getInstance().getCnx();}

    public void addReservationVoyage(ReservationVoyage reservationVoyage) {
        String sql = "INSERT INTO reservation_voyage (nom, prenom, numberticket, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, reservationVoyage.getNom());
            statement.setString(2, reservationVoyage.getPrenom());
            statement.setInt(3, reservationVoyage.getNumberticket());
            statement.setString(4, reservationVoyage.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<ReservationVoyage> getAllReservationVoyage() {
        List<ReservationVoyage> reservations = new ArrayList<>();
        String sql = "SELECT nom, prenom, numberTicket, email FROM reservation_voyage";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ReservationVoyage reservation = new ReservationVoyage();
                reservation.setNom(resultSet.getString("nom"));
                reservation.setPrenom(resultSet.getString("prenom"));
                reservation.setNumberticket(resultSet.getInt("numberticket"));
                reservation.setEmail(resultSet.getString("email"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
















