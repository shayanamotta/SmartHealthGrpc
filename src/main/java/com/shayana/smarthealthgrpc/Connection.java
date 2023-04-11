package com.shayana.smarthealthgrpc;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    private static java.sql.Connection connection=null;
    public static java.sql.Connection connect(){
        String url="jdbc:sqlite:shayana.db";
        try{
            connection= DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            init();
            return connection;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void init() throws SQLException {

        String query="CREATE TABLE IF NOT EXISTS Services(ServiceID TEXT NOT NULL,name TEXT NOT NULL,timeoffered TEXT NOT NULL,cost INT NOT NULL,slots INT NOT NULL)";
        Statement statement= connection.createStatement();

        String query2="CREATE TABLE IF NOT EXISTS Appointments(ServiceID TEXT NOT NULL,slots INT NOT NULL,clientId TEXT NOT NULL)";

        String query3="CREATE TABLE IF NOT EXISTS Employees(EmployeeID TEXT NOT NULL,employeeName TEXT NOT NULL)";

        statement.execute(query);
        statement.execute(query2);
        statement.execute(query3);

        statement.closeOnCompletion();
        connection.close();


    }
}
