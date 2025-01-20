package main;

import DBRelated.ConnectionDB;
import control.VueloControl;
import modelo.VueloDAO;
import javax.swing.*;
import java.sql.*;


public class Main {
    private static VueloControl vueloControl;

    public static void main(String[] args) {
        try {
            //Llama al metodo de conexion en el paquete
            Connection con = ConnectionDB.getConnection();

            if (con == null) {
                System.out.println("No connection");
            }else {
                VueloDAO vueloDAO = new VueloDAO(con);
                vueloControl = new VueloControl(vueloDAO);
            }
            mostrarMenu();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }



    }
    public static void mostrarMenu() {
        boolean isFinished=false;
        String[] opciones = {
                "1. Crear Vuelo","2. Modificar Vuelo","3. Eliminar Vuelo","4. Mostrar Vuelos","5. Crear Pasajero","6. Modificar Pasajero","7. Eliminar Pasajero","8. Mostrar Pasajeros","9.Vuelos por Procedencia","10. Vuelos por Destino","11. Pasajeros por vuelo","12. Vuelo de un Pasajero","13. Salir"};

        do {
            try {
                String eleccion = (String) JOptionPane.showInputDialog(null,
                        "Ingrese el opcion: ",
                        "Opciones",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                int opcion = Integer.parseInt(eleccion.split("\\.")[0]);

                switch (opcion) {
                    case 1:{
                        vueloControl.insert();
                        break;
                    }
                    case 2:{
                        vueloControl.modify();
                        break;
                    }
                    case 13:{
                        isFinished=true;
                    }
                    default:
                        isFinished=true;

                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Introduzca un numero valido");
            }

        }while (!isFinished);
    }
}
