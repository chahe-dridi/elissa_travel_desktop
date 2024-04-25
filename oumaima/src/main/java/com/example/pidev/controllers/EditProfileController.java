/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.controllers;

import com.example.pidev.entities.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.pidev.services.UserService;


/**
 * FXML Controller class
 *
 * @author don7a
 */
public class EditProfileController implements Initializable {
    
    private User user;
    @FXML
    private TextField usernamepTF;
    @FXML
    private TextField nompTF;
    @FXML
    private TextField prenompTF;
    @FXML
    private TextField emailpTF;
    private int id;
      
    public void setUser(User user) {
        this.user = user;
        usernamepTF.setText(user.getUsername());
        nompTF.setText(user.getFirst_name());
        prenompTF.setText(user.getLast_name());
        emailpTF.setText(user.getEmail());
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
private void update(ActionEvent event) {
    if (usernamepTF.getText().isEmpty() || nompTF.getText().isEmpty() || prenompTF.getText().isEmpty() || emailpTF.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
    } else if (!isValidUsername(usernamepTF.getText()) || !isValidName(nompTF.getText()) || !isValidName(prenompTF.getText()) || !isValidEmail(emailpTF.getText())) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid information.");
        alert.showAndWait();
    } else {
        try {
            UserService userService = new UserService();
            String newUsername = usernamepTF.getText();
            String newNom = nompTF.getText();
            String newPrenom = prenompTF.getText();
            String newEmail = emailpTF.getText();

            user.setFirst_name(newNom);
            user.setLast_name(newPrenom);
            user.setEmail(newEmail);
            user.setUsername(newUsername);
            
            
            // Check if the new username already exists in the database, excluding the old username of the current user
            if (  userService.checkUsernameExists(newUsername,user.getId()) ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists. Please choose another username.");
                alert.showAndWait();
                return;
            }

            userService.modifier(user);
            System.out.println("User " + user.getUsername() + " updated in the database.");

            // Load the Profile.fxml view with the updated user credentials
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
            Parent root = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}



    
        private boolean isValidUsername(String username) {
    // Check if the username contains only alphanumeric characters and has length between 3 and 20
    return username.matches("^[a-zA-Z0-9]{3,20}$");
}

private boolean isValidName(String name) {
    // Check if the name contains only letters and has length between 2 and 50
    return name.matches("^[a-zA-Z]{2,50}$");
}

private boolean isValidEmail(String email) {
    // Check if the email matches the email format
    return email.matches("^\\S+@\\S+\\.\\S+$");
}
    
}
