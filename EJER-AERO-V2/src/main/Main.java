package main;

import BD.DBCon;
import Control.PasajeroControl;
import Control.VueloControl;
import Model.PasajeroDAO;
import Model.VueloDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static VueloControl vueloControl;
    public static VueloDAO vueloDAO = new VueloDAO();
    private static PasajeroControl pasajeroControl;
    public static PasajeroDAO pasajeroDAO = new PasajeroDAO();
    public static void main(String[] args) {
        try(Connection con = DBCon.getConnection()){
            boolean mainFinished;
            if (con != null){
                vueloControl = new VueloControl(vueloDAO);
                pasajeroControl = new PasajeroControl(pasajeroDAO);

                do {

                    mainFinished = showMenu();

                }while (!mainFinished);
            }else {
                JOptionPane.showMessageDialog(null,"Error en obtener conexion");
                throw new RuntimeException();
            }
        } catch (SQLException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }
    private static boolean showMenu() {
        boolean showMenuFinished = false;
        String[] optMenu =               {
                "1. Crear Vuelo","2. Modificar Vuelo",
                "3. Eliminar Vuelo","4. Mostrar todos los Vuelos",
                "5. Mostrar Vuelo por codigo","6. Crear Pasajero",
                "7. Modificar Pasajero","8. Eliminar Pasajeros",
                "9.Mostrar Pasajeros","10. Vuelos por Destino",
                "11. Pasajeros por vuelo","12. Vuelos de un Pasajero",
                "13. Vuelos por Fecha",
                "14. Salir"
        };

        do {
            try {
                String selection = (String) JOptionPane.showInputDialog(
                        null,
                        "Available options",
                        "options",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        optMenu,
                        optMenu[0]
                );
                int option = Integer.parseInt(selection.split("\\.")[0]);

                switch (option) {
                    case 1 -> vueloControl;
                    case 2 ->;
                    case 3 ->;
                    case 4 ->;
                    case 5 ->;
                    case 6 ->;
                    case 7 ->;
                    case 8->;
                    case 9->;
                    case 10->;
                    case 11->;
                    case 12-> ;
                    case 13->;
                    case 14-> showMenuFinished=true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Seleccione un numero valido");
            }
        }while (!showMenuFinished);

        return showMenuFinished;
    }
}