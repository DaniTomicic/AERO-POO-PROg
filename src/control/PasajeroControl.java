package control;

import modelo.Pasajero;
import modelo.PasajeroDAO;
import modelo.Vuelo;


public class PasajeroControl {
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();

    public void createPasajero(String DNI, String nombre, String telefono, String cod_vuelo, Vuelo vuelo) {

        Pasajero pasajero = new Pasajero();
        pasajeroDAO.save(pasajero);
        System.out.println("exito");
    }


}

