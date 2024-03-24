package com.example.elissa;

import com.example.elissa.Services.AirportDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;




public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the index.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/elissa/Airport/index.fxml"));

        // Create a new scene with the loaded FXML file as the root
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        // Set the title of the stage
        stage.setTitle("Airport");

        // Set the scene onto the stage
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }



    public static void main(String[] args) {


            launch();

        }
}



