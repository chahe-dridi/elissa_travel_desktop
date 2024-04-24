package com.example.elissa.Models;

import java.time.LocalDateTime;

public class Flight {

    private int id;
    private int airportDepartId;
    private int airportArriveId;
    private int volclassId;
    private int userId;
    private String compagnieAerienne;
    private LocalDateTime heureDepart;
    private LocalDateTime heureArrive;
    private boolean disponible;
    private Flightclass flightclass; // Assuming Flightclass is another model class

    public Flight() {
    }

    public Flight(int id, int airportDepartId, int airportArriveId, int volclassId, int userId, String compagnieAerienne,
                  LocalDateTime heureDepart, LocalDateTime heureArrive, boolean disponible, Flightclass flightclass) {
        this.id = id;
        this.airportDepartId = airportDepartId;
        this.airportArriveId = airportArriveId;
        this.volclassId = volclassId;
        this.userId = userId;
        this.compagnieAerienne = compagnieAerienne;
        this.heureDepart = heureDepart;
        this.heureArrive = heureArrive;
        this.disponible = disponible;
        this.flightclass = flightclass;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAirportDepartId() {
        return airportDepartId;
    }

    public void setAirportDepartId(int airportDepartId) {
        this.airportDepartId = airportDepartId;
    }

    public int getAirportArriveId() {
        return airportArriveId;
    }

    public void setAirportArriveId(int airportArriveId) {
        this.airportArriveId = airportArriveId;
    }

    public int getVolclassId() {
        return volclassId;
    }

    public void setVolclassId(int volclassId) {
        this.volclassId = volclassId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCompagnieAerienne() {
        return compagnieAerienne;
    }

    public void setCompagnieAerienne(String compagnieAerienne) {
        this.compagnieAerienne = compagnieAerienne;
    }

    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public LocalDateTime getHeureArrive() {
        return heureArrive;
    }

    public void setHeureArrive(LocalDateTime heureArrive) {
        this.heureArrive = heureArrive;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Flightclass getFlightclass() {
        return flightclass;
    }

    public void setFlightclass(Flightclass flightclass) {
        this.flightclass = flightclass;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airportDepartId=" + airportDepartId +
                ", airportArriveId=" + airportArriveId +
                ", volclassId=" + volclassId +
                ", userId=" + userId +
                ", compagnieAerienne='" + compagnieAerienne + '\'' +
                ", heureDepart=" + heureDepart +
                ", heureArrive=" + heureArrive +
                ", disponible=" + disponible +
                ", flightClass=" + flightclass +
                '}';
    }


























    public Flight(int id, int airportDepartId, int airportArriveId, int volclassId, int userId, String compagnieAerienne, LocalDateTime heureDepart, LocalDateTime heureArrive, boolean disponible) {
        this.id = id;
        this.airportDepartId = airportDepartId;
        this.airportArriveId = airportArriveId;
        this.volclassId = volclassId;
        this.userId = userId;
        this.compagnieAerienne = compagnieAerienne;
        this.heureDepart = heureDepart;
        this.heureArrive = heureArrive;
        this.disponible = disponible;
    }


    public Flight( int airportDepartId, int airportArriveId, int volclassId, int userId, String compagnieAerienne, LocalDateTime heureDepart, LocalDateTime heureArrive, boolean disponible) {

        this.airportDepartId = airportDepartId;
        this.airportArriveId = airportArriveId;
        this.volclassId = volclassId;
        this.userId = userId;
        this.compagnieAerienne = compagnieAerienne;
        this.heureDepart = heureDepart;
        this.heureArrive = heureArrive;
        this.disponible = disponible;
    }

}
