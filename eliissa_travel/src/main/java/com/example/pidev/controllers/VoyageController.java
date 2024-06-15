package com.example.pidev.controllers;

import com.example.pidev.entities.Voyage;
import com.example.pidev.services.VoyageDAO;
import com.example.pidev.controllers.EditVoyageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoyageController implements Initializable {

    public Button btnFind;
    public TextField tfindDestination;
    @FXML
    private Button btnDelete;
    private Voyage voyage;

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tVilleDepart;

    @FXML
    private TableView<Voyage> table;

    @FXML
    private TextField tannee;

    @FXML
    private TextField tdestination;

    @FXML
    private TextField tmois;


    VoyageDAO voyageDAO = new VoyageDAO();


    @FXML
    private void findTrip() throws SQLException {
        String destination = tfindDestination.getText().trim();
        if (!destination.isEmpty()) {
            Voyage voyageByDestination = voyageDAO.getVoyageByDestination(destination);
            if (voyageByDestination != null) {
                table.getItems().clear();
                table.getItems().addAll(voyageByDestination);
                showAlert(Alert.AlertType.INFORMATION, "Voyages trouvés", "Voyages trouvés pour la destination : " + destination);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun voyage trouve avec la destionation :" + destination);

            }
        } else {
            table.getItems().clear();
            table.getItems().addAll(loadAllVoyages());
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer une destination valide.");

        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void createTrip(ActionEvent event) {

        // Récupérer les valeurs des champs de texte
        String villeDepart = tVilleDepart.getText();
        String destination = tdestination.getText();
        int annee = Integer.parseInt(tannee.getText());
        String mois = tmois.getText();

        // Créer une nouvelle instance de Voyage
        Voyage voyage = new Voyage(villeDepart, destination, annee, mois);

        // Ajouter le voyage à votre source de données
        table.getItems().add(voyage);
        voyageDAO.addVoyage(voyage);
        // Effacer les champs de texte après la création du voyage
        clearFields();

    }



    private void clearFields() {
        tVilleDepart.clear();
        tdestination.clear();
        tannee.clear();
        tmois.clear();
    }


    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }


    @FXML
    void deleteTrip(ActionEvent event) {
        // Récupérer le voyage sélectionné dans le TableView
        Voyage selectedVoyage = table.getSelectionModel().getSelectedItem();

        // Supprimer le voyage de votre source de données
        table.getItems().remove(selectedVoyage);

    }

    @FXML
    void updateTrip(ActionEvent event) {
        // Récupérer le voyage sélectionné dans le TableView
        Voyage selectedVoyage = table.getSelectionModel().getSelectedItem();

        // Mettre à jour les attributs du voyage avec les nouvelles valeurs
        selectedVoyage.setVilledepart(tVilleDepart.getText());
        selectedVoyage.setDestination(tdestination.getText());
        selectedVoyage.setAnnee(Integer.parseInt(tannee.getText()));
        selectedVoyage.setMois(tmois.getText());

        // Mettre à jour le TableView
        table.refresh();

    }
    private List<Voyage> ListVoyages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ListVoyages = new VoyageDAO().getAllVoyages();
        table.setItems(FXCollections.observableArrayList(ListVoyages));
        try {

            ListVoyage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private List<Voyage> loadAllVoyages() {
      return voyageDAO.getAllVoyages();
    }

    @FXML
    private TableColumn<Voyage,String> cVille;
    @FXML
    private TableColumn<Voyage, String> cDestination;
    @FXML
    private TableColumn<Voyage, Integer> cAnne;
    @FXML
    private TableColumn<Voyage, Integer> cMois;


    public void ListVoyage() throws SQLException {
        VoyageDAO hrc = new VoyageDAO();

        cVille.setCellValueFactory(new PropertyValueFactory<>("villedepart"));
        cDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        cAnne.setCellValueFactory(new PropertyValueFactory<>("annee"));
        cMois.setCellValueFactory(new PropertyValueFactory<>("mois"));

        boolean deleteColumnExists = false;
        TableColumn<Voyage, Void> editProfileColumn = new TableColumn<>("Edit Voyage");

        for (TableColumn column : table.getColumns()) {
            if (column.getText().equals("ACTION")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<Voyage, Void> deleteColumn = new TableColumn<>("ACTION");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<Voyage, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Voyage u = getTableView().getItems().get(getIndex());
                            VoyageDAO us = new VoyageDAO();
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Account");
                            alert.setHeaderText("Are you sure you want to delete this voyage?");
                            alert.setContentText("This action cannot be undone.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {

                                    us.deleteVoyage(u.getId());

                                    refreshTable();

                            } else {
                                alert.close();
                            }


                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            });

            table.getColumns().add(deleteColumn);
        }




        // Define the cell factory for the edit profile column
        editProfileColumn.setCellFactory(column -> {
            return new TableCell<Voyage, Void>() {
                private final Button editProfileButton = new Button("Edit Voyage");

                {
                    // Define the action when the edit profile button is clicked
                    editProfileButton.setOnAction(event -> {
                        // Get the User associated with this row
                        Voyage voyage = getTableView().getItems().get(getIndex());

                        // Open the EditProfile.fxml view with the selected user
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/update.fxml"));
                            Parent root = loader.load();
                            EditVoyageController editVoyageController = loader.getController();
                            editVoyageController.setVoyage(voyage);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editProfileButton);
                    }
                }
            };
        });

        // Add the edit profile column to the table
        table.getColumns().add(editProfileColumn);


        List<Voyage> list = hrc.getAllVoyages();
        ObservableList<Voyage> observableList = FXCollections.observableArrayList(list);
        table.setItems(observableList);
    }

    private void refreshTable() {

            ListVoyages = new VoyageDAO().getAllVoyages();
            table.setItems(FXCollections.observableArrayList(ListVoyages));

    }
@FXML
    private  void routButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/programmeIndex.fxml"));
        Parent parent = loader.load();
        ProgrammeController controller = loader.getController();
        tannee.getScene().setRoot(parent);

    }

    @FXML
    private  void routReservation() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Voyage/afficheReservation.fxml"));
        Parent parent = loader.load();
        afficheReservationController controller = loader.getController();
        tannee.getScene().setRoot(parent);

    }







}
