package modelo;

public class Pasajero {
    private String DNI;
    private String nombre;
    private String telefono;
    private String codVuelo;
    private Vuelo vuelo;

    public Pasajero(String DNI, String nombre, String telefono, String codVuelo,Vuelo vuelo) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codVuelo = codVuelo;
        this.vuelo = vuelo;
    }

    public Pasajero(String DNI, String codVuelo, String telefono, String nombre) {
        this.DNI = DNI;
        this.codVuelo = codVuelo;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public Pasajero() {

    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }


}
