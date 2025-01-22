package control;

import EXCP.ExcepcionDani;
import modelo.Vuelo;
import modelo.VueloDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VueloControl {
    private VueloDAO vueloDAO;
    private Vuelo v = new Vuelo();
    private ArrayList<Vuelo> vuelos = new ArrayList<>();
    private int contadorCABECERA = 1;
    private int contadorCOLA = 1;

    public VueloControl(VueloDAO vueloDAO){
        this.vueloDAO = vueloDAO;
    }

    public VueloControl() {

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
            v = vueloDAO.read();
            v.setCodVuelo(JOptionPane.showInputDialog(null,"Codigos de vuelo disponibles, cual quieres modificar? \n" + v.getCodVuelo()));
            Vuelo nuevoVuelo = new Vuelo();

            nuevoVuelo.setCodVuelo(solicitarDato("Codigo de vuelo","ingresa el codigo del vuelo", Pattern.compile("^AEA[1-9][0-9]{4}$")));

            vueloDAO.update(nuevoVuelo,v);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void delete(){
        try {
            v = vueloDAO.read();
            Vuelo nuevoVuelo = new Vuelo();
            nuevoVuelo.setCodVuelo(JOptionPane.showInputDialog(null,"Codigos de vuelo disponibles, cual quieres modificar? \n" + v.toString()));

            vueloDAO.delete(nuevoVuelo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void searchAll(){
        try {

            vueloDAO.searchVuelo(v);

            JOptionPane.showMessageDialog(null, Objects.requireNonNullElse(v, "No hay vuelos"));

        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void search(){
        try {

            vuelos.add(v = vueloDAO.read());

            if (v !=null){
                vuelos.forEach(v -> JOptionPane.showMessageDialog(null, v.getCodVuelo()));
            }else {
                JOptionPane.showMessageDialog(null,"No hay vuelos");
            }

        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vuelo solicitarValidarDatos() throws ExcepcionDani {
        String codVuelo = generarCodvuelo();
        
        LocalDate fechaValida = solicitarDatoFecha("fecha","A que fecha es el vuelo? dd/mm/aaaa");
        
        String origen = solicitarDato("Origen","Origen del vuelo", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));
        
        String destino = solicitarDato("Destino","Destino del vuelo?", Pattern.compile("^[a-zA-ZñÑáÁéÉíÍóÓúÚ]$"));

        return new Vuelo(codVuelo, fechaValida, origen, destino);
    }



    private String generarCodvuelo() throws ExcepcionDani {
        String codigoGenerado = "";
        final String CABECERA = "AEA";

        try {
            codigoGenerado = String.format("%s%d%04d", CABECERA, contadorCABECERA, contadorCOLA);

            contadorCOLA++;

            if (contadorCOLA > 9999) {
                contadorCABECERA++;
                contadorCOLA = 1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Error al generar el codigo del vuelo " + e.getMessage());
        }

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
            }catch (MatchException e){
                JOptionPane.showMessageDialog(null,"El valor de dato es incorrecto");
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

    @Override
    public String toString() {
        return v.getCodVuelo() + "-" + v.getFechaSalida() + "-" + v.getPorcedencia() + "-" + v.getDestino();
    }
}
