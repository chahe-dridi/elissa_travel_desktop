package com.example.pidev.controllers;

import com.example.pidev.entities.Voyage;
import com.example.pidev.services.VoyageDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditVoyageController {
    @FXML
    private TextField tVilleDepart;

    @FXML
    private TextField tannee;

    @FXML
    private TextField tdestination;

    @FXML
    private TextField tmois;

    @FXML
    private TableView<Voyage> table;
    VoyageDAO voyageDAO = new VoyageDAO();
    private Voyage voyage;

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
        tVilleDepart.setText(voyage.getVilledepart());
        tannee.setText(String.valueOf(voyage.getAnnee()));
        tdestination.setText(voyage.getDestination());
        tmois.setText(String.valueOf(voyage.getMois()));

    }

    @FXML
    private void update(ActionEvent event) throws IOException {
        if (tVilleDepart.getText().isEmpty() || tannee.getText().isEmpty() || tdestination.getText().isEmpty() || tmois.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
        } else {

            String newVille = tVilleDepart.getText();
            String newAnnee = tannee.getText();
            String newDestination = tdestination.getText();
            String newMois = tmois.getText();

            voyage.setAnnee(Integer.parseInt(newAnnee));
            voyage.setMois(newMois);
            voyage.setVilledepart(newVille);
            voyage.setDestination(newDestination);


            voyageDAO.updateVoyage(voyage);
            System.out.println("Voyage " + voyage.getDestination() + " updated in the database.");

            // Load the Profile.fxml view with the updated user credentials
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/index.fxml"));
            Parent root = loader.load();
            VoyageController voyageController = loader.getController();
            voyageController.setVoyage(voyage);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }

    }
}
