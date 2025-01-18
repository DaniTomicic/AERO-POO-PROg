package main;

import DBRelated.ConnectionDB;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        //Llama al metodo de conexion en el paquete
        Connection con = ConnectionDB.getConnection();
    }
}