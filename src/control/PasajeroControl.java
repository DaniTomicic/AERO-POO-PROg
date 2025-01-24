package control;


import modelo.Pasajero;
import modelo.PasajeroDAO;
import modelo.Vuelo;
import modelo.VueloDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasajeroControl {
    private static PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private static VueloDAO vueloDAO = new VueloDAO();
    private static Pasajero p = new Pasajero();
    private static ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private static ArrayList<String> codVueloDestino= new ArrayList();
    public PasajeroControl() {
    }

    public void insert() {
        try {
            p = validarDatos();

            pasajeroDAO.create(p);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void modify(){
        try {
            pasajeros = pasajeroDAO.read() ;

            String[] opciones = pasajeros.stream().map(Pasajero::getCodVuelo).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            Pasajero nuevoPasajero = new Pasajero(seleccion);
            nuevoPasajero.setCodVuelo(datos("Codigo de vuelo","ingresa el codigo del vuelo", Pattern.compile("^AEA[1-9][0-9]{4}$")));

            pasajeroDAO.update(nuevoPasajero,p);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Pasajero validarDatos(){
        try {
            String DNI = validarDNI("","Introduza su DNI", Pattern.compile("^[1-9][0-9]{7}[A-Z]$"));

            String nombre = datos("","Introduzca su nombre",Pattern.compile("^[A-Za-zñÑáéíóúüÁÉÍÓÚÜ]+([ '-][A-Za-zñÑáéíóúüÁÉÍÓÚÜ]+)*$"));

            String telefono = datos("","Introduza su numero de telefono",Pattern.compile("^[6-9][0-9]{8}$"));

            String codigoVuelo = seleccionarCodVuelo("Destino","Seleccione su destino");

            p = new Pasajero(DNI,nombre,telefono,codigoVuelo);

        }catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos no acepados");
        }
        return p;
    }
    public static String datos(String dato, String mensaje, Pattern patron) {
        boolean isValidDato = false;
        do {
            try {
                dato = JOptionPane.showInputDialog(null, mensaje);
                Matcher matcher = patron.matcher(dato);

                if (dato.isEmpty()){
                    JOptionPane.showMessageDialog(null, "no se aceptan esos valores: " + mensaje);
                }else if (matcher.matches()){
                    isValidDato = true;
                }

            }catch (NullPointerException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Datos no acepados");
            }
        }while (!isValidDato);

        return dato;

    }
    public void delete() throws SQLException {
        try {
            pasajeros = pasajeroDAO.read();

            String[] opciones = pasajeros.stream().map(Pasajero::getCodVuelo).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            pasajeroDAO.delete(p);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchAll()throws SQLException{
        try {
            pasajeros = pasajeroDAO.read();

            for (Pasajero pasajero : pasajeros) {
                JOptionPane.showMessageDialog(null,pasajero.toString());
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static String validarDNI(String DNI, String mensaje, Pattern pattern) {
        boolean isValidDni=false;
        do {
            try {
                DNI = JOptionPane.showInputDialog(null, mensaje);
                Matcher matcher = pattern.matcher(DNI);
                if (matcher.matches()) {
                    isValidDni = true;
                }else {
                    JOptionPane.showMessageDialog(null, "El patron del DNI no es aceptado");
                }
            }catch (NullPointerException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Datos no acepados " + e.getMessage());
            }
        }while (!isValidDni);

        return DNI;
    }
    public static String seleccionarCodVuelo(String codVuelo, String mensaje) {
        boolean isValidCodVuelo=false;
        List<String> destinos;
        List<Vuelo> vuelos = vueloDAO.read();

        destinos = vuelos.stream().map(Vuelo::getDestino).distinct().toList();

        String[] destinosArray = destinos.toArray(new String[0]);
        do {
            try {

                String eleccion = (String) JOptionPane.showInputDialog(null,
                        mensaje,
                        "Selecciona el destino",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        destinosArray,
                        destinosArray[0]
                );

                if (codVuelo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El destino no puede estar vacio");
                }else {
                    p.setCodVuelo(String.valueOf(vueloDAO.readByDestination(eleccion)));
                    isValidCodVuelo = true;
                }

            }catch (NullPointerException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Datos no acepados");
            }
        }while (!isValidCodVuelo);

        return codVuelo;

    }

}

