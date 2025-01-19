package control;

import modelo.Pasajero;
import modelo.Vuelo;
import modelo.VueloDAO;

import java.time.LocalDate;
import java.util.ArrayList;



public class VueloControl {

    public void createVuelo(String codVuelo, LocalDate fechaSalida, String destino, String origen) {

        Vuelo vuelo = new Vuelo();
        VueloDAO.newVuelo(vuelo);
        System.out.println("exito");
    }
}
