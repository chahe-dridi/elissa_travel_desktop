package com.example.pidev.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.pidev.entities.TypeEvenement;
import com.example.pidev.entities.Event;
import com.example.pidev.entities.User;
import com.example.pidev. services.ServiceTypeEvent;


import com.example.pidev. services.ServiceEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;
public class ModifierEvent implements  Initializable{

    @FXML
    private ComboBox<String> TypeEvenement;



    @FXML
    private Label file_path;

    @FXML
    private TextField fx_Adresse;

    @FXML
    private AnchorPane nh;
    @FXML
    private TextField fx_NbrTicket;

    @FXML
    private DatePicker fx_datedebut;

    @FXML
    private DatePicker fx_datefin;

    @FXML
    private TextField fx_nomEvent;

    @FXML
    private TextField fx_prixEntre;
    private int id; // Nouvelle variable pour stocker l'ID

    private int user_id = 1; // Définir statiquement user_id

    @FXML
    private ImageView imagev;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        ServiceTypeEvent sc = new ServiceTypeEvent();
        ObservableList<TypeEvenement> obList = FXCollections.observableArrayList();
        obList = sc.afficherTypeEvenement2();

        TypeEvenement.getItems().clear();

        for (TypeEvenement nameCat : obList) {
            list.add(nameCat.getNom_type());
        }
        TypeEvenement.setItems(list);
    }
    public void initData(int id, String nom_event, String adresse_event, int nbrticketsdispo, Date datedebut_event, Date datefinevent, int prixentre, String imageevent, int type_evenement_id, int user_id) {
        this.id = id;
        fx_nomEvent.setText(nom_event);
        fx_Adresse.setText(adresse_event);
        fx_NbrTicket.setText(String.valueOf(nbrticketsdispo));

        // Convertir java.sql.Date en java.util.Date
        Date datedebut = new Date(datedebut_event.getTime());
        Date datefin = new Date(datefinevent.getTime());

        fx_datedebut.setValue(datedebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        fx_datefin.setValue(datefin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        fx_prixEntre.setText(String.valueOf(prixentre));
        file_path.setText(imageevent);
        TypeEvenement.setValue(getTypeEventById(type_evenement_id));
    }


    private String getTypeEventById(int categoryId) {
        ServiceTypeEvent sc = new ServiceTypeEvent();
        TypeEvenement category = sc.getTypeEventById(categoryId);
        return (category != null) ? category.getNom_type() : null;
    }

    @FXML
    void uploadimageHandler(MouseEvent event) {
        FileChooser open = new FileChooser();
        Stage stage = (Stage) nh.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            String fileName = UUID.randomUUID().toString() + ".png"; // Nom de fichier unique
            String targetDirectory = "C:\\Users\\DARK\\Downloads"; // Répertoire cible

            try {
                // Copier le fichier vers le dossier de destination avec un nom de fichier unique
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get(targetDirectory, fileName);
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                // Mettre à jour le chemin du fichier affiché
                String newPath = targetDirectory + "/" + fileName; // Utilisation de /
                file_path.setText(newPath);
                // Charger et afficher l'image depuis le nouveau chemin
                Image image = new Image("file:" + newPath, 500, 500, false, true);
                imagev.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la copie du fichier.");
            }
        } else {
            System.out.println("NO DATA EXIST!");
        }
    }

    @FXML
    void modifier(ActionEvent event) {

        String adresse_event = fx_Adresse.getText();
        String nom_event = fx_nomEvent.getText();
        int prixentre=0;
        int nbrticketsdispo=0;
        LocalDate datedebut_event = fx_datedebut.getValue();
        LocalDate datefin_event = fx_datefin.getValue();

        if (nom_event.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nom Event.");
            alert.show();
            return;
        }

        if (adresse_event.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer une adresse Event.");
            alert.show();
            return;
        }
        try {
            prixentre= Integer.parseInt(fx_prixEntre.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nombre valide pour le prixentre.");
            alert.show();
            return;
        }
        if (prixentre < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Le prixentre doit être positif.");
            alert.show();
            return;
        }
        try {
            nbrticketsdispo= Integer.parseInt(fx_NbrTicket.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nombre valide pour nbrticketsdispo.");
            alert.show();
            return;
        }
        if (nbrticketsdispo < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Le nbrticketsdispo doit être positif.");
            alert.show();
            return;
        }
        if (datedebut_event == null || datefin_event == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez sélectionner une date de début et une date de fin.");
            alert.show();
            return;
        }

        if (datefin_event.isBefore(datedebut_event)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : La date de fin doit être postérieure à la date de début.");
            alert.show();
            return;
        }
        ServiceTypeEvent sc = new ServiceTypeEvent();
        int type_evenement_id = sc.gettypeEvenementByName(TypeEvenement.getValue());
        if (type_evenement_id == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : type evenement non trouvée.");
            alert.show();
            return;
        }
        ServiceEvent ss = new ServiceEvent();
        ss.modifierEvent(new Event(id,nom_event, adresse_event, nbrticketsdispo, java.sql.Date.valueOf(datedebut_event), java.sql.Date.valueOf(datefin_event), prixentre, file_path.getText(), type_evenement_id, user_id));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Evenement modifiée avec succès !");
        alert.showAndWait();
        // Après avoir effectué la modification avec succès
        retournerAAfficherEvent();

    }
  /*  private void retournerAAfficherEvent() {
        try {
            FXMLLoader loader = createFXMLLoader("/Event/AfficherEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }*/
 private void retournerAAfficherEvent() {
      try {
          FXMLLoader loader = createFXMLLoader("/Airport/base.fxml"); // Load Airport/base.fxml
          Parent root = loader.load();
          AnchorPane newPane = new AnchorPane(root); // Create a new AnchorPane and set its content to the loaded FXML
          nh.getScene().setRoot(newPane); // Set the scene's root to the new AnchorPane
      } catch (IOException ex) {
          System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
      }
  }




  /*  @FXML
    void retournerAAfficherEvent(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Event/AfficherEvent.fxml");
            Parent root = loader.load();
            AnchorPane newPane = new AnchorPane(root); // Create a new AnchorPane and set its content to the loaded FXML
            nh.getScene().setRoot(newPane); // Set the scene's root to the new AnchorPane
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }*/

    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
    @FXML
    void goToAffiche(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/Event/AfficherEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
}
