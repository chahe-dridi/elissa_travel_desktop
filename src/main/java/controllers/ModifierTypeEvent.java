package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.TypeEvenement;
import services.ServiceTypeEvent;

import java.io.IOException;

public class ModifierTypeEvent {

    private int typeEventId; // Nouvelle variable pour stocker l'ID
    @FXML
    private AnchorPane nh;
    @FXML
    private TextField fx_descriptionevent;

    @FXML
    private TextField fx_typeevent;

    @FXML
    void modifierTypeevent(ActionEvent event) {
        String nom_type= fx_typeevent.getText();
        String description_type = fx_descriptionevent.getText();




        if (nom_type.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer un nom de type evenement.");
            alert.show();
            return;
        }

        if (description_type.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Erreur : Veuillez entrer une description de type.");
            alert.show();
            return;
        }

        ServiceTypeEvent sc = new ServiceTypeEvent();



        sc.modifierTypeEvenement(new TypeEvenement(typeEventId,nom_type,description_type));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success Message");
        alert.setHeaderText(null);
        alert.setContentText("TypeEvenement modifiée avec succès !");
        alert.showAndWait();
        retournerAAfficherEvent();
    }
        // Nouvelle méthode pour initialiser l'ID
        public void initData(int typeEventId, String oldnom_type,String oldtypedescription) {
            this.typeEventId = typeEventId;
            fx_descriptionevent.setText(oldtypedescription);
            fx_typeevent.setText(oldnom_type);
        }

    private void retournerAAfficherEvent() {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherTypeEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
    @FXML
    void goToAffiche(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherTypeEvent.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
}

