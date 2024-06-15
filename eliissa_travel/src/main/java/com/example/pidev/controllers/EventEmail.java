package com.example.pidev.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EventEmail {
    @FXML
    private TextField emailField;

    @FXML
    private TextField eventField;

    @FXML
    public void sendReservationEmail() {
        String to = emailField.getText();
        String event = eventField.getText();
        EmailService.sendReservationEmail(to, event);
    }
}
