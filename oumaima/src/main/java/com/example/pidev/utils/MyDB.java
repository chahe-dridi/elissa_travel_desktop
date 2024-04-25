/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pidev.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private static MyDB instance ;
    private final String URL ="jdbc:mysql://127.0.0.1:3306/oumaima";
    private final String USERNAME="root";
    private final String PASSWORD ="";

    Connection cnx ;


    private MyDB(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("DATABASE: Successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("DATABASE: Failed");
        }

    }
    public static MyDB getInstance(){
        if (instance == null)
            instance = new MyDB();

        return instance;
    }
    public Connection getCnx(){
        return cnx;
    }

}
