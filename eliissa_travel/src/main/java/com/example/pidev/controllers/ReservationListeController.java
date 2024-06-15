package com.example.pidev.controllers;

import com.example.pidev.entities.Reservation;
import com.example.pidev.services.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReservationListeController implements Initializable {

    private ServiceReservation reservationService;
    private Connection connection;

    @FXML
    private TableView<Reservation> Reservations;

    @FXML
    private TableColumn<Reservation, String> nom;

    @FXML
    private TableColumn<Reservation, String> pre_nom;

    @FXML
    private TableColumn<Reservation, String> email;

    @FXML
    private TableColumn<Reservation, String> distination;


    @FXML
    private TableColumn<Reservation, String> chambre;

    @FXML
    private TableColumn<Reservation, String> date_arrive;

    @FXML
    private TableColumn<Reservation, String> date_depart;

    @FXML
    private TableColumn<Reservation, Void> action;


    @FXML
    private Label reser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elissa_travel", "root", "");
            reservationService = new ServiceReservation();
            initializeTable();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Impossible de se connecter à la base de données.");
        }
        initializeShowButton();
    }
    private void initializeShowButton() {
        // Define the action for the "Show" button
        action.setCellFactory(param -> new TableCell<>() {
            private final Button showButton = new Button("Show");

            {
                // Define action for the button
                showButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    showReservationDetails(reservation);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(showButton);
                }
            }
        });
    }

    private void showReservationDetails(Reservation reservation) {
        try {
            // Load the afficherReservation.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hotel/Afficher.fxml"));
            Parent root = loader.load();

            // Get the controller for the afficherReservation.fxml file
            AfficherController controller = loader.getController();

            // Pass the reservation to the controller
            controller.setReservation(reservation);

            // Call the method to display the details
            controller.afficherDetails();

            // Create a new stage and set its content to the loaded FXML
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reservation Details");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initializeTable() {
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        pre_nom.setCellValueFactory(new PropertyValueFactory<>("preNom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        distination.setCellValueFactory(new PropertyValueFactory<>("distination"));
        chambre.setCellValueFactory(new PropertyValueFactory<>("chambr_e"));
        date_arrive.setCellValueFactory(new PropertyValueFactory<>("DateArrive"));
        date_depart.setCellValueFactory(new PropertyValueFactory<>("DateDepart"));

        try {
            List<Reservation> reservationList = reservationService.getAllReservations();
            ObservableList<Reservation> observableList = FXCollections.observableArrayList(reservationList);
            Reservations.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'initialisation de la table.");
        }
    }


    @FXML
    void supprimerReservation(ActionEvent event) {
        Reservation reservation = Reservations.getSelectionModel().getSelectedItem();
        if (reservation == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une réservation à supprimer.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette réservation ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                reservationService.supprimerReservation(reservation.getId());
                refreshTable();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation supprimée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression de la réservation.");
            }
        }
    }

    public void refreshTable() {
        initializeTable();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
