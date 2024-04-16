package com.example.elissa.Models;

public class Flightclass {
    private int id;
    private int userId;
    private String className;
    private String description;
    private double price;
    private int ticketNumber;



    public Flightclass() {

    }


    // Constructor
    public Flightclass(int id, int userId, String className, String description, double price, int ticketNumber) {
        this.id = id;
        this.userId = userId;
        this.className = className;
        this.description = description;
        this.price = price;
        this.ticketNumber = ticketNumber;
    }

    public Flightclass(  int userId, String className, String description, double price, int ticketNumber) {
        this.id = id;
        this.userId = userId;
        this.className = className;
        this.description = description;
        this.price = price;
        this.ticketNumber = ticketNumber;
    }
    // Getters and Setters
    // You can generate them automatically in IDEs like IntelliJ IDEA or Eclipse
    // Or write them manually like below:

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
