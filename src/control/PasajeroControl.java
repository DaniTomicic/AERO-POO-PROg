package control;

import modelo.Pasajero;
import modelo.PasajeroDAO;


public class PasajeroControl {
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public static void createPasajero(String DNI, String nombre, String telefono, String cod_vuelo) {

        Pasajero pasajero = new Pasajero();
        PasajeroDAO.save(pasajero);
        System.out.println("exito");
    }


}

