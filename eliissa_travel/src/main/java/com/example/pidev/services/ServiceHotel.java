package com.example.pidev.services;

import com.example.pidev.entities.Hotel;
import com.example.pidev.utils.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceHotel {

    private Connection connection;

    public ServiceHotel() {
        // Initialize connection
        // Implement getConnection() method in MyDB class to obtain the database connection
        this.connection = MyDB.getInstance().getCnx();
    }

    public void ajouterHotel(Hotel hotel) {
        try {
            String query = "INSERT INTO hotel (nb_chambre, nom_hotel, lieu, tel_hotel, email, disc_hotel, etat_hotel, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, hotel.getNb_chambre());
            preparedStatement.setString(2, hotel.getNom_hotel());
            preparedStatement.setString(3, hotel.getLieu());
            preparedStatement.setString(4, hotel.getTel_hotel());
            preparedStatement.setString(5, hotel.getEmail());
            preparedStatement.setString(6, hotel.getDisc_hotel());
            preparedStatement.setString(7, hotel.getEtat_hotel());
            preparedStatement.setString(8, hotel.getImage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Hotel> getAllHotels() {
        ObservableList<Hotel> hotels = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM hotel";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Hotel hotel = new Hotel(

                        resultSet.getInt("nb_chambre"),
                        resultSet.getString("nom_hotel"),
                        resultSet.getString("lieu"),
                        resultSet.getString("tel_hotel"),
                        resultSet.getString("email"),
                        resultSet.getString("disc_hotel"),
                        resultSet.getString("etat_hotel"),
                        resultSet.getString("image")
                );
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }


    public void modifierHotel(Hotel hotel) {
        try {
            String query = "UPDATE hotel SET nb_chambre = ?, nom_hotel = ?, lieu = ?, tel_hotel = ?, email = ?, disc_hotel = ?, etat_hotel = ?, image = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, hotel.getNb_chambre());
            preparedStatement.setString(2, hotel.getNom_hotel());
            preparedStatement.setString(3, hotel.getLieu());
            preparedStatement.setString(4, hotel.getTel_hotel());
            preparedStatement.setString(5, hotel.getEmail());
            preparedStatement.setString(6, hotel.getDisc_hotel());
            preparedStatement.setString(7, hotel.getEtat_hotel());
            preparedStatement.setString(8, hotel.getImage());
            preparedStatement.setInt(9, hotel.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerHotel(int hotelId) {
        try {
            String query = "DELETE FROM hotel WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hotel> rechercherHotels(String nomOuLieu) {
        String query = "SELECT * FROM hotel WHERE nom_hotel LIKE ? OR lieu LIKE ?";
        List<Hotel> hotels = new ArrayList<>();

        try (Connection connection = MyDB.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + nomOuLieu + "%");
            preparedStatement.setString(2, "%" + nomOuLieu + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Hotel hotel = new Hotel(
                        resultSet.getInt("nb_chambre"),
                        resultSet.getString("nom_hotel"),
                        resultSet.getString("lieu"),
                        resultSet.getString("tel_hotel"),
                        resultSet.getString("email"),
                        resultSet.getString("disc_hotel"),
                        resultSet.getString("etat_hotel"),
                        resultSet.getString("image")
                );
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception SQL
        }

        return hotels;
    }

}
