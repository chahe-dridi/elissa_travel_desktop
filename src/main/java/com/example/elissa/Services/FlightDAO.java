package com.example.elissa.Services;

import com.example.elissa.Models.Flight;
import com.example.elissa.Outil.My_db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    private Connection conn;

    public FlightDAO() {
        conn = My_db.getInstance().getConn();
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM vol";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flight flight = new Flight(
                        resultSet.getInt("id"),
                        resultSet.getInt("airport_depart_id"),
                        resultSet.getInt("airport_arrive_id"),
                        resultSet.getInt("volclass_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("compagnie_aerienne"),
                        resultSet.getObject("heure_depart", LocalDateTime.class),
                        resultSet.getObject("heure_arrive", LocalDateTime.class),
                        resultSet.getBoolean("disponible")
                );
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public void addFlight(Flight flight) {
        String sql = "INSERT INTO vol (airport_depart_id, airport_arrive_id, volclass_id, user_id, compagnie_aerienne, heure_depart, heure_arrive, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, flight.getAirportDepartId());
            statement.setInt(2, flight.getAirportArriveId());
            statement.setInt(3, flight.getVolclassId());
            statement.setInt(4, flight.getUserId());
            statement.setString(5, flight.getCompagnieAerienne());
            statement.setObject(6, flight.getHeureDepart());
            statement.setObject(7, flight.getHeureArrive());
            statement.setBoolean(8, flight.isDisponible());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateFlight(Flight flight) {
        String sql = "UPDATE vol SET airport_depart_id=?, airport_arrive_id=?, volclass_id=?, user_id=?, compagnie_aerienne=?, heure_depart=?, heure_arrive=?, disponible=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, flight.getAirportDepartId());
            statement.setInt(2, flight.getAirportArriveId());
            statement.setInt(3, flight.getVolclassId());
            statement.setInt(4, flight.getUserId());
            statement.setString(5, flight.getCompagnieAerienne());
            statement.setObject(6, flight.getHeureDepart());
            statement.setObject(7, flight.getHeureArrive());
            statement.setBoolean(8, flight.isDisponible());
            statement.setInt(9, flight.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlight(int id) {
        String sql = "DELETE FROM vol WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }










    public Flight getFlightByVolId(int volId) {
        String sql = "SELECT id, compagnie_aerienne, heure_depart, heure_arrive " +
                "FROM vol " +
                "WHERE id = ?";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, volId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve flight details from the result set
                int flightId = resultSet.getInt("id");
                String compagnieAerienne = resultSet.getString("compagnie_aerienne");
                Timestamp heureDepart = resultSet.getTimestamp("heure_depart");
                Timestamp heureArrive = resultSet.getTimestamp("heure_arrive");

                // Create a Flight object and set its details
                Flight flight = new Flight();
                flight.setId(flightId);
                flight.setCompagnieAerienne(compagnieAerienne);
                flight.setHeureDepart(heureDepart.toLocalDateTime());
                flight.setHeureArrive(heureArrive.toLocalDateTime());

                return flight;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no flight is found
    }
}
