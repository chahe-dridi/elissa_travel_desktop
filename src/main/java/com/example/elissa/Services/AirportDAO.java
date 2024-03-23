package com.example.elissa.Services;

import com.example.elissa.Models.Airport;
import com.example.elissa.Outil.My_db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO {

    private Connection conn; // Remove static keyword

    public AirportDAO() {
        conn = My_db.getInstance().getConn();
    }

    public List<Airport> getAllAirports() {
        List<Airport> airports = new ArrayList<>();
        String sql = "SELECT * FROM airport";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Airport airport = new Airport(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("code"),
                        resultSet.getString("name"),
                        resultSet.getString("city"),
                        resultSet.getString("country")
                );
                airports.add(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airports;
    }

    public void addAirport(Airport airport) {
        String sql = "INSERT INTO airport (user_id, code, name, city, country) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, airport.getUserId());
            statement.setString(2, airport.getCode());
            statement.setString(3, airport.getName());
            statement.setString(4, airport.getCity());
            statement.setString(5, airport.getCountry());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateAirport(Airport airport) {
        String sql = "UPDATE airport SET user_id=?, code=?, name=?, city=?, country=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, airport.getUserId());
            statement.setString(2, airport.getCode());
            statement.setString(3, airport.getName());
            statement.setString(4, airport.getCity());
            statement.setString(5, airport.getCountry());
            statement.setInt(6, airport.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAirport(int id) {
        String sql = "DELETE FROM airport WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
