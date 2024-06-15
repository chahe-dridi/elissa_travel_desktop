package com.example.pidev.entities;

public class TypeEvenement {


    private  int id;
    private String nom_type;
    private String type_description	;

    public TypeEvenement(String nom_type, String type_description) {
        this.nom_type = nom_type;
        this.type_description = type_description;
    }

    public TypeEvenement(int id, String nom_type, String type_description) {
        this.id = id;
        this.nom_type = nom_type;
        this.type_description = type_description;
    }

    public TypeEvenement() {

    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_type() {
        return nom_type;
    }

    public void setNom_type(String nom_type) {
        this.nom_type = nom_type;
    }

    public String getType_description() {
        return type_description;
    }

    public void setType_description(String type_description) {
        this.type_description = type_description;
    }

    @Override
    public String toString() {
        return "TypeEvenement{" +
                "id=" + id +
                ", nom_type='" + nom_type + '\'' +
                ", type_description='" + type_description + '\'' +
                '}';
    }
}
