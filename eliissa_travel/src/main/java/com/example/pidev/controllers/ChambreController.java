package com.example.pidev.controllers;

import com.example.pidev.entities.Chambre;
import com.example.pidev.services.ServiceChambre;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChambreController implements Initializable {

    @FXML
    private ChoiceBox<String> TypeChambre;

    @FXML
    private ComboBox<String> logement;

    @FXML
    private ComboBox<String> nomHotel; // Changed to ComboBox<String>

    @FXML
    private TextField prixHotel;

    @FXML
    private ChoiceBox<String> vueHotel;

    private final ServiceChambre serviceChambre = new ServiceChambre();

    @FXML
    private TableView<Chambre> tableViewId;

    @FXML
    private TableColumn<Chambre, String> vueH;

    @FXML
    private TableColumn<Chambre, Double> prixH;

    @FXML
    private TableColumn<Chambre, String> nomH;

    @FXML
    private TableColumn<Chambre, Integer> idC;

    @FXML
    private TableColumn<Chambre, String> TypeC;

    @FXML
    private TableColumn<Chambre, String> prixH1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idC.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        TypeC.setCellValueFactory(data -> data.getValue().typeChambreProperty());
        vueH.setCellValueFactory(data -> data.getValue().vueHotelProperty());
        prixH1.setCellValueFactory(data -> data.getValue().typeLogHotelProperty());
        prixH.setCellValueFactory(data -> data.getValue().prixHotelProperty().asObject());
        nomH.setCellValueFactory(data -> data.getValue().vueHotelProperty());

        ObservableList<String> hotelNames = serviceChambre.getAllHotelNames();
        nomHotel.setItems(hotelNames);
    }

    @FXML
    public void retourToHotel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hotel/Hotel.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) nomHotel.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void confirmerAjoutChambre(ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                String hotelName = nomHotel.getValue();
                String typeChambre = TypeChambre.getValue();
                String vue = vueHotel.getValue();
                String typeLog = logement.getValue();
                double prix = Double.parseDouble(prixHotel.getText());
                int hotelId = serviceChambre.getHotelIdByName(hotelName);
                Chambre chambre = new Chambre(0, hotelId, typeChambre, vue, typeLog, prix);
                serviceChambre.ajouterChambre(chambre);
                tableViewId.setItems(serviceChambre.getAllChambres());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Saisie invalide", "Le prix doit être un nombre valide.");
            }
        }
    }

    private boolean validateInput() {
        String hotelName = nomHotel.getValue();
        String typeChambre = TypeChambre.getValue();
        String vue = vueHotel.getValue();
        String typeLog = logement.getValue();
        String prixText = prixHotel.getText();

        if (hotelName == null || typeChambre == null || vue == null || typeLog == null || prixText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Champs manquants", "Veuillez remplir tous les champs.");
            return false;
        }

        try {
            double prix = Double.parseDouble(prixText);
            if (prix <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Prix invalide", "Le prix doit être supérieur à zéro.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Saisie invalide", "Le prix doit être un nombre valide.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void modifierChambre(ActionEvent actionEvent) {
        // Get the selected chambre from the TableView
        Chambre selectedChambre = tableViewId.getSelectionModel().getSelectedItem();

        // Check if a chambre is selected
        if (selectedChambre != null) {
            // Enable cell editing for each column
            tableViewId.setEditable(true);

            // Set cell factories for each column to enable editing
            TypeC.setCellFactory(TextFieldTableCell.forTableColumn());
            vueH.setCellFactory(TextFieldTableCell.forTableColumn());
            prixH1.setCellFactory(TextFieldTableCell.forTableColumn());
            prixH.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            nomH.setCellFactory(TextFieldTableCell.forTableColumn());

            // Focus and start editing the first column of the selected row
            tableViewId.getSelectionModel().select(selectedChambre);
            tableViewId.getFocusModel().focus(tableViewId.getSelectionModel().getSelectedIndex(), TypeC);
            tableViewId.edit(tableViewId.getSelectionModel().getSelectedIndex(), TypeC);
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune chambre sélectionnée", "Veuillez sélectionner une chambre à modifier.");
        }
    }


    public void supprimerChambre(ActionEvent actionEvent) {
        Chambre selectedChambre = tableViewId.getSelectionModel().getSelectedItem();
        if (selectedChambre != null) {
            serviceChambre.supprimerChambre(selectedChambre.getId());
            tableViewId.getItems().remove(selectedChambre);
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucune chambre sélectionnée", "Veuillez sélectionner une chambre à supprimer.");
        }
    }
}
