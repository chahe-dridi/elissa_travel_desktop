package com.example.elissa.Models;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

//comment
        public static void AjoutCommentaireEmail(String userEmail,String htmlContent) {
            // Paramètres de la session pour envoyer l'e-mail
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP de Gmail
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.port", "587"); // Port SMTP pour TLS (587)
            props.put("mail.smtp.auth", "true"); // Authentification requise
            props.put("mail.smtp.starttls.enable", "true"); // Activation de TLS

            // Informations d'authentification pour votre compte Gmail
            String username = "tester44.tester2@gmail.com"; // Votre adresse e-mail Gmail
            String password = "hpev dqbv clze bhxa"; // Votre mot de passe Gmail

            // Création de la session avec les informations d'authentification
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                // Création du message e-mail
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail)); // Adresse e-mail du destinataire
                message.setSubject("Flight Reservation Details"); // Objet de l'e-mail
                message.setContent(htmlContent, "text/html"); // Set the email content as HTML

                // Envoi de l'e-mail
                Transport.send(message);

                System.out.println("E-mail envoyé avec succès !");

                System.out.println("E-mail envoyé avec succès !");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
            }
        }


}
