package com.example.pidev.services;

import com.example.pidev.entities.Flight;
import com.example.pidev.entities.ReservationVolAdmin;

import java.util.List;

public interface IReservationVolAdminDAO {

    List<Flight> getAllReservations();

    void updateReservation(ReservationVolAdmin reservation);
    void updateFlight(ReservationVolAdmin reservation);
    void deleteReservation(int id);
}
