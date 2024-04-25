package services;

import models.User;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUser {

    Connection cnx = DataSource.getInstance().getConnection();


    public User getNomById(int user_id) {
        User user = null;
        String sql = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, user_id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nom = result.getString("nom");
                    String prenom = result.getString("prenom");

                    user = new User(id, nom,prenom);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return user;
    }
}
