package main;

import DBRelated.ConnectionDB;
import control.PasajeroControl;
import control.VueloControl;
import modelo.Vuelo;
import modelo.VueloDAO;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws SQLException {

        //Llama al metodo de conexion en el paquete
        Connection con = ConnectionDB.getConnection();

        if (con == null) {
            System.out.println("No connection");
        }else {
            VueloControl vueloControl = new VueloControl();
            PasajeroControl pasajeroControl = new PasajeroControl();

            // Alta de un vuelo
            vueloControl.createVuelo("AEAS001", LocalDate.parse("2025/01/21"),"Madrid", "Barcelona");

            // Alta de pasajeros para ese vuelo
            PasajeroControl.createPasajero("11111111P", "Dani Tomicic", "999999999", "AEAS001");

            PasajeroControl.createPasajero("22222222Q", "Juan Pérez", "888888888", "AEAS001");
        }
    }
}
