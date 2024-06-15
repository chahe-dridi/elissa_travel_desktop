package com.example.pidev.entities;

public class Programme {
    private int id;
    private int voyageId;
    private String dateDebut;
    private String dateFin;
    private int prix;
    private String description;


    public Programme(String description, int prix, String dateDebut, String dateFin ,int voyageId) {
        this.description = description;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin=dateFin;
        this.voyageId = voyageId;




    }

    public Programme() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
