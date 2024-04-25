package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.TypeEvenement;
import utils.DataSource;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceTypeEvent implements IServiceTypeEvenement {
    private Connection cnx = DataSource.getInstance().getConnection();
    private ObservableList<TypeEvenement> obList = FXCollections.observableArrayList();

    @Override
    public void ajouterTypeEvenement(TypeEvenement TypeEvent)
    {

        String req = "INSERT INTO `type_evenement`(`nom_type`, `type_description`) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, TypeEvent.getNom_type());
            ps.setString(2, TypeEvent.getType_description());
            ps.executeUpdate();
            System.out.println("Type Evenement ajouté avec succès!");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTypeEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public List<TypeEvenement> afficherTypeEvenement() {
        List<TypeEvenement> TypeEvent = new ArrayList<>();
        String req = "SELECT * FROM type_evenement ";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                TypeEvenement E = new TypeEvenement();
                E.setId(rs.getInt("id"));
                E.setNom_type(rs.getString("nom_type"));
                E.setType_description(rs.getString("type_description"));
                TypeEvent.add(E);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceTypeEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return TypeEvent;
    }

    @Override
    public void modifierTypeEvenement(TypeEvenement TypeEvent) {
        try {
            String req = "UPDATE `type_evenement` SET `nom_type`=?, `type_description`=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, TypeEvent.getNom_type());
                st.setString(2, TypeEvent.getType_description());
                st.setInt(3, TypeEvent.getId());
                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("TypeEvenement modifié avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour le TypeEvenement avec l'ID " + TypeEvent.getId());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void supprimerTypeEvenement(int id) {
        try {
            String req = "DELETE FROM `type_evenement` WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Type Evenement supprimé avec succès");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public TypeEvenement getTypeEventById(int id) {
        TypeEvenement typeEvenement = null;
        String sql = "SELECT * FROM type_evenement WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int idtype = result.getInt("id");
                    String nom_type = result.getString("nom_type");
                    String type_description = result.getString("type_description");

                    typeEvenement = new TypeEvenement(idtype, nom_type,type_description);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return typeEvenement;
    }



    public int gettypeEvenementByName(String nom_type) {
        int id = -1;
        String sql = "SELECT id FROM type_evenement WHERE nom_type = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, nom_type);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    id = result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }

    public ObservableList<TypeEvenement> afficherTypeEvenement2() {
        String sql = "SELECT * FROM type_evenement";
        try {
            Statement statement = cnx.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String nom_type = result.getString(2);
                String type_description=result.getString(3);
                TypeEvenement c = new TypeEvenement(id, nom_type,type_description);
                obList.add(c);
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return obList;
    }

}
