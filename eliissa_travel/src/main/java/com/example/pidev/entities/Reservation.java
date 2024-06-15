package com.example.pidev.entities;

import java.util.Date;

public class Reservation {

    private int id;
    private int hotelId = 1;
    private double prixTt;
    private Date date_arrive;
    private Date date_depart;
    private String email;
    private String nom;
    private String pre_nom;
    private String distination;
    private String chambr_e	;

    public Reservation(int id, int hotelId, double prixTt, Date date_arrive, Date date_depart, String email, String nom, String pre_nom, String distination, String chambr_e) {
        this.id = id;
        this.hotelId = hotelId;
        this.prixTt = prixTt;
        this.date_arrive = date_arrive;
        this.date_depart = date_depart;
        this.email = email;
        this.nom = nom;
        this.pre_nom = pre_nom;
        this.distination = distination;
        this.chambr_e = chambr_e;
    }

    public Reservation( Date date_arrive, Date date_depart, String email, String nom, String pre_nom, String distination, String chambr_e) {

        this.date_arrive = date_arrive;
        this.date_depart = date_depart;
        this.email = email;
        this.nom = nom;
        this.pre_nom = pre_nom;
        this.distination = distination;
        this.chambr_e = chambr_e;
    }



    public int getId() {
        return id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public double getPrixTt() {
        return prixTt;
    }

    public java.sql.Date getDateArrive() {
        return (java.sql.Date) date_arrive;
    }

    public java.sql.Date getDateDepart() {
        return (java.sql.Date) date_depart;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPreNom() {
        return pre_nom;
    }

    public String getDistination() {
        return distination;
    }

    public String getChambr_e() {
        return chambr_e;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setPrixTt(double prixTt) {
        this.prixTt = prixTt;
    }

    public void setDateArrive(Date date_arrive) {
        this.date_arrive = date_arrive;
    }

    public void setDateDepart(Date date_depart) {
        this.date_depart = date_depart;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPreNom(String pre_nom) {
        this.pre_nom = pre_nom;
    }

    public void setDistination(String distination) {
        this.distination = distination;
    }

    public void setChambr_e(String chambr_e) {
        this.chambr_e = chambr_e;
    }
}
