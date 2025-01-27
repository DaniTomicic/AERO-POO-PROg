package main;


import DBRelated.ConnectionDB;
import control.PasajeroControl;
import control.VueloControl;
import javax.swing.*;
import java.sql.*;


public class Main {
    private static VueloControl vueloControl;
    public static PasajeroControl pasajeroControl;
    public static void main(String[] args) {
        try (Connection con = ConnectionDB.getConnection()){
            if (con == null) {
                System.out.println("No connection");
                System.exit(0);
            }
            vueloControl = new VueloControl();
            pasajeroControl = new PasajeroControl();

            mostrarMenu();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (NullPointerException e){
            System.out.println("Acaba programa");
        }

    }
    public static void mostrarMenu() {
        boolean isFinished=false;
        String[] opciones =
                {
                "1. Crear Vuelo","2. Modificar Vuelo",
                "3. Eliminar Vuelo","4. Mostrar todos los Vuelos",
                "5. Mostrar Vuelo por codigo","6. Crear Pasajero",
                "7. Modificar Pasajero","8. Eliminar Pasajeros",
                "9.Mostrar Pasajeros","10. Vuelos por Destino",
                "11. Pasajeros por vuelo","12. Vuelo de un Pasajero",
                "13. Salir"
                };

        do {
            try {
                String eleccion = (String) JOptionPane.showInputDialog(
                        null,
                        "Ingrese el opcion: ",
                        "Opciones",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                int opcion = Integer.parseInt(eleccion.split("\\.")[0]);

                switch (opcion) {
                    case 1 ->   vueloControl.insert();
                    case 2 ->   vueloControl.modify();
                    case 3 ->   vueloControl.delete();
                    case 4 ->   vueloControl.searchAll();
                    case 5 ->   vueloControl.searchByCode();
                    case 6 ->   pasajeroControl.insert();
                    case 7 ->   pasajeroControl.modify();
                    case 8 ->   pasajeroControl.delete();
                    case 9 ->   pasajeroControl.searchAll();
                    case 10 ->  vueloControl.searchFlightByDestinarion();
                    case 11 ->  vueloControl.searchByCodeToPassanger();
                    case 12 ->  vueloControl.searchByCodeToOnePassanger();
                    case 13 -> isFinished=true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Introduzca un numero valido");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }while (!isFinished);
    }
}
