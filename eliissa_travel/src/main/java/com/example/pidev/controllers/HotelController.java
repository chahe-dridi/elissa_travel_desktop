package com.example.pidev.controllers;


import com.example.pidev.entities.Hotel;
import com.example.pidev.services.ServiceHotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;


    public class HotelController {
    
        private ServiceHotel serviceHotel = new ServiceHotel();
    
        @FXML
        private TextField nom_hotel;
        @FXML
        private TextField lieu;
        @FXML
        private TextField telephone;
        @FXML
        private TextField email;
        @FXML
        private TextField description;
        @FXML
        private TextField etat;
        @FXML
        private TextField nb_chambre;
        @FXML
        private TextField image;
        @FXML
        private TextField chercherhotels;
    
        @FXML
        private TableView<Hotel> tableView;
        @FXML
        private TableColumn<Hotel, String> nomColumn;
        @FXML
        private TableColumn<Hotel, String> lieuColumn;
        @FXML
        private TableColumn<Hotel, String> telephoneColumn;
        @FXML
        private TableColumn<Hotel, String> emailColumn;
        @FXML
        private TableColumn<Hotel, String> descriptionColumn;
        @FXML
        private TableColumn<Hotel, String> etatColumn;
        @FXML
        private TableColumn<Hotel, Integer> nb_chambreColumn;
        @FXML
        private TableColumn<Hotel, String> imageColumn;


        public void initialize() {
            // Initialize TableView columns
            nomColumn.setCellValueFactory(data -> data.getValue().nom_hotelProperty());
            lieuColumn.setCellValueFactory(data -> data.getValue().lieuProperty());
            telephoneColumn.setCellValueFactory(data -> data.getValue().tel_hotelProperty());
            emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
            descriptionColumn.setCellValueFactory(data -> data.getValue().disc_hotelProperty());
            etatColumn.setCellValueFactory(data -> data.getValue().etat_hotelProperty());
            nb_chambreColumn.setCellValueFactory(data -> data.getValue().nb_chambreProperty().asObject());
            imageColumn.setCellValueFactory(data -> data.getValue().imageProperty());
            TableColumn<Hotel, String> nomColumn = new TableColumn<>("Nom Hotel");
            nomColumn.setCellValueFactory(cellData -> cellData.getValue().nom_hotelProperty());

            TableColumn<Hotel, String> lieuColumn = new TableColumn<>("Lieu");
            lieuColumn.setCellValueFactory(cellData -> cellData.getValue().lieuProperty());

            // Add other columns similarly

            // Add columns to the TableView
            tableView.getColumns().addAll(nomColumn, lieuColumn);
            // Load data into TableView
            tableView.setItems(serviceHotel.getAllHotels());
        }
    
        @FXML
        public void ajouter() {
            if (validateInput()) {
                try {
                    Hotel hotel = new Hotel(
                            Integer.parseInt(nb_chambre.getText()),
                            nom_hotel.getText(),
                            lieu.getText(),
                            telephone.getText(),
                            email.getText(),
                            description.getText(),
                            etat.getText(),
                            image.getText()
                    );
    
                    serviceHotel.ajouterHotel(hotel);
    
                    // Refresh TableView to show the newly added hotel
                    tableView.getItems().add(hotel);
    
                    // Clear input fields
                    clearFields();
                } catch (NumberFormatException e) {
                    showAlert(AlertType.ERROR, "Erreur", "Saisie invalide", "Le nombre de chambres doit être un entier.");
                }
            }
        }
        private boolean validateInput() {
            String nomHotel = nom_hotel.getText();
            String lieuHotel = lieu.getText();
            String telephoneHotel = telephone.getText();
            String emailHotel = email.getText();
            String nbChambre = nb_chambre.getText();

            // Validation des champs obligatoires
            if (nomHotel.isEmpty() || lieuHotel.isEmpty() || telephoneHotel.isEmpty() || emailHotel.isEmpty() || nbChambre.isEmpty()) {
                showAlert(AlertType.ERROR, "Erreur", "Champs manquants", "Veuillez remplir tous les champs obligatoires.");
                return false;
            }

            // Validation du format de l'email
            String emailRegex = "^(.+)@(.+)$";
            if (!emailHotel.matches(emailRegex)) {
                showAlert(AlertType.ERROR, "Erreur", "Format d'email invalide", "Veuillez saisir une adresse e-mail valide.");
                return false;
            }

            // Validation du format du numéro de téléphone
            String telephoneRegex = "^\\d{8}$";
            if (!telephoneHotel.matches(telephoneRegex)) {
                showAlert(AlertType.ERROR, "Erreur", "Format de numéro de téléphone invalide", "Veuillez saisir un numéro de téléphone valide (10 chiffres).");
                return false;
            }

            // Validation du nombre de chambres (doit être un entier positif)
            try {
                int nbChambreInt = Integer.parseInt(nbChambre);
                if (nbChambreInt <= 0) {
                    showAlert(AlertType.ERROR, "Erreur", "Nombre de chambres invalide", "Le nombre de chambres doit être un entier positif.");
                    return false;
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Erreur", "Saisie invalide", "Veuillez saisir un nombre entier pour le nombre de chambres.");
                return false;
            }

            // Si toutes les validations sont passées avec succès, retourne true
            return true;
        }

        private void clearFields() {
            nom_hotel.clear();
            lieu.clear();
            telephone.clear();
            email.clear();
            description.clear();
            etat.clear();
            nb_chambre.clear();
            image.clear();
        }

        @FXML
        public void rechercherhotel() {
            String nomHotel = chercherhotels.getText();
            List<Hotel> hotels = serviceHotel.rechercherHotels(nomHotel);
            ObservableList<Hotel> observableList = FXCollections.observableArrayList(hotels);
            tableView.setItems(observableList);
        }


        @FXML
        public void supprimer() {
            // Get the selected hotel from the TableView
            Hotel selectedHotel = tableView.getSelectionModel().getSelectedItem();
            if (selectedHotel != null) {
                // Remove the selected hotel from the TableView
                tableView.getItems().remove(selectedHotel);
                // Delete the selected hotel from the database
                serviceHotel.supprimerHotel(selectedHotel.getId());
            }
        }
    
        @FXML
        public void modifier() {
            // Get the selected hotel from the TableView
            Hotel selectedHotel = tableView.getSelectionModel().getSelectedItem();
            if (selectedHotel != null) {
                // Modify the selected hotel in the database
                selectedHotel.setNom_hotel(nom_hotel.getText());
                selectedHotel.setLieu(lieu.getText());
                selectedHotel.setTel_hotel(telephone.getText());
                selectedHotel.setEmail(email.getText());
                selectedHotel.setDisc_hotel(description.getText());
                selectedHotel.setEtat_hotel(etat.getText());
                selectedHotel.setNb_chambre(Integer.parseInt(nb_chambre.getText()));
                selectedHotel.setImage(image.getText());
    
                // Update the TableView with the modified hotel
                tableView.refresh();
                // Update the hotel in the database
                serviceHotel.modifierHotel(selectedHotel);
            }
        }

        @FXML
        void selectImage() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                image.setText(selectedFile.getAbsolutePath());
            }
        }
    
        @FXML
        public void switchToChambre() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Hotel/Chambre.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) nom_hotel.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        private void showAlert(AlertType type, String title, String header, String content) {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
