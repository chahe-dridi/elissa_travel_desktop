package com.example.pidev.services;

import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoyageDAO {

    private Connection conn;

    public VoyageDAO() {
        conn = MyDB.getInstance().getCnx();
    }

    public void addVoyage(Voyage voyage) {
        String sql = "INSERT INTO voyage (villedepart, destination, annee, mois) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, voyage.getVilledepart());
            statement.setString(2, voyage.getDestination());
            statement.setInt(3, voyage.getAnnee());
            statement.setString(4, voyage.getMois());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Voyage> getAllVoyages() {
        List<Voyage> voyages = new ArrayList<>();
        String sql = "SELECT * FROM voyage";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Voyage voyage = new Voyage();
                voyage.setId(resultSet.getInt("id"));
                voyage.setVilledepart(resultSet.getString("villedepart"));
                voyage.setDestination(resultSet.getString("destination"));
                voyage.setAnnee(resultSet.getInt("annee"));
                voyage.setMois(resultSet.getString("mois"));

                voyages.add(voyage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voyages;
    }

    public Voyage getVoyageByDestination(String destination) throws SQLException {
        Voyage voyage = null;
        String sql = "SELECT DISTINCT * FROM voyage WHERE destination = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, destination);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    voyage = new Voyage();
                    voyage.setId(resultSet.getInt("id"));
                    voyage.setVilledepart(resultSet.getString("villedepart"));
                    voyage.setMois(resultSet.getString("mois"));
                    voyage.setAnnee(resultSet.getInt("annee"));
                    voyage.setDestination(resultSet.getString("destination"));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return voyage;
        }
    }




    public void updateVoyage(Voyage voyage) {
        String sql = "UPDATE voyage SET villedepart=?, destination=?, annee=?, mois=? WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, voyage.getVilledepart());
            statement.setString(2, voyage.getDestination());
            statement.setInt(3, voyage.getAnnee());
            statement.setString(4, voyage.getMois());
            statement.setInt(5, voyage.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVoyage(int id) {
        String sql = "DELETE FROM voyage WHERE id=?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
