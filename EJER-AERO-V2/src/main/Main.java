package main;

import Control.PasajeroControl;
import Control.VueloControl;
import Model.PasajeroDAO;
import Model.VueloDAO;

import javax.swing.*;

public class Main {
    public static VueloControl vueloControl;
    public static PasajeroDAO pasajeroDAO = new PasajeroDAO();
    public static VueloDAO vueloDAO = new VueloDAO();
    private static PasajeroControl pasajeroControl;
    public static void main(String[] args) {
        boolean mainFinished;
        try {
            vueloControl = new VueloControl(vueloDAO);
            pasajeroControl = new PasajeroControl(pasajeroDAO);
            do {
                mainFinished = showMenu();
            }while (!mainFinished);
        } catch (Exception e){
            System.out.println("ERROR " + e.getMessage());
        }
    }
    private static boolean showMenu() {
        boolean showMenuFinished = false;

        String[] optMenu = {
                "1. Crear Vuelo","2. Modificar Vuelo",
                "3. Eliminar Vuelo","4. Mostrar todos los Vuelos",
                "5. Mostrar Vuelo por codigo","6. Crear Pasajero",
                "7. Modificar Pasajero","8. Eliminar Pasajeros",
                "9.Mostrar Pasajeros","10. Vuelos por Destino",
                "11. Pasajeros por vuelo","12. Vuelos de un Pasajero",
                "13. Vuelos por Fecha","14. Salir"
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
                int option =
                        Integer.parseInt(selection.split("\\.")[0]);

                switch (option) {
                    case 1 -> vueloControl.insert();
                    case 2 -> vueloControl.update();
                    case 3 -> vueloControl.delete();
                    case 4 -> vueloControl.search();
                    case 5 -> vueloControl.searchByCodVuelo();
                    case 6 -> pasajeroControl.insert();
                    case 7 -> pasajeroControl.update();
                    case 8-> pasajeroControl.delete();
                    case 9-> pasajeroControl.search();
                    case 10-> vueloControl.flightByDestinarion();
                    //case 11->
                    case 12-> pasajeroControl.searchFlightOfPassanger();
                    case 13-> vueloControl.searchByDate();
                    case 14-> showMenuFinished=true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Seleccione un numero valido");
            }
        }while (!showMenuFinished);
        return showMenuFinished;
    }
}