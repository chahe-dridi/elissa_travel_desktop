package com.example.pidev.controllers;

import com.example.pidev.entities.Voyage;
import com.example.pidev.services.VoyageDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class afficheClientController implements Initializable {



    @FXML
    private HBox cardLayout;
    private List<Voyage> AddListVoyage;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddListVoyage = new ArrayList<> (AddListVoyage());
        try
        {
            for (int i = 0; i < AddListVoyage. size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Voyage/card.fxml"));

                //fxmlLoader.setLocation(getClass().getResource(name:"card.fxml"));
                HBox cardBox = fxmlLoader.load();
                cardController cardController = fxmlLoader.getController();
                cardController.SetData(AddListVoyage.get(i));
                cardLayout.getChildren().add(cardBox);
            } }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    VoyageDAO voyageDAO= new VoyageDAO();
    private List<Voyage> AddListVoyage(){
        List<Voyage> ls = voyageDAO.getAllVoyages();



        return ls;
    }







}
