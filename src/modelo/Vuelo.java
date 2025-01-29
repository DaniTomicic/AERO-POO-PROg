package modelo;

import java.time.LocalDate;

public class Vuelo {
    private String codVuelo;
    private LocalDate fechaSalida;
    private String destino;
    private String porcedencia;

    public Vuelo(String codVuelo, LocalDate fechaSalida, String destino, String procedencia) {
        this.codVuelo = codVuelo;
        this.fechaSalida = fechaSalida;
        this.destino = destino;
        this.porcedencia = procedencia;
    }

    public Vuelo() {}

    public Vuelo(String codVuelo) {
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

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPorcedencia() {
        return porcedencia;
    }

    public void setPorcedencia(String origen) {
        this.porcedencia = porcedencia;
    }

    @Override
    public String toString() {
        return "Codgo: " + codVuelo +
                "\n- Fecha de salida: " + fechaSalida +
                "\n- Destino: " + destino +
                "\n- Origen: " + porcedencia;
    }
}
