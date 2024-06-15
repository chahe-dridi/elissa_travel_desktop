package com.example.pidev.services;

import com.example.pidev.entities.Flightclass;
import com.example.pidev.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightclassDAO implements IFlightclassDAO {

    private final Connection conn;

    public FlightclassDAO() {
        conn = MyDB.getInstance().getCnx();
    }

    @Override
    public List<Flightclass> getAllFlightclasses() {
        List<Flightclass> flightclasses = new ArrayList<>();
        String sql = "SELECT * FROM volclass";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flightclass flightclass = mapResultSetToFlightclass(resultSet);
                flightclasses.add(flightclass);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return flightclasses;
    }

    @Override
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
            handleSQLException(e);
        }
    }

    @Override
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
            handleSQLException(e);
        }
    }

    @Override
    public Flightclass getFlightClassById(int id) {
        Flightclass flightclass = null;
        String sql = "SELECT * FROM volclass WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    flightclass = mapResultSetToFlightclass(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return flightclass;
    }


    @Override
    public void deleteFlightclass(int id) {
        String sql = "DELETE FROM volclass WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }



    private Flightclass mapResultSetToFlightclass(ResultSet resultSet) throws SQLException {
        return new Flightclass(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("class_name"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getInt("ticket_number")
        );
    }

    private void handleSQLException(SQLException e) {
        // You can log the exception or throw a custom application-specific exception
        e.printStackTrace(); // For simplicity, just printing stack trace
    }
}
