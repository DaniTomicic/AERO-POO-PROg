package control;

import EXCP.ExcepcionDani;
import modelo.Pasajero;
import modelo.*;
import modelo.Vuelo;
import modelo.VueloDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class VueloControl {
    private final VueloDAO vueloDAO=new VueloDAO();
    private Vuelo v = new Vuelo();
    private Pasajero p = new Pasajero();
    private ArrayList<Vuelo> vuelos = new ArrayList<>();
    private ArrayList<Pasajero> pasajeros = new ArrayList<>();
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();
    public VueloControl(){
    }

    public void insert() {
        try {
            v = solicitarValidarDatos();

            vueloDAO.create(v);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void modify(){
        try {
            vuelos = vueloDAO.read();

        String[] opciones = vuelos.stream().map(Vuelo::getCodVuelo).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            Vuelo nuevoVuelo = new Vuelo(seleccion);

            nuevoVuelo.setCodVuelo(solicitarDato("ingresa el codigo del vuelo", Pattern.compile("^AEA[1-9][0-9]{4}$")));

            vueloDAO.update(nuevoVuelo,v);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void delete() throws SQLException {
        try {
            vuelos = vueloDAO.read();

            String[] opciones = vuelos.stream().map(Vuelo::getCodVuelo).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            vueloDAO.delete(seleccion);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void searchAll()throws SQLException{
        try {
            vuelos = vueloDAO.read();

            for (Vuelo vuelo : vuelos) {
                JOptionPane.showMessageDialog(null,vuelo.toString());
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchByCode(){
        try {
            vuelos = vueloDAO.read();

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    vuelos
            );
            v = vueloDAO.readByCode(seleccion);

            JOptionPane.showMessageDialog(null,v.toString());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchByCodeToPassanger(){
        try {
            vuelos = vueloDAO.read();
            String[] codVuelos = vuelos
                    .stream().map
                    (Vuelo::getCodVuelo)
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    codVuelos,
                    codVuelos[0]
            );

            v = vueloDAO.readByCode(seleccion);

            pasajeros = pasajeroDAO.readByFlight(v);

            for (Pasajero p : pasajeros) {
                JOptionPane.showMessageDialog(null,p.toString());
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchByCodeToOnePassanger(){
        try {
            vuelos = vueloDAO.read();
            String[] codVuelos = vuelos
                    .stream().map
                            (Vuelo::getCodVuelo)
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    codVuelos,
                    codVuelos[0]
            );

            v = vueloDAO.readByCode(seleccion);

            p = pasajeroDAO.readByOneFlight(v);

            String m = (p == null) ? "No hay vuelos disponibles" : p.toString();
            JOptionPane.showMessageDialog(null,m);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void searchFlightByDestinarion(){
        try {
            vuelos = vueloDAO.read();
            String[] destinos = vuelos.stream().map(Vuelo::getDestino).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo:",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    destinos,
                    destinos[0]
            );

            String codVuelo = vueloDAO.readByDestination(seleccion);
            v = vueloDAO.readByCode(codVuelo);

            JOptionPane.showMessageDialog(null,v.toString());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Vuelo solicitarValidarDatos() throws ExcepcionDani, SQLException {
        String codVuelo = generarCodVuelo();
        
        LocalDate fechaValida = solicitarDatoFecha("fecha","A que fecha es el vuelo? dd/mm/aaaa");
        
        String origen = solicitarDato("Origen del vuelo", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));
        
        String destino = solicitarDato("Destino del vuelo?", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));

        return new Vuelo(codVuelo, fechaValida, origen, destino);
    }



    private String generarCodVuelo() throws ExcepcionDani, SQLException {
        String codigoGenerado;
        String CABECERA = "AEA";
        int contadorCABECERA = 1; //
        int contadorCOLA = 1;

        vuelos = vueloDAO.read();


        boolean codigoExiste;
        do {
            codigoGenerado = String.format("%s%d%04d", CABECERA, contadorCABECERA, contadorCOLA);
            contadorCOLA++;

            if (contadorCOLA > 9999) {
                contadorCABECERA++;
                contadorCOLA = 1;
            }

            codigoExiste = false;
            for (Vuelo vuelo : vuelos) {
                if (vuelo.getCodVuelo().equals(codigoGenerado)) {
                    codigoExiste = true;
                    break;
                }
            }

        } while (codigoExiste);

        return codigoGenerado;
    }




    public String solicitarDato(String mensaje, Pattern formato) throws ExcepcionDani {
        String dato = null;
        boolean isValidDato = false;
        do {
            try {
                dato = JOptionPane.showInputDialog(null, mensaje);
                if (dato == null || dato.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El valor no puede estar vacío.");
                    continue;
                }
                if (!formato.matcher(dato).matches()) {
                    JOptionPane.showMessageDialog(null, "El formato del dato es inválido. Intente nuevamente.");
                    continue;
                }
                isValidDato = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al validar el dato.");
            }
        } while (!isValidDato);
        return dato;
    }

    public LocalDate solicitarDatoFecha(String fecha,String mensaje) {
        boolean isValidFecha=false;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaValida = null;
        do {
            
            try {
                
                fecha = JOptionPane.showInputDialog(null,mensaje);
                
                if (fecha == null || fecha.isEmpty()){
                    JOptionPane.showMessageDialog(null,"la fecha debe estar completa");
                }else {
                    fechaValida = LocalDate.parse(fecha,dtf);
                    isValidFecha = true;
                }
                
            }catch (DateTimeException e){
                System.out.println("Error al dar formato de fecha: "+ e.getMessage());
            }
        }while (!isValidFecha);
            
        
    return fechaValida;
    }

}
