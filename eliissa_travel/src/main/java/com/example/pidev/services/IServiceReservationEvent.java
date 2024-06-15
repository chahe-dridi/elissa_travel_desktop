package com.example.pidev.services;

import com.example.pidev.entities.ReservationEvent;
import java.util.List;

public interface IServiceReservationEvent {

    public void ajouterReservationEvent(ReservationEvent reservationevent);

    public List<ReservationEvent> afficherResrvationEvent();


    public void modifierReservationEvent(ReservationEvent reservationevent);

    public void supprimerReservationEvent(int id);
}
