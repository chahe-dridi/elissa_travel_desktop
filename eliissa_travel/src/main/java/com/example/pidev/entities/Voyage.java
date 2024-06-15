package com.example.pidev.entities;

public class Voyage {

    private int id;
    private int userId;
    private String mois;
    private int annee;
    private String destination;
    private String villedepart;

    public Voyage() {
    }

    public Voyage(int id, int userId, String mois, int annee, String villedepart, String destination) {
        this.id = id;
        this.userId = userId;
        this.mois = mois;
        this.annee = annee;
        this.destination = destination;
        this.villedepart=villedepart;
    }

    public Voyage(String villedepart, String destination, int annee, String mois) {
        this.mois = mois;
        this.annee = annee;
        this.destination = destination;
        this.villedepart=villedepart;
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

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVilledepart() {
        return villedepart;
    }

    public void setVilledepart(String villedepart) {
        this.villedepart = villedepart;
    }
}
