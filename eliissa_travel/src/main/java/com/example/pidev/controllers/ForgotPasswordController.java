package com.example.pidev.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import GUI.ControleSaisieTextFields;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.pidev.services.EmailSender;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.pidev.services.UserService;


/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ForgotPasswordController implements Initializable {

    public static int code;
    public static String EmailReset ; 
    @FXML
    private Button BtnCode;
    @FXML
    private TextField tfMailO;

//    ControleSaisieTextFields cs;

    public static int generateVerificationCode() {
        // Générer un code de vérification aléatoire à 6 chiffres
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnCodeAction(ActionEvent event) throws UnsupportedEncodingException {
        code = generateVerificationCode();
        Alert A = new Alert(Alert.AlertType.WARNING);
        UserService su = new UserService();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean verifMail = tfMailO.getText().matches(emailRegex);

        if (!tfMailO.getText().equals("") && verifMail) {
            if (su.ChercherMail(tfMailO.getText()) == 1) {
                EmailReset = tfMailO.getText();
                EmailSender.sendEmail("walahamdi0@gmail.com", "ezqcncluccewfspa", tfMailO.getText(), "Verification code", "Votre code de verification est : " + code);

                try {

                    Parent page1 = FXMLLoader.load(getClass().getResource("/VerifCode.fxml"));

                    Scene scene = new Scene(page1);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);

                    stage.show();

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());

                }

            } else {
                A.setContentText("pas de compte lié avec cette adresse ! ");
                A.show();
            }
        } else {
            A.setContentText("Veuillez saisir une adresse mail valide ! ");
            A.show();
        }
    }

    @FXML
    private void btnAnnulerForgot(ActionEvent event) {
        try {

                    Parent page1 = FXMLLoader.load(getClass().getResource("/Login.fxml"));

                    Scene scene = new Scene(page1);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);

                    stage.show();

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());

                }
    }

}
