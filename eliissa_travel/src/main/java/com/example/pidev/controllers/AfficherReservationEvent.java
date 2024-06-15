package com.example.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import com.example.pidev.entities.Event;
import com.example.pidev.entities.ReservationEvent;
import com.example.pidev.entities.User;
import com.example.pidev.services.ServiceEvent;
import com.example.pidev.services.ServiceReservationEvent;
import com.example.pidev.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherReservationEvent implements Initializable {
    ServiceReservationEvent scom = new ServiceReservationEvent();
    UserService userService = new UserService();
    ServiceEvent serviceEvent = new ServiceEvent();
    ReservationEvent reservationEvent;

    @FXML
    private AnchorPane nh;

    @FXML
    private Text trole;

    @FXML
    private VBox vbox1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<ReservationEvent> reservationEventList = scom.afficherResrvationEvent();

        vbox1.setFillWidth(true);

        for (ReservationEvent reservationEvent1 : reservationEventList) {
            Pane pane = new Pane();
            pane.setPrefHeight(62.0);
            pane.setMinHeight(62.0);
            pane.setPrefWidth(840.0);

            User user = null;
            try {
                user = userService.getUserById(reservationEvent1.getUser_id());
            } catch (SQLException e) {
                e.printStackTrace(); // ou tout autre traitement approprié de l'erreur
            }

            String prenomUser = "";
            String nomUser = "";
            if (user != null) {
                prenomUser = user.getLast_name();
                nomUser = user.getFirst_name();
            }

            Label label1 = new Label(nomUser);
            label1.setLayoutX(4.0);
            label1.setLayoutY(18.0);
            label1.setFont(new Font(14.0));

            Label label2 = new Label(prenomUser);
            label2.setLayoutX(150.0);
            label2.setLayoutY(18.0);
            label2.setPrefHeight(25.0);
            label2.setPrefWidth(200.0);
            label2.setFont(new Font(16.0));

            // Récupérer le nom event
            Event event = serviceEvent.getNomEventById(reservationEvent1.getEvent_id());
            String nom_event = event.getNom_event();

            Label label3 = new Label(nom_event);
            label3.setLayoutX(400.0);
            label3.setLayoutY(18.0);
            label3.setPrefHeight(25.0);
            label3.setPrefWidth(200.0);
            label3.setFont(new Font(16.0));
            ImageView imageView1 = new ImageView();
            Image image1 = new Image(getClass().getResourceAsStream("/Event/poubelle.gif"));
            imageView1.setImage(image1);
            imageView1.setFitHeight(20.0);
            imageView1.setFitWidth(20.0);
            imageView1.setLayoutX(580);
            imageView1.setLayoutY(20.0);
            imageView1.setPickOnBounds(true);
            imageView1.setPreserveRatio(true);
            imageView1.setOnMouseClicked((MouseEvent eventt) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmer la suppression");
                alert.setHeaderText("Êtes-vous sûr de supprimer cette reservation ?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Pane parent = (Pane) pane.getParent();

                    scom.supprimerReservationEvent(reservationEvent1.getId());


                    reservationEventList.remove(reservationEvent1);
                    parent.getChildren().remove(pane);
                }
            });
            Line line = new Line();
            line.setStrokeWidth(2.0);
            line.setStartX(-411.0);
            line.setStartY(9.400012969970703);
            line.setEndX(429.0);
            line.setEndY(9.400012969970703);
            line.setLayoutX(250.0);
            line.setLayoutY(53.0);
            line.setStroke(Color.web("#1F1E2F"));

            pane.getChildren().addAll(label2, label1, label3, imageView1, line);
            vbox1.getChildren().add(pane);
        }
        vbox1.setSpacing(5);
    }

    @FXML
    void NavConnexion(ActionEvent event) {

    }

    @FXML
    void exportDataToExcel(ActionEvent event) throws SQLException {
        List<ReservationEvent> reservationEventList = scom.afficherResrvationEvent();

        // Créer un nouveau FileChooser
        FileChooser fileChooser = new FileChooser();

        // Définir le nom du fichier par défaut et l'extension
        fileChooser.setInitialFileName("reservations.xlsx");

        // Afficher la fenêtre de dialogue pour permettre à l'utilisateur de choisir l'emplacement
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Réservations");

                // Créer l'en-tête du fichier Excel
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("ID");
                headerRow.createCell(1).setCellValue("Nom de l'utilisateur");
                headerRow.createCell(2).setCellValue("Prénom de l'utilisateur");
                headerRow.createCell(3).setCellValue("Nom de l'événement");
                headerRow.createCell(4).setCellValue("Prix de l'événement"); // Ajouter une colonne pour le prix de l'événement

                // Remplir les données
                int rowNum = 1;
                for (ReservationEvent reservationEvent : reservationEventList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(reservationEvent.getId());

                    User user = userService.getUserById(reservationEvent.getUser_id());
                    if (user != null) {
                        row.createCell(1).setCellValue(user.getLast_name());
                        row.createCell(2).setCellValue(user.getFirst_name());
                    } else {
                        row.createCell(1).setCellValue("");
                        row.createCell(2).setCellValue("");
                    }

                    Event e = serviceEvent.getNomEventById(reservationEvent.getEvent_id());
                    if (e != null) {
                        row.createCell(3).setCellValue(e.getNom_event());
                        row.createCell(4).setCellValue(e.getPrixentre()); // Ajouter le prix de l'événement à la colonne 4
                    } else {
                        row.createCell(3).setCellValue("");
                        row.createCell(4).setCellValue("");
                    }
                }

                // Écrire les données dans le fichier Excel sélectionné par l'utilisateur
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Export Excel réussi");
                    alert.setHeaderText(null);
                    alert.setContentText("L'exportation vers Excel a été effectuée avec succès !");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de l'export Excel");
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur s'est produite lors de l'exportation vers Excel !");
                    alert.showAndWait();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
