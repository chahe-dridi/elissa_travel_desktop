/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.controllers;

import com.example.pidev.entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import com.example.pidev.services.UserService;

/**
 * FXML Controller class
 *
 * @author don7a
 */
public class ProfileController implements Initializable {

    @FXML
    private Label usernameLB;
    @FXML
    private Label nomLB;
    @FXML
    private Label prenomLB;
    @FXML
    private Label emailLB;
    
    private User user;
      public void setUser(User user) {
        this.user = user;
        usernameLB.setText(user.getUsername());
        nomLB.setText(user.getFirst_name());
        prenomLB.setText(user.getLast_name());
        emailLB.setText(user.getEmail());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void edit(ActionEvent event) {
        
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProfile.fxml"));
        Parent root = loader.load();
        EditProfileController controller = loader.getController();
        controller.setUser(user);
        usernameLB.getScene().setRoot(root);
    } catch (IOException ex) {
        System.err.println(ex.getMessage());
    }
    }
    

    @FXML
    private void Logout(ActionEvent event) {
           try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            usernameLB.getScene().setRoot(root);
           
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
  

    
    
    
    
    @FXML
private void Delete(ActionEvent event) throws SQLException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Do you really want to delete your account?");
    alert.setContentText("This action cannot be undone.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        try {
            String username = usernameLB.getText();
            UserService userService = new UserService();
            List<User> userList = userService.recuperer();

            for (User userr : userList) {
                if (userr.getUsername().equals(username)) {
                    userService.supprimer(userr);
                    System.out.println("User " + username + " deleted from database.");
                    break;
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            usernameLB.getScene().setRoot(root);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}


    
    
    
   
    
}
