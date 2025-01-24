package control;

import EXCP.ExcepcionDani;
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
    private ArrayList<Vuelo> vuelos = new ArrayList<>();

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

            nuevoVuelo.setCodVuelo(solicitarDato("Codigo de vuelo","ingresa el codigo del vuelo", Pattern.compile("^AEA[1-9][0-9]{4}$")));

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

            vueloDAO.delete(v);

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
        
        String origen = solicitarDato("Origen","Origen del vuelo", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));
        
        String destino = solicitarDato("Destino","Destino del vuelo?", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));

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




    public String solicitarDato(String dato, String mensaje, Pattern formato) throws ExcepcionDani {
        boolean isValidDato=false;
        Matcher matcher = formato.matcher(dato);
        do {
            try {
                dato = JOptionPane.showInputDialog(null,mensaje);

                if (dato.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Ingrese el valor de dato");
                } else if (!matcher.matches()) {
                    isValidDato = true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "No se aceptan numeros en este lugar");
            }catch (MatchException | PatternSyntaxException e){
                JOptionPane.showMessageDialog(null,"El codigo de vuelo debe contener: "+ formato);
            }
        }while (!isValidDato);
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
