package models;

import java.sql.Date;

public class Event {
    private int id;
    private String nom_event;
    private  String adresse_event;
    private  int nbrticketsdispo;
    private Date datedebut_event;
    private  Date datefinevent;
    private int prixentre;
    private String imageevent;
    private int type_evenement_id ;
    private int user_id;

    public Event() {
    }

    public Event(String nom_event, String adresse_event, int nbrticketsdispo, Date datedebut_event, Date datefinevent, int prixentre, String imageevent, int type_evenement_id, int user_id) {
        this.nom_event = nom_event;
        this.adresse_event = adresse_event;
        this.nbrticketsdispo = nbrticketsdispo;
        this.datedebut_event = datedebut_event;
        this.datefinevent = datefinevent;
        this.prixentre = prixentre;
        this.imageevent = imageevent;
        this.type_evenement_id = type_evenement_id;
        this.user_id = user_id;
    }

    public Event(int id, String nom_event) {
        this.id = id;
        this.nom_event = nom_event;
    }

    public Event(int id, String nom_event, String adresse_event, int nbrticketsdispo, Date datedebut_event, Date datefinevent, int prixentre, String imageevent, int type_evenement_id, int user_id) {
        this.id = id;
        this.nom_event = nom_event;
        this.adresse_event = adresse_event;
        this.nbrticketsdispo = nbrticketsdispo;
        this.datedebut_event = datedebut_event;
        this.datefinevent = datefinevent;
        this.prixentre = prixentre;
        this.imageevent = imageevent;
        this.type_evenement_id = type_evenement_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getAdresse_event() {
        return adresse_event;
    }

    public void setAdresse_event(String adresse_event) {
        this.adresse_event = adresse_event;
    }

    public int getNbrticketsdispo() {
        return nbrticketsdispo;
    }

    public void setNbrticketsdispo(int nbrticketsdispo) {
        this.nbrticketsdispo = nbrticketsdispo;
    }

    public Date getDatedebut_event() {
        return datedebut_event;
    }

    public void setDatedebut_event(Date datedebut_event) {
        this.datedebut_event = datedebut_event;
    }

    public Date getDatefinevent() {
        return datefinevent;
    }

    public void setDatefinevent(Date datefinevent) {
        this.datefinevent = datefinevent;
    }

    public int getPrixentre() {
        return prixentre;
    }

    public void setPrixentre(int prixentre) {
        this.prixentre = prixentre;
    }

    public String getImageevent() {
        return imageevent;
    }

    public void setImageevent(String imageevent) {
        this.imageevent = imageevent;
    }

    public int getType_evenement_id() {
        return type_evenement_id;
    }

    public void setType_evenement_id(int type_evenement_id) {
        this.type_evenement_id = type_evenement_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom_event='" + nom_event + '\'' +
                ", adresse_event='" + adresse_event + '\'' +
                ", nbrticketsdispo=" + nbrticketsdispo +
                ", datedebut_event=" + datedebut_event +
                ", datefinevent=" + datefinevent +
                ", prixentre=" + prixentre +
                ", imageevent='" + imageevent + '\'' +
                ", type_evenement_id=" + type_evenement_id +
                ", user_id=" + user_id +
                '}';
    }
}
