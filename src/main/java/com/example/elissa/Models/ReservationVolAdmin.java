package com.example.elissa.Models;


public class ReservationVolAdmin {
    private int id;
    private int volId;
    private int userId;
    private double totalPrice;
    private String paymentMethod;

    public ReservationVolAdmin() {
    }


    private Flight flight; // Assuming Flight is a class representing flight details

    // Constructor, getters, and setters for existing fields...

    public void setFlight(Flight flight) {
        this.flight = flight;
    }





    public ReservationVolAdmin(int volId, int userId, double totalPrice, String paymentMethod) {
        this.volId = volId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public ReservationVolAdmin(int id, int volId, int userId, double totalPrice, String paymentMethod) {
        this.id = id;
        this.volId = volId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVolId() {
        return volId;
    }

    public void setVolId(int volId) {
        this.volId = volId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
