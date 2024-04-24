package com.example.elissa.Services;

import com.example.elissa.Models.Flight;
import java.util.List;

public interface IFlightDAO {
    List<Flight> getAllFlights();
    Flight getFlightById(int id);
    void addFlight(Flight flight);
    void updateFlight(Flight flight);
    void deleteFlight(int id);
}
