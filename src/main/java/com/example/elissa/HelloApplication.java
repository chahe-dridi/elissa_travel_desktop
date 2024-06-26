package com.example.elissa;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;




public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here, e.g., show an error dialog or log the exception
        }
    }



    public static void main(String[] args) {


            launch();

        }
}



