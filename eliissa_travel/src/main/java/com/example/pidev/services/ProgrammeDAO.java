package com.example.pidev.services;

import com.example.pidev.entities.Programme;
import com.example.pidev.entities.Voyage;
import com.example.pidev.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeDAO {
    private static Connection conn;

    public ProgrammeDAO() {
        conn = MyDB.getInstance().getCnx();
    }

    public static void addProgramme(Programme programe) {
        String sql = "INSERT INTO programme (description, prix, dateDebut, dateFin ,voyage_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, programe.getDescription());
            statement.setInt(2, programe.getPrix());
            statement.setString(3, programe.getDateDebut());
            statement.setString(4, programe.getDateFin());
            statement.setInt(5,programe.getVoyageId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Programme> getAllProgram() {
        List<Programme> programs = new ArrayList<>();
        String sql = "SELECT * FROM programme";
        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Programme  programme = new Programme();
                programme.setId(resultSet.getInt("id"));
                programme.setDescription(resultSet.getString("description"));
                programme.setDateDebut(resultSet.getString("datedebut"));
                programme.setDateFin(resultSet.getString("datefin"));
                programme.setPrix(resultSet.getInt("prix"));

                programs.add(programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }










}
