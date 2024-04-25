package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            // **** front  ***//
            //root = FXMLLoader.load(getClass().getResource("/AfficherEventFront.fxml"));
            // *** Back  ****//
            root = FXMLLoader.load(getClass().getResource("/AfficherEvent.fxml"));

            Scene scene = new Scene(root, 1300, 800);

            primaryStage.setTitle("Elissa Travel");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
