package com.example.pidev.services;

import com.example.pidev.entities.Flightclass;

import java.util.List;

public interface IFlightclassDAO {
    List<Flightclass> getAllFlightclasses();

    void addFlightclass(Flightclass flightclass);

    void updateFlightclass(Flightclass flightclass);

    void deleteFlightclass(int id);

    Flightclass getFlightClassById(int id);
}
