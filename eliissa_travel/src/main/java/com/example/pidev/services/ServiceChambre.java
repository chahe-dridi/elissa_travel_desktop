package com.example.pidev.services;


import com.example.pidev.entities.Chambre;
import com.example.pidev.utils.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceChambre {

    private Connection connection;

    public ServiceChambre() {
        // Initialize connection
        // Implement getConnection() method in MyDB class to obtain the database connection
        this.connection = MyDB.getInstance().getCnx();
    }

    public void ajouterChambre(Chambre chambre) {
        try {
            String query = "INSERT INTO chambre (hotel_id, type_chambre, vue_hotel, type_log_hotel, prix_hotel) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, chambre.getHotelId());
            preparedStatement.setString(2, chambre.getTypeChambre());
            preparedStatement.setString(3, chambre.getVueHotel());
            preparedStatement.setString(4, chambre.getTypeLogHotel());
            preparedStatement.setDouble(5, chambre.getPrixHotel());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void supprimerChambre(int chambreId) {
        try {
            String query = "DELETE FROM chambre WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chambreId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierChambre(Chambre chambre) {
        try {
            String query = "UPDATE chambre SET type_chambre = ?, vue_hotel = ?, type_log_hotel = ?, prix_hotel = ? WHERE id = ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setString(1, chambre.getTypeChambre());
            preparedStatement.setString(2, chambre.getVueHotel());
            preparedStatement.setString(3, chambre.getTypeLogHotel());
            preparedStatement.setDouble(4, chambre.getPrixHotel());
            preparedStatement.setInt(5, chambre.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all hotels from the database
    public ObservableList<Chambre> getAllChambres() {
        ObservableList<Chambre> chambres = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM chambre";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Chambre chambre = new Chambre(
                        resultSet.getInt("id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("type_chambre"),
                        resultSet.getString("vue_hotel"),
                        resultSet.getString("type_log_hotel"),
                        resultSet.getDouble("prix_hotel")
                );
                chambres.add(chambre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }


    public ObservableList<String> getAllHotelNames() {
        ObservableList<String> hotelNames = FXCollections.observableArrayList();
        try {
            String query = "SELECT nom_hotel FROM hotel"; // Assurez-vous que la table s'appelle 'hotel'
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String hotelName = resultSet.getString("nom_hotel");
                hotelNames.add(hotelName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelNames;
    }

    public int getHotelIdByName(String hotelName) {
        int hotelId = 0;
        try {
            String query = "SELECT id FROM hotel WHERE nom_hotel = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hotelName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hotelId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelId;
    }


}
