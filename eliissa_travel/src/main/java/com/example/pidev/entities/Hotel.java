package com.example.pidev.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Hotel {

    private final IntegerProperty id;
    private final IntegerProperty nb_chambre;
    private final StringProperty nom_hotel;
    private final StringProperty lieu;
    private final StringProperty tel_hotel;
    private final StringProperty email;
    private final StringProperty disc_hotel;
    private final StringProperty etat_hotel;
    private final StringProperty image;
    ImageView imageView;


    public Hotel(int id, int nb_chambre, String nom_hotel, String lieu, String tel_hotel, String email, String disc_hotel, String etat_hotel, String image) {
        this.id = new SimpleIntegerProperty(id);
        this.nb_chambre = new SimpleIntegerProperty(nb_chambre);
        this.nom_hotel = new SimpleStringProperty(nom_hotel);
        this.lieu = new SimpleStringProperty(lieu);
        this.tel_hotel = new SimpleStringProperty(tel_hotel);
        this.email = new SimpleStringProperty(email);
        this.disc_hotel = new SimpleStringProperty(disc_hotel);
        this.etat_hotel = new SimpleStringProperty(etat_hotel);
        this.image = new SimpleStringProperty(image);
    }

    public Hotel(int nb_chambre, String nom_hotel, String lieu, String tel_hotel, String email, String disc_hotel, String etat_hotel, String image) {
        this.id = new SimpleIntegerProperty();
        this.nb_chambre = new SimpleIntegerProperty(nb_chambre);
        this.nom_hotel = new SimpleStringProperty(nom_hotel);
        this.lieu = new SimpleStringProperty(lieu);
        this.tel_hotel = new SimpleStringProperty(tel_hotel);
        this.email = new SimpleStringProperty(email);
        this.disc_hotel = new SimpleStringProperty(disc_hotel);
        this.etat_hotel = new SimpleStringProperty(etat_hotel);
        this.image = new SimpleStringProperty(image);
        this.imageView = new ImageView(new Image("file:" + image)); // Initialize the ImageView with the image file
        this.imageView.setFitWidth(100); // Set the width of the image (adjust as needed)
        this.imageView.setFitHeight(100);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty nb_chambreProperty() {
        return nb_chambre;
    }

    public int getNb_chambre() {
        return nb_chambre.get();
    }

    public void setNb_chambre(int nb_chambre) {
        this.nb_chambre.set(nb_chambre);
    }

    public StringProperty nom_hotelProperty() {
        return nom_hotel;
    }

    public String getNom_hotel() {
        return nom_hotel.get();
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel.set(nom_hotel);
    }

    public StringProperty lieuProperty() {
        return lieu;
    }

    public String getLieu() {
        return lieu.get();
    }

    public void setLieu(String lieu) {
        this.lieu.set(lieu);
    }

    public StringProperty tel_hotelProperty() {
        return tel_hotel;
    }

    public String getTel_hotel() {
        return tel_hotel.get();
    }

    public void setTel_hotel(String tel_hotel) {
        this.tel_hotel.set(tel_hotel);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty disc_hotelProperty() {
        return disc_hotel;
    }

    public String getDisc_hotel() {
        return disc_hotel.get();
    }

    public void setDisc_hotel(String disc_hotel) {
        this.disc_hotel.set(disc_hotel);
    }

    public StringProperty etat_hotelProperty() {
        return etat_hotel;
    }

    public String getEtat_hotel() {
        return etat_hotel.get();
    }

    public void setEtat_hotel(String etat_hotel) {
        this.etat_hotel.set(etat_hotel);
    }

    public StringProperty imageProperty() {
        return image;
    }

    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nb_chambre=" + nb_chambre +
                ", nom_hotel=" + nom_hotel +
                ", lieu=" + lieu +
                ", tel_hotel=" + tel_hotel +
                ", email=" + email +
                ", disc_hotel=" + disc_hotel +
                ", etat_hotel=" + etat_hotel +
                ", image=" + image +
                '}';
    }
}
