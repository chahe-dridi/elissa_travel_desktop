package com.example.pidev.controllers;

import com.example.pidev.entities.ReservationVoyage;
import com.example.pidev.services.reservationVoyageDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class afficheReservationController implements Initializable {

    @FXML
    private Button btnReturn;

    @FXML
    private TableColumn<ReservationVoyage, String> rnom;

    @FXML
    private TableColumn<ReservationVoyage, String> rprenom;

    @FXML
    private TableColumn<ReservationVoyage, Integer> rticket;

    @FXML
    private TableColumn<ReservationVoyage, String> remail;

    @FXML
    private TableView<ReservationVoyage> tableReservations;
    reservationVoyageDAO reservationVoyageDAO = new reservationVoyageDAO();


    @FXML
    void routReturn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/index.fxml"));
        Parent parent = loader.load();
        VoyageController controller = loader.getController();
        tableReservations.getScene().setRoot(parent);

    }
    public void getAll (){

        rnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        rprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        rticket.setCellValueFactory(new PropertyValueFactory<>("numberticket"));
        remail.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<ReservationVoyage> list = reservationVoyageDAO.getAllReservationVoyage();
        System.out.println(list);
        ObservableList<ReservationVoyage> observableList = FXCollections.observableArrayList(list);
        tableReservations.setItems(observableList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
    }
}
