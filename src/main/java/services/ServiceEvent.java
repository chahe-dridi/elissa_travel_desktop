package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Event;
import models.User;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceEvent implements  IServiceEvent{
    private Connection cnx = DataSource.getInstance().getConnection();
    private ObservableList<Event> obList = FXCollections.observableArrayList();
    @Override
    public void ajouterEvent(Event event) {


        String req = "INSERT INTO `event`(`nom_event`, `adresse_event`, `nbrticketsdispo`, `datedebut_event`,`datefinevent`,`prixentre`,`imageevent`,`type_evenement_id`,`user_id`) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getNom_event());
            ps.setString(2, event.getAdresse_event());
            ps.setInt(3, event.getNbrticketsdispo());
            ps.setDate(4, event.getDatedebut_event());
            ps.setDate(5, event.getDatefinevent());
            ps.setInt(6, event.getPrixentre());
            ps.setString(7, event.getImageevent());
            ps.setInt(8, event.getType_evenement_id());
            ps.setInt(9, event.getUser_id());
            ps.executeUpdate();
            System.out.println("Event ajouté avec succès!");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Event> afficherEvent() {
        List<Event> cours = new ArrayList<>();
        String req = "SELECT * FROM event ";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Event E = new Event();
                E.setId(rs.getInt("id"));
                E.setNom_event(rs.getString("nom_event"));
                E.setAdresse_event(rs.getString("adresse_event"));
                E.setNbrticketsdispo(rs.getInt("nbrticketsdispo"));
                E.setDatedebut_event(rs.getDate("datedebut_event"));
                E.setDatefinevent(rs.getDate("datefinevent"));
                E.setPrixentre(rs.getInt("prixentre"));
                E.setImageevent(rs.getString("imageevent"));
                E.setType_evenement_id(rs.getInt("type_evenement_id"));
                E.setUser_id(rs.getInt("user_id"));
                cours.add(E);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cours;
    }


    @Override
    public void modifierEvent(Event event) {
        try {
            String req = "UPDATE `event` SET `nom_event`=?, `adresse_event`=?, `nbrticketsdispo`=?, `datedebut_event`=?, `datefinevent`=?, `prixentre`=?, `imageevent`=?, `type_evenement_id`=?, `user_id`=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, event.getNom_event());
                st.setString(2, event.getAdresse_event());
                st.setInt(3, event.getNbrticketsdispo());
                st.setDate(4, event.getDatedebut_event());
                st.setDate(5, event.getDatefinevent());
                st.setInt(6, event.getPrixentre());
                st.setString(7, event.getImageevent());
                st.setInt(8, event.getType_evenement_id());
                st.setInt(9, event.getUser_id());
                st.setInt(10, event.getId());


                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Event modifié avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour l' Event avec l'ID " + event.getId());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void supprimerEvent(int id) {
        try {
            String req = "DELETE FROM `event` WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Event supprimé avec succès");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public Event getNomEventById(int event_id) {
        Event event = null;
        String sql = "SELECT * FROM event WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, event_id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nom_event = result.getString("nom_event");
                   // String adresse_event = result.getString("adresse_event");
                   // int nbrticketdispo=result.getInt("nbrticketdispo");
                    event = new Event(id, nom_event);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return event;
    }
    }
