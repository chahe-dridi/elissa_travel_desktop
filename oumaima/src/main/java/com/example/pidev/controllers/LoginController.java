/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.controllers;

import com.example.pidev.entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import com.example.pidev.services.UserService;
import com.example.pidev.utils.MyDB;

/**
 * FXML Controller class
 *
 * @author don7a
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;
    Connection con;
    PreparedStatement pst;
    private User user;
    @FXML
    private Button LoginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }

    public void setUser(User user) {
        this.user = user;
        usernameTF.setText(user.getUsername());
    }

    @FXML
    private void Login(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        String nom = usernameTF.getText();
        String password = passwordTF.getText();

        if (nom.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both username and password");
        } else {
            try {
                UserService userService = new UserService();
                User loggedInUser = userService.login(nom, password);
                if (loggedInUser != null) {

                    if (loggedInUser.getRoles()[0].contains("ROLE_USER")) {
                        System.out.println("Loading user profile");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                        Parent root = loader.load();
                        ProfileController controller = loader.getController();
                        controller.setUser(loggedInUser);
                        usernameTF.getScene().setRoot(root);
                    } else if (loggedInUser.getRoles()[0].contains("ROLE_ADMIN")) {
                        System.out.println("Loading admin interface");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                        Parent root = loader.load();
                        DashboardController controller = loader.getController();
                        controller.setUser(loggedInUser);
                        usernameTF.getScene().setRoot(root);
                    }
                   
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while logging in. Please try again later.");
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void gotoSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
            Parent root = loader.load();
            usernameTF.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void forgor(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgotPassword.fxml"));
            Parent root = loader.load();
            usernameTF.getScene().setRoot(root);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }




}
