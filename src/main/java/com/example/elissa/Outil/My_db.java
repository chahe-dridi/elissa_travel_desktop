package com.example.elissa.Outil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class My_db {



    Connection conn ;
    String  url = "jdbc:mysql://localhost:3306/elissa_travel";
    String  user = "root";
    String  pwd  = "";
    Statement ste;
    static My_db instance;

    private My_db() {
        try {
            conn= DriverManager.getConnection(url , user , pwd);
            System.out.println("connected");
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }


    }
    public static  My_db getInstance(){
        if (instance ==null)
            instance=new My_db();
        return  instance;
    }

    public Connection getConn() {
        return conn;
    }


}
