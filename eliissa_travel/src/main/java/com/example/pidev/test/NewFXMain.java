package com.example.pidev.test;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class NewFXMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file
        //    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/showflightclient.fxml"));

           FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/AfficherTypeEvent.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/AfficherEvent.fxml"));
          //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/Event/AfficherReservationEvent.fxml"));

            Parent root = loader.load();

            // Set the size of the window
            primaryStage.setWidth(1200); // Set the width as needed
            primaryStage.setHeight(800); // Set the height as needed

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            primaryStage.setTitle("Elissa_travel");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
