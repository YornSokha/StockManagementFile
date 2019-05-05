package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * * line 72 change part of file
 * * line 77 config url and database for using
 * * line 86 set user and password
 * search this //<<<< to find what to update
 * GetConnection.connection
 * GetConnection.openConnection () => open connection
 * GetConnection.closeConnection () => close connection
 * */


public class GetConnection {
    public static final String separator = "®";
    public static Connection connection = null;

    public static void  openConnection(){
        try {
            Class.forName("org.postgresql.Driver");//<<<<update driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //<<<< port
        String url = "jdbc:postgresql://localhost:5432/StockTestData";

        try {

            ///<<<< update package connection "user name password "
            ///<<<< user and password
            connection = DriverManager.getConnection(url,"postgres", "123");

        } catch (SQLException e) {

            e.printStackTrace();

        }


    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}//endofcloseConnection

