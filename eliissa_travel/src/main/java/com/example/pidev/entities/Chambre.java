package com.example.pidev.entities;

import javafx.beans.property.*;


public class Chambre {

    private final IntegerProperty id;
    private final IntegerProperty hotelId;
    private final StringProperty typeChambre;
    private final StringProperty vueHotel;
    private final StringProperty typeLogHotel;
    private final DoubleProperty prixHotel;

    public Chambre(int id, int hotelId, String typeChambre, String vueHotel, String typeLogHotel, double prixHotel) {
        this.id = new SimpleIntegerProperty(id);
        this.hotelId = new SimpleIntegerProperty(hotelId);
        this.typeChambre = new SimpleStringProperty(typeChambre);
        this.vueHotel = new SimpleStringProperty(vueHotel);
        this.typeLogHotel = new SimpleStringProperty(typeLogHotel);
        this.prixHotel = new SimpleDoubleProperty(prixHotel);
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

    public IntegerProperty hotelIdProperty() {
        return hotelId;
    }

    public int getHotelId() {
        return hotelId.get();
    }

    public void setHotelId(int hotelId) {
        this.hotelId.set(hotelId);
    }

    public StringProperty typeChambreProperty() {
        return typeChambre;
    }

    public String getTypeChambre() {
        return typeChambre.get();
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre.set(typeChambre);
    }

    public StringProperty vueHotelProperty() {
        return vueHotel;
    }

    public String getVueHotel() {
        return vueHotel.get();
    }

    public void setVueHotel(String vueHotel) {
        this.vueHotel.set(vueHotel);
    }

    public StringProperty typeLogHotelProperty() {
        return typeLogHotel;
    }

    public String getTypeLogHotel() {
        return typeLogHotel.get();
    }

    public void setTypeLogHotel(String typeLogHotel) {
        this.typeLogHotel.set(typeLogHotel);
    }

    public DoubleProperty prixHotelProperty() {
        return prixHotel;
    }

    public double getPrixHotel() {
        return prixHotel.get();
    }

    public void setPrixHotel(double prixHotel) {
        this.prixHotel.set(prixHotel);
    }



    @Override
    public String toString() {
        return "Chambre{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", typeChambre='" + typeChambre + '\'' +
                ", vueHotel='" + vueHotel + '\'' +
                ", typeLogHotel='" + typeLogHotel + '\'' +
                ", prixHotel=" + prixHotel +
                '}';
    }
}
