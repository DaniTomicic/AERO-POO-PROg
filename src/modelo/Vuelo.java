package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vuelo {
    private String codVuelo;
    private LocalDate fechaSalida;
    private String destino;
    private String origen;
    private ArrayList<Pasajero> pasajeros;

    public Vuelo(String codVuelo, LocalDate fechaSalida, String destino, String origen, ArrayList<Pasajero> pasajeros) {
        this.codVuelo = codVuelo;
        this.fechaSalida = fechaSalida;
        this.destino = destino;
        this.origen = origen;
        this.pasajeros = pasajeros;
    }

    public Vuelo(String origen, String destino, LocalDate fechaSalida, String codVuelo) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.codVuelo = codVuelo;
    }

    public Vuelo() {}

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public ArrayList<Pasajero> getPasajeros() {
        if (pasajeros == null) pasajeros = new ArrayList<Pasajero>();
        return pasajeros;
    }

    public void setPasajeros(ArrayList<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }
}
