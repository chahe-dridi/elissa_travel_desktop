package com.example.pidev.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import  com.example.pidev.entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import  com.example.pidev.services.UserService;


public class SignUpController implements Initializable {

    UserService us = new UserService();

    @FXML
    private TextField prenomTF;
    @FXML
    private TextField nomTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private TextField emailTF;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void SignUp(ActionEvent event) throws SQLException, IOException {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        String nom = nomTF.getText();
        String prenom = prenomTF.getText();
        String email = emailTF.getText();

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required");
            alert.showAndWait();
            return;
        }

        // Validation for nom and prenom
        if (!nom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid name format");
            alert.setContentText("Please use a valid name");
            alert.showAndWait();
            return;
        }

        if (!prenom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid family name format");
            alert.setContentText("Please use a valid family name");
            alert.showAndWait();
            return;
        }

        // Check if the username already exists
     /*   if (us.getUserByUsername(username) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username already exists");
            alert.setContentText("Please choose a different username");
            alert.showAndWait();
            return;
        }*/

        // Check if the email format is valid
        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid email format");
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
            return;
        }

        User user = new User(username, password, email, nom, prenom, 0, null, new String[]{"ROLE_USER"});

        us.ajouter(user);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent parent = loader.load();
        LoginController controller = loader.getController();
        controller.setUser(user);
        usernameTF.getScene().setRoot(parent);
    }

    @FXML
    private void gotoLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
//            controller.setUsername(usernameTF.getText());
            usernameTF.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
