package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            Class.forName("com.mysql.jdbc.Driver");//<<<<update driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //<<<< port
        String url = "jdbc:mysql://localhost:3306/javatesting";

        try {

            ///<<<< update package connection "user name password "
            ///<<<< user and password
            connection = DriverManager.getConnection(url,"root", "root");

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

