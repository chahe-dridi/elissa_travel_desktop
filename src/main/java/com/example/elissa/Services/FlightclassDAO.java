package com.example.elissa.Services;

import com.example.elissa.Models.Flightclass;
import com.example.elissa.Outil.My_db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightclassDAO {

    private Connection conn;

    public FlightclassDAO() {
        conn = My_db.getInstance().getConn();
    }

    public List<Flightclass> getAllFlightclasses() {
        List<Flightclass> flightclasses = new ArrayList<>();
        String sql = "SELECT * FROM volclass";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flightclass flightclass = new Flightclass(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("class_name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("ticket_number")
                );
                flightclasses.add(flightclass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightclasses;
    }

    public void addFlightclass(Flightclass flightclass) {
        String sql = "INSERT INTO volclass (user_id, class_name, description, price, ticket_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, flightclass.getUserId());
            statement.setString(2, flightclass.getClassName());
            statement.setString(3, flightclass.getDescription());
            statement.setDouble(4, flightclass.getPrice());
            statement.setInt(5, flightclass.getTicketNumber());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFlightclass(Flightclass flightclass) {
        String sql = "UPDATE volclass SET user_id=?, class_name=?, description=?, price=?, ticket_number=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, flightclass.getUserId());
            statement.setString(2, flightclass.getClassName());
            statement.setString(3, flightclass.getDescription());
            statement.setDouble(4, flightclass.getPrice());
            statement.setInt(5, flightclass.getTicketNumber());
            statement.setInt(6, flightclass.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlightclass(int id) {
        String sql = "DELETE FROM volclass WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
