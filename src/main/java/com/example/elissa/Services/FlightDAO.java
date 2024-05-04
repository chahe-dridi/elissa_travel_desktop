package com.example.elissa.Services;

import com.example.elissa.Models.Flight;
import com.example.elissa.Models.Flightclass;
import com.example.elissa.Outil.My_db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO implements IFlightDAO {

    private Connection conn;

    public FlightDAO() {
        conn = My_db.getInstance().getConn();
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM vol";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flight flight = extractFlightFromResultSet(resultSet);
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public Flight getFlightById(int id) {
        String sql = "SELECT * FROM vol WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFlightFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addFlight(Flight flight) {
        String sql = "INSERT INTO vol (airport_depart_id, airport_arrive_id, volclass_id, user_id, compagnie_aerienne, heure_depart, heure_arrive, disponible) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            setFlightParameters(statement, flight);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFlight(Flight flight) {
        String sql = "UPDATE vol SET airport_depart_id=?, airport_arrive_id=?, volclass_id=?, user_id=?, compagnie_aerienne=?, heure_depart=?, heure_arrive=?, disponible=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            setFlightParameters(statement, flight);
            statement.setInt(9, flight.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFlight(int id) {
        String sql = "DELETE FROM vol WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Flight extractFlightFromResultSet(ResultSet resultSet) throws SQLException {
        return new Flight(
                resultSet.getInt("id"),
                resultSet.getInt("airport_depart_id"),
                resultSet.getInt("airport_arrive_id"),
                resultSet.getInt("volclass_id"),
                resultSet.getInt("user_id"),
                resultSet.getString("compagnie_aerienne"),
                resultSet.getObject("heure_depart", LocalDateTime.class),
                resultSet.getObject("heure_arrive", LocalDateTime.class),
                resultSet.getBoolean("disponible"),
                null // You need to replace `null` with appropriate flight class instance
        );
    }

    private void setFlightParameters(PreparedStatement statement, Flight flight) throws SQLException {
        statement.setInt(1, flight.getAirportDepartId());
        statement.setInt(2, flight.getAirportArriveId());
        statement.setInt(3, flight.getVolclassId());
        statement.setInt(4, flight.getUserId());
        statement.setString(5, flight.getCompagnieAerienne());
        statement.setObject(6, flight.getHeureDepart());
        statement.setObject(7, flight.getHeureArrive());
        statement.setBoolean(8, flight.isDisponible());
    }



    public Flight getFlightByVolId(int id) {
        String sql = "SELECT * FROM vol WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFlightFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }














}
