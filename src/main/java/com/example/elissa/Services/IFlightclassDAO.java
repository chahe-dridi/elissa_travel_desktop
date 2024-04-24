package com.example.elissa.Services;

import com.example.elissa.Models.Flightclass;

import java.util.List;

public interface IFlightclassDAO {
    List<Flightclass> getAllFlightclasses();

    void addFlightclass(Flightclass flightclass);

    void updateFlightclass(Flightclass flightclass);

    void deleteFlightclass(int id);

    Flightclass getFlightClassById(int id);
}
