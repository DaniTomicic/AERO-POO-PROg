package Control;

import Model.Pasajero;
import Model.PasajeroDAO;
import Model.Vuelo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.Main.vueloDAO;

public class PasajeroControl {
    private PasajeroDAO pasajeroDAO;
    private Pasajero p;

    public PasajeroControl(PasajeroDAO pasajeroDAO) {
        this.pasajeroDAO = pasajeroDAO;
        this.p = null;
    }

    public void insert() {
        try {
            String dni = this.dataValidation("DNI", "Teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$");
            Pasajero p = pasajeroDAO.search(dni);

            if (p == null) {
                p = this.dataValidationApplication(dni); // Creamos un nuevo pasajero
                if (p != null) {
                    this.pasajeroDAO.create(p);
                    this.searchAndAssignCodVuelo(dni);
                    p.setVuelos();
                    JOptionPane.showMessageDialog(null, "Pasajero dado de alta en la Base de Datos");

                }
            } else {
                this.searchAndAssignCodVuelo(dni);
            }

        } catch (Exception e) {
            System.out.println("Error en la inserción: " + e.getMessage());
        }
    }

    public void update() {
        try {
            String dni = this.dataValidation("DNI", "Teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$");
            Pasajero pas = pasajeroDAO.search(dni);

            if (pas == null) {
                JOptionPane.showMessageDialog(null, "No se encontró un pasajero con ese DNI");
                return;
            }

            String[] modificaciones = {"Nombre", "Teléfono", "Código de vuelo"};

            String eleccion = (String) JOptionPane.showInputDialog(null,
                    "Elija qué quiere modificar",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    modificaciones,
                    modificaciones[0]
            );

            switch (eleccion) {
                case "Nombre" -> {
                    pas.setNombre(this.dataValidation("Nombre", "Teclea el nuevo nombre", "^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ ]+$"));
                    pasajeroDAO.update(pas);
                }

                case "Teléfono" -> {
                    pas.setTelefono(this.dataValidation("Teléfono", "Teclea el nuevo teléfono", "^[678][0-9]{8}$"));
                    pasajeroDAO.update(pas);
                }

                case "Código de vuelo" -> {
                    String nuevoCodVuelo = this.searchAndAssignCodVuelo(dni);
                    pas.setCodVuelo(nuevoCodVuelo);

                    ArrayList<Vuelo> vuelosEncontrados = pasajeroDAO.searchFlights(dni);

                    if (vuelosEncontrados != null && !vuelosEncontrados.isEmpty()) {

                        pas.setVuelos(vuelosEncontrados);
                        vueloDAO.setVuelosPasajeros(nuevoCodVuelo,dni);
                    } else {
                        pas.setVuelos(new ArrayList<>());
                    }
                }
            }


            pasajeroDAO.update(pas);
            JOptionPane.showMessageDialog(null, "Pasajero actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error en la actualización: " + e.getMessage());
        }
    }


    public void delete() {
        try {
            String dni = this.dataValidation("DNI", "Teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$");
            p = pasajeroDAO.search(dni);

            if (p == null) {
                JOptionPane.showMessageDialog(null, "No se encontró un pasajero con ese DNI");
                return;
            }

            pasajeroDAO.delete(p);
            JOptionPane.showMessageDialog(null, "Pasajero eliminado correctamente");

        } catch (Exception e) {
            System.out.println("Error al eliminar pasajero: " + e.getMessage());
        }
    }

    public void search() {
        try {
            ArrayList<Pasajero> disponibles = pasajeroDAO.read();
            if (disponibles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay pasajeros en la base de datos");
                return;
            }

            for (Pasajero p : disponibles) {
                JOptionPane.showMessageDialog(null, p.toString());
            }

        } catch (Exception e) {
            System.out.println("Error en la búsqueda: " + e.getMessage());
        }
    }

    private Pasajero dataValidationApplication(String dni) {
        String name = this.dataValidation("Nombre", "Teclea el nombre del pasajero", "^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");
        String cellphone = this.dataValidation("Teléfono", "Teclea el número de teléfono del pasajero", "^[678][0-9]{8}$");
        String codVuelo = this.searchAndAssignCodVuelo(dni);

        if (dni == null || dni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: El DNI del pasajero no puede estar vacío.");
            return null;
        }

        return new Pasajero(dni, name, cellphone, codVuelo);
    }

    private String searchAndAssignCodVuelo(String dni) {
        String codVuelo = this.dataValidation("codigo de vuelo","Teclea el codigo del vuelo","^AEA[0-9][0-9]{4}$");
        Vuelo v = vueloDAO.search(codVuelo);

        if (v == null) {
            JOptionPane.showMessageDialog(null, "No existe ese vuelo");
        }else {
            vueloDAO.setVuelosPasajeros(codVuelo,dni);
        }
        return codVuelo;
    }

    public void searchFlightOfPassanger() {
        String[] pasajerosCod = pasajeroDAO.read().stream()
                .map(Pasajero::getDni)
                .toArray(String[]::new);

        if (pasajerosCod.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay pasajeros en la base de datos");
            return;
        }

        try {
            String option = (String) JOptionPane.showInputDialog(null,
                    "Seleccione el DNI del pasajero", "Opciones",
                    JOptionPane.PLAIN_MESSAGE, null, pasajerosCod, pasajerosCod[0]
            );

            if (option == null) return;

            ArrayList<Vuelo> vuelos = pasajeroDAO.searchFlights(option);

            if (vuelos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron vuelos para este pasajero");
            } else {
                for (Vuelo v : vuelos) {
                    JOptionPane.showMessageDialog(null, v.toString());
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la búsqueda de vuelos: " + e.getMessage());
        }
    }

    private String dataValidation(String data, String msj, String pattern) {
        String var;
        boolean valid = false;

        do {
            try {
                var = JOptionPane.showInputDialog(null, msj);

                if (var == null || var.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El dato " + data + " no puede estar vacío");
                    continue;
                }

                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(var);

                if (m.matches()) {
                    valid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Formato inválido para " + data);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en la validación de " + data);
                return "";
            }

        } while (!valid);

        return var;
    }
}
