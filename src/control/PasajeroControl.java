package control;

import EXCP.ExcepcionDani;
import modelo.Pasajero;
import modelo.PasajeroDAO;
import modelo.Vuelo;
import modelo.VueloDAO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasajeroControl {
    private PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private Pasajero p;
    private ArrayList<Pasajero> pasajeros;
    private VueloControl vueloControl = new VueloControl();
    private VueloDAO vueloDAO = new VueloDAO();

    public PasajeroControl(Pasajero p) {
        this.p = p;
    }
    public PasajeroControl(VueloControl vueloControl){
        this.vueloControl = vueloControl;
    }
    public PasajeroControl(VueloDAO vueloDAO,PasajeroDAO pasajeroDAO){
        this.vueloDAO = vueloDAO;
        this.pasajeroDAO = pasajeroDAO;
    }

    public void insert() {
        try {
            p = solicitarValidarDatos();

            pasajeroDAO.create(p);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void modify(){
        try {
            pasajeros.add(p = pasajeroDAO.read());

            p.setCodVuelo(JOptionPane.showInputDialog(null,"Codigos de vuelo disponibles, cual quieres modificar? \n" + pasajeros));

            Pasajero nuevoPasajero = new Pasajero() ;

            nuevoPasajero.setCodVuelo(solicitarDato("Codigo de vuelo","ingresa el codigo del vuelo", Pattern.compile("^AEA[1-9][0-9]{4}$")));

            pasajeroDAO.update(p);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Pasajero solicitarValidarDatos() throws ExcepcionDani {

        String DNI = solicitarDNI("DNI", "Cual es su DNI?");

        String nombre = solicitarDato("nombre", "Cual es su nombre?", Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ' ]+$")
        );

        String telefono = solicitarDato("Telefono", "Cual es su telefono?", Pattern.compile("^[6789][0-9]{8}$"));

        String codVuelo = solicitarCodVuelo("Codigo de vuelo","ingrese el codigo del vuelo",Pattern.compile("^AEA[0-9][0-9]{4}$"));



        return new Pasajero(DNI, nombre, telefono, codVuelo);
    }

    public String solicitarDNI(String DNI, String mensaje) throws ExcepcionDani {
        boolean DNIValid = false;
        Pattern patronDNI = Pattern.compile("^[0-9]{8}[A-Z]$");

        do {
            try {
                DNI = JOptionPane.showInputDialog(null, mensaje);

                Matcher matcherDNI = patronDNI.matcher(DNI);
                if (matcherDNI.matches()) {
                    DNIValid = true;
                } else {
                    JOptionPane.showMessageDialog(null, DNI + " no es un DNI válido.");
                }

            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            }
        } while (!DNIValid);

        return DNI;
    }

    public String solicitarDato(String dato, String mensaje,Pattern patron) throws ExcepcionDani {
        boolean datoValid=false;

        do {
            try {
                dato = JOptionPane.showInputDialog(null,mensaje);

                Matcher matcherDato = patron.matcher(dato);

                if (matcherDato.matches()){
                    datoValid=true;
                }else {
                    JOptionPane.showMessageDialog(null,dato + " no es valido");
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"numeros de esa clase no aceptados en el Dato" + dato);
            }catch (NullPointerException e){
                JOptionPane.showMessageDialog(null,"El Dato no puede estar vacio");
            }

        }while (!datoValid);
        return dato;
    }
    public String solicitarCodVuelo(String codVuelo, String mensaje,Pattern patron) throws ExcepcionDani {
     boolean isFinished=false;
        do {
            try {
                codVuelo = JOptionPane.showInputDialog(null,mensaje);
                Matcher matcherCodVuelo = patron.matcher(codVuelo);
                if (matcherCodVuelo.matches()){
                    //if(vueloDAO.read()){ //si lo encuentra...
                        isFinished=true;
                    //}
                }
            }catch (NullPointerException e){
                JOptionPane.showMessageDialog(null,"El codigo de vuelo no puede ser nulo");
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"No se aceptan los numeros del codigo de vuelo");
            }

        }while (!isFinished);
        return codVuelo;
    }


}

