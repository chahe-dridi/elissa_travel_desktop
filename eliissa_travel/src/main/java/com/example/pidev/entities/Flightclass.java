package com.example.pidev.entities;

public class Flightclass {
    private int id;
    private int userId;
    private String className;
    private String description;
    private double price;
    private int ticketNumber;

    public Flightclass() {
    }



    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }






    public Flightclass(int userId, String className, String description, double price, int ticketNumber) {
        this.userId = userId;
        this.className = className;
        this.description = description;
        this.price = price;
        this.ticketNumber = ticketNumber;
    }

    public Flightclass(int id, int userId, String className, String description, double price, int ticketNumber) {
        this.id = id;
        this.userId = userId;
        this.className = className;
        this.description = description;
        this.price = price;
        this.ticketNumber = ticketNumber;
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

    @Override
    public String toString() {
        return "Flightclass{" +
                "id=" + id +
                ", userId=" + userId +
                ", className='" + className + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", ticketNumber=" + ticketNumber +
                '}';
    }
}
