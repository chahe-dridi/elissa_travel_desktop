package com.example.pidev.controllers;


import com.example.pidev.entities.Reservation;
import com.example.pidev.services.ServiceReservation;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AfficherController {

    @FXML
    private TextArea nom;

    @FXML
    private TextArea pre_nom;

    @FXML
    private TextArea Email;

    @FXML
    private TextArea destination;

    @FXML
    private TextArea chambre;

    @FXML
    private TextArea date_arriver;

    @FXML
    private TextArea date_depart;

    private Reservation selectedReservation; // Pour stocker la réservation sélectionnée
    ServiceReservation serviceReservation = new ServiceReservation();
    public void afficherDetails() {
        if (selectedReservation != null) {
            nom.setText(selectedReservation.getNom());
            pre_nom.setText(selectedReservation.getPreNom());
            Email.setText(selectedReservation.getEmail());
            System.out.println(selectedReservation.getDistination());
            destination.setText(selectedReservation.getDistination());
            chambre.setText(selectedReservation.getChambr_e());
            date_arriver.setText(selectedReservation.getDateArrive().toString());
            date_depart.setText(selectedReservation.getDateDepart().toString());
        }
    }

    private void addRows(PdfPTable table, Reservation selectedReservation) {
        table.addCell(selectedReservation.getNom());
        table.addCell(selectedReservation.getPreNom());
        table.addCell(selectedReservation.getEmail());
        table.addCell(selectedReservation.getDistination());
        table.addCell(selectedReservation.getChambr_e());
        table.addCell(selectedReservation.getDateDepart().toString());
        table.addCell(selectedReservation.getDateArrive().toString());
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        cell.setPhrase(new Phrase("Name", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("LastName", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Mail", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Destination", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Chambre", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Depart", headerFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Arrivée", headerFont));
        table.addCell(cell);
    }

    @FXML
    void generatePDF(ActionEvent event) {


        Document document = new Document();
        try {
            String filePath = "C:\\Users\\mohamed\\Desktop\\reservation.pdf";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.GREEN);
            Paragraph title = new Paragraph("Reservation Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            document.add(new Paragraph("Created on : " + formattedDateTime));

            document.add(new Paragraph("Elissa"));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);
            addTableHeader(table);
            addRows(table, selectedReservation); // Add selected reservation to the table
            document.add(table);

            document.close();
            System.out.println("PDF generated successfully.");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    public void setReservation(Reservation reservation) {
        selectedReservation = reservation;
    }

}
