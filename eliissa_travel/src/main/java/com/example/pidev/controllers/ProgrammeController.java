package com.example.pidev.controllers;



import com.example.pidev.entities.Programme;
import com.example.pidev.entities.Voyage;
import com.example.pidev.services.ProgrammeDAO;
import com.example.pidev.services.VoyageDAO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import java.time.format.DateTimeParseException;
import java.util.List;

public class ProgrammeController {

    @FXML
    private Button btnAddProgram;

    @FXML
    private TableView<Programme> tableProgramme;

    @FXML
    private TextField tdateDebut;

    @FXML
    private TextField tdateFin;

    @FXML
    private TextField tprice;

    @FXML
    private TextField tprogramme;

    private ProgrammeDAO programmeDAO = new ProgrammeDAO();

    private VoyageDAO voyageDAO;

    @FXML
    private Button btnGenerateCode;

    public AnchorPane qrImgPane;

    @FXML
    private ImageView imgQrCode;

    private Image generatedCodeImage;

    @FXML
    private ChoiceBox<String> voyageChoiceBox;

    @FXML
    private TableColumn<Programme,String> prodescription;
    @FXML
    private TableColumn<Programme,String> prodatedebut;
    @FXML
    private TableColumn<Programme,String> prodatefin;
    @FXML
    private TableColumn<Programme,Integer> proprice;

    @FXML
    public void initialize() {
        prodescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        prodatedebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        prodatefin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        proprice.setCellValueFactory(new PropertyValueFactory<>("prix"));
        voyageDAO = new VoyageDAO();
        List<Voyage> voyages = voyageDAO.getAllVoyages();
        voyageChoiceBox.getItems().addAll(voyages.stream().map(Voyage::getDestination).toList());
        List<Programme> programmes = programmeDAO.getAllProgram();
        ObservableList<Programme> observableList = FXCollections.observableArrayList(programmes);
        tableProgramme.setItems(observableList);


    }


    @FXML
    void AddProgram(ActionEvent event) throws SQLException {
        // Récupérer les valeurs des champs de texte
        String description = tprogramme.getText();
        int prix = Integer.parseInt(tprice.getText());
        String dateDebut = tdateDebut.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate datedebut = LocalDate.parse(dateDebut, formatter);
            // Now you have your LocalDate object
        } catch (DateTimeParseException e) {
            // Handle parsing error
            e.printStackTrace();
        }

        String dateFin = tdateFin.getText();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        try {
            LocalDate datefin = LocalDate.parse(dateFin, formatter2);
            // Now you have your LocalDate object
        } catch (DateTimeParseException e) {
            // Handle parsing error
            e.printStackTrace();
        }

        String selectedVoyage = voyageChoiceBox.getValue();

        Voyage voyage = voyageDAO.getVoyageByDestination(selectedVoyage);

        // Créer une nouvelle instance de programme
        Programme program = new Programme(description, prix, dateDebut, dateFin, voyage.getId());

        // Ajouter le voyage à votre source de données
        tableProgramme.getItems().add(program);
        programmeDAO.addProgramme(program);
        // Effacer les champs de texte après la création du voyage
        clearFields();


    }

    @FXML
    void btnGenerateCodeOnAction(ActionEvent event) {

        generateQRCode();
        updatePreview();
        btnGenerateCode.setDisable(false);
    }

    private void updatePreview() {
        if (generatedCodeImage != null) {
            imgQrCode.setImage(generatedCodeImage);
        }
    }

    private void generateQRCode() {

        String textToEncode = String.format("Programme Info: description: %s, price: %s, start date: %s, end date: %s, destination: %s",
                tprogramme.getText(),
                tprice.getText(),
                tdateDebut.getText(),
                tdateFin.getText(),
                voyageChoiceBox.getValue());
        int width = 200;
        int height = 200;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(textToEncode, BarcodeFormat.QR_CODE, width, height);
            generatedCodeImage = SwingFXUtils.toFXImage(MatrixToImageWriter.toBufferedImage(bitMatrix), null);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        saveQRCode();

    }

    private void saveQRCode() {
        if (generatedCodeImage != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
            File fileToSave = fileChooser.showSaveDialog(null);

            if (fileToSave != null) {
                try {
                    // Ensure the selected file has .png an extension
                    if (!fileToSave.getName().toLowerCase().endsWith(".png")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                    }

                    ImageIO.write(SwingFXUtils.fromFXImage(generatedCodeImage, null), "png", fileToSave);

                    // Show a success message
                    System.out.println(" saved successfully!");
                    imgQrCode.setVisible(true);
                    imgQrCode.setImage(new Image("images/qr1.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                    // Show an error message
                    System.out.println("Error saving ");
                }
            }
        }
    }

    private static void convertDate(String dateDebut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate datedebut = LocalDate.parse(dateDebut, formatter);

            // Now you have your LocalDate object
        } catch (DateTimeParseException e) {
            // Handle parsing error
            e.printStackTrace();
        }
    }

    private void clearFields() {
    }




}
