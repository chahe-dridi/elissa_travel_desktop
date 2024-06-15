package com.example.pidev.entities;

public class ReservationVoyage {
    private int id;
    private int userId;
    private int voyageId;

    private static String nom;



    private static String prenom;
    private static int numberticket;
    private static String email;


    public ReservationVoyage(int id, int voyageId, int userId, String nom, String prenom, int numberticket, String email) {
        this.id = id;
        this.voyageId = voyageId;
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.numberticket = numberticket;
        this.email = email;
    }

    public ReservationVoyage(String nom, String prenom, int numberticket, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.numberticket = numberticket;
        this.email = email;


    }

    public ReservationVoyage() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumberticket() {
        return numberticket;
    }

    public void setNumberticket(int numberticket) {
        this.numberticket = numberticket;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
