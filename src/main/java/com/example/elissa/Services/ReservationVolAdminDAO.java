package com.example.elissa.Services;

import com.example.elissa.Models.Airport;
import com.example.elissa.Models.Flight;
import com.example.elissa.Models.Flightclass;
import com.example.elissa.Models.ReservationVolAdmin;
import com.example.elissa.Outil.My_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationVolAdminDAO {

    private Connection conn;

    public ReservationVolAdminDAO() {
        conn = My_db.getInstance().getConn();
    }

    public List<ReservationVolAdmin> getAllReservations() {
        List<ReservationVolAdmin> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservationvol";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ReservationVolAdmin reservation = new ReservationVolAdmin();
                reservation.setId(resultSet.getInt("id"));
                reservation.setVolId(resultSet.getInt("vol_id"));
                reservation.setUserId(resultSet.getInt("user_id"));
                reservation.setTotalPrice(resultSet.getDouble("total_price"));
                reservation.setPaymentMethod(resultSet.getString("payment_method"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public void addReservation(ReservationVolAdmin reservation) {
        String sql = "INSERT INTO reservationvol (vol_id, user_id, total_price, payment_method) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, reservation.getVolId());
            statement.setInt(2, reservation.getUserId());
            statement.setDouble(3, reservation.getTotalPrice());
            statement.setString(4, reservation.getPaymentMethod());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(ReservationVolAdmin reservation) {
        String sql = "UPDATE reservationvol SET vol_id=?, user_id=?, total_price=?, payment_method=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, reservation.getVolId());
            statement.setInt(2, reservation.getUserId());
            statement.setDouble(3, reservation.getTotalPrice());
            statement.setString(4, reservation.getPaymentMethod());
            statement.setInt(5, reservation.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReservation(int id) {
        String sql = "DELETE FROM reservationvol WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<ReservationVolAdmin> searchReservationByQuery(String query) {
        List<ReservationVolAdmin> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservationvol WHERE id LIKE ?"; // Modify the query according to your database schema
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Use "%" to allow partial matching in the database query
            statement.setString(1, "%" + query + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ReservationVolAdmin reservation = new ReservationVolAdmin();
                    reservation.setId(resultSet.getInt("id"));
                    reservation.setVolId(resultSet.getInt("vol_id"));
                    reservation.setUserId(resultSet.getInt("user_id"));
                    reservation.setTotalPrice(resultSet.getDouble("total_price"));
                    reservation.setPaymentMethod(resultSet.getString("payment_method"));
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }






}
