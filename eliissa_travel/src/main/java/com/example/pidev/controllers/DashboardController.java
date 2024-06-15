/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.example.pidev.entities.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.pidev.services.UserService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
/**
 * FXML Controller class
 *
 * @author don7a
 */
public class DashboardController implements Initializable {

    private User user;
    @FXML
    private TableColumn<User,Integer> Cid;
    @FXML
    private TableColumn<User, String> cUsername;
    @FXML
    private TableColumn<User, String> cName;
    @FXML
    private TableColumn<User, String> cLastName;
    @FXML
    private TableColumn<User, String> cEmail;
    @FXML
    private TableView<User> tbUsers;


    @FXML
    private Button gotoadmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ListeUsers();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
    private List<User> usersList;

    public void ListeUsers() throws SQLException {
        UserService hrc = new UserService();

        cUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        cName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        cLastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        boolean deleteColumnExists = false;
        boolean blockColumnExists = false;
        boolean adminColumnExists = false;

        for (TableColumn column : tbUsers.getColumns()) {
            if (column.getText().equals("ACTION")) {
                deleteColumnExists = true;
                break;
            }
        }

        if (!deleteColumnExists) {
            TableColumn<User, Void> deleteColumn = new TableColumn<>("ACTION");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<User, Void>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            User u = getTableView().getItems().get(getIndex());
                            UserService us = new UserService();
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Delete Account");
                            alert.setHeaderText("Are you sure you want to delete this account?");
                            alert.setContentText("This action cannot be undone.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                try {
                                    us.aaaa(u.getId());

                                    refreshTable();
                                } catch (SQLException ex) {
                                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                                }
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

            tbUsers.getColumns().add(deleteColumn);
        }

        if (!blockColumnExists) {
            TableColumn<User, Void> blockColumn = new TableColumn<>("VERIFICATION");
            blockColumn.setCellFactory(column -> {
                return new TableCell<User, Void>() {
                    private final Button blockButton = new Button("Verified");

                    {
                        blockButton.setOnAction(event -> {
                            // Get the User associated with this row
                            User user = getTableView().getItems().get(getIndex());
                            System.out.println(user);
                            System.out.println(user.getIs_verified());
                            // Toggle the is_verified flag on the user object
                            if (user.getIs_verified() == 0)
                                user.setIs_verified(1);
                            else
                                user.setIs_verified(0);

                            // Update the database or perform any other action
                            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elissa_travel", "root", "")) {
                                String sql = "UPDATE user SET is_verified = ? WHERE id = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setInt(1, user.getIs_verified());
                                statement.setInt(2, user.getId());
                                int rowsUpdated = statement.executeUpdate();
                                System.out.println("Rows updated: " + rowsUpdated);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            // Update button text based on is_verified
                            if (user.getIs_verified() == 0) {
                                blockButton.setText("Verified");
                            } else {
                                blockButton.setText("Not Verified");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set button text based on is_verified
                            User user = getTableView().getItems().get(getIndex());
                            if (user.getIs_verified() == 0) {
                                blockButton.setText("Verified");
                            } else {
                                blockButton.setText("Not Verified");
                            }
                            setGraphic(blockButton);
                        }
                    }
                };
            });

            tbUsers.getColumns().add(blockColumn);
        }
        TableColumn<User, Void> editProfileColumn = new TableColumn<>("Edit Profile");

        // Define the cell factory for the edit profile column
        editProfileColumn.setCellFactory(column -> {
            return new TableCell<User, Void>() {
                private final Button editProfileButton = new Button("Edit Profile");

                {
                    // Define the action when the edit profile button is clicked
                    editProfileButton.setOnAction(event -> {
                        // Get the User associated with this row
                        User user = getTableView().getItems().get(getIndex());

                        // Open the EditProfile.fxml view with the selected user
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProfile.fxml"));
                            Parent root = loader.load();
                            EditProfileController editProfileController = loader.getController();
                            editProfileController.setUser(user);
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
        tbUsers.getColumns().add(editProfileColumn);


        List<User> list = hrc.recuperer();
        ObservableList<User> observableList = FXCollections.observableArrayList(list);
        tbUsers.setItems(observableList);
    }

    private void refreshTable() {
        try {
            usersList = new UserService().recuperer();
            tbUsers.setItems(FXCollections.observableArrayList(usersList));
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
   @FXML
    public void Logout(ActionEvent event) throws IOException {
        // Load the login screen

       FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
       Parent root = loader.load();
       Scene scene = new Scene(root);
       Stage stage = (Stage) gotoadmin.getScene().getWindow();
       stage.setScene(scene);
       stage.show();
    }









    public void goto_admin_dash_nav(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Airport/base.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) gotoadmin.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
