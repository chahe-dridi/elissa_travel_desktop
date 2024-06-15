/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Skand
 */
public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    void supprimer(T t) throws SQLException;

    List<T> recuperer() throws SQLException;

}
