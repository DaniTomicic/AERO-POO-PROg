package Model;

public class Pasajero {

    private String dni;
    private String nombre;
    private String telefono;
    private String codVuelo;

    public Pasajero(String dni, String nombre, String telefono, String codVuelo) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codVuelo = codVuelo;
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

    @Override
    public String toString() {
        return getDni() + " - "
                + getNombre() + " - "
                + getTelefono() + " - "
                + getCodVuelo() +"\n";
    }
}
