package com.example.elissa.Services;

import com.example.elissa.Models.Flight;
import com.example.elissa.Models.ReservationVolAdmin;

import java.util.List;

public interface IReservationVolAdminDAO {

    List<Flight> getAllReservations();

    void updateReservation(ReservationVolAdmin reservation);
    void updateFlight(ReservationVolAdmin reservation);
    void deleteReservation(int id);
}
