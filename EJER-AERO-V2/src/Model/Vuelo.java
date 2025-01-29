package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vuelo {
    private String codVuelo;
    private LocalDate fechaSalida;
    private String procedencia;
    private String destino;
    private ArrayList<Pasajero> pasajeros;

    public Vuelo(String codVuelo, LocalDate fechaSalida, String procedencia, String destino) {
        this.codVuelo = codVuelo;
        this.fechaSalida = fechaSalida;
        this.procedencia = procedencia;
        this.destino = destino;
        this.pasajeros = new ArrayList<Pasajero>();
    }

    public Vuelo() {
    }

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

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Pasajero getPasajeros(int x) {
        return pasajeros.get(x);
    }

    public void setPasajeros(Pasajero pasajeros) {
        this.pasajeros.add(pasajeros);
    }



    @Override
    public String toString() {
        return getCodVuelo() + " - "
                + getFechaSalida() + " - "
                + getProcedencia() + " - "
                + getDestino() + "\n";
    }
}
