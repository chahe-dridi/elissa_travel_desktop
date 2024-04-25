package com.example.elissa.Services;



import com.example.elissa.Models.Airport;

import java.sql.SQLException;
import java.util.List;

public interface IAirportDAO {

    void addAirport(Airport airport) throws SQLException;

    void updateAirport(Airport airport) throws SQLException;

    void deleteAirport(Airport airport) throws SQLException;

    List<Airport> getAllAirports() throws SQLException;

    Airport findById(int airportId) throws SQLException;

    List<Airport> searchAirportByCode(String code) throws SQLException;



  Airport getAirportById(int airportId) throws SQLException;


     Airport getAirportByCode(String airportCode) throws SQLException ;
}
