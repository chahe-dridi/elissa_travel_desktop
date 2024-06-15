package com.example.pidev.controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static void sendReservationEmail(String to, String event) {
        final String username = "yesminemdalla3@gmail.com";
        final String password = "fmuk dmur vlmq adyg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Confirmation de réservation");
            message.setText("Votre réservation pour l'événement " + event + " a été confirmée.");

            Transport.send(message);

            System.out.println("E-mail de réservation envoyé avec succès.");
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'e-mail de réservation : " + e.getMessage());
        }
    }
}
