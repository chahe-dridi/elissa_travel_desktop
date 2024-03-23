package com.example.elissa.Models;

public class Airport {


    private int id;
    private int userId;
    private String code;
    private String name;
    private String city;
    private String country;

    // Constructor
    public Airport(int id, int userId, String code, String name, String city, String country) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public Airport(  int userId, String code, String name, String city, String country) {

        this.userId = userId;
        this.code = code;
        this.name = name;
        this.city = city;
        this.country = country;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
