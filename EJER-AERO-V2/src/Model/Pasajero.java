package Model;

import java.util.ArrayList;

public class Pasajero {

    private String dni;
    private String nombre;
    private String telefono;
    private String codVuelo;
    private ArrayList<Vuelo> vuelos = new ArrayList();

    public Pasajero(String dni, String nombre, String telefono, String codVuelo) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codVuelo = codVuelo;
        this.vuelos = vuelos;
    }

    public Pasajero(String dni, String nombre, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Pasajero() {

    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public ArrayList<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(ArrayList<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public void addVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }
    public Vuelo getVuelo(int i) {
        return vuelos.get(i);
    }

    @Override
    public String toString() {
        return getDni() + " - "
                + getNombre() + " - "
                + getTelefono() + " - "
                + getCodVuelo() +"\n";
    }
}
