package control;

import modelo.Pasajero;
import modelo.Vuelo;
import modelo.VueloDAO;

import java.time.LocalDate;
import java.util.ArrayList;

public class VueloControl {
    private VueloDAO vueloDAO = new VueloDAO();

    public void createVuelo(String codVuelo, LocalDate fechaSalida, String destino, String origen, ArrayList<Pasajero> pasajeros) {

        Vuelo vuelo = new Vuelo();
        vueloDAO.save(vuelo);
        System.out.println("exito");
    }
}
